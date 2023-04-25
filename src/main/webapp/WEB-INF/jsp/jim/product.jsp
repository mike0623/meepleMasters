<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>${webName}</title>
    <jsp:include page="/WEB-INF/jsp/include/common_link.jsp" />
    <style>
      .container {
        top: 75px;
        position: relative;
        height: 600px;
      }
    </style>
  </head>
  <body>
    <jsp:include page="/WEB-INF/jsp/include/header.jsp" />
    <div class="container">
      <h1>商品列表</h1>
      <hr />
      <table>
        <thead>
          <tr>
            <th>商品名稱</th>
            <th>商品價格</th>
            <th>上架日期</th>
            <th>商品描述</th>
            <th>遊玩時間</th>
            <th>建議人數</th>
            <th>上手難度</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${allProduct}" var="product">
            <tr>
              <td>${product.productName}</td>
              <td>${product.productPrice}</td>
              <td>${product.addedTime}</td>
              <td>${product.productDescription}</td>
              <td>${product.productPlayTime}</td>
              <td>${product.productMinPlayer}~${product.productMaxPlayer}</td>
              <td>${product.productDifficulty}</td>
              <th>
                <button class="cartbutton" value="${product.productId}">
                  加入購物車
                </button>
              </th>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
    <script>
      let product = document.getElementsByClassName("cartbutton");

      for (i = 0; i < product.length; i++) {
        product[i].addEventListener("click", function () {
          console.log(this.value);
          let pId = this.value;
          axios
            .get("http://localhost:8080/meeple-masters/shoppingCart/insertShoppingCart", {
              params: {
                productId: pId,
                memberId: pId,
              },
            })
            .then((response) => console.log(response))
            .catch((error) => console.log(error));
        });
      }
    </script>
    <jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
  </body>
</html>
