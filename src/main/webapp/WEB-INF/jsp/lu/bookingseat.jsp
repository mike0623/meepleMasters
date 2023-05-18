<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
			<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
				<%@ include file="../include/common_link.jsp" %>
					<%@ page import="java.time.LocalDate" %>
						<!DOCTYPE html>
						<html>

						<head>
							<meta charset="UTF-8">
							<title>${webName}</title>
						
							<style>
							.table-btn {
        background-repeat: no-repeat;
        background-size: 200px 200px;
        background-position: center;
        border: none;
        cursor: pointer;
        width: 200px;
        height: 200px;
        outline: none;
        background-image: url(/meeple-masters/img/lu/meeting-room.png);

        
    }
							
								.table-btn.clicked {
									background-image: url(/meeple-masters/img/lu/meeting-room-click.png);
            /* 設定被點擊時的圖片 */
								}	
								
								.unshow {
										background-image: url(/meeple-masters/img/lu/meeting-room-unshow.png);
           							 /* 設定unshow狀態的圖片 */				
								}	
								
								.container {
									display: flex;
									justify-content: center;
									align-items: center;
									height: 100vh;
								}
							</style>

						</head>

						<body>
							<jsp:include page="../include/header.jsp"></jsp:include>

								<div class="container">
									
									<form action="${root}/booking/display">

										<label>請選擇座位</label><br><br><br><br>

										<input type="hidden" name="selectedDate" value="${selectedDate}">

										<input type="hidden" name="selectedTime" value="${selectedTime}">
										
										
										<c:set var="desk1" value="false" />
										<c:set var="desk2" value="false" />
										<c:set var="desk3" value="false" />
										<c:set var="desk4" value="false" />
										<c:set var="desk5" value="false" />
										
										<c:forEach items="${bookedDeskId}" var="Id">
										    <c:choose>
										        <c:when test="${Id == 1}">
										            <c:set var="desk1" value="true" />
										        </c:when>
										        <c:when test="${Id == 2}">
										            <c:set var="desk2" value="true" />
										        </c:when>
										        <c:when test="${Id == 3}">
										            <c:set var="desk3" value="true" />
										        </c:when>
										        <c:when test="${Id == 4}">
										            <c:set var="desk4" value="true" />
										        </c:when>
										        <c:when test="${Id == 5}">
										            <c:set var="desk5" value="true" />
										        </c:when>
										    </c:choose>
										</c:forEach>
										
										<button type="button" class="table-btn ${desk1 ? 'unshow' : ''}" name="tableId" value="1" ${desk1 ? 'disabled' : ''}>2人桌(1號)</button>
										<button type="button" class="table-btn ${desk2 ? 'unshow' : ''}" name="tableId" value="2" ${desk2 ? 'disabled' : ''}>2人桌(2號)</button>
										<button type="button" class="table-btn ${desk3 ? 'unshow' : ''}" name="tableId" value="3" ${desk3 ? 'disabled' : ''}>4人桌(3號)</button>
										<button type="button" class="table-btn ${desk4 ? 'unshow' : ''}" name="tableId" value="4" ${desk4 ? 'disabled' : ''}>4人桌(4號)</button>
										<button type="button" class="table-btn ${desk5 ? 'unshow' : ''}" name="tableId" value="5" ${desk5 ? 'disabled' : ''}>5人桌(5號)</button>


										<br><br>
										<input type="button" value="繼續" onclick="submitForm()">
									</form>
								</div>

								<jsp:include page="../include/footer.jsp"></jsp:include>

								<script>
									
									function submitForm() {
										const clickedButton = document.querySelector('.table-btn.clicked');
										if (clickedButton) {
											const tableNumberInput = document.createElement('input'); // 創建一個input元素來儲存所點擊的桌號
											tableNumberInput.type = 'hidden';
											tableNumberInput.name = 'deskId';
											tableNumberInput.value = clickedButton.value;
											console.log('666666', clickedButton.value)
											document.forms[0].appendChild(tableNumberInput);
										}
										document.forms[0].submit(); // 提交表單
									}

									// 將每個按鈕加上click事件，當點擊時將其樣式更改
									const buttons = document.querySelectorAll('.table-btn');
									for (let i = 0; i < buttons.length; i++) {
										buttons[i].addEventListener('click', function () {
											const clickedButton = document.querySelector('.table-btn.clicked');
											if (clickedButton) {
												clickedButton.classList.remove('clicked');
											}
											this.classList.add('clicked');
										});
									}
									let DId = "${bookedDeskId}";
									let TId = "${selectedTime}";
									console.log("值="+DId);console.log()
									console.log(TId)

								</script>


						</body>

						</html>