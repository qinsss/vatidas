<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>账户管理目录</title>
<link type="text/css" rel="stylesheet" href="css/accountManagerCatalog.css"> 
</head>

<body>
	<div id="menu_div">账户管理</div>
	<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#38528d SIZE=3 align="left">
    <ul>
    	<li><a class="menu_left" href="accountManagerAction_findAccount" target="right">查看账号</a></li>
        <s:if test="#session.user.account == 'admin' ">
        	<br>
			<li><a class="menu_left" href="accountManagerAction_toAddAccount" target="right">添加新账号</a></li>
        </s:if>
        <br>
        <li><a class="menu_left" href="rightAction_findRight"  target="right">权限管理</a></li>
    </ul>
</body>
</html>