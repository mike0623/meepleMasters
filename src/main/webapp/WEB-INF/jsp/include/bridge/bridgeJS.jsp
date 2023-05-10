<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script>
	    var isBridgeGamePage = true;
		const tableCode = $("#tableCode").text();
		//按下喊王按鈕後
		$(".showGameProgress").on("click","button",function(){
			let choose = $(this).text();
			let suit = "";
			let number = "";
			if("跳過" != choose){
				suit = choose.substring(0,2);
				number = choose.substring(2);
			}else{
				suit = choose.substring(0,2);
				number = 0;
			}
			axios.get("${root}/bridge/bidding/"+tableCode+"/${member.memberEmail}/"+suit+"/"+number).then(function(response){
				let json = response.data;
				if(json.isBiddingPhase){
					$(".showGameProgress").text("目前王牌: "+json.trumpAndLevel+" 目前得標者:"+json.playerNBid+" 等待"+json.playerNTurn+"選擇");
				}
				if(json.isBiddingPhase == false){
					if(json.playerNTurnEmail == "${member.memberEmail}"){
						$(".showGameProgress").text("輪到你出牌了");
					}else{
						$(".showGameProgress").text("等待"+json.playerNTurn+"出牌");
					}
					$(".team1WinRequirement").text(json.team1WinRequirement);
					$(".team2WinRequirement").text(json.team2WinRequirement);
					$(".gameTrump").text(json.trumpAndLevel.substring(0,2));
				}
			}).catch(function(error){
				console.log("按下喊王的按鈕的error",error);
			}).finally(function(){
				
			});
			
		});
	</script>
</body>
</html>