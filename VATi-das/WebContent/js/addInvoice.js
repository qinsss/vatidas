function nullValidate(){
		String.prototype.trim=function(){
			return this.replace(/^\s*/,"").replace(/\s*$/,"");
		}
		var invoiceCode = document.getElementById("invoiceCode").value;
		var invoiceDate = document.getElementById("invoiceDate").value;
		var commodityMoney = document.getElementById("commodityMoney").value;
		var invoiceTaxMoney = document.getElementById("invoiceTaxMoney").value;
		var invoiceMoney = document.getElementById("invoiceMoney").value;
		var infoNullError = document.getElementById("infoNullError");
		if(invoiceCode.trim() == ""  ||  invoiceDate.trim() == ""   ||  commodityMoney.trim() == ""  
			||  invoiceTaxMoney.trim() == ""  ||  invoiceMoney.trim() == "" ){
			infoNullError.innerHTML=" 带*项不能为空";
			return false;
		}else if(document.getElementById("infoNullError").innerHTML != ""){
			return false;
		}else{
			document.form["addform"].submit();
		}
	}
	function clearError(){
		document.getElementById("infoNullError").innerHTML="";
	}

	function checkCode(code){
		if(code != "")
			window.location.href="invoiceAction_checkCode?code="+code;
	}


