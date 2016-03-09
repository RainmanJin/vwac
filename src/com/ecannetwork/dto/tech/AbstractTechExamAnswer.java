package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;



/**
 * AbstractTechExamAnswer entity provides the base persistence definition of the TechExamAnswer entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechExamAnswer  extends DtoSupport {


    // Fields    

     private String id;
     private String title;
     private String isRight;
     private String idx;
     private String quesId;
     private String index;
     private String option;
     
     private String is_right;


    // Constructors

    /** default constructor */
    public AbstractTechExamAnswer() {
    }

    
    /** full constructor */
    public AbstractTechExamAnswer(String title, String isRight, String idx, String quesId) {
        this.title = title;
        this.isRight = isRight;
        this.idx = idx;
        this.quesId = quesId;
    }

   
    // Property accessors

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

    public String getIsRight() {
		return isRight;
	}


	public void setIsRight(String isRight) {
		this.isRight = isRight;
	}


	public String getIdx() {
		return idx;
	}


	public void setIdx(String idx) {
		this.idx = idx;
	}


	public String getQuesId() {
        return this.quesId;
    }
    
    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }
    
    


	public String getIndex() {
		return getIdx();
	}


	public void setIndex(String index) {
		this.index = index;
	}

	

	public String getOption() {
		return getTitle();
	}


	public void setOption(String option) {
		this.option = option;
	}
	
	


	public String getIs_right() {
		return getIsRight();
	}


	@Override
	public String toString() {
		return "AbstractTechExamAnswer [id=" + id + ", idx=" + idx
				+ ", isRight=" + isRight + ", quesId=" + quesId + ", title="
				+ title + "]";
	}
   








}