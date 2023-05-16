const banButton = document.querySelector("#banButton");

banButton.addEventListener("click", function(){
    if(banButton.classList.contains("btn-danger")){
        console.log("停權")
        banButton.classList.remove("btn-danger");
        banButton.classList.add("btn-info");
        banButton.innerHTML = "取消停權";
    }
    if(banButton.classList.contains("btn-info")){
        console.log("恢復")
        banButton.classList.remove("btn-info");
        banButton.classList.add("btn-danger");
        banButton.innerHTML = "停權";
    }
})



// window.addEventListener("load", function(){
//     axios.get("/meeple-masters/admin/findAllMember")
//     .then(res => {
//         console.log(res)
//         $("#memberList").empty()
//         $.each(res.data, function (index, member) {
//         $("#memberList").append(
//             '<tr>'+
//             '<td>'+member.memberId+'</td>'+
//             '<td><img src="'+root+'/member/findMemberImg/'+ member.memberId +'" alt="" width="32" height="32" class="rounded-circle">'+  member.memberName+'</td>'+
//             '<td>'+member.memberEmail+'</td>'+
//             '<td>'+member.memberBirth+'</td>'+
//             '<td>'+member.memberGender+'</td>'+
//             '<td>'+member.memberTel+'</td>'+
//             '<td>'+member.memberAddress+'</td>'+
//             '<td>'+member.memberCoin+'</td>'+
//             '<td>'+member.memberActive+'</td>'+
//             '<td>'+member.createTime+'</td>'+
//             '</tr>'

//         );
//     });

//     })
//     .catch(err => {
//         console.error(err); 
//     })

// })

