package com.ecannetwork.dto.tech;

import com.ecannetwork.core.module.db.dto.DtoSupport;



/**
 * AbstractTechExamMain entity provides the base persistence definition of the TechExamMain entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractTechExamMain extends DtoSupport{


    // Fields    

     private String id;
     private String title;
     private Integer timeFlag;
     private Integer showAnswer;
     private Integer type;
     private Integer singleScort;
     private Integer multiScort;
     private Integer flag;
     private Integer randomCount;
     private Integer passLevel;
     private Integer leftCount;
     
     private Integer mtype;


    // Constructors

    /** default constructor */
    public AbstractTechExamMain() {
    }

    
    /** full constructor */
    public AbstractTechExamMain(String title, Integer timeFlag, Integer showAnswer, Integer type, Integer singleScort, Integer multiScort, Integer flag, Integer leftCount) {
        this.title = title;
        this.timeFlag = timeFlag;
        this.showAnswer = showAnswer;
        this.type = type;
        this.singleScort = singleScort;
        this.multiScort = multiScort;
        this.flag = flag;
        this.leftCount = leftCount;
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

    public Integer getTimeFlag() {
        return this.timeFlag;
    }
    
    public void setTimeFlag(Integer timeFlag) {
        this.timeFlag = timeFlag;
    }

    public Integer getShowAnswer() {
        return this.showAnswer;
    }
    
    public void setShowAnswer(Integer showAnswer) {
        this.showAnswer = showAnswer;
    }

    public Integer getType() {
        return this.type;
    }
    
    
    public Integer getMtype() {
		return getType();
	}


	public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSingleScort() {
        return this.singleScort;
    }
    
    public void setSingleScort(Integer singleScort) {
        this.singleScort = singleScort;
    }

    public Integer getMultiScort() {
        return this.multiScort;
    }
    
    public void setMultiScort(Integer multiScort) {
        this.multiScort = multiScort;
    }

    public Integer getFlag() {
        return this.flag;
    }
    
    public void setFlag(Integer flag) {
        this.flag = flag;
    }


	public Integer getRandomCount() {
		return randomCount;
	}


	public void setRandomCount(Integer randomCount) {
		this.randomCount = randomCount;
	}


	public Integer getPassLevel() {
		return passLevel;
	}


	public void setPassLevel(Integer passLevel) {
		this.passLevel = passLevel;
	}


	public Integer getLeftCount() {
		return leftCount;
	}


	public void setLeftCount(Integer leftCount) {
		this.leftCount = leftCount;
	}
   
}