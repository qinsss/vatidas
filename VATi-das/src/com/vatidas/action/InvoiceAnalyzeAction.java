package com.vatidas.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.jfree.chart.JFreeChart;

import com.opensymphony.xwork2.ActionContext;
import com.vatidas.service.IInvoiceService;
import com.vatidas.utils.CreateSheetByChartUtil;

/**
 * 进行分析数据的查找、数据的导出、分析统计图的生成获取action
 * @author qins
 *
 */
public class InvoiceAnalyzeAction {
	
	private IInvoiceService invoiceService;
	private String analyzeItem;//分析项
	private String analyzeView;//视图形状
	private Date startYm;//开始年月
	private Date endYm;//截止年月
	
	//获取到sessionMap
	Map<String, Object> sessMap = ActionContext.getContext().getSession();
	@SuppressWarnings("unchecked")
	Map<String, Object> reqMap = (Map<String, Object>) ActionContext.getContext().get("request");
	
	private JFreeChart chart;//接收图表 用以导出数据集
	
	private String fileName;//下载的分析图名或导出数据的文件名
	private InputStream inputStreamChart; //下载图表的输入流
	private InputStream inputStreamDataSheet;//导出数据的输入流

	
	public InputStream getInputStreamDataSheet() {
		JFreeChart chart2 = (JFreeChart) sessMap.get("chart");
		this.fileName = CreateSheetByChartUtil.getFileName(chart2.getTitle().getText(),(String)sessMap.get("analyzeView"));
		inputStreamDataSheet = CreateSheetByChartUtil.getSheetInputStream(chart2,fileName);
		return inputStreamDataSheet;
	}

	public void setInputStreamDataSheet(InputStream inputStreamDataSheet) {
		this.inputStreamDataSheet = inputStreamDataSheet;
	}

	public String getFileName() {
		try {
			return new String(fileName.getBytes(),"ISO-8895-1");//组装为ISO-8895-1的编码格式
		} catch (UnsupportedEncodingException e) {
			return this.fileName;
		}
	}

	public InputStream getInputStreamChart() {
		this.fileName = "chart.png";
		inputStreamChart =  ServletActionContext.getServletContext().getResourceAsStream("chart/chart.png");
		return inputStreamChart;
	}

	public void setFileName(String fileName) {
		try {
			//将utf-8格式打散变为ISO-8859-1，解决中文乱码
			this.fileName = new String(fileName.getBytes("ISO8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public void setInputStreamChart(InputStream inputStreamChart) {
		this.inputStreamChart = inputStreamChart;
	}

	public Date getStartYm() {
		return startYm;
	}

	public void setStartYm(Date startYm) {
		this.startYm = startYm;
	}

	public Date getEndYm() {
		return endYm;
	}

	public void setEndYm(Date endYm) {
		this.endYm = endYm;
	}


	
	public IInvoiceService getInvoiceService() {
		return invoiceService;
	}
	
	public void setInvoiceService(IInvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}
	
	public String getAnalyzeItem() {
		return analyzeItem;
	}
	public void setAnalyzeItem(String analyzeItem) {
		this.analyzeItem = analyzeItem;
	}
	public String getAnalyzeView() {
		return analyzeView;
	}
	public void setAnalyzeView(String analyzeView) {
		this.analyzeView = analyzeView;
	}

	
	public String findAnalyzeView(){
		int flag = 1; //标记查询系统发票数据
		String url = ServletActionContext.getServletContext().getRealPath("/");
		//根据分析项和时间去查得数据制作统计分析图表返回
		chart = invoiceService.getAnalyzeDataItem(flag,analyzeItem,analyzeView,startYm,endYm,url);
		reqMap.put("submitFlag", ServletActionContext.getRequest().getParameter("submitFlag"));
		sessMap.put("analyzeView", analyzeView);
		sessMap.put("chart", chart);
		reqMap.put("img", "1");
		return "inOutAnalyze";
	}
	
	public String getDownloadChart(){
		return "getChart";
	}
	
	public String getAnalyzeData(){
		return "dataSheet";
	}
}
