<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!-- 以上自行取用 -->
<%@ include file="../include/common_link.jsp"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"></jsp:include>
<link href="${root}/css/bootstrap.min.css" rel="stylesheet">
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
ol, ul {
	padding-left: 0rem;
}

body {
	color: #858796;
}

.box {
	width: 60%;
	margin: auto;
}

.showExistGameArea {
	margin-top: 20px;
	background-color: #F0EBE2;
}

.myLove {
	background-color: #F0EBE2;
}

.searchGame {
	background-color: #F0EBE2;
}

.chooseGame {
	margin: 20px 10px;
	border: gray solid 1px;
	border-radius: 10px;
}

.existCertainGameArea {
	
}

.playerImgCircle {
	width: 40px;
	height: 40px;
	border: 1px black solid;
	border-radius: 50%;
}

.waitingTable {
	border: 1px black solid;
	border-radius: 10px;
	display: flex;
	margin-top: 10px;
}

.playerN {
	display: flex;
	margin-right: 20px;
	position: relative;
}

.joinRoom {
	position: absolute;
	right: 20px;
}

.leaveRoom {
	position: absolute;
	right: 20px;
}

.myLoveGameArea {
	display: flex;
	
}

.lovedGameImg {
	width: 225px;
	height: 225px;
}

.lovedGame {
	text-align: center;
	margin-right:5px;
}
</style>
</head>
<body>
	<div style="width: 100%; position: relative; top: 75px; margin-bottom:120px">
		<div class="box">
			<div class="showExistGameArea"></div>
			<div class="myLove">
				<h3>我的最愛</h3>
				<div class="myLoveGameArea flex-wrap justify-content-around">
					<c:if test="${favoritelist.size() > 0}">
					<c:forEach begin="0" end="${favoritelist.size()-1}" step="1" var="i">
						<div class="lovedGame">
							<img class="lovedGameImg" src="${root}/mall/getPhoto?pId=${favoritelist[i].productId}"><br />
							<span style="font-size: 20px;">${favoritelist[i].productName}</span><br />
							<button onclick="showExistGameArea(this.id)" id="${favoritelist[i].productName}">尋找可加入的房間</button>
						</div>
					</c:forEach>
					</c:if>
					<c:if test="${favoritelist.size() == 0}">
						<h3>還沒有最愛遊戲喔，快去 <a href="${root}/mall/product">遊戲列表</a> 增加吧</h3>
					</c:if>
				
<!-- 					<div class="lovedGame"> -->
<%-- 						<img class="lovedGameImg" src="${root}/mall/getPhoto?pId=3"><br /> --%>
<!-- 						<span style="font-size: 20px;">Bridge</span><br /> -->
<!-- 						<button onclick="showExistGameArea(this.id)" id="Bridge">尋找可加入的房間</button> -->
<!-- 					</div> -->

				</div>
			</div>
			<div class="searchGame"></div>
		</div>
	</div>

	<script type="text/javascript">
		//宣告在ws的js上
		isGameLobbyPage = true;
//		//依照遊戲名展開或收合目前可加入的房間列表
		function showExistGameArea(gameName){
			//判斷目前是開還是關的狀態
			let status = $("#"+gameName).text();
			if(status == "尋找可加入的房間"){
				$("#"+gameName).text("收合");
			}
			if(status == "收合"){
				$("#"+gameName).text("尋找可加入的房間");
				$("."+gameName).remove();
				return;
			}
			
			axios.get("${root}/game/showExistGameByGameName/"+gameName).then(function(response){
				console.log("按下立即加入按鈕的回應",response);
				let json = response.data.table;
				let id = response.data.productId;
				console.log(json);
				
				let keyArray = Object.keys(json);
				let valueArray = Object.values(json);
				$("."+gameName).empty();
				$(".showExistGameArea").append(`
						<div class="chooseGame `+gameName+`">
						<div  class="container text-center">
							<div class="row">
								<div class="col-4">
									<h4>`+gameName+`</h4>
									<img style="width:225px;height:225px;" src="${root}/mall/getPhoto?pId=`+id+`" alt="">
								</div>
								<div class="col-8 existCertainGameArea">
									
								</div>
							</div>
						</div>
						<button onclick="showExistGameArea('`+gameName+`')">收合</button>
					</div>
						`)
				for(let i = 0;i<keyArray.length;i++){
					let finalNumOfPlayer = valueArray[i].finalNumOfPlayer;
					let hostEmail = valueArray[i].hostEmail;
					let maxNumOfPlayer = valueArray[i].maxNumOfPlayer;
					let minNumOfPlayer = valueArray[i].minNumOfPlayer;
					let players = valueArray[i].players;
					let isInTheRoom = false;
					let eachTableStr = `<div class="waitingTable">`;
					for(let j = 0;j<players.length;j++){
						eachTableStr += `<div class="playerN">
											<div><img class="playerImgCircle" src="${root}/member/findMemberImg/`+players[j].memberId+`"></div>
											<span>`+players[j].memberName+`</span>
										</div>`;
						//判斷該使用者是否已在房間內
						if(players[j].memberEmail == "${member.memberEmail}"){
							isInTheRoom = true;
						}
					}
					for(let j = 0;j<finalNumOfPlayer - players.length;j++){
						eachTableStr += `<div class="playerN">
							<div><img class="playerImgCircle" src="${root}/img/michael/question-mark.png"></div>
							<span>(自由位)</span>
						</div>`;
					}
					if(!isInTheRoom){
						eachTableStr += `<form action="${root}/game/joinGame/`+keyArray[i]+`/${member.memberEmail}">
											<button id="`+keyArray[i]+`" class="joinRoom">加入房間</button>
										</form>
									</div>`;
					}else{
						eachTableStr += `<form action="${root}/game/playerLeaveGame/`+keyArray[i]+`/${member.memberEmail}">
									<button id="`+keyArray[i]+`" class="leaveRoom">離開房間</button>
								</form>
							</div>`;
					}
					$("."+gameName).find(".existCertainGameArea").append(eachTableStr);
				}
				
			}).catch(function(error){
				console.log("按下立即加入按鈕的錯誤",error);
			}).finally(function(){
				
			});
		}
		
		

		
	</script>
	<jsp:include page="/WEB-INF/jsp/include/footer.jsp"></jsp:include>
</body>
</html>