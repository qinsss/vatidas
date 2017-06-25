package com.vatidas.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.vatidas.entity.Right1;
import com.vatidas.entity.Role;
import com.vatidas.service.IRightService;

public class RightAction {

	private IRightService rightService;
	private List<Right1> rightList;
	private Role role;
	private Right1 right;
	private String rightauthor;

	public String getRightauthor() {
		return rightauthor;
	}

	public void setRightauthor(String rightauthor) {
		this.rightauthor = rightauthor;
	}

	public Right1 getRight() {
		return right;
	}

	public void setRight(Right1 right) {
		this.right = right;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public IRightService getRightService() {
		return rightService;
	}

	public void setRightService(IRightService rightService) {
		this.rightService = rightService;
	}

	public List<Right1> getRightList() {
		return rightList;
	}
	
	public void setRightList(List<Right1> rightList) {
		this.rightList = rightList;
	}

	public String findRight(){
		rightList = rightService.findAllRight();
		return "rightManager";
	}
	
	public String findRoleRight(){
		String roleName = ServletActionContext.getRequest().getParameter("roleName");
		role = rightService.findRoleRight(roleName);
		return "rightManager";
	}
	
	public String authorize(){
		System.out.println(right+"--"+rightauthor);
		return "redriectRightManager";
	}
}
