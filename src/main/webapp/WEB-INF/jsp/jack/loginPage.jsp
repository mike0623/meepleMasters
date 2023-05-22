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
              .card {
                text-align: center;
              }

              #login-error {
                text-align: center;
              }
            </style>
          </head>

          <body>
            <jsp:include page="../include/header.jsp"></jsp:include>
            <div class="bodyContainer" style="text-align: center">
              <img src="${root}/img/logo.png" class="card-img-top" alt="..." style="width: 200px; margin: 10px" />
              <!-- <form action="${root}/member/login" method="post"> -->
                <div class="card container"
                  style="width: 30rem; background-color: #e0ccc5; position: relative; top:80px; margin-bottom:120;">

                  <div class="card-body  text-center">
                    <h5 class="card-title" style="color: #8a513f">登入</h5>
                    <div class="form-floating mb-3">
                      <input type="email" class="form-control" id="email" placeholder="name@example.com"
                        style="background-color: rgb(255, 245, 223)" />
                      <label for="email">Email</label>
                    </div>
                    <div class="form-floating mb-3">
                      <input type="password" class="form-control" id="password" placeholder="Password"
                        style="background-color: rgb(250, 240, 217)" />
                      <label for="password">密碼</label>
                    </div>
                    <div>
                      <a href="${root}/forgetPwd">忘記密碼</a>
                    </div>
                    <div id="login-error" class="d-none alert alert-danger alert-dismissible fade show" role="alert">
                      <strong>錯誤!</strong> 帳號密碼不能空白
                    </div>
                    <div class="row justify-content-center" style="margin: 20px">
                      <button class="btn btn-outline-secondary" type="button" id="login">登入</button>
                    </div>
                    <div class="row justify-content-center" style="margin: 20px">
                      <a href="${root}/register" class="btn btn-outline-secondary">註冊</a>
                    </div>
                    <div class="col align-self-center">
                      其他登入方式
                    </div>
                    <div class="row justify-content-center" style="margin: 20px">
                      <a href="${root}/googleLogin" class="btn btn-outline-secondary">Google</a>
                    </div>
                    <a href="#" class="" style="text-decoration: none; color: #8a513f">
                      Terms of service
                    </a>
                    <div>

                      <button class="btn btn-outline-primary btn-sm" type="button" id="admin">
                        admin
                      </button>
                      <button class="btn btn-outline-primary btn-sm" type="button" id="member1">
                        Member1
                      </button>
                      <button class="btn btn-outline-primary btn-sm" type="button" id="member2">
                        Member2
                      </button>
                      <button class="btn btn-outline-primary btn-sm" type="button" id="member3">
                        Member3
                      </button>
                      <button class="btn btn-outline-danger btn-sm" type="button" id="member4">
                        新帳號
                      </button>
                      <div style="margin-top: 10px;">
                        <button class="btn btn-outline-secondary btn-sm" type="button" id="reset">
                          Reset
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              <!-- </form> -->
            </div>
            <jsp:include page="../include/footer.jsp"></jsp:include>
            <script type="text/javascript" src="${root}/js/jack/loginPage.js"></script>


          </body>

          </html>