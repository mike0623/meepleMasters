<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>${webName}</title>
    <link rel="stylesheet" type="text/css" href="${root}/css/index.css" />
    <jsp:include page="/WEB-INF/jsp/include/common_link.jsp" />
  </head>
  <body>
    <jsp:include page="/WEB-INF/jsp/include/header.jsp" />
    <div class="bodyContainer gameCardDiv">
      <div class="gameListTitle">
        我的最愛
        <div class="link-top"></div>
      </div>

      <div class="row px-4 pt-4 justify-content-center" id="dataHome"></div>

      <div class="pageButton"></div>
      <div><button id="testButton">測試按鈕</button></div>
    </div>
    <jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
    <script>
      $("#testButton").on("click", function () {
        getFavoriteGameList();
      });
      function getFavoriteGameList() {
        axios
          .get(`/meeple-masters/favoriteGame/favoriteGameList/${member.memberEmail}`)
          .then((response) => {
            console.log(response.data);
          })
          .catch((error) => {
            console.log(error);
          });
      }
    </script>
  </body>
</html>
