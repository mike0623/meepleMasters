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
<style>
ol, ul {
     padding-left: 0rem;
 }
 body{ 
 	color: #858796; 
 } 
.box {
	width: 60%;
	margin: auto;
}

.gameType {
	font-size: 40px;
	background-color: #dfa661;
	margin-top: 20px;
	height: 260px;
	margin-bottom: 50px;
}

.playersAndSetting {
	background-color: #e0b583;
	padding-top: 10px;
	padding-bottom: 5px;
}

.degreeLine {
	background-color: #e0ccc5;
	width: 90%;
	height: 50px;
}

.player {
	background-color: white;
	margin-bottom: 10px;
	margin-right: 10px;
	margin-left: 10px;
	border: gray solid 1px;
	border-radius: 10px;
}

.degree {
	background-color: blue;
	height: 50px;
}
.inviteFriendToGame{
	width:400px;
	border:1px solid black;
}
</style>
</head>

<body>
<div style="width:100%; position:relative; top:75px; margin-bottom:120px">
	<div class="box">
		<div class="gameType">
			<div class="container text-center">
				<div class="row">
					<div class="col-4">
						<img style="height: 225px;"
							src="${root}/mall/getPhoto?pId=${game.productId}" alt="">
					</div>
					<div class="col-8">
						<span>${game.productName}</span><br /> <span>新遊戲桌</span><br /> <span>預計遊玩時間:${game.productPlayTime}</span><br />
						<c:if test="${ishost}">
							<c:if test="${gameStatus == 0}">
								<a id="createRoomA" href="${root}/game/finalNumOfPlayer/${game.productName}/${member.memberEmail}/${game.productMinPlayer}"><button type="submit" class="btn btn-primary">創建房間(請確認遊戲人數)</button></a>
								<a href="${root}/game/playGameLobby"><button type="submit" class="btn btn-primary">離開房間</button></a>
							</c:if>
							<c:if test="${gameStatus == 1 or gameStatus == 2}">
								<c:if test="${gameStatus != 2}">
									<button id="${tableCode}" onclick="openRoom()" class="openRoom btn btn-primary">開放其他玩家加入</button>
								</c:if>
								<button onclick="inviteFriendButton()" id="inviteFriendButton" class="btn btn-primary">邀請好友</button>
								<button onclick="startButton()" id="startButton" class="btn btn-primary">開始遊戲</button>
								<a href="${root}/game/leaveGame/${tableCode}"><button class="btn btn-primary">離開房間</button></a>
							</c:if>
						</c:if>
						<c:if test="${ishost == false}">
							<a href="${root}/game/playerLeaveGame/${tableCode}/${member.memberEmail}"><button class="btn btn-primary">離開房間</button></a>
						</c:if>
					</div>
				</div>
			</div>
		</div>
		<div class="inviteFriendToGame">
			
		</div>
		<div class="playersAndSetting">
			<c:if test="${ishost && gameStatus == 0}">
			<div class="setting">
				玩家數:<input id="numOfPlayers" type="number" max="${game.productMaxPlayer}" min="${game.productMinPlayer}" value="${game.productMinPlayer}" oninput="if(value>${game.productMaxPlayer})value=${game.productMaxPlayer}" oninput="if(value<${game.productMinPlayer})value=${game.productMinPlayer}">
			</div>
			</c:if>
			<div class="players">
				<c:forEach begin="0" end="${playerList.size() -1}" step="1" var="i">
					<div class="player hasSeat ${playerList[i].memberName}">
					<div class="container text-center">
						<div class="row">
							<div class="col-4">
								<img style="width: 100px;"
									src="${root}/member/findMemberImg/${playerList[i].memberId}" alt="">
								<span>${playerList[i].memberName}</span><br />
								<c:if test="${ishost && i == 0}">
									<span>房主</span>
								</c:if>
								<c:if test="${ishost && i != 0}">
									<span onclick="kickPlayerOut('${playerList[i].memberEmail}','${playerList[i].memberName}')" style="cursor: pointer;">踢出玩家</span>
								</c:if>
							</div>
							<div class="col-8">
								<div class="degreeLine">
									<div class="degree"></div>
								</div>
								<br/> <span class="degreePoint" style="text-align: center">遊戲熟練度:${degreeScoreList[i]}</span>
							</div>
						</div>
					</div>
				</div>
				</c:forEach>
				<c:if test="${0 == finalNumOfPlayer}">
						<div class="player freeSeat">自由位</div>
				</c:if>
				<c:if test="${finalNumOfPlayer != 0 && finalNumOfPlayer-playerList.size()-1 >= 0}">
					<c:forEach begin="0" end="${finalNumOfPlayer - playerList.size() -1}" step="1" var="i">
						<div class="player freeSeat">自由位</div>
					</c:forEach>
				</c:if>
				

			</div>
		</div>
		</div>


	</div>
<!-- 	如果是房主才有的js -->
	<c:if test="${ishost}">
		<script type="text/javascript">
		//直接執行
		//$("#startButton").hide();
		
		//更改遊戲人數時
		if($("#numOfPlayers").length >0){
			$("#numOfPlayers")[0].addEventListener('change', function (event) {
			  $(".player:eq(0)").siblings().remove();
			  for(let i =2;i<=$("#numOfPlayers").val();i++){
				  $(".players").append(`
				  		<div class="player freeSeat">自由位</div>
						  `);
			  }
			  $("#createRoomA")[0].href = "${root}/game/finalNumOfPlayer/${game.productName}/${member.memberEmail}/"+$("#numOfPlayers").val();
			});
		}
		
		//開放其他玩家加入時
		function openRoom(){
			axios.get("${root}/game/openRoom/${tableCode}").then(function(response){
				$(".openRoom").remove();
			}).catch(function(error){
				console.log("開放房間時錯誤",error);
			}).finally(function(){
				
			});
		}
		
		//邀請好友時顯示在線好友的框框
		var isInviteFriendToGameshow = false;
		function inviteFriendButton(){
			if(isInviteFriendToGameshow == false){
				isInviteFriendToGameshow = true;
				let json = {
						"action":"getOnlineFriends",
						"myself":"${member.memberEmail}"
				}
				ws.send(JSON.stringify(json));
			}else{
				$(".inviteFriendToGame").hide();
				isInviteFriendToGameshow = false;
			}
		}
		
		//按下邀請特定玩家按鈕時，送訊息給他
		function inviteFriendToTheGame(idType){
			$(".inviteFriendToGame").hide();
			let theOther = changeToOriginEmail(idType);
			let responseJson = {
					"sender":"${member.memberEmail}",
					"receiver":theOther,
					"messageType":1,
					"messageText":'<h3>邀請您一起玩${game.productName}</h3><a href="${root}/game/joinGame/${tableCode}/'+theOther+'"><button>加入遊戲<button></a>',
					"messageSticker": null
			}
			axios.post("http://localhost:8080/meeple-masters/friendMessage/insertFriendMessageFromSystem",responseJson).then(function(response){
				console.log("按下傳送按鈕時",response);
				//重新製作右邊聊天室內容
				showChatContent(changeToOriginEmail(idType));
				//變成最上面的tag
				let userName = $(".chatActive>.mytext>.name").text();
				$(".chatActive").remove();			
				makeChatTag(changeToOriginEmail(idType),userName,0);
				//傳訊息給後端告訴後端我有傳訊息給朋友
				let info = {
					"action":"sendMessage",
					"receiver":theOther
				}
				ws.send(JSON.stringify(info));
			}).catch(function(error){
				console.log("按下傳送按鈕時",error);
			}).finally(function(){
				
			});
		}
		
		
		//當玩家數達上限時，可以按下開始按鈕
		function startButton(){
			if($(".hasSeat").length == ${finalNumOfPlayer}){
				//連結到遊玩頁面
				window.location.href = "${root}/game/startGame/${game.productName}/${tableCode}/${member.memberEmail}";
			}else{
				alert("尚未達遊玩人數");			
			}
		}
		
		//踢出玩家
		function kickPlayerOut(playerEmail,playerName){
			axios.get("${root}/game/kickPlayerOut/${tableCode}/"+playerEmail).then(function(response){
// 				ws已通知已重新整理頁面
// 				$(".hasSeat."+playerName).remove();
// 				$(".players").append(`<div class="player freeSeat">自由位</div>`);
			}).catch(function(error){
				console.log("踢出玩家出錯啦",error);
			}).finally(function(){
				
			});
		}
		
		
		</script>
	</c:if>
	
	
	<script>
		//開場執行的
		for(i=0;i<$(".degree").length;i++){
			countDegreePercent(i+1,$(".degreePoint:eq("+i+")").text().replace("遊戲熟練度:",""));
		}
		$(".inviteFriendToGame").hide();
		//宣告在ws的js上
		isRoomPage = true;
		if(${hasCreatedRoom == "true"}){
			alert("已經在房間內囉!請先退出再創建新房間!");
		}
		if(${hasJoinedRoom == "true"}){
			alert("已經在房間內囉!請先退出再加入新遊戲!");
		}
		
		//製作熟練度進度條
		function countDegreePercent(NumOfPlayer,degree){
			let i = NumOfPlayer - 1;
			let percent = degree/1000 * 100;
			$(".degree")[i].style.width = percent+"%";
		}
		
	</script>
	<jsp:include page="/WEB-INF/jsp/include/footer.jsp"></jsp:include>
</body>
</html>