package com.ecannetwork.dto.tech;

public class SubjectUnit
{
	private Integer N_SubId;
	private Integer N_SysId;
	private Integer subid;
	private String C_SubTitle;
	private Integer Parentid;
	private Integer N_Need;
	
	public Integer getN_SubId()
	{
		return N_SubId;
	}
	public void setN_SubId(Integer n_SubId)
	{
		N_SubId = n_SubId;
	}
	public Integer getN_SysId()
	{
		return N_SysId;
	}
	public void setN_SysId(Integer n_SysId)
	{
		N_SysId = n_SysId;
	}
	public Integer getSubid()
	{
		return subid;
	}
	public void setSubid(Integer subid)
	{
		this.subid = subid;
	}
	public String getC_SubTitle()
	{
		return C_SubTitle;
	}
	public void setC_SubTitle(String c_SubTitle)
	{
		C_SubTitle = c_SubTitle;
	}
	public Integer getParentid()
	{
		return Parentid;
	}
	public void setParentid(Integer parentid)
	{
		Parentid = parentid;
	}
	public Integer getN_Need()
	{
		return N_Need;
	}
	public void setN_Need(Integer n_Need)
	{
		N_Need = n_Need;
	}
}
