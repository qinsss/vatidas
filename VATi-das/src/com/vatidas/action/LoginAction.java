package com.vatidas.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.vatidas.entity.User;
import com.vatidas.service.IUserService;

/*
 * LoginAction 处理登录相关的操作
 */
public class LoginAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4538071837834330028L;
	//接收账号密码  需与jsp中输入框的name一致 使用属性驱动
	private String account;
	private String password;
	
	//处理用户的服务注入
	private IUserService userService;
	
	private Map<String, Object> sessionMap;
	
	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	/*
	 * 登录方法
	 */
	public String doLogin(){
		System.out.println("doLogin");
		return "toMianFramePage";
	}
	
	/*
	 * 登出方法  需要将session中的内容清除，也防止直接使用URL访问资源
	 */
	public String doLogout(){
		System.out.println("doLogout");
		sessionMap.clear();
		return "logout";
	}
	/*
	 * 对登录进行正确性验证  action继承ActionSupport
	 */
	public void validateDoLogin(){
		System.out.println("validateDoLogin");
		User user = userService.queryAccount(getAccount(),getPassword());
		if(user == null){
			this.addFieldError("infoError", "*输入的账号或者密码有误，请重新输入");
		}else{
			//系统有该账号，将它存进session并成功登录
			sessionMap.put("user", user);
		}
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.sessionMap = arg0;
	}
	
}
