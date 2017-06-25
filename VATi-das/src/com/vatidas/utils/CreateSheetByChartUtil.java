package com.vatidas.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

public class CreateSheetByChartUtil {

	/**
	 * 根据图表获得excel数据表
	 * @param chart
	 * @param fileName
	 * @return
	 */
	public static InputStream getSheetInputStream(JFreeChart chart, String fileName) {
		InputStream is = null;
		CategoryDataset dataset = chart.getCategoryPlot().getDataset();
		//创建workBook
		HSSFWorkbook wb = new HSSFWorkbook();
		
		//创建sheet
		HSSFSheet sheet = wb.createSheet(fileName);
		HSSFRow row = sheet.createRow(0);////创建行  第一行
		HSSFCell cell = row.createCell(0);
		cell.setCellValue(fileName);//存放标题
		CellRangeAddress region = new CellRangeAddress(0, 0, 0, dataset.getColumnCount());//合并单元格第1行的1-图表的总列数列
		sheet.addMergedRegion(region);
		row.setHeightInPoints(35f);//设置标题行的高度
		sheet.setColumnWidth(0, 40*256);
		
		HSSFCellStyle styleRow1 = wb.createCellStyle();//为标题行添加特有样式
		HSSFFont titleFont = wb.createFont();
		titleFont.setBold(true);//设置粗体
		titleFont.setFontHeightInPoints((short)20);//设置字体大小
		styleRow1.setFont(titleFont);
		styleRow1.setAlignment(HorizontalAlignment.CENTER);//居中
		cell.setCellStyle(styleRow1);
		
		//根据图表的columKeys创建行数，如可能同时有 进项数据和销项数据情况，另需多加一行每列的标题行
		for(int i = 1; i <= dataset.getRowCount()+1; i++){
			row = sheet.createRow(i);
			if(i == 1){	
				row.setHeightInPoints(30f);//设置第一行高
				cell = row.createCell(0);
				cell.setCellValue("");//除标题外的第一行的第一列为空Tab
				cell.setCellStyle(baseCellStyle(wb));
				//再根据图表rowKeys创建列数，如有12月就应创建12列
				for(int j = 1; j <= dataset.getColumnCount(); j++)	{
					cell = row.createCell(j);
					cell.setCellValue((String)dataset.getColumnKey(j-1));//其他列填上rowKey值
					sheet.setColumnWidth(j, 20*256);
					cell.setCellStyle(baseCellStyle(wb));
				}
			}else{			
				//其他行填上数据
				cell = row.createCell(0);
				System.out.println(dataset.getRowKey(i-2));
				cell.setCellValue((String)dataset.getRowKey(i-2)+"/元");//填上columnKey
				sheet.setColumnWidth(0, 20*256);;
				cell.setCellStyle(baseCellStyle(wb));
				for(int j = 1; j <= dataset.getColumnCount(); j++){
					cell = row.createCell(j);
					cell.setCellValue(Double.parseDouble((dataset.getValue(i-2, j-1)).toString()));
					sheet.setColumnWidth(j, 20*256);
					cell.setCellStyle(baseCellStyle(wb));
				}
			}
		}
		
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			wb.write(baos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//将它转为输入流
		byte[] bytes = baos.toByteArray();
		is = new ByteArrayInputStream(bytes);
		return is;
	}
	
	
	/**
	 *设置基本的单元格样式 
	 */
	private static HSSFCellStyle baseCellStyle(HSSFWorkbook wb){
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);//水平居中
		style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");//设置字体
		font.setFontHeightInPoints((short)15);//设置字号
		style.setFont(font);
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#.00"));
		return style;
	}
	
	
	/**
	 * 获取格式化的数据文件名
	 * @param text
	 * @param analyzeView
	 * @return
	 */
	public static String getFileName(String text, String analyzeView) {
		System.out.println("12");
		int index;   
		String fileName;
		if("pie".equals(analyzeView)){
			index = text.lastIndexOf("饼");
			fileName = text.substring(0,index)+"数据表";
		}else if("bar".equals(analyzeView)){
			System.out.println("bar");
			index = text.lastIndexOf("柱");
			fileName = text.substring(0,index)+"数据表";
		}else if("line".equals(analyzeView)){
			index = text.lastIndexOf("折");
			fileName = text.substring(0,index)+"数据表";
		}else{
			fileName = null;
		}
		return fileName;
	}

}
