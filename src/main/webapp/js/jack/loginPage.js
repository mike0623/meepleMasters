const email = document.querySelector("#email");
const password = document.querySelector("#password");

// admin按鈕
document.querySelector("#admin").addEventListener("click", function () {
    email.value = "AAA@gmail.com",
    password.value = "1234"
  });

// member1按鈕
document.querySelector("#member1").addEventListener("click", function () {
    email.value = "BBB@gmail.com",
    password.value = "1234"
  });

// member2按鈕
document.querySelector("#member2").addEventListener("click", function () {
    email.value = "CCC@gmail.com",
    password.value = "1234"
  });
  
  // reset按鈕
  document.querySelector("#reset").addEventListener("click", function () {
    email.value = "",
    password.value = ""
  });

// login
document.querySelector("#login").addEventListener("click",function(e){
    if(email.value ==="" || password.value ===""){
        e.preventDefault;
        document.querySelector("#login-error").classList.remove("d-none");
    }else{
		axios.post("/meeple-masters/login",{
            memberEmail:email.value,
            memberPwd:password.value
        })
        .then(res => {
            console.log(res);
            var url = res.data.url;
            window.location.href= root+ url;


        })
        .catch(err => {
            // console.error(err); 
        })
    
//    if (!document.querySelector("#agree").checked) {
//        e.preventDefault();
//        document.querySelector("#checkbox-error").innerText="Please read the terms of service and check the box."
//        document.querySelector("#agree").classList.add("is-invalid");
//      }else{
//        
//      }
    }
})

email.addEventListener("focus", function(){
    document.querySelector("#login-error").classList.add("d-none");
})

password.addEventListener("focus", function(){
    document.querySelector("#login-error").classList.add("d-none");
})

document.querySelector("#agree").addEventListener("change", function () {
    document.querySelector("#agree").classList.remove("is-invalid")
  });