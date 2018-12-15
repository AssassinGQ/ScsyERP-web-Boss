<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<form action="/user/dologin" method="post">
    <spring:message code="username"/> <input type="text" name="UserName"><br>
    <spring:message code="password"/> <input type="password" name="PassWord"><br>
    <h5>${login_info}</h5>
    <input type="submit" value=<spring:message code="submit"/>>
</form>
<a href="/html/user/register.jsp">修改密码</a>