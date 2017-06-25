function validate(totalPage){
	var flag;
	var inte = /^(0|\+?[1-9][0-9]*)$/;/*正整数的正则式*/
	var page = document.getElementsByName("page")[0].value;
	var gotoNumberError = document.getElementById("gotoNumberError");
	if(!inte.test(page)){
		gotoNumberError.innerHTML="* 请输入正确跳转页号";
		flag = false;
	}else if(parseInt(page) > parseInt(totalPage)){
		gotoNumberError.innerHTML="* 要跳转的页面不存在";
		flag = false;
	}
	return flag;
}
function clearError(){
	var gotoNumberError = document.getElementById("gotoNumberError");
	gotoNumberError.innerHTML="";
}

function deleteInvoice(code){
	if(confirm("确定删除？"))
		window.location = "invoiceAction_deleteInvoice?code="+code;
}

