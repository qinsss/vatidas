<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>添加新账号</title>
<link type="text/css" rel="stylesheet" href="css/addAccount.css"> 
<script type="text/javascript" src="js/addAccount.js" charset="utf-8"></script> 
<script type="text/javascript">

	window.onload = function(){
		var codeExist = "${accountExist}";
		if(codeExist == "exist"){
			document.getElementById("accountError").innerHTML="* 该账号已存在";
		}
	}
</script>
</head>
	
<body>
	<div id="input_div">
    	<span class="reminder_text_in">填入新账号信息</span>
    	<form id="addAcountForm" action="accountManagerAction_addAccount" method="post" onsubmit="return formValidate()">
        	<span class="info_item">账&nbsp;&nbsp;号</span>
			<input class="info_input" id="account" type="text" name="account"   placeholder="员工编号或数字字母组成" 
			value="${account}" onblur="formValidate(this.value);" onfocus="clearError(this.id)"/>
			<span class="errorInfo" id="accountError"></span><br>
			
            <span class="info_item">密&nbsp;&nbsp;码</span>
			<input class="info_input" id="password" type="password" name="password"  placeholder="密码6-9位长度字符"  onblur="formValidate();" onfocus="clearError(this.id);"/>
			<span  class ="errorInfo" id="passwordError"></span><br>
			
            <span class="info_item">角&nbsp;&nbsp;色</span>
            <select name="role">
                <option value="财务人员">财务人员</option>
            	<option value="企业运营人员">企业运营者</option>
            </select><br>
            
            <span class="info_item">昵&nbsp;&nbsp;称</span>
			<input class="info_input" id="nickname" type="text" name="nickname"  placeholder="小张"  onblur="formValidate();" onfocus="clearError(this.id); "/>
			<span class="errorInfo" id="nicknameError"></span><br>
            <input id="submit_input" type="submit" value="确定"/>
            <input id="reset_input" type="reset" value="重填"/>
        </form>
    </div>
</body>
</html>
