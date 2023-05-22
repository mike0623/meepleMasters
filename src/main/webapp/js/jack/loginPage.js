const email = document.querySelector("#email");
const password = document.querySelector("#password");

// admin按鈕
document.querySelector("#admin").addEventListener("click", function () {
    email.value = "AAA@gmail.com",
    password.value = "asd123123"
  });

// member1按鈕
document.querySelector("#member1").addEventListener("click", function () {
    email.value = "BBB@gmail.com",
    password.value = "asd123123"
  });

// member2按鈕
document.querySelector("#member2").addEventListener("click", function () {
    email.value = "CCC@gmail.com",
    password.value = "asd123123"
  });

// member3按鈕
document.querySelector("#member3").addEventListener("click", function () {
  email.value = "DDD@gmail.com",
  password.value = "asd123123"
});

// member4按鈕 新帳號
document.querySelector("#member4").addEventListener("click", function () {
  email.value = "testmeeple@gmail.com",
  password.value = "eeit1622"
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
            console.error(err); 
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


// Remember me
// document.querySelector("#login").addEventListener("click",function(){

// // 獲取當前時間
// const now = new Date();

// // 當前時間加上兩周時間
// const twoWeeksLater = new Date(now.getTime() + (14 * 24 * 60 * 60 * 1000));

// // 將時間轉為GMT String
// const expires = twoWeeksLater.toUTCString();

// if (document.querySelector("#remember").checked) {
//   document.cookie = `email=${member.memberEmail}; expires=${expires}; path=/`;
//   document.cookie = `password=${member.memberPwd}; expires=${expires}; path=/`;
// }

// window.addEventListener("load", function() {
//   const cookieEmail = getCookie(email);
//   const cookiePwd = getCookie(password);
//   console.log(cookieEmail)
//   console.log(cookiePwd)
//   if (cookieEmail && cookiePwd) {
//     email.value = cookieEmail;
//     password.value = cookiePwd;
//     rememberMeCheckbox.checked = true;
//   }
// });

// function getCookie(name) {
//   const cookies = document.cookie.split("; ");
//   for (const cookie of cookies) {
//     const [cookieName, cookieValue] = cookie.split("=");
//     if (cookieName === name) {
//       return cookieValue;
//     }
//   }
//   return null;
// }
// })
