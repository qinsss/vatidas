package com.vatidas.entity;

import java.util.List;

public class InvoicePage {
	
	private int allCount;
	private int totalPage;
	private int currentPage;
	private List<Invoice> invoiceList;
	
	public InvoicePage() {
	}
	
	
	public InvoicePage(int allCount, int totalPage, int currentPage, List<Invoice> invoiceList) {
		super();
		this.allCount = allCount;
		this.totalPage = totalPage;
		this.currentPage = currentPage;
		this.invoiceList = invoiceList;
	}


	public List<Invoice> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<Invoice> invoiceList) {
		this.invoiceList = invoiceList;
	}
	public int getAllCount() {
		return allCount;
	}
	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	/*
	 * 获取当前页中起始记录位置
	 * pageSize 页面大小
	 * currentPage 当前页
	 */
	public int getCurrentPageOffset(int pageSize, int currentPage){
		return pageSize * (currentPage - 1);
		 
	}
	
	/*
	 * 根据参数获得总页数大小
	 * pageSize 页面大小
	 * allCount 总记录数
	 */
	public int getTotalPage(int pageSize, int allCount){
		return allCount % pageSize == 0?allCount/pageSize:allCount/pageSize+1;
		
	}
	
	public int getCurrentPage(int page) {
		return page == 0?1:page;
	}
}
