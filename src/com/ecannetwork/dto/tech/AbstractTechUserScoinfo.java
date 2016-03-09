package com.ecannetwork.dto.tech;

/**
 * AbstractTechUserScoinfo entity provides the base persistence definition of
 * the TechUserScoinfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechUserScoinfo extends
		com.ecannetwork.core.module.db.dto.DtoSupport implements java.io.Serializable {

	// Fields

	private String id;
	private String userId;
	private String courseId;
	private String scoid;
	private String launch;
	private String parameterstring;
	private String TStatus;
	private String prerequisites;
	private String TExit;
	private String entry;
	private String masteryscore;
	private String idx;
	private String TType;

	// Constructors

	/** default constructor */
	public AbstractTechUserScoinfo() {
	}

	/** full constructor */
	public AbstractTechUserScoinfo(String userId, String courseId,
			String scoid, String launch, String parameterstring,
			String TStatus, String prerequisites, String TExit, String entry,
			String masteryscore, String idx, String TType) {
		this.userId = userId;
		this.courseId = courseId;
		this.scoid = scoid;
		this.launch = launch;
		this.parameterstring = parameterstring;
		this.TStatus = TStatus;
		this.prerequisites = prerequisites;
		this.TExit = TExit;
		this.entry = entry;
		this.masteryscore = masteryscore;
		this.idx = idx;
		this.TType = TType;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCourseId() {
		return this.courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getScoid() {
		return this.scoid;
	}

	public void setScoid(String scoid) {
		this.scoid = scoid;
	}

	public String getLaunch() {
		return this.launch;
	}

	public void setLaunch(String launch) {
		this.launch = launch;
	}

	public String getParameterstring() {
		return this.parameterstring;
	}

	public void setParameterstring(String parameterstring) {
		this.parameterstring = parameterstring;
	}

	public String getTStatus() {
		return this.TStatus;
	}

	public void setTStatus(String TStatus) {
		this.TStatus = TStatus;
	}

	public String getPrerequisites() {
		return this.prerequisites;
	}

	public void setPrerequisites(String prerequisites) {
		this.prerequisites = prerequisites;
	}

	public String getTExit() {
		return this.TExit;
	}

	public void setTExit(String TExit) {
		this.TExit = TExit;
	}

	public String getEntry() {
		return this.entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public String getMasteryscore() {
		return this.masteryscore;
	}

	public void setMasteryscore(String masteryscore) {
		this.masteryscore = masteryscore;
	}

	public String getIdx() {
		return this.idx;
	}

	public void setIdx(String idx) {
		this.idx = idx;
	}

	public String getTType() {
		return this.TType;
	}

	public void setTType(String TType) {
		this.TType = TType;
	}

}