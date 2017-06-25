<%@page import="com.vatidas.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>系统头部导航</title>
<link type="text/css" rel="stylesheet" href="css/head.css"> 
<script type="text/javascript" src="js/head.js" charset="UTF-8"></script>  
</head>
<body>
	<div class="head_loginInfo_div">
		欢迎,
		<%-- <span class="userNickname"><%=((User)session.getAttribute("user")).getNickname();%></span> &nbsp;&nbsp; --%>
		<span class="userNickname">${sessionScope.user.role.name}，${sessionScope.user.nickname}</span>
		<%--需要清除session --%>
		[<a class="logout" onclick="logout();">退出系统</a>]
	</div>
    <div class="menuBar_div">
		<span class="menuBar_text">
			<a class="menu" id="invoiceManager" href="#" onclick=" turnPage(this.id);">发票管理</a>
		</span> 
		<span class="menuBar_text">
			<a class="menu" id="invoiceAnalyze" href="#" onclick="turnPage(this.id);">发票数据统计分析</a>
		</span>
		<span class="menuBar_text">
			<a class="menu"  id="accountManager" href="#" onclick="turnPage(this.id);">账号管理</a>
		</span>
		<s:if test="#session.user.account == 'admin' || #session.user.role.name == '企业运营人员' ">
			<span class="menuBar_text">
				<a class="menu"  id="findLog" href="#" onclick="turnPage(this.id);">查看日志</a>
			</span>
		</s:if>
	</div>
</body>
</html>
