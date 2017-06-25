package com.vatidas.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.vatidas.entity.InOutStatistic;

public class CommonUtils {
	//将指定格式字符串转换为日期
	public static Date DateTransform(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Date DateTransformTest(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	//将日期转换为字符串
	public static String DateTransform(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		return sdf.format(date);
	}
	//将日期转换为字符串
	public static String DateTranY_M(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM");
		return sdf.format(date);
	}
	
	
	
	//根据类型的值获得type在数据库中存储串名
	public static String getType(String type){
		if(type.startsWith("in")){
			return "进项";
		}else if(type.startsWith("out")){
			return "销项";
		}else{
			return null;
		}
	}
	//增值税额按销项税额-进项税额计算
	public static Map<Date, BigDecimal> getVat(Iterator<InOutStatistic> iterator) {
		InOutStatistic next;
		Date key;
		BigDecimal value;
		Map<Date,BigDecimal> vatMap = new HashMap<Date,BigDecimal>();
		while(iterator.hasNext()){
			next = iterator.next();
			key = next.getYearMonth();
			value = next.getMoney().subtract(iterator.next().getMoney()).abs();
			vatMap.put(key, value);
		}
		return vatMap;
	}
}














