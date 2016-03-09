package com.ecannetwork.tech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.dto.tech.TechTestActiveStandard;

@Component
public class TestActiveIndexStandardFacade
{
	@Autowired
	private CommonService commonService;

	/**
	 * 专业课
	 * 
	 * @return
	 */
	public TechTestActiveStandard getZhuanYeKe(String proType)
	{
		TechTestActiveStandard t = (TechTestActiveStandard) this.commonService
				.get(TechTestActiveStandard.Type.ZHUAN_YE_KE + proType,
						TechTestActiveStandard.class);
		if (t == null)
		{
			t = new TechTestActiveStandard();
			t.setLevelOne(75d);
			t.setLevelTwo(80d);
		}
		return t;
	}

	/**
	 * 教学法
	 * 
	 * @return
	 */
	public TechTestActiveStandard getJiaoXueFa()
	{
		return (TechTestActiveStandard) this.commonService.get(
				TechTestActiveStandard.Type.JIAO_XUE_FA,
				TechTestActiveStandard.class);
	}

}
