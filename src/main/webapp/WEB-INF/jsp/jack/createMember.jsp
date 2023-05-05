<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
      <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
        <%@ include file="../include/common_link.jsp" %>
          <!DOCTYPE html>
          <html>

          <head>
            <meta charset="UTF-8" />
            <title>${webName}</title>
            <style>
              .loading-spinner {
                width: 30px;
                height: 30px;
                border: 2px solid indigo;
                border-radius: 50%;
                border-top-color: #0001;
                display: inline-block;
                animation: loadingspinner .7s linear infinite;
              }

              @keyframes loadingspinner {
                0% {
                  transform: rotate(0deg)
                }

                100% {
                  transform: rotate(360deg)
                }
              }
            </style>

          </head>

          <body>
            <jsp:include page="../include/header.jsp"></jsp:include>
            <div class="bodyContainer" style="text-align: center">
              <img src="${root}/img/logo.png" class="card-img-top" alt="..." style="width: 200px; margin: 10px" />
              <div class="card container" style="width: 30rem; background-color: #e0ccc5">
                <div class="card-body text-center" id="main-form">
                  <h5 class="card-title" style="color: #8a513f">Sign-up</h5>
                  <form class="needs-validation" novalidate>
                    <div class="form-floating mb-3">
                      <input type="text" class="form-control" id="name" placeholder="Name"
                        style="background-color: rgb(250, 240, 217)" required />
                      <label for="name">Name</label>
                      <div id="name-error" class="invalid-feedback">
                        Your name shouldn't be empty.
                      </div>
                    </div>
                    <div class="form-floating mb-3">
                      <input type="email" class="form-control" id="email" placeholder="name@example.com"
                        style="background-color: rgb(255, 245, 223)" required />
                      <div id="email-error" class="invalid-feedback">

                      </div>
                      <label for="email">Email address</label>
                    </div>
                    <div class="form-floating mb-3">
                      <input type="password" class="form-control" id="password" placeholder="Password"
                        style="background-color: rgb(250, 240, 217)" required />
                      <label for="password">Password</label>
                      <div id="password-error" class="invalid-feedback">

                      </div>
                    </div>
                    <div class="form-floating mb-3">
                      <input type="password" class="form-control" id="confirmPwd" placeholder="Repeat your password"
                        style="background-color: rgb(250, 240, 217)" />
                      <label for="confirmPwd">Repeat your password</label>
                      <div id="confirmPwd-error" class="invalid-feedback">

                      </div>
                    </div>
                    <div class="form-floating mb-3">
                      <input type="text" class="form-control" id="age" placeholder="Age"
                        style="background-color: rgb(250, 240, 217)" />
                      <label for="age">Age</label>
                    </div>
                    <div class="mb-3">
                      <select class="form-select" id="gender" aria-label="Default select example"
                        style="background-color: rgb(250, 240, 217)">
                        <option selected value="">請選擇性別</option>
                        <option value="男">男</option>
                        <option value="女">女</option>
                        <option value="其他">其他</option>
                      </select>
                    </div>
                    <div class="form-floating mb-3">
                      <input type="text" class="form-control" id="tel" placeholder="09xxxxxxxx"
                        style="background-color: rgb(250, 240, 217)" />
                      <label for="tel">Phone</label>
                    </div>
                    <div class="form-floating mb-3">
                      <input type="text" class="form-control" id="address" placeholder="Address"
                        style="background-color: rgb(250, 240, 217)" />
                      <label for="address">Address</label>
                    </div>
                    <span>
                      <input type="checkbox" name="agree" id="agree" />I agree all statement.
                      <a href="#" class="" style="text-decoration: none; color: #8a513f">Terms of service</a>
                      <div id="checkbox-error" class="invalid-feedback">

                      </div>
                    </span>

                    <div class="row justify-content-center is-invalid" style="margin: 20px">
                      <button class="btn btn-outline-secondary btn-sm" type="button" id="confirm">
                        確認
                      </button>
                      <div id="confirm-error" class="invalid-feedback"></div>
                    </div>
                  </form>
                  <button class="btn btn-outline-primary btn-sm" type="button" id="member">
                    Member
                  </button>
                  <button class="btn btn-outline-secondary btn-sm" type="button" id="reset">
                    Reset
                  </button>

                  <div class="modal" id="modal-loading" data-backdrop="static">
                    <div class="modal-dialog modal-sm">
                      <div class="modal-content">
                        <div class="modal-body text-center">
                          <div class="loading-spinner mb-2"></div>
                          <div>驗證信寄送中，請稍後</div>
                        </div>
                      </div>
                    </div>
                  </div>
                  

                </div>
              </div>
            </div>
            <script type="text/javascript" src="${root}/js/jack/createMember.js"></script>

          </body>

          </html>