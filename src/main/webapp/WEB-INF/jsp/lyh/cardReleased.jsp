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
            <div style="height: 50px;"><button id="newRelease" class="button-19"><span>上架我的卡片</span></button></div>
            <div class="content">
                <!-- Nav pills -->
                <ul class="nav nav-pills" role="tablist" style="background: none; height: auto;">
                    <li class="nav-item">
                        <a class="nav-link active nav-all" data-toggle="pill" href="#allCard">所有上架卡片</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link nav-my" data-toggle="pill" href="#myCard">我的上架卡片</a>
                    </li>
                </ul>

                <!-- Tab panes -->
                <div class="tab-content">
                    <span class="myCoinDiv">
                        <h4>我的米寶幣 <span class="myCoin"></span> <i class="fa-solid fa-coins"></i></h4>
                    </span>
                    <div class="btn-group row justify-content-end">
                        <button type="button" class="btn btn-outline-warning dropdown-toggle d-none"
                            data-bs-toggle="dropdown" aria-expanded="false">
                            上架排序
                        </button>
                        <ul class="dropdown-menu">
                            <li class="dropdown-item" id="newList">最新上架</li>
                            <li class="dropdown-item" id="endTimeList">下架時間</li>
                            <li class="dropdown-item" id="starDESCList">星數　
                                <i class="fa-solid fa-arrow-down-wide-short"></i>
                            </li>
                            <li class="dropdown-item" id="starASCList">星數　
                                <i class="fa-solid fa-arrow-up-short-wide"></i>
                            </li>
                        </ul>
                    </div>
                    <div id="allCard" class="container tab-pane active">
                        <div class="row container cardContainer justify-content-center" id="allCardContainer"></div>
                    </div>
                    <div id="myCard" class="container tab-pane fade">
                        <div class="row container cardContainer justify-content-center" id="myCardContainer"></div>
                    </div>
                </div>

            </div>

        </div>
        <form action="${root}/editRelease" method="get" class="forEdit">
            <input type="hidden" name="releasedId" id="paramDirect" hidden>
            <input type="submit" value="Submit" hidden>
        </form>
        <form action="${root}/editAuction" method="get" class="forAuctionEdit">
            <input type="hidden" name="releasedId" id="paramAuction" hidden>
            <input type="submit" value="Submit" hidden>
        </form>
    </div>


    <jsp:include page="../include/footer.jsp"></jsp:include>

    <script>

        let memberId = "${member.memberId}"
        let memberCoin = "${member.memberCoin}"

        if (memberId == "") {
            window.location.href = "${root}/login";
        }

        $(".myCoin").empty();
        $(".myCoin").append(memberCoin);

        $(async function () {
            await getAllList();
            await getMyList();
        })
        let status = 1;

        $("#newList").click(function () {
            status = 1;
            getAllList();
            getMyList();
        })

        $("#endTimeList").click(function () {
            status = 2;
            getAllList();
            getMyList();
        })

        $("#starDESCList").click(function () {
            status = 3;
            getAllList();
            getMyList();
        })

        $("#starASCList").click(function () {
            status = 4;
            getAllList();
            getMyList();
        })

        $("#newRelease").click(function () {
            Swal.fire({
                title: '選擇上架方式',
                showDenyButton: true,
                showCancelButton: false,
                confirmButtonText: '<i class="fa-solid fa-coins"></i> 直購',
                denyButtonText: '<i class="fa-solid fa-gavel"></i> 拍賣',
                confirmButtonColor: '#dc7e6a',
                denyButtonColor: '#da9255',
                customClass: 'endAlert'
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = `${root}/newRelease`
                } else if (result.isDenied) {
                    window.location.href = "${root}/newAuction"
                }
            })
        })

        const today = new Date();
        let getEndTime;
        let endTime;
        let times = 0;
        let diffInMs;
        let diffInDays;


        function getAllList() {
            return axios.get("${root}/released/all/" + status)
                .then(res => {
                    let htmlstr = "";

                    let count = 0;

                    console.log(res)

                    for (i = 0; i < res.data.length; i++) {
                        getEndTime = `\${res.data[i].endTime}`;
                        endTime = new Date(getEndTime);
                        diffInMs = endTime - today;
                        diffInDays = diffInMs / (1000 * 60 * 60 * 24);

                        if (diffInDays < 0) {
                            if (res.data[i].purchasePrice != null) {
                                axios.post("${root}/released/stopAuction?releasedId=" + res.data[i].releasedId + "&ownedId=" + res.data[i].ownedId + "&price=" + res.data[i].purchasePrice)
                            } else {
                                axios.post("${root}/released/discontinued?releasedId=" + res.data[i].releasedId + "&ownedId=" + res.data[i].ownedId)
                            }
                        }

                        htmlstr += `<div class="col-3 d-flex cardEach"><div class="card">`;

                        if (res.data[i].memberId == memberId) {
                            htmlstr += `<figure><img alt="" src="${root}/card/downloadCard/\${res.data[i].cardId}" class="hanafuda">`;
                            htmlstr += `<div class="releaseDetail" id="\${res.data[i].releasedId}">\${res.data[i].cardName}<br>`;
                        } else {
                            htmlstr += `<figure id="\${res.data[i].releasedId}"><img alt="" src="${root}/card/downloadCard/\${res.data[i].cardId}" class="hanafuda">`;
                            htmlstr += `<div class="releaseDetail">\${res.data[i].cardName}<br>`;
                        }

                        if (res.data[i].type == 2) {
                            if (res.data[i].purchasePrice == null) {
                                htmlstr += `\${res.data[i].startPrice} <i class="fa-solid fa-gavel"></i><br>`;
                            } else {
                                htmlstr += `\${res.data[i].purchasePrice} <i class="fa-solid fa-gavel"></i><br>`;
                            }
                        }

                        if (res.data[i].directPrice != null) {
                            htmlstr += `\${res.data[i].directPrice} <i class="fa-solid fa-coins"></i>`;
                        }


                        if (res.data[i].memberId == memberId) {
                            htmlstr += `<br><i class="fa-regular fa-pen-to-square edit"></i></div>`;
                        } else {
                            if (res.data[i].type == 1) {
                                htmlstr += `</div><img src="${root}/img/lyh/shopping.png" class="shopping direct" />`
                            } else {
                                htmlstr += `</div><img src="${root}/img/lyh/auction.png" class="shopping auction" />`
                            }
                        }

                        if (diffInDays <= 1) {
                            htmlstr += `<img src="${root}/img/lyh/hourglass.png" class="expire">`;
                        }

                        htmlstr += `</figure></div></div>`;
                        count += 1;
                    }
                    if (count == 0) {
                        htmlstr = `<div style="height: 200px; line-height: 200px;">目前沒有上架中的卡片</div>`
                    }

                    if ($('.nav-all').hasClass('active')) {
                        $(".dropdown-toggle").removeClass("d-none");
                    }

                    $("#allCardContainer").empty();
                    $("#allCardContainer").append(htmlstr);

                    $(".edit").click(function () {
                        let id = $(this).parent().attr('id')

                        axios.get(`${root}/released/card/\${id}`)
                            .then(res => {

                                let getStartTime = `\${res.data.startTime}`;
                                let formattedStartTime = getStartTime.substr(0, 10);
                                // console.log("formattedStartTime:"+formattedStartTime)

                                let getEndTime = `\${res.data.endTime}`;
                                let endTime = new Date(getEndTime);

                                // // 檢查 endTime 是否為午夜 00:00:00
                                // if (endTime.getHours() !== 0 || endTime.getMinutes() !== 0 || endTime.getSeconds() !== 0) {
                                //     // 將日期減少一天
                                //     endTime.setDate(endTime.getDate() + 1);
                                // }

                                let formattedEndTime;
                                if (getEndTime.substr(-8) != "00:00:00") {
                                    formattedEndTime = getEndTime.substr(0, 10);
                                } else {
                                    formattedEndTime = endTime.toISOString().substr(0, 10);
                                }


                                let cardHtml = "";

                                if (res.data.type == 2) {
                                    let releasedId = `\${res.data.releasedId}`;
                                    $("#paramAuction").val(releasedId);

                                    cardHtml += `<div class="newCardContainer container text-center"><div class="row">
                                            <div class="newCardImgDiv col-6">
                                            <img src="${root}/card/downloadCard/\${res.data.cardId}" class="newCardImg"></div><div class="col-6">
                                            <div class="newCardTitle" style="margin: 10px">\${res.data.cardName}</div>
                                            <div id="countdownAll" style="font-size: 14px; height: 19px;"></div>
                                            <div class="cardDetail row"><div class="col-3 detailLeft">`
                                    if (res.data.directPrice != null) {
                                        cardHtml += `目前出價<br>最高出價者<br>直購價<br>上架時間<br>結束時間`;
                                    } else {
                                        cardHtml += `目前出價<br>最高出價者<br>上架時間<br>結束時間`
                                    }

                                    cardHtml += `</div><div class="col-4">`

                                    if (res.data.purchasePrice != null) {
                                        cardHtml += `\${res.data.purchasePrice} <i class="fa-solid fa-coins"></i><br>
                                    \${res.data.purchaserName} <i class="fa-solid fa-user"></i><br>`
                                    } else {
                                        cardHtml += `\${res.data.startPrice} <i class="fa-solid fa-coins"></i><br>
                                    尚未有人出價<br>`
                                    }
                                    if (res.data.directPrice != null) {
                                        cardHtml += `\${res.data.directPrice} <i class="fa-solid fa-coins"></i><br>`
                                    }
                                    cardHtml += `\${formattedStartTime} <i class="fa-regular fa-clock"></i><br>
                                            \${formattedEndTime} <i class="fa-regular fa-clock"></i><br>
                                            </div></div></div></div></div>`

                                    let intervalId = setInterval(() => {
                                        let now = new Date().getTime();
                                        endTime = new Date(getEndTime);
                                        let distance = endTime - now;

                                        let days = Math.floor(distance / (1000 * 60 * 60 * 24));
                                        let hours = Math.floor(
                                            (distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
                                        );
                                        let minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
                                        let seconds = Math.floor((distance % (1000 * 60)) / 1000);

                                        $("#countdownAll").html(`\${days}天\${hours}小時\${minutes}分\${seconds}秒 結束`);
                                    }, 1000);

                                    if (res.data.purchasePrice == null) {
                                        Swal.fire({
                                            title: '',
                                            html: cardHtml,
                                            showDenyButton: true,
                                            showCancelButton: false,
                                            confirmButtonText: '下架卡片',
                                            denyButtonText: `編輯上架資訊`,
                                            confirmButtonColor: '#dc7e6a',
                                            denyButtonColor: '#647168',
                                            background: '#dfa661',
                                            customClass: 'editAlert',
                                            willClose: () => {
                                                clearInterval(intervalId);
                                            }
                                        }).then((result) => {
                                            if (result.isConfirmed) {
                                                axios.post("${root}/released/discontinued?releasedId=" + res.data.releasedId + "&ownedId=" + res.data.ownedId)
                                                    .then(res => {
                                                        Swal.fire({
                                                            title: '下架成功',
                                                            showDenyButton: true,
                                                            showCancelButton: false,
                                                            confirmButtonText: '回卡片市集',
                                                            denyButtonText: `回我的卡片`,
                                                            confirmButtonColor: '#dc7e6a',
                                                            denyButtonColor: '#da9255',
                                                            customClass: 'endAlert'
                                                        }).then((result) => {
                                                            if (result.isConfirmed) {
                                                                window.location.href = "${root}/card/releasedList"
                                                            } else if (result.isDenied) {
                                                                window.location.href = `${root}/card/mycard/\${memberId}`
                                                            }
                                                        })
                                                        console.log(res)
                                                    })
                                                    .catch(err => {
                                                        console.error(err);
                                                    })
                                            } else if (result.isDenied) {
                                                $('.forAuctionEdit').submit();
                                            }
                                        })
                                    } else {
                                        Swal.fire({
                                            title: '',
                                            html: cardHtml,
                                            showCancelButton: false,
                                            confirmButtonText: '提前結標',
                                            confirmButtonColor: '#dc7e6a',
                                            background: '#dfa661',
                                            customClass: 'editAlert',
                                            willClose: () => {
                                                clearInterval(intervalId);
                                            }
                                        }).then((result) => {
                                            console.log("???")
                                            if (result.isConfirmed) {
                                                Swal.fire({
                                                    title: `確定要提前結標？`,
                                                    showCancelButton: true,
                                                    confirmButtonText: '<i class="fa-regular fa-circle-check"></i> 確定',
                                                    cancelButtonText: '<i class="fa-regular fa-circle-xmark"></i> 取消',
                                                    confirmButtonColor: '#CA7159',
                                                    cancelButtonColor: '#CBC0AA',
                                                    customClass: 'confirmAlert',
                                                    reverseButtons: true
                                                }).then((result) => {
                                                    if (result.isConfirmed) {
                                                        axios.post("${root}/released/stopAuction?releasedId=" + res.data.releasedId + "&ownedId=" + res.data.ownedId + "&price=" + res.data.purchasePrice)

                                                        $(".myCoin").empty();
                                                        $(".myCoin").append(memberCoin);
                                                        window.location.href = "${root}/card/releasedList"
                                                    }
                                                })
                                            }
                                        })
                                    }
                                } else {
                                    let releasedId = `\${res.data.releasedId}`;
                                    $("#paramDirect").val(releasedId);

                                    cardHtml += `<div class="newCardContainer container text-center"><div class="row">
                                            <div class="newCardImgDiv col-6">
                                            <img src="${root}/card/downloadCard/\${res.data.cardId}" class="newCardImg"></div><div class="col-6">
                                            <div class="newCardTitle" style="margin: 10px">\${res.data.cardName}</div>
                                            <div id="countdownEditMy" style="font-size: 14px; height: 19px;"></div>
                                            <div class="cardDetail row"><div class="col-3 detailLeft">`
                                    if (res.data.directPrice != null) {
                                        cardHtml += `直購價<br>上架時間<br>結束時間`;
                                    }

                                    cardHtml += `</div><div class="col-4">`


                                    if (res.data.directPrice != null) {
                                        cardHtml += `\${res.data.directPrice} <i class="fa-solid fa-coins"></i><br>`
                                    }
                                    cardHtml += `\${formattedStartTime} <i class="fa-regular fa-clock"></i><br>
                                            \${formattedEndTime} <i class="fa-regular fa-clock"></i><br>
                                            </div></div></div></div></div>`

                                    Swal.fire({
                                        title: '',
                                        html: cardHtml,
                                        showDenyButton: true,
                                        showCancelButton: false,
                                        confirmButtonText: '下架卡片',
                                        denyButtonText: `編輯上架資訊`,
                                        confirmButtonColor: '#dc7e6a',
                                        denyButtonColor: '#647168',
                                        background: '#dfa661',
                                        customClass: 'editAlert',
                                        willClose: () => {
                                            clearInterval(intervalId);
                                        }
                                    }).then((result) => {
                                        if (result.isConfirmed) {
                                            axios.post("${root}/released/discontinued?releasedId=" + res.data.releasedId + "&ownedId=" + res.data.ownedId)
                                                .then(res => {
                                                    Swal.fire({
                                                        title: '下架成功',
                                                        showDenyButton: true,
                                                        showCancelButton: false,
                                                        confirmButtonText: '回卡片市集',
                                                        denyButtonText: `回我的卡片`,
                                                        confirmButtonColor: '#dc7e6a',
                                                        denyButtonColor: '#da9255',
                                                        customClass: 'endAlert'
                                                    }).then((result) => {
                                                        if (result.isConfirmed) {
                                                            window.location.href = "${root}/card/releasedList"
                                                        } else if (result.isDenied) {
                                                            window.location.href = `${root}/card/mycard/\${memberId}`
                                                        }
                                                    })
                                                    console.log(res)
                                                })
                                                .catch(err => {
                                                    console.error(err);
                                                })
                                        } else if (result.isDenied) {
                                            $('.forEdit').submit();
                                        }
                                    })
                                }
                            })
                            .catch(err => {
                                console.error(err);
                            })

                    })

                    $(".direct").click(function () {
                        let id = $(this).parent().attr('id')
                        axios.get(`${root}/released/card/\${id}`)
                            .then(res => {

                                let getStartTime = `\${res.data.startTime}`;
                                let formattedStartTime = getStartTime.substr(0, 10);
                                // console.log("formattedStartTime:"+formattedStartTime)

                                let getEndTime = `\${res.data.endTime}`;
                                let endTime = new Date(getEndTime);

                                // 檢查 endTime 是否為午夜 00:00:00
                                // if (endTime.getHours() !== 0 || endTime.getMinutes() !== 0 || endTime.getSeconds() !== 0) {
                                //     // 將日期減少一天
                                //     endTime.setDate(endTime.getDate() + 1);
                                // }

                                let formattedEndTime;
                                if (getEndTime.substr(-8) != "00:00:00") {
                                    formattedEndTime = getEndTime.substr(0, 10);
                                } else {
                                    formattedEndTime = endTime.toISOString().substr(0, 10);
                                }


                                let cardHtml = "";
                                cardHtml += `<div class="newCardContainer container text-center"><div class="row">
                                            <div class="newCardImgDiv col-6">
                                            <img src="${root}/card/downloadCard/\${res.data.cardId}" class="newCardImg"></div><div class="col-6">
                                            <div class="newCardTitle">\${res.data.cardName}</div>
                                            <div class="cardDetail row">
                                            <div class="col-3">
                                                賣家<br>價格<br>上架時間<br>結束時間<br>我的米寶幣
                                            </div>
                                            <div class="col-4">
                                                \${res.data.memberName} <i class="fa-solid fa-user"></i><br>
                                                \${res.data.directPrice} <i class="fa-solid fa-coins"></i><br>
                                                \${formattedStartTime} <i class="fa-regular fa-clock"></i><br>
                                                \${formattedEndTime} <i class="fa-regular fa-clock"></i><br>
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
                                        let memberCoin = "${member.memberCoin}"
                                        if (memberId == "") {
                                            window.location.href = "${root}/login";
                                        } else if (memberCoin < res.data.directPrice) {
                                            Swal.fire({ title: '餘額不足', confirmButtonColor: '#CA7159', customClass: 'confirmAlert' })
                                        } else {
                                            Swal.fire({
                                                title: `確定要花 \${res.data.directPrice} <i class="fa-solid fa-coins"></i> 購買嗎？`,
                                                html: `購買後剩餘 \${memberCoin-res.data.directPrice} <i class="fa-solid fa-coins"></i>`,
                                                showCancelButton: true,
                                                confirmButtonText: '<i class="fa-regular fa-circle-check"></i> 確定',
                                                cancelButtonText: '<i class="fa-regular fa-circle-xmark"></i> 取消',
                                                confirmButtonColor: '#CA7159',
                                                cancelButtonColor: '#CBC0AA',
                                                customClass: 'confirmAlert',
                                                reverseButtons: true
                                            }).then((result) => {
                                                if (result.isConfirmed) {
                                                    axios.post("${root}/released/buy?releasedId=" + res.data.releasedId + "&ownedId=" + res.data.ownedId + "&price=" + res.data.directPrice)
                                                        .then(res => {
                                                            Swal.fire({
                                                                title: '購買成功！',
                                                                showDenyButton: true,
                                                                showCancelButton: false,
                                                                confirmButtonText: '回卡片市集',
                                                                denyButtonText: `回我的卡片`,
                                                                icon: 'success',
                                                                confirmButtonColor: '#dc7e6a',
                                                                denyButtonColor: '#da9255',
                                                                customClass: 'successAlert'
                                                            }).then((result) => {
                                                                /* Read more about isConfirmed, isDenied below */
                                                                if (result.isConfirmed) {
                                                                    window.location.href = "${root}/card/releasedList"
                                                                } else if (result.isDenied) {
                                                                    window.location.href = `${root}/card/mycard/\${memberId}`
                                                                }
                                                            })

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



                    $(".auction").click(function () {
                        let id = $(this).parent().attr('id')
                        axios.get(`${root}/released/card/\${id}`)
                            .then(res => {

                                let getStartTime = `\${res.data.startTime}`;
                                let formattedStartTime = getStartTime.substr(0, 10);
                                // console.log("formattedStartTime:"+formattedStartTime)

                                let getEndTime = `\${res.data.endTime}`;
                                let endTime = new Date(getEndTime);

                                // 檢查 endTime 是否為午夜 00:00:00
                                // if (endTime.getHours() !== 0 || endTime.getMinutes() !== 0 || endTime.getSeconds() !== 0) {
                                //     // 將日期減少一天
                                //     endTime.setDate(endTime.getDate() + 1);
                                // }

                                let formattedEndTime;
                                if (getEndTime.substr(-8) != "00:00:00") {
                                    formattedEndTime = getEndTime.substr(0, 10);
                                } else {
                                    formattedEndTime = endTime.toISOString().substr(0, 10);
                                }
                                // console.log("formattedEndTime:"+formattedEndTime)


                                let cardHtml = "";
                                cardHtml += `<div class="newCardContainer container text-center"><div class="row">
                                            <div class="newCardImgDiv col-6">
                                            <img src="${root}/card/downloadCard/\${res.data.cardId}" class="newCardImg"></div><div class="col-6">
                                            <div class="newCardTitle" style="margin: 10px">\${res.data.cardName}</div>
                                            <div id="countdown" style="font-size: 14px; height: 19px;"></div>
                                            <div class="cardDetail row" style="line-height: 38px;"><div class="col-3 detailLeft">`
                                if (res.data.directPrice != null) {
                                    cardHtml += `賣家<br>目前出價<br>最高出價者<br>直購價<br>上架時間<br>結束時間`;
                                } else {
                                    cardHtml += `賣家<br>目前出價<br>最高出價者<br>上架時間<br>結束時間`
                                }

                                if (res.data.purchaserId != memberId) {
                                    cardHtml += `<br>我的米寶幣<br>出價`;
                                }

                                cardHtml += `</div><div class="col-4">`
                                cardHtml += `\${res.data.memberName} <i class="fa-solid fa-user"></i><br>`;

                                if (res.data.purchasePrice != null) {
                                    cardHtml += `\${res.data.purchasePrice} <i class="fa-solid fa-coins"></i><br>
                                    \${res.data.purchaserName} <i class="fa-solid fa-user"></i><br>`
                                } else {
                                    cardHtml += `\${res.data.startPrice} <i class="fa-solid fa-coins"></i><br>
                                    尚未有人出價<br>`
                                }
                                if (res.data.directPrice != null) {
                                    cardHtml += `\${res.data.directPrice} <i class="fa-solid fa-coins"></i><br>`
                                }
                                cardHtml += `\${formattedStartTime} <i class="fa-regular fa-clock"></i><br>
                                            \${formattedEndTime} <i class="fa-regular fa-clock"></i>`

                                if (res.data.purchaserId != memberId) {
                                    cardHtml += `<br>
                                            \${memberCoin} <i class="fa-solid fa-coins"></i><br><input type="number" name="purchasePrice" id="forPurchase" class="purchasePrice" onkeyup="if(event.keyCode !=37 && event.keyCode != 39)value=value.replace(/\D/g,'')" oninput="`
                                    if (res.data.directPrice != null) {
                                        cardHtml += `if(value>\${res.data.directPrice})value=\${res.data.directPrice};`
                                    }
                                    if (res.data.purchasePrice != null) {
                                        cardHtml += `if(value<\${res.data.purchasePrice})value=(\${res.data.purchasePrice}+1)" required>`;
                                    } else {
                                        cardHtml += `if(value<\${res.data.startPrice})value=(\${res.data.startPrice}+1)" required>`;
                                    }
                                }



                                cardHtml += `</div></div></div></div></div>`

                                let intervalId = setInterval(() => {
                                    let now = new Date().getTime();
                                    endTime = new Date(getEndTime);
                                    let distance = endTime - now;

                                    let days = Math.floor(distance / (1000 * 60 * 60 * 24));
                                    let hours = Math.floor(
                                        (distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
                                    );
                                    let minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
                                    let seconds = Math.floor((distance % (1000 * 60)) / 1000);

                                    let countdownEl = document.getElementById("countdown");
                                    countdownEl.innerHTML = `\${days}天\${hours}小時\${minutes}分\${seconds}秒 結束`;
                                }, 1000);

                                if (res.data.purchaserId != memberId) {
                                    if (res.data.directPrice == null) {
                                        Swal.fire({
                                            title: '',
                                            html: cardHtml,
                                            color: '#FFF',
                                            showCloseButton: true,
                                            focusConfirm: false,
                                            background: '#dfa661',
                                            confirmButtonText: '<i class="fa-solid fa-gavel"></i> 出價',
                                            confirmButtonColor: '#CA7159',
                                            customClass: 'swal-newCard',
                                            willClose: () => {
                                                clearInterval(intervalId);
                                            }
                                        }).then((result) => {
                                            if (result.isConfirmed) {
                                                let memberCoin = Number("${member.memberCoin}")
                                                let price = $("#forPurchase").val();
                                                console.log("memberCoin=" + memberCoin)
                                                console.log("price=" + price)
                                                if (memberId == "") {
                                                    window.location.href = "${root}/login";
                                                } else if (memberCoin < price) {
                                                    Swal.fire({ title: '餘額不足', confirmButtonColor: '#CA7159', customClass: 'confirmAlert' })
                                                } else {
                                                    Swal.fire({
                                                        title: `確定要以 \${price} <i class="fa-solid fa-coins"></i> 出價嗎？`,
                                                        html: '出價後會先扣除您持有的米寶幣<br>若有其他會員再出價時則返還',
                                                        showCancelButton: true,
                                                        confirmButtonText: '<i class="fa-regular fa-circle-check"></i> 確定',
                                                        cancelButtonText: '<i class="fa-regular fa-circle-xmark"></i> 取消',
                                                        confirmButtonColor: '#CA7159',
                                                        cancelButtonColor: '#CBC0AA',
                                                        customClass: 'confirmAlert',
                                                        reverseButtons: true
                                                    }).then((result) => {
                                                        if (result.isConfirmed) {
                                                            // console.log("price=" + price);

                                                            const now = new Date();
                                                            getEndTime = `\${res.data.endTime}`;
                                                            endTime = new Date(getEndTime);
                                                            diffInMs = endTime - now;
                                                            diffInMinutes = Math.floor(diffInMs / (1000 * 60));

                                                            if (diffInMinutes < 5) {
                                                                axios.post("${root}/released/addFiveMinutes?releasedId=" + res.data.releasedId)
                                                            }

                                                            axios.post("${root}/released/purchaseAuction?releasedId=" + res.data.releasedId + "&purchasePrice=" + price).then(() => {
                                                                window.location.href = "${root}/card/releasedList"

                                                                let memberId = "${member.memberId}"
                                                                let memberCoin = "${member.memberCoin}"

                                                                $(".myCoin").empty();
                                                                $(".myCoin").append(memberCoin);
                                                            })
                                                            
                                                            
                                                        }

                                                    })
                                                }
                                            }
                                        });
                                    } else {
                                        Swal.fire({
                                            title: '',
                                            html: cardHtml,
                                            color: '#FFF',
                                            showDenyButton: true,
                                            showCloseButton: true,
                                            background: '#dfa661',
                                            confirmButtonText: '<i class="fa-solid fa-coins"></i> 直購',
                                            denyButtonText: '<i class="fa-solid fa-gavel"></i> 出價',
                                            confirmButtonColor: '#CA7159',
                                            denyButtonColor: '#da9255',
                                            customClass: 'swal-newCard',
                                            willClose: () => {
                                                clearInterval(intervalId);
                                            }
                                        }).then((result) => {
                                            if (result.isConfirmed) {
                                                let memberCoin = "${member.memberCoin}"
                                                if (memberId == "") {
                                                    window.location.href = "${root}/login";
                                                } else if (memberCoin < res.data.directPrice) {
                                                    Swal.fire({ title: '餘額不足', confirmButtonColor: '#CA7159', customClass: 'confirmAlert' })
                                                } else {
                                                    Swal.fire({
                                                        title: `確定要花 \${res.data.directPrice} <i class="fa-solid fa-coins"></i> 購買嗎？`,
                                                        html: `購買後剩餘 \${memberCoin-res.data.directPrice} <i class="fa-solid fa-coins"></i>`,
                                                        showCancelButton: true,
                                                        confirmButtonText: '<i class="fa-regular fa-circle-check"></i> 確定',
                                                        cancelButtonText: '<i class="fa-regular fa-circle-xmark"></i> 取消',
                                                        confirmButtonColor: '#CA7159',
                                                        cancelButtonColor: '#CBC0AA',
                                                        customClass: 'confirmAlert',
                                                        reverseButtons: true
                                                    }).then((result) => {
                                                        if (result.isConfirmed) {
                                                            axios.post("${root}/released/buyAuctionDirect?releasedId=" + res.data.releasedId + "&ownedId=" + res.data.ownedId + "&price=" + res.data.directPrice)
                                                                .then(res => {
                                                                    Swal.fire({
                                                                        title: '購買成功！',
                                                                        showDenyButton: true,
                                                                        showCancelButton: false,
                                                                        confirmButtonText: '回卡片市集',
                                                                        denyButtonText: `回我的卡片`,
                                                                        icon: 'success',
                                                                        confirmButtonColor: '#dc7e6a',
                                                                        denyButtonColor: '#da9255',
                                                                        customClass: 'successAlert'
                                                                    }).then((result) => {
                                                                        /* Read more about isConfirmed, isDenied below */
                                                                        if (result.isConfirmed) {
                                                                            window.location.href = "${root}/card/releasedList"
                                                                        } else if (result.isDenied) {
                                                                            window.location.href = `${root}/card/mycard/\${memberId}`
                                                                        }
                                                                    })

                                                                    console.log(res)
                                                                })
                                                                .catch(err => {
                                                                    console.error(err);
                                                                })
                                                        }

                                                    })
                                                }
                                            } else if (result.isDenied) {
                                                let memberCoin = Number("${member.memberCoin}")
                                                let price = $("#forPurchase").val();
                                                console.log("memberCoin=" + memberCoin)
                                                console.log("price=" + price)
                                                if (memberId == "") {
                                                    window.location.href = "${root}/login";
                                                } else if (memberCoin < price) {
                                                    Swal.fire({ title: '餘額不足', confirmButtonColor: '#CA7159', customClass: 'confirmAlert' })
                                                } else {
                                                    Swal.fire({
                                                        title: `確定要以 \${price} <i class="fa-solid fa-coins"></i> 出價嗎？`,
                                                        html: '出價後會先扣除您持有的米寶幣<br>若有其他會員再出價時則返還',
                                                        showCancelButton: true,
                                                        confirmButtonText: '<i class="fa-regular fa-circle-check"></i> 確定',
                                                        cancelButtonText: '<i class="fa-regular fa-circle-xmark"></i> 取消',
                                                        confirmButtonColor: '#CA7159',
                                                        cancelButtonColor: '#CBC0AA',
                                                        customClass: 'confirmAlert',
                                                        reverseButtons: true
                                                    }).then((result) => {
                                                        if (result.isConfirmed) {

                                                            const now = new Date();
                                                            getEndTime = `\${res.data.endTime}`;
                                                            endTime = new Date(getEndTime);
                                                            diffInMs = endTime - now;
                                                            diffInMinutes = Math.floor(diffInMs / (1000 * 60));

                                                            if (diffInMinutes < 5) {
                                                                axios.post("${root}/released/addFiveMinutes?releasedId=" + res.data.releasedId)
                                                            }

                                                            axios.post("${root}/released/purchaseAuction?releasedId=" + res.data.releasedId + "&purchasePrice=" + price).then(function(){
                                                                window.location.href = "${root}/card/releasedList"
                                                                
                                                                let memberId = "${member.memberId}"
                                                                let memberCoin = "${member.memberCoin}"

                                                                $(".myCoin").empty();
                                                                $(".myCoin").append(memberCoin);

                                                            })
                                                            
                                                        }
                                                    })
                                                }
                                            }
                                        });
                                    }
                                } else {
                                    Swal.fire({
                                        title: '',
                                        html: cardHtml,
                                        showCancelButton: false,
                                        confirmButtonText: '關閉',
                                        confirmButtonColor: '#dc7e6a',
                                        background: '#dfa661',
                                        customClass: 'editAlert',
                                        willClose: () => {
                                            clearInterval(intervalId);
                                        }
                                    })
                                }
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
            return axios.get("${root}/released/all/" + status)
                .then(res => {

                    let memberId = "${member.memberId}"
                    let htmlstr = "";
                    let count = 0;

                    for (i = 0; i < res.data.length; i++) {
                        getEndTime = `\${res.data[i].endTime}`;
                        endTime = new Date(getEndTime);
                        diffInMs = endTime - today;
                        diffInDays = diffInMs / (1000 * 60 * 60 * 24);

                        if (diffInDays < 0) {
                            axios.post("${root}/released/discontinued?releasedId=" + res.data[i].releasedId + "&ownedId=" + res.data[i].ownedId)
                        }

                        if (res.data[i].memberId == memberId) {
                            htmlstr += `<div class="col-3 d-flex cardEach"><div class="card">`;
                            htmlstr += `<figure><img alt="" src="${root}/card/downloadCard/\${res.data[i].cardId}" class="hanafuda">`;
                            htmlstr += `<div class="releaseDetail" id="\${res.data[i].releasedId}">\${res.data[i].cardName}<br>`;


                            if (res.data[i].type == 2) {
                                htmlstr += `\${res.data[i].startPrice} <i class="fa-solid fa-gavel"></i><br>`;
                            }

                            if (res.data[i].directPrice != null) {
                                htmlstr += `\${res.data[i].directPrice} <i class="fa-solid fa-coins"></i>`;
                            }

                            htmlstr += `<br><i class="fa-regular fa-pen-to-square edit"></i></div>`;

                            if (diffInDays <= 1) {
                                htmlstr += `<img src="${root}/img/lyh/hourglass.png" class="expire">`;
                            }
                            htmlstr += `</figure></div></div>`;
                            count += 1;
                        }
                    }

                    if (count == 0) {
                        htmlstr = `<div style="height: 200px; line-height: 200px;">目前沒有上架中的卡片</div>`
                    }

                    $("#myCardContainer").empty();
                    $("#myCardContainer").append(htmlstr);

                    $(".edit").click(function () {
                        let id = $(this).parent().attr('id')

                        axios.get(`${root}/released/card/\${id}`)
                            .then(res => {

                                let getStartTime = `\${res.data.startTime}`;
                                let formattedStartTime = getStartTime.substr(0, 10);
                                // console.log("formattedStartTime:"+formattedStartTime)

                                let getEndTime = `\${res.data.endTime}`;
                                let endTime = new Date(getEndTime);

                                // // 檢查 endTime 是否為午夜 00:00:00
                                // if (endTime.getHours() !== 0 || endTime.getMinutes() !== 0 || endTime.getSeconds() !== 0) {
                                //     // 將日期減少一天
                                //     endTime.setDate(endTime.getDate() + 1);
                                // }

                                let formattedEndTime;
                                if (getEndTime.substr(-8) != "00:00:00") {
                                    formattedEndTime = getEndTime.substr(0, 10);
                                } else {
                                    formattedEndTime = endTime.toISOString().substr(0, 10);
                                }

                                // console.log("formattedEndTime:"+formattedEndTime)

                                let releasedId = `\${res.data.releasedId}`;
                                $("#param").val(releasedId);

                                let cardHtml = "";

                                if (res.data.type == 2) {
                                    let releasedId = `\${res.data.releasedId}`;
                                    $("#paramAuction").val(releasedId);

                                    cardHtml += `<div class="newCardContainer container text-center"><div class="row">
                                            <div class="newCardImgDiv col-6">
                                            <img src="${root}/card/downloadCard/\${res.data.cardId}" class="newCardImg"></div><div class="col-6">
                                            <div class="newCardTitle" style="margin: 10px">\${res.data.cardName}</div>
                                            <div id="countdownEditMy" style="font-size: 14px; height: 19px;"></div>
                                            <div class="cardDetail row"><div class="col-3 detailLeft">`
                                    if (res.data.directPrice != null) {
                                        cardHtml += `目前出價<br>最高出價者<br>直購價<br>上架時間<br>結束時間`;
                                    } else {
                                        cardHtml += `目前出價<br>最高出價者<br>上架時間<br>結束時間`
                                    }

                                    cardHtml += `</div><div class="col-4">`

                                    if (res.data.purchasePrice != null) {
                                        cardHtml += `\${res.data.purchasePrice} <i class="fa-solid fa-coins"></i><br>
                                    \${res.data.purchaserName} <i class="fa-solid fa-user"></i><br>`
                                    } else {
                                        cardHtml += `\${res.data.startPrice} <i class="fa-solid fa-coins"></i><br>
                                    尚未有人出價<br>`
                                    }
                                    if (res.data.directPrice != null) {
                                        cardHtml += `\${res.data.directPrice} <i class="fa-solid fa-coins"></i><br>`
                                    }
                                    cardHtml += `\${formattedStartTime} <i class="fa-regular fa-clock"></i><br>
                                            \${formattedEndTime} <i class="fa-regular fa-clock"></i><br>
                                            </div></div></div></div></div>`

                                    let intervalId = setInterval(() => {
                                        let now = new Date().getTime();
                                        endTime = new Date(getEndTime);
                                        let distance = endTime - now;

                                        let days = Math.floor(distance / (1000 * 60 * 60 * 24));
                                        let hours = Math.floor(
                                            (distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
                                        );
                                        let minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
                                        let seconds = Math.floor((distance % (1000 * 60)) / 1000);

                                        let countdownEl = document.getElementById("countdownEditMy");
                                        countdownEl.innerHTML = `\${days}天\${hours}小時\${minutes}分\${seconds}秒 結束`;
                                    }, 1000);
                                    if (res.data.purchasePrice == null) {
                                        Swal.fire({
                                            title: '',
                                            html: cardHtml,
                                            showDenyButton: true,
                                            showCancelButton: false,
                                            confirmButtonText: '下架卡片',
                                            denyButtonText: `編輯上架資訊`,
                                            confirmButtonColor: '#dc7e6a',
                                            denyButtonColor: '#647168',
                                            background: '#dfa661',
                                            customClass: 'editAlert',
                                            willClose: () => {
                                                clearInterval(intervalId);
                                            }
                                        }).then((result) => {
                                            if (result.isConfirmed) {
                                                axios.post("${root}/released/discontinued?releasedId=" + res.data.releasedId + "&ownedId=" + res.data.ownedId)
                                                    .then(res => {
                                                        Swal.fire({
                                                            title: '下架成功',
                                                            showDenyButton: true,
                                                            showCancelButton: false,
                                                            confirmButtonText: '回卡片市集',
                                                            denyButtonText: `回我的卡片`,
                                                            confirmButtonColor: '#dc7e6a',
                                                            denyButtonColor: '#da9255',
                                                            customClass: 'endAlert'
                                                        }).then((result) => {
                                                            if (result.isConfirmed) {
                                                                window.location.href = "${root}/card/releasedList"
                                                            } else if (result.isDenied) {
                                                                window.location.href = `${root}/card/mycard/\${memberId}`
                                                            }
                                                        })
                                                        console.log(res)
                                                    })
                                                    .catch(err => {
                                                        console.error(err);
                                                    })
                                            } else if (result.isDenied) {
                                                $('.forAuctionEdit').submit();
                                            }
                                        })
                                    } else {
                                        Swal.fire({
                                            title: '',
                                            html: cardHtml,
                                            showCancelButton: false,
                                            confirmButtonText: '提前結標',
                                            confirmButtonColor: '#dc7e6a',
                                            background: '#dfa661',
                                            customClass: 'editAlert',
                                            willClose: () => {
                                                clearInterval(intervalId);
                                            }
                                        }).then((result) => {
                                            console.log("???")
                                            if (result.isConfirmed) {
                                                Swal.fire({
                                                    title: `確定要提前結標？`,
                                                    showCancelButton: true,
                                                    confirmButtonText: '<i class="fa-regular fa-circle-check"></i> 確定',
                                                    cancelButtonText: '<i class="fa-regular fa-circle-xmark"></i> 取消',
                                                    confirmButtonColor: '#CA7159',
                                                    cancelButtonColor: '#CBC0AA',
                                                    customClass: 'confirmAlert',
                                                    reverseButtons: true
                                                }).then((result) => {
                                                    if (result.isConfirmed) {
                                                        axios.post("${root}/released/stopAuction?releasedId=" + res.data.releasedId + "&ownedId=" + res.data.ownedId + "&price=" + res.data.purchasePrice)

                                                        $(".myCoin").empty();
                                                        $(".myCoin").append(memberCoin);
                                                        window.location.href = "${root}/card/releasedList"
                                                    }
                                                })
                                            }
                                        })
                                    }
                                } else {
                                    let releasedId = `\${res.data.releasedId}`;
                                    $("#paramDirect").val(releasedId);

                                    cardHtml += `<div class="newCardContainer container text-center"><div class="row">
                                            <div class="newCardImgDiv col-6">
                                            <img src="${root}/card/downloadCard/\${res.data.cardId}" class="newCardImg"></div><div class="col-6">
                                            <div class="newCardTitle" style="margin: 10px">\${res.data.cardName}</div>
                                            <div id="countdownEditMy" style="font-size: 14px; height: 19px;"></div>
                                            <div class="cardDetail row"><div class="col-3 detailLeft">`
                                    if (res.data.directPrice != null) {
                                        cardHtml += `直購價<br>上架時間<br>結束時間`;
                                    }

                                    cardHtml += `</div><div class="col-4">`


                                    if (res.data.directPrice != null) {
                                        cardHtml += `\${res.data.directPrice} <i class="fa-solid fa-coins"></i><br>`
                                    }
                                    cardHtml += `\${formattedStartTime} <i class="fa-regular fa-clock"></i><br>
                                            \${formattedEndTime} <i class="fa-regular fa-clock"></i><br>
                                            </div></div></div></div></div>`

                                    Swal.fire({
                                        title: '',
                                        html: cardHtml,
                                        showDenyButton: true,
                                        showCancelButton: false,
                                        confirmButtonText: '下架卡片',
                                        denyButtonText: `編輯上架資訊`,
                                        confirmButtonColor: '#dc7e6a',
                                        denyButtonColor: '#647168',
                                        background: '#dfa661',
                                        customClass: 'editAlert'
                                    }).then((result) => {
                                        if (result.isConfirmed) {
                                            axios.post("${root}/released/discontinued?releasedId=" + res.data.releasedId + "&ownedId=" + res.data.ownedId)
                                                .then(res => {
                                                    Swal.fire({
                                                        title: '下架成功',
                                                        showDenyButton: true,
                                                        showCancelButton: false,
                                                        confirmButtonText: '回卡片市集',
                                                        denyButtonText: `回我的卡片`,
                                                        confirmButtonColor: '#dc7e6a',
                                                        denyButtonColor: '#da9255',
                                                        customClass: 'endAlert'
                                                    }).then((result) => {
                                                        if (result.isConfirmed) {
                                                            window.location.href = "${root}/card/releasedList"
                                                        } else if (result.isDenied) {
                                                            window.location.href = `${root}/card/mycard/\${memberId}`
                                                        }
                                                    })
                                                    console.log(res)
                                                })
                                                .catch(err => {
                                                    console.error(err);
                                                })
                                        } else if (result.isDenied) {
                                            $('.forEdit').submit();
                                        }
                                    })
                                }
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