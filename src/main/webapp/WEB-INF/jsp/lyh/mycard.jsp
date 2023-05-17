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
	<style>

	</style>

</head>


<body>
	<jsp:include page="../include/header.jsp"></jsp:include>
	<div class="cardListContainer">
		<div class="mycardContainer">
			<div class="myCoinDiv">
				<h2>我的米寶幣</h2>
				<div class="myCoinTitle">${member.memberCoin} <i class="fa-solid fa-coins"></i></div>
				<div style="font-size: 10px;" class="coinInfo"><i class="fa-regular fa-circle-question"></i> 如何取得米寶幣
				</div>
			</div>
			<div class="cardListButtons">
				<!-- <button class="cardAnimeButton" id="showCardAnime">抽卡動畫</button> -->
				<button id="drawCard" class="button-19"><span>抽卡</span><i></i></button>
			</div>
			<br>
			<hr color="#47322d">
			<h2>我的卡片</h2>
			<button class="btn btn-warning cardAnimeButton" id="showCardList">卡片列表</button>
			<div class="btn-group row justify-content-end">
				<button type="button" class="btn btn-outline-warning dropdown-toggle" data-bs-toggle="dropdown"
					aria-expanded="false">
					卡片排序
				</button>
				<ul class="dropdown-menu">
					<li class="dropdown-item" id="orderDESC">最新獲得</li>
					<li class="dropdown-item" id="order">獲得順序</li>
					<li class="dropdown-item" id="starOrderDESC">星數　
						<i class="fa-solid fa-arrow-down-wide-short"></i>
					</li>
					<li class="dropdown-item" id="starOrderASC">星數　
						<i class="fa-solid fa-arrow-up-short-wide"></i>
					</li>
				</ul>
			</div>
			<div class="sellingNote"><img src="${root}/img/lyh/selling.png" style="width: 60px;">上架中</div>



			<div class="row container cardContainer justify-content-center" id="cardContainer">
				<c:forEach var="card" items="${cardList}">
					<div class="col-3 d-flex">
						<div class="card">
							<figure><img alt="" src="${root}/card/downloadCard/${card.cardId}" class="hanafuda">
								<figcaption>${card.cardName}</figcaption>
							</figure>
						</div>
					</div>
				</c:forEach>
				<c:forEach var="card" items="${sellList}">
					<div class="col-3 d-flex">
						<div class="card">
							<figure><img alt="" src="${root}/card/downloadCard/${card.cardId}" class="hanafuda">
								<img src="${root}/img/lyh/selling.png" class="sellingCard">
								<figcaption>${card.cardName}</figcaption>
							</figure>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	<jsp:include page="../include/footer.jsp"></jsp:include>


	<script src="${root}/json/starcard.js"></script>
	<script src="https://unpkg.com/@lottiefiles/lottie-player@latest/dist/lottie-player.js"></script>
	<script type="text/javascript">

		let getCardListId = [];
		let getSellListId = [];
		let getCardList = [];
		let getSellList = [];

		$(async function () {
			await getMyList().then(() => {
			for (let i = 4; i >= 0; i--) {
				outputString += `<div class='row container cardContainer justify-content-center' id='card'>`;
				outputString += `<div class='starDiv' id='starDiv'>`;
				outputString += `<img src='${root}/img/lyh/star/star\${i+1}.png' class='star' id='star'>`;
				outputString += ` <i class="fa-solid fa-caret-down fa-beat fa-2xl caret" style="color: #F0EBE2;"></i></div>`;


				for (let item of cardList[i].imgs) {
					let matchedCard = cardListArray.find(card => card.cardId === item.id);
					if (matchedCard) {
						// 有符合cardListArray中擁有的卡片ID
						outputString += "<div class='col-3 d-flex d-none' id='cardContent'>";
						outputString += "<div class='card'>";
						outputString += `<figure><img src='${root}/img/lyh/nostarCard/\${item.src}' class='hanafuda' id='\${item.id}'>`;
						outputString += `<figcaption>\${item.name}</figcaption>`;
						outputString += `<img src="${root}/img/lyh/having.png" class="sellingCard"></figure></div></div>`;
					} else {
						outputString += "<div class='col-3 d-flex d-none' id='cardContent'>";
						outputString += "<div class='card'>";
						outputString += `<figure><img src='${root}/img/lyh/nostarCard/\${item.src}' class='hanafuda' id='\${item.id}'>`;
						outputString += `<figcaption>\${item.name}</figcaption>`;
						outputString += `</figure></div></div>`;
					}
				}

				outputString += "</div>";
			}
		});
		})

		let cardListArray = [];
		let sellListArray = [];

		function getMyList() {
			// console.log("執行了getMyList");
			return axios.get("${root}/card/mycard/${member.memberId}/getOrder")
				.then(res => {
					console.log(res)

					for (i = 0; i < res.data.length; i++) {
						if (res.data[i].cardStatus == 1) {
							cardListArray.push(res.data[i])
						} else {
							sellListArray.push(res.data[i])
						}

					}
				})
				.catch(err => {
					console.error(err);
				})
		}

		console.log(cardListArray)
		// console.log(sellListArray)
		$(".coinInfo").click(function () {
			Swal.fire({
				title: '如何取得米寶幣？',
				html: '新註冊會員可獲得500米寶幣，遊玩一場遊戲可獲得100幣，<br/>贏家還可以再得100幣喔，一起來收集卡片吧！',
				customClass: 'Swal-coinInfo',
				confirmButtonColor: '#CA7159',
				confirmButtonText: '我知道了'
			})
		})

		let outputString = "<div class='cardStarTableDiv'><table class='cardStarTable'><tr><td colspan='2'>總機率</td></tr><tr><td><img src='${root}/img/lyh/star/star5.png'class='cardStarlabel'></td><td>2%</td></tr><tr><td><img src='${root}/img/lyh/star/star4.png'class='cardStarlabel'></td><td>6%</td></tr><tr><td><img src='${root}/img/lyh/star/star3.png'class='cardStarlabel'></td><td>10%</td></tr><tr><td><img src='${root}/img/lyh/star/star2.png'class='cardStarlabel'></td><td>22%</td></tr><tr><td><img src='${root}/img/lyh/star/star1.png'class='cardStarlabel'></td><td>60%</td></tr></table></div><div class='sellingNote'><img src='${root}/img/lyh/having.png' style='width: 60px;''> 已擁有卡片</div>";

		$("#showCardList").click(function () {
			Swal.fire({
				title: '卡片列表',
				html: outputString,
				color: '#FFF',
				showCloseButton: true,
				focusConfirm: false,
				background: '#647168',
				confirmButtonColor: '#dfa661'
			});

			let ids = [];
			for (let i = 0; i < cardListArray.length; i++) {
				// console.log("id:" + cardListArray[i].cardId)
				$('.hanafuda').each(function () {
					if (this.id == (cardListArray[i].cardId)) {
						$(this).removeClass("d-none")
					}
					// console.log(this.id)
				})
			}


			$('.starDiv').click(function () {
				if ($(this).nextAll().hasClass('d-none')) {
					$(this).nextAll().removeClass('d-none');
					$(this).children(".caret").css("transform", "rotate(180deg)").removeClass('fa-beat');
				} else {
					$(this).nextAll().addClass('d-none');
					$(this).children(".caret").css("transform", "none").addClass('fa-beat');
				}


			})
		})

		$("#order").click(function () {
			console.log("按下排序按鈕");
			cardListArray.sort(function (a, b) {

				return a.ownedId - b.ownedId
			});

			sellListArray.sort(function (a, b) {

				return a.ownedId - b.ownedId
			});

			let ajaxString = `<div class="row container cardContainer justify-content-center" id="cardContainer">`;
			for (let i = 0; i < cardListArray.length; i++) {
				ajaxString += `<div class="col-3 d-flex"><div class="card">`;
				ajaxString += `<figure><img alt="" src="${root}/card/downloadCard/\${cardListArray[i].cardId}" class="hanafuda">`;
				ajaxString += `<figcaption>\${cardListArray[i].cardName}</figcaption>`;
				ajaxString += `</figure></div></div>`;
			}

			for (let i = 0; i < sellListArray.length; i++) {
				ajaxString += `<div class="col-3 d-flex"><div class="card">`;
				ajaxString += `<figure><img alt="" src="${root}/card/downloadCard/\${sellListArray[i].cardId}" class="hanafuda">`;
				ajaxString += `<figcaption>\${sellListArray[i].cardName}</figcaption>`;
				ajaxString += `<img src="${root}/img/lyh/selling.png" class="sellingCard"></figure></div></div>`;
			}
			ajaxString += `</div>`;
			$(".cardContainer").remove();
			$(".mycardContainer").append(ajaxString);

		})

		$("#orderDESC").click(function () {

			cardListArray.sort(function (a, b) {

				return b.ownedId - a.ownedId
			});

			sellListArray.sort(function (a, b) {

				return b.ownedId - a.ownedId
			});

			let ajaxString = `<div class="row container cardContainer justify-content-center" id="cardContainer">`;
			for (let i = 0; i < cardListArray.length; i++) {
				ajaxString += `<div class="col-3 d-flex"><div class="card">`;
				ajaxString += `<figure><img alt="" src="${root}/card/downloadCard/\${cardListArray[i].cardId}" class="hanafuda">`;
				ajaxString += `<figcaption>\${cardListArray[i].cardName}</figcaption>`;
				ajaxString += `</figure></div></div>`;
			}

			for (let i = 0; i < sellListArray.length; i++) {
				ajaxString += `<div class="col-3 d-flex"><div class="card">`;
				ajaxString += `<figure><img alt="" src="${root}/card/downloadCard/\${sellListArray[i].cardId}" class="hanafuda">`;
				ajaxString += `<figcaption>\${sellListArray[i].cardName}</figcaption>`;
				ajaxString += `<img src="${root}/img/lyh/selling.png" class="sellingCard"></figure></div></div>`;
			}
			ajaxString += `</div>`;
			// console.log(`getSellList: ${getSellList[i].cardName}`)
			$(".cardContainer").remove();
			$(".mycardContainer").append(ajaxString);

		})



		$("#starOrderDESC").click(function () {

			cardListArray.sort(function (a, b) {

				let stara = a.cardStar
				let starb = b.cardStar

				if (stara === starb) {
					return a.cardId - b.cardId
				}

				return starb - stara
			});

			sellListArray.sort(function (a, b) {

				let stara = a.cardStar
				let starb = b.cardStar

				if (stara === starb) {
					return a.cardId - b.cardId
				}

				return starb - stara
			});

			let ajaxString = `<div class="row container cardContainer justify-content-center" id="cardContainer">`;
			for (let i = 0; i < cardListArray.length; i++) {
				ajaxString += `<div class="col-3 d-flex"><div class="card">`;
				ajaxString += `<figure><img alt="" src="${root}/card/downloadCard/\${cardListArray[i].cardId}" class="hanafuda">`;
				ajaxString += `<figcaption>\${cardListArray[i].cardName}</figcaption>`;
				ajaxString += `</figure></div></div>`;
			}

			for (let i = 0; i < sellListArray.length; i++) {
				ajaxString += `<div class="col-3 d-flex"><div class="card">`;
				ajaxString += `<figure><img alt="" src="${root}/card/downloadCard/\${sellListArray[i].cardId}" class="hanafuda">`;
				ajaxString += `<figcaption>\${sellListArray[i].cardName}</figcaption>`;
				ajaxString += `<img src="${root}/img/lyh/selling.png" class="sellingCard"></figure></div></div>`;
			}
			ajaxString += `</div>`;

			$(".cardContainer").remove();
			$(".mycardContainer").append(ajaxString);

		})

		$("#starOrderASC").click(function () {

			cardListArray.sort(function (a, b) {

				let stara = a.cardStar
				let starb = b.cardStar

				if (stara === starb) {
					return a.cardId - b.cardId
				}

				return stara - starb
			});

			sellListArray.sort(function (a, b) {

				let stara = a.cardStar
				let starb = b.cardStar

				if (stara === starb) {
					return a.cardId - b.cardId
				}

				return stara - starb
			});

			let ajaxString = `<div class="row container cardContainer justify-content-center" id="cardContainer">`;
			for (let i = 0; i < cardListArray.length; i++) {
				ajaxString += `<div class="col-3 d-flex"><div class="card">`;
				ajaxString += `<figure><img alt="" src="${root}/card/downloadCard/\${cardListArray[i].cardId}" class="hanafuda">`;
				ajaxString += `<figcaption>\${cardListArray[i].cardName}</figcaption>`;
				ajaxString += `</figure></div></div>`;
			}

			for (let i = 0; i < sellListArray.length; i++) {
				ajaxString += `<div class="col-3 d-flex"><div class="card">`;
				ajaxString += `<figure><img alt="" src="${root}/card/downloadCard/\${sellListArray[i].cardId}" class="hanafuda">`;
				ajaxString += `<figcaption>\${sellListArray[i].cardName}</figcaption>`;
				ajaxString += `<img src="${root}/img/lyh/selling.png" class="sellingCard"></figure></div></div>`;
			}
			ajaxString += `</div>`;

			$(".cardContainer").remove();
			$(".mycardContainer").append(ajaxString);

		})



		$("#drawCard").click(function () {

			let member = "${member}";
			let coin = "${member.memberCoin}"

			if (member == "") {
				window.location.href = "${root}/login";
			} else if (coin < 100) {
				Swal.fire({ title: '餘額不足', confirmButtonColor: '#CA7159', customClass: 'confirmAlert' })
			} else {
				Swal.fire({
					title: '確定要花100米寶幣抽卡嗎？',
					showCancelButton: true,
					confirmButtonText: '<i class="fa-regular fa-circle-check"></i> 確定',
					cancelButtonText: '<i class="fa-regular fa-circle-xmark"></i> 取消',
					confirmButtonColor: '#CA7159',
					cancelButtonColor: '#CBC0AA',
					customClass: 'confirmAlert',
					reverseButtons: true
				}).then((result) => {

					if (result.isConfirmed) {

						let newCardString = "";
						let isNew = false;
						axios.post("${root}/card/getNewCard").then(response => {
							var str = response.data.newCard;
							// console.log(str)
							var cardId = str.split("cardId=")[1].split(",")[0];
							var cardName = str.split("cardName=")[1].split(",")[0];
							newCardString += `<div class="container newCardContainer"><div class="newCardImgDiv"><img src="${root}/card/downloadCard/\${cardId}" class="newCardImg"><img src="${root}/img/lyh/pop_new.png" class="newIcon d-none"></div><div class="newCardTitle">\${cardName}</div><div class="ribbonAnime"><lottie-player src="${root}/json/123225-ribbon.json"  background="transparent" speed="1" style="width: 500px; height: 600px;" loop autoplay></lottie-player></div></div>`;


							Swal.fire({
								title: '',
								html: newCardString,
								color: '#FFF',
								showCloseButton: true,
								focusConfirm: false,
								background: '#dfa661',
								confirmButtonText: '<i class="fa-regular fa-circle-check"></i> 確定',
								confirmButtonColor: '#CA7159',
								customClass: 'swal-newCard'
							});

							if (response.data.isNew == true) {
								// console.log("isNew is true.");
								$(".newIcon").removeClass("d-none");
							}
							console.log(response.data.isNew)
							console.log(response.data.memberCoin)

							let coin = response.data.memberCoin
							$(".myCoinTitle").empty();
							$(".myCoinTitle").append(coin + ` <i class="fa-solid fa-coins"></i>`)

							axios.get("${root}/card/mycard/${member.memberId}/getOrder").then(function (response) {
								// console.log(response.data.cardList[0].cardId)
								cardListArray = [];
								sellListArray = [];
								for (i = 0; i < response.data.length; i++) {
									if (response.data[i].cardStatus == 1) {
										cardListArray.push(response.data[i])
									} else {
										sellListArray.push(response.data[i])
									}
								}

								let ajaxString = `<div class="row container cardContainer justify-content-center" id="cardContainer">`;
								for (let i = 0; i < cardListArray.length; i++) {
									ajaxString += `<div class="col-3 d-flex"><div class="card">`;
									ajaxString += `<figure><img alt="" src="${root}/card/downloadCard/\${cardListArray[i].cardId}" class="hanafuda">`;
									ajaxString += `<figcaption>\${cardListArray[i].cardName}</figcaption>`;
									ajaxString += `</figure></div></div>`;
								}

								for (let i = 0; i < sellListArray.length; i++) {
									ajaxString += `<div class="col-3 d-flex"><div class="card">`;
									ajaxString += `<figure><img alt="" src="${root}/card/downloadCard/\${sellListArray[i].cardId}" class="hanafuda">`;
									ajaxString += `<figcaption>\${sellListArray[i].cardName}</figcaption>`;
									ajaxString += `<img src="${root}/img/lyh/selling.png" class="sellingCard"></figure></div></div>`;
								}
								ajaxString += `</div>`;

								$(".cardContainer").remove();
								$(".mycardContainer").append(ajaxString);
							}).catch(function (error) {
								console.log("排序時錯誤", error);
							}).finally()
						})
							.catch(error => {
								console.error(error);
							})
					}
				})
			}




		})


	</script>


</body>


</html>