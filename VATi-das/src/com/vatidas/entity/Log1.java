package com.vatidas.entity;

import java.util.Date;

public class Log1 {
	private String id;
	private String operator;
	private Date operateTime;
	private String operateName;
	private String operateResult;
	private String operateParams;
	public String getOperateParams() {
		return operateParams;
	}
	public void setOperateParams(String operateParams) {
		this.operateParams = operateParams;
	}
	public String getOperator() {
		return operator;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public String getOperateName() {
		return operateName;
	}
	public String getOperateResult() {
		return operateResult;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
	}
	
	@Override
	public String toString() {
		return "Log1 [id=" + id + ", operator=" + operator + ", operateTime=" + operateTime + ", operateName="
				+ operateName + ", operateResult=" + operateResult + "]";
	}
}
