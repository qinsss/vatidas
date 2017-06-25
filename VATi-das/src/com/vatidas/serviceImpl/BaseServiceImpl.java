package com.vatidas.serviceImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.vatidas.service.IBaseService;

public class BaseServiceImpl<T> implements IBaseService<T> {

	@SuppressWarnings("unused")
	private Class<T> c;
	@SuppressWarnings("unchecked")
	public BaseServiceImpl() {
		Type t = this.getClass().getGenericSuperclass();
		if(t instanceof ParameterizedType){
			ParameterizedType type = (ParameterizedType)t;
			c = (Class<T>)type.getActualTypeArguments()[0];
		}
	}
}
