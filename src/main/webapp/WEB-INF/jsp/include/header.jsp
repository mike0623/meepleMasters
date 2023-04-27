<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${webName}</title>
<link rel="stylesheet" type="text/css" href="${root}/css/header.css">
<style>
    
</style>
</head>
<body>
    <div class="nav fixed-top">
        <header>
            <div class="logo"><a href="${root}"><img src="${root}/img/logo.png" alt="" class="logoImg" /></a></div>
            <nav role="navigation">
                <ul>
                    <li>
                        <a href="/">遊戲列表</a>
                    </li>
                    <li>
                        <a href="${root}" aria-disabled="true"> 卡片收藏</a>
                        <div>
                            <ul>
                                <li><a href="${root}/card/mycard/1">我的卡片</a></li>
                                <li><a href="#">卡片市集</a></li>
                                <li><a href="#">收藏記錄</a></li>
                            </ul>
                        </div>
                    </li>
                    <li>
                        <a href="#">遊戲討論</a>
                    </li>
                    <li>
                        <a href="#">場地預約</a>
                    </li>
                    <li><a href="#">會員中心</a></li>
                    <li style="background: none;"><a href="${root}/member/login" style="font-size: 16px;">註冊 / 登入</a></li>
                </ul>
            </nav>
        </header>
    </div>
</body>
</html>