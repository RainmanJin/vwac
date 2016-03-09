package com.ecannetwork.tech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.dto.tech.MwServicecost;
import com.ecannetwork.dto.tech.MwVotecourse;
import com.ecannetwork.tech.service.TestDbService;

//服务费用计算
@Controller
@RequestMapping("/servicecost")
public class ServiceCostController
{
	@Autowired
	private CommonService commonService;
	@Autowired
	private TestDbService dbService;

	@RequestMapping("index")
	public String index(Model model)
	{
		List<MwServicecost> list = commonService.list("from MwServicecost t order by id desc");// 列表
		model.addAttribute("dblist", list);

		return "tech/servicecost/index";
	}
	
	@RequestMapping("del")
	public @ResponseBody
	AjaxResponse del(Model model, @RequestParam(value = "id", required = true) String id)
	{
		MwServicecost mwServicecost = (MwServicecost) commonService.get(id, MwServicecost.class);
		if (mwServicecost != null)
		{
			commonService.deleteTX(MwServicecost.class, id);
			return new AjaxResponse(true, "i18n.commit.del.success");
		}
		return new AjaxResponse(true, "i18n.commit.del.success");
	}
}
