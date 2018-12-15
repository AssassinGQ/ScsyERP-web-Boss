<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
    <title>测试</title>
    <script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js" type="text/javascript"></script>
    <!--<link rel="stylesheet" href="../../css/AuthConfig.css" type="text/css">-->
    <script src="../../js/test/test.js" type="text/javascript"></script>
</head>
<body>
<h1>new</h1>
<ul>
    <li><input id="checkbox1" type="checkbox" disabled="true"><label for="checkbox1">123</label></li>
    <li><input id="checkbox2" type="checkbox" readonly="readonly" onclick="checkboxclick(this);"><label for="checkbox2">123</label></li>
    <li><input id="checkbox3" type="checkbox" disabled><label for="checkbox3">123</label></li>
    <li><input id="checkbox4" type="checkbox" onclick="checkboxclick(this);"><label for="checkbox4">123</label></li>
</ul>
<button type="button" id="button-save">保存</button>
<ul>
    <li><input name="a" id="radio1" type="radio"><label for="checkbox1">123</label></li>
    <li><input name="a" id="radio2" type="radio"><label for="checkbox2">123</label></li>
    <li><input name="a" id="radio3" type="radio"><label for="checkbox3">123</label></li>
    <li><input name="a" id="radio4" type="radio"><label for="checkbox4">123</label></li>
</ul>
</body>
</html>