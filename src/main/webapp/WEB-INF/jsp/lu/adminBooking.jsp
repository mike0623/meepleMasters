<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ include file="../include/common_link.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>${adminWebName}</title>
</head>

<body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">

        <!-- ######## include ######## -->
        <jsp:include page="../include/admin/adminSidebar.jsp"></jsp:include>

        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">

                <!-- ######## include ######## -->
                <jsp:include page="../include/admin/adminNavbar.jsp"></jsp:include>

                <!-- 參考內容開始，之後會拿掉 -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <div class="d-sm-flex align-items-center justify-content-between mb-4">
                        <h1 class="h3 mb-0 text-gray-800">預約管理</h1>
                    </div>

                </div>
                <!-- /.container-fluid -->

                <!-- 表格參考 -->
                <!-- Begin Page Content -->
                <div class="container-fluid">


                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">預定列表</h6>
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
                                            <th>刪除預定</th>
                                            <th>更新預定</th>
                                        </tr>
                                    </thead>

                                    <c:forEach items="${bookingManagement}" var="booking">
                                    <tbody>
                                        <tr>
                                            <td>
                                                <c:set var="isMatched" value="false" />
												<c:forEach var="member" items="${members}">
												  <c:if test="${member.memberId == booking.bookMemberId}">
													<c:if test="${not isMatched}">
													  ${member.memberName}
													  <c:set var="isMatched" value="true" />
													</c:if>
												  </c:if>
												</c:forEach>
                                            </td>
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
                                            	<button class="deleteButton btn btn-danger btn-circle" value="${booking.bookId}">
                                            		<i class='fas fa-trash'></i>
                                            	</button>
                                            </td>
                                            <td>
                                            	<button class="updateButton btn btn-info btn-circle" value="${booking.bookId}">
                                            		<i class='fas fa-info-circle'></i>
                                            	</button>
			                                    </a>
                                            </td>
                                        </tr>
                                    </tbody>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </div>

                </div>


                <!-- 參考內容結束 -->

            </div>

            <!-- ######## include ######## -->
            <jsp:include page="../include/admin/adminFooter.jsp"></jsp:include>

        </div>

    </div>

    <!-- 回到最頂的按鈕，有需要可加 -->
    <a class="scroll-to-top rounded" href="#page-top">
        <i class="fa-solid fa-angle-up" style="line-height: 46px;"></i>
    </a>

    <!-- 套版提供的Logout Modal，不需要可刪 -->
    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                    <a class="btn btn-primary" href="${root}/member/logout">Logout</a>
                </div>
            </div>
        </div>
    </div>

	<script>
	
		let mId = "${member.memberId}";
		
		
		
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

    $('.updateButton').click(function() {
    var bookId = $(this).val();

    $.ajax({
            type: "GET",
            url: "${root}/booking/updateForm/" + bookId,
            success: function (response) {

                console.log(response);
                document.querySelector("html").innerHTML=response;
                // 將時間格式轉換為符合<input type="date">的格式（YYYY-MM-DD）
                var bookDate = new Date(response.bookingUpdate.bookDate);
                var formattedDate = bookDate.toISOString().split("T")[0];

                // 將格式化後的時間設定為輸入框的值
                $("#bookDate").val(formattedDate);
            },
            error: function () {
                alert("Failed to load update form!");
      	    }
   		 });
  	});
	
  

	</script>
</body>

</html>