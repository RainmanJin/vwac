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
import com.ecannetwork.dto.tech.MwVotekey;
import com.ecannetwork.dto.tech.MwVotesubject;
import com.ecannetwork.dto.tech.MwVotesystem;
import com.ecannetwork.dto.tech.MwVoteunit;
import com.ecannetwork.dto.tech.TechTrainPlanUser;
import com.ecannetwork.tech.service.TestDbService;

@Controller
@RequestMapping("/vwsurveymakevote")
public class VwVotesubjectController
{
	@Autowired
	private CommonService commonService;
	@Autowired
	private TestDbService dbService;

	@RequestMapping("votesubject")
	public String index(Model model,@RequestParam(value = "sysId", required=true) Integer sysId){
		@SuppressWarnings("unchecked")
		List<MwVotesubject> list  = commonService.list("from MwVotesubject t where t.NSysId=?", sysId);
		model.addAttribute("dblist", list);
		model.addAttribute("sysId", sysId);

		return "tech/vwsurveysubject/index";
	}
	
	@RequestMapping("votesubjectview")
	public String view(Model model, 
			@RequestParam(value = "id", defaultValue="-1")String id,
			@RequestParam(value = "sysId", required = false)String sysId){
		MwVotesubject mwVotesubject = (MwVotesubject) commonService.get(id, MwVotesubject.class);
		model.addAttribute("mwVotesubject", mwVotesubject);
		model.addAttribute("sysId", sysId);
		return "tech/vwsurveysubject/view";
	}
	
	@RequestMapping("votesubjectsave")
	public @ResponseBody
	AjaxResponse save(Model model, MwVotesubject mwVotesubject){
		if (mwVotesubject.getId() == null || mwVotesubject.getId() == ""){
			commonService.saveTX(mwVotesubject);// 保存
			return new AjaxResponse(true, I18N.parse("i18n.commit.success"));
		}else{
			commonService.updateTX(mwVotesubject);// 更新
			return new AjaxResponse(true, I18N.parse("i18n.commit.success"));
		}
	}
	
}
