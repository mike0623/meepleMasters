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
    <div class="bodyContainer">
      <section class="vh-100" style="background-color: #f0ebe2">
        <div class="container h-100">
          <div
            class="row d-flex justify-content-center align-items-center h-100"
          >
            <div class="col">
              <p>
                <span class="h2">購物車</span
                ><span class="h4">(1 item in your cart)</span>
              </p>
              <div class="card mb-4">
                <div class="card-body p-4">
                  <div class="row align-items-center">
                    <div class="col-md-4">
                      <img
                        src="https://mdbcdn.b-cdn.net/img/Photos/Horizontal/E-commerce/Products/1.webp"
                        class="img-fluid"
                        alt="Generic placeholder image"
                      />
                    </div>
                    <div class="col-md-4 d-flex justify-content-center">
                      <div>
                        <p class="small text-muted mb-4 pb-2">遊戲名稱</p>
                        <p class="lead fw-normal mb-0">抽鬼牌</p>
                      </div>
                    </div>
                    <div class="col-md-4 d-flex justify-content-center">
                      <div>
                        <p class="small text-muted mb-4 pb-2">價格</p>
                        <p class="lead fw-normal mb-0">50元</p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <div class="card mb-5">
                <div class="card-body p-4">
                  <div class="float-end">
                    <p class="mb-0 me-5 d-flex align-items-center">
                      <span class="small text-muted me-2">Order total:</span>
                      <span class="lead fw-normal">$799</span>
                    </p>
                  </div>
                </div>
              </div>

              <div class="d-flex justify-content-end">
                <button type="button" class="btn btn-light btn-lg me-2">
                  Continue shopping
                </button>
                <button type="button" class="btn btn-primary btn-lg">
                  Add to cart
                </button>
              </div>
            </div>
          </div>
        </div>
      </section>

      <div class="bodyContainer gameCardDiv">
        <div class="row px-4 pt-4 justify-content-center" id="dataHome"></div>

        <div class="pageButton"></div>
        <div><button id="testButton">測試按鈕</button></div>
      </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
    <script>
      $("#testButton").on("click", function () {
        getFavoriteGameList();
      });
      function getFavoriteGameList() {
        axios
          .get(
            `/meeple-masters/favoriteGame/favoriteGameList/${member.memberEmail}`
          )
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
