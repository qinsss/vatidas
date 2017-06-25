package com.vatidas.entity;

public class Right1 {
	private Integer id;
	private String rightName = "未命名";
	private String rightUrl;
	private int rightCode;
	
	
	public int getRightCode() {
		return rightCode;
	}
	public void setRightCode(int rightCode) {
		this.rightCode = rightCode;
	}
	public Integer getId() {
		return id;
	}
	public String getRightName() {
		return rightName;
	}
	public String getRightUrl() {
		return rightUrl;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setRightName(String rightName) {
		this.rightName = rightName;
	}
	public void setRightUrl(String rightUrl) {
		this.rightUrl = rightUrl;
	}
	@Override
	public String toString() {
		return "Right1 [id=" + id + ", rightName=" + rightName + ", rightUrl=" + rightUrl + ", rightCode=" + rightCode
				+ "]";
	}
	@Override
	public boolean equals(Object o) {
		if(o == this){
			return true;
		}
		if(!(o instanceof Right1)){
			return false;
		}
		Right1 r = (Right1) o;
		if((id == r.getId()) && (rightName.equals(r.getRightName()))
				&& (rightCode == r.getRightCode())&&(rightUrl.equals(r.getRightUrl()))){
			return true;
		}
		return false;
	}
	
	
	
	
}
