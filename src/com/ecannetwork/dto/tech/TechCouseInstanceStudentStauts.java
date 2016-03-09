package com.ecannetwork.dto.tech;


/**
 * TechCouseInstance entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechCouseInstanceStudentStauts implements java.io.Serializable
{

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
	// Constructors
	private TechCouseInstance courseInstance;
	private TechStudentStatus student;

	/** default constructor */
	public TechCouseInstanceStudentStauts()
	{
	}

	public TechCouseInstanceStudentStauts(TechCouseInstance courseInstance,
	        TechStudentStatus student)
	{
		this.courseInstance = courseInstance;
		this.student = student;
	}

	public TechCouseInstance getCourseInstance()
    {
    	return courseInstance;
    }

	public void setCourseInstance(TechCouseInstance courseInstance)
    {
    	this.courseInstance = courseInstance;
    }

	public TechStudentStatus getStudent()
    {
    	return student;
    }

	public void setStudent(TechStudentStatus student)
    {
    	this.student = student;
    }

}
