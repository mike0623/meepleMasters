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
    <div class="cardListContainer" style="padding-bottom: 0;">
        <div class="releasedContainer">
            <div style="height: 50px;"><a href="${root}/newRelease"><button id="drawCard"
                        class="button-19"><span>上架我的卡片</span></button></a></div>
            <div class="content">
                <!-- Nav pills -->
                <ul class="nav nav-pills" role="tablist" style="background: none; height: auto;">
                    <li class="nav-item">
                        <a class="nav-link active" data-toggle="pill" href="#allCard">所有上架卡片</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" data-toggle="pill" href="#myCard">我的上架卡片</a>
                    </li>
                </ul>

                <!-- Tab panes -->
                <div class="tab-content">
                    <div id="allCard" class="container tab-pane active">
                        <div class="row container cardContainer justify-content-center" id="allCardContainer"></div>
                    </div>
                    <div id="myCard" class="container tab-pane fade">
                        <div class="row container cardContainer justify-content-center" id="myCardContainer"></div>
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

        function getAllList() {
            return axios.get("${root}/released/all")
                .then(res => {
                    let htmlstr = "";

                    let memberId = "${member.memberId}"
                    let memberCoin = "${member.memberCoin}"

                    console.log(res)

                    for (i = 0; i < res.data.length; i++) {
                        if (res.data[i].memberId == memberId) {
                            htmlstr += `<div class="col-3 d-flex cardEach"><div class="card">`;
                            htmlstr += `<figure><img alt="" src="${root}/card/downloadCard/\${res.data[i].cardId}" class="hanafuda">`;
                            htmlstr += `<div class="releaseDetail" id="\${res.data[i].releasedId}">\${res.data[i].cardName}<br>`;
                            htmlstr += `\${res.data[i].directPrice} <i class="fa-solid fa-coins"></i><br><i class="fa-regular fa-pen-to-square edit"></i></div>`;
                            htmlstr += `</figure></div></div>`;
                        } else {
                            htmlstr += `<div class="col-3 d-flex cardEach"><div class="card">`;
                            htmlstr += `<figure id="\${res.data[i].releasedId}"><img alt="" src="${root}/card/downloadCard/\${res.data[i].cardId}" class="hanafuda">`;
                            htmlstr += `<div class="releaseDetail">\${res.data[i].cardName}<br>`;
                            htmlstr += `\${res.data[i].directPrice} <i class="fa-solid fa-coins"></i></div>`;
                            htmlstr += `<img src="${root}/img/lyh/shopping.png" class="shopping"/></figure></div></div>`;
                        }
                    }
                    $("#allCardContainer").append(htmlstr);

                    $(".edit").click(function () {
                        let id = $(this).parent().attr('id')

                        axios.get(`${root}/released/card/\${id}`)
                            .then(res => {
                                console.log(res)

                                    ``

                            })
                            .catch(err => {
                                console.error(err);
                            })

                    })

                    $(".shopping").click(function () {
                        let id = $(this).parent().attr('id')

                        axios.get(`${root}/released/card/\${id}`)
                            .then(res => {

                                var getDate = `\${res.data[0].endTime}`;
                                var date = new Date(getDate);
                                var formattedDate = date.toISOString().substr(0, 10);

                                console.log(getDate)
                                console.log(date)
                                console.log(formattedDate);

                                let cardHtml = "";
                                cardHtml += `<div class="newCardContainer container text-center"><div class="row">
                                            <div class="newCardImgDiv col-6">
                                            <img src="${root}/card/downloadCard/\${res.data[0].cardId}" class="newCardImg"></div><div class="col-6">
                                            <div class="newCardTitle">\${res.data[0].cardName}</div>
                                            <div class="cardDetail row">
                                            <div class="col-2">
                                                價格<br>賣家<br>結束時間<br>我的金幣
                                            </div>
                                            <div class="col-4">
                                                \${res.data[0].directPrice} <i class="fa-solid fa-coins"></i><br>
                                                \${res.data[0].memberName}<br>
                                                \${formattedDate} <i class="fa-regular fa-clock"></i><br>
                                                \${memberCoin} <i class="fa-solid fa-coins"></i>
                                                </div>
                                            </div></div></div></div>`
                                Swal.fire({
                                    title: '',
                                    html: cardHtml,
                                    color: '#FFF',
                                    showCloseButton: true,
                                    focusConfirm: false,
                                    background: '#dfa661',
                                    confirmButtonText: '<i class="fa-solid fa-coins"></i> 購買',
                                    confirmButtonColor: '#CA7159',
                                    customClass: 'swal-newCard'
                                }).then((result) => {
                                    if (result.isConfirmed) {
                                        if (memberId == "") {
                                            window.location.href = "${root}/login";
                                        } else if (memberCoin < res.data[0].directPrice) {
                                            Swal.fire({ title: '餘額不足', confirmButtonColor: '#CA7159', customClass: 'confirmAlert' })
                                        } else {
                                            Swal.fire({
                                                title: '確定要購買嗎？',
                                                showCancelButton: true,
                                                confirmButtonText: '<i class="fa-regular fa-circle-check"></i> 確定',
                                                cancelButtonText: '<i class="fa-regular fa-circle-xmark"></i> 取消',
                                                confirmButtonColor: '#CA7159',
                                                cancelButtonColor: '#CBC0AA',
                                                customClass: 'confirmAlert',
                                                reverseButtons: true
                                            }).then((result) => {
                                                if (result.isConfirmed) {
                                                    axios.post("${root}/released/buy?releasedId=" + res.data[0].releasedId + "&ownedId=" + res.data[0].ownedId + "&price=" + res.data[0].directPrice)
                                                        .then(res => {
                                                            // Swal.fire({
                                                            //     icon: 'success',
                                                            //     title: '購買成功',
                                                            //     showConfirmButton: false,
                                                            //     timer: 1500
                                                            // })
                                                            window.location.href = "${root}/card/releasedList"
                                                            console.log(res)
                                                        })
                                                        .catch(err => {
                                                            console.error(err);
                                                        })
                                                }

                                            })


                                        }
                                    }

                                });

                                console.log(res)
                            })
                            .catch(err => {
                                console.error(err);
                            })
                    })

                })
                .catch(err => {
                    console.error(err);
                })
        }

        function getMyList() {
            return axios.get("${root}/released/all")
                .then(res => {

                    let memberId = "${member.memberId}"

                    let htmlstr = "";


                    for (i = 0; i < res.data.length; i++) {
                        if (res.data[i].memberId == memberId) {
                            htmlstr += `<div class="col-3 d-flex cardEach"><div class="card">`;
                            htmlstr += `<figure><img alt="" src="${root}/card/downloadCard/\${res.data[i].cardId}" class="hanafuda">`;
                            htmlstr += `<div class="releaseDetail" id="\${res.data[i].releasedId}">\${res.data[i].cardName}<br>`;
                            htmlstr += `\${res.data[i].directPrice} <i class="fa-solid fa-coins"></i><br><i class="fa-regular fa-pen-to-square edit"></i></div>`;
                            htmlstr += `</figure></div></div>`;
                        }
                    }
                    $("#myCardContainer").append(htmlstr);

                    $(".edit").click(function () {
                        let id = $(this).parent().attr('id')

                        axios.get(`${root}/released/card/\${id}`)
                            .then(res => {
                                console.log(res)
                            })
                            .catch(err => {
                                console.error(err);
                            })
                    })

                })
                .catch(err => {
                    console.error(err);
                })
        }



    </script>

</body>

</html>