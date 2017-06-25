function loginValidate(){
	var flag;
	String.prototype.trim=function(){
		return this.replace(/^\s*/,"").replace(/\s*$/,"");
	}
	var password = document.getElementById("password").value;
	var account = document.getElementById("account").value;
	var loginError = document.getElementById("loginError");
	if((account.trim()=="") || (password.trim()=="")){
		loginError.innerHTML = "*账号或密码不能为空";
		flag = false;
	}
	return flag;
}
function clearError(){
	document.getElementById("loginError").innerHTML = "&nbsp";
	document.getElementById("validError").innerHTML = "&nbsp";
}
