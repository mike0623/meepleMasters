<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ include file="../include/common_link.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>${webName}</title>
    <link rel="stylesheet" type="text/css" href="${root}/css/card/newCardRelease.css">
</head>

<body>
    <jsp:include page="../include/header.jsp"></jsp:include>
    <div class="login-root">
        <div class="box-root flex-flex flex-direction--column" style="flex-grow: 1;">
            <div class="box-root padding-top--48 padding-bottom--24 flex-flex flex-justifyContent--center">
                <h1>卡片上架</h1>
            </div>
            <div class="formbg-outer">
                <div class="formbg">
                    <div class="formbg-inner padding-horizontal--48">
                        <span class="padding-bottom--15">卡片上架</span>
                        <form id="stripe-login">
                            <div class="field padding-bottom--24">
                                <label for="email">Email</label>
                                <input type="email" name="email">
                            </div>
                            <div class="field field-checkbox padding-bottom--24 flex-flex align-center">
                                <label for="type">
                                    <input type="radio" name="type"> 直購
                                </label>
                            </div>
                            <div class="field padding-bottom--24">
                                <div class="grid--50-50">
                                    <label for="password">Password</label>
                                </div>
                                <input type="password" name="password">
                            </div>
                            
                            <div class="field padding-bottom--24">
                                <input type="submit" name="submit" value="Continue">
                            </div>

                        </form>
                    </div>
                </div>

            </div>
        </div>


    </div>
    <jsp:include page="../include/footer.jsp"></jsp:include>
</body>

</html>