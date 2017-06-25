package com.vatidas.serviceImpl;

import java.util.List;

import com.vatidas.dao.IBaseDao;
import com.vatidas.entity.Right1;
import com.vatidas.entity.Role;
import com.vatidas.service.IRightService;

public class RightServiceImpl extends BaseServiceImpl<Right1> implements IRightService {

	private IBaseDao<Right1> rightDao;
	private IBaseDao<Role> roleDao;
	
	public void setRoleDao(IBaseDao<Role> roleDao) {
		this.roleDao = roleDao;
	}

	public void setRightDao(IBaseDao<Right1> rightDao){
		this.rightDao = rightDao;
	}
	
	@Override
	public void appendRightURL(String url) {
		String hql1 = "from Right1 r where r.rightUrl = ? ";
		Right1 r1  = (Right1) rightDao.findEntityUnique(hql1, url);
		System.out.println(r1);
		//如果不存在，则进行保存
		if(r1 == null){
			//创建一个Right对象
			Right1 r = new Right1();
			//首先查看数据库表是否为空表-->查询数据库的最大rightCode
			String hql2 = "select max(r.rightCode) from Right1 r";
			Object o = rightDao.findEntityUnique(hql2);
			int maxRightCode =  (int) o;
			System.out.println(maxRightCode+"m");
			//将它加1赋给r，如果为空表它的初始值为0，加1得1开始，如果不为空也是加1存储
			r.setRightCode(maxRightCode+1);
			r.setRightUrl(url);
			//保存实体
			System.out.println(r);
			rightDao.saveEntity(r);
		}
	}

	@Override
	public List<Right1> findAllRight() {
		List<Right1> rightList = rightDao.findEntityByHql("from Right1");
		return  rightList;
	}

	@Override
	public Role findRoleRight(String roleName) {
		return (Role) roleDao.findEntityUnique("from Role r where r.name = ?", roleName);
	}

	

}
