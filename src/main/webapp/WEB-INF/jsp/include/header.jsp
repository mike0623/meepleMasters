<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${webName}</title>
<style>
    * {
            margin: 0;
            padding: 0;
            font-family: 微軟正黑體;
            outline: none;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
        }

        *:after,
        *:before {
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
        }

        article,
        aside,
        details,
        figcaption,
        figure,
        footer,
        header,
        hgroup,
        nav,
        section {
            display: block;
        }

        html {
            font-size: 100%;
            height: auto !important;
            height: 100%;
            -webkit-text-size-adjust: 100%;
            -ms-text-size-adjust: 100%;
        }

        /*GENERIC STYLES*/
        body {
            max-width: 100%;
            min-width: 100%;
            background: #f2f2f2;
            color: #222;
            -webkit-font-smoothing: antialiased;
            font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
            font-size: 1.05em;
            font-weight: 400;
            height: auto !important;
            height: 100%;
            line-height: 1.6rem;
            min-height: 100%;
        }

        .bodyContainer {
            padding-top: 49px;
            padding-bottom: 180px;
        }

        /*NAV*/

        .logo {
            align-self: center;
        }

        .logoImg {
            width: 170px;
        }

        .nav {
            display: fixed;
            width: 100%;
            background: linear-gradient(to left, #CBC0AA 0, #F0EBE2 100%);
            height: 75px;
        }

        header {
            width: 1200px;
            z-index: 1000;
            justify-content: space-between;
            display: flex;
            margin: auto;
        }

        ol,
        ul {
            padding: 0;
        }

        a:hover {
            color: #fff;
        }

        header>nav>ul {
            display: flex;
            flex-wrap: wrap;
            justify-content: flex-start;
            list-style: none;
            margin: 0;
            padding: 0;
        }

        header>nav>ul>li {
            flex: 0 1 auto;
            margin: 0;
            padding: 0;
            position: relative;
            transition: all linear 0.1s;
            width: 150px;
            text-align: center;
        }


        header>nav>ul>li:hover {
            background: #F3D18E;
            height: 75px;
            color: #fff;
        }

        header>nav>ul>li a+div {
            background: #F3D18E;
            border-radius: 0 0 2px 2px;
            display: none;
            font-size: 14px;
            position: absolute;
            width: 150px;
        }

        header>nav>ul>li:hover a+div {
            display: block;
        }

        header>nav>ul>li a+div>ul {
            list-style-type: none;
        }

        header>nav>ul>li a+div>ul>li {
            margin: 0;
            padding: 0;
        }

        header>nav>ul>li a+div>ul>li>a {
            color: rgba(255, 255, 255, .9);
            display: block;
            font-size: 18px;
            text-decoration: none;
            text-transform: uppercase;
            height: 60px;
            text-align: center;
            line-height: 60px;
        }

        header>nav>ul>li a+div>ul>li:hover>a {
            background-color: #CBC0AA;
            width: 100%;
            color: #fff;
        }

        header>nav>ul>li>a {
            align-items: flex-start;
            color: #fff;
            font-size: 18px;
            font-weight: 600;
            letter-spacing: 1px;
            text-decoration: none;
            text-shadow: 0 1px 1px rgba(0, 0, 0, .1);
            transition: all linear 0.1s;
            height: 75px;
            line-height: 75px;
        }
</style>
</head>
<body>
    <div class="nav fixed-top">
        <header>
            <div class="logo"><img src="${root}/img/logo.png" alt="" class="logoImg" /></div>
            <nav role="navigation">
                <ul>
                    <li>
                        <a href="/">遊戲列表</a>
                    </li>
                    <li>
                        <a href="/"> 卡片收藏</a>
                        <div>
                            <ul>
                                <li><a href="#">我的卡片</a></li>
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
                    <li style="background: none;"><a href="#" style="font-size: 16px;">註冊 / 登入</a></li>
                </ul>
            </nav>
        </header>
    </div>
</body>
</html>