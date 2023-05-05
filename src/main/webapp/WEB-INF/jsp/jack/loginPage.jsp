<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> <%@
include file="../include/common_link.jsp" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>${webName}</title>
    <style>
    .card{
    	text-align: center;
    }
    </style>
  </head>
  <body>
    <jsp:include page="../include/header.jsp"></jsp:include>
    <div
      class="bodyContainer"
      style="text-align: center"
    >
      <img
        src="${root}/img/logo.png"
        class="card-img-top"
        alt="..."
        style="width: 200px; margin: 10px"
      />
      <form action="${root}/member/login" method="post">
      <div class="card container" style="width: 30rem; background-color: #e0ccc5; position: relative; top:80px">
      
        <div class="card-body  text-center" >
          <h5 class="card-title" style="color: #8a513f">Login</h5>
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
            <input type="checkbox" name="agree" id="agree" />Remember me.&nbsp;&nbsp;&nbsp;   
            
            <div id="checkbox-error" class="invalid-feedback">

            </div>
          </span>
          <div id="login-error" class="d-none alert alert-danger alert-dismissible fade show" role="alert">
            <strong>Login error!</strong> Your email or password is empty.
          </div>
          <div class="row justify-content-center" style="margin: 20px">
            <button class="btn btn-outline-secondary" type="button" id="login">Login</button>
          </div>
          <div class="row justify-content-center" style="margin: 20px">
            <a href="${root}/register" class="btn btn-outline-secondary">Register</a>
          </div>
          <div class="col align-self-center">
          	Other way to login
          </div>
          <div class="row justify-content-center" style="margin: 20px">
            <a href="#" class="btn btn-outline-secondary">Google</a>
        </div>
        <a href="#" class="" style="text-decoration: none; color: #8a513f">
          Terms of service
        </a>
        <div>

        <button
          class="btn btn-outline-primary btn-sm"
          type="button"
          id="admin"
        >
          admin
        </button>
        <button
            class="btn btn-outline-primary btn-sm"
            type="button"
            id="member1"
          >
            Member1
          </button>
          <button
            class="btn btn-outline-primary btn-sm"
            type="button"
            id="member2"
          >
            Member2
          </button>
          <button
            class="btn btn-outline-secondary btn-sm"
            type="button"
            id="reset"
          >
            Reset
          </button>
        </div>
        </div>
      </div>
        </form>
    </div>
    
    <script type="text/javascript" src="${root}/js/jack/loginPage.js"></script>

 
  </body>
</html>
