package com.vatidas.service;

import java.util.List;

import com.vatidas.entity.Role;

public interface IRoleService extends IBaseService<Role> {

	/**
	 * 查询所有的角色
	 */
	public List<Role> findAllRole();

}
