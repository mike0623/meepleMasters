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
	.openLike{
		background-color: yellow;
	}
	.openDislike{
		background-color: lightBlue;
	}
</style>
<script src="https://cdn.ckeditor.com/ckeditor5/37.1.0/classic/ckeditor.js"></script>
</head>
<!-- 最上面那排bar -->
<jsp:include page="../include/header.jsp" />
<body>
	<div class="bodyContainer">

		<!-- 顯示文章 -->
		<div>文章</div>
		<div>
		<div>articleId:${article.articleId}</div>
		<div>poster:${article.fkMemberId}</div>
		<div>product:${article.fkProductId}</div>
		<div>createDate:${article.articleCreatedDate}</div>
		<div>updateDate:${article.articleUpdatedDate}</div>
		<div>title:${article.articleTitle}</div>
		<div>content:${article.articleContent}</div>


		<!-- 本人才能修改、刪除 -->
		<!-- 非本人才能讚、噓 -->
		<!-- 沒登入只能看 -->
		<c:choose>
			<c:when test="${author}">
			
			<form action="${root}/editArticle/${article.articleId}" method="get">
				<button>修改</button>
			</form>
			
			<form action="${root}/deleteArticle/${article.articleId}" method="get">
				<button>刪除</button>
			</form>		
			
			</c:when>
			<c:otherwise>
				<c:if test="${member != null}">
					<button id="like" name="like" 
					class="<c:if test='${review==true}'>openLike</c:if>">讚</button>
					<button id="dislike" name="dislike" 
					class="<c:if test='${review==false&&review!=null}'>openDislike</c:if>">噓</button>
				</c:if>
			</c:otherwise>
		</c:choose>
				
		<div>文章留言</div>

		<!-- 顯示文章留言 -->
		<c:if test="${member != null}">
				<form action="${root}/newArticleComment" method="post">

				<label for="poster">Poster:</label> 
				<input type="text" id="poster" name="poster"> <br>
				
				<label for="product">Product:</label>
				<input type="text" id="product" name="product"> <br>
				
				<label for="content">content:</label>
				<textarea  id="editor" name="content"></textarea><br>

				<button type="submit">新增留言</button>
				
			</form>
		</c:if>



		</div>
		
	</div>
</body>
<!-- 最下面標題 -->
<jsp:include page="../include/footer.jsp" />

<!-- 讚、噓按鈕實作 -->
<script type="text/javascript">

	var review = ${review==null}?null:${review};


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



</html>