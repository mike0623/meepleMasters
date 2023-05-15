<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
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
						let theIsChatActive = false;
						let theOtherEmail = changeToIdType(theOther);
						let idTypeTag = theOtherEmail+"tag";
						$(".friendButtonDiv").empty();
						$(".friendButtonDiv").append(`
								<button class="btn btn-info" onclick="sendFriendInvite('${member.memberEmail}','`+selectedFriendEmail+`')">加好友</button>
								`);
						if($(".chatActive").length != 0){
							//如果他剛好是chatActive，就重新給chatActive並更新右邊畫面
							if($(".chatActive")[0].id == idTypeTag){
								theIsChatActive = true;
							}
						}
						//刪除好友欄與對話框
						$("#"+theOtherEmail).closest(".container").remove();
						if($("#"+idTypeTag).length != 0){
							$("#"+idTypeTag).remove();
						}
						//刪除排序資料表我跟他有關的排序
						updateChatRoomOrder();
						//重給chatActive並更新畫面
						if($(".chatListTag").length != 0){
							$(".chatListTag")[0].classList.add("chatActive");
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
	
	
	//邀請者自己按下取消邀請按鈕
	function deleteFriendInvite(myself,theOther){
		//呼叫Server端程式
		let json = {
				"myself":myself,
				"theOther":theOther,
				"accepter": "theOther"
		}
		axios.post("http://localhost:8080/meeple-masters/friendInvite/delete",json).then(function(response) {
			if(isSelectedPage == true){
				$(".friendButtonDiv").empty();
				$(".friendButtonDiv").empty();
				$(".friendButtonDiv").append(`
						<button class="btn btn-info" onclick="sendFriendInvite('${member.memberEmail}','`+selectedFriendEmail+`')">加好友</button>
						`);
			}
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
			//目前在查詢好友頁面時才發動
			if(isSelectedPage == true){
				$(".friendButtonDiv").empty();
				$(".friendButtonDiv").append(`
						<button class="btn btn-info" onclick="pressChatButton('`+buttonTypeName+`','`+idTypeEmail+`')">傳送訊息</button>
						<button class="btn btn-info" onclick="deleteFriend('${member.memberEmail}','`+selectedFriendEmail+`')">刪除好友</button>
						`)
			}
			//更新好友邀請欄
			findFriendInvite();
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
	
	//受邀者按下拒絕好友邀請
	function rejectFriendInvite(myself,theOther){
		//呼叫Server端程式
		let json = {
				"myself":myself,
				"theOther":theOther,
				"accepter": "myself"
		}
		axios.post("http://localhost:8080/meeple-masters/friendInvite/delete",json).then(function(response) {
			if(isSelectedPage == true){
				$(".friendButtonDiv").empty();
				$(".friendButtonDiv").empty();
				$(".friendButtonDiv").append(`
						<button class="btn btn-info" onclick="sendFriendInvite('${member.memberEmail}','`+selectedFriendEmail+`')">加好友</button>
						`);
			}
			//更新好友邀請欄
			findFriendInvite();
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
					<button class="btn btn-info" onclick="deleteFriendInvite('${member.memberEmail}','`+selectedFriendEmail+`')">取消邀請</button>
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