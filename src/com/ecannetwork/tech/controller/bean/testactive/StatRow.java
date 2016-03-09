package com.ecannetwork.tech.controller.bean.testactive;

import java.util.HashMap;
import java.util.Map;

import com.ecannetwork.tech.util.TechConsts;

public class StatRow
{
	/**
	 * 维度名称
	 */
	private String dimName;
	
	private Integer idx;
	

	/**
	 * 标准指标
	 */
	private Double pointIndex;

	/**
	 * 阶段、观察员信息<br />
	 * Map<阶段编号,Map<观察员编号, 观察员平均分>>
	 */
	private Map<String, Map<String, Double>> points = new HashMap<String, Map<String, Double>>();

	/**
	 * ptp得分
	 */
	private Double ptpPoint;

	/**
	 * 获取某个观察员的某个维度的
	 * 
	 * @param stepLevelOne
	 * @param watchMen
	 * @return
	 */
	public Double getPoint(String stepLevelOne, String watchMen)
	{
		Map<String, Double> sd = points.get(stepLevelOne);
		if (sd != null)
		{
			return sd.get(watchMen);
		}

		return null;
	}

	/**
	 * 是否有观察员分数大于2的间距
	 * 
	 * @return
	 */
	public boolean isWatchMenStep2()
	{
		for (String key : points.keySet())
		{
			Map<String, Double> map = points.get(key);
			Double min = Double.MAX_VALUE;
			Double max = Double.MIN_VALUE;

			for (Double d : map.values())
			{
				if (d < min)
				{
					min = d;
				}

				if (d > max)
				{
					max = d;
				}
			}

			return max - min > 2;
		}

		return false;
	}

	/**
	 * 增加一个得分
	 * 
	 * @param stepLevelOne
	 * @param watchMen
	 * @param point
	 */
	public void addPoints(String stepLevelOne, String watchMen, Double point)
	{
		if (stepLevelOne.equals(TechConsts.PTP))
		{
			ptpPoint = point;
		} else
		{
			Map<String, Double> sd = points.get(stepLevelOne);
			if (sd == null)
			{
				sd = new HashMap<String, Double>();
				points.put(stepLevelOne, sd);
			}

			sd.put(watchMen, point);
		}
	}

	/**
	 * ptp与非ptp28分配算法
	 * 
	 * @return
	 */
	public Double getAvg28()
	{
		int c = 0;
		Double total = new Double(0);

		for (Map<String, Double> step : points.values())
		{
			for (Double d : step.values())
			{
				c++;
				total += d;
			}
		}

		if (ptpPoint == null)
		{
			if (c > 0)
			{
				return total / c;
			} else
			{
				return null;
			}
		} else
		{
			Double ptp6 = ptpPoint / 20 + 1;
			if (c > 0)
			{
				return total / c * 0.8 + ptp6 * 0.2;
			} else
			{
				return ptp6;
			}
		}
	}

	public String getDimName()
	{
		return dimName;
	}

	public void setDimName(String dimName)
	{
		this.dimName = dimName;
	}

	public Map<String, Map<String, Double>> getPoints()
	{
		return points;
	}

	public void setPoints(Map<String, Map<String, Double>> points)
	{
		this.points = points;
	}

	public Double getPtpPoint()
	{
		return ptpPoint;
	}

	public void setPtpPoint(Double ptpPoint)
	{
		this.ptpPoint = ptpPoint;
	}

	public Double getPointIndex()
	{
		if(pointIndex == null)
			this.pointIndex = new Double(0);
		return pointIndex;
	}

	public void setPointIndex(Double pointIndex)
	{
		this.pointIndex = pointIndex;
	}

	/**
	 * 指标间距
	 * 
	 * @return
	 */
	public Double getAbsToIndex()
	{
		if (this.pointIndex != null)
		{
			Double avg28 = this.getAvg28();
			if (avg28 != null)
			{
				return Math.abs(this.pointIndex - this.getAvg28());
			}
		}
		return null;
	}

	public Integer getIdx()
	{
		return idx;
	}

	public void setIdx(Integer idx)
	{
		this.idx = idx;
	}
	
	
}
