package com.ecannetwork.tech.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.i18n.I18N;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.dto.tech.MwVotecourse;
import com.ecannetwork.dto.tech.MwVotesystem;
import com.ecannetwork.dto.tech.TechTest;
import com.ecannetwork.dto.tech.TechTestDb;
import com.ecannetwork.tech.service.TestDbService;

@Controller
@RequestMapping("/vwsurveymakevote")
public class VwsurveyMakevoteController
{
	@Autowired
	private CommonService commonService;

	@RequestMapping("index")
	public String index(Model model)
	{
		@SuppressWarnings("unchecked")
		List<MwVotesystem> list = commonService.list("from MwVotesystem t order by id desc");// 列表
		model.addAttribute("dblist", list);

		return "tech/vwsurveymakevote/index";
	}
	
	@RequestMapping("view")
	public String view(Model model, @RequestParam(value = "id", defaultValue="-1")String id)
	{
		MwVotesystem mwVotesystem = (MwVotesystem) commonService.get(id, MwVotesystem.class);
		model.addAttribute("mwVotesystem", mwVotesystem);
		return "tech/vwsurveymakevote/view";
	}
	
	@RequestMapping("save")
	public @ResponseBody
	AjaxResponse save(Model model, MwVotesystem mwVotesystem)
	{
		if (mwVotesystem.getId() == null || mwVotesystem.getId() == "")
		{
			commonService.saveTX(mwVotesystem);// 保存
			return new AjaxResponse(true, I18N.parse("i18n.commit.success"));
		}
		else
		{
			commonService.updateTX(mwVotesystem);// 更新
			return new AjaxResponse(true, I18N.parse("i18n.commit.success"));
		}
	}
	
}
