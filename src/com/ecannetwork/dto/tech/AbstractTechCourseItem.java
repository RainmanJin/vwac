package com.ecannetwork.dto.tech;

/**
 * AbstractTechCourseItem entity provides the base persistence definition of the
 * TechCourseItem entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechCourseItem extends
		com.ecannetwork.core.module.db.dto.DtoSupport implements
		java.io.Serializable
{

	// Fields

	private String id;
	private String courseId;
	private String indentify;
	private String type;
	private String title;
	private String lanunch;
	private String parameterstring;
	private String datafromlms;
	private String prerequisites;
	private String masteryscore;
	private String maxtimeallowed;
	private String timelimitaction;
	private String idx;
	private Integer thelevel;

	// Constructors

	/** default constructor */
	public AbstractTechCourseItem()
	{
	}

	/** full constructor */
	public AbstractTechCourseItem(String courseId, String indentify,
			String type, String title, String lanunch, String parameterstring,
			String datafromlms, String prerequisites, String masteryscore,
			String maxtimeallowed, String timelimitaction, String idx,
			Integer thelevel)
	{
		this.courseId = courseId;
		this.indentify = indentify;
		this.type = type;
		this.title = title;
		this.lanunch = lanunch;
		this.parameterstring = parameterstring;
		this.datafromlms = datafromlms;
		this.prerequisites = prerequisites;
		this.masteryscore = masteryscore;
		this.maxtimeallowed = maxtimeallowed;
		this.timelimitaction = timelimitaction;
		this.idx = idx;
		this.thelevel = thelevel;
	}

	// Property accessors

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getCourseId()
	{
		return this.courseId;
	}

	public void setCourseId(String courseId)
	{
		this.courseId = courseId;
	}

	public String getIndentify()
	{
		return this.indentify;
	}

	public void setIndentify(String indentify)
	{
		this.indentify = indentify;
	}

	public String getType()
	{
		return this.type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getTitle()
	{
		return this.title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getLanunch()
	{
		return this.lanunch;
	}

	public void setLanunch(String lanunch)
	{
		this.lanunch = lanunch;
	}

	public String getParameterstring()
	{
		return this.parameterstring;
	}

	public void setParameterstring(String parameterstring)
	{
		this.parameterstring = parameterstring;
	}

	public String getDatafromlms()
	{
		return this.datafromlms;
	}

	public void setDatafromlms(String datafromlms)
	{
		this.datafromlms = datafromlms;
	}

	public String getPrerequisites()
	{
		return this.prerequisites;
	}

	public void setPrerequisites(String prerequisites)
	{
		this.prerequisites = prerequisites;
	}

	public String getMasteryscore()
	{
		return this.masteryscore;
	}

	public void setMasteryscore(String masteryscore)
	{
		this.masteryscore = masteryscore;
	}

	public String getMaxtimeallowed()
	{
		return this.maxtimeallowed;
	}

	public void setMaxtimeallowed(String maxtimeallowed)
	{
		this.maxtimeallowed = maxtimeallowed;
	}

	public String getTimelimitaction()
	{
		return this.timelimitaction;
	}

	public void setTimelimitaction(String timelimitaction)
	{
		this.timelimitaction = timelimitaction;
	}

	public String getIdx()
	{
		return this.idx;
	}

	public void setIdx(String idx)
	{
		this.idx = idx;
	}

	public Integer getThelevel()
	{
		return this.thelevel;
	}

	public void setThelevel(Integer thelevel)
	{
		this.thelevel = thelevel;
	}

}