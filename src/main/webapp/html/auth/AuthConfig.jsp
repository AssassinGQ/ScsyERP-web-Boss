<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
    <head>
        <%--<meta http-equiv="Content-Type" content="text/html; charset=utf-8">--%>
        <title>权限管理</title>
        <script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js" type="text/javascript"></script>
        <link rel="stylesheet" href="../../css/AuthConfig.css" type="text/css">
        <script src="../../js/auth/AuthConfig.js" type="text/javascript"></script>
        <script src="../../js/auth/TreeBuilder.js" type="text/javascript"></script>
    </head>
    <body>
        <div id="full-height-container" class="project-container color-theme-white">
            <div id="header">
                <div id="header_item">
                    <div id="header_index">
                        <div id="header_l">
                        </div>
                        <div id="header_r">
                        </div>
                    </div>
                </div>
            </div>
            <div id="m-splitter">
                <div class="m-splitter-l left-container" style="width: 270px; height: 794px;">
                </div>
                <div class="splitter-handle left-drager">
                </div>
                <div id="pro-main" class="main-container font0">
                    <div id="roles-container">
                        <%--<ul Id="ul-roles-1">--%>
                            <%--<li>--%>
                                <%--<h1 Id="text-roleid">+ 超级管理员</h1>--%>
                                <%--<span Id="span-roleid">--%>
                                    <%--<ul class="menu-2 nodisplay">--%>

                                    <%--</ul>--%>
                                <%--</span>--%>
                            <%--</li>--%>
                        <%--</ul>--%>
                    </div>
                    <div id="roles-permissions-drager"></div>
                    <div id="permissions-container">
                        <ul id="ul-permissions">
                        </ul>
                        <button type="button" id="button-save" onclick="SaveButtonClick();" hidden="hidden">保存</button>
                    </div>
                </div>
                <div id="pro-footer">
                    <h1>this is footer</h1>
                </div>
            </div>
        </div>
    </body>
</html>