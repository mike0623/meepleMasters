const memberName = document.querySelector("#name");
const email = document.querySelector("#email");
const password = document.querySelector("#password");
const confirmPwd = document.querySelector("#confirmPwd");
const age = document.querySelector("#age");
const gender = document.querySelector("#gender");
const tel = document.querySelector("#tel");
const address = document.querySelector("#address")

const inputs = [memberName, email, password, confirmPwd, age, gender, tel, address];
      

      document.querySelector("#member").addEventListener("click", function () {
        memberName.value = "Test",
        email.value = "456456@gmail.com",
        password.value = "123123123",
        confirmPwd.value = "123123123",
        age.value = 20,
        gender.value = "男",
        tel.value = "0912345678",
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
        
        if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email.value)){
            document.querySelector("#email-error").innerText="Please check your email format.";
            email.classList.add("is-invalid");
        }
        axios.get("/meeple-masters/checkMemberByEmail?memberEmail="+email.value)
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
      
      
      password.addEventListener("blur", function () {
        if(!/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(password.value)){
            document.querySelector("#password-error").innerText="Your password must contain letter and number at least 8 characters long."
            password.classList.add("is-invalid");
          }
      });
      
      
      confirmPwd.addEventListener("blur", function () {
        if (confirmPwd.value != password.value){
            document.querySelector("#confirmPwd-error").innerText="Please check your password."
            confirmPwd.classList.add("is-invalid");
        }
      });
      
      function formatPhoneNumber(phoneNumberString) {
        // 移除字串中的所有非數字字元
        let cleaned = phoneNumberString.replace(/\D/g, '');
      
        // 將字串分成三部分並添加空格
        let formatted = cleaned.replace(/^(\d{4})(\d{3})(\d{3})$/, '$1-$2-$3');
      
        return formatted;
      }

      tel.addEventListener("blur",function(){
        tel.value = formatPhoneNumber(tel.value);
        if(!/^\d{4}-\d{3}-\d{3}$/.test(tel.value)){
          document.querySelector("#tel-error").innerText="Please check your phone number."
          tel.classList.add("is-invalid");
        }
      })
      
      inputs.forEach(function (input) {
        input.addEventListener("input", function (e) {
            if (input.classList.contains("is-invalid")) {
                input.classList.remove("is-invalid");
            }
        });
    });

      document
        .querySelector("#confirm")
        .addEventListener("click", function (e) {
          if (!document.querySelector("#agree").checked) {
            e.preventDefault();
            document.querySelector("#checkbox-error").innerText="Please check the box."
            document.querySelector("#agree").classList.add("is-invalid");
          } 
          if(memberName.value==""||
             !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email.value)||
             !/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(password.value)||
             password.value==""||
             confirmPwd.value==""||
             password.value!=confirmPwd.value||
             tel.value!=""||
             !/^\d{4}-\d{3}-\d{3}$/.test(tel.value)
          ){
            document.querySelector("#confirm-error").classList.remove("d-none");
            document.querySelector("#confirm-error").innerHTML = "請檢查輸入資訊是否有誤";
          }else {
            $('#modal-loading').modal('show');
            axios
              .post("/meeple-masters/createMember", {
                memberName: memberName.value,
                memberEmail: email.value,
                memberPwd: password.value,
                memberAge: age.value === ""? null: age.value,
                memberGender: gender.value === ""? null: gender.value,
                memberTel: tel.value === ""?null: tel.value,
                memberAddress: address.value === ""?null: address.value
              })
              .then((res) => {
                // console.log(res);
                // console.log(data);
                window.location.href = root+"/login";
              })
              .catch((err) => {
                console.error(err);
              });
          }
        });

      document.querySelector("#agree").addEventListener("change", function () {
        document.querySelector("#agree").classList.remove("is-invalid")
      });
    