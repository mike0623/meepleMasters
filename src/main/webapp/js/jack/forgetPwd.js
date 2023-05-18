const email = document.querySelector("#email")


document.querySelector("#confirm").addEventListener("click",function(e){
    if(!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email.value)){
        console.log("123")
        e.preventDefault();
    }else{
        $('#modal-loading').modal('show');
        
        axios.post(root+"/forgetPwdCheckEmail",{
            memberEmail : email.value
        })
        .then(res => {
            console.log(res)
            
            window.location.href = root + "/";
        })
        .catch(err => {
            console.error(err); 
        })
    
    }
})

email.addEventListener("blur",function(){
    if(!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email.value)){
        document.querySelector("#email-error").innerText = "請檢查輸入Email格式是否有誤"
        email.classList.add("is-invalid");
    }
})

email.addEventListener("focus",function(){
    email.classList.remove("is-invalid");
})