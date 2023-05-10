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
    <link rel="stylesheet" type="text/css" href="${root}/css/card/mycard.css">
    <link rel="stylesheet" type="text/css" href="${root}/css/card/cardList.css">
    <link rel="stylesheet" type="text/css" href="${root}/css/card/cardReleased.css">
</head>

<body>

    <jsp:include page="../include/header.jsp"></jsp:include>
    <div class="cardListContainer">
        <div class="releasedContainer">
            <div class="content">
                <!-- Nav pills -->
                <ul class="nav nav-pills" role="tablist" style="background: none; height: auto;">
                    <li class="nav-item">
                        <a class="nav-link active" data-toggle="pill" href="#login">所有上架卡片</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" data-toggle="pill" href="#regis">我的上架卡片</a>
                    </li>
                </ul>

                <!-- Tab panes -->
                <div class="tab-content">
                    <div id="login" class="container tab-pane active">
                        <div class="row container cardContainer justify-content-center" id="allCardContainer"></div>
                        1
                    </div>
                    <div id="regis" class="container tab-pane fade">
                        <div class="row container cardContainer justify-content-center" id="myCardContainer"></div>
                        2
                    </div>
                </div>
                
            </div>

        </div>
    </div>

    <jsp:include page="../include/footer.jsp"></jsp:include>

    <script>

        $(async function () {
            await getAllList();
            await getMyList();
        })

        let onwedArrayall = [];
        let onwedIdArrayall = [];
        let priceArrayall = [];
        let endTimeArrayall = [];
        let cardArrayall = [];
        let cardDetailImgall = [];
        let cardNameall = [];

        let onwedArraymy = [];
        let onwedIdArraymy = [];
        let priceArraymy = [];
        let endTimeArraymy = [];
        let cardArraymy = [];
        let cardDetailImgmy = [];
        let cardNamemy = [];

        function getAllList() {
            return axios.get("${root}/released/all")
                .then(res => {
                    let htmlstr = "";

              

                    for (i = 0; i < res.data.cardList.length; i++) {
                        // console.log(res.data.cardList[i])
                        onwedArrayall.push($.get(`${root}/released/owned/\${res.data.cardList[i].fkOwnedId}`))
                        onwedIdArrayall.push(`\${res.data.cardList[i].fkOwnedId}`)
                        priceArrayall.push(`\${res.data.cardList[i].directPrice}`)
                        endTimeArrayall.push(`\${res.data.cardList[i].endtime}`)
                    }


                    Promise.all(onwedArrayall).then(function (ownedRes) {

                        for (i = 0; i < ownedRes.length; i++) {
                            // console.log(ownedRes[i].showOnwedDetail)
                            cardArrayall.push($.get(`${root}/released/getCard/\${ownedRes[i].showOnwedDetail.fkCardId}`))
                        }

                        

                        Promise.all(cardArrayall).then(function (cardRes) {

                            for (i = 0; i < cardRes.length; i++) {
                                cardDetailImgall.push(`${root}/card/downloadCard/\${cardRes[i].card.cardId}`)
                                cardNameall.push(`\${cardRes[i].card.cardName}`)
                                // console.log(cardRes[i].card.cardName)
                            }

                            Promise.all(cardDetailImgall).then(function (results) {
                                let id = 0;
                                // console.log(cardName)
                                for (i = 0; i < cardNameall.length; i++) {
                                    htmlstr += `<div class="col-3 d-flex cardEach"><div class="card" id="\${id}">`;
                                    htmlstr += `<figure><img alt="" src="\${results[i]}" class="hanafuda">`;
                                    htmlstr += `<div class="releaseDetail">\${cardNameall[i]}<br>`;
                                    htmlstr += `\${priceArrayall[i]} <i class="fa-solid fa-coins"></i></div>`;
                                    htmlstr += `<a href="#" onclick="
                                    Swal.fire('Any fool can use a computer')
                                    "><img src="${root}/img/lyh/shopping.png" class="shopping" /></a></figure></div></div>`;
                                    id += 1;
                                }


                                // console.log("htmlstr" + htmlstr);
                                $("#allCardContainer").append(htmlstr);

                            })
                        })
                    })


                })
                .catch(err => {
                    console.error(err);
                })
        }

        function getMyList() {
            return axios.get("${root}/released/my")
                .then(res => {
                    let htmlstr = "";



                    for (i = 0; i < res.data.cardList.length; i++) {
                        // console.log(res.data.cardList[i])
                        onwedArraymy.push($.get(`${root}/released/owned/\${res.data.cardList[i].fkOwnedId}`))
                        onwedIdArraymy.push(`\${res.data.cardList[i].fkOwnedId}`)
                        priceArraymy.push(`\${res.data.cardList[i].directPrice}`)
                        endTimeArraymy.push(`\${res.data.cardList[i].endtime}`)
                    }



                    Promise.all(onwedArraymy).then(function (ownedRes) {

                        for (i = 0; i < ownedRes.length; i++) {
                            // console.log(ownedRes[i].showOnwedDetail)
                            cardArraymy.push($.get(`${root}/released/getCard/\${ownedRes[i].showOnwedDetail.fkCardId}`))
                        }



                        Promise.all(cardArraymy).then(function (cardRes) {

                            for (i = 0; i < cardRes.length; i++) {
                                cardDetailImgmy.push(`${root}/card/downloadCard/\${cardRes[i].card.cardId}`)
                                cardNamemy.push(`\${cardRes[i].card.cardName}`)
                                // console.log(cardRes[i].card.cardName)
                            }

                            Promise.all(cardDetailImgmy).then(function (results) {

                                // console.log(cardNamemy)
                                for (i = 0; i < cardNamemy.length; i++) {
                                    let id = 0;
                                    htmlstr += `<div class="col-3 d-flex"><div class="card" id="\${id}">`;
                                    htmlstr += `<figure><img alt="" src="\${results[i]}" class="hanafuda">`;
                                    htmlstr += `<div class="releaseDetail">\${cardNamemy[i]}<br>`;
                                    htmlstr += `\${priceArraymy[i]} <i class="fa-solid fa-coins"></i></div>`;
                                    htmlstr += `<a href="#" title="測試超連結"><img src="${root}/img/lyh/shopping.png" class="shopping" /></a></figure></div></div>`;
                                    id += 1;
                                }


                                // console.log("htmlstr" + htmlstr);
                                $("#myCardContainer").append(htmlstr);

                            })
                        })
                    })


                })
                .catch(err => {
                    console.error(err);
                })
        }

        $(".shopping").click(function() {
            // console.log("this")
            Swal.fire('Any fool can use a computer')
        })

        let cards = document.getElementsByClassName('card');

        // console.log(cards.length)
        
        for(var i = 0; i < cards.length; i++) {
        (function(index) {
            cards[index].addEventListener("click", function() {
            // console.log("Clicked index: " + index);
        })
        })(i);

}


    </script>

</body>

</html>