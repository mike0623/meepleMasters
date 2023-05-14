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
    <link rel="stylesheet" type="text/css" href="${root}/css/card/newCardRelease.css">
</head>

<body>
    <jsp:include page="../include/header.jsp"></jsp:include>
    <div class="backButton"></div>
    <main>
        <figure>
            <picture>
                <img src="${root}/img/logo.png" alt="Citymap illustration" class="newReleaseIcon" id="releaseCardImg" />
            </picture>
        </figure>

        <form action="${root}/released/insertCardDirect" method="post" id="formConfirm">

            <span class="headline">
                <i class="fa-solid fa-arrow-left-long" onclick="location.href='${root}/card/releasedList'"></i>
                <h2 class="text-headline">卡片上架</h2>
            </span>
            <span class="form-group mb-3" style="position: relative; top: -20;">
                <label for="ownedId" class="text-small-uppercase form-label" style="width: 100%;">我的卡片
                    <select class="text-body form-control form-select" id="ownedId" name="ownedId" type="text"
                        style="margin-top: 5px;" path="fkOwnedId" required>
                        <option id="notSelect" value="">請選擇卡片</option>
                    </select>
                </label>
            </span>
            <span class="form-group mb-3">
                <label for="price" class="text-small-uppercase form-label">售出價</label>
                <input class="text-body" id="price" name="price" type="text"
                    onkeyup="if(event.keyCode !=37 && event.keyCode != 39)value=value.replace(/\D/g,'')"
                    path="directPrice" required />
            </span>
            <span class="form-group mb-3">
                <label for="endTime" class="text-small-uppercase form-label">結束時間（至選擇日期的23:59截止）</label>
                <input class="text-body" id="endTime" name="endTime" type="text" path="endTime" required />
            </span>
            <div class="wrapper form-group mb-3">
                <input type="radio" name="select" id="option-1" checked>
                <label for="option-1" class="option option-1 form-label">
                    <div class="dot"></div>
                    <span>直接購買</span>
                </label>
            </div>
            <input class="inputSubmit" value="上架" type="submit">
        </form>
    </main>
    <jsp:include page="../include/footer.jsp"></jsp:include>
    <script>

        let card = [];
        let member = "${member}"

        $.get("${root}/released/ownedCard", function (data) {

            function compare(a, b) {
                if (a.id < b.id) {
                    return -1;
                }
                if (a.id > b.id) {
                    return 1;
                }
                return 0;
            }

            function compare(a, b) {
                if (a.fkCardId < b.fkCardId) {
                    return -1;
                }
                if (a.fkCardId > b.fkCardId) {
                    return 1;
                }
                return 0;
            }
            (data.cardList).sort(compare)
            let cardOwnedId = []

            for (i = 0; i < data.cardList.length; i++) {
                card.push(data.cardList[i].fkCardId);
                cardOwnedId.push(data.cardList[i].ownedId)
            }
            // console.log("cardOwnedId: " + cardOwnedId);

            let cardIdArray = []

            for (i = 0; i < card.length; i++) {
                let cardid = card[i];
                // console.log("cardid: " + cardid);
                cardIdArray.push($.get(`${root}/released/getCard/\${cardid}`));
            }
            // console.log(cardIdArray)

            let times = 0;

            Promise.all(cardIdArray).then(function (results) {
                results.forEach(function (cardData) {
                    // console.log("cardData: " + cardData);

                    let cardStar = cardData.card.cardStar;
                    let star = "☆";

                    if (cardStar == 1) {
                        star = "☆";
                    } else if (cardStar == 2) {
                        star = "☆☆";
                    } else if (cardStar == 3) {
                        star = "☆☆☆";
                    } else if (cardStar == 4) {
                        star = "☆☆☆☆";
                    } else if (cardStar == 5) {
                        star = "☆☆☆☆☆";
                    }

                    $("#ownedId").append(`<option value="\${cardOwnedId[times]}" label="\${cardData.card.cardId}">\${star} \${cardData.card.cardName}</option>`);

                    times += 1;
                });
            });
        })

        $("#ownedId").change(function () {
            $("#ownedId option:selected").each(function () {
                let id = $(this).attr('label')
                console.log(id)
                let imageUrl = `${root}/card/downloadCard/\${id}`;
                $("#releaseCardImg").removeClass("newReleaseIcon");
                $("#releaseCardImg").attr("src", imageUrl);
            })

            let id = $(this).find("option:selected").attr("id");
            if (id == "notSelect") {
                $("#releaseCardImg").addClass("newReleaseIcon");
                $("#releaseCardImg").attr("src", "${root}/img/logo.png");
            }
        })


        $("#endTime").datepicker({
            changeMonth: true,
            changeYear: true,
            dateFormat: 'yy-mm-dd',
            minDate: 0
        });

        $("#endTime").on("change", function () {
            let selectedDate = $("#endTime").datepicker("getDate");
            if (selectedDate != "") {
                $(this).parent().addClass("inputs--filled");
            }
        });

        var inputs = document.querySelectorAll('input[type=text], input[type=email]');
        for (i = 0; i < inputs.length; i++) {
            var inputEl = inputs[i];
            if (inputEl.value.trim() !== '') {
                inputEl.parentNode.classList.add('input--filled');
            }
            inputEl.addEventListener('focus', onFocus);
            inputEl.addEventListener('blur', onBlur);
        }

        function onFocus(ev) {
            ev.target.parentNode.classList.add('inputs--filled');
        }

        function onBlur(ev) {
            if (ev.target.value.trim() === '') {
                ev.target.parentNode.classList.remove('inputs--filled');
            } else if (ev.target.checkValidity() == false) {
                ev.target.parentNode.classList.add('inputs--invalid');
                ev.target.addEventListener('input', liveValidation);
            } else if (ev.target.checkValidity() == true) {
                ev.target.parentNode.classList.remove('inputs--invalid');
                ev.target.addEventListener('input', liveValidation);
            }
        }

        function liveValidation(ev) {
            if (ev.target.checkValidity() == false) {
                ev.target.parentNode.classList.add('inputs--invalid');
            } else {
                ev.target.parentNode.classList.remove('inputs--invalid');
            }
        }

        $(".inputSubmit").click(function (e) {
            e.preventDefault();
            Swal.fire({
                title: '確定要上架嗎？',
                showCancelButton: true,
                confirmButtonText: '<i class="fa-regular fa-circle-check"></i> 確定',
                cancelButtonText: '<i class="fa-regular fa-circle-xmark"></i> 取消',
                confirmButtonColor: '#CA7159',
                cancelButtonColor: '#CBC0AA',
                customClass: 'confirmAlert',
                reverseButtons: true
            }).then((result) => {
                if (result.isConfirmed) {
                    if (member == "") {
                        window.location.href = "${root}/login";
                    } else {
                        // 觸發表單驗證機制
                        document.getElementById('formConfirm').reportValidity();
                        // 如果表單驗證通過，就提交表單
                        if (document.getElementById('formConfirm').checkValidity()) {
                            document.getElementById('formConfirm').submit();
                        }
                    }
                }

            })
        });


    </script>
</body>

</html>