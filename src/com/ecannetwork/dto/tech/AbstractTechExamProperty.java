package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;



/**
 * AbstractTechExamProperty entity provides the base persistence definition of the TechExamProperty entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechExamProperty  extends DtoSupport {


    // Fields    

     private String id;
     private Integer score=-1;
     private Integer leftCount;
     private String userid;
     private String mainId;


    // Constructors

    /** default constructor */
    public AbstractTechExamProperty() {
    }

    
    /** full constructor */
    public AbstractTechExamProperty(Integer score, Integer leftCount, String userid, String mainId) {
        this.score = score;
        this.leftCount = leftCount;
        this.userid = userid;
        this.mainId = mainId;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public Integer getScore() {
        return this.score;
    }
    
    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getLeftCount() {
        return this.leftCount;
    }
    
    public void setLeftCount(Integer leftCount) {
        this.leftCount = leftCount;
    }

    public String getUserid() {
        return this.userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMainId() {
        return this.mainId;
    }
    
    public void setMainId(String mainId) {
        this.mainId = mainId;
    }
   








}