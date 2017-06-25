package com.vatidas.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.ServletContextAware;

import com.vatidas.entity.Right1;
import com.vatidas.service.IRightService;

public class InitAllRightListener implements ApplicationListener<ApplicationEvent>, ServletContextAware {

	private IRightService rightService;
	private ServletContext sc;
	
	public void setRightService(IRightService rightService) {
		this.rightService = rightService;
	}
	/**
	 * 将所有权限查询出来放进application中
	 */
	@Override
	public void onApplicationEvent(ApplicationEvent arg0) {
		if(arg0 instanceof ContextRefreshedEvent){
			List<Right1> list = rightService.findAllRight();
			Map<String, Right1> rightMap = new HashMap<String, Right1>();
			for (Right1 r : list) {
				rightMap.put(r.getRightUrl(), r);
			}
			if(sc != null){
				sc.setAttribute("rightsMap", rightMap);
			}
		}
		System.out.println("权限初始化完成");
	}
	@Override
	public void setServletContext(ServletContext arg0) {
		this.sc = arg0;
	}


}
