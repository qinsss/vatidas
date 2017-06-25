package com.vatidas.entity;

/**
 * 映射数据库inOutStatisticView视图  统计的是按月划分的发票金额和税额
 */
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class InOutStatistic implements Serializable {
	private static final long serialVersionUID = 381056342032876358L;
	private Date yearMonth;
	private int invoNum;
	private String type;
	private BigDecimal taxMoney;
	private BigDecimal money;
	public InOutStatistic() {
	}
	public InOutStatistic(Date yearMonth, int invoNum, String type, BigDecimal taxMoney, BigDecimal money) {
		super();
		this.yearMonth = yearMonth;
		this.invoNum = invoNum;
		this.type = type;
		this.taxMoney = taxMoney;
		this.money = money;
	}
	public Date getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(Date yearMonth) {
		this.yearMonth = yearMonth;
	}
	public int getInvoNum() {
		return invoNum;
	}
	public void setInvoNum(int invoNum) {
		this.invoNum = invoNum;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getTaxMoney() {
		return taxMoney;
	}
	public void setTaxMoney(BigDecimal taxMoney) {
		this.taxMoney = taxMoney;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	@Override
	public String toString() {
		return "InOutStatistic [yearMonth=" + yearMonth + ", invoNum=" + invoNum + ", type=" + type + ", taxMoney="
				+ taxMoney + ", money=" + money + "]";
	}
	
}
