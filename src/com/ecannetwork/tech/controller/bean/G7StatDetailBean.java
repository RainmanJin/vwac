package com.ecannetwork.tech.controller.bean;

public class G7StatDetailBean
{
	private Double avg;
	private Integer qid;
	private Integer carid;

	public G7StatDetailBean(Double avg, Integer qid, Integer carid)
	{
		super();
		this.avg = avg;
		this.qid = qid;
		this.carid = carid;
	}

	public Double getAvg()
	{
		return avg;
	}

	public void setAvg(Double avg)
	{
		this.avg = avg;
	}

	public Integer getQid()
	{
		return qid;
	}

	public void setQid(Integer qid)
	{
		this.qid = qid;
	}

	public Integer getCarid()
	{
		return carid;
	}

	public void setCarid(Integer carid)
	{
		this.carid = carid;
	}

}
