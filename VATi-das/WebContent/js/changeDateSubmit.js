function goSubmit(){
	String.prototype.trim=function(){
		return this.replace(/^\s*/,"").replace(/\s*$/,"");
	}
	
	var startTime= document.getElementById("startTime").value;
	var endTime= document.getElementById("endTime").value;
	var beginDate = new Date(startTime.replace(/-/g,"/"));
	var endDate = new Date(endTime.replace(/-/g,"/"));
	var dateError = document.getElementById("dateError");
	if((startTime.trim() == "") || ( endTime.trim() == "")){
		dateError.innerHTML="* 必须选择时间区间";
	}else if(beginDate >= endDate){
		dateError.innerHTML="* 截止日期必须大于起始日期";
	}else if(((endDate - beginDate)/1000/60/60/24/31) > 12){
		dateError.innerHTML="* 日期区间最大为12个月";
	}else{
		dateError.innerHTML="";
		document.forms["date_choice_form"].submit();
	}
}