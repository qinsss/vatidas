package com.vatidas.action;

import java.util.Date;
import java.util.List;

import com.vatidas.entity.Log1;
import com.vatidas.service.ILogService;

public class LogAction {

	private ILogService logService;
	private List<Log1> logList;
	private Date startDate;
	private Date endDate;
	
	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<Log1> getLogList() {
		return logList;
	}

	public void setLogList(List<Log1> logList) {
		this.logList = logList;
	}

	public void setLogService(ILogService logService){
		this.logService = logService;
	}
	
	public String findLogs(){
		logList = logService.findAllLogs();
		return "toFindLog";
	}
	
	public String findLogByDate(){
		logList = logService.findLogByDate(startDate,endDate);
		return "toFindLog";
	}
	
}
