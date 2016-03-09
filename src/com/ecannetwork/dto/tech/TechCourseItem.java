package com.ecannetwork.dto.tech;

/**
 * TechCourseItem entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechCourseItem extends AbstractTechCourseItem implements
		java.io.Serializable
{

	// Constructors

	/** default constructor */
	public TechCourseItem()
	{
	}

	/** full constructor */
	public TechCourseItem(String courseId, String indentify, String type,
			String title, String lanunch, String parameterstring,
			String datafromlms, String prerequisites, String masteryscore,
			String maxtimeallowed, String timelimitaction, String idx,
			Integer thelevel)
	{
		super(courseId, indentify, type, title, lanunch, parameterstring,
				datafromlms, prerequisites, masteryscore, maxtimeallowed,
				timelimitaction, idx, thelevel);
	}

	public String getIndexName()
	{
		StringBuilder sb = new StringBuilder();

		String temp = this.getIdx();
		while(temp.length()>0)
		{
			String level = temp.substring(0,3);
			if(level.equals("000"))
			{
				break;
			}
			sb.append(Integer.valueOf(level) -100+1).append(".");
			temp = temp.substring(3);
		}
		
		return sb.toString();
	}
	public static void main(String[] args)
	{
		TechCourseItem item = new TechCourseItem();
		item.setIdx("100100101000000");
		System.out.println(item.getIndexName());
	}
}
