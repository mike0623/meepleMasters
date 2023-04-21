<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
      <h1>testpage</h1>
      <hr />
      <button class="test">查詢</button>
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
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
    <jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
  </body>
  <script type="text/javascript">
    let test = $(".test");
    test[0].addEventListener("click", function () {
      console.log("test");
    });
  </script>
</html>
