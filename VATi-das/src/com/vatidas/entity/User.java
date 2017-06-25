package com.vatidas.entity;

import java.util.Set;

public class User {
	
	private Integer id;
	private String account;
	private String password;
	private String nickname;
	

	//账户与角色关联  本系统一个账户对应一个角色
	private Role role;
	public User() {
	}
	public User(String account, String password, String nickname, Role role) {
		
		this.account = account;
		this.password = password;
		this.nickname = nickname;
		this.role = role;
	}
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public boolean hasRight(Right1 r) {
		Set<Right1> rights = role.getRights();
		if(rights.contains(r)){
			System.out.println("有权限");
			return true;
		}else{
			return false;
		}
	}
	
	
}
