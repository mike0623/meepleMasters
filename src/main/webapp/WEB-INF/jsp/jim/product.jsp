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
    <style>
      .bodyContainer {
        height: 1700px;
      }

      li {
        list-style-type: none;
      }

      .faviroteButton {
        border: none;
        background: none;
        padding: 0;
        float: right;
        color: #ff0000;
      }

      .cartButton {
        border: none;
        background: none;
        padding: 0;
      }
    </style>
  </head>
  <body>
    <jsp:include page="../include/header.jsp" />
    <div class="bodyContainer">
      <div class="container gameCardDiv">
        <div>
          <select id="productpPlayTime">
            <option value="">請選擇</option>
            <option value="簡短">簡短</option>
            <option value="長度適中">長度適中</option>
            <option value="耗時">耗時</option>
          </select>
          <select id="productPrice">
            <option value="10000">請選擇</option>
            <option value="100">小於100元</option>
            <option value="300">小於300元</option>
            <option value="500">小於500元</option>
          </select>
          <button type="button" id="queryButton">查詢</button>
        </div>
        <div>
          <a href="${root}/shoppingCart">購物車</a>
        </div>
        <div>
          <a href="${root}/order/">訂單</a>
        </div>
        <div>
          <a href="${root}/order/orderList/?memberId=${member.memberId}"
            >訂單列表</a
          >
        </div>

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
      let mId = `${member.memberId}`;
    </script>
    <script src="${root}/js/jim/product.js"></script>
  </body>
</html>
