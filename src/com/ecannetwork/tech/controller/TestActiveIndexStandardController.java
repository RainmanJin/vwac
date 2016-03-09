package com.ecannetwork.tech.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.dto.tech.TechTestActiveStandard;

/**
 * 测评标准管理
 * 
 * @author leo
 * 
 */
@Controller
@RequestMapping("testactivestandard")
public class TestActiveIndexStandardController
{
	@Autowired
	private CommonService commonService;

	/**
	 * 首页
	 * 
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("index")
	public String index(Model model)
	{
		List<TechTestActiveStandard> list = this.commonService
				.list(TechTestActiveStandard.class);
		// 初始化为map

		Map map = new HashMap();

		Map<String, TechTestActiveStandard> zyk = new HashMap<String, TechTestActiveStandard>();

		for (TechTestActiveStandard standard : list)
		{
			if (standard.getId().startsWith(
					TechTestActiveStandard.Type.ZHUAN_YE_KE)
					&& standard.getId().indexOf("_") != -1)
			{
				String[] t = standard.getId().split("_");
				if (t.length == 2)
				{
					zyk.put(t[1], standard);
				}
			} else
			{
				map.put(standard.getId(), standard);
			}
		}

		model.addAttribute("jxf", map
				.get(TechTestActiveStandard.Type.JIAO_XUE_FA));
		model.addAttribute("zyk", zyk);

		return "tech/testactivestandard/index";
	}

	/**
	 * 保存指标
	 * 
	 * @param type
	 * @param levelOne
	 * @param levelTwo
	 * @return
	 */
	@RequestMapping("save")
	public @ResponseBody
	AjaxResponse save(
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "levelOne", required = false) Double levelOne,
			@RequestParam(value = "levelTwo", required = false) Double levelTwo,
			HttpServletRequest request)
	{
		if (id != null)
		{// 教学法
			TechTestActiveStandard std = (TechTestActiveStandard) this.commonService
					.get(id, TechTestActiveStandard.class);

			std.setLevelOne(levelOne);
			std.setLevelTwo(levelTwo);

			this.commonService.updateTX(std);
		} else
		{// 专业课
			String[] proTypes = request.getParameterValues("zyk");
			if (proTypes != null)
			{
				for (int i = 0; i < proTypes.length; i++)
				{
					Double one = NumberUtils.toDouble(request
							.getParameter("levelOne_" + proTypes[i]));
					Double two = NumberUtils.toDouble(request
							.getParameter("levelTwo_" + proTypes[i]));

					TechTestActiveStandard std = (TechTestActiveStandard) this.commonService
							.get(TechTestActiveStandard.Type.ZHUAN_YE_KE
									+ proTypes[i], TechTestActiveStandard.class);

					if (std != null)
					{
						std.setLevelOne(one);
						std.setLevelTwo(two);
						this.commonService.updateTX(std);
					} else
					{
						std = new TechTestActiveStandard();
						std.setId(TechTestActiveStandard.Type.ZHUAN_YE_KE
								+ proTypes[i]);
						std.setLevelOne(one);
						std.setLevelTwo(two);
						this.commonService.saveTX(std);
					}
				}
			}
		}
		return new AjaxResponse();
	}
}
