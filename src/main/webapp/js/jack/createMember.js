const memberName = document.querySelector("#name");
const email = document.querySelector("#email");
const password = document.querySelector("#password");
const confirmPwd = document.querySelector("#confirmPwd");
const age = document.querySelector("#age");
const gender = document.querySelector("#gender");
const tel = document.querySelector("#tel");
const address = document.querySelector("#address")

const inputs = [memberName, email, password, confirmPwd, age, gender, tel, address];
      //      let data = {
      //     "memberName":document.querySelector('#name').value,
      //     "memberEmail":document.querySelector('#email').value,
      //     "memberPwd":document.querySelector('#password').value,
      //     "memberAge":document.querySelector('#age').value,
      //     "memberGender":document.querySelector('#gender').value,
      //     "memberTel":document.querySelector('#tel').value,
      //     "memberAddress":document.querySelector('#address').value
      // };

      document.querySelector("#member").addEventListener("click", function () {
        memberName.value = "Test",
        email.value = "456456@gmail.com",
        password.value = "123123123",
        confirmPwd.value = "123123123",
        age.value = 20,
        gender.value = "男",
        tel.value = "0900111222",
        address.value = "台北市"
      });

      document.querySelector("#reset").addEventListener("click", function () {
        memberName.value = "",
        email.value = "",
        password.value = "",
        confirmPwd.value = "",
        age.value = null,
        gender.value = "",
        tel.value = "",
        address.value = ""
      });

      memberName.addEventListener("blur", function () {
        if (!memberName.value){
            memberName.classList.add("is-invalid")
        }
      });
      
      email.addEventListener("blur",function(){
        
        if (!email.value.includes("@"&&".")){
            document.querySelector("#email-error").innerText="Please check your email format.";
            email.classList.add("is-invalid");
        }
        axios.get("/meeple-masters/member/checkMemberByEmail?memberEmail="+email.value)
        .then(res => {
            console.log(res)
            if (res.status == 200) {
                document.querySelector("#email-error").innerText="Your email has been used."
                email.classList.add("is-invalid");
            }
            if(res.status == 204){
                
            }
        })
        .catch(err => {
            // console.error(err); 
        })
      })

    //   email.addEventListener("blur", function () {
        
    //   });
      
      
      password.addEventListener("blur", function () {
        if (!password.value){
            document.querySelector("#password-error").innerText="Your password shouldn't be empty."
            password.classList.add("is-invalid");
        }
      });
      
      
      confirmPwd.addEventListener("blur", function () {
        if (confirmPwd.value != password.value){
            document.querySelector("#confirmPwd-error").innerText="Please check your password."
            confirmPwd.classList.add("is-invalid");
        }
      });
      
      
      inputs.forEach(function (input) {
        input.addEventListener("input", function (e) {
            if (input.classList.contains("is-invalid")) {
                input.classList.remove("is-invalid");
            }
        });
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
            document.querySelector("#checkbox-error").innerText="Please check the box."
            document.querySelector("#agree").classList.add("is-invalid");
          } 
          if(memberName.value==""||
             !email.value.includes("@")||
             password.value==""||
             confirmPwd.value==""||
             password.value!=confirmPwd.value
          ){
            document.querySelector("#confirm-error").innerText="Please check your information."
            document.querySelector("#confirm").classList.add("is-invalid");
          }else {
            axios
              .post("/meeple-masters/member/createMember", {
                memberName: memberName.value,
                memberEmail: email.value,
                memberPwd: password.value,
                memberAge: age.value === ""? null: age.value,
                memberGender: gender.value === ""? null: gender.value,
                memberTel: tel.value === ""?null: tel.value,
                memberAddress: address.value === ""?null: address.value
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
        document.querySelector("#agree").classList.remove("is-invalid")
      });
    