package com.vatidas.interceptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.vatidas.utils.RightUtil;

public class RightFilterInterceptor implements Interceptor {

	private static final long serialVersionUID = 2467392569268822079L;

	@Override
	public void destroy() {

	}

	@Override
	public void init() {

	}

	/**
	 * 做权限的拦截
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionProxy proxy = invocation.getProxy();
		String namespace = proxy.getNamespace();
		String actionName = proxy.getActionName();
		//去验证是否有权限
		if(RightUtil.validateRight(namespace, actionName, ServletActionContext.getRequest())){
			return invocation.invoke();
		}
		return "onRightError";
	}
}
