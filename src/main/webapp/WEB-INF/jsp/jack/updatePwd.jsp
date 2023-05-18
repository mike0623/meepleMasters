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
              <form action="${root}/updateConfirm" method="post" id="updatePwd">

                <input type="text" value="${memberEmail}" style="display: none;" name="email">

                <div class="card container"
                  style="width: 30rem; background-color: #e0ccc5; position: relative; top:80px; margin-bottom: 100;">

                  <div class="card-body  text-center">
                    <h5 class="card-title" style="color: #8a513f">更改密碼</h5>
                    <div class="form-floating mb-3">
                      <input type="password" class="form-control" id="password"
                        style="background-color: rgb(255, 245, 223)" name="password" />
                      <label for="password">新密碼</label>

                      <div id="password-error" class="invalid-feedback">
                      </div>

                    </div>

                    <div class="form-floating mb-3">
                      <input type="password" class="form-control" id="confirmPwd"
                        style="background-color: rgb(250, 240, 217)" />
                      <label for="confirmPwd">重複您的密碼</label>

                      <div id="confirmPwd-error" class="invalid-feedback">
                      </div>

                    </div>

                    <div id="pwd-error" class="d-none alert alert-danger alert-dismissible fade show" role="alert">
                      <strong>錯誤!</strong> 密碼不能空白
                    </div>

                    <div class="row justify-content-center is-invalid" style="margin: 20px">
                      <button class="btn btn-outline-secondary btn-sm" type="button" id="confirm">
                        確認
                      </button>
                      <div id="confirm-error" class="d-none alert alert-danger alert-dismissible fade show" role="alert"
                        style="text-align:center;">
                      </div>
                    </div>
                    

              </form>
            </div>


            </div>

            

            <script type="text/javascript" src="${root}/js/jack/updatePwd.js"></script>
            
            
          </body>

          </html>