<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>${webName}</title>
    <jsp:include page="../include/common_link.jsp" />
    <link rel="stylesheet" type="text/css" href="${root}/css/index.css" />
  </head>
  <body>
    <jsp:include page="../include/header.jsp" />
    <div class="bodyContainer">
      <div class="container gameCardDiv">
        <div>
          <a href="${root}/mall/shoppingCart">購物車</a>
        </div>
        <div>
          <form action="${root}/mall/order/" method="get">
            <input type="hidden" value="${member.memberId}" name="memberId" />
            <button>訂單</button>
          </form>
        </div>
        <!-- <div>
          <select name="productDifficulty" id="productDifficultySelect">
            <option value="簡單">簡單</option>
            <option value="普通">普通</option>
            <option value="困難">困難</option>
          </select>
        </div> -->
        <div class="gameListTitle">
          遊戲列表
          <div class="link-top"></div>
        </div>
        <div class="pageButton"></div>
        <div class="row px-4 pt-4 justify-content-center" id="dataHome"></div>
      </div>
    </div>
    <jsp:include page="../include/footer.jsp" />
    <script>
      let mId = "${member.memberId}";
      $("#productDifficultySelect").change(function () {
        console.log(this.value);
      });
    </script>
    <script src="${root}/js/jim/product.js"></script>
  </body>
</html>
