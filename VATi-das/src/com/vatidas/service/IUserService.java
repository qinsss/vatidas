package com.vatidas.service;

import java.util.List;

import com.vatidas.entity.User;

public interface IUserService extends IBaseService<User> {
	
	/**
	 * 根据账号密码查询用户账号是否存在
	 * @param account
	 * @param password
	 * @return
	 */
	User queryAccount(String account, String password);

	/**
	 * 查询所有账号
	 * @return
	 */
	List<User> findAllAccount();
	
	/**
	 * 根据账号进行删除
	 * @param account
	 * @return
	 */
	void deleteAccount(String account);

	/**
	 * 判断原密码是否正确
	 * @param account
	 * @param oldPassword
	 * @return
	 */
	boolean validatePassword(String account, String oldPassword);

	/**
	 * 修改密码
	 * @param account
	 * @param newPassword
	 */
	void modifyPassword(String account, String newPassword);

	/**
	 * 添加新账号
	 * @param account
	 * @param password
	 * @param role
	 * @param nickname
	 */
	void addAccount(String account, String password, String role, String nickname);

	/**
	 * 添加账号时检查账号是否存在
	 * @param parameter
	 * @return
	 */
	String checkAddAccount(String parameter);

}
