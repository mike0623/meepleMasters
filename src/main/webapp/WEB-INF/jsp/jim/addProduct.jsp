<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>${webName}</title>
<jsp:include page="/WEB-INF/jsp/include/common_link.jsp" />
<style>
.container {
	top: 75px;
	position: relative;
	height: 600px;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/include/header.jsp" />
	<div class="container">
		<h1>新增商品</h1>
		<form:form action="/mall/addProduct" method="post" modelAttribute="p"
			enctype="multipart/form-data">
			商品名稱:<input>
			<br>
			商品價格:<input>
			<br>
			上架日期:<input>
			<br>
            商品描述:<input>
			<br>
            遊玩時間:<input>
			<br>
            建議人數:<input>
			<br>
             上手難度:<input>
		</form:form>
	</div>
	<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
</body>
</html>
