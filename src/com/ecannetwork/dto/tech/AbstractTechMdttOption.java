/**
 * 
 */
package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * @author leo
 * 
 */
public abstract class AbstractTechMdttOption extends DtoSupport
{
	private String id;
	private String pkgID;
	private String optID;
	private String optName;
	private String qustionID;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getQustionID()
	{
		return qustionID;
	}

	public void setQustionID(String qustionID)
	{
		this.qustionID = qustionID;
	}

	public String getOptID()
	{
		return optID;
	}

	public void setOptID(String optID)
	{
		this.optID = optID;
	}

	public String getOptName()
	{
		return optName;
	}

	public void setOptName(String optName)
	{
		this.optName = optName;
	}

	public String getPkgID()
	{
		return pkgID;
	}

	public void setPkgID(String pkgID)
	{
		this.pkgID = pkgID;
	}
}
