package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;

public class MwSktItem extends DtoSupport implements java.io.Serializable {
  
  private String id;
  
  private Integer cid;
  
  private Integer chapterid;
  
  private Integer sort;
  
  private String cname;
  
  private Integer cdimension;
  
  private Integer ctype;
  
  private Integer ccolor;
  
  private Integer parentid;
  
  private Integer parentid2;
  
  private String colpath;
  
  private String colpath2;
  
  private Integer a1;
  
  private Integer a2;
  
  private String a3;
  
  private String a4;
  
  public MwSktItem() {
  }
  
  public MwSktItem(String id, Integer cid, Integer chapterid, Integer sort, String cname,
      Integer cdimension, Integer ctype, Integer ccolor, Integer parentid, Integer parentid2,
      String colpath, String colpath2, Integer a1, Integer a2, String a3, String a4) {
    super();
    this.id = id;
    this.cid = cid;
    this.chapterid = chapterid;
    this.sort = sort;
    this.cname = cname;
    this.cdimension = cdimension;
    this.ctype = ctype;
    this.ccolor = ccolor;
    this.parentid = parentid;
    this.parentid2 = parentid2;
    this.colpath = colpath;
    this.colpath2 = colpath2;
    this.a1 = a1;
    this.a2 = a2;
    this.a3 = a3;
    this.a4 = a4;
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
  
  public void setCid(Integer cid) {
    this.cid = cid;
  }
  
  public Integer getChapterid() {
    return chapterid;
  }
  
  public void setChapterid(Integer chapterid) {
    this.chapterid = chapterid;
  }
  
  public Integer getSort() {
    return sort;
  }
  
  public void setSort(Integer sort) {
    this.sort = sort;
  }
  
  public String getCname() {
    return cname;
  }
  
  public void setCname(String cname) {
    this.cname = cname;
  }
  
  public Integer getCdimension() {
    return cdimension;
  }
  
  public void setCdimension(Integer cdimension) {
    this.cdimension = cdimension;
  }
  
  public Integer getCtype() {
    return ctype;
  }
  
  public void setCtype(Integer ctype) {
    this.ctype = ctype;
  }
  
  public Integer getCcolor() {
    return ccolor;
  }
  
  public void setCcolor(Integer ccolor) {
    this.ccolor = ccolor;
  }
  
  public Integer getParentid() {
    return parentid;
  }
  
  public void setParentid(Integer parentid) {
    this.parentid = parentid;
  }
  
  public Integer getParentid2() {
    return parentid2;
  }
  
  public void setParentid2(Integer parentid2) {
    this.parentid2 = parentid2;
  }
  
  public String getColpath() {
    return colpath;
  }
  
  public void setColpath(String colpath) {
    this.colpath = colpath;
  }
  
  public String getColpath2() {
    return colpath2;
  }
  
  public void setColpath2(String colpath2) {
    this.colpath2 = colpath2;
  }
  
  public Integer getA1() {
    return a1;
  }
  
  public void setA1(Integer a1) {
    this.a1 = a1;
  }
  
  public Integer getA2() {
    return a2;
  }
  
  public void setA2(Integer a2) {
    this.a2 = a2;
  }
  
  public String getA3() {
    return a3;
  }
  
  public void setA3(String a3) {
    this.a3 = a3;
  }
  
  public String getA4() {
    return a4;
  }
  
  public void setA4(String a4) {
    this.a4 = a4;
  }
  
}
