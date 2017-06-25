package com.vatidas.service;

import java.util.Date;
import java.util.List;

import com.vatidas.entity.Log1;

public interface ILogService extends IBaseService<Log1> {

	public List<Log1> findAllLogs();

	public void createTable(String sql);

	public void saveLogEntity(Log1 log);

	public List<Log1> findLogByDate(Date startDate, Date endDate);

}
