package com.ecannetwork.tech.controller.bean.testactive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ecannetwork.dto.tech.TechTestActivePoint;

/**
 * 用于编辑和显示评分报表的辅助类，一个row表示对一个维度的多个观察员的评分
 * 
 * @author liulibin
 * 
 */
public class Row
{
	// 维度域值
	private String dimensionId;

	// 观察员编号作为key,得分
	private Map<String, TechTestActivePoint> points = new HashMap<String, TechTestActivePoint>();

	// 子维度
	private List<Row> childs = new ArrayList<Row>();

	/**
	 * 增加打分
	 * 
	 * @param ps
	 */
	public void addPoints(List<TechTestActivePoint> ps)
	{
		for (TechTestActivePoint p : ps)
		{
			points.put(p.getWatchUserId(), p);
		}
	}

	/**
	 * 获取多个观察人的平均评分
	 * 
	 * @return
	 */
	public Double getWatchMensAvgPoint()
	{
		Double total = new Double(0);
		if (points.size() == 0)
			return total;

		for (TechTestActivePoint p : points.values())
		{
			total += p.getPoints();
		}

		return total / points.size();
	}

	/**
	 * 获取一个观察人的多个子项的平均得分
	 * 
	 * @return
	 */
	public Double getWatchMenAvgPoint(String watchMenId)
	{
		Double total = new Double(0);

		int count = 0;
		for (Row row : childs)
		{
			TechTestActivePoint p = row.getPoints().get(watchMenId);

			if (p != null)
			{// 没打分的不记录平均分
				total += row.points.get(watchMenId).getPoints();
				count++;
			}
		}

		if (count > 0)
			return total / count;
		else
			return null;
	}

	/**
	 * 间距
	 * 
	 * @return
	 */
	public int getStp()
	{
		int min = 100000;
		int max = 0;

		for (TechTestActivePoint p : points.values())
		{
			if (p.getPoints().intValue() < min)
			{
				min = p.getPoints().intValue();
			}

			if (p.getPoints().intValue() > max)
			{
				max = p.getPoints().intValue();
			}
		}

		return max - min;
	}

	public String getDimensionId()
	{
		return dimensionId;
	}

	public void setDimensionId(String dimensionId)
	{
		this.dimensionId = dimensionId;
	}

	public Map<String, TechTestActivePoint> getPoints()
	{
		return points;
	}

	public void setPoints(Map<String, TechTestActivePoint> points)
	{
		this.points = points;
	}

	public List<Row> getChilds()
	{
		return childs;
	}

	public void setChilds(List<Row> childs)
	{
		this.childs = childs;
	}

}
