package com.vatidas.other;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.conversion.TypeConversionException;
import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;

public class DateConversion extends DefaultTypeConverter {

	public Object convertValue(Object value, @SuppressWarnings("rawtypes") Class toType) {
		try {
			if (toType == Date.class) {// 由页面到服务器端是String类型转换为Date类型
				String source = ((String[]) value)[0];
				SimpleDateFormat sdf= null;
				if(Pattern.matches("^\\d{4}-\\d{2}-\\d{2}$", source)){
					sdf = new SimpleDateFormat("yyyy-MM-dd");
				}else if(Pattern.matches("^\\d{4}-\\d{2}$", source)){
					sdf = new SimpleDateFormat("yyyy-MM");
				}else throw new TypeConversionException();
				//为了转换为相同的格式，将它存放进sessiong中，转出的时候再获取这个sdf
				ActionContext.getContext().getSession().put("sdf", sdf);
				return sdf.parse(source);
			} else if (toType == String.class) {// 由服务器端是Date类型转换成String类型
				SimpleDateFormat sdf = (SimpleDateFormat) ActionContext.getContext().getSession().get("sdf");
				return sdf.format((Date) value);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return super.convertValue(value, toType);
	}

	
}
