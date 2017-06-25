package com.vatidas.junitTest;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.vatidas.dao.IBaseDao;
import com.vatidas.entity.ImportInvoice;
import com.vatidas.entity.InOutStatistic;
import com.vatidas.entity.Invoice;
import com.vatidas.entity.Log1;
import com.vatidas.entity.Right1;
import com.vatidas.entity.Role;
import com.vatidas.entity.User;
import com.vatidas.service.IInvoiceService;
import com.vatidas.utils.CommonUtils;
import com.vatidas.utils.LogUtil;

public class configTest {
	//spring配置文件路径
	String path = "classpath:com/config/springConfigCommon.xml";
	ApplicationContext ac = null;
	
	/*
	 * 获取spring容器
	 */
	@Before
	public void getApplicationContext(){
		ac = new ClassPathXmlApplicationContext(path);
	}
	/*
	 * 关闭容器
	 */
	@After
	public void closeApplicationContext(){
		((AbstractApplicationContext) ac).close();
	}
	/*
	 * 测试数据源是否配置成功
	 */
	@Test
	public void testDataSource() throws SQLException{
		ComboPooledDataSource conn = (ComboPooledDataSource) ac.getBean("dataSource");
		System.out .println(conn);//com.mchange.v2.c3p0.impl.NewProxyConnection@74d3776e连接成功
		System.out .println(conn.getPassword());
		System.out .println(conn.getJdbcUrl());
	}
	
	/*
	 * 
	 * 测试关联关系的建立  成功
	 */
	@Test
	public void addRole(){
		Role role = new Role();
		role.setName("企业运者xx");
		User u1 = new User();
		u1.setAccount("1009");
		u1.setPassword("123");
		u1.setNickname("小张");
		User u2 = new User();
		u2.setAccount("10010");
		u2.setPassword("123");
		u2.setNickname("黄总");
		Set<User> user = new HashSet<User>();
		user.add(u1);
		user.add(u2);
		role.setUser(user);
		/*u2.setRole(role);
		u1.setRole(role);*/
		@SuppressWarnings("unchecked")
		IBaseDao<Role> baseDao = (IBaseDao<Role>)ac.getBean("baseDao");
		baseDao.saveEntity(role);
	}
	
	
	/**
	 * 测试发票数据添加到数据库
	 */
	@Test
	public void addInvoice(){
		Invoice i1 = new Invoice();
		for(int i=1;i<11;i++){
			i1.setCode("2017060020-00"+i);
			i1.setDate(CommonUtils.DateTransformTest("2017-06-20"));
			i1.setUnit("广西食物材料销售中心");
			i1.setCommodityName("鸡蛋");
			i1.setCommodityNum(2000);
			i1.setCommodityUnit("个");
			i1.setCommodityPrePrice(new BigDecimal(1.2));
			i1.setCommodityMoney(new BigDecimal(2.3));
			i1.setType("销项");
			i1.setInvoiceTaxMoney(new BigDecimal(0.25));
			i1.setTotalMoney(new BigDecimal(2.4));
			@SuppressWarnings("unchecked")
			IBaseDao<Invoice> baseDao = (IBaseDao<Invoice>)ac.getBean("baseDao");
			baseDao.saveEntity(i1);
		}
	}
	
	@Test
	public void testQuery(){
		@SuppressWarnings("unchecked")
		IBaseDao<User> baseDao = (IBaseDao<User>)ac.getBean("baseDao");
		List<User> findEntityByHql = baseDao.findEntityByHql("from User");
		for (User user : findEntityByHql) {
			System.out.println(user);
		}
	}
	
	@Test
	public void testStatisticView(){
		@SuppressWarnings("unchecked")
		IBaseDao<InOutStatistic> baseDao = (IBaseDao<InOutStatistic>)ac.getBean("baseDao");
		List<InOutStatistic> list = baseDao.findEntityByHql("from InOutStatistic");
		System.out.println("================");
		System.out.println(list.size());
		for (InOutStatistic inOutStatistic : list) {
			System.out.println(inOutStatistic);
		}
	}
	
	
	@Test
	public void createChart(){
		IInvoiceService invoiceService = (IInvoiceService) ac.getBean("invoiceService");
		String analyzeItem = "inOutMoney";
		String analyzeView = "bar";
		Date startYm = CommonUtils.DateTransform("2017-03");
		Date endYm = CommonUtils.DateTransform("2017-07");
		invoiceService.getAnalyzeDataItem(2,analyzeItem, analyzeView, startYm, endYm,"SS");
	}
	
	
	
	@Test
	public void testImportDataTable(){
		@SuppressWarnings("unchecked")
		IBaseDao<ImportInvoice> baseDao = (IBaseDao<ImportInvoice>)ac.getBean("baseDao");
		ImportInvoice i = new ImportInvoice();
		i.setCommodityName("猪肉");
		i.setType("进项");
		i.setMoney(BigDecimal.valueOf(5.25));
		i.setYearMonth(CommonUtils.DateTransform("2018-06"));
		baseDao.saveEntity(i);
		System.out.println(baseDao.findEntityByHql("from ImportInvoice").size());
	}
	
	
	
	
	@Test
	public void testImportDataTableSum(){
		@SuppressWarnings("unchecked")
		IBaseDao<ImportInvoice> baseDao = (IBaseDao<ImportInvoice>)ac.getBean("baseDao");
		String hql = "select new ImportInvoice(i.yearMonth,i.type,sum(i.money)) from ImportInvoice i where (i.yearMonth between ? and ?) and i.type=? group by i.yearMonth,i.type order by i.yearMonth";
		//String hql = "select new ImportInvoice(i.yearMonth,i.type,sum(i.money)) from ImportInvoice i where i.yearMonth between ? and ? group by i.yearMonth,i.type order by i.yearMonth";
		List<ImportInvoice> list = baseDao.findEntityByHql(hql, CommonUtils.DateTransform("2016-01"),CommonUtils.DateTransform("2016-06"),"进项");
		System.out.println(list.size());
		int i=0;
		for (ImportInvoice importInvoice : list) {
			System.out.println(importInvoice +"" + i++);
		}
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testRight(){
		Role role = new Role("xxxx","xxx");
		Set<Right1> rset = new HashSet<Right1>();
		Right1 r = new Right1();
		r.setRightCode(1);
		r.setRightName("添加发票");
		r.setRightUrl("/invoiceAction_addInvoice");
		rset.add(r);
		role.setRights(rset);
		IBaseDao<Role> baseDao = (IBaseDao<Role>)ac.getBean("baseDao");
		baseDao.saveEntity(role);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void queryRight(){
		IBaseDao<Right1> baseDao = (IBaseDao<Right1>)ac.getBean("baseDao");
		List<Right1> list = baseDao.findEntityByHql("from Right1");
		System.out.println(list.size());
	}
	
	@Test
	public void addSystemAdmin(){
		User u = new User();
		
		u.setNickname("管理");
		u.setAccount("sa");
		u.setPassword("123456");
		Role r = new Role();
		r.setName("系统管理员");
		r.setRightValue(-1);
		u.setRole(r);
		@SuppressWarnings("unchecked")
		IBaseDao<User> baseDao = (IBaseDao<User>)ac.getBean("baseDao");
		baseDao.saveEntity(u);
	}
	
	@Test
	public void setRoleRights(){
		@SuppressWarnings("unchecked")
		IBaseDao<Role> roleDao = (IBaseDao<Role>)ac.getBean("baseDao");
		Role role = (Role) roleDao.findEntityUnique("from Role r where r.name = ?", "账号管理员");
		Set<Right1> rset = new HashSet<Right1>();
		@SuppressWarnings("unchecked")
		IBaseDao<Right1> baseDao = (IBaseDao<Right1>)ac.getBean("baseDao");
		List<Right1> rigList = baseDao.findEntityByHql("from Right1");
		for (Right1 right1 : rigList) {
			rset.add(right1);
		}
		role.setRights(rset);
		System.out.println(role);
		roleDao.saveOrUpdateEntity(role);
	}
	@Test
	public void findLog(){
		System.out.println("!");
		@SuppressWarnings("unchecked")
		IBaseDao<Log1> logDao =   (IBaseDao<Log1>) ac.getBean("baseDao");
		System.out.println("2");
		String sql = "select * from " + LogUtil.generateLogTableName(0) 
		  + " union select * from " + LogUtil.generateLogTableName(1);
			List<Log1> logList = logDao.findEntityBySql(sql);
		System.out.println(logList);
	}
	
	@Test
	public void tableExists(){
		String sql = " select TABLE_NAME t from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA='vatidas' and TABLE_NAME='log1'";
		IBaseDao logDao = (IBaseDao)ac.getBean("baseDao");
		List o = logDao.checkSQL(sql);
		System.out.println("==");
		System.out.println(o.size());
		System.out.println("==");
	}
}
