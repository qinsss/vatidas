package com.vatidas.dao;

import java.util.List;

/**
 *	创建dao层接口
 *	使用泛型可以不用引进其它类，进一步降低耦合度
 *	可在其实现类中通过反射获得具体类 
 */
public interface IBaseDao<T> {
	
	//添加实体操作
	public void saveEntity(T t);
	//删除实体操作
	public void deleteEntity(T t);
	//更新实体操作
	public void updateEntity(T t);
	
	//通过hql语句操作实体  objects为hql语句的动态参数
	public void batchEntityByHql(String hql, Object...objects);
	
	//通过sql语句操作实体  objects为sql语句的动态参数
	public void batchEntityBySql(String sql, Object...objects);
	
	//根据Hql语句查询  objects为hql语句的动态参数
	public List<T> findEntityByHql(String hql, Object...objects);
	
	//根据hql语句查询唯一结果值
	public Object findEntityUnique(String hql, Object...objects);
	
	//使用hql进行分页查询
	public List<T> findEntityByPage(String hql, int offset, int pageSize, Object...objects);
	
	public void executeSQL(String sql, Object...objects);
	
	public List<T> findEntityBySql(String sql, Object...objects);
	 
	public void saveOrUpdateEntity(T t);
	
	public List<?> checkSQL(String sql);
	
	
}
