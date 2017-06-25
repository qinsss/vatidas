package com.vatidas.service;

import java.util.Date;
import java.util.List;

import org.jfree.chart.JFreeChart;

import com.vatidas.entity.ImportInvoice;
import com.vatidas.entity.Invoice;
import com.vatidas.entity.InvoicePage;

/**
 * 发票服务接口
 * 定义发票进行发票操作所需要的方法
 * @author qinshou
 *
 */
public interface IInvoiceService extends IBaseService<Invoice> {

	/**
	 * 根据当前页和页面大小返回一个InvoicePage对象,包含了所有发票记录, 并进行分页
	 * @param pageSize
	 * @param page
	 * @return
	 */
	public InvoicePage findInvoicePage(int pageSize, int page);
	
	/**
	 * 方法重载 进行条件查询并分页
	 * @param startTime
	 * @param endTime
	 * @param invoiceClasses
	 * @param i
	 * @param page
	 * @return
	 */
	public InvoicePage findInvoicePage(Date startTime, Date endTime, String invoiceClasses, int i, int page);
	
	/**
	 * 添加新的发票
	 * @param invoice
	 */
	public void addInvoice(Invoice invoice);
	
	/**
	 * 根据发票编号来判断是否已存在
	 * @param code
	 * @return
	 */
	public String findInvoiceByCode(String code);

	/**
	 * 根据发票编号进行删除
	 * @param code
	 */
	public void deleteInvoiceByCode(String code);
	
	/**
	 * 根据analyzeItem，startYm，startYm统计查询到待分析数据集，制作成图表返回
	 * @param flag 
	 * @param analyzeItem
	 * @param analyzeView 
	 * @param startYm
	 * @param startYm
	 * @param url 
	 * @return 
	 */
	public JFreeChart getAnalyzeDataItem(int flag, String analyzeItem, String analyzeView, Date startYm, Date endYm, String url);

	/**
	 * 保存到数据库
	 * @param importInvoiceList
	 */
	public void importInvoiceData(List<ImportInvoice> importInvoiceList);

	/**
	 * 清除之前导入的数据
	 */
	public void clearImportData();


}
