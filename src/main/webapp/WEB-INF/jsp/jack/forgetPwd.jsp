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
                <div class="card container"
                  style="width: 30rem; background-color: #e0ccc5; position: relative; top:80px;">

                  <div class="card-body  text-center">
                    <h5 class="card-title" style="color: #8a513f">請輸入您註冊的Email</h5>
                    <div class="form-floating mb-3">
                      <input type="email" class="form-control" id="email" placeholder="name@example.com"
                        style="background-color: rgb(255, 245, 223)" />
                      <label for="email">Email</label>
                     
                      <div id="email-error" class="invalid-feedback">
                      </div>
                   
                    </div>
                    
                    
                    
                    <div class="row justify-content-center" style="margin: 20px">
                      <button class="btn btn-outline-secondary" type="button" id="confirm">確認</button>
                    </div>
                    
                    <div class="modal" id="modal-loading" data-backdrop="static">
                        <div class="modal-dialog modal-sm">
                          <div class="modal-content">
                            <div class="modal-body text-center">
                              <div class="loading-spinner mb-2"></div>
                              <div>更改密碼信件寄送中，請稍後</div>
                            </div>
                          </div>
                        </div>
                      </div>
                    
                  </div>
                </div>
            </div>
            <jsp:include page="../include/footer.jsp"></jsp:include>
            <script type="text/javascript" src="${root}/js/jack/forgetPwd.js"></script>


          </body>

          </html>