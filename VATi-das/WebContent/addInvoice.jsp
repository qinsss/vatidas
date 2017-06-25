<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>添加发票</title>
<link type="text/css" rel="stylesheet" href="css/addInvoice.css"> 
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/addInvoice.js"></script>
<script>
	window.onload = function(){
		var codeExist = "${exist}";
		if(codeExist == "true"){
			document.getElementById("infoNullError").innerHTML="* 该发票已存在，请核对";
		}
	}
</script>
</head>
	
<body>
	<div id="input_div">
    	<span class="reminder_text">请输入发票信息（带<span style="color:red">*</span>项为必填项）</span>
    	<span class="errorInfo" id="infoNullError"></span>
        <form  name="addform" method="post" action="invoiceAction_addInvoice">
        	<span class="info_item"><span class="must_mark">*</span>发票编号</span>
			<input class="info_input" id="invoiceCode" type="text" name="invoice.code"
			value="${code }"
			onBlur="checkCode(this.value);" onfocus="clearError();"/>
			
            <span class="info_item">开票单位</span>
			<input class="info_input" id="invoice_unit" type="text" name="invoice.unit" />
            <br>

            <span class="info_item"><span class="must_mark">*</span>开票日期</span>
			<input class="info_input" id="invoiceDate" type="text" name="invoice.date"  placeholder="2017-01-01" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})"/>
            <span class="info_item">商品名称</span>
			<input class="info_input" type="text" name="invoice.commodityName" />
            <br>

             <span class="info_item"><span class="must_mark">*</span>商品金额</span>
			 <input class="info_input" id="commodityMoney" type="text" name="invoice.commodityMoney"  placeholder="0.0000万元"/>
             <span class="info_item">商品单价</span>
			 <input class="info_input" type="text" name="invoice.commodityPrePrice" placeholder="0.0000元 "/>
            <br>

            <span class="info_item"><span class="must_mark">*</span>发票税额</span>
			<input class="info_input" id="invoiceTaxMoney" type="text" name="invoice.invoiceTaxMoney"  placeholder="0.0000万元"/>
            <span class="info_item">商品数量</span>
			<input class="info_input" type="text" name="invoice.commodityNum" />
            <br>

            <span class="info_item"><span class="must_mark">*</span>发票金额</span>
			<input class="info_input" id="invoiceMoney" type="text" name="invoice.totalMoney"  placeholder="0.0000万元"/>
            <span class="info_item">商品单位</span>
			<input class="info_input" type="text" name="invoice.commodityUnit" />
            <br>

			<span class="info_item"><span class="must_mark">*</span>发票类别</span>
            <select id="chioce_box" name="invoice.type">
            	<option value="进项" selected>进项</option>
                <option value="销项" selected>销项</option>
            </select>
            <br>
     		<br>
            <input id="submit_input" type="submit" value="确定"  onclick="return nullValidate();" />
            <input id="reset_input" type="reset" value="全部重填"/>
        </form>
    </div>
</body>
</html>
