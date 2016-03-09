package com.ecannetwork.dto.core;

import org.extremecomponents.table.cell.FilterOption;

import com.ecannetwork.core.module.db.dto.DtoSupport;

public abstract class AbstractEcanDomainvalueDTO extends DtoSupport implements
		FilterOption
{
	private static final long serialVersionUID = 1L;

	private String id;
	private String domainlabel;
	private String domainId;
	private String domainvalue;
	private Long indexsequnce;
	private String domainLevel;
	private String isDelete;
	
	public String getDomainLevel()
	{
		return this.domainLevel;
	}

	public void setDomainLevel(String domainLevel)
	{
		this.domainLevel = domainLevel;
	}

	public Long getIndexsequnce()
	{
		return this.indexsequnce;
	}

	public void setIndexsequnce(Long indexsequnce)
	{
		this.indexsequnce = indexsequnce;
	}

	public AbstractEcanDomainvalueDTO()
	{
	}

	public AbstractEcanDomainvalueDTO(String id)
	{
		setId(id);
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getDomainId()
	{
		return domainId;
	}

	public void setDomainId(String domainId)
	{
		this.domainId = domainId;
	}


	public String getDomainlabel()
	{
		return this.domainlabel;
	}

	public void setDomainlabel(String domainlabel)
	{
		this.domainlabel = domainlabel;
	}

	public String getDomainvalue()
	{
		return this.domainvalue;
	}

	public void setDomainvalue(String domainvalue)
	{
		this.domainvalue = domainvalue;
	}

	public String getIsDelete()
	{
		return isDelete;
	}

	public void setIsDelete(String isDelete)
	{
		this.isDelete = isDelete;
	}
}