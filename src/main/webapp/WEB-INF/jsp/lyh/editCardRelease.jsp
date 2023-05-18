<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ include file="../include/common_link.jsp" %>
<% String releasedId = request.getParameter("releasedId"); %>
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

        <form action="${root}/released/edit" method="post" id="formConfirm">

            <span class="headline">
                <i class="fa-solid fa-arrow-left-long" onclick="location.href='${root}/card/releasedList'"></i>
                <h2 class="text-headline">卡片上架修改</h2>
            </span>
            <span class="form-group mb-3" style="position: relative; top: -20;">
                <label for="releasedId" class="text-small-uppercase form-label" style="width: 100%;">我的卡片
                    <select class="text-body form-control form-select" id="releasedId" name="releasedId" type="text"
                        style="margin-top: 5px;" path="releasedId" required>
                        <option id="notSelect" value="">請選擇卡片</option>
                    </select>
                </label>
            </span>
            <span class="form-group mb-3">
                <label for="directPrice" class="text-small-uppercase form-label">售出價</label>
                <input class="text-body" id="directPrice" name="directPrice" type="text"
                    onkeyup="if(event.keyCode !=37 && event.keyCode != 39)value=value.replace(/\D/g,'')"
                    path="directPrice" required />
            </span>
            <span class="form-group mb-3">
                <label for="endTime" class="text-small-uppercase form-label">結束時間（至選擇日期的23:59截止）</label>
                <input class="text-body" id="endTime" name="endTime" type="text" path="endTime" required />
            </span>
            <div class="wrapper form-group mb-3">
                <input type="radio" name="select" id="option-1" checked>
                <input type="radio" name="select" id="option-2">
                <label for="option-1" class="option option-1 form-label">
                    <div class="dot"></div>
                    <span>直接出價</span>
                </label>
                <!-- <label for="option-2" class="option option-2">
                    <div class="dot"></div>
                    <span>Teacher</span>
                </label> -->
            </div>
            <input class="inputSubmit" value="修改" type="submit">
        </form>
    </main>
    <jsp:include page="../include/footer.jsp"></jsp:include>
    <script>
        

        let card = [];
        const member = "${member}"

        const releasedId = '<%= request.getParameter("releasedId") %>';
        console.log("releasedId: " + releasedId);

        let price = "";
        let getTime = "";
        let cardName = "";
        let cardId = "";
        let endTime = "";
        let formattedEndTime = "";

        $.get(`${root}/released/card/\${releasedId}`, function (data) {

            // console.log(data)
            price = `\${data.directPrice}`;
            getTime = `\${data.endTime}`;
            cardName = `\${data.cardName}`;
            cardId = `\${data.cardId}`;
            endTime = new Date(getTime);
            formattedEndTime = endTime.toISOString().substr(0, 10);

            if (price != "") {
                $("#directPrice").val(`\${price}`);
                $("#directPrice").parent().addClass("inputs--filled");
            }

            if (formattedEndTime != "") {
                $("#endTime").val(`\${formattedEndTime}`);
                $("#endTime").parent().addClass("inputs--filled");
            }

            if (cardName != "") {
                $("#releasedId").append(`<option value="\${releasedId}" selected>\${cardName}</option>`);
                $("#releasedId").attr('disabled', true)
            }

            if (cardId != "") {
                let imageUrl = `${root}/card/downloadCard/\${cardId}`;
                $("#releaseCardImg").removeClass("newReleaseIcon");
                $("#releaseCardImg").attr("src", imageUrl);
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

        // var submitBtn = document.querySelector('input[type=submit]');
        // submitBtn.addEventListener('click', onSubmit);

        // function onSubmit(ev) {
        //     var inputsWrappers = ev.target.parentNode.querySelectorAll('span');
        //     for (i = 0; i < inputsWrappers.length; i++) {
        //         input = inputsWrappers[i].querySelector('input[type=text], input[type=email]');
        //         if (input.checkValidity() == false) {
        //             inputsWrappers[i].classList.add('inputs--invalid');
        //         } else if (input.checkValidity() == true) {
        //             inputsWrappers[i].classList.remove('inputs--invalid');
        //         }
        //     }
        // }

        $(".inputSubmit").click(function (e) {
            e.preventDefault();
            Swal.fire({
                title: '確定要修改嗎？',
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
                        $("#releasedId").attr('disabled', false)
                        document.getElementById('formConfirm').submit();
                    }
                }

            })
        });


    </script>
</body>

</html>