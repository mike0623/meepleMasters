<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
			<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
				<%@ include file="../include/common_link.jsp" %>
					<%@ page import="java.util.Date" %>
						<%@ page import="java.util.Calendar" %>

							<!DOCTYPE html>
							<html>

							<head>
								<meta charset="UTF-8">
								<title>${webName}</title>

								<style>
									.container {
										display: flex;
										justify-content: center;
										align-items: center;
										height: 100vh;
									}

									.image-area {
										flex: 1;
										padding-right: 20px;
									}

									.image-area img {
										width: 100%;
										height: auto;
									}

									.selection-area {
										flex: 1;
										padding-right: 20px;
									}

									.quick-booking-buttons {
										display: flex;
										flex-wrap: wrap;
										margin-bottom: 20px;
									}

									.quick-booking-button {
										background-color: #3e2723;
										border: none;
										color: white;
										padding: 10px;
										margin-right: 10px;
										margin-bottom: 10px;
										cursor: pointer;
									}

									.date-time-area {
										flex: 1;
									}

									.date-time-area label {
										display: block;
										margin-bottom: 10px;
									}

									.date-time-area select {
										width: 100%;
										padding: 10px;
										margin-bottom: 10px;
									}

									.submit-button {
										float: right;
										background-color: #3e2723;
										border: none;
										color: white;
										padding: 15px 30px;
										font-size: 16px;
										cursor: pointer;
										border-radius: 4px;
									}
								</style>

							</head>

							<body>
								<jsp:include page="../include/header.jsp"></jsp:include>

								<% // 宣告需要用到的變數 
								Date currentDate = new Date();
								java.util.Calendar calendar = java.util.Calendar.getInstance();
								calendar.setTime(currentDate);
								calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
								Date minDate = calendar.getTime();
								calendar.add(java.util.Calendar.DAY_OF_MONTH, 6);
								Date maxDate = calendar.getTime();
									%>

									<div class="container">
										<div class="image-area">
											<img alt src="/meeple-masters/img/lu/MeepleMasterStore.png">
										</div>
										<div class="selection-area">
											<p>店名: MeepleMaster</p>
											<p>地址: 台北市大安區復興南路一段390號2樓</p>
											<p>連絡電話: (02)2345-6789</p>
											<form action="${root}/booking/continue">
												<label for="date">訂位日期:</label>
												<input type="date" id="date" name="date" min="<%= minDate %>"
													max="<%= maxDate %>" onchange="handleDateChange(this.value)">
												<label for="time">訂位時段:</label>
												<select id="time" name="time">
													<option value="" disabled selected>選擇時段</option>
													<option value="早上" ${isMorningBooked ? 'disabled' : '' }>早上</option>
													<option value="中午" ${isNoonBooked ? 'disabled' : '' }>中午</option>
													<option value="晚上" ${isEveningBooked ? 'disabled' : '' }>晚上</option>
												</select><br><br>
												<input type="button" value="繼續(選擇座位)" onclick="submitForm()">
											</form>
											<br><br>





										<!-- <div class="quick-booking-buttons">
										    <c:forEach items="${dates}" var="formattedDate">
										        <form action="${root}/booking/continue" method="get">
										            <button type="submit" name="date" value="${formattedDate}" class="quick-booking-button">
										                ${formattedDate} - 早上
										            </button>
													<input type="hidden" name="time" value="早上">
										        </form>
										        <form action="${root}/booking/continue" method="get">
										            <button type="submit" name="date" value="${formattedDate}" class="quick-booking-button">
										                ${formattedDate} - 中午
										            </button>
													<input type="hidden" name="time" value="中午">
										        </form>
										        <form action="${root}/booking/continue" method="get">
										            <button type="submit" name="date" value="${formattedDate}" class="quick-booking-button">
										                ${formattedDate} - 晚上
										            </button>
										            <input type="hidden" name="time" value="晚上">
										        </form>
										    </c:forEach>
										</div> -->


										</div>
									</div>
									<jsp:include page="../include/footer.jsp"></jsp:include>
								
									<script>
										function submitForm() {
											document.forms[0].submit();
										}

										function handleDateChange(dateValue) {
										    var timeSelect = document.getElementById('time');
										    var selectedTime = timeSelect.options[timeSelect.selectedIndex].value;

										    var xhr = new XMLHttpRequest();
										    xhr.open('GET', '${root}/checkAvailability?date=' + dateValue + '&time=' + selectedTime);
										    xhr.onload = function () {
										        if (xhr.status === 200) {
										            var response = JSON.parse(xhr.responseText);
										            renderAvailability(response);
										        }
										    };
										    xhr.send();
										}

										function renderAvailability(response) {
										    var timeSelect = document.getElementById('time');
										    // var selectedTime = timeSelect.options[timeSelect.selectedIndex].value;
											if(response.morning>=5){
												timeSelect.querySelector("option[value=早上]").disabled=true;
											} else {
										    // 該時段仍可選擇，將選項設為可點擊
												timeSelect.querySelector("option[value=早上]").disabled=false;
										    }

											if(response.afternoon>=5){
												timeSelect.querySelector("option[value=中午]").disabled=true;
											} else {
										    // 該時段仍可選擇，將選項設為可點擊
												timeSelect.querySelector("option[value=中午]").disabled=false;
										    }

											if(response.night>=5){
												timeSelect.querySelector("option[value=晚上]").disabled=true;
											} else {
										    // 該時段仍可選擇，將選項設為可點擊
												timeSelect.querySelector("option[value=晚上]").disabled=false;
										       
										    }
										}
										
										

										
									</script>
									
							</body>

							</html>