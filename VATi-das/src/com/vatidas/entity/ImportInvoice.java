package com.vatidas.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 定义导入的发票实体类
 * 由于导入的发票数据只含有金额和月份的信息，故重新为其定义一个实体类
 * @author qinshou
 *
 */
public class ImportInvoice {
	
	private Integer id;
	private Date yearMonth;
	private String type;
	private String commodityName;
	private BigDecimal money;
	
	public ImportInvoice() {
		
	}

	public ImportInvoice(Date yearMonth, String type, String commodityName, BigDecimal money) {
		super();
		this.yearMonth = yearMonth;
		this.type = type;
		this.commodityName = commodityName;
		this.money = money;
	}
	/*
	 * 定义三个参数的构造器，供hql使用聚集函数查询时使用
	 */
	public ImportInvoice(Date yearMonth, String type,BigDecimal money){
		this.yearMonth = yearMonth;
		this.type = type;
		this.money = money;
	}

	public Integer getId() {
		return id;
	}

	public Date getYearMonth() {
		return yearMonth;
	}

	public String getType() {
		return type;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setYearMonth(Date yearMonth) {
		this.yearMonth = yearMonth;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	@Override
	public String toString() {
		return "ImorptInvoice [yearMonth=" + yearMonth + ", type=" + type + ", commodityName=" + commodityName
				+ ", money=" + money + "]";
	}
	
	
	
	
}
