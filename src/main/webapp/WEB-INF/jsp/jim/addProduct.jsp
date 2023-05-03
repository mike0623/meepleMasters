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
<link rel="stylesheet" type="text/css" href="${root}/css/index.css" />
<style>
.container {
	position: relative;
	top: 80px;
	height: 600px;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/include/header.jsp" />
	<div class="container">
		<h1>新增商品</h1>
		<form:form action="/meeple-masters/mall/addProduct" method="post"
			modelAttribute="product" enctype="multipart/form-data">
			<label for="productName">商品名稱:</label>
			<input type="text" id="productName" name="productName" />
			<br>
			<label for="productPrice">商品價格:</label>
			<input type="number" id="productPrice" name="productPrice" min="0" />
			<br>
			<label for="productDescription">商品描述:</label>
			<input type="text" id="productDescription" name="productDescription" />
			<br>
			<label for="productPlayTime">遊玩時間:</label>
			<input type="text" id="productPlayTime" name="productPlayTime" />
			<br>
			<label for="productMaxPlayer">建議最多遊玩人數:</label>
			<input type="number" id="productMaxPlayer" name="productMaxPlayer"
				min="2" />
			<br>
			<label for="productMinPlayer">建議最少遊玩人數:</label>
			<input type="number" id="productMinPlayer" name="productMinPlayer"
				min="1" />
			<br>
			<label for="productDifficulty">上手難度:</label>
			<input type="text" id="productDifficulty" name="productDifficulty" />
			<br>
			<label for="productImg">商品圖片:</label>
			<input type="file" name="pImg" id="productImg">
			<br>
			<button>新增</button>
			<button type="reset">清除</button>
			<input type="button" value="測試新增資料" id="newTest" />
			<br>
			<img alt="" src="#" id="imgPreview">
		</form:form>
	</div>
	<script>
		// 一鍵輸入測試資料
		$("#newTest").on("click", function() {
			$("#productName").val("測試用商品");
			$("#productPrice").val("162");
			$("#productDescription").val("說明商品的地方");
			$("#productPlayTime").val("簡短普通耗時");
			$("#productMaxPlayer").val("8");
			$("#productMinPlayer").val("4");
			$("#productDifficulty").val("簡單普通困難");
		});
		// 顯示上傳圖片的預覽圖
		let productImg = $("#productImg");
		let imgPreview = $("#imgPreview");

		productImg.change(function() {
			const file = this.files[0];
			const tempSrc = URL.createObjectURL(file);
			imgPreview.attr("src", tempSrc);
		});
	</script>
	<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
</body>
</html>
