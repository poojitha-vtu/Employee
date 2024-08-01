<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    response.setHeader("Cache-Control","no-store");
    response.setHeader("Pragma","no-cache"); 
    response.setHeader ("Expires", "0"); //prevents caching at the proxy server
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Employer Tracking</title>
<link rel="stylesheet" href="./css/index.css">
</head>
<body>
<div class="container">
    <div class="right-side">
        <h1>Employee Time Tracker</h1>
        <div class="btn-container">
            <a href="admin_login.jsp" class="btn">Admin Login</a>
            <a href="EmployeeLogin.jsp" class="btn">User Login</a>
        </div>
    </div>
</div>
</body>
</html>
