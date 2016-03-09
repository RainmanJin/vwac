package com.ecannetwork.dto.tech;

import java.util.Date;

public class GetresultfieldHT implements java.io.Serializable {
	
    private String id;
    private String chaptertype;
    private String name;
    private String type;
    private String color;
    private String parentid;
    private String parentid2;
    private String a1;
    private String a2;
    private String a3;
    private String a4;
    
    public GetresultfieldHT() {

	}

	public GetresultfieldHT(String id, String chaptertype, String name,
			String type, String color, String parentid, String parentid2,
			String a1, String a2,String a3,String a4) {
		this.id = id;
		this.chaptertype=chaptertype;
		this.name=name;
		this.type=type;
		this.color=color;
		this.parentid=parentid;
		this.parentid2=parentid2;
		this.a1=a1;
		this.a2=a2;
		this.a3=a3;
		this.a4=a4;
	}

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getChaptertype(){
		return this.chaptertype;
	}
	
	public void setChaptertype(String chaptertype){
		this.chaptertype=chaptertype;
	}
	
    public String getName(){
    	return this.name;
    }
    
    public void setName(String name){
    this.name=name;	
    }
    
    public String getType(){
    	return this.type;
    }
    
    public void setType(String type){
    	this.type=type;
    }
    
    public String getColor(){
    	return this.color;
    }
    
    public void setColor(String color){
    	this.color=color;
    }
    
    public String getParentid(){
    	return this.parentid;
    }
    
    public void setParentid(String parentid){
    	this.parentid=parentid;
    }
    
    public String getParentid2(){
    	return this.parentid2;
    }
    
    public void setParentid2(String parentid2){
    	this.parentid2=parentid2;
    }
    
    public String getA1(){
    	return this.a1;
    }
    
    public void setA1(String a1){
    	this.a1=a1;
    }
    
    public String getA2(){
    	return this.a2;
    }
    
    public void setA2(String a2){
    	this.a2=a2;
    }
    
    public String getA3(){
    	return this.a3;
    }
    
    public void setA3(String a3){
    	this.a3=a3;
    }
    
    public String getA4(){
    	return this.a4;
    }
    
    public void setA4(String a4){
    	this.a4=a4;
    }
}
