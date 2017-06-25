package com.vatidas.serviceImpl;

import java.util.List;

import com.vatidas.dao.IBaseDao;
import com.vatidas.entity.Role;
import com.vatidas.service.IRoleService;

public class RoleServiceImpl extends BaseServiceImpl<Role> implements IRoleService {

	private IBaseDao<Role> roleDao;
	
	public void setRoleDao(IBaseDao<Role> roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public List<Role> findAllRole() {
		List<Role> roleList = roleDao.findEntityByHql("from Role");
		return roleList;
	}

}
