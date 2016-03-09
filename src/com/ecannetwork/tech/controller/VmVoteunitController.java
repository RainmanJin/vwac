package com.ecannetwork.tech.controller;

import java.util.List;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.i18n.I18N;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.dto.tech.MwVotesystem;
import com.ecannetwork.dto.tech.MwVoteunit;
import com.ecannetwork.dto.tech.TechTrainPlanUser;
import com.ecannetwork.tech.service.TestDbService;

@Controller
@RequestMapping("/vwsurveymakevote")
public class VmVoteunitController
{
	@Autowired
	private CommonService commonService;
	@Autowired
	private TestDbService dbService;

	@RequestMapping("voteunit")
	public String index(Model model,@RequestParam(value = "voteId", required=true) Integer voteId){
		@SuppressWarnings("unchecked")
		List<MwVoteunit> list  = commonService.list("from MwVoteunit t where t.NSysId=?", voteId);
		model.addAttribute("dblist", list);
		model.addAttribute("voteId", voteId);

		return "tech/vwsurveyvoteunit/index";
	}
	
	@RequestMapping("voteunitview")
	public String view(Model model, 
			@RequestParam(value = "id", defaultValue="-1")String id,
			@RequestParam(value = "voteId", required = false)String voteId){
		MwVoteunit mwVoteunit = (MwVoteunit) commonService.get(id, MwVoteunit.class);
		model.addAttribute("mwVoteunit", mwVoteunit);
		model.addAttribute("voteId", voteId);
		return "tech/vwsurveyvoteunit/view";
	}
	
	@RequestMapping("voteunitsave")
	public @ResponseBody
	AjaxResponse save(Model model, MwVoteunit mwVoteunit){
		if (mwVoteunit.getId() == null || mwVoteunit.getId() == ""){
			commonService.saveTX(mwVoteunit);// 保存
			return new AjaxResponse(true, I18N.parse("i18n.commit.success"));
		}else{
			commonService.updateTX(mwVoteunit);// 更新
			return new AjaxResponse(true, I18N.parse("i18n.commit.success"));
		}
	}
	
}
