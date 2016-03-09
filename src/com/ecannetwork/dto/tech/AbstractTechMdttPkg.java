package com.ecannetwork.dto.tech;

import java.util.Date;

import com.ecannetwork.core.module.db.dto.DtoSupport;

public abstract class AbstractTechMdttPkg extends DtoSupport
{
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String version;
	private String filePath;
	private String previewPath;
	private String jsonMenu;
	private String brand;
	private String proType;
	private Date lastUpdateTime;
	private String status;
	private String trianPlanID;
	private String conentType;// class room(CP) or SSP
	private String remark;
	private String iconURL;
	private String valid;// 是否有效课件
	private String pkgType;// 包的类型，是PDF还是SCORM
	private String fileSize;
	private Integer versionCode;
	private String fixedName;
	private String motorcycle;
	private String icon1URL;
	private String icon2URL;
	private String osType;
	
	public String getmotorcycle()
	{
		return motorcycle;
	}

	public void setMotorcycle(String motorcycle)
	{
		this.motorcycle = motorcycle;
	}
	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getFilePath()
	{
		return filePath;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	public String getPreviewPath()
	{
		return previewPath;
	}

	public void setPreviewPath(String previewPath)
	{
		this.previewPath = previewPath;
	}

	public String getJsonMenu()
	{
		return jsonMenu;
	}

	public void setJsonMenu(String jsonMenu)
	{
		this.jsonMenu = jsonMenu;
	}

	public String getBrand()
	{
		return brand;
	}

	public void setBrand(String brand)
	{
		this.brand = brand;
	}

	public Date getLastUpdateTime()
	{
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime)
	{
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getTrianPlanID()
	{
		return trianPlanID;
	}

	public void setTrianPlanID(String trianPlanID)
	{
		this.trianPlanID = trianPlanID;
	}

	public String getProType()
	{
		return proType;
	}

	public void setProType(String proType)
	{
		this.proType = proType;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getIconURL()
	{
		return iconURL;
	}

	public void setIconURL(String iconURL)
	{
		this.iconURL = iconURL;
	}

	public String getValid()
	{
		return valid;
	}

	public void setValid(String valid)
	{
		this.valid = valid;
	}

	public String getConentType()
	{
		return conentType;
	}

	public void setConentType(String conentType)
	{
		this.conentType = conentType;
	}

	public String getPkgType()
	{
		return pkgType;
	}

	public void setPkgType(String pkgType)
	{
		this.pkgType = pkgType;
	}

	public Integer getVersionCode()
	{
		return versionCode;
	}

	public void setVersionCode(Integer versionCode)
	{
		this.versionCode = versionCode;
	}

	public String getFileSize()
	{
		return fileSize;
	}

	public void setFileSize(String fileSize)
	{
		this.fileSize = fileSize;
	}

	public String getFixedName()
	{
		return fixedName;
	}

	public void setFixedName(String fixedName)
	{
		this.fixedName = fixedName;
	}
	//20141202 新增 手机缩略图，课件所属版本
	public String getosType()
	{
		return osType;
	}

	public void setosType(String osType)
	{
		this.osType = osType;
	}
	public String getIcon1URL()
	{
		return icon1URL;
	}

	public void setIcon1URL(String iconurl)
	{
		this.icon1URL = iconurl;
	}
	public String getIcon2URL()
	{
		return icon2URL;
	}

	public void setIcon2URL(String iconurl)
	{
		this.icon2URL = iconurl;
	}
}
