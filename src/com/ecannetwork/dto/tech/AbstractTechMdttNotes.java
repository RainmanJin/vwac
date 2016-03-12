package com.ecannetwork.dto.tech;

import java.util.Date;

import com.ecannetwork.core.module.db.dto.DtoSupport;

public abstract class AbstractTechMdttNotes extends DtoSupport
{
	private Integer uid;
	private String pkgID;
	private String menuID;
	private String contentType;
	private String content;
	private String ownerID;
	private Date createTime;
	private String pkgimg;
	private String name;

	public String getId()
	{
		return null;
	}

	public Integer getUid()
	{
		return uid;
	}

	public void setUid(Integer uid)
	{
		this.uid = uid;
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

	public String getContentType()
	{
		return contentType;
	}

	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getOwnerID()
	{
		return ownerID;
	}

	public void setOwnerID(String ownerID)
	{
		this.ownerID = ownerID;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
	
	public String getPkgimg(){
		return this.pkgimg;
	}
	
	public void setPkgimg(String pkgimg){
		this.pkgimg=pkgimg;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name=name;
	}
}
