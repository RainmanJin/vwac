package com.ecannetwork.dto.tech;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.extremecomponents.table.cell.FilterOption;

/**
 * TechTrainCourse entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechTrainCourse extends AbstractTechTrainCourse implements
		java.io.Serializable, FilterOption
{

	// Constructors

	/** default constructor */
	public TechTrainCourse()
	{
	}

	/** full constructor */
	public TechTrainCourse(String name, String proType, String brand,
			String status)
	{
		super(name, proType, brand, status);
	}
	/** full constructor */
	public TechTrainCourse(String name, String proType, String brand,
			String status,String motorcycle)
	{
		super(name, proType, brand, status,motorcycle);
	}

	/**
	 * 排期的周
	 */
	private Map<Integer, List<TechTrainPlan>> planWeeks = new HashMap<Integer, List<TechTrainPlan>>();

	public void setTrainPlans(List<TechTrainPlan> list)
	{
		if (list == null)
			return;

		for (TechTrainPlan plan : list)
		{
			List<TechTrainPlan> plans = planWeeks.get(plan.getPlanWeek());
			if (plans == null)
			{
				plans = new ArrayList<TechTrainPlan>();
				planWeeks.put(plan.getPlanWeek(), plans);
			}
			if (!plan.getStatus().equals(TechTrainPlan.Status.draft))
			{
				plans.add(plan);
			}
		}
	}

	/**
	 * 是否有计划
	 * 
	 * @param week
	 *            周编号
	 * @return
	 */
	public boolean isPlaned(Integer week)
	{
		List<TechTrainPlan> plans = planWeeks.get(week);
		if (plans != null && plans.size() > 0)
			return true;
		else
			return false;
	}

	/**
	 * 获取当前课程某一周的培训计划
	 * 
	 * @param week
	 * @return
	 */
	public TechTrainPlan getPlan(Integer week)
	{
		List<TechTrainPlan> plans = planWeeks.get(week);
		if (plans != null && plans.size() > 0)
			return plans.get(0);
		else
			return null;
	}

	public String getPlanRemarksForXls(Integer week)
	{
		List<TechTrainPlan> plans = planWeeks.get(week);

		if (plans != null && plans.size() > 0)
		{
			StringBuilder sb = new StringBuilder();
			for (TechTrainPlan plan : plans)
			{
				sb.append("【").append(plan.getPlanDays()).append("】")
						.append(":").append(plan.getRemark()).append("\r\n");
			}
			return sb.toString();
		} else
		{
			return null;
		}

	}

	public String getPlanRemarks(Integer week)
	{
		List<TechTrainPlan> plans = planWeeks.get(week);

		if (plans != null && plans.size() > 0)
		{
			StringBuilder sb = new StringBuilder();
			for (TechTrainPlan plan : plans)
			{
				sb.append("<b>").append(plan.getPlanDays()).append("</b>")
						.append(":").append(plan.getRemark()).append("<hr />");
			}
			return sb.toString();
		} else
		{
			return null;
		}

	}

	@Override
	public Object getLabel()
	{
		return this.getName();
	}

	@Override
	public Object getValue()
	{
		return this.getId();
	}

	// 模块列表
	private Map<String, TechTrainCourseItem> items = new HashMap<String, TechTrainCourseItem>();

	public Map<String, TechTrainCourseItem> getItemMap()
	{
		return this.items;
	}

	public Collection<TechTrainCourseItem> getItems()
	{
		return items.values();
	}

	public void setItems(List<TechTrainCourseItem> items)
	{
		for (TechTrainCourseItem item : items)
		{
			this.items.put(item.getId(), item);
		}
	}
}
