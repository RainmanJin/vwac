package com.ecannetwork.dto.tech;

/**
 * TechTrainPlanResourse entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechTrainPlanResourse extends AbstractTechTrainPlanResourse
        implements java.io.Serializable
{
	public static final class PlanType
	{
		//消耗品资源
		public static final String XiaoHaoPin = "xhp";
		//常设资源
		public static final String ChangShe = "cs";
	}
	
	// Constructors

	/** default constructor */
	public TechTrainPlanResourse()
	{
	}

	/** full constructor */
	public TechTrainPlanResourse(String planType, String resId, Float resSum)
	{
		super(planType, resId, resSum);
	}
	
}
