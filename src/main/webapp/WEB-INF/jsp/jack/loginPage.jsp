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
    <jsp:include page="../include/header.jsp"></jsp:include>
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
      <div class="card" style="width: 30rem; background-color: #e0ccc5">
        <div class="card-body container text-center">
          <h5 class="card-title" style="color: #8a513f">會員登入</h5>
          <div class="form-floating mb-3">
            <input
              type="email"
              class="form-control"
              id="floatingInput"
              placeholder="name@example.com"
              style="background-color: rgb(255, 245, 223)"
            />
            <label for="floatingInput">Email address</label>
          </div>
          <div class="form-floating">
            <input
              type="password"
              class="form-control"
              id="floatingPassword"
              placeholder="Password"
              style="background-color: rgb(250, 240, 217)"
            />
            <label for="floatingPassword">Password</label>
          </div>
          <span>
            <input type="checkbox" name="agree" id="agree" />我同意會員條款
          </span>
          <a href="#" class="" style="text-decoration: none; color: #8a513f"
            >會員條款</a
          >
          <div class="row justify-content-center" style="margin: 20px">
            <a href="#" class="btn btn-outline-secondary">登入</a>
          </div>
          <div class="row justify-content-center" style="margin: 20px">
            <a href="/createPage" class="btn btn-outline-secondary">註冊會員</a>
          </div>
          <div class="col align-self-center">使用其他登入方式</div>
          <div class="row justify-content-center" style="margin: 20px">
            <a href="#" class="btn btn-outline-secondary">Google</a>
          </div>
        </div>
      </div>
    </div>

    <jsp:include page="../include/footer.jsp"></jsp:include>
  </body>
</html>
