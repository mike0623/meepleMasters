<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>${webName}</title>
    <jsp:include page="../include/common_link.jsp" />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
    />
    <link rel="stylesheet" type="text/css" href="${root}/css/index.css" />
    <style>
      body {
        font-family: Arial;
        font-size: 17px;
        padding: 8px;
      }

      * {
        box-sizing: border-box;
      }

      .row {
        display: -ms-flexbox; /* IE10 */
        display: flex;
        -ms-flex-wrap: wrap; /* IE10 */
        flex-wrap: wrap;
        margin: 0 -16px;
      }

      .col-25 {
        -ms-flex: 25%; /* IE10 */
        flex: 25%;
      }

      .col-50 {
        -ms-flex: 50%; /* IE10 */
        flex: 50%;
      }

      .col-75 {
        -ms-flex: 75%; /* IE10 */
        flex: 75%;
      }

      .col-25,
      .col-50,
      .col-75 {
        padding: 0 16px;
      }

      .container2 {
        background-color: #f2f2f2;
        padding: 5px 20px 15px 20px;
        border: 1px solid lightgrey;
        border-radius: 3px;
      }

      input[type="text"] {
        width: 100%;
        margin-bottom: 20px;
        padding: 12px;
        border: 1px solid #ccc;
        border-radius: 3px;
      }

      label {
        margin-bottom: 10px;
        display: block;
      }

      .icon-container {
        margin-bottom: 20px;
        padding: 7px 0;
        font-size: 24px;
      }

      .btn2 {
        background-color: #f3d18e;
        color: #613e3b;
        padding: 12px;
        margin: 10px 0;
        border: none;
        width: 100%;
        border-radius: 3px;
        cursor: pointer;
        font-size: 17px;
      }

      .btn:hover {
        background-color: #45a049;
      }

      a {
        color: #2196f3;
      }

      hr {
        border: 1px solid lightgrey;
      }

      span.price {
        float: right;
        color: grey;
      }
      /* Responsive layout - when the screen is less than 800px wide, make the two columns stack on top of each other instead of next to each other (also change the direction - make the "cart" column go on top) */
      @media (max-width: 800px) {
        .row {
          flex-direction: column-reverse;
        }
        .col-25 {
          margin-bottom: 20px;
        }
      }
    </style>
  </head>
  <body>
    <jsp:include page="../include/header.jsp" />
    <div class="bodyContainer">
      <div class="container px-3 my-5 clearfix">
        <div class="row">
          <div class="col-50">
            <h2>確認訂單</h2>
            <div class="container2">
              <form action="${root}/order/ecPay" id="orderForm">
                <div class="row">
                  <div class="col-50">
                    <h3>使用者資訊</h3>
                    <label for="fname"><i class="fa fa-user"></i> 使用者</label>
                    <input
                      type="text"
                      id="fname"
                      value="${member.memberName}"
                      disabled
                    />
                    <label for="email"
                      ><i class="fa fa-envelope"></i> 信箱</label
                    >
                    <input
                      type="text"
                      id="email"
                      placeholder="john@example.com"
                      value="${member.memberEmail}"
                      disabled
                    />
                    <label for="city"
                      ><i class="fa fa-institution"></i> 居住城市</label
                    >
                    <input
                      type="text"
                      id="city"
                      name="city"
                      placeholder="New York"
                      value="${member.memberAddress}"
                      disabled
                    />
                    <label for="adr"
                      ><i class="fa fa-address-card-o"></i> 聯絡電話</label
                    >
                    <input
                      type="text"
                      id="adr"
                      value="${member.memberTel}"
                      disabled
                    />
                  </div>

                  <div class="col-50">
                    <div class="container">
                      <h4>
                        購物車<span class="price" style="color: black">
                          <i class="fa fa-shopping-cart"></i>
                          <b>${orderDetails.size()}</b></span
                        >
                      </h4>
                      <c:forEach items="${orderDetails}" var="od">
                        <p>
                          <span>${od.product.productName}</span>
                          <span class="price">${od.product.productPrice}</span>
                        </p>
                      </c:forEach>
                      <hr />
                      <p>
                        總價
                        <span class="price" style="color: black"
                          ><b>${order.totalPrice}</b></span
                        >
                      </p>
                      <label for="paymentMethod">付款方式</label>
                      <select name="paymentMethod" id="paymentMethod">
                        <option value="綠界">綠界</option>
                        <option value="LinePay">LinePay</option>
                      </select>
                      <input type="submit" value="送出" class="btn2" />
                    </div>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
    <jsp:include page="../include/footer.jsp" />
    <script>
      // 選擇付款方式時改變form表單的導向
      $("#paymentMethod").on("change", function () {
        console.log(this.value);
        if (this.value == "綠界") {
          document.getElementById("orderForm").action = "${root}/order/ecPay";
        }
        if (this.value == "LinePay") {
          document.getElementById("orderForm").action = "${root}/order/linePay";
        }
      });

      document
        .querySelector("#orderForm")
        .addEventListener("submit", function (e) {
          e.preventDefault();
          Swal.fire({
            title: "確定結帳",
            icon: "warning",
            showCancelButton: true,
            confirmButtonText: "確定",
            cancelButtonText: "取消",
          }).then((result) => {
            if (result.isConfirmed) {
              this.submit();
            } else if (result.dismiss === Swal.DismissReason.cancel) {
            }
          });
        });
    </script>
  </body>
</html>
