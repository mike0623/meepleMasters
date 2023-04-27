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
<link rel="stylesheet" type="text/css" href="${root}/css/card/cardList.css">
</head>

<body>
	<div class="cardListDiv" id="cardListDiv">
		<div class="title">卡片列表</div>
		<div class="cardStarTableDiv">
			<table class="cardStarTable">
				<tr>
					<td colspan="2">總機率</td>
				</tr>
				<tr>
					<td><img src="${root}/img/lyh/star/star5.png"
						class="cardStarlabel"></td>
					<td>2%</td>
				</tr>
				<tr>
					<td><img src="${root}/img/lyh/star/star4.png"
						class="cardStarlabel"></td>
					<td>6%</td>
				</tr>
				<tr>
					<td><img src="${root}/img/lyh/star/star3.png"
						class="cardStarlabel"></td>
					<td>10%</td>
				</tr>
				<tr>
					<td><img src="${root}/img/lyh/star/star2.png"
						class="cardStarlabel"></td>
					<td>22%</td>
				</tr>
				<tr>
					<td><img src="${root}/img/lyh/star/star1.png"
						class="cardStarlabel"></td>
					<td>60%</td>
				</tr>
			</table>
		</div>

		<div id="dataHome"></div>
		<button onclick="showJSPContent()">卡片列表</button>
	</div>

	<script src="${root}/json/starcard.js"></script>
	<script type="text/javascript">


	let outputString = "";
	
	
	for(let i = 4; i>=0 ; i--){
		outputString += `<div class='row container cardContainer justify-content-center' id='123'>`;
		outputString += "<div class='starDiv'>";
		outputString += `<img src='${root}/img/lyh/star/star\${i+1}.png' class='star'>`;
		outputString += "</div>";
		
		for(let item of cardList[i].imgs){
			outputString += "<div class='col-3 d-flex'>";
			outputString += "<div class='card'>";
			outputString += `<figure><img src='${root}/img/lyh/nostarCard/\${item.src}' class='hanafuda'>`;
			outputString += `<figcaption>\${item.name}</figcaption>`;
			outputString += "</figure></div></div>";
		}
		outputString += "</div>";
	}

	
	// $("#dataHome").html(outputString)


// 		$.each(cardList, function (index, value) {
// 			if ((value.star) == 1) {
// 				console.log(`\${value.star}`)
// 				$.each(value.imgs, function (index2, value2) { 
// 					console.log(`\${value.imgs}`)
// 					$("#card1").append(`<div class="col-3 d-flex"><div class="card"><figure><img src="\${value2.src}" class="hanafuda" /><figcaption>\${value2.name}</figcaption>	</figure></div></div>`);
// 				})
// 			} else if ((value.star) == 2) {
// 				$.each(value.imgs, function (index2, value2) {
// 					$("#card2").append(`<div class="col-3 d-flex"><div class="card"><figure><img src="${value2.src}" class="hanafuda" /><figcaption>${value2.name}</figcaption>	</figure></div></div>`);
// 				})
// 			} else if ((value.star) == 3) {
// 				$.each(value.imgs, function (index2, value2) {
// 					$("#card3").append(`<div class="col-3 d-flex"><div class="card"><figure><img src="${value2.src}" class="hanafuda" /><figcaption>${value2.name}</figcaption>	</figure></div></div>`);
// 				})
// 			} else if ((value.star) == 4) {
// 				$.each(value.imgs, function (index2, value2) {
// 					$("#card4").append(`<div class="col-3 d-flex"><div class="card"><figure><img src="${value2.src}" class="hanafuda" /><figcaption>${value2.name}</figcaption>	</figure></div></div>`);
// 				})
// 			} else {
// 				$.each(value.imgs, function (index2, value2) {
// 					$("#card5").append(`<div class="col-3 d-flex"><div class="card"><figure><img src="${value2.src}" class="hanafuda" /><figcaption>${value2.name}</figcaption>	</figure></div></div>`);
// 				})
// 			}
// 		})
	</script>

</body>

</html>