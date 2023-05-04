<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c"%> <%@taglib uri="http://www.springframework.org/tags/form"
prefix="form"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>${webName}</title>
    <link rel="stylesheet" type="text/css" href="${root}/css/index.css" />
    <jsp:include page="/WEB-INF/jsp/include/common_link.jsp" />
  </head>
  <body>
    <jsp:include page="../include/header.jsp" />
    <div class="bodyContainer">
      <h1>更新商品</h1>
      <form
        id="updateForm"
        action="${root}/mall/updateProduct"
        method="post"
        enctype="multipart/form-data"
      >
        <input type="hidden" value="${product.productId}" name="productId" />
        <input type="hidden" value="${product.addedTime}" name="addedTime" />
        <label for="productName">商品名稱:</label>
        <input
          type="text"
          id="productName"
          name="productName"
          value="${product.productName}"
        />
        <br />
        <label for="productPrice">商品價格:</label>
        <input
          type="number"
          id="productPrice"
          name="productPrice"
          min="0"
          value="${product.productPrice}"
        />
        <br />
        <label for="productDescription">商品描述:</label>
        <input
          type="text"
          id="productDescription"
          name="productDescription"
          value="${product.productDescription}"
        />
        <br />
        <label for="productPlayTime">遊玩時間:</label>
        <input
          type="text"
          id="productPlayTime"
          name="productPlayTime"
          value="${product.productPlayTime}"
        />
        <br />
        <label for="productMaxPlayer">建議最多遊玩人數:</label>
        <input
          type="number"
          id="productMaxPlayer"
          name="productMaxPlayer"
          min="2"
          value="${product.productMaxPlayer}"
        />
        <br />
        <label for="productMinPlayer">建議最少遊玩人數:</label>
        <input
          type="number"
          id="productMinPlayer"
          name="productMinPlayer"
          min="1"
          value="${product.productMinPlayer}"
        />
        <br />
        <label for="productDifficulty">上手難度:</label>
        <input
          type="text"
          id="productDifficulty"
          name="productDifficulty"
          value="${product.productDifficulty}"
        />
        <br />
        <label for="productImg">商品圖片:</label>
        <input
          type="file"
          name="pImg"
          id="productImg"
          src="${root}/mall/getPhoto?pId=${product.productId}"
        />
        <br />
        <button>更新</button>
        <img
          alt=""
          src="${root}/mall/getPhoto?pId=${product.productId}"
          id="imgPreview"
        />
      </form>
    </div>
    <jsp:include page="../include/footer.jsp" />
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
