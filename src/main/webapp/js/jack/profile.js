const memberName = document.querySelector("#memberName");
const address = document.querySelector("#memberAddress");
const tel = document.querySelector("#memberTel");
const gender = document.querySelector("#memberGender");
const age = document.querySelector("#memberAge");
const img = document.querySelector("#memberImg");

const inputs = [memberName, address, tel, gender, age];

if (performance.navigation.type == 2) {
    console.log("Doing reload");
        location.reload(true);
        console.log("Done with reload");
    }
    console.log("Script loaded.")



document.querySelector("#edit").addEventListener("click",function(){
    document.querySelector("#edit").classList.add("d-none");
    document.querySelector("#save").classList.remove("d-none");
    inputs.forEach(function(el,index){
        el.removeAttribute("readonly");
        el.removeAttribute("disabled")

        if(index === 0 ){
            el.focus();
        }
    })
})

document.querySelector("#save").addEventListener("click", function(){
    document.querySelector("#save").classList.add("d-none");
    document.querySelector("#edit").classList.remove("d-none");
    inputs.forEach(function(el,index){
        el.setAttribute("readonly",true);
        el.setAttribute("disabled",true);
        console.log("/meeple-masters/member/updateMember/"+dbId)
    })
    axios.put(`/meeple-masters/member/updateMember/${dbId}`,{
                memberName: memberName.value === ""? null: memberName.value,
                memberAge: age.value === ""? null: age.value,
                memberGender: gender.value === ""? null: gender.value,
                memberTel: tel.value === ""?null: tel.value,
                memberAddress: address.value === ""?null: address.value

    })
    .then(res => {
        console.log(res);
        console.log(res.config.data);
    })
    .catch(err => {
        console.error(err); 
    })

})

let theImg = document.querySelector("#theImg");
let previewImg = document.querySelector("#memberImg");

theImg.addEventListener("change",async function(){
    let f = this.files[0];
    let tempSrc = URL.createObjectURL(f);
    previewImg.src = tempSrc;
    
    let fileReader = new FileReader();
    fileReader.readAsDataURL(f);
    fileReader.onload =async function () {
    console.log(fileReader.result); 
    const config = { headers: {'Content-Type': 'application/json'} };
    
    axios.put(`/meeple-masters/member/updateMemberImg/${dbId}`,
            fileReader.result,
            config)
    .then(res => {
        console.log(res)
        console.log(res.data)
        console.log("content after ajax: " + fileReader.result)
    })
    .catch(err => {
        // console.error(err); 
    })
     };
    
})


