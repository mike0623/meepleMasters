<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!-- 以上自行取用 -->
<%@ include file="../include/common_link.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="../include/header.jsp"></jsp:include>
<h1>Example</h1>

<div class="card" style="width: 18rem;">
  <img src="..." class="card-img-top" alt="...">
  <div class="card-body">
    <h5 class="card-title">Card title</h5>
    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
    <a href="#" class="btn btn-primary">Go somewhere</a>
  </div>
</div>
<input type="button" value="測試ajax回到此頁" onclick="testAjax()">


<jsp:include page="../include/footer.jsp"></jsp:include>
<script type="text/javascript">
	function testAjax(){
		axios.get("${root}/michael/index").then(function(response){
			console.log("有成功");
			bootbox.alert({
				title:"<h1 class='text-success'>新增成功</h1>",
				message:"新增成功",
				buttons:{
					ok:{
						label:"關閉",
						className:"btn-success"
					}
				}
			})
		}).catch(function(error){
			
		}).finally(function(){
			
		});
	}
</script>
</body>
</html>