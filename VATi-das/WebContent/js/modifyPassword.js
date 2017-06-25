function passwordValidate(){
	var flag;
	String.prototype.trim=function(){
		return this.replace(/^\s*/,"").replace(/\s*$/,"");
	}
	var oldpassword = document.getElementById("oldPassword").value;
	var newPassword = document.getElementById("newPassword").value;
	var confirmPassword = document.getElementById("confirmPassword").value;
	var oldPasswordError = document.getElementById("oldPasswordError");
	var newPasswordError = document.getElementById("newPasswordError");
	var confirmPasswordError = document.getElementById("confirmPasswordError");
	//验证原密码   是否正确留在后台验证...ajax
	if(oldpassword.trim()==""){
		oldPasswordError.innerHTML = "*原密码必填";
		flag = false;
	}
	else if(newPassword.trim()==""){
		newPasswordError.innerHTML = "*新密码不能为空";
		flag = false;
	}else if(newPassword.length<6 || newPassword.length>9){
		newPasswordError.innerHTML = "*密码长度在[6-9]位之间";
		flag = false;
	}else if((newPassword.trim()=="")||(confirmPassword != newPassword)){
		confirmPasswordError.innerHTML = "*确认密码与新密码不一致";
		flag = false;
	}else{
		flag=true;
	}
	return flag;
}
function clearError(id){
	if(id=="newPassword"){
		document.getElementById("newPasswordError").innerHTML="";
	}else if(id=="oldPassword"){
	 document.getElementById("oldPasswordError").innerHTML="";
	 document.getElementById("passwordError").innerHTML="";
	}else if(id=="confirmPassword"){
	 document.getElementById("confirmPasswordError").innerHTML="";
	}
}