<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>登录系统</title>
<link type="text/css" rel="stylesheet" href="css/login.css"> 
<script type="text/javascript" src="js/login.js" charset="utf-8"></script>  
<script type="text/javascript">
	if (top.location != self.location) {  
	
	    top.location = self.location;  
	
	}  
</script>
</head>
<body>
	<div class="login_div">
		<div class="login_image_div"></div>
			<div class="login_item_div">
				<div class="login_font_div">
					<span class="login_font">登录系统</span>
				</div>
				<div class="login_input_div">
					<s:form  method="post" action="loginAction_doLogin" namespace="/" onsubmit="return loginValidate();">
						<input class="input_box" id="account" type="text" name="account" value="${account}" placeholder="账号" onfocus="clearError();"/><br>
						<input class="input_box"  id="password" type="password" name="password" value="${password}" placeholder="密码"  onfocus="clearError();"/><br>
						<div class="error_div">
							<span id="loginError">&nbsp</span>&nbsp
							<span id="validError"><s:fielderror  cssClass="validError" ></s:fielderror></span>
						</div>
						<input class="submit_button" type="submit" onclick="loginValidate();" value="登录" /><br>
					</s:form>
					<br/>
					<span id="forgetPass">忘记密码？请联系管理员</span>
				</div>
			</div>
	</div>
	<div class="tail_div"></div>
</body>
</html>