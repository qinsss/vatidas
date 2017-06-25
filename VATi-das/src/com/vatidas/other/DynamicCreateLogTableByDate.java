
package com.vatidas.other;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.vatidas.service.ILogService;
import com.vatidas.utils.LogUtil;

public class DynamicCreateLogTableByDate extends QuartzJobBean {

	private ILogService logService;
	
	public void setLogService(ILogService logService) {
		this.logService = logService;
	}

	//任务,按年月生成每个月的日志表
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		//生成下一个月的日志表
		String tableName = LogUtil.generateLogTableName(1);
		String sql = "create table if not exists "+ tableName+ " like log1";
		logService.createTable(sql);
	}

}
