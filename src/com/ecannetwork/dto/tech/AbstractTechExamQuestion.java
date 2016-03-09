package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;



/**
 * AbstractTechExamQuestion entity provides the base persistence definition of the TechExamQuestion entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechExamQuestion  extends DtoSupport {


    // Fields    

     private String id;
     private String title;
     private String type;
     private Integer flag;
     private String mainId;
     private String eid;


    // Constructors

    /** default constructor */
    public AbstractTechExamQuestion() {
    }

    
    /** full constructor */
    public AbstractTechExamQuestion(String title, String type, Integer flag, String mainId) {
        this.title = title;
        this.type = type;
        this.flag = flag;
        this.mainId = mainId;
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


    public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public Integer getFlag() {
        return this.flag;
    }
    
    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getMainId() {
        return this.mainId;
    }
    
    
    
    public String getEid() {
		return getId();
	}


	public void setMainId(String mainId) {
        this.mainId = mainId;
    }


	@Override
	public String toString() {
		return "AbstractTechExamQuestion [flag=" + flag + ", id=" + id
				+ ", mainId=" + mainId + ", title=" + title + ", type=" + type
				+ "]";
	}
   
}