<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="../../include/common_link.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.box{
		position:relative;
	}
	.showGameProgress{
		background-color:lightBlue;
		height:5vh;
		width:100vw;
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
		bottom:20px;
		left:5%;
	}
	.cardInMyHand{
		width:85px
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
</style>
</head>
<body>
	<div class="box">
		<div class="showGameProgress">
			<c:choose>
				<c:when test="${bridge.phase == 1}">等待${bridge.player1.name}出牌</c:when>
				<c:when test="${bridge.phase == 2}">等待${bridge.player1.name}出牌</c:when>
				<c:when test="${bridge.phase == 3}">等待${bridge.player1.name}出牌</c:when>
				<c:when test="${bridge.phase == 4}">等待${bridge.player1.name}出牌</c:when>
				<c:when test="${bridge.phase == 5}">一輪結束，${bridge.perTurnWinner.team} (${bridge.perTurnWinner.name})贏得此墩</c:when>
				<c:when test="${bridge.phase == 11}">等待${bridge.player1.name}選擇</c:when>
				<c:when test="${bridge.phase == 21}">等待${bridge.player2.name}選擇</c:when>
				<c:when test="${bridge.phase == 31}">等待${bridge.player3.name}選擇</c:when>
				<c:when test="${bridge.phase == 41}">等待${bridge.player4.name}選擇</c:when>
			</c:choose>
			<c:if test="true">
				<button class="btn btn-primary">梅花1</button>
				<button class="btn btn-primary">方塊1</button>
				<button class="btn btn-primary">紅心1</button>
				<button class="btn btn-primary">黑桃1</button>
				<button class="btn btn-primary">無王1</button>
				<button class="btn btn-primary">跳過</button>
			</c:if>
		</div>
		<div class="container-fluid text-center">
		  <div class="row">
		    <div class="col-9 leftSide">
		    	<div class="tableArea">
		    		<div class="trump">
		    			<span>本局王牌:</span>
		    			<span>${trump}</span>
		    		</div>
					<div class="desk">
						<img class="cardInDesk" src="${root}/poker/0"><br/>
						<span>x32</span>
					</div>
					<div class="showCardForToPlayers">
						<img class="cardInDesk" src="${root}/poker/44"><br/>
					</div>
					<div class="myShowCardArea">
						<img class="cardInMyShowCardArea" src="${root}/poker/1">
						<span style="position:absolute;bottom:3px;left:50px">自己</span>
					</div>
					<div class="player2ShowCardArea">
						<span>Player2</span>
						<img class="cardInplayer2ShowCardArea" src="${root}/poker/2">
					</div>
					<div class="player3ShowCardArea">
						<span>Player3</span>
						<img class="cardInplayer3ShowCardArea" src="${root}/poker/3">
					</div>
					<div class="player4ShowCardArea">
						<span>Player4</span>
						<img class="cardInplayer4ShowCardArea" src="${root}/poker/4">
					</div>
					<div class="discardArea">
						<span>棄牌堆</span>
						<img class="cardInDiscardArea" src="${root}/poker/0">
					</div>
				</div>
				<div class="myHand">
				<h2>我的手牌</h2>
					<div class="cardInMyHandArea">
						<img class="cardInMyHand" src="${root}/poker/0">
						<img class="cardInMyHand" src="${root}/poker/0">
						<img class="cardInMyHand" src="${root}/poker/0">
						<img class="cardInMyHand" src="${root}/poker/0">
						<img class="cardInMyHand" src="${root}/poker/0">
						<img class="cardInMyHand" src="${root}/poker/0">
						<img class="cardInMyHand" src="${root}/poker/0">
						<img class="cardInMyHand" src="${root}/poker/0">
						<img class="cardInMyHand" src="${root}/poker/0">
						<img class="cardInMyHand" src="${root}/poker/0">
						<img class="cardInMyHand" src="${root}/poker/0">
						<img class="cardInMyHand" src="${root}/poker/0">
						<img class="cardInMyHand" src="${root}/poker/0">
					</div>
				</div>
		    </div>
		    <div class="col-3 rightSide">
		    	<div class="playerArea">
					<img class="playerImg" src="${root}/poker/back/0">
					<div class="playerInfo1">
						<span>Andy</span><br/>
						<span>熟練度:500</span><br/>
					</div>
					<div class="playerInfo2">
						<span>隊伍:紅隊</span><br/>
						<span>贏下墩數:1</span>
					</div>
				</div>
				
				<div class="playerArea">
					<img class="playerImg" src="${root}/poker/back/0">
					<div class="playerInfo1">
						<span>Betty</span><br/>
						<span>熟練度:50</span><br/>
					</div>
					<div class="playerInfo2">
						<span>隊伍:藍隊</span><br/>
						<span>贏下墩數:1</span>
					</div>
				</div>
				<div class="teamInfo">
					<table>
						<tr>
							<td></td>
							<td>勝利需要墩數</td>
							<td>已贏得墩數</td>
						</tr>
						<tr>
							<td>紅隊</td>
							<td>?</td>
							<td>?</td>
						</tr>
						<tr>
							<td>藍隊</td>
							<td>?</td>
							<td>?</td>
						</tr>
					</table>
				</div>
				<div class="chatRoomInTheGame">
					
				</div>
		    </div>
		  </div>
		</div>
	</div>
	
	
	
	<script>
		
	</script>
</body>
</html>