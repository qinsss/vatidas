package com.vatidas.interceptor;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.vatidas.service.IRightService;

public class CatchUrlInterceptor implements Interceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6992339852141831138L;

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
		//获取Url
		ActionProxy proxy = invocation.getProxy();
		String namespace = proxy.getNamespace();
		String actionName = proxy.getActionName();
		if(namespace == null || "".equals(namespace.trim())|| "/".equals(namespace)){
			namespace = "";
		}
		String url = namespace + "/" +actionName;
		//将url编码存进数据库
		ServletContext sc = ServletActionContext.getServletContext();
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sc);
		IRightService rightService = (IRightService) ac.getBean("rightService");
		System.out.println(url);
		rightService.appendRightURL(url);
		return invocation.invoke();
	}

}
