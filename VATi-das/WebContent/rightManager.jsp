<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限管理</title>
<link type="text/css" rel="stylesheet" href="css/rightManager.css"> 
<script type="text/javascript" src="js/rightManager.js" charset="utf-8"></script>  
<script type="text/javascript">
	function findRoleRight(value){
		if(value != "x")
			window.location.href="rightAction_findRoleRight?roleName="+value;
	}
	window.onload = function(){
			var role = "${role.name}";
			var select = document.getElementById("roleSelect");
			for(var i = 0; i < select.options.length; i++){
				if(select.options[i].value == role){
					select.options[i].selected = "selected";
				}
			}
	}
	function isAuthorize(){
		var select = document.getElementById("roleSelect"); 
		if(select.value == "x")	
			return false;
	}
</script>
</head>
<body>
	
		<div id="reminder_text_div"><span>选择授予
			<select name="roleName" id="roleSelect" onchange="findRoleRight(this.value);">
				<option value="x">选择角色</option>
				<option value="财务人员">财务人员</option>
				<option value="企业运营人员">企业运营人员</option>
				<option value="管理员">管理员</option>
			</select>
			角色的权限项</span>
		</div>
		<div id="right_table_div">
		<form action="rightAction_authorize" method="post">
	    	<table id="right_table">
		        	<tr>
		            	<th width="80px">权限码</th>
		                <th width="200px">权限名</th>
		                <th width="300px">权限URI</th>
		                <th><button id="allChoice_button" type="button" onclick="allChoice(this.innerHTML)">全选</button></th>
		            </tr>
		            <!-- 从application中取得所有权限。。 -->
		            <s:iterator value="#application.rightsMap" var = "right">
			            <tr>
			            	
			            	<td><input type="hidden" name="right.id" value=<s:property value="value.id"/> readonly/> 
			            	 <input type="text" name= "right.rightCode" value=<s:property value="value.rightCode"/> readonly /></td>
			                <td><input type="text" name="right.rightName" value=<s:property value="value.rightName" /> readonly/></td>
			                <td><input type="text" name="right.rightUrl" value=<s:property value="value.rightUrl"/> readonly/></td>
			                <s:if test="@com.vatidas.utils.RightUtil@contains(value,role) == true">
			                	<td><input class="right_select" checked="checked" type="checkbox" name="rightauthor" value="y"/></td>
			                </s:if>
			                <s:else>
			                	<td><input class="right_select"  type="checkbox" name="rightauthor" value="y"/></td>
			                </s:else>
			            </tr>
		            </s:iterator>
		            <tr>
		            	<td colspan="4"><input id="confirm_button" type="submit" value="确定" onclick="return isAuthorize()"/></td>
		            </tr>
	        </table>
		</form>
    </div>
</body>
</html>
