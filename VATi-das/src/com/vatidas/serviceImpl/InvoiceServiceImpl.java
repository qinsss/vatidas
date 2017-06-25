package com.vatidas.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import com.vatidas.dao.IBaseDao;
import com.vatidas.entity.ImportInvoice;
import com.vatidas.entity.InOutStatistic;
import com.vatidas.entity.Invoice;
import com.vatidas.entity.InvoicePage;
import com.vatidas.service.IInvoiceService;
import com.vatidas.utils.CommonUtils;
import com.vatidas.utils.CreateChartUtil;

public class InvoiceServiceImpl extends BaseServiceImpl<Invoice> implements IInvoiceService {

	/*
	 * 注入发票dao
	 */
	private IBaseDao<Invoice> invoiceDao;
	private IBaseDao<InOutStatistic> inOutDao;
	private IBaseDao<ImportInvoice> importInvoiceDao;

	public void setInvoiceDao(IBaseDao<Invoice> invoiceDao) {
		this.invoiceDao = invoiceDao;
	}
	public void setInOutDao(IBaseDao<InOutStatistic> inOutDao) {
		this.inOutDao = inOutDao;
	}
	public void setImportInvoiceDao(IBaseDao<ImportInvoice> importInvoiceDao) {
		this.importInvoiceDao = importInvoiceDao;
	}

	/*
	 * 查询所有发票分页实现
	 */
	@Override
	public InvoicePage findInvoicePage(int pageSize, int page) {
		InvoicePage invoicepage = new InvoicePage();
		int allCount = invoiceDao.findEntityByHql("from Invoice").size();
		int curPage = invoicepage.getCurrentPage(page);
		int offset = invoicepage.getCurrentPageOffset(pageSize, curPage);
		String hql = "from Invoice";
		List<Invoice> list = invoiceDao.findEntityByPage(hql, offset, pageSize);
		return this.getInvoicePage(pageSize, page, curPage, allCount, list);
	}
	/*
	 * 提取封装InvoicePage
	 */
	public InvoicePage getInvoicePage(int pageSize, int page, int curPage, int allCount, List<Invoice> list){
		InvoicePage invoicepage = new InvoicePage();
		int totalPage = invoicepage.getTotalPage(pageSize, allCount);
		invoicepage.setAllCount(allCount);
		invoicepage.setTotalPage(totalPage);
		invoicepage.setCurrentPage(curPage);
		invoicepage.setInvoiceList(list);
		return invoicepage;
	}
	
	/* 
	 * 按条件查询发票分页实现
	 */
	@Override
	public InvoicePage findInvoicePage(Date startTime, Date endTime, String invoiceClasses, int pageSize, int page) {
		InvoicePage invoicepage = new InvoicePage();
		int curPage = invoicepage.getCurrentPage(page);
		int offset = invoicepage.getCurrentPageOffset(pageSize, curPage);
		String hql = null;
		int allCount = 0;
		List<Invoice> list = null;
		//注意这里的allCount与list的size并不相等
		if("all".equals(invoiceClasses)){
			hql = "from Invoice i where i.date between ? and ?";
			allCount = invoiceDao.findEntityByHql(hql, startTime, endTime).size();
			list = invoiceDao.findEntityByPage(hql, offset, pageSize, startTime, endTime);

		}
		else{
			hql = "from Invoice i where (i.date between ? and ?) and i.type = ?";
			allCount = invoiceDao.findEntityByHql(hql, startTime, endTime, CommonUtils.getType(invoiceClasses)).size();
			list = invoiceDao.findEntityByPage(hql, offset, pageSize, startTime, endTime, CommonUtils.getType(invoiceClasses));
		}
		System.out.println(allCount+"all");
		return this.getInvoicePage(pageSize, page, curPage, allCount, list);
	}

	@Override
	public void addInvoice(Invoice invoice) {
		invoiceDao.saveEntity(invoice);
	}

	@Override
	public String findInvoiceByCode(String code) {
		Invoice invoice = null;
		invoice = (Invoice) invoiceDao.findEntityUnique("from Invoice i where i.code = ?", code);
		return (invoice == null)?"false":"true";
	}
	
	@Override
	public void deleteInvoiceByCode(String code) {
		String hql = "delete Invoice i where i.code = ?";
		invoiceDao.batchEntityByHql(hql, code);
	}

	/**
	 * 获取jfreechart图表并将其保存在服务器上
	 */
	@Override
	public JFreeChart getAnalyzeDataItem(int flag,String analyzeItem, String analyzeView, Date startYm, Date endYm, String url){
		String hql;
		List<InOutStatistic> inOutList = null;
		
		//直接查询的是系统添加的发票数据
		if(flag == 1){
			if(!(analyzeItem.startsWith("inOut")||analyzeItem.contains("vat"))){
				hql = "from InOutStatistic i where (i.yearMonth between ? and ?) and i.type=?";
				//根据hql查询出数据集
				inOutList = inOutDao.findEntityByHql(hql, startYm, endYm, CommonUtils.getType(analyzeItem));
			}else{
				hql = "from InOutStatistic i where i.yearMonth between ? and ? order by i.yearMonth";
				inOutList = inOutDao.findEntityByHql(hql, startYm, endYm);
			}
		}
		//查询的是导入的数据
		else if(flag == 2){
			inOutList = getInOutListByImportInvoice(analyzeItem, startYm, endYm);
		}
		
		System.out.println(analyzeItem+"--"+inOutList.size()+"--"+analyzeView+"--"+startYm+"--"+endYm);
		//创建分析图表
		JFreeChart chart = CreateChartUtil.createAnalyzeView(analyzeItem,inOutList, analyzeView, startYm, endYm);
		File file = null;
		try {
			file = new File(url+"chart/chart.png");
			ChartUtilities.saveChartAsPNG(file, chart, 1000, 450);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return chart;
	}
	
	/*
	 *将excel文件解析得数据保存到数据库的实现 
	 *
	 */
	@Override
	public void importInvoiceData(List<ImportInvoice> importInvoiceList) {
		for (ImportInvoice importInvoice : importInvoiceList) {
			importInvoiceDao.saveEntity(importInvoice);
		}
	}

	/**
	 * 查询的是导入的数据，由于数据格式有一部分不一样，
	 * 需要将查询出来的字段封装 ，转换 成InOutStatistic对象，就可以使用同一个图表创建方法
	 * @param endYm 
	 * @param startYm 
	 * @param analyzeItem 
	 * @return 
	 */
	public List<InOutStatistic> getInOutListByImportInvoice(String analyzeItem, Date startYm, Date endYm){
		String hql;
		List<InOutStatistic> inOutList = new ArrayList<InOutStatistic>();
		List<ImportInvoice> list = new ArrayList<ImportInvoice>();
		if(!(analyzeItem.startsWith("inOut")||analyzeItem.contains("vat"))){
			hql = "select new ImportInvoice(i.yearMonth,i.type,sum(i.money)) from ImportInvoice i "
					+ "where (i.yearMonth between ? and ?) and i.type=? group by i.yearMonth,i.type order by i.yearMonth";
			//根据hql查询出数据集
			list = importInvoiceDao.findEntityByHql(hql, startYm, endYm, CommonUtils.getType(analyzeItem));
		}else{
			hql = "select new ImportInvoice(i.yearMonth,i.type,sum(i.money)) from ImportInvoice i "
					+ "where i.yearMonth between ? and ? group by i.yearMonth,i.type order by i.yearMonth";
			list = importInvoiceDao.findEntityByHql(hql, startYm, endYm);
		}
		Iterator<ImportInvoice> it = list.iterator();
		while(it.hasNext()){
			ImportInvoice importInvoice = it.next();
			InOutStatistic inOutStatistic = new InOutStatistic();
			inOutStatistic.setYearMonth(importInvoice.getYearMonth());
			inOutStatistic.setType(importInvoice.getType());
			inOutStatistic.setMoney(importInvoice.getMoney());
			inOutList.add(inOutStatistic);
		}
		System.out.println(inOutList.size());
		return inOutList;
	}
	
	/**
	 * 清除之前的导入数据
	 */
	public void clearImportData(){
		String hql = "delete from ImportInvoice";
		importInvoiceDao.batchEntityByHql(hql);
		System.out.println("剩余数据量"+importInvoiceDao.findEntityByHql("from ImportInvoice").size());
	}
	
}
