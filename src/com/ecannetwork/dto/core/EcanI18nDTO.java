package com.ecannetwork.dto.core;

import com.ecannetwork.core.module.db.dto.DtoSupport;

/**
 * <pre>
 * +--------+--------------+------+-----+---------+-------+
 * | Field  | Type         | Null | Key | Default | Extra |
 * +--------+--------------+------+-----+---------+-------+
 * | ID     | varchar(20)  | NO   | PRI | NULL    |       |
 * | NAME   | varchar(100) | NO   |     | NULL    |       |
 * | CONTENT| text         | NO   |     | NULL    |       |
 * | STATUS | varchar(10)  | NO   |     | NULL    |       |
 * | REMARK | varchar(40)  | NO   |     | NULL    |       |
 * +--------+--------------+------+-----+---------+-------+
 * 
 * </pre>
 * 
 * @author leo
 * 
 */
public class EcanI18nDTO extends DtoSupport
{
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String content;
	private String status;
	private String remark;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

}
