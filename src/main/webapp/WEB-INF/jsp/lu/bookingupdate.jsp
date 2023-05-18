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

                                        <div class="container rounded mt-5 mb-5">
                                            <!-- Topbar Search -->
                                            <div
                                                class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 p-3 py-5">
                                                <div class="input-group">
                                                    <div class="input-group-append">
                                                        <button class="btn btn-warning" type="button"
                                                            style="border-radius: 5px">
                                                            <i class="fas fa-search fa-sm"></i>
                                                        </button>
                                                    </div>
                                                    <input type="text" class="form-control bg-light border-0 small"
                                                        placeholder="搜尋訂單" aria-label="Search"
                                                        aria-describedby="basic-addon2" id="searchMember"
                                                        style="width: 400px; border-radius: 5px">
                                                </div>

                                            </div>

                                            <form:form modelAttribute="bookingUpdate" action="${root}/booking/update"
                                                method="POST">
                                                <input type="hidden" name="bookId" value="${bookingUpdate.bookId}" />

                                                <div class="form-group">
                                                    <label for="bookMemberName">Member Name:</label>
                                                    <input type="text" id="bookMemberName" name="bookMemberName"
                                                        value="${memberName.memberName}" readonly />
                                                </div>



                                                <div class="form-group">
                                                    <label for="bookTime">Time:</label>
                                                    <select id="bookTime" name="bookTime" class="form-control">
                                                        <option value="早上" ${bookingUpdate.bookTime=='早上' ? 'selected'
                                                            : '' }>早上</option>
                                                        <option value="中午" ${bookingUpdate.bookTime=='中午' ? 'selected'
                                                            : '' }>中午</option>
                                                        <option value="晚上" ${bookingUpdate.bookTime=='晚上' ? 'selected'
                                                            : '' }>晚上</option>
                                                    </select>
                                                </div>

                                                <div class="form-group">
                                                    <label for="bookDeskId">Desk Number:</label>
                                                    <select id="bookDeskId" name="bookDeskId" class="form-control">
                                                        <option value="1" ${bookingUpdate.bookDeskId==1 ? 'selected'
                                                            : '' }>1號桌</option>
                                                        <option value="2" ${bookingUpdate.bookDeskId==2 ? 'selected'
                                                            : '' }>2號桌</option>
                                                        <option value="3" ${bookingUpdate.bookDeskId==3 ? 'selected'
                                                            : '' }>3號桌</option>
                                                        <option value="4" ${bookingUpdate.bookDeskId==4 ? 'selected'
                                                            : '' }>4號桌</option>
                                                        <option value="5" ${bookingUpdate.bookDeskId==5 ? 'selected'
                                                            : '' }>5號桌</option>
                                                    </select>
                                                </div>

                                                <div class="form-group">
                                                    <label for="bookDate">Date:</label>
                                                    <input type="date" id="bookDate" name="bookDate"
                                                        value="${bookingUpdate.bookDate}" class="form-control" />
                                                </div>

                                                <button type="submit" class="btn btn-primary">Update</button>
                                            </form:form>



                                            <!-- ######## include ######## -->
                                            <jsp:include page="../include/admin/adminFooter.jsp"></jsp:include>

                                        </div>

                                    </div>



                                    <script>

                                        

                                        $(document).ready(function () {
                                            // 監聽表單提交事件
                                            $("#bookingUpdateForm").submit(function (event) {
                                                event.preventDefault(); // 阻止預設的表單提交行為

                                                var form = $(this);
                                                var url = form.attr("action");
                                                var formData = form.serialize(); // 將表單資料序列化

                                                $.ajax({
                                                    type: "POST",
                                                    url: url,
                                                    data: formData,
                                                    success: function (response) {
                                                        // 處理成功回應，例如顯示成功訊息、重新載入頁面等
                                                        alert("Update successful!");
                                                        location.reload(); // 重新載入頁面
                                                    },
                                                    error: function (xhr, status, error) {
                                                        // 處理錯誤回應，例如顯示錯誤訊息等
                                                        alert("An error occurred while updating the booking.");
                                                        console.log(xhr.responseText);
                                                    }
                                                });
                                            });
                                        });








                                    </script>
                        </body>

                        </html>