package com.ecannetwork.dto.tech;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.ecannetwork.core.module.db.dto.DtoSupport;

public abstract class AbsctractArticle extends DtoSupport implements
		java.io.Serializable {
	private String id;
	private String title;
	private String typeId;
	private String shareUsers;
	private String contents;
	private String filePath;
	private Integer sort;
	private Integer audit;
	private Integer recoveryState;
	private String aid;
	private Date updateTime;
	/*@DateTimeFormat(pattern="yyyy-MM-dd")*/
	private Date createTime;
	private String fj;

	public AbsctractArticle() {

	}

	public AbsctractArticle(String id, String title, String typeId,
			String shareUsers, String contents, String filePath, Integer sort,
			Integer audit, Integer recoveryState, String aid, Date updateTime,
			Date createTime, String fj) {
		this.id = id;
		this.title = title;
		this.typeId = typeId;
		this.shareUsers = shareUsers;
		this.contents = contents;
		this.filePath = filePath;
		this.sort = sort;
		this.audit = audit;
		this.recoveryState = recoveryState;
		this.aid = aid;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.fj = fj;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTypeId() {
		return this.typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getShareUsers() {
		return this.shareUsers;
	}

	public void setShareUsers(String shareUsers) {
		this.shareUsers = shareUsers;
	}

	public String getContents() {
		return this.contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public int getAudit() {
		return this.audit;
	}

	public void setAudit(Integer audit) {
		this.audit = audit;
	}

	public int getRecoveryState() {
		return this.recoveryState;
	}

	public void setRecoveryState(Integer recoveryState) {
		this.recoveryState = recoveryState;
	}

	public String getAid() {
		return this.aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getTime(){
		SimpleDateFormat sdFormat=new SimpleDateFormat("yyyy-MM-dd");
		return sdFormat.format(createTime);
	}

	public String getFj() {
		return this.fj;
	}

	public void setFj(String fj) {
		this.fj = fj;
	}
}
