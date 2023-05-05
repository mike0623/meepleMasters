<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${webName}</title>
<style>

    .footer {
        position: relative;
        bottom: 0;
    }
</style>
</head>
<body>
    <div class="footerContainer">
        <section class="navbar-inverse navbar-fixed-bottom footer">
            <!-- Footer -->
            <footer class="text-center text-white" style="background-color: #CA7159;">
                <!-- Grid container -->
                
                <div class="container p-4 pb-0" style="height: 80px;">
                    <section class="">
                        <p class="d-flex justify-content-center align-items-center">
                    <c:if test="${member == null}">
                            <span class="me-3">免費註冊</span>
                            <button type="button" class="btn btn-outline-light btn-rounded">
                                登入！
                            </button>
                    </c:if>
                        </p>
                    </section>
                </div>
                <!-- Grid container -->
    
                <!-- Copyright -->
                <div class="text-center p-3" style="background-color: #613E3B;">
                    © 2023 Copyright:
                    <a class="text-white" href="/">MeepleMasters</a>
                </div>
                <!-- Copyright -->
            </footer>
            <!-- Footer -->
        </section>
    </div>
    
</body>
</html>