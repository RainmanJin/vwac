package com.ecannetwork.dto.tech;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * TechTestActive entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechTestActive extends AbstractTechTestActive implements
		java.io.Serializable
{

	public static class STATUS
	{
		public static final String EDIT = "edit";
		public static final String READY = "ready";
		public static final String RUNNING = "run";
		public static final String FIN = "fin";
	}

	// Constructors

	/** default constructor */
	public TechTestActive()
	{
	}

	/** full constructor */
	public TechTestActive(String name, Date creatTime, String proType,
			String brand, String status, String hosterId, String mainManId,
			Integer testTimelimit, Integer testCount)
	{
		super(name, creatTime, proType, brand, status, hosterId, mainManId,
				testTimelimit, testCount);
	}

	// 观察员
	private Set<String> watchMenIds = new HashSet<String>();
	// 候选人
	private Set<String> userids = new HashSet<String>();

	private List<TechTestUser> users = new ArrayList<TechTestUser>();
	
	/**
	 * 重写该方法， 将观察人分割成Set
	 */
	public void setWatchMens(String watchMens)
	{
		super.setWatchMens(watchMens);

		if (StringUtils.isNotBlank(watchMens))
		{
			String ids[] = watchMens.split(",");
			for (String id : ids)
			{
				watchMenIds.add(id);
			}
		}
	}

	public Set<String> getWatchMenIds()
	{
		return watchMenIds;
	}

	public void setWatchMenIds(Set<String> watchMenIds)
	{
		this.watchMenIds = watchMenIds;
	}

	/**
	 * 设定候选人
	 * 
	 * @param users
	 */
	public void setUserids(List<TechTestUser> users)
	{
		this.users = users;
		
		for (TechTestUser user : users)
		{
			this.userids.add(user.getUserId());
		}
	}
	

	/**
	 * 获取所有的候选人
	 * 
	 * @return
	 */
	public Set<String> getUserids()
	{
		return this.userids;
	}
	
	public List<TechTestUser> getUsers()
	{
		return this.users;
	}
}
