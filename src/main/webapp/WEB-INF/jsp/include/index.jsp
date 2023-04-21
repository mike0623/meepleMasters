<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ include file="./common_link.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${webName}</title>
<style>
            /* ad */

            .ad {
            position: relative;
            height: 800px;
            background-color: #CA7159;
        }

        .adDiv1 {
            width: 640px;
            height: 500px;
            margin: auto;
            position: relative;
        }

        .adsImg {
            position: absolute;
            top: 160px;
        }

        .adBlock1 {
            color: #FFF;
            position: absolute;
            width: 300px;
            height: 300px;
            background-color: #613E3B;
            top: 300px;
            right: -200px;
            padding: 50px;
        }

        .adsContent {
            height: 150px;
        }

        .adDiv2 {
            height: 300px;
            background-color: #df927d;
        }

        /* adsButton */
        .button-19 {
            appearance: button;
            background-color: #cdae71;
            border: solid transparent;
            border-radius: 16px;
            border-width: 0 0 4px;
            box-sizing: border-box;
            color: #613E3B;
            cursor: pointer;
            display: inline-block;
            font-family: din-round, sans-serif;
            font-size: 15px;
            font-weight: 700;
            letter-spacing: .8px;
            line-height: 20px;
            margin: 0;
            outline: none;
            overflow: visible;
            padding: 13px 16px;
            text-align: center;
            text-transform: uppercase;
            touch-action: manipulation;
            transform: translateZ(0);
            transition: filter .2s;
            user-select: none;
            -webkit-user-select: none;
            vertical-align: middle;
            white-space: nowrap;
            width: 100%;
        }

        .button-19:after {
            background-clip: padding-box;
            background-color: #F3D18E;
            border: solid transparent;
            border-radius: 16px;
            border-width: 0 0 4px;
            bottom: -4px;
            content: "";
            left: 0;
            position: absolute;
            right: 0;
            top: 0;
            z-index: -1;
        }

        .button-19:main,
        .button-19:focus {
            user-select: auto;
        }

        .button-19:hover:not(:disabled) {
            filter: brightness(1.1);
            -webkit-filter: brightness(1.1);
        }

        .button-19:disabled {
            cursor: auto;
        }

        /* card */

        .gameCardDiv {
            top: 100px;
            position: relative;
        }

        .gameListTitle {
            width: 1320px;
            height: 60px;
            text-align: center;
            font-size: 30px;
            /* border-bottom: #000 solid 2px; */
        }

        .link-top {
            width: 200px;
            height: 2px;
            background-color: #000;
            margin: auto;
            margin-top: 10px;
        }


        .card {
            width: 1000px;
            background-color: #ffffff;
            border-radius: 3px;
            border: 1px solid #a1a1a1;
            padding: 20px;
        }

        .card .pic img {
            width: 100%;
        }

        .card .card-header {
            border-bottom: none;
            background-color: transparent;
        }

        .card .title {
            color: #444444;
        }

        .card .text {
            color: #444444;
        }

        .card-footer {
            border-top: none !important;
            background-color: transparent !important;
        }

        /* store */

        .storeBlock {
            position: relative;
            top: 200px;
            height: 800px;
            background-color: #F3D18E;
        }

        .map {
            width: 600px;
            ;
            position: relative;
            top: 200px;
            margin: auto;
        }
</style>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<div class="bodyContainer">
    <div class="ad">
        <div class="adDiv1">
            <img src="${root}/img/example/poker1.jpg" class="adsImg" />
            <div class="adBlock1">
                <div class="adsContent">挑戰德州撲克！<br />世界上最流行的公牌撲克，目標贏取彩池中的所有彩金！</div>
                <button class="button-19" role="button">開始遊玩</button>
            </div>
        </div>

        <div class="adDiv2"></div>
    </div>

    <div class="container gameCardDiv">
        <div class="gameListTitle">遊戲列表
            <div class="link-top"></div>
        </div>
        <div class="row px-4 pt-4 justify-content-center">
            <div class="col-3 d-flex align-items-stretch">
                <div class="card">
                    <div class="pic">
                        <img src="https://picsum.photos/300/?random=10">
                    </div>
                    <div class="card-header">
                        number 1
                    </div>
                    <div class="card-body">
                        <h3 class="title">
                            Special title treatment
                        </h3>
                    </div>
                    <div class="card-footer">
                        <p class="text">
                            This is a wider card with supporting text below as a natural lead-in to additional
                            content.
                            This
                            content is a little bit longer.
                        </p>
                    </div>
                </div>
            </div>
            <div class="col-3 d-flex align-items-stretch">
                <div class="card">
                    <div class="pic">
                        <img src="https://picsum.photos/300/?random=11">
                    </div>
                    <div class="card-header">
                        number 2
                    </div>
                    <div class="card-body">
                        <h3 class="title">
                            Lorem ipsum dolor sit amet, consectetur adipisicing elit.
                        </h3>
                    </div>
                    <div class="card-footer">
                        <p class="text">
                            With supporting text below as a natural lead-in to additional content.
                        </p>
                    </div>
                </div>
            </div>

            <div class="col-3 d-flex align-items-stretch">
                <div class="card">
                    <div class="pic">
                        <img src="https://picsum.photos/300/?random=12">
                    </div>
                    <div class="card-header">
                        number 3
                    </div>
                    <div class="card-body">
                        <h3 class="title">
                            Lorem ipsum dolor sit amet.
                        </h3>
                    </div>
                    <div class="card-footer">
                        <p class="text">
                            With supporting text below as a natural lead-in to additional content.
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="storeBlock">
        <div class="">

        </div>
        <div class="map">
            <iframe
                src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3615.0109929955192!2d121.54330379999999!3d25.033700999999997!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3442abd37971c7cb%3A0x40ba641f27b6d4e3!2zMTA25Y-w5YyX5biC5aSn5a6J5Y2A5b6p6IiI5Y2X6Lev5LiA5q61Mzkw6Jmf!5e0!3m2!1szh-TW!2stw!4v1681057217647!5m2!1szh-TW!2stw"
                width="600" height="450" style="border:0;" allowfullscreen="" loading="lazy"
                referrerpolicy="no-referrer-when-downgrade"></iframe>
        </div>

    </div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>