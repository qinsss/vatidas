package com.vatidas.entity;

import java.util.HashSet;
import java.util.Set;

public class Role {

	private Integer id;
	private String name;
	private String roleDesc;
	private Integer rightValue = 0;//表明权限值，供给系统管理员使用，其他角色一律为0
	
	private Set<Right1> rights = new HashSet<Right1>();
	//一对多账户
	private Set<User> user = new HashSet<User>();

	
	
	public Role() {
		
	}
	
	public Integer getRightValue() {
		return rightValue;
	}

	public void setRightValue(Integer rightValue) {
		this.rightValue = rightValue;
	}

	public Role(String name, String roleDesc) {
		this.name = name;
		this.roleDesc = roleDesc;
	}

	
	public Set<Right1> getRights() {
		return rights;
	}
	
	public void setRights(Set<Right1> rights) {
		this.rights = rights;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public Set<User> getUser() {
		return user;
	}
	public void setUser(Set<User> user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", roleDesc=" + roleDesc + ", rightValue=" + rightValue
				+ ", rights=" + rights + ", user=" + user + "]";
	}
	
}
