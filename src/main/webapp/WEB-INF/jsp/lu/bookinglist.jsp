<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../include/common_link.jsp" %>
<%@ page import="java.time.LocalDate" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${webName}</title>
<link rel="stylesheet" type="text/css" href="${root}/css/index.css">
<style>

	.table-btn {
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
    // 宣告需要用到的變數
    LocalDate currentDate = LocalDate.now();
    LocalDate minDate = currentDate.plusDays(1);
    LocalDate maxDate = currentDate.plusDays(31);
%>




<div class="container">




<form action="${root}/booking/continue">
  <label for="date">訂位日期:</label>
  <input type="date" id="date" name="date" min="<%= minDate %>" max="<%= maxDate %>"><br><br>
  
	  <label for="time">訂位時段:</label>
	  <select id="time" name="time">
	  	<option value="早上">早上</option>
	    <option value="中午">中午</option>
	    <option value="晚上">晚上</option>
	  </select><br><br>
	    
	  <input type="button" value="繼續(選擇座位)" onclick="submitForm()">
</form>
	
</div>
<jsp:include page="../include/footer.jsp"></jsp:include>
<script>
    function submitForm() {
        document.forms[0].submit();
    }
</script>


</body>
</html>

