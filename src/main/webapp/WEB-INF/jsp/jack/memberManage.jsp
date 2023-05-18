<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
                <%@ include file="../include/common_link.jsp" %>
                    <!DOCTYPE html>
                    <html>

                    <head>
                        <meta charset="UTF-8">
                        <title>${adminWebName}</title>

                        <style>
                            td img {
                                margin-right: 20px;
                            }
                            
                            #dataTable{
                                text-align: center;
                            }
                            
                            tbody td:nth-child(2) {
                                text-align: left;
                            }
                        </style>
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
                                        <h1 class="h3 mb-2 text-gray-800">會員列表</h1>


                                        <!-- DataTales Example -->
                                        <div class="card shadow mb-4">
                                            <div class="card-header py-3">
                                                <h6 class="m-0 font-weight-bold text-primary">會員</h6>
                                            </div>
                                            <div class="card-body">
                                                <div class="table-responsive">
                                                    <table class="table table-bordered" id="dataTable" width="100%"
                                                        cellspacing="0">
                                                        <thead>
                                                            <tr>
                                                                <th>Id</th>
                                                                <th>姓名</th>
                                                                <th>Email</th>
                                                                <th>生日</th>
                                                                <th>性別</th>
                                                                <th>電話</th>
                                                                <th>地址</th>
                                                                <th>Coin</th>
                                                                <th>帳號驗證</th>
                                                                <th>註冊日期</th>
                                                                <th></th>
                                                            </tr>
                                                        </thead>
                                                        <tfoot>
                                                            <tr>
                                                                <th>Id</th>
                                                                <th>姓名</th>
                                                                <th>Email</th>
                                                                <th>生日</th>
                                                                <th>性別</th>
                                                                <th>電話</th>
                                                                <th>地址</th>
                                                                <th>Coin</th>
                                                                <th>帳號驗證</th>
                                                                <th>註冊日期</th>
                                                                <th></th>

                                                            </tr>
                                                        </tfoot>
                                                        <tbody id="memberList">
                                                            <c:forEach items="${allMember}" var="all">
                                                                <c:if test='${!all.memberLevel.equals("管理員")}'>
                                                                <tr>
                                                                    <td>${all.memberId}</td>
                                                                    <td><img src="${root}/member/findMemberImg/${all.memberId}"
                                                                            alt="" width="32" height="32"
                                                                            class="rounded-circle"> ${all.memberName}
                                                                    </td>
                                                                    <td>${all.memberEmail}</td>
                                                                    <c:set var="BirthDate"
                                                                        value="${all.memberBirth}" />
                                                                    <fmt:formatDate value="${BirthDate}"
                                                                        pattern="yyyy-MM-dd" var="formattedDate" />
                                                                    <td>${formattedDate}</td>
                                                                    <td>${all.memberGender}</td>
                                                                    <td>${all.memberTel}</td>
                                                                    <td>${all.memberAddress}</td>
                                                                    <td>${all.memberCoin}</td>
                                                                    <c:choose>
                                                                        <c:when test="${all.memberActive == 1}">
                                                                            <td>已驗證</td>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <td style="color: red;">已停權</td>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                    <td>${all.createTime}</td>
                                                                    <c:choose>
                                                                        <c:when test="${all.memberActive == 1}">
                                                                            <td><a href="${root}/admin/banMember?id=${all.memberId}" class="btn btn-danger" id="banButton">停權</a></td>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <td><a href="${root}/admin/banMember?id=${all.memberId}" class="btn btn-info" id="banButton">認證</a></td>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </tr>

                                                            </c:if>
                                                            </c:forEach>

                                                            <!-- <tr>
                                                                <td>id</td>
                                                                <td><img src="${root}/member/findMemberImg/${member.memberId}"
                                                                        alt="" width="32" height="32"
                                                                        class="rounded-circle"> Name</td>
                                                                <td>Email</td>
                                                                <td>生日</td>
                                                                <td>性別</td>
                                                                <td>電話</td>
                                                                <td>地址</td>
                                                                <td>Coin</td>
                                                                <td>帳號驗證</td>
                                                                <td>註冊日期</td>
                                                            </tr> -->

                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>



                                    </div>
                                    <!-- /.container-fluid -->
                                    <!-- ######## include ######## -->
                                    <jsp:include page="../include/admin/adminFooter.jsp"></jsp:include>
                                </div>
                            </div>
                        </div>

                        <!-- 套版提供的Logout Modal，不需要可刪 -->
                        <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog"
                            aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                                        <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">×</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">Select "Logout" below if you are ready to end your current
                                        session.</div>
                                    <div class="modal-footer">
                                        <button class="btn btn-secondary" type="button"
                                            data-dismiss="modal">Cancel</button>
                                        <a class="btn btn-primary" href="${root}/member/logout">Logout</a>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <script type="text/javascript" src="${root}/js/jack/memberManage.js"></script>
                    </body>

                    </html>