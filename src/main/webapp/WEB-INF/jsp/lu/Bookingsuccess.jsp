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

<div class="container">
 
    <form action="/booking/calendar" method="post">
    	<label>訂位成功</label><br><br>
		
		<!-- 在頁面上顯示選擇的日期和時段 -->
		<p>日期 : ${selectedDate}</p>
		<p>時段 : ${selectedTime}</p>
		<p>桌號 : ${tableType.deskType}-${tableType.deskId}號</p>
        <input type="hidden" name="selectedDate" value="${selectedDate}">
		<input type="hidden" name="selectedTime" value="${selectedTime}">
		<input type="hidden" name="deskIdtype" value="${tableType.deskId}">
        <input type="submit" value="加入行事曆">
    </form>
    
    <form action="/booking/cancel" method="post">
        <input type="hidden" name="bookingId" value="">
        <input type="submit" value="取消訂位">
    </form>
</div>
<jsp:include page="../include/footer.jsp"></jsp:include>
	<script>
		let mId = "${member.memberId}";
		console.log(mId);

	</script>

</body>
</html>