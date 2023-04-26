<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> <%@
include file="../include/common_link.jsp" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Insert title here</title>
    <style></style>
  </head>
  <body>
    <!-- <jsp:include page="../include/header.jsp"></jsp:include> -->
    <div
      class="bodyContainer position-absolute top-50 start-50"
      style="text-align: center"
    >
      <img
        src="${root}/img/logo.png"
        class="card-img-top"
        alt="..."
        style="width: 200px; margin: 10px"
      />
      <form action="${root}/member/login" method="post">
      <div class="card" style="width: 30rem; background-color: #e0ccc5">
        <div class="card-body container text-center">
          <h5 class="card-title" style="color: #8a513f">會員登入</h5>
          <div class="form-floating mb-3">
            <input
              type="email"
              class="form-control"
              id="email"
              placeholder="name@example.com"
              style="background-color: rgb(255, 245, 223)"
            />
            <label for="email">Email address</label>
          </div>
          <div class="form-floating mb-3">
            <input
              type="password"
              class="form-control"
              id="password"
              placeholder="Password"
              style="background-color: rgb(250, 240, 217)"
            />
            <label for="password">Password</label>
          </div>
          <span>
            <input type="checkbox" name="agree" id="agree" />我同意會員條款
            <a href="#" class="" style="text-decoration: none; color: #8a513f"
              >會員條款</a
            >
            <div id="checkbox-error" class="invalid-feedback">

            </div>
          </span>
          <div class="row justify-content-center" style="margin: 20px">
            <button class="btn btn-outline-secondary" type="button" id="login">登入</button>
          </div>
        </form>
          <div class="row justify-content-center" style="margin: 20px">
            <a href="${root}/member/register" class="btn btn-outline-secondary">註冊會員</a>
          </div>
          <div class="col align-self-center">使用其他登入方式</div>
          <div class="row justify-content-center" style="margin: 20px">
            <a href="#" class="btn btn-outline-secondary">Google</a>
        </div>
      </div>
    </div>
    <script>const root = "${root}" </script>
    <script type="text/javascript" src="${root}/js/jack/loginPage.js"></script>

    <!-- <jsp:include page="../include/footer.jsp"></jsp:include> -->
  </body>
</html>
