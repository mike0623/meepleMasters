<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>測試跳轉用</h1>





<script>
	var ws = new WebSocket("ws://localhost:8080${root}/michael/websocket/${userEmail}");
 	console.log("${userEmail}");
</script>
</body>
</html>