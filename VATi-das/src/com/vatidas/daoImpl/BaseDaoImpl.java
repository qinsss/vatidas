package com.vatidas.daoImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;

import com.vatidas.dao.IBaseDao;
import com.vatidas.entity.Log1;

public  class BaseDaoImpl<T> implements IBaseDao<T> {

	//hibernate的会话工厂 核心
	private SessionFactory sf;
	//通过set方法进行注入
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}
	
	public SessionFactory getSf() {
		return sf;
	}

	public Class<T> getC() {
		return c;
	}

	private Class<T> c;
	
	/*
	 * 通过无参构造器获取泛型对应的实体
	 */
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		Type t = this.getClass().getGenericSuperclass();
		if(t instanceof ParameterizedType){
			ParameterizedType type = (ParameterizedType)t;
			c = (Class<T>)type.getActualTypeArguments()[0];
		}
	}
	
	@Override
	public void saveEntity(T t) {
		//session.save(t);
		sf.getCurrentSession().save(t);
	}

	@Override
	public void deleteEntity(T t) {
		//session.delete(t);
		sf.getCurrentSession().delete(t);
		
	}

	@Override
	public void updateEntity(T t) {
		//session.update(t);
		sf.getCurrentSession().update(t);
	}

	@Override
	public void batchEntityByHql(String hql, Object... objects) {
		Query query = sf.getCurrentSession().createQuery(hql);
		//需要给hql动态传参objects
		for(int i = 0; i < objects.length; i++ ){
			query.setParameter(i, objects[i]);
		}
		query.executeUpdate();
	}

	@Override
	public void batchEntityBySql(String sql, Object... objects) {
		//SQLQuery sqlQuery = session.createSQLQuery(sql);
		SQLQuery sqlQuery = sf.getCurrentSession().createSQLQuery(sql);
		for(int i = 0; i < objects.length; i++){
			sqlQuery.setParameter(i, objects[i]);
		}
		sqlQuery.executeUpdate();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<T> findEntityByHql(String hql, Object... objects) {
		//Query query = session.createQuery(hql);
		Query query = sf.getCurrentSession().createQuery(hql);
		for(int i = 0; i < objects.length; i++){
			query.setParameter(i, objects[i]);
		}
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object findEntityUnique(String hql, Object... objects) {
		Query hqlQuery = sf.getCurrentSession().createQuery(hql);
		for(int i = 0 ; i < objects.length ; i++){
			hqlQuery.setParameter(i, objects[i]);
		}
		return (T) hqlQuery.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findEntityBySql(String sql, Object... objects) {
		//SQLQuery sqlQuery = session.createSQLQuery(sql);
		System.out.println(this.getSf());
		SQLQuery sqlQuery = this.getSf().getCurrentSession().createSQLQuery(sql);
		for(int i = 0; i < objects.length; i ++){
			sqlQuery.setParameter(i, objects[i]);
		}
		//添加实体类，将sql查询的集合中，将其封装成实体类
		sqlQuery.addEntity(Log1.class);
		return sqlQuery.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findEntityByPage(String hql, int offset, int pageSize, Object...objects) {
		Query query = sf.getCurrentSession().createQuery(hql);
		query.setFirstResult(offset);
		query.setMaxResults(pageSize);
		for(int i = 0 ; i < objects.length ; i++){
			query.setParameter(i, objects[i]);
		}
		return query.list();
	}

	@Override
	public void executeSQL(String sql, Object... objects) {
		SQLQuery q = sf.getCurrentSession().createSQLQuery(sql);
		for(int i = 0 ; i < objects.length ; i ++){
			q.setParameter(i, objects[i]);
		}
		q.executeUpdate();
	}

	@Override
	public void saveOrUpdateEntity(T t) {
		sf.getCurrentSession().saveOrUpdate(t);
	}

	@Override
	public List<?> checkSQL(String sql) {
		return sf.getCurrentSession().createSQLQuery(sql).list();
	}

}
