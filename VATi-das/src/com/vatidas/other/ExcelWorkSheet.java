package com.vatidas.other;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析出Excel文件的数据类
 * @author qinshou
 *
 */
public class ExcelWorkSheet<T> {

	private String sheetName;//excel表名
	private List<T> data = new ArrayList<T>();
	private List<String> columnName;
	
	public String getSheetName() {
		return sheetName;
	}
	public List<T> getData() {
		return data;
	}
	public List<String> getColumnName() {
		return columnName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public void setColumnName(List<String> columnName) {
		this.columnName = columnName;
	}
	
	
}
