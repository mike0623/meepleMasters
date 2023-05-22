<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="./common_link.jsp"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>${webName}</title>
<link rel="stylesheet" type="text/css" href="${root}/css/index.css">
<style>
</style>
</head>

<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="bodyContainer">


		<div class="ad">
			<div class="adDiv1">
				<img src="${root}/img/example/poker1.jpg" class="adsImg" />
				<div class="adBlock1">
					<div class="adsContent">
						挑戰德州撲克！<br />世界上最流行的公牌撲克，目標贏取彩池中的所有彩金！
					</div>
					<c:if test="${member == null}">
					<form action="${root}/member/login">
						<button class="button-19" role="button">開始遊玩</button>
					</form>
					</c:if>
					<c:if test="${member != null}">
					<form action="${root}/game/playGameLobby">
						<button class="button-19" role="button">開始遊玩</button>
					</form>
					</c:if>
				</div>
			</div>

			<div class="adDiv2"></div>
		</div>

		<div class="container gameCardDiv">
			<div class="gameListTitle">
				遊戲列表
				<div class="link-top"></div>
			</div>
			<div class="row px-4 pt-4 justify-content-center">

				<c:forEach items="${productList}" var="product">
					<div class="col-3 d-flex align-items-stretch">
						<div class="card">
							<div class="pic">
								<img class="productImg" src="${root}/mall/getPhoto?pId=${product.productId}">
							</div>
							<div class="card-header">遊戲人數：${product.productMinPlayer}~${product.productMaxPlayer}</div>
							<div class="card-body">
								<h3 class="title">${product.productName}</h3>
							</div>
							<div class="card-footer">
								<p class="text">${product.productDescription}</p>
							</div>
						</div>
					</div>
				</c:forEach>

			</div>
		</div>

		<div class="storeBlock">
			<div class=""></div>
			<div class="map">
				<iframe
					src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3615.0109929955192!2d121.54330379999999!3d25.033700999999997!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3442abd37971c7cb%3A0x40ba641f27b6d4e3!2zMTA25Y-w5YyX5biC5aSn5a6J5Y2A5b6p6IiI5Y2X6Lev5LiA5q61Mzkw6Jmf!5e0!3m2!1szh-TW!2stw!4v1681057217647!5m2!1szh-TW!2stw"
					width="600" height="450" style="border: 0;" allowfullscreen=""
					loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
			</div>

		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>

</html>