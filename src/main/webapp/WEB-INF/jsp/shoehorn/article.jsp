<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="../include/common_link.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${webName}</title>
<link rel="stylesheet" type="text/css" href="${root}/css/index.css" />
<style>
.openLike {
	background-color: yellow;
}

.openDislike {
	background-color: lightBlue;
}

.articleBody {
	width: 60%;
	margin: auto;
	text-align: center;
}

table {
	border-collapse: collapse;
	margin: auto;
}

table td, table th{
	font-size: 30px;
	text-align: center;
	border: 1px solid black;
}
.firstTable{
	margin-bottom:5px;
}
.secondH2,thirdH2{
	margin-top:20px;
}
.newArticleComment{
	font-size: 30px;
	text-align: center;
}
</style>
<script
	src="https://cdn.ckeditor.com/ckeditor5/37.1.0/classic/ckeditor.js"></script>
</head>
<!-- 最上面那排bar -->
<jsp:include page="../include/header.jsp" />
<body>
	<div class="bodyContainer">
		<div class="articleBody">
			<!-- 顯示文章 -->
			<h2>文章</h2>
			<div>
				<table class="firstTable">
					<tr>
						<th>發文者</th>
						<th>遊戲</th>
						<th>創文時間</th>
						<th>標題</th>
						<th></th>
						<th></th>
					</tr>
					<tr>
						<td>${memberName}</td>
						<td>${productName}</td>
						<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short"  
value="${article.articleCreatedDate}" /></td>
						<td>${article.articleTitle}</td>

						
						<c:choose>
							<c:when test="${author}">
								<td>
								<form action="${root}/editArticle/${article.articleId}"
									method="get">
									<button>修改</button>
								</form>
								</td>
								<td>
								<form action="${root}/deleteArticle/${article.articleId}"
									method="get">
									<button>刪除</button>
								</form>
								</td>
							</c:when>
							<c:otherwise>
								<c:if test="${member != null}">
									<td>
									<button id="like" name="like"
										class="<c:if test='${review==true}'>openLike</c:if>">讚</button>
									</td>
									<td>
									<button id="dislike" name="dislike"
										class="<c:if test='${review==false&&review!=null}'>openDislike</c:if>">噓</button>
									</td>
								</c:if>
							</c:otherwise>
						</c:choose>



					</tr>
				</table>

				<table>
					<tr>
						<th>內文</th>
					</tr>
					<tr>
						<td>${article.articleContent}</td>
					</tr>
				</table>


				<!-- 本人才能修改、刪除 -->
				<!-- 非本人才能讚、噓 -->
				<!-- 沒登入只能看 -->
				




				<h2 class="secondH2">文章留言</h2>
				<table class="commentTable">
					<tr>
						<th>留言者</th>
						<th>留言內容</th>
						<th>好評</th>
						<th>差評</th>
						<c:if test="${member != null}">
							<th></th>
							<th></th>
						</c:if>
					</tr>
					<c:if test="${articleCommentList.size()>= 1}">
						<c:forEach begin="0" end="${articleCommentList.size()-1}" step="1"
							var="i">
							<tr id="status${creview[i]}">
								<td onclick="<c:if test='${member.memberId == articleCommentList[i].fkMemberId}'>location.href='${root}/articleComment?articleCommentId=${articleCommentList[i].articleCommentId}'</c:if>">${memberNameList[i]}</td>
								<td onclick="<c:if test='${member.memberId == articleCommentList[i].fkMemberId}'>location.href='${root}/articleComment?articleCommentId=${articleCommentList[i].articleCommentId}'</c:if>">${articleCommentList[i].articleCommentContent}</td>
								<td onclick="<c:if test='${member.memberId == articleCommentList[i].fkMemberId}'>location.href='${root}/articleComment?articleCommentId=${articleCommentList[i].articleCommentId}'</c:if>">${goodReviewCount[i]}</td>
								<td onclick="<c:if test='${member.memberId == articleCommentList[i].fkMemberId}'>location.href='${root}/articleComment?articleCommentId=${articleCommentList[i].articleCommentId}'</c:if>">${badReviewCount[i]}</td>
								<c:if test="${member != null}">
									<td>
										<button id="clike${articleCommentList[i].articleCommentId}"
											name="clike"
											class="commentButton ${creview[i] == true ? 'openLike' : ''}">讚</button>
									</td>
									<td>
										<button id="cdislike${articleCommentList[i].articleCommentId}"
											name="cdislike"
											class="commentButton ${creview[i]==false && creview[i]!=null? 'openDislike' : ''}">噓</button>
									</td>
								</c:if>
							</tr>
						</c:forEach>
					</c:if>
				</table>



				<!-- 顯示文章留言 -->
				<div class="newArticleComment">
				<c:if test="${member != null}">
				<h2 class="thirdH2">新增文章留言</h2>
					<form action="${root}/newArticleComment" method="post">
						<input type="text" id="articleId" name="articleId"
							value="${article.articleId}" style="display: none;"> <input
							type="text" id="poster" name="poster" value="${member.memberId}"
							style="display: none"> <input type="text" id="product"
							name="product" value="${article.fkProductId}"
							style="display: none"> <label for="content">content:</label>
						<textarea id="editor" name="content"></textarea>
						<br>

						<button type="submit">新增留言</button>

					</form>
				</c:if>
				</div>



			</div>
		</div>
	</div>
</body>
<!-- 最下面標題 -->
<jsp:include page="../include/footer.jsp" />

<!-- 文章 讚、噓按鈕實作 -->
<script type="text/javascript">
	let firstTableWidth = $(".firstTable").width();
	console.log("第一個table長度",firstTableWidth);
	$("table")[1].width = firstTableWidth;

	if("${review}" == ""){
		var review = null;
	}
	if("${review}" == "true"){
		var review = true;
	}
	if("${review}" == "false"){
		var review = false;
	}
	
	console.log(review);



	$("#like").on("click",function(){
		
		console.log("old review: "+ (review==null?"":review));
		
		review==true?review=null:review=true;
				
		console.log("new review: "+ (review==null?"":review));
		
		let json = {
				"memberId":${member.memberId},
				"articleId":${article.articleId},
				"articleReview":review
		}
		
		axios.post("${root}/article/like",json).then(function(response){
			console.log(response);
			console.log(response.data.xxx);
			if(review == true){
				$("#like").addClass("openLike");
				$("#dislike").removeClass("openDislike");
			}else{
				$("#like").removeClass("openLike");
			}
		}).catch(function(error){
			console.log("按下讚後的錯誤",error);
		}).finally(function(){
			
		});
	});
	
$("#dislike").on("click",function(){
		
		console.log("old review: "+ (review==null?"":review));		
		
		review==false?review=null:review=false;
		
		console.log("new review: "+(review==null?"":review));
		
		let json = {
				"memberId":${member.memberId},
				"articleId":${article.articleId},
				"articleReview":review
		}
		
		axios.post("${root}/article/like",json).then(function(response){
			console.log(response);
			console.log(response.data.xxx);
			if(review==false && review!=null){
				$("#like").removeClass("openLike");
				$("#dislike").addClass("openDislike");
			}else{
				$("#dislike").removeClass("openDislike");
			}
		}).catch(function(error){
			console.log("按下噓後的錯誤",error);
		}).finally(function(){
			
		});
	});
</script>

<!-- 留言 讚、噓按鈕實作 -->
<script type="text/javascript">
if(${creview} == ""){
	var creview = null;
}
if(${creview} == "true"){
	var creview = true;
}
if(${creview} == "false"){
	var creview = false;
}
$(".commentTable").on("click",".commentButton",function(){
	let tr = $(this).closest("tr")[0];
	var creview = true;
	let word = $(this).closest("tr")[0].id.substring(6);
	if(word == "true"){
		creview = true;
	}
	if(word == "false"){
		creview = false;
	}
	if(word == ""){
		creview = null;
	}
	var articleCommentId = "";
	if($(this)[0].name == "clike"){
		articleCommentId = $(this)[0].id.substring(5);
	}
	if($(this)[0].name == "cdislike"){
		articleCommentId = $(this)[0].id.substring(8);
	}
	console.log("原本的",creview);
	//接到原本的值後，改變成新的
	if($(this)[0].name == "clike" && creview == true){
		creview = null;
	}else
	if($(this)[0].name == "clike" && creview == false && creview != null){
		creview = true;
	}else
	if($(this)[0].name == "clike" && creview == null){
		creview = true;
	}else
	if($(this)[0].name == "cdislike" && creview == true){
		creview = false;
	}else
	if($(this)[0].name == "cdislike" && creview == false && creview != null){
		creview = null;
	}else
	if($(this)[0].name == "cdislike" && creview == null){
		creview = false;
	}
	//製作json
	let json = {
			"memberId":${member.memberId},
			"articleCommentId":articleCommentId,
			"commentReview":creview
	}
	axios.post("${root}/articleComment/like",json).then(function(response){
		console.log(response);
		console.log("回傳的值",creview);
		//改變tr狀態表
		if(creview == null){
			tr.id = "status";
		}else{
			tr.id = "status"+creview;
		}
		//改驗顏色
		if(creview == true){
			console.log("進入true方法");
			$("#clike"+articleCommentId).addClass("openLike");
			$("#cdislike"+articleCommentId).removeClass("openDislike");
		}
		if(creview == false && creview != null){
			console.log("進入false方法");
			$("#clike"+articleCommentId).removeClass("openLike");
			$("#cdislike"+articleCommentId).addClass("openDislike");
		}
		if(creview == null){
			console.log("進入null方法");
			$("#clike"+articleCommentId).removeClass("openLike");
			$("#cdislike"+articleCommentId).removeClass("openDislike");
		}
	}).catch(function(error){
		console.log("按下留言按鈕的錯誤",error);
	}).finally(function(){
		
	});
});


function clike(articleCommentId,creview){

console.log('articleCommentId: '+articleCommentId);
console.log('creview: '+creview);	
console.log('creview: '+${creview});	

	if(creview == ""){
		var creview = null;
	}
	if(creview == "true"){
		var creview = true;
	}
	if(creview == "false"){
		var creview = false;
	}
	
	console.log('creview: '+creview);
		
		console.log("old review: "+ (creview==null?"":creview));
		
		creview==true?creview=null:creview=true;
				
		console.log("new review: "+ (creview==null?"":creview));
		
		let json = {
				"memberId":${member.memberId},
				"articleCommentId":articleCommentId,
				"commentReview":creview
		}
		
		axios.post("${root}/articleComment/like",json).then(function(response){
			console.log(response);
			console.log(response.data.xxx);
			if(creview == true){
				$("#clike").addClass("openLike");
				$("#cdislike").removeClass("openDislike");
			}else{
				$("#clike").removeClass("openLike");
			}
		}).catch(function(error){
			console.log("按下讚後的錯誤",error);
		}).finally(function(){
			
		});
	};
	
function cdislike(articleCommentId,creview){
	
	if(creview == ""){
		var creview = null;
	}
	if(creview == "true"){
		var creview = true;
	}
	if(creview == "false"){
		var creview = false;
	}
		
		console.log("old review: "+ (creview==null?"":creview));		
		
		creview==false?creview=null:creview=false;
		
		console.log("new review: "+(creview==null?"":creview));
		
		let json = {
				"memberId":${member.memberId},
				"articleCommentId":articleCommentId,
				"commentReview":creview
		}
		
		axios.post("${root}/articleComment/like",json).then(function(response){
			console.log(response);
			console.log(response.data.xxx);
			if(creview==false && creview!=null){
				$("#clike").removeClass("openLike");
				$("#cdislike").addClass("openDislike");
			}else{
				$("#cdislike").removeClass("openDislike");
			}
		}).catch(function(error){
			console.log("按下噓後的錯誤",error);
		}).finally(function(){
			
		});
	};
</script>


</html>