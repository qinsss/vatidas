<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
	<title>发票列表</title>
	<link type="text/css" rel="stylesheet" href="css/invoiceList.css"/> 
	<script type="text/javascript" src="js/invoiceList.js" charset="utf-8"></script>  
	<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="js/changeDateSubmit.js"></script>
	<script type="text/javascript">
		window.onload = function(){
			var invoiceClasses ="${invoiceClasses}";
			var select = document.getElementsByName("invoiceClasses")[0];
			for(var i = 0; i < select.options.length; i++){
				if(select.options[i].value == invoiceClasses){
					select.options[i].selected = "selected";
				}
			}
			//判断是否已提交过
			//刷新或不是经过查询invoiceAction_findInvoice到达此页面时{request.submitFlag}为null
			 if("1" != "${submitFlag}"){
				document.forms["date_choice_form"].submit();
			} 
		}
	 </script>
</head>

<body>
	<div id="date_choice_div">
		<form  name="date_choice_form" method="post"  action="invoiceAction_findInvoice">
			<input type="hidden" id="submitFlag" name="submitFlag" value="1"/> 
	        <span class="reminder_text">起始日期</span>
	        <input class="input_timeBox" type="text" id="startTime" name="startTime" 
	        value=<s:if test="startTime==null">"2017-01-01"</s:if><s:else>'<s:property value="startTime"/>'</s:else> 
	        onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" placeholder="2017-01-01" onchange="goSubmit();"/>
	       
	        <span class="reminder_text">截止日期</span> 
	        <input class="input_timeBox" type="text" id="endTime" name="endTime" 
	        <%-- value="<s:property value="#session.endTime"/>"  --%>
	        value=<s:if test="endTime==null">
	        	<%=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime())%></s:if>
	       		<s:else><s:property value="endTime"/></s:else>  
	        onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-{%d+1}'})" placeholder="2017-01-01" onchange="goSubmit();"/>
	        
	        <span class="reminder_text">进销类别</span>
	        <select class="choice_box" name="invoiceClasses" onchange="goSubmit();">
	         	<option value="all">全部</option>
	            <option value="in" >进项</option>
	            <option value="out">销项</option>
	        </select>
	        <span id="dateError"></span>
        </form>
    </div>
    
    <div id="invoice_table_div">
    	<table id="invoice_table">
        	<tr>
            	<th>发票编号</th>
                <th>开票日期</th>
                <th>发票类别</th>
                <th>开票单位</th>
                <th>商品名称</th>
                <th>商品单价 (/元)</th>
                <th>商品数量</th>
                <th>商品金额 (/万元)</th>
                <th>发票税额 (/万元)</th>
                <th>发票金额 (/万元)</th>
                <th>操作</th>
            </tr>
            <s:if test="invoicePage.invoiceList == null">
	            <tr >
	            	<th colspan="11"> <span id="noInvoiceText">选择日期和发票类型进行查看</span></th>
	            </tr>
	        </s:if>
            <s:else>
	            <s:iterator value="invoicePage.invoiceList" var="invoice">
		            <tr>
		            	<td><s:property value="#invoice.code"/></td>
		                <td><s:date name="#invoice.date" format="yyyy-MM-dd" /></td>
		                <td><s:property value="#invoice.type"/></td>
		                <td><s:property value="#invoice.unit"/></td>
		                <td><s:property value="#invoice.commodityName"/></td>
		                <td><s:property value="#invoice.commodityPrePrice"/></td>
		                <td><s:property value="#invoice.commodityNum"/></td>
		                <td><s:property value="#invoice.commodityMoney"/></td>
		                <td><s:property value="#invoice.invoiceTaxMoney"/></td>
		                <td><s:property value="#invoice.totalMoney"/></td>
		                <td><a class="operate" onclick="deleteInvoice('${invoice.code}');">删除</a></td>
		            <tr>
	            </s:iterator>
    		</s:else>
        </table>
    </div>
    
    <s:if test="invoicePage != null">
	    <div id="no_form_div">
		     <s:form name="gotoPage_form" action="invoiceAction_findInvoice" namespace="/" method="post" >
				  <input type="hidden" id="submitFlag" name="submitFlag" value="1"/> 
				  <input type="hidden" name="startTime" value="<s:property value='#session.startTime'/>"/> 
			      <input type="hidden" name="endTime" value="<s:property value='#session.endTime'/>"/>
			      <input type="hidden" name="invoiceClasses" value="<s:property value='#session.invoiceClasses'/>"/><%-- 这里多的一个空格花了我整整1个小时 --%>
			      <input class="goto_box" type="text" name="page" onfocus="clearError();"/>
			      <input class="reminder_text" type="submit" value="跳转" onclick="return validate(<s:property value="invoicePage.totalPage"/>);"/>
		     </s:form>
	   	</div>
	    <div id="no_view_div">
	    	<span class="reminder_text">总记录数 <span class="recoderCount"><s:property value="invoicePage.allCount"/></span>&nbsp条</span>
	        <span class="reminder_text"> 
	        	<span class="recoderCount">
	        		<s:property value="invoicePage.currentPage"/>&nbsp/
	        		<s:property value="invoicePage.totalPage"/>	
	        	</span>
	                     页</span>
	    </div>
	   
		<div id="number_error_div">
			<span id="gotoNumberError"></span>
		</div>
	</s:if>
	
</body>
</html>