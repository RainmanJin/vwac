package com.ecannetwork.dto.tech;

import java.util.Date;


/**
 * AbstractTechTestingInstanceRecord entity provides the base persistence definition of the TechTestingInstanceRecord entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechTestingInstanceRecord extends com.ecannetwork.core.module.db.dto.DtoSupport implements java.io.Serializable {


    // Fields    

     private String id;
     private String courseId;
     private Double points;
     private Date beginTime;
     private Date endTime;


    // Constructors

    /** default constructor */
    public AbstractTechTestingInstanceRecord() {
    }

    
    /** full constructor */
    public AbstractTechTestingInstanceRecord(String courseId, Double points, Date beginTime, Date endTime) {
        this.courseId = courseId;
        this.points = points;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return this.courseId;
    }
    
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Double getPoints() {
        return this.points;
    }
    
    public void setPoints(Double points) {
        this.points = points;
    }

    public Date getBeginTime() {
        return this.beginTime;
    }
    
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
   








}