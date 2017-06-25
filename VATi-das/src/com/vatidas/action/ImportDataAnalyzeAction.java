package com.vatidas.action;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.vatidas.entity.ImportInvoice;
import com.vatidas.service.IInvoiceService;
import com.vatidas.utils.CommonUtils;
import com.vatidas.utils.ParseExcelDataUtil;

public class ImportDataAnalyzeAction {

	private IInvoiceService invoiceService;
	
	private File excelFile;//文件
	private String excelFileFileName;//文件名
	private List<ImportInvoice> importInvoiceList; //将获取的数据封装成ImportInvoice
	
	private String analyzeItem;//分析项
	private String analyzeView;//视图形状
	private Date startYm;//开始年月
	private Date endYm;//截止年月
	
	@SuppressWarnings("unchecked")
	Map<String,String> reqMap = (Map<String, String>) ActionContext.getContext().get("request");
	
	
	public void setInvoiceService(IInvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	public File getExcelFile() {
		return excelFile;
	}

	public String getExcelFileFileName() {
		return excelFileFileName;
	}

	public String getAnalyzeItem() {
		return analyzeItem;
	}

	public String getAnalyzeView() {
		return analyzeView;
	}

	public Date getStartYm() {
		return startYm;
	}

	public Date getEndYm() {
		return endYm;
	}

	
	public List<ImportInvoice> getImportInvoiceList() {
		return importInvoiceList;
	}

	public void setImportInvoiceList(List<ImportInvoice> importInvoiceList) {
		this.importInvoiceList = importInvoiceList;
	}

	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

	public void setExcelFileFileName(String excelFileFileName) {
		this.excelFileFileName = excelFileFileName;
	}

	public void setAnalyzeItem(String analyzeItem) {
		this.analyzeItem = analyzeItem;
	}

	public void setAnalyzeView(String analyzeView) {
		this.analyzeView = analyzeView;
	}

	public void setStartYm(Date startYm) {
		this.startYm = startYm;
	}

	public void setEndYm(Date endYm) {
		this.endYm = endYm;
	}

	public String fileUpload(){
		if(excelFile == null){
			return "fileError";
		}else{
			//传入文件，文件名，解析获得其中数据
			importInvoiceList = ParseExcelDataUtil.getImportInvoiceList(excelFile,excelFileFileName);
			//调用发票服务 将数据存入到数据库中
			invoiceService.importInvoiceData(importInvoiceList);
			reqMap.put("importSuccess", "导入新数据成功！");
			//导入文件成功将文件中的第一个月与最后一个月返回到前台显示
			reqMap.put("fileFirstMonth",CommonUtils.DateTransform(importInvoiceList.get(0).getYearMonth()));
			reqMap.put("fileLastMonth",CommonUtils.DateTransform(importInvoiceList.get(importInvoiceList.size()-1).getYearMonth()));
			return "importDataAnalyze";
		}
	}
	
	public String dataAnalyze(){
		int flag = 2;
		String url = ServletActionContext.getServletContext().getRealPath("/");
		//根据分析项和时间去查得数据制作统计分析图表返回
		System.out.println("导入数据分析中");
		reqMap.put("img", "1");
		invoiceService.getAnalyzeDataItem(flag,analyzeItem,analyzeView,startYm,endYm,url);
		return "importDataAnalyze";
	}
	
	public String clearImportData(){
		invoiceService.clearImportData();
		reqMap.put("clearSuccess", "清除完成！");
		return "importDataAnalyze";
	}
}
