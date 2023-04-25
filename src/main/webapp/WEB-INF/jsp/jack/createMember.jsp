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
  </head>
  <body>
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
        <div class="card-body container text-center" id="main-form">
          <h5 class="card-title" style="color: #8a513f">Sign-up</h5>
          <form class="needs-validation" novalidate>
            <div class="form-floating mb-3">
              <input
                type="text"
                class="form-control"
                id="name"
                placeholder="Name"
                style="background-color: rgb(250, 240, 217)"
                required
              />
              <label for="name">Name</label>
              <!-- <div class="invalid-feedback">
                Your name can't be empty.
              </div>  -->
            </div>
            <div class="form-floating mb-3">
              <input
                type="email"
                class="form-control"
                id="email"
                placeholder="name@example.com"
                style="background-color: rgb(255, 245, 223)"
                required
              />
              <!-- <div class="invalid-feedback">
                Your email has been used.
              </div>               -->
              <label for="email">Email address</label>
            </div>
            <div class="form-floating mb-3">
              <input
                type="password"
                class="form-control"
                id="password"
                placeholder="Password"
                style="background-color: rgb(250, 240, 217)"
                required
              />
              <label for="password">Password</label>
            </div>
            <div class="form-floating mb-3">
              <input
                type="password"
                class="form-control"
                id="confirmPwd"
                placeholder="Repeat your password"
                style="background-color: rgb(250, 240, 217)"
              />
              <label for="confirmPwd">Repeat your password</label>
            </div>
            <div class="form-floating mb-3">
              <input
                type="text"
                class="form-control"
                id="age"
                placeholder="Age"
                style="background-color: rgb(250, 240, 217)"
              />
              <label for="age">Age</label>
            </div>
            <div class="mb-3">
              <select
                class="form-select"
                id="gender"
                aria-label="Default select example"
                style="background-color: rgb(250, 240, 217)"
              >
                <option selected value="">請選擇性別</option>
                <option value="male">男</option>
                <option value="female">女</option>
                <option value="other">其他</option>
              </select>
            </div>
            <div class="form-floating mb-3">
              <input
                type="text"
                class="form-control"
                id="tel"
                placeholder="09xxxxxxxx"
                style="background-color: rgb(250, 240, 217)"
              />
              <label for="tel">Phone</label>
            </div>
            <div class="form-floating mb-3">
              <input
                type="text"
                class="form-control"
                id="address"
                placeholder="Address"
                style="background-color: rgb(250, 240, 217)"
              />
              <label for="address">Address</label>
            </div>
            <span>
              <input type="checkbox" name="agree" id="agree" />我同意會員條款
              <a href="#" class="" style="text-decoration: none; color: #8a513f"
                >會員條款</a
              >
              <p class="invalid-feedback">Please check the box.</p>
            </span>

            <div
              class="row justify-content-center is-invalid"
              style="margin: 20px"
            >
              <button
                class="btn btn-outline-secondary btn-sm"
                type="button"
                id="confirm"
              >
                確認
              </button>
            </div>
          </form>
          <button
            class="btn btn-outline-primary btn-sm"
            type="button"
            id="member"
          >
            Member
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

    <script>
      //       let data = {
      //     "memberName":document.querySelector('#name').value,
      //     "memberEmail":document.querySelector('#email').value,
      //     "memberPwd":document.querySelector('#password').value,
      //     "memberAge":document.querySelector('#age').value,
      //     "memberGender":document.querySelector('#gender').value,
      //     "memberTel":document.querySelector('#tel').value,
      //     "memberAddress":document.querySelector('#address').value
      // };

      document.querySelector("#member").addEventListener("click", function () {
        document.querySelector("#name").value = "Test";
        document.querySelector("#email").value = "456456@gmail.com";
        document.querySelector("#password").value = "123123123";
        document.querySelector("#confirmPwd").value = "123123123";
        document.querySelector("#age").value = 20;
        document.querySelector("#gender").value = "male";
        document.querySelector("#tel").value = 0900111222;
        document.querySelector("#address").value = "台北市";
      });

      document.querySelector("#reset").addEventListener("click", function () {
        document.querySelector("#name").value = "";
        document.querySelector("#email").value = "";
        document.querySelector("#password").value = "";
        document.querySelector("#confirmPwd").value = "";
        document.querySelector("#age").value = null;
        document.querySelector("#gender").value = "";
        document.querySelector("#tel").value = null;
        document.querySelector("#address").value = "";
      });

      document.querySelector("#name").addEventListener("blur", function () {
        if (document.querySelector("#name").value)
          document.querySelector("#agree").classList.add("is-invalid");
      });

      // document.querySelector("#email").addEventListener("blur",function(){
      //   console.log(document.querySelector('#email').value)
      //   axios.get("/meeple-masters/findMemberByEmail")
      //   .then(res => {
      //     console.log(res)
      //     document.querySelector("#email").classList.add("is-invalid");
      //   })
      //   .catch(err => {
      //     document.querySelector("#email").classList.remove("is-invalid");
      //   }
      // )});

      document
        .querySelector("#confirm")
        .addEventListener("click", function (e) {
          if (!document.querySelector("#agree").checked) {
            e.preventDefault();
            document.querySelector("#agree").classList.add("is-invalid");
          } else {
            console.log(document.querySelector("#address").value);
            axios
              .post("/meeple-masters/createMember", {
                memberName: document.querySelector("#name").value,
                memberEmail: document.querySelector("#email").value,
                memberPwd: document.querySelector("#password").value,
                memberAge: document.querySelector("#age").value,
                memberGender: document.querySelector("#gender").value,
                memberTel: document.querySelector("#tel").value,
                memberAddress: document.querySelector("#address").value,
              })
              .then((res) => {
                console.log(res);
                console.log(data);
              })
              .catch((err) => {
                // console.error(err);
              });
          }
        });

      document.querySelector("#agree").addEventListener("change", function () {
        document.querySelector("#agree").classList.remove("is-invalid");
      });
    </script>
  </body>
</html>
