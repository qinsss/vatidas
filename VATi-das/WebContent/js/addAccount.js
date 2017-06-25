function formValidate(){
	var flag;
	var password = document.getElementById("password").value;
	var account = document.getElementById("account").value;
	var nickname = document.getElementById("nickname").value;
	var accountError = document.getElementById("accountError");
	var nicknameError = document.getElementById("nicknameError");
	var passwordError = document.getElementById("passwordError");
	String.prototype.trim=function(){
		return this.replace(/^\s*/,"").replace(/\s*$/,"");
	}
	String.prototype.trim=function(){
		return this.replace(/^\s*/,"").replace(/\s*$/,"");
	}
	if(account.trim()==""){
		accountError.innerHTML = "*账号不能为空";
		flag =false;
	}else if(!(/^[0-9]+$/.test(account))){//只能是数字
		accountError.innerHTML = "*账号格式只能是数字";
		flag = false;
	}else if(account.length>10){
		accountError.innerHTML = "*账号长度最大为10个字符";
		flag = false;
	}
	if(password.trim()==""){
		passwordError.innerHTML = "*密码不能为空";
		flag = false;
	}else if(password.length<6 || password.length>9){
		passwordError.innerHTML = "*密码长度在[6-9]位";
		flag = false;
	}
	if(nickname.trim()==""){
		nicknameError.innerHTML = "*昵称不能为空";
		flag = false;
	}else if(password.length>10){
		nicknameError.innerHTML = "*昵称长度最大为10个字符";
		flag = false;
	}else{
		flag=true;
	}
	return flag;
}
function clearError(id){
	if(id=="account"){
		document.getElementById("accountError").innerHTML="";
	}else if(id=="nickname"){
	 document.getElementById("nicknameError").innerHTML="";
	}else if(id=="password"){
	 document.getElementById("passwordError").innerHTML="";
	}
}