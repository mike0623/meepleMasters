<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/common_link.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<button onclick="Send()">送出訊息</button>
<a href="${root}/michael/testPage">跳轉頁面</a>


<script>
  	var ws = new WebSocket("ws://localhost:8080${root}/michael/websocket/${userEmail}");
	ws.onopen = function(){
		console.log("${userEmail}");
	}

// 	window.onload=function(){
// 	}
	 ws.onmessage = function(message){
		console.log(message.data);
		$("body").append("<p>"+message.data+"</p>");
	}
	
 	function Send(){
 		let message = {
 				"action":"sendMessageByUserEmail",
 				"receiver":"Mike",
 				"text":"哈哈哈"
 				}
 		
 		
 		ws.send(JSON.stringify(message));
 	}
</script>
</body>
</html>