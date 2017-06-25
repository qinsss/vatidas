<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>查看账号</title>
<link type="text/css" rel="stylesheet" href="css/findAccount.css"> 
<script type="text/javascript">
	function deleteAccount(account){
		if(confirm("确认删除？"))
			window.location.href="accountManagerAction_deleteAccount?account="+account;
	}
	//window.location.reload(true);
</script>
</head>
<body>
	<div id="account_table_div">
    	<table id="account_table">
        	<tr>
            	<th>账号</th>
                <th>昵称</th>
                <th>担任角色</th>
                <th>操作</th>
            </tr>
            <s:if test="userList == null">
            	<tr> 
            		<td colspan="4">系统当前没有账号，请<a class="operator" href="addAccount.jsp">添加账号</a></td>
            	</tr>
            </s:if>
            <s:else>
            	<s:iterator value="#request.userList" var="user">
		            <tr>
		            	<td><s:property value="#user.account" /></td>
		                <td><s:property value="#user.nickname"/></td>
		                <td><s:property value="#user.role.name"/></td>
		                <td>
		                <s:if test="#user.account == #session.user.account" >
		                	<a class="operate" href="modifyPassword.jsp?account=<s:property value="#user.account" />">修改密码</a> 
		                </s:if>
		                <s:elseif test="#session.user.account == 'admin'">
		                	<a class="operate" href="modifyPassword.jsp?account=<s:property value="#user.account" />">修改密码</a> 
		                	<a class="operate" href="#" onclick="deleteAccount(<s:property value="#user.account"/>)">删除</a></td>
		            	</s:elseif>
		            <tr>
	            </s:iterator>
            </s:else>
        </table>
    </div>
</body>
</html>