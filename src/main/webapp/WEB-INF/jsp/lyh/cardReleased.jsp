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
                        <a class="nav-link active nav-all" data-toggle="pill" href="#allCard">所有上架卡片</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link nav-my" data-toggle="pill" href="#myCard">我的上架卡片</a>
                    </li>
                </ul>


                <!-- Tab panes -->
                <div class="tab-content">
                    <div class="btn-group row justify-content-end">
                        <button type="button" class="btn btn-outline-warning dropdown-toggle d-none"
                            data-bs-toggle="dropdown" aria-expanded="false">
                            上架排序
                        </button>
                        <ul class="dropdown-menu">
                            <li class="dropdown-item" id="newList">最新上架</li>
                            <li class="dropdown-item" id="endTimeList">下架時間</li>
                            <li class="dropdown-item" id="priceDESCList">價格　
                                <i class="fa-solid fa-arrow-down-wide-short"></i>
                            </li>
                            <li class="dropdown-item" id="priceASCList">價格　
                                <i class="fa-solid fa-arrow-up-short-wide"></i>
                            </li>
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
        <form action="${root}/editRelease" method="get" id="forEdit">
            <input type="hidden" name="releasedId" id="param" hidden>
            <input type="submit" value="Submit" hidden>
        </form>
    </div>


    <jsp:include page="../include/footer.jsp"></jsp:include>

    <script>

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

        $("#priceDESCList").click(function () {
            status = 5;
            getAllList();
            getMyList();
        })

        $("#priceASCList").click(function () {
            status = 6;
            getAllList();
            getMyList();
        })

        const today = new Date();
        let getEndTime;
        let endTime;
        let times = 0;
        let diffInMs;
        let diffInDays;

        function getAllList() {
            console.log("製作畫面");
            return axios.get("${root}/released/all/" + status)
                .then(res => {
                    let htmlstr = "";

                    let memberId = "${member.memberId}"
                    let memberCoin = "${member.memberCoin}"
                    let count = 0;

                    console.log(res)


                    for (i = 0; i < res.data.length; i++) {
                        getEndTime = `\${res.data[i].endTime}`;
                        endTime = new Date(getEndTime);
                        diffInMs = endTime - today;
                        diffInDays = diffInMs / (1000 * 60 * 60 * 24);

                        if (diffInDays < 0) {
                            axios.post("${root}/released/discontinued?releasedId=" + res.data[i].releasedId + "&ownedId=" + res.data[i].ownedId)
                        }

                        if (diffInDays <= 1) {
                            if (res.data[i].memberId == memberId) {
                                htmlstr += `<div class="col-3 d-flex cardEach"><div class="card">`;
                                htmlstr += `<figure><img alt="" src="${root}/card/downloadCard/\${res.data[i].cardId}" class="hanafuda">`;
                                htmlstr += `<div class="releaseDetail" id="\${res.data[i].releasedId}">\${res.data[i].cardName}<br>`;
                                htmlstr += `\${res.data[i].directPrice} <i class="fa-solid fa-coins"></i><br><i class="fa-regular fa-pen-to-square edit"></i></div>`;
                                htmlstr += `<img src="${root}/img/lyh/hourglass.png" class="expire"></figure></div></div>`;
                                count += 1;
                            } else {
                                htmlstr += `<div class="col-3 d-flex cardEach"><div class="card">`;
                                htmlstr += `<figure id="\${res.data[i].releasedId}"><img alt="" src="${root}/card/downloadCard/\${res.data[i].cardId}" class="hanafuda">`;
                                htmlstr += `<div class="releaseDetail">\${res.data[i].cardName}<br>`;
                                htmlstr += `\${res.data[i].directPrice} <i class="fa-solid fa-coins"></i></div>`;
                                htmlstr += `<img src="${root}/img/lyh/shopping.png" class="shopping"/><img src="${root}/img/lyh/hourglass.png" class="expire"></figure></div></div>`;
                                count += 1;
                            }
                        } else {
                            if (res.data[i].memberId == memberId) {
                                htmlstr += `<div class="col-3 d-flex cardEach"><div class="card">`;
                                htmlstr += `<figure><img alt="" src="${root}/card/downloadCard/\${res.data[i].cardId}" class="hanafuda">`;
                                htmlstr += `<div class="releaseDetail" id="\${res.data[i].releasedId}">\${res.data[i].cardName}<br>`;
                                htmlstr += `\${res.data[i].directPrice} <i class="fa-solid fa-coins"></i><br><i class="fa-regular fa-pen-to-square edit"></i></div>`;
                                htmlstr += `</figure></div></div>`;
                                count += 1;
                            } else {
                                htmlstr += `<div class="col-3 d-flex cardEach"><div class="card">`;
                                htmlstr += `<figure id="\${res.data[i].releasedId}"><img alt="" src="${root}/card/downloadCard/\${res.data[i].cardId}" class="hanafuda">`;
                                htmlstr += `<div class="releaseDetail">\${res.data[i].cardName}<br>`;
                                htmlstr += `\${res.data[i].directPrice} <i class="fa-solid fa-coins"></i></div>`;
                                htmlstr += `<img src="${root}/img/lyh/shopping.png" class="shopping"/></figure></div></div>`;
                                count += 1;
                            }
                        }
                    }
                    if (count == 0) {
                        htmlstr = `<div style="height: 200px; line-height: 200px;">目前沒有上架中的卡片</div>`
                    }

                    if ($('.nav-all').hasClass('active')) {
                        console.log(1)
                        $(".dropdown-toggle").removeClass("d-none");
                    }
                    
                    $("#allCardContainer").empty();
                    $("#allCardContainer").append(htmlstr);

                    $(".edit").click(function () {
                        let id = $(this).parent().attr('id')

                        axios.get(`${root}/released/card/\${id}`)
                            .then(res => {

                                let getStartTime = `\${res.data[0].startTime}`;
                                let formattedStartTime = getStartTime.substr(0, 10);
                                // console.log("formattedStartTime:"+formattedStartTime)

                                let getEndTime = `\${res.data[0].endTime}`;
                                let endTime = new Date(getEndTime);
                                let formattedEndTime = endTime.toISOString().substr(0, 10);
                                // console.log("formattedEndTime:"+formattedEndTime)

                                let releasedId = `\${res.data[0].releasedId}`;
                                $("#param").val(releasedId);

                                let cardHtml = "";

                                cardHtml += `<div class="newCardContainer container text-center"><div class="row">
                                            <div class="newCardImgDiv col-6">
                                            <img src="${root}/card/downloadCard/\${res.data[0].cardId}" class="newCardImg"></div><div class="col-6">
                                            <div class="newCardTitle">\${res.data[0].cardName}</div>
                                            <div class="cardDetail row">
                                            <div class="col-2">
                                                價格<br>上架時間<br>結束時間
                                            </div>
                                            <div class="col-4">
                                                \${res.data[0].directPrice} <i class="fa-solid fa-coins"></i><br>
                                                \${formattedStartTime} <i class="fa-regular fa-clock"></i><br>
                                                \${formattedEndTime} <i class="fa-regular fa-clock"></i><br>
                                                </div>
                                            </div></div></div></div>`

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
                                    /* Read more about isConfirmed, isDenied below */
                                    if (result.isConfirmed) {
                                        axios.post("${root}/released/discontinued?releasedId=" + res.data[0].releasedId + "&ownedId=" + res.data[0].ownedId)
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
                                    } else if (result.isDenied) {
                                        $('#forEdit').submit();
                                    }
                                })

                            })
                            .catch(err => {
                                console.error(err);
                            })

                    })

                    $(".shopping").click(function () {
                        let id = $(this).parent().attr('id')

                        axios.get(`${root}/released/card/\${id}`)
                            .then(res => {

                                let getStartTime = `\${res.data[0].startTime}`;
                                let formattedStartTime = getStartTime.substr(0, 10);
                                // console.log("formattedStartTime:"+formattedStartTime)

                                let getEndTime = `\${res.data[0].endTime}`;
                                let endTime = new Date(getEndTime);
                                let formattedEndTime = endTime.toISOString().substr(0, 10);
                                // console.log("formattedEndTime:"+formattedEndTime)


                                let cardHtml = "";
                                cardHtml += `<div class="newCardContainer container text-center"><div class="row">
                                            <div class="newCardImgDiv col-6">
                                            <img src="${root}/card/downloadCard/\${res.data[0].cardId}" class="newCardImg"></div><div class="col-6">
                                            <div class="newCardTitle">\${res.data[0].cardName}</div>
                                            <div class="cardDetail row">
                                            <div class="col-2">
                                                價格<br>賣家<br>上架時間<br>結束時間<br>我的金幣
                                            </div>
                                            <div class="col-4">
                                                \${res.data[0].directPrice} <i class="fa-solid fa-coins"></i><br>
                                                \${res.data[0].memberName} <i class="fa-solid fa-user"></i><br>
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
                                                            Swal.fire({
                                                                title: '購買成功！',
                                                                showDenyButton: true,
                                                                showCancelButton: false,
                                                                confirmButtonText: '回卡片市集',
                                                                denyButtonText: `回我的卡片`,
                                                                icon: 'success',
                                                                confirmButtonColor: '#dc7e6a',
                                                                denyButtonColor: '#da9255',
                                                                customClass: 'endAlert'
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

                        if (diffInDays <= 1) {
                            if (res.data[i].memberId == memberId) {
                                htmlstr += `<div class="col-3 d-flex"><div class="card">`;
                                htmlstr += `<figure><img alt="" src="${root}/card/downloadCard/\${res.data[i].cardId}" class="hanafuda">`;
                                htmlstr += `<div class="releaseDetail" id="\${res.data[i].releasedId}">\${res.data[i].cardName}<br>`;
                                htmlstr += `\${res.data[i].directPrice} <i class="fa-solid fa-coins"></i><br><i class="fa-regular fa-pen-to-square edit"></i></div>`;
                                htmlstr += `<img src="${root}/img/lyh/hourglass.png" class="expire"></figure></div></div>`;
                                count += 1;
                            }
                        } else {
                            if (res.data[i].memberId == memberId) {
                                htmlstr += `<div class="col-3 d-flex"><div class="card">`;
                                htmlstr += `<figure><img alt="" src="${root}/card/downloadCard/\${res.data[i].cardId}" class="hanafuda">`;
                                htmlstr += `<div class="releaseDetail" id="\${res.data[i].releasedId}">\${res.data[i].cardName}<br>`;
                                htmlstr += `\${res.data[i].directPrice} <i class="fa-solid fa-coins"></i><br><i class="fa-regular fa-pen-to-square edit"></i></div>`;
                                htmlstr += `</figure></div></div>`;
                                count += 1;
                            }
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

                                let getStartTime = `\${res.data[0].startTime}`;
                                let formattedStartTime = getStartTime.substr(0, 10);
                                // console.log("formattedStartTime:"+formattedStartTime)

                                let getEndTime = `\${res.data[0].endTime}`;
                                let endTime = new Date(getEndTime);
                                let formattedEndTime = endTime.toISOString().substr(0, 10);
                                // console.log("formattedEndTime:"+formattedEndTime)

                                let releasedId = `\${res.data[0].releasedId}`;
                                $("#param").val(releasedId);

                                let cardHtml = "";

                                cardHtml += `<div class="newCardContainer container text-center"><div class="row">
                                            <div class="newCardImgDiv col-6">
                                            <img src="${root}/card/downloadCard/\${res.data[0].cardId}" class="newCardImg"></div><div class="col-6">
                                            <div class="newCardTitle">\${res.data[0].cardName}</div>
                                            <div class="cardDetail row">
                                            <div class="col-2">
                                                價格<br>上架時間<br>結束時間
                                            </div>
                                            <div class="col-4">
                                                \${res.data[0].directPrice} <i class="fa-solid fa-coins"></i><br>
                                                \${formattedStartTime} <i class="fa-regular fa-clock"></i><br>
                                                \${formattedEndTime} <i class="fa-regular fa-clock"></i><br>
                                                </div>
                                            </div></div></div></div>`

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
                                    /* Read more about isConfirmed, isDenied below */
                                    if (result.isConfirmed) {
                                        axios.post("${root}/released/discontinued?releasedId=" + res.data[0].releasedId + "&ownedId=" + res.data[0].ownedId)
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
                                    } else if (result.isDenied) {
                                        $('#forEdit').submit();
                                    }
                                })

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