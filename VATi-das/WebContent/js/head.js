function turnPage(id){
	var parentWindow = window.parent;
	var leftTarget = parentWindow.left;
	var rightTarget = parentWindow.right;
	if(id=="invoiceManager"){
		leftTarget.location  = "invoiceManagerCatalog.jsp";
		rightTarget.location  = "invoiceList.jsp";
	}else if(id == "invoiceAnalyze"){
		leftTarget.location  = "invoiceAnalyzeCatalog.jsp";
		rightTarget.location = "inOutAnalyze.jsp";
	}else if(id=="accountManager"){
		leftTarget.location  = "accountManagerCatalog.jsp";
		rightTarget.location = "accountManagerAction_findAccount";
	}else if(id == "findLog"){
		leftTarget.location  = "findLogCatalog.jsp";
		rightTarget.location = "logAction_findLogs";
	}
}
function logout(){
	var flag = confirm("确认退出？");
	if(flag == true){
		top.location.href="loginAction_doLogout";
	}else{
		return;
	}
}