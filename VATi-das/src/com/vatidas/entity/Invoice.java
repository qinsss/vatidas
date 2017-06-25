package com.vatidas.entity;

import java.math.BigDecimal;
import java.util.Date;


public class Invoice {

	private Integer id;
	private String code;
	private Date date;
	private String unit;
	private String type;
	private String commodityName;
	private Integer commodityNum;
	private String commodityUnit;
	private BigDecimal commodityPrePrice;
	private BigDecimal commodityMoney;
	private BigDecimal invoiceTaxMoney;
	private BigDecimal totalMoney;
	public Invoice() {
	}
	public Invoice(String code, Date date, String unit, String type, String commodityName,
			Integer commodityNum, String commodityUnit, BigDecimal commodityPrePrice, BigDecimal commodityMoney,
			BigDecimal invoiceTaxMoney, BigDecimal totalMoney) {
		this.code = code;
		this.date = date;
		this.unit = unit;
		this.type = type;
		this.commodityName = commodityName;
		this.commodityNum = commodityNum;
		this.commodityUnit = commodityUnit;
		this.commodityPrePrice = commodityPrePrice;
		this.commodityMoney = commodityMoney;
		this.invoiceTaxMoney = invoiceTaxMoney;
		this.totalMoney = totalMoney;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCommodityName() {
		return commodityName;
	}
	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}
	public Integer getCommodityNum() {
		return commodityNum;
	}
	public void setCommodityNum(Integer commodityNum) {
		this.commodityNum = commodityNum;
	}
	public String getCommodityUnit() {
		return commodityUnit;
	}
	public void setCommodityUnit(String commodityUnit) {
		this.commodityUnit = commodityUnit;
	}
	public BigDecimal getCommodityPrePrice() {
		return commodityPrePrice;
	}
	public void setCommodityPrePrice(BigDecimal commodityPrePrice) {
		this.commodityPrePrice = commodityPrePrice;
	}
	public BigDecimal getCommodityMoney() {
		return commodityMoney;
	}
	public void setCommodityMoney(BigDecimal commodityMoney) {
		this.commodityMoney = commodityMoney;
	}
	public BigDecimal getInvoiceTaxMoney() {
		return invoiceTaxMoney;
	}
	public void setInvoiceTaxMoney(BigDecimal invoiceTaxMoney) {
		this.invoiceTaxMoney = invoiceTaxMoney;
	}
	public BigDecimal getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}
	
}