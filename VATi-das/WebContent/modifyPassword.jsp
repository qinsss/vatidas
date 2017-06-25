<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改密码页</title>
<link type="text/css" rel="stylesheet" href="css/modifyPassword.css"> 
<script type="text/javascript" src="js/modifyPassword.js" charset="utf-8"></script>  
</head>
	
<body>
	<div id="updatePass_div">
    	<span class="reminder_text_in">填入密码信息</span><a id="return" href="accountManagerAction_findAccount">返回</a>
    	<form action="accountManagerAction_modifyPassword" method="post" onSubmit="return passwordValidate();" >
        	<span class="info_item">账&nbsp;&nbsp;号&nbsp;&nbsp;</span>
			<input class="info_input" id="account" type="text" name="account" value="${account }" readonly="readonly" /> <br>
            
            <span class="info_item">旧密码&nbsp;&nbsp;</span>
			<input class="info_input" id="oldPassword" type="password" name="oldPassword"  placeholder="账号原密码" onblur="passwordValidate();" onfocus="clearError(this.id);"/>
			<span class="errorInfo" id="oldPasswordError"></span>
			<span class="errorInfo" id="passwordError"><s:property value="#request.passwordError"/></span><br>
			
            <span class="info_item">新密码&nbsp;&nbsp;</span>
			<input class="info_input" id="newPassword" type="password" name="newPassword"   / placeholder="账号新密码" onblur="passwordValidate();" onfocus="clearError(this.id);">
			<span class="errorInfo" id="newPasswordError"></span><br>
            
            <span class="info_item">确认密码</span>
			<input class="info_input" id="confirmPassword" type="password" name="confirmPassword"  placeholder="确认新密码" onblur="passwordValidate();" onfocus="clearError(this.id);"/>
			<span class="errorInfo" id="confirmPasswordError"></span><br>
			
			
            <input id="submit_input" type="submit" value="确定"/>
            <input id="reset_input" type="reset" value="重填"/>
        </form>
    </div>
</body>
</html>
