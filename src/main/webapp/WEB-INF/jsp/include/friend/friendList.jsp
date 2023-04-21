<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!-- 以上自行取用 -->
<!--通常給別人include，自己不需要這個，不然會有兩次  <趴小老鼠include file="../common_link.jsp"趴>  -->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="${root}/css/friend/friendList.css" rel="stylesheet">
<style type="text/css">
	.friendList{
		position: fixed;
        right: 0;
        top: 0;
        width: 500px;
        border: 3px solid black;
	}
	.dialogBox{
		position: fixed;
        left: 0;
        bottom: 0;
        width: 771px;
        height: 600px;
        border: 3px solid black;
        
	}
	.controllerDialogBox{
		position: fixed;
	}
	.closedChatRoom{
		left: 0;
		bottom: 0;
		width: 200px;
		height: 35px;
		border: 3px solid black;
	}
	.openChatRoom{
		left: 0;
		bottom: 600;
		width: 771px;
		height: 35px;
		border: 3px solid black;
	}
</style>
</head>
<body>
	<button onclick="showFriendList()">朋友列表</button>
	<div id="friendList" class="friendList">
		<div class="onlineFriend">
			
		</div>
		<div class="offlineFriend">
			
		</div>
	</div>
	
	
	
	
	
	
	
	
	
	
	<div class="dialogBox">
		<div class="chat_container">
    <div id="myChatList" class="chat_list">
      <div class="chatListTag">
        <div class="head"><img src="${root}/img/michael/chatRoom/friends/Alpha_Team.png" alt=""></div>
        <div class="mytext">
          <div class="name">Alpha_Team</div>
          <div class="dec">Alpha 測試團隊</div>
        </div>
        <div class="msg_num">3</div>
      </div>
    </div>
    <div class="chat_box">
      <div id="chatRoom" class="chat_message">
        <div class="message_row you-message">
          <div class="message-content">
            <div class="message-text">Ok then</div>
            <div class="message-time">09/23 10:16</div>
          </div>
        </div>
        <div class="message_row other-message">
          <div class="message-content">
            <img class="head" src="${root}/img/michael/chatRoom/friends/David.png" alt="">
            <div class="message-text">推薦給你一張漂亮的底圖...</div>
            <div class="message-time">09/23 10:16</div>
          </div>
        </div>
        <div class="message_row other-message">
          <div class="message-content">
            <img class="head" src="${root}/img/michael/chatRoom/friends/David.png" alt="">
            <!-- <div class="message-text"></div> -->
            <img class="ejIcon" src="${root}/img/michael/chatRoom/emoji/like.png" alt="">
            <div class="message-time">09/23 10:16</div>
          </div>
        </div>
      </div>
      <div class="chat_input">
        <div class="myIcon2 iconTag">
          <i class="fa fa-smile-o ipt_icon"></i>
          <div class="icon_block">
            <div class="emoji_icon">
              <img src="${root}/img/michael/chatRoom/emoji/cry.png" alt="">
            </div>
            <div class="emoji_icon">
              <img src="${root}/img/michael/chatRoom/emoji/cry2.png" alt="">
            </div>
            <div class="emoji_icon">
              <img src="${root}/img/michael/chatRoom/emoji/I_dont_know.jpg" alt="">
            </div>
            <div class="emoji_icon">
              <img src="${root}/img/michael/chatRoom/emoji/like.png" alt="">
            </div>
            <div class="emoji_icon">
              <img src="${root}/img/michael/chatRoom/emoji/like2.png" alt="">
            </div>
            <div class="emoji_icon">
              <img src="${root}/img/michael/chatRoom/emoji/omg.png" alt="">
            </div>
            <div class="emoji_icon">
              <img src="${root}/img/michael/chatRoom/emoji/robot-face.png" alt="">
            </div>
            <div class="emoji_icon">
              <img src="${root}/img/michael/chatRoom/emoji/smile.png" alt="">
            </div>
            <div class="emoji_icon">
              <img src="${root}/img/michael/chatRoom/emoji/strange.png" alt="">
            </div>
            <div class="emoji_icon">
              <img src="${root}/img/michael/chatRoom/emoji/what.jpg" alt="">
            </div>
            <div class="emoji_icon">
              <img src="${root}/img/michael/chatRoom/emoji/wow.png" alt="">
            </div>
          </div>
        </div>
        <input class="sendMsg" type="text" placeholder="請輸入訊息">
        <div class="myIcon2">
          <i class="fa fa-share send_icon"></i>
        </div>
      </div>
    </div>
  </div>
 	</div>
	<div class="controllerDialogBox closedChatRoom" onclick="openCloseChatRoom()">
		<span>聊天室</span>
	</div>
	
	
	<script>
		var ws = new WebSocket("ws://localhost:8080${root}/michael/websocket/${userEmail}");
		
		ws.onopen = function(){
			
			console.log("friend的${userEmail}");
		}
		
		ws.onmessage = function(message){
			console.log("有收到訊息:",message);
			var json = JSON.parse(message.data)
			var action = json.action;
//			if(新增刪除好友){
//				refleshFriendContent();
//		}
			if("onOfflineFriend"==action){
				refleshFriendContent();
			}
			if("someoneLogin"==action){
				let email = json.loginer;
				email = email.replace('@','').replace('.','');
				let name = $("#"+email).text();
				$("#"+email).remove();
				$(".onlineFriend").append("<p id='"+ email +"'>"+name+"</p>");
				
			}
			if("someoneLogout"==action){
				let email = json.logouter;
				email = email.replace('@','').replace('.','');
				let name = $("#"+email).text();
				$("#"+email).remove();
				$(".offlineFriend").append("<p id='"+ email +"'>"+name+"</p>");
			}
			
			
			
			
			
			
			function refleshFriendContent(){
				var onlineKeyArray = Object.keys(json.onlineFriend);
				var offlineKeyArray = Object.keys(json.offlineFriend);
				var onlineValueArray = Object.values(json.onlineFriend); //取key的方法是keys()
				var offlineValueArray = Object.values(json.offlineFriend);
				$(".onlineFriend").empty();
				$(".offlineFriend").empty();
				$(".onlineFriend").append('<h3>線上</h3>');
				for(let i = 0;i<onlineValueArray.length;i++){
					$(".onlineFriend").append('<div class="container text-center"><div class="row"><div class="col-3">圖像</div><div class="col-6"><span id="'+ onlineKeyArray[i].replace("@","").replace(".","") +'">'+onlineValueArray[i]+'</span></div><div class="col-3"><button id="'+ onlineValueArray[i].replace("@","").replace(".","") +'button" name="'+onlineKeyArray[i].replace("@","").replace(".","")+'" onclick="sendMessageToOnlineFriend(this.id,this.name)">傳送訊息</button></div></div></div>');
					
					//$(".onlineFriend").append("<p id='"+ onlineKeyArray[i].replace('@','').replace('.','') +"'>"+onlineValueArray[i]+"</p>");
				}
				$(".offlineFriend").append('<h3>離線</h3>');
				for(let i = 0;i<offlineValueArray.length;i++){
					$(".offlineFriend").append('<div class="container text-center"><div class="row"><div class="col-3">圖像</div><div class="col-6"><span id="'+ offlineKeyArray[i].replace("@","").replace(".","") +'">'+offlineValueArray[i]+'</span></div><div class="col-3"><button id="'+ offlineValueArray[i].replace("@","").replace(".","") +'button" name="'+offlineKeyArray[i].replace("@","").replace(".","")+'" onclick="sendMessageToOfflineFriend(this.id,this.name)">傳送訊息</button></div></div></div>');
					
					//$(".offlineFriend").append("<p id='"+ offlineKeyArray[i].replace('@','').replace('.','') +"'>"+offlineValueArray[i]+"</p>");
				}
			}
		}
		
		//傳訊息給在線朋友
		var receiver;
		function sendMessageToOnlineFriend(buttonId,userEmailStr){
			let userName = buttonId.replace("button","");
			receiver = userEmailStr.replace("com",".com").replace("gmail","@gmail");
			console.log(buttonId);
			console.log(receiver);	
			console.log(userName);
			$(".chatMemberList").append('<div class="chatMember"><span>'+userName+'</span><img src="${root}/img/michael/x.png" width="25px" height="25px"/></div>');
			
		}
		
		
		
		//傳訊息給離線朋友
		function sendMessageToOnlineFriend(buttonId,userEmailStr){
			
		}
		
		
	
		//控制聊天室開關
		$(".dialogBox").hide();
		function openCloseChatRoom(){
			$(".controllerDialogBox").toggleClass("closedChatRoom").toggleClass("openChatRoom");
			if($(".controllerDialogBox").hasClass("closedChatRoom")){
				$(".dialogBox").hide();
			}
			if($(".controllerDialogBox").hasClass("openChatRoom")){
				$(".dialogBox").show();
			}
		}
		
		
	
		var isShow = false;
		$("#friendList").hide();
		function showFriendList(){
			if(isShow==false){
				$("#friendList").show();
				isShow = true;
			}else{
				$("#friendList").hide();
				isShow = false;
			}
		}

	</script>
	 
</body>
</html>