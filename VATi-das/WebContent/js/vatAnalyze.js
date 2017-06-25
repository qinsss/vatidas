function goSubmit(){
	var flag;
	var startTime= document.getElementById("startTime").value;
	var endTime= document.getElementById("endTime").value;
	String.prototype.trim=function(){
		return this.replace(/^\s*/,"").replace(/\s*$/,"");
	}
	
	if((startTime.trim() == "") || ( endTime.trim() == "")){
		flag = false;
	}else{
		flag = true;
	}
	return flag;
}
function validateFile(){
	var flag;
	file = document.getElementById("excelFile").value;
	var pos1 = file.lastIndexOf(".xls");
	var pos2 = file.lastIndexOf(".xlsx");
	var fileError = document.getElementById("fileError");
	if(pos1 == -1 && pos2 == -1){
		fileError.innerHTML = "* 文件格式错误";
		flag = false;
	}else{
		flag = true;
	}
	return flag;
}
function clearError(){
	document.getElementById("fileError").innerHTML="";
}
function clearData(){
	document.getElementById("fileError").innerHTML="";
	window.location.href="importDataAnalyzeAction_clearImportData";
}
function downloadChart(img){
	if(img == "1"){
		window.location.href="invoiceAnalyzeAction_getDownloadChart";
	}else{
		alert("当前没有图表");
		window.location.href="#"
	}
}