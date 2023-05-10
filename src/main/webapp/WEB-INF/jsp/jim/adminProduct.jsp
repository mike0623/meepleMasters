<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>${adminWebName}</title>
    <jsp:include page="../include/common_link.jsp" />
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

          <!-- /.container-fluid -->

          <!-- 表格參考 -->
          <!-- Begin Page Content -->
          <div class="container-fluid">
            <!-- Page Heading -->
            <h1 class="h3 mb-2 text-gray-800">商品管理列表</h1>

            <!-- DataTales Example -->
            <div class="card shadow mb-4">
              <div class="card-header py-3">
                <h6 class="m-0 font-weight-bold text-primary">
                  <a href="${root}/mall/addProduct">新增商品</a>
                </h6>
              </div>
              <div class="card-body">
                <div class="table-responsive text-center">
                  <table
                    class="table table-bordered"
                    width="100%"
                    cellspacing="0"
                  >
                    <thead>
                      <tr>
                        <th>商品名稱</th>
                        <th>商品價格</th>
                        <th>上架日期</th>
                        <th>商品描述</th>
                        <th>遊戲時間</th>
                        <th>建議遊玩人數</th>
                        <th>遊戲難度</th>
                        <th>刪除商品</th>
                        <th>更新商品</th>
                      </tr>
                    </thead>
                    <tbody id="dataHome">
                      <tr>
                        <td>Temp</td>
                        <td>Temp</td>
                        <td>Temp</td>
                        <td>Temp</td>
                        <td>Temp</td>
                        <td>Temp</td>
                        <td>Temp</td>
                        <td>Temp</td>
                        <td>Temp</td>
                      </tr>
                    </tbody>
                  </table>
                </div>

                <div class="pageButton"></div>
              </div>
            </div>
          </div>
          <!-- /.container-fluid -->
        </div>

        <!-- ######## include ######## -->
        <jsp:include page="../include/admin/adminFooter.jsp"></jsp:include>
      </div>
    </div>
    <script src="${root}/js/jim/adminProduct.js"></script>
  </body>
</html>
