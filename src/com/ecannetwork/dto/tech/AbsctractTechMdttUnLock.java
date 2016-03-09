package com.ecannetwork.dto.tech;

import java.util.Date;

import com.ecannetwork.core.module.db.dto.DtoSupport;

public abstract class AbsctractTechMdttUnLock extends DtoSupport
{
	private String id;
	private String pkgID;
	private String menuID;
	private Date updateTime;
	private String planID;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getPkgID()
	{
		return pkgID;
	}

	public void setPkgID(String pkgID)
	{
		this.pkgID = pkgID;
	}

	public String getMenuID()
	{
		return menuID;
	}

	public void setMenuID(String menuID)
	{
		this.menuID = menuID;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	public String getPlanID()
	{
		return planID;
	}

	public void setPlanID(String planID)
	{
		this.planID = planID;
	}
}
