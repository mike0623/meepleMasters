const search = document.querySelector("#searchMember");
$("#result").empty()

search.addEventListener("keyup", function () {
  axios.post("/meeple-masters/member/findmemberByName", {
    memberName: search.value
  })
    .then(res => {

      console.log(res)
      $("#result").empty()
      if (search.value !== "") {
        $.each(res.data, function (index, member) {
          console.log(member.memberLevel)
          if(member.memberLevel !=="管理員"){

            $("#result").append(
              '<a href="' + root + '/member/myProfile/' + member.memberId + '"' + 'class="list-group-item list-group-item-action">' +
              '<img src="' + root + '/member/findMemberImg/' + member.memberId + '"' + 'class="rounded-circle" width="50px" height="50px">' +
              '&nbsp; &nbsp;' + member.memberName + '</a>'
              
              );
              
            }

        });
      }
     
    })
    .catch(err => {
      console.error(err);
    })
})



result.addEventListener("click", function () {
  $("#result").empty()
})

document.body.addEventListener("click",function(){
  $("#result").empty()
},true)

