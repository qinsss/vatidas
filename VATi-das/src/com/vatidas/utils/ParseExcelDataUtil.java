package com.vatidas.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.vatidas.entity.ImportInvoice;
import com.vatidas.other.ExcelWorkSheet;

public class ParseExcelDataUtil {
	
	
	public static List<ImportInvoice> getImportInvoiceList(File excelFile, String excelFileFileName) {
		
		List<ImportInvoice> importInvoiceList = new ArrayList<ImportInvoice>();
		
		//获取到sheet的ExcelWorkSheet集合
		try {
			List<ExcelWorkSheet<ImportInvoice>> excelWorkSheetList = getInvoiceData(excelFile, excelFileFileName);
			
			//对ExcelWorkSheet集合进行处理得到ImportInvoice的list
			for(int i = 0; i < excelWorkSheetList.size(); i++){
				//获取sheetName
				String sheetName = excelWorkSheetList.get(i).getSheetName();
				//获取columnName
				 List<String> columnNames = excelWorkSheetList.get(i).getColumnName();
				//获取Data
				 List<ImportInvoice> data = excelWorkSheetList.get(i).getData();
				 for(int j = 0; j < data.size(); j++){
					 data.get(j).setType(sheetName.contains("进项数据")?"进项":"销项");
					 //将相应的列名插进对应的数据里
					 data.get(j).setCommodityName(columnNames.get(j%(columnNames.size()))); 
					 importInvoiceList.add(data.get(j));
				 }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return importInvoiceList;
	}  
	
	
	
	
	
	
	public static List<ExcelWorkSheet<ImportInvoice>> getInvoiceData(File excelFile, String excelFileFileName) throws IOException {
		Workbook workbook = createWorkBook(new FileInputStream(excelFile),excelFileFileName);
		Sheet sheet;
		List<String> columnName;
		ImportInvoice importInvoice;
		
		//Java不允许创建含泛型的数组，所有使用list吧
		List<ExcelWorkSheet<ImportInvoice>> workSheetList = new ArrayList<ExcelWorkSheet<ImportInvoice>>();
		
		//对多个sheet进行读取
		for(int i = 0; i < workbook.getNumberOfSheets(); i++){
			sheet = workbook.getSheetAt(i);
			//第一行第一列为本sheet的title
			Row titleRow = sheet.getRow(0);
			String sheetName = titleRow.getCell(0).getStringCellValue();
			
			//对每一个sheet创建一个ExcelWorkSheet<ImportInvoice>对象
			ExcelWorkSheet<ImportInvoice> excelWorkSheet = new ExcelWorkSheet<ImportInvoice>();
			excelWorkSheet.setSheetName(sheetName);
			
			//第二行为列名
			Row columnNameRow = sheet.getRow(1);
			columnName = new ArrayList<String>();
			//忽略第二行的第一列
			for(int j = 1; j < columnNameRow.getLastCellNum(); j++){
				columnName.add(columnNameRow.getCell(j).getStringCellValue());
			}
			excelWorkSheet.setColumnName(columnName);
			
			//第三行开始为数据行
			Row dataRow;
			//获取行的值好像没有算上第一行。。。。所以要少一行
			for(int k = 2; k < sheet.getLastRowNum() + 1; k++){
				dataRow = sheet.getRow(k);
				for(int l = 1; l < dataRow.getLastCellNum(); l++){
					importInvoice = new ImportInvoice();
					//获取每一行的第一列为年月，由于表中只有月，我们添加一个年，组成年月
					importInvoice.setYearMonth(CommonUtils.DateTransform("2016-"+String.valueOf(dataRow.getCell(0).getNumericCellValue())));
					//其它列则是数据的读取
					importInvoice.setMoney(BigDecimal.valueOf(dataRow.getCell(l).getNumericCellValue()));
					//每次保存到数据list中的importInvoice对象只有YearMonth和Money有值，另外两个值需到excelWorkSheet对象列名和sheet名中去获取
					excelWorkSheet.getData().add(importInvoice);
				}
			}
			//将excelWorkSheet添加进workSheetList
			workSheetList.add(excelWorkSheet);
		}
		return workSheetList;
	}
	

	
	public static Workbook createWorkBook(InputStream is, String excelFileFileName) throws IOException{  
		Workbook workbook = null;
		if(excelFileFileName.toLowerCase().endsWith("xls")){  
			workbook =  new HSSFWorkbook(is);  
        }  
        if(excelFileFileName.toLowerCase().endsWith("xlsx")){  
        	workbook = new XSSFWorkbook(is);  
        }  
        return workbook;  
    }


	

}
