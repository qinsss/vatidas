package com.vatidas.serviceImpl;

import java.util.List;

import com.vatidas.dao.IBaseDao;
import com.vatidas.entity.Role;
import com.vatidas.entity.User;
import com.vatidas.service.IUserService;

public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {
	
	//使用接口注入 而不是实际的类
	private IBaseDao<User> userDao;
	private IBaseDao<Role> roleDao;
	

	//使用setter方法进行注入
	public void setUserDao(IBaseDao<User> userDao) {
		this.userDao = userDao;
	}
	public void setRoleDao(IBaseDao<Role> roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public User queryAccount(String account, String password) {
		String hql = "from User u where u.account = ? and u.password = ?";
		User user = (User) userDao.findEntityUnique(hql, account, password);
		return user;
	}

	@Override
	public List<User> findAllAccount() {
		String hql = "from User";
		List<User> userList = userDao.findEntityByHql(hql);
		return userList;
	}

	@Override
	public void deleteAccount(String account) {
		String hql = "delete from User u where u.account = ?";
		userDao.batchEntityByHql(hql, account);
	}

	@Override
	public boolean validatePassword(String account, String oldPassword) {
		String hql = "from User u where u.account = ? and u.password = ?";
		//唯一值
		User user = (User) userDao.findEntityUnique(hql, account, oldPassword);
		if(user != null){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public void modifyPassword(String account, String newPassword) {
		String hql = "upadte u.password = ? from User u where u.account = ?";
		userDao.findEntityByHql(hql, newPassword, account);
	}

	@Override
	public void addAccount(String account, String password, String role, String nickname) {
		//需要先创建一个角色对象  可是一共只有两个角色，不能每次都重新创建对象呀，，，，，而是应该判断其存不存在
		Role r;
		r = (Role) roleDao.findEntityUnique("from Role r where r.name = ?", role);
		System.out.println(r);
		////如果角色对象不存在，则新创建一个role对象
		if(r == null){
			r = new Role(role, null);
		}else{ 
			;
		}
		User u = new User(account,password,nickname,r);
		userDao.saveEntity(u);
		
	}
	@Override
	public String checkAddAccount(String account) {
		User u = (User) userDao.findEntityUnique("from User u where u.account = ?", account);
		if(u != null){
			return "exist";
		}
		return "";
	}

	
}
