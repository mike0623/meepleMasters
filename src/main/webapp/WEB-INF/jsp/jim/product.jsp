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
      .container {
        height: 1450px;
      }
      .card-body ul > li:not(:last-child) {
        border-bottom: 1px dashed;
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
        color: #8a513f;
      }
    </style>
  </head>
  <body>
    <jsp:include page="../include/header.jsp" />
    <div class="container gameCardDiv">
      <!-- <div>
        <select id="productpPlayTime">
          <option value="">請選擇</option>
          <option value="簡短">簡短</option>
          <option value="長度適中">長度適中</option>
          <option value="耗時">耗時</option>
        </select>
        <select id="productDifficulty">
          <option value="">請選擇</option>
          <option value="休閒小品">休閒小品</option>
          <option value="適合一般玩家">適合一般玩家</option>
          <option value="適合深度玩家">適合深度玩家</option>
        </select>
        <button type="button" id="queryButton">查詢</button>
      </div> -->
      <div class="gameListTitle">
        遊戲列表
        <div class="link-top"></div>
      </div>
      <div class="pageButton"></div>
      <div class="row px-4 pt-4 justify-content-center" id="dataHome"></div>
    </div>
    <jsp:include page="../include/footer.jsp" />
    <script>
      let mId = `${member.memberId}`;
      let mEmail = `${member.memberEmail}`;
    </script>
    <script src="${root}/js/jim/product.js"></script>
  </body>
</html>
