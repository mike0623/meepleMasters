<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ include file="../include/common_link.jsp" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>${webName}</title>
	<link rel="stylesheet" type="text/css" href="${root}/css/card/cardList.css">
	<link rel="stylesheet" type="text/css" href="${root}/css/card/mycard.css">
</head>

<body>
	<jsp:include page="../include/header.jsp"></jsp:include>
	<div class="container">
		<div class="mycardTitle">我的卡片</div>


	</div>

	<div><button onclick="showCardList()" class="cardListButton">卡片列表</button></div>

	<script src="${root}/json/starcard.js"></script>
	<script type="text/javascript">


		let outputString = "<div class='cardStarTableDiv'><table class='cardStarTable'><tr><td colspan='2'>總機率</td></tr><tr><td><img src='${root}/img/lyh/star/star5.png'class='cardStarlabel'></td><td>2%</td></tr><tr><td><img src='${root}/img/lyh/star/star4.png'class='cardStarlabel'></td><td>6%</td></tr><tr><td><img src='${root}/img/lyh/star/star3.png'class='cardStarlabel'></td><td>10%</td></tr><tr><td><img src='${root}/img/lyh/star/star2.png'class='cardStarlabel'></td><td>22%</td></tr><tr><td><img src='${root}/img/lyh/star/star1.png'class='cardStarlabel'></td><td>60%</td></tr></table></div>";


		for (let i = 4; i >= 0; i--) {
			outputString += "<div class='row container cardContainer justify-content-center' id='card5'>"
			outputString += "<div class='starDiv'>";
			outputString += `<img src='${root}/img/lyh/star/star\${i+1}.png' class='star'>`;
			outputString += "</div>";

			for (let item of cardList[i].imgs) {
				outputString += "<div class='col-3 d-flex'>";
				outputString += "<div class='card'>";
				outputString += `<figure><img src='${root}/img/lyh/nostarCard/\${item.src}' class='hanafuda'>`;
				outputString += `<figcaption>\${item.name}</figcaption>`;
				outputString += "</figure></div></div>";
			}
			outputString += "</div>";
		}

		function showCardList() {

			Swal.fire({
				title: '卡片列表',
				html: outputString,
				color: '#FFF',
				showCloseButton: true,
				focusConfirm: false,
				background: '#647168',
				confirmButtonColor: '#dfa661'
			})
		}
	</script>
</body>


</html>