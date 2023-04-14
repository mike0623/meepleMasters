<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/common_link.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<button onclick="Send()">送出</button>


<script>
let xxx = "ws://localhost:8080${root}/michael/websocket/Mike";
console.log("xxx=", xxx);


 	var ws;
// 	window.onload=function(){
// 	}
	
 	ws = new WebSocket(xxx);
 	function Send(){
		ws.send("123");
 	}
</script>
</body>
</html>