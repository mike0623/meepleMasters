<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script>
	    isBridgeGamePage = true;
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
						$(".cardInMyHand").addClass("canDo");
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
		
		
		//輪到我的回合，點選卡牌時
		$(".cardInMyHandArea").on("click",".canDo",function(){
			let card = this.name;
			let dom = $(this)
			axios.get("${root}/bridge/useCard/${tableCode}/${member.memberEmail}/"+card).then(function(response){
				console.log("點選我的卡牌後，回傳的資料",response);
				//刪除點選卡片，並在出牌區增加該卡片
				dom.remove();
				$(".myShowCardArea").append(`
					<img class="cardInMyShowCardArea" src="${root}/poker/`+card+`" />	
						`);
				//改變手牌的canDo
				$(".cardInMyHand").removeClass("canDo");
				let json = response.data;
				//回合還沒結束
				if(json.isEndOfTheTurn == false){
					$(".showGameProgress").text("等待"+json.playerNTurn+"出牌");
				}
				//回合結束
				if(json.isEndOfTheTurn){
					let perTurnWinTeam ="";
					if(json.perTurnWinnerTeam == 1){
						perTurnWinTeam = "紅隊";
					}
					if(json.perTurnWinnerTeam == 2){
						perTurnWinTeam = "藍隊";
					}
					//如果是兩人階段一，就寫不同的字
					if(json.isThisTurnTwoPlayerPhaseOne){
						$(".showGameProgress").text(`
								一輪結束，`+json.perTurnWinnerName+` 獲得`+json.winnerGetCardInTwoPlayerPhaseOne+`
									`);
						//判斷下回合還是不是phase1，如果不是就移除牌庫跟展示的牌
						if(!json.isTwoPlayerPhaseOne){
							$(".showCardForToPlayers").remove();
							$(".desk").remove();
							$(".fastForwardBtn").remove();
						}
					}else{
						$(".showGameProgress").text(`
							一輪結束，`+perTurnWinTeam+` (`+json.perTurnWinnerName+`)贏得此墩
								`);
						//改變已贏得的敦數
						$(".team1WonTricks").text(json.team1WonTricks);
						$(".team2WonTricks").text(json.team2WonTricks);
						$(".playerArea."+json.perTurnWinnerName).children(".playerInfo2").children("span:eq(1)").text("贏下墩數:"+json.perTurnWinnerWonTricks);
						//如果遊戲結束，就跳出視窗
						if(json.isEndOfTheGame){
							//秀出結束畫面
							makeEndingView(json);
							
							//後面就不做了
							return;
						}
					}
					setTimeout(function(){
						$(".myShowCardArea img").remove();
						$(".player2ShowCardArea img").remove();
						$(".player3ShowCardArea img").remove();
						$(".player4ShowCardArea img").remove();
						//如果下回合兩人階段變更牌庫數量及顯示的卡片
						if(json.isTwoPlayerPhaseOne){
							$(".desk").children("span").text("x"+json.restSizeOfDesk);
							$(".showCardForToPlayers").children("img").attr("src","/meeple-masters/poker/"+json.newForTwoPlayerCard);
						}
						if(json.playerNTurnEmail == "${member.memberEmail}"){
							//輪到我
							//上面的進度說明
							$(".showGameProgress").text("輪到你出牌了");
							//手牌管理
							$(".cardInMyHand").addClass("canDo");
						}else{
							//不是輪到我
							//上面的進度說明
							$(".showGameProgress").text("等待"+json.playerNTurn+"出牌");
							//手牌管理，上面已經全部移除canDo
						}
					}, 2000);
				}
			}).catch(function(error){
				console.log("出牌時出錯啦",error);
			}).finally(function(){
				
			});
		});
		
		//放棄遊戲時
		function giveUpTheGame(){
			bootbox.confirm({
				title: "放棄遊戲",
				message: '確定要放棄遊戲嗎?這樣會導致您和您的隊友輸掉遊戲',
				buttons: {
					confirm: {
						label: "確定"
					},
					cancel: {
						label: "取消"
					}
				},
				callback: function(result) {
					if(result){
						axios.get("${root}/bridge/giveUpGame").then(function(response){
							console.log("放棄遊戲的回應",response);
							let json = response.data;
							//秀出結束畫面
							makeEndingView(json);
						}).catch(function(error){
							console.log("放棄遊戲時的錯誤",error);
						}).finally(function(){
							
						});
					}
				}
			});
		}
		
		//遊戲結束時，製作結束畫面
		function makeEndingView(json){
			console.log("有進入製作結束畫面方法");
			$(".giveUpButtonDiv").remove();
			$(".showGameProgress").text("遊戲結束，"+json.winTeam+"勝利");
			$(".team1WonTricks").text(json.team1WonTricks);
			$(".team2WonTricks").text(json.team2WonTricks);
			//改變右邊結果
			for(let i = 0;i<json.playerSeat.length;i++){
				$(".playerArea."+json.playerSeat[i].name).children(".playerInfo2").children("span:eq(1)").text("贏下墩數:"+json.playerSeat[i].wonTricks);
			}
			//改變手牌的canDo
			$(".cardInMyHand").removeClass("canDo");
			let loseTeam = "";
			if(json.winTeam == "紅隊"){
				loseTeam = "藍隊";
			}
			if(json.winTeam == "藍隊"){
				loseTeam = "紅隊";
			}
			let winTeanPlayerTr = "";
			for(let i = 0;i<json.winTeamPlayers.length;i++){
				winTeanPlayerTr += `
					<tr>
	                    <td>`+json.winTeamPlayers[i].name+`</td>
	                    <td>`+json.winTeamPlayers[i].bridgeDegree+`</td>
	                    <td>`+json.winTeam+`</td>
	                    <td>`+json.winTeamPlayers[i].wonTricks+`</td>
	                    <td>`+json.winTeamPlayers[i].changeScore+`</td>
	                    <td>+200</td>
	                </tr>
				`;
			}
			let loseTeanPlayerTr = "";
			for(let i = 0;i<json.loseTeamPlayers.length;i++){
				loseTeanPlayerTr += `
					<tr>
	                    <td>`+json.loseTeamPlayers[i].name+`</td>
	                    <td>`+json.loseTeamPlayers[i].bridgeDegree+`</td>
	                    <td>`+loseTeam+`</td>
	                    <td>`+json.loseTeamPlayers[i].wonTricks+`</td>
	                    <td>`+json.loseTeamPlayers[i].changeScore+`</td>
	                    <td>+100</td>
	                </tr>
				`;
			}
			
			Swal.fire({
				backdrop: false,
				title: '遊戲結束',
				html: `
					<h2>獲勝隊伍:`+json.winTeam+` 本局王牌:`+json.trump+` 王牌得標者:`+json.playerNBid+` 競標成本:`+json.trumpLevel+`</h2>
				    <div class="alertDiv">
				        <table class="endPersonalInfo">
				            <tbody>
				                <tr>
				                    <td></td>
				                    <td>原本熟練度</td>
				                    <td>隊伍</td>
				                    <td>個人贏得墩數</td>
				                    <td>熟練度增減</td>
				                    <td>米寶幣增加</td>
				                </tr>`+winTeanPlayerTr+` `+loseTeanPlayerTr+`
				            </tbody>
				        </table>
				        
				        <table class="endTeamInfo">
				            <tbody>
				                <tr>
				                    <td></td>
				                    <td>勝利所需墩數</td>
				                    <td>贏得墩數</td>
				                </tr>
				                <tr>
				                    <td>`+json.winTeam+`</td>
				                    <td>`+json.winTeamRequirement+`</td>
				                    <td>`+json.winTeamWonTricks+`</td>
				                </tr>
				                <tr>
				                <td>`+loseTeam+`</td>
			                    <td>`+json.loseTeamRequirement+`</td>
			                    <td>`+json.loseTeamWonTricks+`</td>
				                </tr>
				            </tbody>
				        </table>
				    </div>
				`,
				customClass: 'endGameStyle',
				//confirmButtonColor: '#CA7159',
				confirmButtonText: '回到遊戲大廳'
			}).then((value) => {
				location.href = "${root}/game/playGameLobby";
			});
		}
	</script>
</body>
</html>