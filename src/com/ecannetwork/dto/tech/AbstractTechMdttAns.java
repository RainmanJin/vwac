package com.ecannetwork.dto.tech;

import java.util.Date;

import com.ecannetwork.core.module.db.dto.DtoSupport;

public abstract class AbstractTechMdttAns extends DtoSupport
{
	private String id;
	private String opt;
	private String pkgID;
	private String questionID;
	private String userID;
	private String menuID;
	private Date ansTime;

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

	public String getQuestionID()
	{
		return questionID;
	}

	public void setQuestionID(String questionID)
	{
		this.questionID = questionID;
	}

	public String getUserID()
	{
		return userID;
	}

	public void setUserID(String userID)
	{
		this.userID = userID;
	}

	public Date getAnsTime()
	{
		return ansTime;
	}

	public void setAnsTime(Date ansTime)
	{
		this.ansTime = ansTime;
	}

	public String getOpt()
	{
		return opt;
	}

	public void setOpt(String opt)
	{
		this.opt = opt;
	}

	public String getMenuID()
	{
		return menuID;
	}

	public void setMenuID(String menuID)
	{
		this.menuID = menuID;
	}

}
