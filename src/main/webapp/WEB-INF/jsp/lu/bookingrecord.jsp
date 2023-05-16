<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../include/common_link.jsp" %>
<%@ page import="java.time.LocalDate" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${webName}</title>

<style>



</style>

</head>

<body>
<jsp:include page="../include/header.jsp"></jsp:include>


<div class="container-fluid">

                    <!-- Page Heading -->
                    <div class="d-sm-flex align-items-center justify-content-between mb-4">
                        <h1 class="h3 mb-0 text-gray-800">訂位紀錄</h1>
                    </div>

                </div>
                <!-- /.container-fluid -->

                <!-- 表格參考 -->
                <!-- Begin Page Content -->
                <div class="container-fluid">


                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">訂位列表</h6>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th>訂位姓名</th>
                                            <th>日期</th>
                                            <th>時段</th>
                                            <th>桌種</th>
                                            <th>桌號</th>
                                            <th>加入行事曆</th>
                                            <th>取消訂位</th>
                                        </tr>
                                    </thead>

                                    <c:forEach items="${bookingrecord}" var="booking">
                                    <tbody>
                                        <tr>
                                            <td>${memberName}</td>
                                            <td><fmt:formatDate value="${booking.bookDate}" pattern="yyyy-MM-dd" /></td>
                                            <td>${booking.bookTime}</td>
											<td>
												<c:set var="isMatched" value="false" />
												<c:forEach var="desk" items="${desks}">
												  <c:if test="${desk.deskId == booking.bookDeskId}">
													<c:if test="${not isMatched}">
													  ${desk.deskType}
													  <c:set var="isMatched" value="true" />
													</c:if>
												  </c:if>
												</c:forEach>
											  </td>
                                            <td>${booking.bookDeskId}號桌</td>
                                            <td>
                                            	<a href="#" class="btn btn-primary btn-icon-split">
			                                        <span class="icon text-white-50">
			                                            <i class="fas fa-flag"></i>
			                                        </span>
			                                      
			                                    </a>
                                            </td>
                                            <td>
                                            	<button class="deleteButton btn btn-danger btn-circle" value="${booking.bookId}">
                                            		<i class='fas fa-trash'></i>
                                            	</button>
                                            </td>
                                        </tr>
                                    </tbody>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </div>

                </div>

<jsp:include page="../include/footer.jsp"></jsp:include>
	<script>
	
		let mId = "${member.memberId}";
		console.log(mId);
		
		
	$('.deleteButton').click(function() {
    var bookId = $(this).val();

    $.ajax({
      	url: '${root}/booking/delete/' + bookId,
      	type: 'DELETE',
      	success: function(response) {
        // 做一些事情，例如重新載入頁面或更新表格
      		location.reload();
      	},
      	error: function() {
        // 錯誤處理
      	}
   		 });
  		});
		
  

	</script>

</body>
</html>