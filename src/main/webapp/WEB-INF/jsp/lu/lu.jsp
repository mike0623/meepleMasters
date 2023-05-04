<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>訂位清單</title>
</head>
<body>
    <h1>訂位清單</h1>
    <form action="bookTable" method="POST">
        <p>姓名：<input type="text" name="name"></p>
        <p>日期：<input type="date" name="date"></p>
        <p>時段：
            <select name="period">
                <option value="morning">早上</option>
                <option value="afternoon">中午</option>
                <option value="evening">晚上</option>
            </select>
        </p>
        <p>桌號：
            <select name="deskId">
                <option value="1">桌1</option>
                <option value="2">桌2</option>
                <option value="3">桌3</option>
                <option value="4">桌4</option>
                <option value="5">桌5</option>
            </select>
        </p>
        <p>人數：<input type="number" name="numPeople"></p>
        <p><input type="submit" value="訂位"></p>
    </form>
</body>
</html>