package com.vatidas.serviceImpl;

import java.util.Date;
import java.util.List;

import org.hibernate.id.UUIDHexGenerator;

import com.vatidas.dao.IBaseDao;
import com.vatidas.entity.Log1;
import com.vatidas.service.ILogService;
import com.vatidas.utils.LogUtil;

public class LogServiceImpl extends BaseServiceImpl<Log1> implements ILogService {

	private IBaseDao<Log1> logDao;
	private UUIDHexGenerator uid = new UUIDHexGenerator();
	
	public void setLogDao(IBaseDao<Log1> logDao) {
		this.logDao = logDao;
	}

	/**
	 * 查询应使用联合查询
	 */
	@Override
	public List<Log1> findAllLogs() {
		//查询本月和上月的所有日志
		//此时查询不同的表，hibernate查询出的实体放进缓存  id不应该相同，所有将日志表的id改成uuid生成策略，执行sql需要自己生成uuid的值，查看保存日志方法
		String sql = "select * from " + LogUtil.generateLogTableName(-1) 
					  + " union all select * from " + LogUtil.generateLogTableName(0);
		List<Log1> logList = logDao.findEntityBySql(sql);
		return logList;
	}

	/**
	 * 将记录插入到当前月的日志表
	 */
	@Override
	public void saveLogEntity(Log1 log) {
		String sql = "insert into " + LogUtil.generateLogTableName(0) +"(id, operator, operateName, operateTime, operateParams, operateResult)"
					+"values (?,?,?,?,?,?)";
		String id = (String) uid.generate(null, null);
		logDao.executeSQL(sql, id, log.getOperator(), log.getOperateName(),
							   log.getOperateTime(),log.getOperateParams(),log.getOperateResult());
	}

	@Override
	public void createTable(String sql) {
		logDao.executeSQL(sql);
	}

	/**
	 * 只能查询最近三个月任意时间段的日志记录
	 * 如果要更早，得去数据库找
	 */
	@Override
	public List<Log1> findLogByDate(Date startDate, Date endDate) {
		String sql = "select * from " + LogUtil.generateLogTableName(-1) + " g where g.operateTime between ? and ?" 
					+ " union all select * from " + LogUtil.generateLogTableName(0)+ "  g where g.operateTime between ? and ?"
					+ " union all select * from " + LogUtil.generateLogTableName(1)+ "  g where g.operateTime between ? and ?";
		System.out.println(sql);
		return logDao.findEntityBySql(sql, startDate, endDate,  startDate, endDate,  startDate, endDate);
	}
	
	
}
