package com.vatidas.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class CheckLoginInterceptor implements Interceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1668841224649816753L;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Object object = ActionContext.getContext().getSession().get("user");
		System.out.println("CheckLoginInterceptor");
		if(object != null){
			return invocation.invoke();
		}else{
			return "goLogin";
		}
	}

}
