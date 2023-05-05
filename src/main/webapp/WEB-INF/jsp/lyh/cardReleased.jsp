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
<link rel="stylesheet" type="text/css" href="${root}/css/card/cardReleased.css">
</head>
<body>

    <jsp:include page="../include/header.jsp"></jsp:include>
    <div class="bodyContainer">
        <div class="releasedContainer">
            <div class="releasedTitle">交易市集</div>
            <div class="releasedSelect">
                <button></button>

            </div>




        </div>
        <jsp:include page="../include/footer.jsp"></jsp:include>
    </div>

</body>
</html>