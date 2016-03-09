package com.ecannetwork.dto.tech;

import java.util.Date;


/**
 * AbstractTechTestingInstance entity provides the base persistence definition of the TechTestingInstance entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechTestingInstance extends com.ecannetwork.core.module.db.dto.DtoSupport implements java.io.Serializable {


    // Fields    

     private String id;
     private String studentId;
     private String courseInstanceId;
     private String courseId;
     private Double testingPoint;
     private Date testingTime;
     private String isFinish;


    // Constructors

    /** default constructor */
    public AbstractTechTestingInstance() {
    }

    
    /** full constructor */
    public AbstractTechTestingInstance(String studentId, String courseInstanceId, String courseId, Double testingPoint, Date testingTime, String isFinish) {
        this.studentId = studentId;
        this.courseInstanceId = courseInstanceId;
        this.courseId = courseId;
        this.testingPoint = testingPoint;
        this.testingTime = testingTime;
        this.isFinish = isFinish;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return this.studentId;
    }
    
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseInstanceId() {
        return this.courseInstanceId;
    }
    
    public void setCourseInstanceId(String courseInstanceId) {
        this.courseInstanceId = courseInstanceId;
    }

    public String getCourseId() {
        return this.courseId;
    }
    
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Double getTestingPoint() {
        return this.testingPoint;
    }
    
    public void setTestingPoint(Double testingPoint) {
        this.testingPoint = testingPoint;
    }

    public Date getTestingTime() {
        return this.testingTime;
    }
    
    public void setTestingTime(Date testingTime) {
        this.testingTime = testingTime;
    }

    public String getIsFinish() {
        return this.isFinish;
    }
    
    public void setIsFinish(String isFinish) {
        this.isFinish = isFinish;
    }
   








}