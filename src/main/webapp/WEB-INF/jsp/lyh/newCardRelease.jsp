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
    <div class="newReleaseContainer"></div>
    <main>
        <figure>
            <picture>
                <img src="${root}/img/favicon.png" alt="Citymap illustration" class="newReleaseIcon"/>
            </picture>
        </figure>

        <form>
            <span class="headline">
                <h2 class="text-headline">卡片上架</h2>
            </span>
            <span class="form-group mb-3" style="position: relative; top: -20;">
                <label for="ownedId" class="text-small-uppercase form-label" style="width: 100%;">選擇卡片
                <select class="text-body form-control form-select" id="ownedId" name="ownedId" type="text" required>
                    
                </select>
            </label>
            </span>
            <span class="form-group mb-3">
                <label for="price" class="text-small-uppercase form-label">售出價</label>
                <input class="text-body" id="price" name="price" type="text" onkeyup="if(event.keyCode !=37 && event.keyCode != 39)value=value.replace(/\D/g,'')" required>
            </span>
            <span class="form-group mb-3">
                <label for="city" class="text-small-uppercase form-label">結束時間</label>
                <input class="text-body" id="city" name="city" type="text" required>
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
            <input class="text-small-uppercase" id="submit" type="submit" value="上架">
        </form>
    </main>
    <jsp:include page="../include/footer.jsp"></jsp:include>

    <script>

        let card = [];

        $.get("${root}/released/ownedCard", function(data){
            
            function compare(a, b) {
                if (a.id < b.id) {
                return -1;
                }
                if (a.id > b.id) {
                return 1;
                }
                return 0;
            }
            
            for (i = 0; i < data.cardList.length; i++) {
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
                card.push(data.cardList[i]);
            }

            for (i = 0; i < card.length; i++) {
                $.get(`${root}/released/getCard/\${card[i].fkCardId}`, function(cardData){
                        // console.log(`\${cardData.card}`)
                        $("#ownedId").append(`<option value="\${cardData.cardId}">\${cardData.cardName}</option>`)
                    })
            }

            
            console.log(card);
        })

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

        var submitBtn = document.querySelector('input[type=submit]');
        submitBtn.addEventListener('click', onSubmit);

        function onSubmit(ev) {
            var inputsWrappers = ev.target.parentNode.querySelectorAll('span');
            for (i = 0; i < inputsWrappers.length; i++) {
                input = inputsWrappers[i].querySelector('input[type=text], input[type=email]');
                if (input.checkValidity() == false) {
                    inputsWrappers[i].classList.add('inputs--invalid');
                } else if (input.checkValidity() == true) {
                    inputsWrappers[i].classList.remove('inputs--invalid');
                }
            }
        }
    </script>
</body>

</html>