package com.ecannetwork.dto.tech;

import java.util.Date;

//import sun.misc.JavaAWTAccess;

public class MwArticle extends AbsctractArticle implements java.io.Serializable {
	
	private String createTimeDesc;
	
	
	

	public String getCreateTimeDesc() {
		return createTimeDesc;
	}

	public void setCreateTimeDesc(String createTimeDesc) {
		this.createTimeDesc = createTimeDesc;
	}

	public MwArticle() {

	}
	
	public MwArticle(String id,String title,String typeId,String shareUsers,String contents,String filePath,int sort,int audit,int recoveryState,String aid,Date updateDate,Date createDate,String fj){
		super(id,title,typeId,shareUsers,contents,filePath,sort,audit,recoveryState,aid,updateDate,createDate,fj);
	}
}
