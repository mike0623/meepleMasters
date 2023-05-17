const email = document.querySelector("#email")

document.querySelector("#confirm").addEventListener("click",function(){
    console.log("123")
    $('#modal-loading').modal('show');
    // if(!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email.value)){
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

    // }
})