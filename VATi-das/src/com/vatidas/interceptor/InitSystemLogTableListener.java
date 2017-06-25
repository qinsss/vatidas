package com.vatidas.interceptor;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.vatidas.service.ILogService;
import com.vatidas.utils.LogUtil;

/**
 * 日志表的初始化，当系统运行的是否，自动线=先创建本月的表和下一个月的表
 * @author qinshou
 *
 */
public class InitSystemLogTableListener implements ApplicationListener<ApplicationEvent> {

	private ILogService logService;
	
	public void setLogService(ILogService logService) {
		this.logService = logService;
	}

	@Override
	public void onApplicationEvent(ApplicationEvent arg0) {
		if(arg0 instanceof ContextRefreshedEvent){
			String sql;
			sql = "create table if not exists " + LogUtil.generateLogTableName(-1) + " like log1";
			logService.createTable(sql);
			sql = "create table if not exists " + LogUtil.generateLogTableName(0) + " like log1";
			logService.createTable(sql);
			sql = "create table if not exists " + LogUtil.generateLogTableName(1) + " like log1";
			logService.createTable(sql);
			
		}
	}

}
