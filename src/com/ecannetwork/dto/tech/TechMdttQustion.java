package com.ecannetwork.dto.tech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TechMdttQustion extends AbstractTechMdttQustion
{
	private static final long serialVersionUID = 1L;

	public static final class QustionType
	{
		public static final String CHECKBOX = "1";
		public static final String RADIO = "2";
		public static final String INPUT = "3";

	}

	// 选项列表
	private Map<String, TechMdttOption> options = new HashMap<String, TechMdttOption>();
	// 主观题的答案清单
	private List<TechMdttAns> inputAnsList = new ArrayList<TechMdttAns>();

	// 总共的答题数
	private Integer ansCount = 0;

	public Map<String, TechMdttOption> getOptions()
	{
		return options;
	}

	public void setOptions(Map<String, TechMdttOption> options)
	{
		this.options = options;
	}

	public void addAns(TechMdttAns ans)
	{
		if (this.getQusType().equals(TechMdttQustion.QustionType.INPUT))
		{
			inputAnsList.add(ans);
		} else
		{
			TechMdttOption opt = options.get(ans.getOpt());
			if (opt != null)
			{
				opt.getAnsList().add(ans);
				ansCount++;
			}
		}
	}

	public Integer getAnsCount()
	{
		return ansCount;
	}

	public void setAnsCount(Integer ansCount)
	{
		this.ansCount = ansCount;
	}

	public List<TechMdttAns> getInputAnsList()
	{
		return inputAnsList;
	}

	public void setInputAnsList(List<TechMdttAns> inputAnsList)
	{
		this.inputAnsList = inputAnsList;
	}
}
