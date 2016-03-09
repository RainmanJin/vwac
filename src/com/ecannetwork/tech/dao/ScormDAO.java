package com.ecannetwork.tech.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ecannetwork.core.module.db.dao.DaoSupport;
import com.ecannetwork.dto.tech.TechCourse;
import com.ecannetwork.dto.tech.TechCourseItem;

@Repository
public class ScormDAO extends DaoSupport<TechCourse>
{

	@Override
	public Class<TechCourse> getDTOClass()
	{
		return TechCourse.class;
	}

	/**
	 * 列举所有课件章节信息
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TechCourseItem> listItems(String id)
	{
		return this.getHibernateTemplate().find(
				"from TechCourseItem where courseId=? order by thelevel, idx",
				id);
	}

}
