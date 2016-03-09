package com.ecannetwork.dto.tech;


/**
 * TechTestUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechTestUser extends AbstractTechTestUser implements
		java.io.Serializable
{
	public static class Status
	{
		public static final String TO_TEST = "totest";
		public static final String TESTED = "tested";
		public static final String SUBMITTED = "submitted";
	}

	// Constructors

	/** default constructor */
	public TechTestUser()
	{
	}

	/** full constructor */
	public TechTestUser(String userId, String userType, String testActiveId,
			Double pointCn, Double pointDe, String comment, Double points,
			String isSubmit)
	{
		super(userId, userType, testActiveId, pointCn, pointDe, comment,
				points, isSubmit);
	}

	private String name;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * 获取状态
	 * 
	 * @return
	 */
	public String getStatus()
	{
		if (this.getPointCn() == null)
		{// 待测试
			return Status.TO_TEST;
		} else
		{
			if (this.getPoints() == null)
			{// 未设置得分
				return Status.TESTED;
			} else
			{// 已设置得分
				return Status.SUBMITTED;
			}
		}
	}
}
