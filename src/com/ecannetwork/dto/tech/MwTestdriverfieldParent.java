package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * MwTestdriverfield generated by hbm2java 拓展MwTestdriverfield，parentName用于生成和显示父级名称
 */
public class MwTestdriverfieldParent extends MwTestdriverfield
{
	private String parentName;

	public MwTestdriverfieldParent()
	{
	}

	public MwTestdriverfieldParent(String parentName)
	{
		this.parentName = parentName;
	}

	public String getParentName()
	{
		return parentName;
	}

	public void setParentName(String parentName)
	{
		this.parentName = parentName;
	}
}
