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
import com.ecannetwork.dto.tech.MwVotesystem;
import com.ecannetwork.dto.tech.MwVoteunit;
import com.ecannetwork.dto.tech.TechTrainPlanUser;
import com.ecannetwork.tech.service.TestDbService;

@Controller
@RequestMapping("/vwsurveymakevote")
public class VmVotekeyController
{
	@Autowired
	private CommonService commonService;
	@Autowired
	private TestDbService dbService;

	@RequestMapping("votekey")
	public String index(Model model,@RequestParam(value = "subId", required=true) Integer subId){
		@SuppressWarnings("unchecked")
		List<MwVotekey> list  = commonService.list("from MwVotekey t where t.NSubId=?", subId);
		model.addAttribute("dblist", list);
		model.addAttribute("subId", subId);

		return "tech/vwsurveyvotekey/index";
	}
	
	@RequestMapping("votekeyview")
	public String view(Model model, 
			@RequestParam(value = "id", defaultValue="-1")String id,
			@RequestParam(value = "subId", required = false)String subId){
		MwVotekey mwVotekey = (MwVotekey) commonService.get(id, MwVotekey.class);
		model.addAttribute("mwVotekey", mwVotekey);
		model.addAttribute("subId", subId);
		return "tech/vwsurveyvotekey/view";
	}
	
	@RequestMapping("votekeysave")
	public @ResponseBody
	AjaxResponse save(Model model, MwVotekey mwVotekey){
		if (mwVotekey.getId() == null || mwVotekey.getId() == ""){
			commonService.saveTX(mwVotekey);// 保存
			return new AjaxResponse(true, I18N.parse("i18n.commit.success"));
		}else{
			commonService.updateTX(mwVotekey);// 更新
			return new AjaxResponse(true, I18N.parse("i18n.commit.success"));
		}
	}
	
}
