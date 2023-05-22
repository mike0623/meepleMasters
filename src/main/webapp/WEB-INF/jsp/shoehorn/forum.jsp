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
<style>
.articleBody {
	width: 60%;
	margin: auto;
	text-align: center;
}

.allArticleTable {
	border-collapse: collapse;
	margin: auto;
}

.allArticleTable tbody tr {
	cursor: pointer;
}

.allArticleTable td, .allArticleTable th {
	font-size: 30px;
	text-align: center;
	border: 1px solid black;
}

.myArticle {
	background-color: #F0EBE2;
}

.otherArticle {
	background-color: #CBC0AA;
}

label {
	font-size: 25px;
}
</style>
</head>
<!-- 最上面那排bar -->
<jsp:include page="../include/header.jsp" />
<body>
	<div class="bodyContainer">
		<div class="articleBody">
			<div>
				<!-- 搜尋條件 -->

				<form action="${root}/forum" method="post">

					<label for="poster">發文者:</label> 
					<input type="text" id="poster" name="poster">
					
					<label for="product">遊戲:</label> 
					<select id="product" name="product">
						<option value=""></option>
						<c:forEach begin="0" end="${allProduct.size()-1}" step="1"	var="i">
							<option value="${allProduct[i].productId}">${allProduct[i].productName}</option>
						</c:forEach>
					</select>
					
					<label for="title">標題:</label> 
					<input type="text" id="title" name="title">

					<button type="submit">搜尋</button>



				</form>

			</div>



			<hr />
			<!-- 文章列表 -->
			<table class="allArticleTable">
				<thead>
					<tr>
						<th>遊戲</th>
						<th>文章標題</th>
						<th>發文者</th>
						<th>好評</th>
						<th>差評</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${articleList.size()>= 1}">
						<c:forEach begin="0" end="${articleList.size()-1}" step="1"
							var="i">
							<tr
								class="<c:if test="${articleList[i].fkMemberId == member.memberId}">myArticle</c:if><c:if test="${articleList[i].fkMemberId != member.memberId}">otherArticle</c:if>"
								onclick="location.href='${root}/article?articleId=${articleList[i].articleId}'">
								<td>${productNameList[i]}</td>
								<td>${articleList[i].articleTitle}</td>
								<td>${memberNameList[i]}</td>
								<td>${goodReviewCount[i]}</td>
								<td>${badReviewCount[i]}</td>
							</tr>
						</c:forEach>
					</c:if>
					<tr>
						<td><a href="${root}/newArticle"><button>新增文章</button></a></td>
					</tr>
				</tbody>
			</table>

		</div>

	</div>
</body>
<!-- 最下面標題 -->
<jsp:include page="../include/footer.jsp" />
</html>