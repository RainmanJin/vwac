package com.ecannetwork.core.module.service;

import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import com.ecannetwork.core.module.db.dao.CommonDAO;
import com.ecannetwork.core.module.db.dto.DtoSupport;

@Service("commonService")
public class CommonService
{
	@Autowired
	private CommonDAO commonDAO;

	public void saveTX(DtoSupport dto)
	{
		commonDAO.save(dto);
	}

	public void saveTX(Collection<?> cl)
	{
		commonDAO.save(cl);
	}

	public DtoSupport get(String id, @SuppressWarnings("rawtypes") Class c)
	{
		return commonDAO.get(id, c);
	}

	public List get(Class d, String ids)
	{
		return this.commonDAO.get(d, ids);
	}

	public List list(String hql, Object... params)
	{
		return this.commonDAO.list(hql, params);
	}

	@SuppressWarnings("rawtypes")
	public List list(@SuppressWarnings("rawtypes") Class c)
	{
		return this.commonDAO.list(c);
	}

	/**
	 * 删除
	 * 
	 * @param c
	 * @param ids
	 */
	public void deleteTX(Class c, String... ids)
	{
		this.commonDAO.delete(c, ids);
	}

	public void deleteTX(DtoSupport dto)
	{
		commonDAO.delete(dto);
	}

	public void deleteAllTX(Collection collection)
	{
		commonDAO.deleteAll(collection);
	}

	public void updateTX(DtoSupport dto)
	{
		commonDAO.update(dto);
	}

	public void saveOrUpdateTX(DtoSupport dto)
	{
		commonDAO.saveOrUpdate(dto);
	}

	public void saveOrUpdateTX(Collection<?> cl)
	{
		commonDAO.saveOrUpdate(cl);
	}

	/**
	 * 删除一个hql的结果集
	 * 
	 * @param hql
	 * @param params
	 */
	public void deleteAllTX(String hql, Object... params)
	{
		List list = this.list(hql, params);
		this.commonDAO.deleteAll(list);
	}

	/**
	 * 清空整张表
	 * 
	 * @param c
	 */
	public void deleteAllTX(Class c)
	{
		List list = this.list(c);
		this.commonDAO.deleteAll(list);
	}

	public Object listOnlyObject(String sql, Object... params)
	{
		List list = this.list(sql, params);
		if (list.size() > 0)
		{
			return list.get(0);
		} else
		{
			return null;
		}
	}
	
	/**
	 * 分页查询
	 * @param hql
	 * @param maxCount
	 * @param firstResult
	 * @return
	 * @throws Exception
	 */

	/**
	 * 分页查询
	 * @param hql
	 * @param maxCount
	 * @param firstResult
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<?> pageListQuery(final String hql,final int maxCount,final int firstResult){  
		 return this.commonDAO.getHibernateTemplate().executeFind(new HibernateCallback<Object>() {  
			 public Object doInHibernate(final Session session) {  
				 final Query query = session.createQuery(hql);  
				 query.setMaxResults(maxCount);  
				 query.setFirstResult(firstResult);  
				 return query.list();  
			 }  
		 });  
	}

	/**
	 * 执行一个callback
	 * 
	 * @param callback
	 * @return
	 */
	public Object executeCallbackTX(HibernateCallback callback)
	{
		return this.commonDAO.getHibernateTemplate().execute(callback);
	}

	public List listWithNameValues(String hql, List<String> names,
			List<String> args)
	{
		return this.commonDAO.listWithNameValues(hql, names, args);
	}
}
