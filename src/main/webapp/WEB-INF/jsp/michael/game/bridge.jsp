<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="../../include/common_link.jsp" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"></jsp:include>
<link href="${root}/css/bootstrap.min.css" rel="stylesheet">
<meta charset="UTF-8">
<title>Bridge</title>
<style>
	ol, ul {
     padding-left: 0rem;
	}
	body{ 
 		color: #858796; 
 	} 
	.box{
		position:relative;
	}
	.showGameProgress{
		background-color:lightBlue;
		height:5vh;
		width:100%;
		text-align:center;
		font-size:28px;
	}
	.leftSide{
		position:relative;
		height:95vh;
	}
	.rightSide{
		position:relative;
		height:95vh;
		background-color:lightGreen;
	}
	.myHand{
		height:35.5vh;
		width:100%;
		background-color:lightBlue;
		position:absolute;
		bottom:0px;
	}
	.cardInMyHandArea{
		width:90%;
/* 		background-color:black; */
		height:200px;
		position:absolute;
		bottom:40px;
		left:5%;
	}
	.cardInMyHand{
		width:85px;
		opacity: 0.5;
	}
	.playerImg{
		position:absolute;
		height:70px;
		width:70px;
		border-radius:50%;
		top:15px;
		left:5px;
	}
	.playerArea{
		position:relative;
		height:100px;
		margin-bottom:5px;
		background-color:#CBC0AA;
		border-radius:5px;
	}
	.playerInfo1{
		position:absolute;
		left:100px;
		top:15px;
		font-size:30px;
	}
	.playerInfo2{
		position:absolute;
		left:250px;
		top:15px;
		font-size:30px;
	}
	
	table{
		text-align:center;
		font-size:30px;
	}
	.desk{
		position:absolute;
		top:150px;
		left:50px;
		font-size: 30px;
	}
	.cardInDesk{
		height:200px;
	}
	.tableArea{
		position:absolute;
		background-color:#CBC0AA;
		height:60.5vh;
		width:100%;
		top:0;
	}
	.showCardForToPlayers{
		position:absolute;
		top:150px;
		left:250px;
		font-size: 30px;
		
	}
	.player2ShowCardArea{
		position:absolute;
		width:160px;
		height:250;
		top:120px;
		right:300px;
		font-size: 30px;
		text-align:center;
		background-color:white;
		border:1px dashed black;
	}
	.player3ShowCardArea{
		position:absolute;
		width:160px;
		height:250;
		top:10px;
		right:500px;
		font-size: 30px;
		text-align:center;
		background-color:white;
		border:1px dashed black;
	}
	.player4ShowCardArea{
		position:absolute;
		width:160px;
		height:250;
		top:120px;
		right:700px;
		font-size: 30px;
		text-align:center;
		background-color:white;
		border:1px dashed black;
	}
	.cardInplayer2ShowCardArea, .cardInplayer3ShowCardArea, .cardInplayer4ShowCardArea{
		height:200px;
		position:absolute;
		left:9px;
		bottom:3px;
	}
	.myShowCardArea{
		position:absolute;
		width:160px;
		height:250;
		bottom:10px;
		right:500px;
		font-size: 30px;
		text-align:center;
		background-color:white;
		border:1px dashed black;
	}
	.cardInMyShowCardArea{
		height:200px;
		position:absolute;
		left:9px;
		top:3px;
	}
	.discardArea{
		position:absolute;
		bottom:0px;
		right:0px;
		text-align:center;
		font-size:20px;
		height:162px;
		width:120px;
	}
	.cardInDiscardArea{
		position:absolute;
		bottom:0px;
		right:10px;
		height:133px;
	}
	.trump{
		position:absolute;
		top:50px;
		left:50px;
		font-size:30px;
	}
	.canDo{
		cursor: pointer;
		opacity: 1;
	}
	.canDo:hover{
		position:relative;
		bottom:20px;
	}
	.giveUpButtonDiv{
		margin-top:3px;
		margin-bottom:3px;
		position:relative;
		height:40px;
	}
	.giveUpButton{
		position:absolute;
		right:0px;
		top:0px;
	}
	.endGameStyle{
		width:1200px;
	}
	.endPersonalInfo{
            border-collapse:collapse;
            text-align: center;
            border:1px solid black;
            margin-bottom: 10px;
    }
    .endPersonalInfo tr td{
        width:200px;
        border: 1px solid black;
        background-color: azure;
        
    }
    .endTeamInfo{
        border-collapse:collapse;
        text-align: center;
        border:1px solid black;
        margin-bottom: 10px;
    }
    .endTeamInfo tr td{
        width:200px;
        border: 1px solid black;
        background-color: azure; 
    }
</style>
</head>
<body>
	<span id="tableCode" style="display:none;">${tableCode}</span>
	<div style="width:100%; position:relative; top:75px;">
	<div class="box">
		<div class="showGameProgress">
		<c:if test="${bridge.isEndOfTheGame == true}">遊戲結束，${bridge.winTeam}勝利</c:if>
		<c:if test="${bridge.isEndOfTheGame == false}">
			<c:if test="${myArray.get(0).playerNumber != bridge.playerNTurn.playerNumber}">
				<c:choose>
					<c:when test="${bridge.phase == 1}">等待${bridge.player1.name}出牌</c:when>
					<c:when test="${bridge.phase == 2}">等待${bridge.player2.name}出牌</c:when>
					<c:when test="${bridge.phase == 3}">等待${bridge.player3.name}出牌</c:when>
					<c:when test="${bridge.phase == 4}">等待${bridge.player4.name}出牌</c:when>
					<c:when test="${bridge.phase == 5}">一輪結束，${bridge.perTurnWinner.team} (${bridge.perTurnWinner.name})贏得此墩</c:when>
					<c:when test="${bridge.phase == 11}">目前王牌:
						<c:choose>
							<c:when test="${bridge.trump == 0}">黑桃${bridge.trumpLevel}</c:when>
							<c:when test="${bridge.trump == 1}">紅心${bridge.trumpLevel}</c:when>
							<c:when test="${bridge.trump == 2}">方塊${bridge.trumpLevel}</c:when>
							<c:when test="${bridge.trump == 3}">梅花${bridge.trumpLevel}</c:when>
							<c:when test="${bridge.trump == 4}">無王${bridge.trumpLevel}</c:when>
						</c:choose>
						<c:if test="${bridge.playerNBid != null}">
							目前得標者:${bridge.playerNBid.name}
						</c:if>
						等待${bridge.player1.name}選擇
					</c:when>
					<c:when test="${bridge.phase == 21}">目前王牌:
						<c:choose>
							<c:when test="${bridge.trump == 0}">黑桃${bridge.trumpLevel}</c:when>
							<c:when test="${bridge.trump == 1}">紅心${bridge.trumpLevel}</c:when>
							<c:when test="${bridge.trump == 2}">方塊${bridge.trumpLevel}</c:when>
							<c:when test="${bridge.trump == 3}">梅花${bridge.trumpLevel}</c:when>
							<c:when test="${bridge.trump == 4}">無王${bridge.trumpLevel}</c:when>
						</c:choose>
						<c:if test="${bridge.playerNBid != null}">
							目前得標者:${bridge.playerNBid.name}
						</c:if>
						等待${bridge.player2.name}選擇
					</c:when>
					<c:when test="${bridge.phase == 31}">目前王牌:
						<c:choose>
							<c:when test="${bridge.trump == 0}">黑桃${bridge.trumpLevel}</c:when>
							<c:when test="${bridge.trump == 1}">紅心${bridge.trumpLevel}</c:when>
							<c:when test="${bridge.trump == 2}">方塊${bridge.trumpLevel}</c:when>
							<c:when test="${bridge.trump == 3}">梅花${bridge.trumpLevel}</c:when>
							<c:when test="${bridge.trump == 4}">無王${bridge.trumpLevel}</c:when>
						</c:choose>
						<c:if test="${bridge.playerNBid != null}">
							目前得標者:${bridge.playerNBid.name}
						</c:if>
						等待${bridge.player3.name}選擇
					</c:when>
					<c:when test="${bridge.phase == 41}">目前王牌:
						<c:choose>
							<c:when test="${bridge.trump == 0}">黑桃${bridge.trumpLevel}</c:when>
							<c:when test="${bridge.trump == 1}">紅心${bridge.trumpLevel}</c:when>
							<c:when test="${bridge.trump == 2}">方塊${bridge.trumpLevel}</c:when>
							<c:when test="${bridge.trump == 3}">梅花${bridge.trumpLevel}</c:when>
							<c:when test="${bridge.trump == 4}">無王${bridge.trumpLevel}</c:when>
						</c:choose>
						<c:if test="${bridge.playerNBid != null}">
							目前得標者:${bridge.playerNBid.name}
						</c:if>
						等待${bridge.player4.name}選擇
					</c:when>
				</c:choose>
	
			</c:if>
			<c:if test="${myArray.get(0).playerNumber == bridge.playerNTurn.playerNumber}">
				<c:choose>
					<c:when test="${bridge.phase < 10}">輪到你出牌了</c:when>
					<c:when test="${bridge.phase > 10}">
						目前王牌:
						<c:choose>
							<c:when test="${bridge.trump == 0}">黑桃${bridge.trumpLevel}</c:when>
							<c:when test="${bridge.trump == 1}">紅心${bridge.trumpLevel}</c:when>
							<c:when test="${bridge.trump == 2}">方塊${bridge.trumpLevel}</c:when>
							<c:when test="${bridge.trump == 3}">梅花${bridge.trumpLevel}</c:when>
							<c:when test="${bridge.trump == 4}">無王${bridge.trumpLevel}</c:when>
						</c:choose>
						<c:if test="${bridge.playerNBid != null}">
							目前得標者:${bridge.playerNBid.name}
						</c:if>
						請選擇是否要喊王:
						<button class="btn btn-primary">梅花${bridge.trumpLevel+1}</button>
						<c:choose>
							<c:when test="${bridge.trump == 0}"><button class="btn btn-primary">方塊${bridge.trumpLevel+1}</button></c:when>
							<c:when test="${bridge.trump == 1}"><button class="btn btn-primary">方塊${bridge.trumpLevel+1}</button></c:when>
							<c:when test="${bridge.trump == 2}"><button class="btn btn-primary">方塊${bridge.trumpLevel+1}</button></c:when>
							<c:when test="${bridge.trump == 3}"><button class="btn btn-primary">方塊${bridge.trumpLevel}</button></c:when>
							<c:when test="${bridge.trump == 4}"><button class="btn btn-primary">方塊${bridge.trumpLevel+1}</button></c:when>
						</c:choose>
						<c:choose>
							<c:when test="${bridge.trump == 0}"><button class="btn btn-primary">紅心${bridge.trumpLevel+1}</button></c:when>
							<c:when test="${bridge.trump == 1}"><button class="btn btn-primary">紅心${bridge.trumpLevel+1}</button></c:when>
							<c:when test="${bridge.trump == 2}"><button class="btn btn-primary">紅心${bridge.trumpLevel}</button></c:when>
							<c:when test="${bridge.trump == 3}"><button class="btn btn-primary">紅心${bridge.trumpLevel}</button></c:when>
							<c:when test="${bridge.trump == 4}"><button class="btn btn-primary">紅心${bridge.trumpLevel+1}</button></c:when>
						</c:choose>
						<c:choose>
							<c:when test="${bridge.trump == 0}"><button class="btn btn-primary">黑桃${bridge.trumpLevel+1}</button></c:when>
							<c:when test="${bridge.trump == 1}"><button class="btn btn-primary">黑桃${bridge.trumpLevel}</button></c:when>
							<c:when test="${bridge.trump == 2}"><button class="btn btn-primary">黑桃${bridge.trumpLevel}</button></c:when>
							<c:when test="${bridge.trump == 3}"><button class="btn btn-primary">黑桃${bridge.trumpLevel}</button></c:when>
							<c:when test="${bridge.trump == 4}"><button class="btn btn-primary">黑桃${bridge.trumpLevel+1}</button></c:when>
						</c:choose>
						<c:if test="${bridge.trump == 4}">
							<button class="btn btn-primary">無王${bridge.trumpLevel+1}</button>
						</c:if>
						<c:if test="${bridge.trump != 4}">
							<button class="btn btn-primary">無王${bridge.trumpLevel}</button>
						</c:if>
						<button class="btn btn-primary">跳過</button>
					</c:when>
				</c:choose>
			</c:if>
			</c:if>
		</div>
		<div class="container-fluid text-center">
		  <div class="row">
		    <div class="col-9 leftSide">
		    	<div class="tableArea">
		    		<div class="trump">
		    			<span>本局王牌:</span>
		    			<c:if test="${bridge.phase > 10}">
							<span class="gameTrump">尚未決定</span>
						</c:if>
						<c:if test="${bridge.phase < 10}">
			    			<c:choose>
								<c:when test="${bridge.trump == 0}"><span class="gameTrump">黑桃</span></c:when>
								<c:when test="${bridge.trump == 1}"><span class="gameTrump">紅心</span></c:when>
								<c:when test="${bridge.trump == 2}"><span class="gameTrump">方塊</span></c:when>
								<c:when test="${bridge.trump == 3}"><span class="gameTrump">梅花</span></c:when>
								<c:when test="${bridge.trump == 4}"><span class="gameTrump">無王</span></c:when>
							</c:choose>
						</c:if>
		    		</div>
		    		<c:if test="${bridge.finalNumOfPlayer == 2 && bridge.deskList.size() > 0}">
						<div class="desk">
							<img class="cardInDesk" src="${root}/poker/0"><br/>
							<span>x${bridge.deskList.size()}</span>
						</div>
						<div class="showCardForToPlayers">
							<img class="cardInDesk" src="${root}/poker/${bridge.forTwoPlayersCard}"><br/>
						</div>
					</c:if>
					<c:if test="${bridge.finalNumOfPlayer == 3}">
						<div class="forThreePlayerArea"></div>
					</c:if>
					<div class="myShowCardArea">
						<c:if test="${myArray.get(0).playedCard != null}">
							<img class="cardInMyShowCardArea" src="${root}/poker/${myArray.get(0).playedCard}">
						</c:if>
						<span style="position:absolute;bottom:3px;left:50px">自己</span>
					</div>
					<c:if test="${myArray.size() >= 4}">
						<div class="player2ShowCardArea">
							<span>${myArray.get(1).name}</span>
							<c:if test="${myArray.get(1).playedCard != null}">
								<img class="cardInplayer2ShowCardArea" src="${root}/poker/${myArray.get(1).playedCard}">
							</c:if>
						</div>
					</c:if>
					<c:if test="${myArray.size() == 2}">
						<div class="player3ShowCardArea">
							<span>${myArray.get(1).name}</span>
							<c:if test="${myArray.get(1).playedCard != null}">
								<img class="cardInplayer3ShowCardArea" src="${root}/poker/${myArray.get(1).playedCard}">
							</c:if>
						</div>
					</c:if>
					<c:if test="${myArray.size() >= 4}">
						<div class="player3ShowCardArea">
							<span>${myArray.get(2).name}</span>
							<c:if test="${myArray.get(2).playedCard != null}">
								<img class="cardInplayer3ShowCardArea" src="${root}/poker/${myArray.get(2).playedCard}">
							</c:if>
						</div>
					</c:if>
					<c:if test="${myArray.size() >= 4}">
						<div class="player4ShowCardArea">
							<span>${myArray.get(3).name}</span>
							<c:if test="${myArray.get(3).playedCard != null}">
								<img class="cardInplayer4ShowCardArea" src="${root}/poker/${myArray.get(3).playedCard}">
							</c:if>
						</div>
					</c:if>
				</div>
				<div class="myHand">
				<h2>我的手牌</h2>
					<div class="cardInMyHandArea">
						<c:forEach items="${myArray.get(0).handCardList}" var="i">
							<img name="${i}" class="cardInMyHand <c:if test="${myArray.get(0).playerNumber == bridge.playerNTurn.playerNumber && bridge.phase <10 && (bridge.perTurnSuit == null || bridge.perTurnSuit == Integer((i-1) / 13))}">canDo</c:if> <c:if test="${myArray.get(0).playerNumber == bridge.playerNTurn.playerNumber && bridge.perTurnSuit != null && bridge.perTurnSuit != Integer((i-1) / 13) && bridge.getNumOfSpecific(myArray.get(0), bridge.perTurnSuit == null?4:bridge.perTurnSuit) == 0}">canDo</c:if>" src="${root}/poker/${i}">
						</c:forEach>
					</div>
				</div>
		    </div>
		    <div class="col-3 rightSide">
		    	<div class="giveUpButtonDiv">
		    		<c:if test="${bridge.finalNumOfPlayer == 2 && bridge.deskList.size() > 0 && bridge.isEndOfTheGame == false}">
		    			<form action="${root}/bridge/forTwoPlayersfastForward"><button class="fastForwardBtn btn btn-primary">快轉到牌庫剩一張</button></form>
		    		</c:if>
		    		<button class="giveUpButton btn btn-secondary" onclick="giveUpTheGame()">投降</button>
		    	</div>
		    	<c:forEach items="${myArray}" var="playerN">
		    		<div class="playerArea ${playerN.name}">
					<img class="playerImg" src="${root}/member/emailFindMemberImg/${playerN.email}">
					<div class="playerInfo1">
						<span>${playerN.name}</span><br/>
						<span>熟練度:${playerN.bridgeDegree}</span><br/>
					</div>
					<div class="playerInfo2">
						<c:choose>
							<c:when test="${playerN.team == 1}"><span>隊伍:紅隊</span><br/></c:when>
							<c:when test="${playerN.team == 2}"><span>隊伍:藍隊</span><br/></c:when>
						</c:choose>
						<span>贏下墩數:${playerN.wonTricks}</span>
					</div>
					</div>
		    	</c:forEach>
	
				<div class="teamInfo">
					<table>
						<tr>
							<td></td>
							<td>勝利需要墩數</td>
							<td>已贏得墩數</td>
						</tr>
						<tr>
							<td>紅隊</td>
							<c:choose>
								<c:when test="${bridge.team1WinRequirement != null}"><td class="team1WinRequirement">${bridge.team1WinRequirement}</td></c:when>
								<c:when test="${bridge.team1WinRequirement == null}"><td class="team1WinRequirement">?</td></c:when>
							</c:choose>
							<td class="team1WonTricks">${bridge.team1WonTricks}</td>
						</tr>
						<tr>
							<td>藍隊</td>
							<c:choose>
								<c:when test="${bridge.team2WinRequirement != null}"><td class="team2WinRequirement">${bridge.team2WinRequirement}</td></c:when>
								<c:when test="${bridge.team2WinRequirement == null}"><td class="team2WinRequirement">?</td></c:when>
							</c:choose>
							<td class="team2WonTricks">${bridge.team2WonTricks}</td>
						</tr>
					</table>
				</div>
				<div class="chatRoomInTheGame">
					
				</div>
		    </div>
		  </div>
		</div>
	</div>
	</div>
	
	
	<jsp:include page="../../include/bridge/bridgeJS.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/include/footer.jsp"></jsp:include>
</body>
</html>