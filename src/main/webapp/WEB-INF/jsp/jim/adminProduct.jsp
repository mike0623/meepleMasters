<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="/WEB-INF/jsp/include/common_link.jsp" />
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>${adminWebName}</title>
  </head>

  <body id="page-top">
    <!-- Page Wrapper -->
    <div id="wrapper">
      <!-- ######## include ######## -->
      <jsp:include
        page="/WEB-INF/jsp/include/admin/adminSidebar.jsp"
      ></jsp:include>

      <!-- Content Wrapper -->
      <div id="content-wrapper" class="d-flex flex-column">
        <!-- Main Content -->
        <div id="content">
          <!-- ######## include ######## -->
          <jsp:include
            page="/WEB-INF/jsp/include/admin/adminNavbar.jsp"
          ></jsp:include>

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
                <div class="table-responsive">
                  <table
                    class="table table-bordered"
                    id="dataTable"
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
                        <td>Tiger Nixon</td>
                        <td>System Architect</td>
                        <td>Edinburgh</td>
                        <td>61</td>
                        <td>2011/04/25</td>
                        <td>$320,800</td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
          <!-- /.container-fluid -->

          <!-- 按鈕參考 -->
          <!-- Begin Page Content -->
          <div class="container-fluid">
            <!-- Page Heading -->
            <h1 class="h3 mb-4 text-gray-800">按鈕</h1>

            <div class="row">
              <div class="col-lg-6">
                <!-- Circle Buttons -->
                <div class="card shadow mb-4">
                  <div class="card-header py-3">
                    <h6 class="m-0 font-weight-bold text-primary">
                      Circle Buttons
                    </h6>
                  </div>
                  <div class="card-body">
                    <p>
                      Use Font Awesome Icons (included with this theme package)
                      along with the circle buttons as shown in the examples
                      below!
                    </p>
                    <!-- Circle Buttons (Default) -->
                    <div class="mb-2">
                      <code>.btn-circle</code>
                    </div>
                    <a href="#" class="btn btn-primary btn-circle">
                      <i class="fab fa-facebook-f"></i>
                    </a>
                    <a href="#" class="btn btn-success btn-circle">
                      <i class="fas fa-check"></i>
                    </a>
                    <a href="#" class="btn btn-info btn-circle">
                      <i class="fas fa-info-circle"></i>
                    </a>
                    <a href="#" class="btn btn-warning btn-circle">
                      <i class="fas fa-exclamation-triangle"></i>
                    </a>
                    <a href="#" class="btn btn-danger btn-circle">
                      <i class="fas fa-trash"></i>
                    </a>
                    <!-- Circle Buttons (Small) -->
                    <div class="mt-4 mb-2">
                      <code>.btn-circle .btn-sm</code>
                    </div>
                    <a href="#" class="btn btn-primary btn-circle btn-sm">
                      <i class="fab fa-facebook-f"></i>
                    </a>
                    <a href="#" class="btn btn-success btn-circle btn-sm">
                      <i class="fas fa-check"></i>
                    </a>
                    <a href="#" class="btn btn-info btn-circle btn-sm">
                      <i class="fas fa-info-circle"></i>
                    </a>
                    <a href="#" class="btn btn-warning btn-circle btn-sm">
                      <i class="fas fa-exclamation-triangle"></i>
                    </a>
                    <a href="#" class="btn btn-danger btn-circle btn-sm">
                      <i class="fas fa-trash"></i>
                    </a>
                    <!-- Circle Buttons (Large) -->
                    <div class="mt-4 mb-2">
                      <code>.btn-circle .btn-lg</code>
                    </div>
                    <a href="#" class="btn btn-primary btn-circle btn-lg">
                      <i class="fab fa-facebook-f"></i>
                    </a>
                    <a href="#" class="btn btn-success btn-circle btn-lg">
                      <i class="fas fa-check"></i>
                    </a>
                    <a href="#" class="btn btn-info btn-circle btn-lg">
                      <i class="fas fa-info-circle"></i>
                    </a>
                    <a href="#" class="btn btn-warning btn-circle btn-lg">
                      <i class="fas fa-exclamation-triangle"></i>
                    </a>
                    <a href="#" class="btn btn-danger btn-circle btn-lg">
                      <i class="fas fa-trash"></i>
                    </a>
                  </div>
                </div>

                <!-- Brand Buttons -->
                <div class="card shadow mb-4">
                  <div class="card-header py-3">
                    <h6 class="m-0 font-weight-bold text-primary">
                      Brand Buttons
                    </h6>
                  </div>
                  <div class="card-body">
                    <p>
                      Google and Facebook buttons are available featuring each
                      company's respective brand color. They are used on the
                      user login and registration pages.
                    </p>
                    <p>
                      You can create more custom buttons by adding a new color
                      variable in the
                      <code>_variables.scss</code>
                      file and then using the Bootstrap button variant mixin to
                      create a new style, as demonstrated in the
                      <code>_buttons.scss</code>
                      file.
                    </p>
                    <a href="#" class="btn btn-google btn-block"
                      ><i class="fab fa-google fa-fw"></i> .btn-google</a
                    >
                    <a href="#" class="btn btn-facebook btn-block"
                      ><i class="fab fa-facebook-f fa-fw"></i> .btn-facebook</a
                    >
                  </div>
                </div>
              </div>

              <div class="col-lg-6">
                <div class="card shadow mb-4">
                  <div class="card-header py-3">
                    <h6 class="m-0 font-weight-bold text-primary">
                      Split Buttons with Icon
                    </h6>
                  </div>
                  <div class="card-body">
                    <p>
                      Works with any button colors, just use the
                      <code>.btn-icon-split</code>
                      class and the markup in the examples below. The examples
                      below also use the
                      <code>.text-white-50</code>
                      helper class on the icons for additional styling, but it
                      is not required.
                    </p>
                    <a href="#" class="btn btn-primary btn-icon-split">
                      <span class="icon text-white-50">
                        <i class="fas fa-flag"></i>
                      </span>
                      <span class="text">Split Button Primary</span>
                    </a>
                    <div class="my-2"></div>
                    <a href="#" class="btn btn-success btn-icon-split">
                      <span class="icon text-white-50">
                        <i class="fas fa-check"></i>
                      </span>
                      <span class="text">Split Button Success</span>
                    </a>
                    <div class="my-2"></div>
                    <a href="#" class="btn btn-info btn-icon-split">
                      <span class="icon text-white-50">
                        <i class="fas fa-info-circle"></i>
                      </span>
                      <span class="text">Split Button Info</span>
                    </a>
                    <div class="my-2"></div>
                    <a href="#" class="btn btn-warning btn-icon-split">
                      <span class="icon text-white-50">
                        <i class="fas fa-exclamation-triangle"></i>
                      </span>
                      <span class="text">Split Button Warning</span>
                    </a>
                    <div class="my-2"></div>
                    <a href="#" class="btn btn-danger btn-icon-split">
                      <span class="icon text-white-50">
                        <i class="fas fa-trash"></i>
                      </span>
                      <span class="text">Split Button Danger</span>
                    </a>
                    <div class="my-2"></div>
                    <a href="#" class="btn btn-secondary btn-icon-split">
                      <span class="icon text-white-50">
                        <i class="fas fa-arrow-right"></i>
                      </span>
                      <span class="text">Split Button Secondary</span>
                    </a>
                    <div class="my-2"></div>
                    <a href="#" class="btn btn-light btn-icon-split">
                      <span class="icon text-gray-600">
                        <i class="fas fa-arrow-right"></i>
                      </span>
                      <span class="text">Split Button Light</span>
                    </a>
                    <div class="mb-4"></div>
                    <p>Also works with small and large button classes!</p>
                    <a href="#" class="btn btn-primary btn-icon-split btn-sm">
                      <span class="icon text-white-50">
                        <i class="fas fa-flag"></i>
                      </span>
                      <span class="text">Split Button Small</span>
                    </a>
                    <div class="my-2"></div>
                    <a href="#" class="btn btn-primary btn-icon-split btn-lg">
                      <span class="icon text-white-50">
                        <i class="fas fa-flag"></i>
                      </span>
                      <span class="text">Split Button Large</span>
                    </a>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!-- /.container-fluid -->

          <!-- Border參考 -->
          <!-- Begin Page Content -->
          <div class="container-fluid">
            <!-- Page Heading -->
            <h1 class="h3 mb-1 text-gray-800">Border</h1>
            <p class="mb-4">
              Bootstrap's default utility classes can be found on the official
              <a href="https://getbootstrap.com/docs"
                >Bootstrap Documentation</a
              >
              page. The custom utilities below were created to extend this theme
              past the default utility classes built into Bootstrap's framework.
            </p>

            <!-- Content Row -->
            <div class="row">
              <!-- Border Left Utilities -->
              <div class="col-lg-6">
                <div class="card mb-4 py-3 border-left-primary">
                  <div class="card-body">.border-left-primary</div>
                </div>

                <div class="card mb-4 py-3 border-left-secondary">
                  <div class="card-body">.border-left-secondary</div>
                </div>

                <div class="card mb-4 py-3 border-left-success">
                  <div class="card-body">.border-left-success</div>
                </div>

                <div class="card mb-4 py-3 border-left-info">
                  <div class="card-body">.border-left-info</div>
                </div>

                <div class="card mb-4 py-3 border-left-warning">
                  <div class="card-body">.border-left-warning</div>
                </div>

                <div class="card mb-4 py-3 border-left-danger">
                  <div class="card-body">.border-left-danger</div>
                </div>

                <div class="card mb-4 py-3 border-left-dark">
                  <div class="card-body">.border-left-dark</div>
                </div>
              </div>

              <!-- Border Bottom Utilities -->
              <div class="col-lg-6">
                <div class="card mb-4 py-3 border-bottom-primary">
                  <div class="card-body">.border-bottom-primary</div>
                </div>

                <div class="card mb-4 py-3 border-bottom-secondary">
                  <div class="card-body">.border-bottom-secondary</div>
                </div>

                <div class="card mb-4 py-3 border-bottom-success">
                  <div class="card-body">.border-bottom-success</div>
                </div>

                <div class="card mb-4 py-3 border-bottom-info">
                  <div class="card-body">.border-bottom-info</div>
                </div>

                <div class="card mb-4 py-3 border-bottom-warning">
                  <div class="card-body">.border-bottom-warning</div>
                </div>

                <div class="card mb-4 py-3 border-bottom-danger">
                  <div class="card-body">.border-bottom-danger</div>
                </div>

                <div class="card mb-4 py-3 border-bottom-dark">
                  <div class="card-body">.border-bottom-dark</div>
                </div>
              </div>
            </div>
          </div>
          <!-- 參考內容結束 -->
        </div>

        <!-- ######## include ######## -->
        <jsp:include
          page="/WEB-INF/jsp/include/admin/adminFooter.jsp"
        ></jsp:include>
      </div>
    </div>

    <!-- 回到最頂的按鈕，有需要可加 -->
    <a class="scroll-to-top rounded" href="#page-top">
      <i class="fa-solid fa-angle-up" style="line-height: 46px"></i>
    </a>

    <script>
      // 初始化
      (function init() {
        getProductList();
      })();
      // 透過AJAX取得頁面資料
      function getProductList(page, count) {
        axios
          .get("${root}/mall/productList", {
            params: {
              page: page,
              count: count,
            },
          })
          .then((response) => {
            let pageInfo = response.data;
            console.log(pageInfo.totalPages);

            renderProduct(pageInfo.content);
          });
      }
      function renderProduct(pList) {
        let outputString = "";
        for (let p of pList) {
          outputString += "<tr>";
          outputString += `<td>\${p.productName}</td>`;
          outputString += `<td>\${p.productPrice}元</td>`;
          outputString += `<td>\${p.addedTime}</td>`;
          outputString += `<td>\${p.productDescription}</td>`;
          outputString += `<td>\${p.productPlayTime}</td>`;
          outputString += `<td>\${p.productMinPlayer}~\${p.productMaxPlayer}</td>`;
          outputString += `<td>\${p.productDifficulty}</td>`;
          outputString += "<td>";
          outputString += `<button class="deleteButton btn btn-danger btn-circle" value="\${p.productId}">`;
          outputString += "<i class='fas fa-trash'></i>";
          outputString += "</button>";
          outputString += "</td>";
          outputString += "<td>";
          outputString +=
            "<form action='${root}/mall/updateProduct' method='GET'>";
          outputString += `<button class="updateButton btn btn-info btn-circle" type='submit'>`;
          outputString += "<i class='fas fa-info-circle'></i>";
          outputString += "</button>";
          outputString += `<input type='hidden' name='id' value='\${p.productId}'>`;
          outputString += "</form>";
          outputString += "</td>";
          outputString += "</tr>";

          //   outputString += `<img src="${root}/mall/getPhoto?pId=\${p.productId}">`;
        }
        $("#dataHome").html(outputString);

        // 用AJAX透過ID刪除資料
        let deleteButton = document.getElementsByClassName("deleteButton");
        for (i = 0; i < deleteButton.length; i++) {
          deleteButton[i].addEventListener("click", function () {
            console.log(this.value);
            let pId = this.value;

            axios
              .delete("${root}/mall/deleteProductById", {
                params: { id: pId },
              })
              .then((response) => {
                if (response.data == "刪除成功") {
                  getProductList();
                }
              })
              .catch((error) => console.log(error));
          });
        }
      }
    </script>
  </body>
</html>
