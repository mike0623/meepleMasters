<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/common_link.jsp" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../include/friend/friendList.jsp"></jsp:include>
<jsp:include page="../include/friend/friendButtonControll.jsp"></jsp:include>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<button onclick="showFriendList()">好友列表</button>
<input type="text" id="selectedFriend"><button onclick="searchFriend()">搜尋好友</button>
<form action="${root}/game/createGameTable/Bridge/${member.memberEmail}">
	<button>創建橋牌房間</button>
</form>
<form action="${root}/game/playGameLobby">
	<button>進入遊戲大廳</button>
</form>
<div class="friendButtonDiv">
	
</div>



<script>
	isSelectedPage = true;
	var selectedFriendEmail = "${findMember.memberEmail}";
	var selectedFriendName = "${findMember.memberName}";
	var idTypeEmail = changeToIdType(selectedFriendEmail);
	var buttonTypeName = selectedFriendName+"button";
	function searchFriend(){
// 		//----------------------------------------------------------假資料
// 		if("A" == $("#selectedFriend").val()){
// 			selectedFriendEmail = "AAA@gmail.com";
// 			selectedFriendName = "Andy";
// 			idTypeEmail = changeToIdType(selectedFriendEmail);
// 			buttonTypeName = selectedFriendName+"button";
// 		}
// 		if("B" == $("#selectedFriend").val()){
// 			selectedFriendEmail = "BBB@gmail.com";
// 			selectedFriendName = "Betty";
// 			idTypeEmail = changeToIdType(selectedFriendEmail);
// 			buttonTypeName = selectedFriendName+"button";
// 		}
// 		if("C" == $("#selectedFriend").val()){
// 			selectedFriendEmail = "CCC@gmail.com";
// 			selectedFriendName = "Cart";
// 			idTypeEmail = changeToIdType(selectedFriendEmail);
// 			buttonTypeName = selectedFriendName+"button";
// 		}
// 		if("D" == $("#selectedFriend").val()){
// 			selectedFriendEmail = "DDD@gmail.com";
// 			selectedFriendName = "David";
// 			idTypeEmail = changeToIdType(selectedFriendEmail);
// 			buttonTypeName = selectedFriendName+"button";
// 		}
// 		if("E" == $("#selectedFriend").val()){
// 			selectedFriendEmail = "EEE@gmail.com";
// 			selectedFriendName = "Emily";
// 			idTypeEmail = changeToIdType(selectedFriendEmail);
// 			buttonTypeName = selectedFriendName+"button";
// 		}
// 		if("F" == $("#selectedFriend").val()){
// 			selectedFriendEmail = "FFF@gmail.com";
// 			selectedFriendName = "Frimon";
// 			idTypeEmail = changeToIdType(selectedFriendEmail);
// 			buttonTypeName = selectedFriendName+"button";
// 		}
		//-------------------------------------------------------------
		//先清空
		$(".friendButtonDiv").empty();
		//判斷是不是好友
		axios.get("http://localhost:8080/meeple-masters/friend/isFriend/${member.memberEmail}/"+selectedFriendEmail).then(function(response){
			if("isFriend"==response.data){
				console.log("是好友了");
				$(".friendButtonDiv").append(`
						<button onclick="pressChatButton('`+buttonTypeName+`','`+idTypeEmail+`')">傳送訊息</button>
						<button onclick="deleteFriend('${member.memberEmail}','`+selectedFriendEmail+`')">刪除好友</button>
						`)
				return;
			}
			//若不是好友，判斷有沒有誰發出過邀請
			axios.get("http://localhost:8080/meeple-masters/friendInvite/isExist/${member.memberEmail}/"+selectedFriendEmail).then(function(response){
				if("${member.memberEmail}"==response.data.inviter){
					console.log("我發出邀請了");
					//我發出邀請了，對方沒接受
					$(".friendButtonDiv").empty();
					$(".friendButtonDiv").append(`
							<button onclick="deleteFriendInvite('${member.memberEmail}','`+selectedFriendEmail+`')">取消邀請</button>
							`);
					return;
				}
				if(selectedFriendEmail==response.data.inviter){
					console.log("對方發出邀請了");
					//對方發出邀請了，我沒接受
					$(".friendButtonDiv").empty();
					$(".friendButtonDiv").append(`
							<button class="btn btn-info" onclick="acceptFriendInvite('${member.memberEmail}','`+selectedFriendEmail+`')">接受邀請</button>
							<button class="btn btn-info" onclick="rejectFriendInvite('${member.memberEmail}','`+selectedFriendEmail+`')">拒絕邀請</button>
							`);
					return;
				}
				console.log("不是朋友，且誰都沒發出邀請");
				//不是好友也不存在邀請
				$(".friendButtonDiv").empty();
				$(".friendButtonDiv").append(`
						<button onclick="sendFriendInvite('${member.memberEmail}','`+selectedFriendEmail+`')">加好友</button>
						`);
				
			}).catch(function(error){
				console.log("判斷誰發過請出錯啦!",error);
			}).finally(function(){
				
			});
			
			
			
		}).catch(function(error){
			console.log("判斷是不是好友出錯啦!",error);
		}).finally(function(){
			
		});
	}
	
</script>
</body>
</html>