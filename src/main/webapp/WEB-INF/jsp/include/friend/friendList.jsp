<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!-- 以上自行取用 -->
<%@ include file="../common_link.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	.friendList{
		position: fixed;
        right: 0;
        top: 0;
        width: 500px;
        border: 3px solid black;
	}
</style>
</head>
<body>
	<button onclick="showFriend()">朋友列表</button>
	<div id="friendList" class="friendList">
	</div>
	
	
	<script>
		var ws = new WebSocket("ws://localhost:8080${root}/michael/websocket/${userEmail}");
		ws.onopen = function(){
			let message = {
					"action":"login"
					
			}
		}
	
	
	
		var isShow = true;
		function showFriend(){
			if(isShow==false){
				$("#friendList").show();
				isShow = true;
			}else{
				$("#friendList").hide();
				isShow = false;
			}
		}
		//網址要改成memberId
		axios.get("${root}/friend/selectByMemberId/2").then(function(response){
			console.log("response: ",response);
			let friendArray = response.data.friendName;
			for(let i = 0;i<friendArray.length;i++){
				console.log(friendArray[i]);
				$("#friendList").append("<p>"+friendArray[i]+"</p>");
			}
		}).catch(function(error){
			console.log("error: ",error);
		}).finally(function(){
			
		});
	</script>
</body>
</html>