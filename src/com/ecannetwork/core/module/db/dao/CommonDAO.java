package com.ecannetwork.core.module.db.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * 通用DAO， 简单需求可以直接使用该DAO，而不用单独创建DAO
 * 
 * @author leo
 * 
 */
@Repository
@SuppressWarnings("unchecked")
public class CommonDAO extends SpringDAOSupport
{

	/**
	 * 清空数据:::先查后删，大数据量时，效率会比较低
	 */
	public void clean(Class d)
	{
		this.deleteAll(this.list(d));
	}

	/**
	 * 删除多个对象
	 * 
	 * @param ids
	 */
	public void delete(Class d, String... ids)
	{
		List list = this.get(d, ids);
		this.getHibernateTemplate().deleteAll(list);
	}

	/**
	 * 获取多个对象
	 * 
	 * @param ids
	 * @return
	 */
	public List get(Class d, String... ids)
	{
		String hql = "from " + d.getName() + " t where t.id in (?)";
		List<String> idList = new ArrayList<String>();
		for (int i = 0; i < ids.length; i++)
		{
			idList.add(ids[i]);
		}

		return this.getHibernateTemplate().find(hql, ids);
	}

	/**
	 * 列表查询
	 * 
	 * @param hql
	 * @param args
	 * @return
	 */
	public List list(String hql, Object... args)
	{
		if (args == null)
		{
			return this.getHibernateTemplate().find(hql);
		} else
		{
			return this.getHibernateTemplate().find(hql, args);
		}
	}

	/**
	 * 保存对象
	 * 
	 * @param dto
	 */
	public void save(DtoSupport dto)
	{
		this.getHibernateTemplate().save(dto);
	}

	/**
	 * 保存多个对象
	 * 
	 * @param list
	 */
	public void save(Collection list)
	{
		for (Object o : list)
		{
			this.getHibernateTemplate().save(o);
		}
	}

	/**
	 * 获取单个对象
	 * 
	 * @param id
	 * @param c
	 * @return
	 */
	public DtoSupport get(java.lang.String id, Class c)
	{
		return (DtoSupport) this.getHibernateTemplate().get(c, id);
	}

	public List list(Class c)
	{
		return this.getHibernateTemplate().find("from " + c.getName());
	}

	/**
	 * 删除
	 * 
	 * @param dto
	 */
	public void delete(DtoSupport dto)
	{
		this.getHibernateTemplate().delete(dto);
	}

	public void deleteAll(Collection collection)
	{
		if (collection != null && collection.size() > 0)
			this.getHibernateTemplate().deleteAll(collection);
	}

	public void update(DtoSupport dto)
	{
		this.getHibernateTemplate().update(dto);
	}

	public void update(Collection list)
	{
		for (Object o : list)
		{
			this.getHibernateTemplate().update(o);
		}
	}

	public void saveOrUpdate(DtoSupport dto)
	{
		this.getHibernateTemplate().saveOrUpdate(dto);
	}

	public void saveOrUpdate(Collection list)
	{
		for (Object dto : list)
			this.getHibernateTemplate().saveOrUpdate(dto);
	}

	public List listWithNameValues(String hql, List<String> names,
			List<String> args)
	{
		if (names != null && names.size() > 0)
		{
			String[] ns = new String[names.size()];
			String[] vs = new String[args.size()];
			for (int i = 0; i < ns.length; i++)
			{
				ns[i] = names.get(i);
				vs[i] = args.get(i);
			}

			return this.getHibernateTemplate().findByNamedParam(hql, ns, vs);
		} else
		{
			return this.getHibernateTemplate().find(hql);
		}
	}
}
