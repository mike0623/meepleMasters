<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<c:if test="${member != null }">
	<jsp:include page="friend/friendList.jsp"></jsp:include>
</c:if>
<meta charset="UTF-8">
<title>${webName}</title>
<link rel="stylesheet" type="text/css" href="${root}/css/header.css">
<style>
	
</style>
</head>
<body>
	<c:if test="${member != null }">
		<div class="friendButton">
			<span class="friendListSpan" onclick="showFriendList()">好友列表</span>
		</div>
	</c:if>
    <div class="nav fixed-top">
        <header>
            <div class="logo"><a href="${root}"><img src="${root}/img/logo.png" alt="" class="logoImg" /></a></div>
            <nav role="navigation">
                <ul>
                    <li>
                        <a href="${root}/mall/product">遊戲列表</a>
                    </li>
                    <c:if test="${member == null }">
                        <li>
                            <a href="${root}/member/login" aria-disabled="true">卡片收藏</a>
                        </li>
                    </c:if>
                    <c:if test="${member != null }">
                        <li>
                            <a href="javascript:void(0);" onclick="js_method()">卡片收藏</a>
                            <div>
                                <ul>
    
                                    <li><a href="${root}/card/mycard/${member.memberId}">我的卡片</a></li>
                                    <li><a href="${root}/card/releasedList">卡片市集</a></li>   
                                </ul>
                            </div>
                        </li>
                    </c:if>
                    <li>
                        <a href="${root}/forum">遊戲討論</a>
                    </li>
                    <li>
                        <a href="javascript:void(0);" onclick="js_method()">場地預約</a>
                        <div>
                            <c:if test="${member == null }">
                                <ul>
                                    <li><a href="${root}/bookingForm">開始預約</a></li>
                                    <li><a href="${root}/member/login">訂位紀錄</a></li>
                                </ul>
                            </c:if>
                            <c:if test="${member != null }">
                                <ul>
                                    <li><a href="${root}/bookingForm">場地預約</a></li>
                                    <li><a href="${root}/booking/record">訂位紀錄</a></li>
                                </ul>
                            </c:if>
                        </div>
                    </li>
                    <c:if test="${member != null }">
                    <li>
                    	<a href="#">開始遊玩</a>
                    	<div>
                                <ul>
    								
                                    <li><a href="${root}/game/playGameLobby">進入遊戲大廳</a></li>
                                    <c:if test="${tableCode != null}">
                                    	<li><a href="${root}/game/joinGame/${tableCode}/${member.memberEmail}">回到遊玩中的遊戲</a></li>
                                    </c:if>
    
                                </ul>
                            </div>
                    </li>
                    </c:if>
	                <c:if test="${member == null }">
	                    <li>
	                    	<a href="${root}/member/login">開始遊玩</a>
                    	</li>
                    </c:if>
                    <c:if test="${member == null }">
                    	<li style="background: none;"><a href="${root}/member/login" style="font-size: 16px;">註冊 / 登入</a></li>
                    </c:if>
                    <c:if test="${member != null }">
                    	<li class="" style="background: none;">
                        <a class="dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                          Hi,${member.memberName}
                          <img src="${root}/member/findMemberImg/${member.memberId}" alt="" width="32" height="32" class="rounded-circle">
                        </a>
                        <ul class="dropdown-menu">
                        <c:if test='${member.memberLevel.equals("管理員") }'>
                          <li><a class="dropdown-item" href="${root}/admin">後台管理</a></li>
                        </c:if>
                          <li><a class="dropdown-item" href="${root}/member/game">我的遊戲</a></li>
                          <li><a class="dropdown-item" href="${root}/member/myProfile/${member.memberId}">我的檔案</a></li>
                          <li><a class="dropdown-item" href="${root}/order/orderList/?memberId=${member.memberId}">我的訂單</a></li>
                          <li><a class="dropdown-item" href="${root}/member/profile">修改資料</a></li>
                          <li><hr class="dropdown-divider"></li>
                          <li><a class="dropdown-item" href="${root}/member/logout">登出</a></li>
                        </ul>
                      </li>
                      <div><a href="${root}/shoppingCart"><i class="fa-solid fa-cart-shopping fa-2xl shoppingCartButton"></i></a></div>
                    </c:if>
                </ul>
            </nav>
        </header>
    </div>
</body>
</html>