package com.ecannetwork.core.module.db.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * 
 * @author leo
 * 
 * @param <D>
 */
public abstract class DaoSupport<D> extends SpringDAOSupport
{
	public abstract Class<D> getDTOClass();

	/**
	 * 清空数据
	 */
	public void clean()
	{
		this.getHibernateTemplate().deleteAll(this.list());
	}

	/**
	 * 删除多个对象
	 * 
	 * @param ids
	 */
	public void delete(String... ids)
	{
		List<D> list = this.get(ids);
		this.getHibernateTemplate().deleteAll(list);
	}

	/**
	 * 获取多个对象
	 * 
	 * @param ids
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<D> get(String... ids)
	{
		String hql = "from " + this.getDTOClass().getName()
				+ " t where t.id in (?)";
		List<String> idList = new ArrayList<String>();
		for (int i = 0; i < ids.length; i++)
		{
			idList.add(ids[i]);
		}

		return this.getHibernateTemplate().find(hql, idList);
	}

	/**
	 * 列表查询
	 * 
	 * @param hql
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<D> list(String hql, Object... args)
	{
		return this.getHibernateTemplate().find(hql, args);
	}

	/**
	 * 保存
	 * 
	 * @param dto
	 */
	public void save(DtoSupport dto)
	{
		this.getHibernateTemplate().save(dto);
	}

	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	public D get(java.lang.String id)
	{
		return (D) this.getHibernateTemplate().get(this.getDTOClass(), id);
	}

	/**
	 * 列举所有对象
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<D> list()
	{
		return (List<D>) this.getHibernateTemplate().find(
				"from " + getDTOClass().getName());
	}

	/**
	 * 删除对象
	 * 
	 * @param dto
	 */
	public void delete(DtoSupport dto)
	{
		this.getHibernateTemplate().delete(dto);
	}

	/**
	 * 删除所有
	 * 
	 * @param collection
	 */
	public void deleteAll(Collection<DtoSupport> collection)
	{
		this.getHibernateTemplate().deleteAll(collection);
	}

	/**
	 * 更新
	 * 
	 * @param dto
	 */
	public void update(DtoSupport dto)
	{
		this.getHibernateTemplate().update(dto);
	}

	/**
	 * 更新列表对象
	 * 
	 * @param dtos
	 */
	public void updateAll(List<D> dtos)
	{
		for (D dto : dtos)
		{
			this.getHibernateTemplate().update(dto);
		}
	}

	/**
	 * 保存或者更新
	 * 
	 * @param dto
	 */
	public void saveOrUpdate(DtoSupport dto)
	{
		this.getHibernateTemplate().saveOrUpdate(dto);
	}

}
