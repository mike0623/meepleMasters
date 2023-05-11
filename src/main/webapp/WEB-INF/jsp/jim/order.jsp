<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
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
        background-color: #04aa6d;
        color: white;
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
      <!-- <p>
        Resize the browser window to see the effect. When the screen is less
        than 800px wide, make the two columns stack on top of each other instead
        of next to each other.
      </p> -->
      <div class="row">
        <div class="col-25"></div>

        <div class="col-50">
          <h2>確認訂單</h2>
          <div class="container2">
            <form action="#">
              <div class="row">
                <div class="col-50">
                  <h3>帳單地址</h3>
                  <label for="fname"><i class="fa fa-user"></i> 購買人</label>
                  <input
                    type="text"
                    id="fname"
                    value="${member.memberName}"
                    disabled
                  />
                  <label for="email"><i class="fa fa-envelope"></i> 信箱</label>
                  <input
                    type="text"
                    id="email"
                    placeholder="john@example.com"
                    value="${member.memberEmail}"
                    disabled
                  />
                  <label for="city"
                    ><i class="fa fa-institution"></i> 城市</label
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
                    ><i class="fa fa-address-card-o"></i> 電話</label
                  >
                  <input
                    type="text"
                    id="adr"
                    value="${member.memberTel}"
                    disabled
                  />
                  <!-- <div class="row">
                    <div class="col-50">
                      <label for="state">State</label>
                      <input
                        type="text"
                        id="state"
                        name="state"
                        placeholder="NY"
                      />
                    </div>
                    <div class="col-50">
                      <label for="zip">Zip</label>
                      <input
                        type="text"
                        id="zip"
                        name="zip"
                        placeholder="10001"
                      />
                    </div>
                  </div> -->
                </div>
                <div class="col-50">
                  <div class="container">
                    <h4>
                      購物車
                      <span class="price" style="color: black"
                        ><i class="fa fa-shopping-cart"></i> <b>4</b></span
                      >
                    </h4>
                    <p>
                      <a href="#">Product 1</a> <span class="price">$15</span>
                    </p>
                    <p>
                      <a href="#">Product 2</a> <span class="price">$5</span>
                    </p>
                    <p>
                      <a href="#">Product 3</a> <span class="price">$8</span>
                    </p>
                    <p>
                      <a href="#">Product 4</a> <span class="price">$2</span>
                    </p>
                    <hr />
                    <p>
                      總價
                      <span class="price" style="color: black"><b>$30</b></span>
                    </p>
                    <input type="submit" value="確定送出" class="btn2" />
                  </div>
                </div>
              </div>
            </form>
          </div>
        </div>
        <div class="col-25"></div>
      </div>
    </div>
    <jsp:include page="../include/footer.jsp" />
  </body>
</html>
