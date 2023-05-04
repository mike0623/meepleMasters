<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ page import="java.time.LocalDate" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>座位選擇</title>

<style>

/*	.table-btn {
		background-color: #3e2723;
		border: none;
		color: white;
	    padding: 15px 32px;
	    text-align: center;
	    text-decoration: none;
	    display: inline-block;
	    font-size: 16px;
	    margin: 4px 2px;
	    cursor: pointer;
	    border-radius: 4px;
}		
*/
	.table-btn.clicked {
  		background-color: #ccc; /* 將按鈕背景色更改為淺色 */
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

<%
    // 讀取存放在 session 中的日期和時段
    String selectedDate = (String) session.getAttribute("selectedDate");
    String selectedTime = (String) session.getAttribute("selectedTime");
%>

<!-- 在頁面上顯示選擇的日期和時段 -->
<p>日期：<%= selectedDate %></p>
<p>時段：<%= selectedTime %></p>


<div class="container">
 
	


	<form action="${root}/booking/display">
	  
		<label>請選擇座位</label><br><br><br><br>
		
			<input type="hidden" name="selectedDate" value="<%= selectedDate %>">
		
  			<input type="hidden" name="selectedTime" value="<%= selectedTime %>">		
		<!--
		<input type="hidden" id="selectedTable" name="selectedTable" value="">
		<button type="button" class="table-btn" name="type1" value="2人桌(1號)" onclick="selectTable('2人桌(1號)')">2人桌(1號)</button>
		<button type="button" class="table-btn" name="type3" value="4人桌(3號)" onclick="selectTable('4人桌(3號)')">4人桌(3號)</button><br><br>
		<button type="button" class="table-btn" name="type2" value="2人桌(2號)" onclick="selectTable('2人桌(2號)')">2人桌(2號)</button>
		<button type="button" class="table-btn" name="type4" value="4人桌(4號)" onclick="selectTable('4人桌(4號)')">4人桌(4號)</button>
		<button type="button" class="table-btn" name="tableNumber" value="5人桌(5號)" onclick="selectTable('5人桌(5號)')">5人桌(5號)</button><br><br>
		-->
		
		
		<button type="button" class="table-btn" name="tableId" value="1">2人桌(1號)</button>
		
		<button type="button" class="table-btn" name="" value="3">4人桌(3號)</button><br><br>
		
		<button type="button" class="table-btn" name="" value="2">2人桌(2號)</button>
		
		<button type="button" class="table-btn" name="" value="4">4人桌(4號)</button>
		
		<button type="button" class="table-btn" name="" value="5">5人桌(5號)</button><br><br>
		
 
		<br><br>
	  <input type="button" value="繼續" onclick="submitForm()">
	</form>
	

	<script>


    	
    	function submitForm() {
    		  const clickedButton = document.querySelector('.table-btn.clicked');
    		  if (clickedButton) {
    		    const tableNumberInput = document.createElement('input'); // 創建一個input元素來儲存所點擊的桌號
    		    tableNumberInput.type = 'hidden';
    		    tableNumberInput.name = 'deskId';
    		    tableNumberInput.value = clickedButton.value;
    		    console.log('666666',clickedButton.value)
    		    document.forms[0].appendChild(tableNumberInput);
    		  }
    		  document.forms[0].submit(); // 提交表單
    		}

    		// 將每個按鈕加上click事件，當點擊時將其樣式更改
    		const buttons = document.querySelectorAll('.table-btn');
    		for (let i = 0; i < buttons.length; i++) {
    		  buttons[i].addEventListener('click', function() {
    		    const clickedButton = document.querySelector('.table-btn.clicked');
    		    if (clickedButton) {
    		      clickedButton.classList.remove('clicked');
    		    }
    		    this.classList.add('clicked');
    		  });
    		}
		
</script>


</div>
</body>
</html>

