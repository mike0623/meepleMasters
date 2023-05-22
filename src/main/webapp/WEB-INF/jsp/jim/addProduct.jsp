<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<jsp:include page="../include/common_link.jsp" />
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
      <jsp:include page="../include/admin/adminSidebar.jsp"></jsp:include>
      <!-- Content Wrapper -->
      <div id="content-wrapper" class="d-flex flex-column">
        <!-- Main Content -->
        <div id="content">
          <!-- ######## include ######## -->
          <jsp:include page="../include/admin/adminNavbar.jsp"></jsp:include>
          <!-- /.container-fluid -->

          <div class="col-lg-6 mb-4">
            <!-- Illustrations -->
            <div class="card shadow mb-4">
              <div class="card-header py-3">
                <h6 class="m-0 font-weight-bold text-primary">新增商品</h6>
              </div>
              <div class="card-body">
                <div class="text-center">
                  <img
                    class="img-fluid px-3 px-sm-4 mt-3 mb-4"
                    style="width: 25rem"
                    src="${root}/img/example/cat-Happy.jpg"
                    id="imgPreview"
                  />
                </div>
                <div>
                  <form
                    action="${root}/mall/addProduct"
                    method="post"
                    enctype="multipart/form-data"
                  >
                    <label for="productName">商品名稱:</label>
                    <input type="text" id="productName" name="productName" />
                    <br />
                    <label for="productPrice">商品價格:</label>
                    <input
                      type="number"
                      id="productPrice"
                      name="productPrice"
                      min="0"
                    />
                    <br />
                    <label for="productDescription">商品描述:</label>
                    <input
                      type="text"
                      id="productDescription"
                      name="productDescription"
                    />
                    <br />
                    <label for="productPlayTime">遊玩時間:</label>
                    <input
                      type="text"
                      id="productPlayTime"
                      name="productPlayTime"
                    />
                    <br />
                    <label for="productPlayer">建議遊玩人數:</label>
                    <input
                      type="number"
                      id="productMinPlayer"
                      name="productMinPlayer"
                      min="1"
                    />~<input
                      type="number"
                      id="productMaxPlayer"
                      name="productMaxPlayer"
                      min="1"
                    />
                    <br />
                    <label for="productDifficulty">上手難度:</label>
                    <input
                      type="text"
                      id="productDifficulty"
                      name="productDifficulty"
                    />
                    <br />
                    <label for="productImg">商品圖片:</label>
                    <input type="file" name="pImg" id="productImg" /> <br />
                    <button>新增</button>
                    <button id="resetButton" type="reset">清除</button>
                    <input type="button" value="測試新增資料" id="newTest" />
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- ######## include ######## -->
        <jsp:include page="../include/admin/adminFooter.jsp"></jsp:include>
      </div>
    </div>
    <script>
      // 一鍵輸入測試資料
      $("#newTest").on("click", function () {
        $("#productName").val("測試用撲克牌");
        $("#productPrice").val("162");
        $("#productDescription").val("商品說明");
        $("#productPlayTime").val("普通");
        $("#productMaxPlayer").val("6");
        $("#productMinPlayer").val("2");
        $("#productDifficulty").val("普通");
      });
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
