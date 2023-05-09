<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>${webName}</title>
    <link rel="stylesheet" type="text/css" href="${root}/css/index.css" />
    <jsp:include page="/WEB-INF/jsp/include/common_link.jsp" />
    <style>
      body {
        margin-top: 20px;
        background: #eee;
      }
      .ui-w-40 {
        width: 40px !important;
        height: auto;
      }

      .card {
        box-shadow: 0 1px 15px 1px rgba(52, 40, 104, 0.08);
      }

      .ui-product-color {
        display: inline-block;
        overflow: hidden;
        margin: 0.144em;
        width: 0.875rem;
        height: 0.875rem;
        border-radius: 10rem;
        -webkit-box-shadow: 0 0 0 1px rgba(0, 0, 0, 0.15) inset;
        box-shadow: 0 0 0 1px rgba(0, 0, 0, 0.15) inset;
        vertical-align: middle;
      }
    </style>
  </head>
  <body>
    <jsp:include page="/WEB-INF/jsp/include/header.jsp" />
    <div class="bodyContainer">
      <div class="container px-3 my-5 clearfix">
        <!-- Shopping cart table -->
        <div class="card">
          <div class="card-header">
            <h2>購物車</h2>
          </div>
          <div class="card-body">
            <div class="table-responsive">
              <table class="table table-bordered m-0">
                <thead>
                  <tr>
                    <!-- Set columns width -->
                    <th class="text-center py-3 px-4" style="min-width: 10px">
                      圖片
                    </th>
                    <th class="text-center py-3 px-4" style="min-width: 300px">
                      商品名稱
                    </th>
                    <th class="text-center py-3 px-4" style="width: 300px">
                      價格
                    </th>
                    <th
                      class="text-center align-middle py-3 px-0"
                      style="width: 50px"
                    >
                      <a
                        href="#"
                        class="shop-tooltip float-none text-light"
                        title=""
                        data-original-title="Clear cart"
                        ><i class="ino ion-md-trash"></i
                      ></a>
                    </th>
                  </tr>
                </thead>
                <tbody id="cartTableBody">
                  <tr>
                    <td>
                      <img src="" class="d-block ui-w-40 ui-bordered mr-4" />
                    </td>
                    <td class="text-center p-4">
                      <div class="media align-items-center">
                        <div class="media-body">
                          <div class="d-block text-dark">測試欄位</div>
                        </div>
                      </div>
                    </td>
                    <td
                      class="text-center font-weight-semibold align-middle p-4"
                    >
                      測試欄位
                    </td>

                    <td class="text-center align-middle px-0">
                      <a
                        href="#"
                        class="shop-tooltip close float-none text-danger"
                        title=""
                        data-original-title="Remove"
                        >×</a
                      >
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            <!-- / Shopping cart table -->

            <div
              class="d-flex flex-wrap justify-content-between align-items-center pb-4"
            >
              <div class="mt-4">
                <!-- <label class="text-muted font-weight-normal">Promocode</label>
                <input type="text" placeholder="ABC" class="form-control" /> -->
              </div>
              <div class="d-flex">
                <div class="text-right mt-4">
                  <label class="text-muted font-weight-normal m-0">總價</label>
                  <div class="text-large">
                    <strong id="totalPrice">$1164.65</strong>
                  </div>
                </div>
              </div>
            </div>
            <div class="float-right">
              <button type="button" class="btn btn-lg btn-primary mt-2">
                結帳
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
    <script>
      // 初始化
      (function inti() {
        getShoppingCartList();
      })();
      // 取得購物車清單
      function getShoppingCartList() {
        let mId = "${member.memberId}";
        axios
          .get("/meeple-masters/shoppingCart/findShoppingCartByMemberId", {
            params: { memberId: mId },
          })
          .then((response) => {
            if (response.data.length == 0) {
              $(".card-body").html("尚未加入商品");
            } else {
              renderShoppingCart(response.data);
              addCartButton();
            }
          })
          .catch((error) => {
            console.log(error);
          });
      }
      // 渲染購物車頁面
      function renderShoppingCart(cartList) {
        let outputString = "";
        let totalPrice = 0;
        for (let p of cartList) {
          outputString += "<tr><td>";
          outputString += `<img src='${root}/mall/getPhoto?pId=\${p.product.productId}' class='d-block ui-w-40 ui-bordered mr-4'/>`;
          outputString += "</td>";
          outputString += "<td class='text-center p-4'>";
          outputString += "<div class='media align-items-center'>";
          outputString += "<div class='media-body'>";
          outputString += `<div class='d-block text-dark'>\${p.product.productName}</div>`;
          outputString += "</div></div></td>";
          outputString +=
            "<td class='text-center font-weight-semibold align-middle p-4'>";
          outputString += `\${p.product.productPrice}元</td>`;
          outputString += "<td class='text-center align-middle px-0'>";
          outputString += `<button class='deleteButton btn btn-danger btn-circle' value='\${p.product.productId}''><i class='fas fa-trash'></i></button>`;
          outputString += "</td></tr>";
          totalPrice += p.product.productPrice;
        }
        totalPrice += "元";
        $("#cartTableBody").html(outputString);
        $("#totalPrice").html(totalPrice);
      }
      //渲染移除購物車商品按鈕
      function addCartButton() {
        let cartButton = document.getElementsByClassName("deleteButton");
        for (i = 0; i < cartButton.length; i++) {
          cartButton[i].addEventListener("click", function () {
            let pId = this.value;
            let mId = "${member.memberId}";
            axios
              .get("${root}/shoppingCart/addShoppingCart", {
                params: {
                  productId: pId,
                  memberId: mId,
                },
              })
              .then((response) => {
                if (response.data == "remove") {
                  getShoppingCartList();
                }
              })
              .catch((error) => console.log(error));
          });
        }
      }
    </script>
  </body>
</html>
