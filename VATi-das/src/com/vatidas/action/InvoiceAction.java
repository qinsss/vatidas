package com.vatidas.action;

import java.util.Date;

import org.apache.struts2.ServletActionContext;

import com.vatidas.entity.Invoice;
import com.vatidas.entity.InvoicePage;
import com.vatidas.service.IInvoiceService;
/**
 * InvoiceAction
 * 对invoice的处理操作，进行跳转控制
 * @author qinshou
 *
 */
public class InvoiceAction  {
	//注入发票服务
	private IInvoiceService invoiceService;
	
	//接收起始时间、截止时间、发票类型进行条件查询  
	//前台只能传yyyy-MM-dd类型日期，struts2自己能把这种格式转换为日期类型
	private Date startTime;
	private Date endTime;
	private String invoiceClasses;
	
	//页面跳转传过来的页 
	private int page;
	
	//接收整个invoice对象
	private Invoice invoice;
	
	//用以接收和传送发票分页对象，
	private InvoicePage invoicePage;
	
	private String exist;
	private String submitFlag;
	//发票编号
	private String code;
	

	public String getCode() {
		return code;
	}


	public String getExist() {
		return exist;
	}


	public void setExist(String exist) {
		this.exist = exist;
	}


	public void setCode(String code) {
		this.code = code;
	}
	
	public String getSubmitFlag() {
		return submitFlag;
	}

	public void setSubmitFlag(String submitFlag) {
		this.submitFlag = submitFlag;
	}
	
	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getInvoiceClasses() {
		return invoiceClasses;
	}

	public void setInvoiceClasses(String invoiceClasses) {
		this.invoiceClasses = invoiceClasses;
	}
	
	public InvoicePage getInvoicePage() {
		return invoicePage;
	}

	public void setInvoicePage(InvoicePage invoicePage) {
		this.invoicePage = invoicePage;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public IInvoiceService getInvoiceService() {
		return invoiceService;
	}

	public void setInvoiceService(IInvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	/*
	 * 发票列表页面  每次加载完毕自动提交参数进行查看
	 * 查看发票
	 */
	public String findInvoice(){
		//将条件查询的时间放进session中，使得返回页面的时候获取的时间一致
		invoicePage = invoiceService.findInvoicePage(startTime, endTime, invoiceClasses, 5, page);
		//将是否已经提交过的标志放进reqMap中
		return "invoiceList";
	}
	
	/*
	 * 添加新的发票 重定向到invoiceList.jsp
	 */
	public String addInvoice(){
		invoiceService.addInvoice(invoice);
		return "redirectInvoiceList";
	}
	
	public String toAddInvoice(){
		return "addInvoice";
	}
	/*
	 * 根据编号来查询是否存在 返回到addInvoice.jsp
	 */
	public String checkCode(){
		code = ServletActionContext.getRequest().getParameter("code");
		System.out.println(code);
		if(code != ""){
			exist = invoiceService.findInvoiceByCode(code);
		}
		System.out.println(exist);
		return "addInvoice";
	}
	/*
	 * 删除发票操作，传进一个发票编号code
	 */
	public String deleteInvoice(){
		code = ServletActionContext.getRequest().getParameter("code");
		invoiceService.deleteInvoiceByCode(code);
		//删除后把查看的条件传出
		return "invoiceList";
	}
}
