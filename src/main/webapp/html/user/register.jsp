<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
    <head>
        <title>注册</title>
        <script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js" type="text/javascript"></script>
        <%--<link rel="stylesheet" href="../../css/AuthConfig.css" type="text/css">--%>
        <script src="../../js/user/register.js" type="text/javascript"></script>
    </head>
    <body>
    <form id="form-register" action="/user/reg" method="post">
        <table>
            <tr>
                <td><spring:message code="username"/></td>
                <td><input type="text" name="UserName"></td>
            </tr>
            <tr>
                <td><spring:message code="password"/></td>
                <td><input type="password" name="PassWord"></td>
            </tr>
            <tr>
                <td>手机号</td>
                <td><input id="input-phone" type="text" name="Phone"></td>
                <td><button type="button" onclick="OnVcodeButtonClick();">获取验证码</button></td>
            </tr>
            <tr>
                <td>验证码</td>
                <td><input type="text" name="Vcode"></td>
            </tr>
            <tr>
                <td><h5 id="text-message"></h5></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value=<spring:message code="submit"/>></td>
            </tr>
        </table>
    </form>
    </body>
</html>