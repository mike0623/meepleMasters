<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/common_link.jsp" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../include/friend/friendList.jsp"></jsp:include>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<input type="text" id="selectedFriend"><button onclick="searchFriend()">搜尋好友</button>
<div class="friendButtonDiv">
	
</div>



<script>
	var isSelectedPage = true;
	var selectedFriendEmail = "";
	var selectedFriendName = "";
	var idTypeEmail = "";
	var buttonTypeName = "";
	function searchFriend(){
		//----------------------------------------------------------假資料
		if("A" == $("#selectedFriend").val()){
			selectedFriendEmail = "AAA@gmail.com";
			selectedFriendName = "Andy";
			idTypeEmail = changeToIdType(selectedFriendEmail);
			buttonTypeName = selectedFriendName+"button";
		}
		if("B" == $("#selectedFriend").val()){
			selectedFriendEmail = "BBB@gmail.com";
			selectedFriendName = "Betty";
			idTypeEmail = changeToIdType(selectedFriendEmail);
			buttonTypeName = selectedFriendName+"button";
		}
		if("C" == $("#selectedFriend").val()){
			selectedFriendEmail = "CCC@gmail.com";
			selectedFriendName = "Cart";
			idTypeEmail = changeToIdType(selectedFriendEmail);
			buttonTypeName = selectedFriendName+"button";
		}
		if("D" == $("#selectedFriend").val()){
			selectedFriendEmail = "DDD@gmail.com";
			selectedFriendName = "David";
			idTypeEmail = changeToIdType(selectedFriendEmail);
			buttonTypeName = selectedFriendName+"button";
		}
		if("E" == $("#selectedFriend").val()){
			selectedFriendEmail = "EEE@gmail.com";
			selectedFriendName = "Emily";
			idTypeEmail = changeToIdType(selectedFriendEmail);
			buttonTypeName = selectedFriendName+"button";
		}
		if("F" == $("#selectedFriend").val()){
			selectedFriendEmail = "FFF@gmail.com";
			selectedFriendName = "Frimon";
			idTypeEmail = changeToIdType(selectedFriendEmail);
			buttonTypeName = selectedFriendName+"button";
		}
		//-------------------------------------------------------------
		//先清空
		$(".friendButtonDiv").empty();
		//判斷是不是好友
		axios.get("http://localhost:8080/meeple-masters/friend/isFriend/${member.memberEmail}/"+selectedFriendEmail).then(function(response){
			if("isFriend"==response.data){
				console.log("是好友了");
				$(".friendButtonDiv").append(`
						<button onclick="pressChatButton('`+buttonTypeName+`','`+idTypeEmail+`')">傳送訊息</button>
						<button onclick="deleteFriend('${member.memberEmail}','`+selectedFriendEmail+`')">刪除好友</button>
						`)
				return;
			}
			//若不是好友，判斷有沒有誰發出過邀請
			axios.get("http://localhost:8080/meeple-masters/friendInvite/isExist/${member.memberEmail}/"+selectedFriendEmail).then(function(response){
				if("${member.memberEmail}"==response.data.inviter){
					console.log("我發出邀請了");
					//我發出邀請了，對方沒接受
					$(".friendButtonDiv").empty();
					$(".friendButtonDiv").append(`
							<button onclick="deleteFriendInvite('${member.memberEmail}','`+selectedFriendEmail+`')">取消邀請</button>
							`);
					return;
				}
				if(selectedFriendEmail==response.data.inviter){
					console.log("對方發出邀請了");
					//對方發出邀請了，我沒接受
					$(".friendButtonDiv").empty();
					$(".friendButtonDiv").append(`
							<button onclick="acceptFriendInvite('${member.memberEmail}','`+selectedFriendEmail+`')">接受邀請</button>
							`);
					return;
				}
				console.log("不是朋友，且誰都沒發出邀請");
				//不是好友也不存在邀請
				$(".friendButtonDiv").empty();
				$(".friendButtonDiv").append(`
						<button onclick="sendFriendInvite('${member.memberEmail}','`+selectedFriendEmail+`')">加好友</button>
						`);
				
			}).catch(function(error){
				console.log("判斷誰發過請出錯啦!",error);
			}).finally(function(){
				
			});
			
			
			
		}).catch(function(error){
			console.log("判斷是不是好友出錯啦!",error);
		}).finally(function(){
			
		});
	}
	
	
	
	//按下刪除好友按鈕後
	function deleteFriend(myself,theOther){
		//詢問是否確定要刪除資料
		bootbox.confirm({
			title: "刪除好友",
			message: '確定要解除好友關係？',
			buttons: {
				confirm: {
					label: "確定"
				},
				cancel: {
					label: "取消"
				}
			},
			callback: function(result) {
				if(result) {
					//呼叫Server端程式
					let json = {
							"myself":myself,
							"theOther":theOther
					}
					axios.post("http://localhost:8080/meeple-masters/friend/delete",json).then(function(response) {
						let theIsActive = false;
						let theOtherEmail = changeToIdType(theOther);
						let idTypeTag = theOtherEmail+"tag";
						$(".friendButtonDiv").empty();
						$(".friendButtonDiv").append(`
								<button onclick="sendFriendInvite('${member.memberEmail}','`+selectedFriendEmail+`')">加好友</button>
								`);
						//如果他剛好是active，就重新給active並更新右邊畫面
						if($(".active")[0].id == idTypeTag){
							theIsActive = true;
						}
						//刪除好友欄與對話框
						$("#"+theOtherEmail).closest(".container").remove();
						if($("#"+idTypeTag).length != 0){
							$("#"+idTypeTag).remove();
						}
						//刪除排序資料表我跟他有關的排序
						updateChatRoomOrder();
						//重給active並更新畫面
						if($(".chatListTag").length != 0){
							$(".chatListTag")[0].classList.add("active");
							showChatContent(changeToOriginEmail($(".chatListTag")[0].id.replace("tag","")));
						}else{
							$("#chatRoom").empty();							
						}
						//websocket呼叫後端告訴後端已經不是好友了
						let wsJson = {
								"action":"deleteFriend",
								"myself":myself,
								"theOther":theOther
						}
						ws.send(JSON.stringify(wsJson));
					}).catch(function(error) {
						console.log("error", error);
					}).finally(function() {
						
					});
					
				}
			}
		});
	}
	
	
	//按下取消邀請按鈕
	function deleteFriendInvite(myself,theOther){
		//呼叫Server端程式
		let json = {
				"myself":myself,
				"theOther":theOther
		}
		axios.post("http://localhost:8080/meeple-masters/friendInvite/delete",json).then(function(response) {
			$(".friendButtonDiv").empty();
			$(".friendButtonDiv").empty();
			$(".friendButtonDiv").append(`
					<button onclick="sendFriendInvite('${member.memberEmail}','`+selectedFriendEmail+`')">加好友</button>
					`);
			//websocket呼叫後端告訴後端將對方的接受按鈕變回不是好友
			let wsJson = {
					"action":"deleteFriendInvite",
					"myself":myself,
					"theOther":theOther
			}
			ws.send(JSON.stringify(wsJson));
		}).catch(function(error) {
			console.log("error", error);
		}).finally(function() {
			
		});
	}
	
	
	//按下接受邀請按鈕
	function acceptFriendInvite(myself,theOther){
		//呼叫Server端程式
		let json = {
				"myself":myself,
				"theOther":theOther
		}
		axios.post("http://localhost:8080/meeple-masters/friend/acceptInvite",json).then(function(response) {
			$(".friendButtonDiv").empty();
			$(".friendButtonDiv").append(`
					<button onclick="pressChatButton('`+buttonTypeName+`','`+idTypeEmail+`')">傳送訊息</button>
					<button onclick="deleteFriend('${member.memberEmail}','`+selectedFriendEmail+`')">刪除好友</button>
					`)
			//用websocket判斷對方是否在線，製作好友欄
			let wsJson = {
				"action":"askIsOnflineFriend",
				"myself":myself,
				"theOther":theOther
			}
			ws.send(JSON.stringify(wsJson));
		}).catch(function(error) {
			console.log("error", error);
		}).finally(function() {
			
		});
	}
	
	
	
	//按下加好友按鈕時
	function sendFriendInvite(myself,theOther){
		//呼叫Server端程式
		let json = {
				"myself":myself,
				"theOther":theOther
		}
		axios.post("http://localhost:8080/meeple-masters/friendInvite/insert",json).then(function(response) {
			$(".friendButtonDiv").empty();
			$(".friendButtonDiv").append(`
					<button onclick="deleteFriendInvite('${member.memberEmail}','`+selectedFriendEmail+`')">取消邀請</button>
					`);
			//websocket呼叫後端
			let wsJson = {
					"action":"addFriendInvite",
					"myself":myself,
					"theOther":theOther
			}
			ws.send(JSON.stringify(wsJson));
		}).catch(function(error) {
			console.log("error", error);
		}).finally(function() {
			
		});
	}

	
	
	
</script>
</body>
</html>