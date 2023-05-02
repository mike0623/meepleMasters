const email = document.querySelector("#email");
const password = document.querySelector("#password");

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