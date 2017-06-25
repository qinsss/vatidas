package com.vatidas.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class JspFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	/*
	 * 当用户直接以地址访问jsp页面，判断用户是否有登录，没有则不允许访问
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		//获取session
		HttpSession session = req.getSession();
		Object user = session.getAttribute("user");
		System.out.println(user);
		String uri = req.getRequestURI();
		System.out.println("jspFilter"+uri);
		//如果是访问的登录页面应直接跳转 因为第一次访问没有登录直接重定向将产生错误
		if((req.getContextPath()+"/login.jsp").equals(uri)){
			req.getRequestDispatcher(req.getContextPath()+"/login.jsp");
			
		}else if(user == null){
			resp.sendRedirect(req.getContextPath()+"/login.jsp");
			return;
		}
		chain.doFilter(request, response);
	}

	//获取配置参数
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
