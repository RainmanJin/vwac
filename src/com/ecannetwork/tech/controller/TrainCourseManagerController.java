package com.ecannetwork.tech.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.dto.tech.TechTrainCourse;
import com.ecannetwork.dto.tech.TechTrainCourseItem;
import com.ecannetwork.tech.service.TrainPlanService;

/**
 * 培训课程管理
 * 
 * @author liulibin
 * 
 */
@Controller
@RequestMapping("triancoursemanager")
public class TrainCourseManagerController
{
	@Autowired
	private CommonService commonService;

	@Autowired
	private TrainPlanService trainPlanService;

	/**
	 * 课程管理首页， 显示所有课程列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public String index(Model model)
	{
		model.addAttribute("list", this.commonService
				.list(TechTrainCourse.class));

		return "tech/triancoursemanager/index";
	}

	/**
	 * 查看或新增课程：：：弹出框展示
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("view")
	public String view(Model model,
			@RequestParam(value = "id", required = false) String id)
	{
		if (StringUtils.isNotBlank(id))
		{
			model.addAttribute("dto", this.trainPlanService
					.getTrainCourseWithItems(id));
		}

		return "tech/triancoursemanager/view";
	}

	/**
	 * 保存培训课程
	 * 
	 * @param id
	 * @param name
	 * @param proType
	 * @param brand
	 * @param status
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("save")
	public @ResponseBody
	AjaxResponse save(@RequestParam(value = "id", required = false) String id,
			@RequestParam("name") String name,
			@RequestParam("proType") String proType,
			@RequestParam("brand") String brand,
			@RequestParam("status") String status,
			@RequestParam("days") String days,
			@RequestParam("type") String type, HttpServletRequest request)
	{
		TechTrainCourse c = new TechTrainCourse();
		if (StringUtils.isNotBlank(id))
			c.setId(id);

		c.setName(name);
		c.setType(type);
		c.setProType(proType);
		c.setBrand(brand);
		c.setStatus(status);
		c.setDays(days);

		// 模块管理部分
		List<TechTrainCourseItem> items = new ArrayList<TechTrainCourseItem>();
		Set<String> names = request.getParameterMap().keySet();
		for (String key : names)
		{
			if (key.startsWith("mds_"))
			{// 是课程模块

				if (key.length() > 4)
				{// 编辑的

					String value = request.getParameter(key);
					if (StringUtils.isNotBlank(value))
					{
						TechTrainCourseItem item = new TechTrainCourseItem();
						item.setTrainCourseId(id);
						item.setName(value);

						String k[] = key.split("_");
						if (k.length == 2 && StringUtils.isNotBlank(k[1]))
						{// 模块编号
							item.setId(k[1]);
						}
						items.add(item);
					}
				} else
				{
					// 新增的, do nth
					String[] vs = request.getParameterValues(key);
					if (vs != null)
					{
						for (String value : vs)
						{
							if (StringUtils.isNotBlank(value))
							{
								TechTrainCourseItem item = new TechTrainCourseItem();
								item.setTrainCourseId(id);
								item.setName(value);
								items.add(item);
							}
						}
					}
				}

			}
		}

		return this.trainPlanService.saveTechTrainCourseTX(c, items);
	}

	/**
	 * 删除用户：：：已有开始评测或关联测评信息的用户不能删除
	 * 
	 * @param model
	 * @param id
	 *            用户编号
	 * @return
	 */
	@RequestMapping("del")
	public @ResponseBody
	AjaxResponse delete(Model model, @RequestParam("id") String id)
	{

		return this.trainPlanService.delCourseTX(id);
	}

	/**
	 * 停用课程
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("disable")
	public @ResponseBody
	AjaxResponse disable(Model model, @RequestParam("id") String id)
	{
		return this.trainPlanService.disableCouseTX(id);
	}

	/**
	 * 启用
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("active")
	public @ResponseBody
	AjaxResponse active(Model model, @RequestParam("id") String id)
	{
		return this.trainPlanService.activeCourseTX(id);
	}
}
