package com.ecannetwork.dto.tech;

import com.ecannetwork.dto.core.EcanUser;

/**
 * TechTrainPlanUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechTrainPlanUser extends AbstractTechTrainPlanUser implements
		java.io.Serializable
{
	public static class UserType
	{
		public static final String STUDENT = "student";
		public static final String TEACHER = "teacher";
	}

	// Constructors

	/** default constructor */
	public TechTrainPlanUser()
	{

	}

	/** full constructor */
	public TechTrainPlanUser(String userType, String userId)
	{
		super(userType, userId);
	}

	private EcanUser user;

	public EcanUser getUser()
	{
		return user;
	}

	public void setUser(EcanUser user)
	{
		this.user = user;
	}

}
