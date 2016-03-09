package com.ecannetwork.dto.tech;

import java.util.Date;

import com.ecannetwork.core.module.db.dto.DtoSupport;

public abstract class AbsctractAppPlatForm extends DtoSupport implements
		java.io.Serializable {
	private String id;
	private String version;
	private String pkgid;
	private String name;
	private String apkurl;
	private String iosurl;
	private String brand;
	private String proType;
	private Date lastUpdateTime;
	private String status;
	private String trianPlanId;
	private String remark;
	private String iconUrl;
	private String valid;
	private String pkgType;
	private Integer versionCode;
	private String fileSize;

	public AbsctractAppPlatForm()
	{
	}

	public AbsctractAppPlatForm(String id,String version,String pkgid, String name, String apkurl,
			String iosurl, String brand, String proType, Date lastUpdateTime,
			String status, String trianPlanId, String remark, String iconUrl,
			String valid, String pkgType, Integer versionCode, String fileSize)
	{
		this.id=id;
		this.version=version;
		this.pkgid = pkgid;
		this.name = name;
		this.apkurl = apkurl;
		this.iosurl = iosurl;
		this.brand = brand;
		this.proType = proType;
		this.lastUpdateTime = lastUpdateTime;
		this.status = status;
		this.trianPlanId = trianPlanId;
		this.remark = remark;
		this.iconUrl = iconUrl;
		this.valid = valid;
		this.pkgType = pkgType;
		this.versionCode = versionCode;
		this.fileSize = fileSize;
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getVersion()
	{
		return this.version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getPkgid()
	{
		return this.pkgid;
	}

	public void setPkgid(String pkgid)
	{
		this.pkgid = pkgid;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getApkurl()
	{
		return this.apkurl;
	}

	public void setApkurl(String apkurl)
	{
		this.apkurl = apkurl;
	}

	public String getIosurl()
	{
		return this.iosurl;
	}

	public void setIosurl(String iosurl)
	{
		this.iosurl = iosurl;
	}

	public String getBrand()
	{
		return this.brand;
	}

	public void setBrand(String brand)
	{
		this.brand = brand;
	}

	public String getProType()
	{
		return this.proType;
	}

	public void setProType(String proType)
	{
		this.proType = proType;
	}

	public Date getLastUpdateTime()
	{
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime)
	{
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getStatus()
	{
		return this.status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getTrianPlanId()
	{
		return this.trianPlanId;
	}

	public void setTrianPlanId(String trianPlanId)
	{
		this.trianPlanId = trianPlanId;
	}

	public String getRemark()
	{
		return this.remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getIconUrl()
	{
		return this.iconUrl;
	}

	public void setIconUrl(String iconUrl)
	{
		this.iconUrl = iconUrl;
	}

	public String getValid()
	{
		return this.valid;
	}

	public void setValid(String valid)
	{
		this.valid = valid;
	}

	public String getPkgType()
	{
		return this.pkgType;
	}

	public void setPkgType(String pkgType)
	{
		this.pkgType = pkgType;
	}

	public Integer getVersionCode()
	{
		return this.versionCode;
	}

	public void setVersionCode(Integer versionCode)
	{
		this.versionCode = versionCode;
	}

	public String getFileSize()
	{
		return this.fileSize;
	}

	public void setFileSize(String fileSize)
	{
		this.fileSize = fileSize;
	}
}
