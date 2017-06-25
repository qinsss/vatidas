<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>查看日志页</title>
<link type="text/css" rel="stylesheet" href="css/findLog.css"> 
<script type="text/javascript" src="js/changeDateSubmit.js" charset="utf-8"></script> 
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js">
</script>
</head>
	
<body>
	<div id="date_choice_div">
        <form  name="date_choice_form" method="post" action="logAction_findLogByDate" >
            <span class="reminder_text">起始日期</span> 
			<input class="input_timeBox" id="startTime"  type="text" name="startDate" value="<s:property value="startDate"/>"
			onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate: '%y-%M-%d' ,minDate:'%y-{%M-2}-%d' })"  placeholder="2017-01-01" onchange="goSubmit();"/><!--只要日期发生改变即可刷新-->
            <span class="reminder_text">终止日期</span> 
			<input class="input_timeBox" id="endTime" type="text" name="endDate"  value="<s:property value="endDate"/>"
			onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-{%d+1}'})"  placeholder="2017-01-01" onchange="goSubmit();" />
			<span id="dateError"></span>
        </form>
    </div>
	<div id="log_table_div">
    	<table id="log_table">
        	<tr>
            	<th>操作人</th>
                <th width="200px">操作日期</th>
                <th width="240px">操作项</th>
                <th>操作参数</th>
                <th>操作结果</th>
            </tr>
	        <s:if test="logList.size == 0">
    			<tr>
    				<td colspan="5">系统当前没有日志</td>
    			</tr>
    		</s:if>
	        <s:else>
	            <s:iterator value="logList" var = "log">
	            	<tr>
	            		<td><s:property value="#log.operator"/></td>
	            		<td><s:date name="#log.operateTime" format="yyyy-MM-dd hh:mm:ss"/></td>
	            		<td><s:property value="#log.operateName"/></td>
	            		<td><s:property value="#log.operateParams"/></td>
	            		<td><s:property value="#log.operateResult"/></td>
	            	</tr> 
	            </s:iterator>
            </s:else>
        </table>
    </div>
</body>
</html>
