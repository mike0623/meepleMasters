<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>${webName}</title>
    <jsp:include page="/WEB-INF/jsp/include/common_link.jsp" />
    <link rel="stylesheet" type="text/css" href="${root}/css/index.css" />
    <style>
      .gameCardDiv {
        height: 1500px;
      }
    </style>
  </head>

  <body>
    <jsp:include page="/WEB-INF/jsp/include/header.jsp" />
    <div class="container gameCardDiv">
      <div class="gameListTitle">
        遊戲列表
        <div class="link-top"></div>
      </div>

      <div class="row px-4 pt-4 justify-content-center" id="dataHome">
        <div class="col-3 d-flex align-items-stretch">
          <div class="card">
            <div class="pic">
              <img src="https://picsum.photos/300/?random=10" />
            </div>
            <div class="card-header">${product.productName}</div>

            <div class="card-body">
              <h3 class="title">${product.productPrice}</h3>
            </div>

            <div class="card-footer">
              <div class="text">
                <ul>
                  <li>${product.addedTime}</li>
                  <li>${product.productDescription}</li>
                  <li>${product.productPlayTime}</li>
                  <li>
                    ${product.productMinPlayer}~${product.productMaxPlayer}
                  </li>
                  <li>${product.productDifficulty}</li>
                  <li>
                    <button class="cartbutton" value="${product.productId}">
                      加入購物車
                    </button>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <script>
      (function init() {
        getProductList();
      })();

      function getProductList(page, count) {
        axios
          .get("${root}/mall/productList", {
            params: {
              page: page,
              count: count,
            },
          })
          .then((response) => {
            let pList = response.data.content;
            renderPage(pList);
          });
      }

      function renderPage(pList) {
        let outputString = "";
        for (let p of pList) {
          outputString += '<div class="col-3 d-flex align-items-stretch">';
          outputString += '<div class="card">';
          outputString += '<div class="pic">';
          outputString += `<img src="${root}/mall/getPhoto?pId=\${p.productId}">`;
          outputString += "</div>";
          outputString += `<div class="card-header">\${p.productName}</div>`;
          outputString += '<div class="card-body">';
          outputString += `<h3 class="title">\${p.productPrice}</h3>`;
          outputString += "</div>";
          outputString += '<div class="card-footer">';
          outputString += '<div class="text">';
          outputString += "<ul>";
          outputString += `<li>\${p.addedTime}</li>`;
          outputString += `<li>\${p.productDescription}</li>`;
          outputString += `<li>\${p.productPlayTime}</li>`;
          outputString += `<li>\${p.productMinPlayer}~\${p.productMaxPlayer}</li>`;
          outputString += `<li>\${p.productDifficulty}</li>`;
          outputString += "<li>";
          outputString += `<button class="cartbutton" value="\${p.productId}">加入購物車</button>`;
          outputString += "</li>";
          outputString += "</ul></div></div></div></div>";
        }

        $("#dataHome").html(outputString);
      }

      // 用AJAX將商品加入購物車
      let product = document.getElementsByClassName("cartbutton");
      for (i = 0; i < product.length; i++) {
        product[i].addEventListener("click", function () {
          console.log(this.value);
          let pId = this.value;
          let mId = 123;
          axios
            .get(
              "http://localhost:8080/meeple-masters/shoppingCart/insertShoppingCart",
              {
                params: {
                  productId: pId,
                  memberId: mId,
                },
              }
            )
            .then((response) => console.log(response), alert("新增成功"))
            .catch((error) => console.log(error), alert("新增失敗"));
        });
      }
    </script>
    <!-- <jsp:include page="/WEB-INF/jsp/include/footer.jsp" /> -->
  </body>
</html>
