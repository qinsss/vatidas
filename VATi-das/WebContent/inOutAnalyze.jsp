<%@page import="java.io.File"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>进销项数据分析</title>
<link type="text/css" rel="stylesheet" href="css/inOutAnalyze.css">
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/inOutAnalyze.js" charset="utf-8"></script> 
<script type="text/javascript">
	window.onload = function(){
		var analyzeItem ="${analyzeItem}";
		var select = document.getElementsByName("analyzeItem")[0];
		for(var i = 0; i < select.options.length; i++){
			if(select.options[i].value == analyzeItem){
				select.options[i].selected = "selected";
			}
		}
		
		var analyzeItem = document.getElementsByName("analyzeItem")[0].value;
		var analyzeView = document.getElementsByName("analyzeView")[0];
		if(analyzeItem == "inOutMoney" || analyzeItem == "inOutTaxMoney"){
			analyzeView.options.remove(2);
		}else{
			var option = document.createElement("option");
			option.value = "pie";
			option.innerHTML = "平面饼图";
			analyzeView.add(option,null);
		}
		
		var analyzeView ="${analyzeView}";
		var select = document.getElementsByName("analyzeView")[0];
		for(var i = 0; i < select.options.length; i++){
			if(select.options[i].value == analyzeView){
				select.options[i].selected = "selected";
			}
		}
		/* if("flag" != "${submitFlag}"){
			document.forms["analyze_from"].submit(); 
		} */
		document.getElementById("img").src = "<%=basePath %>chart/chart.png?t=" + Math.random(); 
	}

	function goSubmit(){
		var flag;
		var startTime= document.getElementById("startTime").value;
		var endTime= document.getElementById("endTime").value;
		var beginDate = new Date(startTime.replace(/-/g,"/"));
		var endDate = new Date(endTime.replace(/-/g,"/"));
		var dateError = document.getElementById("dateError");
		var analyzeItem = document.getElementsByName("analyzeItem")[0].value;
		var analyzeView = document.getElementsByName("analyzeView")[0];
		
		String.prototype.trim=function(){
			return this.replace(/^\s*/,"").replace(/\s*$/,"");
		}
		if((startTime.trim() == "") || ( endTime.trim() == "")){
			dateError.innerHTML="*必须选择日期区间";
			flag = false;
		}else if(beginDate >= endDate){
			dateError.innerHTML="* 截止年月必须大于起始年月";
			flag = false;
		}else if(((endDate - beginDate)/1000/60/60/24/31) > 12){
			dateError.innerHTML="* 时间区间最大为12个月";
			flag = false;
		}else{
			if((analyzeItem == "inOutMoney" || analyzeItem == "inOutTaxMoney") && analyzeView.value == "pie"){
				analyzeView.value="bar";
			}
		 	dateError.innerHTML="";
		 	flag = true;
		}
		return flag;
	}
	function download(name){
		if(name == "downloadChart"){
			window.location.href="invoiceAnalyzeAction_getDownloadChart";
		}else if(name == "downloadSheet"){
			if(document.getElementsByName("analyzeView")[0].value=="pie"){
				alert("请选择柱形或折线视图导出数据")
			}else
				window.location.href="invoiceAnalyzeAction_getAnalyzeData";
		}
	}
</script> 
</head>
<body>
	<div id="analyzeItem_choice_div">
		<form name="analyze_from" action="invoiceAnalyzeAction_findAnalyzeView" method="post" >
			<input type="hidden"  name="submitFlag" value="flag"/>
			<span class="reminder_text">待分析项</span> 
			<select name="analyzeItem"  onchange="goSubmit();">
				<option	value="inMoney">进项发票金额</option>
				<option value="outMoney">销项发票金额</option>
				<option value="inTaxMoney">进项发票税额</option>
				<option value="outTaxMoney">销项发票税额</option>
				<option value="inOutMoney">进销项发票金额</option>
				<option value="inOutTaxMoney">进销项发票税额</option>
				<option value="vatMoney">增值税金额</option>
			</select>
			
			<span class="reminder_text">分析图形状</span> 
			<select name="analyzeView">
				<option value="bar">平面柱状图</option>
				<option value="line">平面折线图</option>
			</select>
			<span id="dateError"></span>
			<br>
			<span class="reminder_text">起始年月</span> 
			<input class="input_timeBox" type="text" id="startTime" name="startYm" 
			value=<s:if test="startYm==null">"2017-01"</s:if><s:else><s:property value="startYm"/></s:else> 
			onFocus="WdatePicker({dateFmt:'yyyy-MM',maxDate:'%y-%M'})" required placeholder="2017-01"/>
			
			<span class="reminder_text">截止年月</span> 
			<input class="input_timeBox" type="text"  id="endTime" name="endYm" 
			value=<s:if test="endYm==null">
	        	<%=new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime())%></s:if>
	       		<s:else><s:property value="endYm"/></s:else>
			onFocus="WdatePicker({dateFmt:'yyyy-MM',maxDate:'%y-%M'})" required placeholder="2017-12" />
			
			
			<span class="reminder_text">按月/季度</span> 
			<select name="monthOrQuarter" >
				<option value="month">月</option>
				<option value="quarter" disabled="disabled">季度</option>
			</select>
			<input type="submit" value="查看图表" onclick = "return goSubmint();"/>
		</form>
	</div>
	
	<div id="download_div">
    	<button class="downloadText" name="downloadSheet" onclick="download(name);">导出数据</button>
        <button class="downloadText" name="downloadChart" onclick="download(name);">保存分析图</button>
    </div>
    <center>
		<s:if test="#request.img == null">
	    	<img  alt="confirm choice chartView" src=""/>
	    </s:if>
	    <s:else>
		    <img  alt="数据图表" id="img"/>
	    </s:else>
    </center>
	<!-- <div id="analyzeView_div">
		<img alt="发票数据分析图表" />
	</div> -->
	
    
  
</body>
</html>