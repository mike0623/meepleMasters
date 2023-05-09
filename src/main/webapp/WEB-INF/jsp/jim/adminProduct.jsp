<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>${adminWebName}</title>
    <jsp:include page="/WEB-INF/jsp/include/common_link.jsp" />
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
      let pageButton = document.getElementsByClassName("pageButton")[0];
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
            renderProduct(response.data.content);
            renderPageButton(response.data);
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
                getProductList();
              })
              .catch((error) => console.log(error));
          });
        }
      }

      // 渲染分頁按鈕
      function renderPageButton(pageInfo) {
        let outputString = "";
        outputString += `<ul class="pagination justify-content-center">`;
        let disabled = pageInfo.number == 0 ? "disabled" : "";
        outputString += `<li class="page-item \${disabled}">`;
        outputString += `<button class="page-link">＜</button>`;
        outputString += `</li>`;

        for (let i = 1; i <= pageInfo.totalPages; i++) {
          let active = i == pageInfo.number + 1 ? "active" : "";
          outputString += `<li class="page-item \${active}"><button class="page-link">\${i}</button></li>`;
        }

        disabled = pageInfo.number == pageInfo.totalPages - 1 ? "disabled" : "";
        outputString += `<li class="page-item \${disabled}">`;

        outputString += `<button class="page-link">＞</button>`;

        outputString += `</li>`;
        outputString += `</ul>`;
        pageButton.innerHTML = outputString;
      }

      // 分頁點擊換頁
      pageButton.addEventListener("click", function (e) {
        if (e.target.tagName != "BUTTON") {
          return;
        }
        let page = e.target.innerText;
        page =
          page == "＜"
            ? parseInt(
                pageButton.getElementsByClassName("active")[0].innerText
              ) - 1
            : page;
        page =
          page == "＞"
            ? parseInt(
                pageButton.getElementsByClassName("active")[0].innerText
              ) + 1
            : page;
        getProductList(page, 6);
      });
    </script>
  </body>
</html>
