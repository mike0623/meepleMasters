<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="../include/common_link.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${webName}</title>
<link rel="stylesheet" type="text/css" href="${root}/css/index.css" />
</head>
<!-- 最上面那排bar -->
<jsp:include page="../include/header.jsp" />
<body>
	<div class="bodyContainer">

		<!-- 搜尋條件 -->
		<div>
		
			<form action="${root}/forum" method="post">

				<label for="poster">Poster:</label> 
				<input type="text" id="poster" name="poster"> <br>
				
				<label for="product">Product:</label>
				<input type="text" id="product" name="product"> <br>
				
				<label for="title">Title:</label>
				<input type="text" id="title" name="title"> <br>

				<button type="submit">搜尋</button>
				
			</form>

		</div>
				<a href="${root}/newArticle"><button>新增文章</button></a>


		<hr/>
		<!-- 文章列表 -->
		<table>
			<tr>
				<th>遊戲</th>
				<th>文章標題</th>
				<th>發文者</th>
				<th>好評</th>
				<th>差評</th>
			</tr>
			<c:if test="${articleList.size()>= 1}">
			<c:forEach begin="0" end="${articleList.size()-1}" step="1" var="i">
				<tr onclick="location.href='${root}/article?articleId=${articleList[i].articleId}'">
					<td>${articleList[i].fkProductId}</td>
					<td>${articleList[i].articleTitle}</td>
					<td>${articleList[i].fkMemberId}</td>
					<td>${goodReviewCount[i]}</td>
					<td>${badReviewCount[i]}</td>
				</tr>
			</c:forEach>
			</c:if>
		</table>

		<!-- 
		<form action="${root}/article/dynamicSelect" method="post">
		<input name="body" type="text">
		<button>送出</button>
		</form>
		 -->

	</div>
</body>
<!-- 最下面標題 -->
<jsp:include page="../include/footer.jsp" />
</html>