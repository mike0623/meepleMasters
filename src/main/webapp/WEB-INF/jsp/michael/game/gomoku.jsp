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
<title>Insert title here</title>
<style>
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
		background-color:#F0EBE2;
	}
	.rightSide{
		position:relative;
		height:95vh;
		background-color:lightGreen;
	}
	*{
        margin: 0;
        padding: 0;
     }
     .chessBoardDiv{
     	position:relative;
     }
     .chessBoardTable{
        border-spacing: 0px;
        line-height: 29px;
        position:absolute;
        left:406px;
     	top:100px;
     }
     .chessBoardTd{
        margin-inline: 0px;
        padding-right: 9px;
        padding-left: 8px;
        font-size:16px;
        position:relative;
     }
     .chessBoardImg{
     	position:absolute;
     	left:400px;
     	top:100px;
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
	.here{
		display:none;
	}
	.redPiece{
		position:absolute;
		top:9px; 
		right:6px;
		width:16px; 
		height:16px; 
		border:1px solid black; 
		border-radius:50%; 
		background-color:red;
		display:block;
	}
	.blackPiece{
		position:absolute;
		top:9px; 
		right:6px;
		width:16px; 
		height:16px; 
		border:1px solid black; 
		border-radius:50%; 
		background-color:black;
		display:block;
	}
	.whitePiece{
		position:absolute;
		top:9px; 
		right:6px;
		width:16px; 
		height:16px; 
		border:1px solid black; 
		border-radius:50%; 
		background-color:white;
		display:block;
	}
	.endGameStyle{
		text-align:center;
		
	}
	.endGameStyle td{
		width: 200px;
	    border: 1px solid black;
	    background-color: azure;
	    text-align: center;
	}
</style>
</head>
<body>
<div style="width:100%; position:relative; top:75px;">
	<div class="box">
		<div class="showGameProgress">
			<c:if test="${gomoku.endOfTheGame == true}">遊戲結束，${gomoku.winner}贏了</c:if>
			<c:if test="${member.memberEmail eq gomoku.playerNTurn && gomoku.endOfTheGame == false}">輪到你的回合</c:if>
			<c:if test="${member.memberEmail ne gomoku.playerNTurn && gomoku.endOfTheGame == false}">等待<c:if test="${'player1' == myself}">${gomoku.player2Name}</c:if><c:if test="${'player1' != myself}">${gomoku.player1Name}</c:if>行動</c:if>
		</div>
		<div class="container-fluid text-center">
		    <div class="row">
		    	<div class="col-9 leftSide">
		    		<div class="chessBoardDiv">
		    			<img class="chessBoardImg" src="${root}/img/michael/game/chessBoard.jpg">
				        <table class="chessBoardTable">
				            <tbody>
								<c:forEach begin="1" end="19" step="1" var="i">
									<tr>
									<c:forEach begin="1" end="19" step="1" var="j">
										<c:set value="x${j}y${20-i}" var="key"/>
										<td id="x${j}y${20-i}" class="chessBoardTd <c:if test="${member.memberEmail == gomoku.playerNTurn && gomoku.chessBoard[key] == null}">canDo</c:if>" style="">
											<span style="visibility: hidden;">+</span>
											<div class="here <c:if test="${gomoku.chessBoard[key] == 'black'}">blackPiece</c:if><c:if test="${gomoku.chessBoard[key] == 'white'}">whitePiece</c:if>"></div>
										</td>
									</c:forEach>
									</tr>
								</c:forEach>
				            </tbody>
				        </table>

		    		</div>
		    	</div>
		    	<div class="col-3 rightSide">
		    		<div class="giveUpButtonDiv">
			    		<button class="giveUpButton btn btn-secondary" onclick="giveUpTheGame()">投降</button>
			    	</div>
			    	<c:choose>
			    	<c:when test="${'player1' == myself}">
			    		<div class="playerArea ${gomoku.player1Name}">
							<img class="playerImg" src="${root}/member/emailFindMemberImg/${gomoku.player1Email}">
							<div class="playerInfo1">
								<span>${gomoku.player1Name}</span><br/>
								<span>熟練度:${gomoku.player1Degree}</span><br/>
							</div>
							<div class="playerInfo2">
								<c:if test="${gomoku.player1Color == 'black'}"><span>黑子</span></c:if>
								<c:if test="${gomoku.player1Color == 'white'}"><span>白子</span></c:if>
							</div>
						</div>
						
						<div class="playerArea ${gomoku.player2Name}">
							<img class="playerImg" src="${root}/member/emailFindMemberImg/${gomoku.player2Email}">
							<div class="playerInfo1">
								<span>${gomoku.player2Name}</span><br/>
								<span>熟練度:${gomoku.player2Degree}</span><br/>
							</div>
							<div class="playerInfo2">
								<c:if test="${gomoku.player2Color == 'black'}"><span>黑子</span></c:if>
								<c:if test="${gomoku.player2Color == 'white'}"><span>白子</span></c:if>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="playerArea ${gomoku.player2Name}">
							<img class="playerImg" src="${root}/member/emailFindMemberImg/${gomoku.player2Email}">
							<div class="playerInfo1">
								<span>${gomoku.player2Name}</span><br/>
								<span>熟練度:${gomoku.player2Degree}</span><br/>
							</div>
							<div class="playerInfo2">
								<c:if test="${gomoku.player2Color == 'black'}"><span>黑子</span></c:if>
								<c:if test="${gomoku.player2Color == 'white'}"><span>白子</span></c:if>
							</div>
						</div>
						
						<div class="playerArea ${gomoku.player1Name}">
							<img class="playerImg" src="${root}/member/emailFindMemberImg/${gomoku.player1Email}">
							<div class="playerInfo1">
								<span>${gomoku.player1Name}</span><br/>
								<span>熟練度:${gomoku.player1Degree}</span><br/>
							</div>
							<div class="playerInfo2">
								<c:if test="${gomoku.player1Color == 'black'}"><span>黑子</span></c:if>
								<c:if test="${gomoku.player1Color == 'white'}"><span>白子</span></c:if>
							</div>
						</div>
					</c:otherwise>
					</c:choose>
		    	</div>
		    </div>
		</div>
			
			
	</div>
</div>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp"></jsp:include>
<script type="text/javascript">
	isGomokuGamePage = true;
	$(".chessBoardDiv").on("click","td.canDo",function(){
		let position = this.id
		let yIndex = this.id.indexOf("y");
		let x = position.substring(1,yIndex);
		let y = position.substring(yIndex+1);
		axios.get("${root}/gomoku/action/"+x+"/"+y).then(function(response){
			console.log(response);
			console.log(response.data.color);
			let color = response.data.color;
			let playerNTurn = response.data.playerNTurn;
			let json = response.data;
			if(!response.data.endOfTheGame){
				$(".showGameProgress").text("等待"+playerNTurn+"行動");
				$(".canDo").removeClass("canDo");
				$(".here").removeClass("redPiece");
				if(color == "black"){
					$("#x"+x+"y"+y).children(".here").addClass("blackPiece");
				}
				if(color == "white"){
					$("#x"+x+"y"+y).children(".here").addClass("whitePiece");
				}
			}else{
				//贏了要做的事情
				endingView(json);
			}
		}).catch(function(error){
			console.log("下棋出錯啦",error);
		}).finally(function(){
			
		});
	});
	
	$(".chessBoardDiv").on("mouseenter","td.canDo",function(){
		$(this).children("div").addClass("redPiece");
	});
	
	$(".chessBoardDiv").on("mouseleave","td.canDo",function(){
		$(this).children("div").removeClass("redPiece");
	});
	
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
					axios.get("${root}/gomoku/giveUp").then(function(response){
						let json = response.data;
						endingView(json);
					}).catch(function(error){
						console.log("下棋出錯啦",error);
					}).finally(function(){
						
					});
				}
			}
		});
	}
	
	function endingView(json){
		$(".showGameProgress").text("遊戲結束，"+json.winner+"贏了");
		$(".canDo").removeClass("canDo");
		Swal.fire({
			backdrop: false,
			title: '遊戲結束',
			html: `
				<h2>遊戲結束，`+json.winner+`勝利</h2>
				<table><tbody>
					<tr>
						<td></td>
						<td>原本分數</td>
						<td>增減分數</td>
						<td>增加米寶幣</td>
					</tr>
					<tr>
						<td>`+json.winner+`</td>
						<td>`+json.winnerDegree+`</td>
						<td>`+json.winnerChange+`</td>
						<td>200</td>
					</tr>
					<tr>
						<td>`+json.loser+`</td>
						<td>`+json.loserDegree+`</td>
						<td>`+json.loserChange+`</td>
						<td>100</td>
					</tr>
				</tbody></table>
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