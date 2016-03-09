package com.ecannetwork.dto.tech;

import java.util.Date;

import com.ecannetwork.core.module.db.dto.DtoSupport;

public class MwSktChapter extends DtoSupport implements java.io.Serializable {
  
  private String id;
  
  private Integer cid;
  
  private Integer sort;
  
  private String CName;
  
  private Integer type;
  
  private String content;
  
  private Date CreateTime;
  
  private Integer islock;
  
  public MwSktChapter() {
    
  }
  
  public MwSktChapter(String id, Integer cId, Integer sort, String CName, Integer c_Type,
      String c_Content, Date createTime, Integer islock) {
    super();
    this.id = id;
    cid = cId;
    this.sort = sort;
    this.CName = CName;
    type = c_Type;
    content = c_Content;
    CreateTime = createTime;
    this.islock = islock;
  }
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public Integer getCid() {
    return cid;
  }
  
  public void setCid(Integer cId) {
    cid = cId;
  }
  
  public Integer getSort() {
    return sort;
  }
  
  public void setSort(Integer sort) {
    this.sort = sort;
  }
  
  public String getCName() {
    return CName;
  }
  
  public void setCName(String CName) {
    this.CName = CName;
  }
  
  public Integer getType() {
    return type;
  }
  
  public void setType(Integer c_Type) {
    type = c_Type;
  }
  
  public String getContent() {
    return content;
  }
  
  public void setContent(String c_Content) {
    content = c_Content;
  }
  
  public Date getCreateTime() {
    return CreateTime;
  }
  
  public void setCreateTime(Date createTime) {
    CreateTime = createTime;
  }
  
  public Integer getIslock() {
    return islock;
  }
  
  public void setIslock(Integer isLock) {
    islock = isLock;
  }
}
