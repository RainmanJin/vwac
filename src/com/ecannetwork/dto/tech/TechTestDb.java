package com.ecannetwork.dto.tech;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TechTestDb entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechTestDb extends AbstractTechTestDb implements java.io.Serializable
{

	// Constructors

	/** default constructor */
	public TechTestDb()
	{
	}

	/** full constructor */
	public TechTestDb(String title, String status, Date createTime, String createUserId, String answerType, String proType, String trainCourseId, String brand, List<TechTestAnswer> list)
	{
		super(title, status, createTime, createUserId, answerType, proType, trainCourseId, brand, list);
	}

	// 答案
	private List<TechTestAnswer> ans = new ArrayList<TechTestAnswer>();

	public List<TechTestAnswer> getAns()
	{
		return ans;
	}

	public void setAns(List<TechTestAnswer> ans)
	{
		this.ans = ans;
	}

	/**
	 * 随机排序数，抽取测评试卷时使用
	 */
	private Integer randomIdx;

	public Integer getRandomIdx()
	{
		return randomIdx;
	}

	public void setRandomIdx(Integer randomIdx)
	{
		this.randomIdx = randomIdx;
	}

}
