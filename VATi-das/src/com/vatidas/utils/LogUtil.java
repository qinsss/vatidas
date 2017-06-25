package com.vatidas.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.vatidas.dao.IBaseDao;

public class LogUtil {

	/**
	 * 按照传入的参数offset表示生成哪个月
	 * -往前，+往后，0表示当月
	 * @param offset
	 * @return
	 */
	public static String generateLogTableName(int offset) {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH)+1+offset;
		if(month > 12){
			year++;
			month-=12;
		}else if(month < 1){
			year--;
			month+=12;
		}
		return "log1_"+year+"_"+month;
	}

	/**
	 * 获取两个日期差距跨过的表名
	 * @param startDate
	 * @param endDate
	 * @param baseDao 
	 * @return 
	 */
	public static String getSql(Date startDate, Date endDate, IBaseDao<?> baseDao) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(startDate);
		c2.setTime(endDate);
		int dMonth = 0;
		int year1 = c1.get(Calendar.YEAR);
		int year2 = c2.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH)+1;
		int month2 = c2.get(Calendar.MONTH)+1;
		int dYear = year2-year1;
		if(year2 >= year1){
			dMonth = dYear*12 + month2 - month1;
		}else {
			dMonth = -dYear*12 + month1 - month2;
		}
		String tn = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ( select * from ");
		String sqlTemp =null;
		//String[] table = new String[dMonth+1];
		for(int i = 0; i < dMonth + 1; i++){
			if(month1 > 12){
				year1++;
				month1 = 1;
			}
			tn = "log1_"+year1+"_"+month1;
			sqlTemp = " select TABLE_NAME t from INFORMATION_SCHEMA.TABLES where "
					+ "TABLE_SCHEMA='vatidas' and TABLE_NAME="+"'"+tn+"'" ;
			List<?> l = baseDao.checkSQL(sqlTemp);
			if(l.size() == 0){	//该表名不存在
				;
			}else{
				sql.append(tn);
				sql.append("union select * from ");
			}
			
			month1++;
		}
		
		
		return null;
	}

		
		String sql = "select * from " + LogUtil.generateLogTableName(0) +"g where g.date between ? and ? " 
				  + " union select * from " + LogUtil.generateLogTableName(1);

}
