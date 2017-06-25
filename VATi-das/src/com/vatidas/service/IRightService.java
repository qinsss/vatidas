package com.vatidas.service;

import java.util.List;

import com.vatidas.entity.Right1;
import com.vatidas.entity.Role;

public interface IRightService extends IBaseService<Right1> {

	/**
	 * 向数据库中保存捕获到的URL
	 * @param url
	 */
	public void appendRightURL(String url);

	/**
	 * 查看所有权限
	 * @return 
	 */
	public List<Right1> findAllRight();

	/**
	 * 查询出对应角色,然后迭代它的权限
	 * @param roleName
	 * @return
	 */
	public Role findRoleRight(String roleName);
	

}
