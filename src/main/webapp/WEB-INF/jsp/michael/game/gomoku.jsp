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
     .chessBoardTable{
        border-spacing: 0px;
        line-height: 30px;
     }
     .chessBoardTd{
        margin-inline: 0px;
     }
</style>
</head>
<body>
<div style="width:100%; position:relative; top:75px;">
	<div class="box">
		<div class="showGameProgress">
			
		</div>
		<div class="container-fluid text-center">
		    <div class="row">
		    	<div class="col-9 leftSide">
		    		<div class="chessBoard">
				        <table class="chessBoardTable">
				            <tbody>
								<c:forEach begin="1" end="19" step="1" var="i">
									<tr>
									<c:forEach begin="1" end="19" step="1" var="j">
										<td class="x${j} y${20-i} chessBoardTd" style="font-size:60px;"></td>
									</c:forEach>
									</tr>
								</c:forEach>
				            </tbody>
				        </table>

		    		</div>
		    	</div>
		    	<div class="col-3 rightSide">
		    
		    	</div>
		    </div>
		</div>
			
			
	</div>
</div>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp"></jsp:include>
</body>
</html>