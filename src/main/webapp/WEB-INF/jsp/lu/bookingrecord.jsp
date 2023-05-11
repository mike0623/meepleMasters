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
	    <p>您已成功訂位，以下是訂位資訊：</p><br/><br/>
		 <c:forEach items="${bookingrecord}" var="booking">
		   
		  <div>${booking.bookTime}</div>
		  <div></div>
		   
		 </c:forEach>
	    
        <input type="hidden" name="Date" value="">

        <input type="hidden" name="tableId" value="">
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