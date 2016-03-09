package com.ecannetwork.tech.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecannetwork.core.i18n.I18N;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.db.dao.CommonDAO;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.dto.tech.TechTestDb;
import com.ecannetwork.dto.tech.TechTrainCourse;
import com.ecannetwork.dto.tech.TechTrainCourseItem;
import com.ecannetwork.dto.tech.TechTrainPlan;
import com.ecannetwork.tech.controller.bean.commonresouce.TrianCourseModule;

/**
 * 培训计划
 * 
 * @author liulibin
 * 
 */
@Service
public class TrainPlanService
{
	@Autowired
	private CommonDAO commonDAO;

	/**
	 * 停用课程
	 * 
	 * @param id
	 * @return
	 */
	public AjaxResponse disableCouseTX(String id)
	{
		TechTrainCourse course = (TechTrainCourse) this.commonDAO.get(id, TechTrainCourse.class);
		course.setStatus(CoreConsts.YORN.NO);
		return new AjaxResponse();
	}

	/**
	 * 启用课程
	 * 
	 * @param id
	 * @return
	 */
	public AjaxResponse activeCourseTX(String id)
	{
		TechTrainCourse course = (TechTrainCourse) this.commonDAO.get(id, TechTrainCourse.class);
		course.setStatus(CoreConsts.YORN.YES);
		return new AjaxResponse();
	}

	/**
	 *  删除培训课程：：：已有培训计划的不能删除
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public AjaxResponse delCourseTX(String id)
	{
		List<TechTrainPlan> plans = this.commonDAO.list("from TechTrainPlan t where t.trainId = ?", id);
		if (plans.size() > 0)
		{// 有培训计划的不能删
			return new AjaxResponse(false, I18N.parse("i18n.traincourse.msg.deleteUsedCourseWithPlan"));
		}
		else
		{
			List<TechTestDb> dbs = this.commonDAO.list("from TechTestDb t where t.trainCourseId=?", id);
			if (dbs.size() > 0)
			{// 有测评试题的不能删除
				return new AjaxResponse(false, I18N.parse("i18n.traincourse.msg.deleteUsedCourseWithTestDB"));
			}
			else
			{
				this.commonDAO.delete(TechTrainCourse.class, id);
			}
		}

		return new AjaxResponse();
	}

	/**
	 * 列举一个年度计划
	 * 
	 * @param year
	 * @param brand
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TechTrainPlan> listByYearAndBrand(String year, String brand)
	{
		return this.commonDAO.list("from TechTrainPlan t where t.yearValue=? and t.brand=?", year, brand);
	}

	/**
	 * 查询某个品牌、领域下的课程
	 * 
	 * @param brand
	 * @param proType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TechTrainCourse> listTrainCourseByProTypeAndBrand(String brand, String proType)
	{
		return this.commonDAO.list("from com.ecannetwork.dto.tech.TechTrainCourse t where t.proType=? and t.brand=? and t.status=?", proType, brand, CoreConsts.YORN.YES);
	}

	/**
	 * 查询某个品牌、领域下的课程与模块列表
	 * 
	 * @param brand
	 * @param proType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TrianCourseModule> listTrainCourseAndItemsByProTypeAndBrand(String brand, String proType)
	{
		// 所有课程
		List<TechTrainCourse> courses = this.commonDAO.list("from com.ecannetwork.dto.tech.TechTrainCourse t where t.proType=? and t.brand=? and t.status=?", proType, brand, CoreConsts.YORN.YES);

		List<TrianCourseModule> tms = new ArrayList<TrianCourseModule>();

		if (courses.size() > 0)
		{
			Map<String, TechTrainCourse> couseMap = new HashMap<String, TechTrainCourse>();
			// 查询出课程下的所有模块
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < courses.size(); i++)
			{
				TechTrainCourse c = courses.get(i);
				couseMap.put(c.getId(), c);

				sb.append("'").append(c.getId()).append("'");
				if (i != courses.size() - 1)
				{
					sb.append(",");
				}
			}

			List<TechTrainCourseItem> items = this.commonDAO.list("from TechTrainCourseItem i where i.trainCourseId in(" + sb.toString() + ")");

			Set<String> idsToRemove = new HashSet<String>();
			for (TechTrainCourseItem item : items)
			{
				idsToRemove.add(item.getTrainCourseId());

				TechTrainCourse c = couseMap.get(item.getTrainCourseId());

				TrianCourseModule tm = new TrianCourseModule();
				tm.setBrand(c.getBrand());
				tm.setCourseid(c.getId());
				tm.setCourseName(c.getName());
				tm.setProType(c.getProType());
				tm.setModuleid(item.getId());
				tm.setModuleName(item.getName());
				tm.setDays(c.getDays());

				tms.add(tm);
			}

			for (String id : idsToRemove)
			{
				couseMap.remove(id);
			}

			for (TechTrainCourse c : couseMap.values())
			{
				TrianCourseModule tm = new TrianCourseModule();
				tm.setBrand(c.getBrand());
				tm.setCourseid(c.getId());
				tm.setCourseName(c.getName());
				tm.setProType(c.getProType());
				tm.setDays(c.getDays());
				tm.setModuleid("");
				tm.setModuleName("");

				tms.add(tm);
			}
		}

		return tms;
	}

	/**
	 * 获取所有启动的课程 yufei,2015-01-26，原来为No
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TechTrainCourse> listTrainCourse()
	{
		return this.commonDAO.list("from com.ecannetwork.dto.tech.TechTrainCourse t where t.status=?", CoreConsts.YORN.YES);
	}

	/**
	 * 查询出所有课程排期，组成适合报表展示的样式
	 * 
	 * @param year
	 *            年度
	 * @param brand
	 *            品牌
	 * @return
	 */
	public Map<String, List<TechTrainCourse>> listTranPlayTable(String year, String brand)
	{
		// 所有课程
		List<TechTrainCourse> courses = this.commonDAO.list("from com.ecannetwork.dto.tech.TechTrainCourse t where t.brand=? and t.status=?", brand, CoreConsts.YORN.YES);

		// 所有培训计划
		List<TechTrainPlan> plans = this.commonDAO.list("from TechTrainPlan t where t.yearValue=? and t.brand=?", year, brand);

		// 将培训计划初始化为map结构方便查找
		Map<String, List<TechTrainPlan>> planMap = new HashMap<String, List<TechTrainPlan>>(courses.size());
		for (TechTrainPlan plan : plans)
		{
			List<TechTrainPlan> pls = planMap.get(plan.getTrainId());
			if (pls == null)
			{
				pls = new ArrayList<TechTrainPlan>();
				planMap.put(plan.getTrainId(), pls);
			}
			pls.add(plan);
		}

		// 构建课程排期表Map<领域编号,List<课程(包含计划)>
		Map<String, List<TechTrainCourse>> map = new HashMap<String, List<TechTrainCourse>>();
		for (TechTrainCourse c : courses)
		{
			List<TechTrainCourse> cs = map.get(c.getProType());
			if (cs == null)
			{
				cs = new ArrayList<TechTrainCourse>();
				map.put(c.getProType(), cs);
			}
			cs.add(c);

			c.setTrainPlans(planMap.get(c.getId()));
		}

		return map;
	}

	/**
	 * 删除某个年度的排期
	 * 
	 * @param year
	 * @param brand
	 */
	public void deleteTX(String year, String brand)
	{
		List<TechTrainPlan> list = this.listByYearAndBrand(year, brand);
		if (list.size() > 0)
		{
			this.commonDAO.deleteAll(list);
		}
	}

	/**
	 * 删除某个排期
	 * 
	 * @param year
	 * @param brand
	 * @param week
	 */
	public void deleteTX(String planId)
	{
		TechTrainPlan plan = (TechTrainPlan) this.commonDAO.get(planId, TechTrainPlan.class);
		this.commonDAO.delete(plan);
	}

	/**
	 * 查询排期计划
	 * 
	 * @param week
	 * @param year
	 * @param brand
	 * @param proType
	 * @param courseId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public TechTrainPlan get(String id)
	{
		return (TechTrainPlan) this.commonDAO.get(id, TechTrainPlan.class);
	}

	/**
	 * 查询排期计划
	 * 
	 * @param week
	 * @param year
	 * @param brand
	 * @param proType
	 * @param courseId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TechTrainPlan> get(Integer week, String year, String brand, String courseId)
	{
		return this.commonDAO.list("from TechTrainPlan t where planWeek=? and yearValue=? and brand=? and trainId=?", week, year, brand, courseId);

	}

	/**
	 * 获取培训课程，附带有课程模块
	 * 
	 * @param id
	 * @return
	 */
	public TechTrainCourse getTrainCourseWithItems(String id)
	{
		TechTrainCourse course = (TechTrainCourse) this.commonDAO.get(id, TechTrainCourse.class);

		List<TechTrainCourseItem> items = this.commonDAO.list("from TechTrainCourseItem t where t.trainCourseId = ?", id);

		course.setItems(items);
		return course;
	}

	/**
	 * 保存或更新课程与模块<br />
	 * 数据库中没有的模块直接增加，有的则更新，如数据库中有，但修改后的对象中 没有则设置其状态为删除status=0
	 * 
	 * @param course
	 */
	public AjaxResponse saveTechTrainCourseTX(TechTrainCourse course, List<TechTrainCourseItem> items)
	{
		if (StringUtils.isBlank(course.getId()))
		{
			this.commonDAO.save(course);
			for (TechTrainCourseItem item : course.getItems())
			{
				this.commonDAO.save(item);
			}
		}
		else
		{
			TechTrainCourse dto = this.getTrainCourseWithItems(course.getId());
			// nb的课程模块
			// 1、比较所有模块，找出需要删除的模块
			List<TechTrainCourseItem> toUpdate = new ArrayList<TechTrainCourseItem>();
			List<TechTrainCourseItem> toInsert = new ArrayList<TechTrainCourseItem>();
			List<TechTrainCourseItem> toDelete = new ArrayList<TechTrainCourseItem>();

			for (TechTrainCourseItem item : items)
			{// 循环所有提交过来的模块
				if (StringUtils.isBlank(item.getId()))
				{
					toInsert.add(item);
				}
				else
				{
					// 移除数据库记录中所有的在提交数据中包含了的模块，剩余的就是数据库中有、提交的数据中没有的，就是删除。
					TechTrainCourseItem dtoItem = dto.getItemMap().remove(item.getId());

					if (dtoItem != null)
					{// 数据库中包含现在提交的对象，更新
						dtoItem.setName(item.getName());
						toUpdate.add(dtoItem);
					}
				}
			}
			toDelete.addAll(dto.getItemMap().values());

			// 查询是否有习题引用了待删除的模块
			if (toDelete.size() > 0)
			{
				StringBuilder sb = new StringBuilder();
				int i = 0;
				for (TechTrainCourseItem item : toDelete)
				{
					i++;
					sb.append("'").append(item.getId()).append("'");
					if (i != toDelete.size())
					{
						sb.append(",");
					}
				}

				List<TechTestDb> testDb = this.commonDAO.list("from TechTestDb t where t.trainCourseId=? and t.moduleId in (" + sb.toString() + ")", course.getId());

				if (testDb.size() > 0)
				{
					return new AjaxResponse(false, I18N.parse("i18n.traincourse.msg.deleteUsedModule"));
				}
			}

			// 更新属性
			dto.setBrand(course.getBrand());
			dto.setName(course.getName());
			dto.setType(course.getType());
			dto.setProType(course.getProType());
			dto.setStatus(course.getStatus());
			dto.setDays(course.getDays());
			this.commonDAO.update(dto);

			// 模块部分
			this.commonDAO.deleteAll(toDelete);
			this.commonDAO.save(toInsert);
			this.commonDAO.update(toUpdate);

		}

		return new AjaxResponse();
	}
}
