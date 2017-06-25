package com.vatidas.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.vatidas.entity.User;
/**
 * 处理用户账号的Action
 * 包括查看账号，添加账号，修改密码，删除账号的操作
 */
import com.vatidas.service.IUserService;

public class AccountManagerAction  {
	
	//注入user服务
	private IUserService userService;
	private List<User> userList; //传递账号列表
	private String account; //接收账号
	private String oldPassword;//接收原密码
	private String newPassword;//接收新密码
	private String confirmPassword;//接收确认密码
	private String password;//接收添加账号的密码
	private String nickname;//接收添加账号的昵称
	private String role;//接收添加账号的角色名
	

	@SuppressWarnings("unchecked")
	Map<String,String> reqMap = ((Map<String, String>) ActionContext.getContext().get("request"));
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public String getPassword() {
		return password;
	}

	public String getNickname() {
		return nickname;
	}

	public String getRole() {
		return role;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setRole(String role) {
		this.role = role;
	}

	
	
	
	
	public String findAccount(){
		userList = userService.findAllAccount();
		reqMap.put("flag", "flag");
		return "findAccount";
	}
	
	public String deleteAccount(){
		userService.deleteAccount(ServletActionContext.getRequest().getParameter("account"));
		return "toFindAccount";
	}
	
	public String modifyPassword(){
		boolean ok = false;
		//验证原密码，不正确返回修改密码页面进行提示
		ok = userService.validatePassword(account,oldPassword);
		if(ok){
			reqMap.put("passwordError", "原密码错误");
			return "modifyPassword";
		}else{  //正确则修改密码返回查看账号页面
			userService.modifyPassword(account, newPassword);
			return "toFindAccount";
		}
	}
	public String toAddAccount(){
		return "editAccount";
	}
	
	public String addAccount(){
		String accountExist = "exist";
		accountExist = userService.checkAddAccount(account);
		reqMap.put("accountExist", accountExist);
		if("exist".equals(accountExist)){
			return "editAccount";
		}
		userService.addAccount(account,password,role,nickname);
		return "toFindAccount";
	}
	
	
}
