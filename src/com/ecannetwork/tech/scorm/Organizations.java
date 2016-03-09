package com.ecannetwork.tech.scorm;

import java.util.ArrayList;
import java.util.List;

public class Organizations
{
	private Organization defaultOrgnization;
	private List<Organization> orgnizations = new ArrayList<Organization>();

	public Organization getDefaultOrgnization()
	{
		return defaultOrgnization;
	}

	public void setDefaultOrgnization(Organization defaultOrgnization)
	{
		this.defaultOrgnization = defaultOrgnization;
	}

	public List<Organization> getOrgnizations()
	{
		return orgnizations;
	}

	public void setOrgnizations(List<Organization> orgnizations)
	{
		this.orgnizations = orgnizations;
	}

	/**
	 * 课件标题
	 * 
	 * @return
	 */
	public String getTitle()
	{
		if (this.getDefaultOrgnization() != null)
			return this.getDefaultOrgnization().getTitle();
		return "";
	}
}
