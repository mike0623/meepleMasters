const email = document.querySelector("#email");
const password = document.querySelector("#password");

document.querySelector("#login").addEventListener("click",function(e){
    if (!document.querySelector("#agree").checked) {
        e.preventDefault();
        document.querySelector("#checkbox-error").innerText="Please check the box."
        document.querySelector("#agree").classList.add("is-invalid");
      }else{
        axios.post("/meeple-masters/member/login",{
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
      }

})

document.querySelector("#agree").addEventListener("change", function () {
    document.querySelector("#agree").classList.remove("is-invalid")
  });