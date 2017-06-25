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
<title>增值税数据分析</title>
<link type="text/css" rel="stylesheet" href="css/vatAnalyze.css"> 
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/vatAnalyze.js" charset="utf-8"></script>
</head>

  
<body>
	
	<div class="inputFile_div">
		<s:form id="fileForm" action="importDataAnalyzeAction_fileUpload" method="post" enctype="multipart/form-data" onsubmit="return validateFile();">
			<input class="fileInput" type="file" id="excelFile" name="excelFile" placeholder="导入.xls或.xlsx文件" />
			<input class="input" type="submit" value="导入" onclick="validateFile();" onblur="clearError();"/>
			<input class="input" id="clearInput" type="button" value="清除导入数据" onclick="clearData();"/> 
			<span class="error" id="fileError">
				<s:property value="#request.importSuccess"/>
				<s:property value="#request.clearSuccess"/>
			</span>
		</s:form>
	</div>
	<div id="analyzeItem_choice_div">
		<form name="analyze_from" action="importDataAnalyzeAction_dataAnalyze" method="post" onsubmit="return goSubmit();">
		    	<span class="reminder_text">待分析项</span> 
				<select name="analyzeItem">
		        	<option  value="inMoney">进项金额</option>
		        	<option  value="outMoney">销项金额</option>
		        	<option  value="inOutMoney">进销项金额</option>
		        	<option  value="vatMoney">增值税额</option>
		        </select>
		        
		        <span class="reminder_text">分析图形状</span> 
		        <select name="analyzeView">
		        	<option selected value="bar">平面柱状图</option>
		            <option value="line">平面折线图</option>
		            <option value="pie">平面饼图</option>
		        </select>
		        
		        <span class="reminder_text">起始年月</span> 
		        <input class="input" type="text" id="startTime" name="startYm"
		        value="${startYm}" onFocus="WdatePicker({dateFmt:'yyyy-MM',maxDate:'%y-%M'})" placeholder="2017-01"/ required>
		        <span class="reminder_text">截止年月</span> 
		        <input class="input" type="text"  id="endTime" name="endYm" value="${endYm }"
		        onFocus="WdatePicker({dateFmt:'yyyy-MM',maxDate:'%y-%M'})"  placeholder="2017-12"  required/>
				<span class="reminder_text">按月/季度</span> 
		        
		        <select name="monthOrQuarter">
		        	<option selected value="month">月</option>
		            <option value="quarter" disabled="disabled">季度</option>
		        </select>
				<input class="input" type="submit" value="查看图表" onclick="goSubmit();"/>
		</form>
 	</div>
	<div id="download_div">
        <button class="downloadText" type="submit" onclick="downloadChart(<s:property value="#request.img"/>);">保存分析图</button>
    </div>
    <center>
    <s:if test="#request.importSuccess != null ">
    	<table class="dataView_table">
    		<tr>
    			<td colspan="4" style="font-size:23px;color:#3887C7;">新导入的数据项</td>
    		</tr>
    		<s:iterator value="importInvoiceList" var="invoice">
    			<tr>
    				<td><s:date name="#invoice.yearMonth" format="yyyy-MM" /></td>
					<td><s:property value="#invoice.commodityName"/></td>
					<td><s:property value="#invoice.type"/></td>
					<td><s:property value="#invoice.money"/>万元</td>    			
    			</tr>
    		</s:iterator>
    	</table>
   	 </s:if>
    <s:elseif test="#request.img == null">
    	<img   alt="数据	图表" src=""/>
    </s:elseif>
    <s:else>
	    <img  alt="数据图表" src="<%=basePath+"chart/chart.png"%>"/>
    </s:else>
    </center>
</body>
</html>