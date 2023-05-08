<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c"%> <%@taglib uri="http://www.springframework.org/tags/form"
prefix="form"%>
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

          <div class="col-lg-6 mb-4">
            <!-- Illustrations -->
            <div class="card shadow mb-4">
              <div class="card-header py-3">
                <h6 class="m-0 font-weight-bold text-primary">更新商品</h6>
              </div>
              <div class="card-body">
                <div class="text-center">
                  <img
                    class="img-fluid px-3 px-sm-4 mt-3 mb-4"
                    style="width: 25rem"
                    src="${root}/mall/getPhoto?pId=${product.productId}"
                    id="imgPreview"
                  />
                </div>
                <div>
                  <form
                    action="${root}/mall/updateProduct"
                    method="post"
                    enctype="multipart/form-data"
                  >
                    <input
                      type="hidden"
                      value="${product.productId}"
                      name="productId"
                    />
                    <label for="productName">商品名稱:</label>
                    <input
                      type="text"
                      name="productName"
                      value="${product.productName}"
                    />
                    <br />
                    <label for="productPrice">商品價格:</label>
                    <input
                      type="number"
                      name="productPrice"
                      min="0"
                      value="${product.productPrice}"
                    />
                    <br />
                    <label for="productDescription">商品描述:</label>
                    <input
                      type="text"
                      name="productDescription"
                      value="${product.productDescription}"
                    />
                    <br />
                    <label for="productPlayTime">遊玩時間:</label>
                    <input
                      type="text"
                      name="productPlayTime"
                      value="${product.productPlayTime}"
                    />
                    <br />
                    <label for="productMinPlayer">建議遊玩人數:</label>
                    <input
                      type="number"
                      name="productMinPlayer"
                      min="1"
                      value="${product.productMinPlayer}"
                    />~
                    <input
                      type="number"
                      name="productMaxPlayer"
                      min="1"
                      value="${product.productMaxPlayer}"
                    />
                    <br />
                    <label for="productDifficulty">上手難度:</label>
                    <input
                      type="text"
                      name="productDifficulty"
                      value="${product.productDifficulty}"
                    />
                    <br />
                    <label for="productImg">商品圖片:</label>
                    <input type="file" name="pImg" id="productImg" />
                    <br />
                    <button>更新</button>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- ######## include ######## -->
        <jsp:include
          page="/WEB-INF/jsp/include/admin/adminFooter.jsp"
        ></jsp:include>
      </div>
    </div>
    <script>
      // 顯示上傳圖片的預覽圖
      let productImg = $("#productImg");
      let imgPreview = $("#imgPreview");

      productImg.change(function () {
        const file = this.files[0];
        const tempSrc = URL.createObjectURL(file);
        imgPreview.attr("src", tempSrc);
      });
    </script>
  </body>
</html>
