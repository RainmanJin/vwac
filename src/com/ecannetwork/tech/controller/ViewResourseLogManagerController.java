package com.ecannetwork.tech.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecannetwork.core.module.controller.DateBindController;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.dto.tech.TechResourseLog;

/**
 * 查看资源管理变更日志：
 * 
 * @author think
 * 
 */
@Controller
@RequestMapping("/viewresourselogmanager")
public class ViewResourseLogManagerController extends DateBindController
{
	@Autowired
	private CommonService commonService;

	/**
	 * 查看资源列表
	 * 
	 * @param model
	 * @return
	 */
	@SuppressWarnings( { "unused", "unchecked" })
	@RequestMapping("index")
	public String listResourse(Model model)
	{

		List<TechResourseLog> resourseList = new ArrayList<TechResourseLog>();
		resourseList = commonService.list(
		        "from TechResourseLog t order by t.insertDate desc", null);
		// resourseList = commonService.list(TechResourseLog.class);
		model.addAttribute("resourseList", resourseList);
		return "tech/viewresourselog/index";
	}

	@RequestMapping("view")
	public String view(@RequestParam("id")
	String id, Model model)
	{
		TechResourseLog log = new TechResourseLog();
		log = (TechResourseLog) commonService.get(id, TechResourseLog.class);
		model.addAttribute("dto", log);
		return "tech/viewresourselog/view";
	}
}
