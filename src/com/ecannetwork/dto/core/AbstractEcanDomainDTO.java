package com.ecannetwork.dto.core;

import com.ecannetwork.core.module.db.dto.DtoSupport;

public abstract class AbstractEcanDomainDTO extends DtoSupport
{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String canmodify;
	private String remark;

	public AbstractEcanDomainDTO()
	{
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getCanmodify()
	{
		return this.canmodify;
	}

	public void setCanmodify(String canmodify)
	{
		this.canmodify = canmodify;
	}

	public String getRemark()
	{
		return this.remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

}