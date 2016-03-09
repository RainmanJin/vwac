package com.ecannetwork.dto.tech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TechResourseManager entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechResourseManager extends AbstractTechResourseManager implements
		java.io.Serializable
{

	// Constructors

	/** default constructor */
	public TechResourseManager()
	{
	}

	private boolean hasUser;

	public boolean isHasUser()
	{
		return hasUser;
	}

	public void setHasUser(boolean hasUser)
	{
		this.hasUser = hasUser;
	}

	/** full constructor */
	public TechResourseManager(String resName, String resType, Float resSum,
			String resRemark, Float idleSum, String resStatus, String status)
	{
		super(resName, resType, resSum, resRemark, idleSum, resStatus, status);
	}

	// 周使用记录
	private Map<Integer, Float> weekUsedMap = new HashMap<Integer, Float>();

	private Map<Integer, List<TechTrainPlan>> weekPlanMap = new HashMap<Integer, List<TechTrainPlan>>();

	public Map<Integer, Float> getWeekUsedMap()
	{
		return weekUsedMap;
	}

	public void setWeekUsedMap(Map<Integer, Float> weekUsedMap)
	{
		this.weekUsedMap = weekUsedMap;
	}

	public Map<Integer, List<TechTrainPlan>> getWeekPlanMap()
	{
		return weekPlanMap;
	}

	public void setWeekPlanMap(Map<Integer, List<TechTrainPlan>> weekPlanMap)
	{
		this.weekPlanMap = weekPlanMap;
	}

	public void addWeekPlan(TechTrainPlan plan)
	{
		List<TechTrainPlan> list = this.weekPlanMap.get(plan.getPlanWeek());
		if (list == null)
		{
			list = new ArrayList<TechTrainPlan>();
			this.weekPlanMap.put(plan.getPlanWeek(), list);
		}
		list.add(plan);
	}
}
