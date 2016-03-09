package com.ecannetwork.tech.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.CoreConsts.YORN;
import com.ecannetwork.core.util.ExecuteContext;
import com.ecannetwork.dto.tech.TechMdttQA;

/**
 * 课件包管理
 * 
 * @author leo
 * 
 */
@Controller
@RequestMapping("mdttqa")
public class MDTTQAController
{
	@Autowired
	private CommonService commonService;

	@RequestMapping("index")
	public String index(Model model)
	{
		List<TechMdttQA> list = this.commonService
				.list("from TechMdttQA t where t.status != 'D' order by t.lastUpdateTime desc");
		model.addAttribute("list", list);
		return "tech/mdttqa/index";
	}

	@RequestMapping("view")
	public String view(Model model,
			@RequestParam(value = "id", required = false) String id)
	{
		TechMdttQA qa = null;
		if (StringUtils.isNotBlank(id))
		{
			qa = (TechMdttQA) this.commonService.get(id, TechMdttQA.class);
		}

		if (qa == null)
		{
			qa = new TechMdttQA();
			qa.setLastUpdateTime(new Date());
			qa.setStatus(YORN.YES);
			qa.setUserID(ExecuteContext.user().getId());
		}

		model.addAttribute("dto", qa);

		return "tech/mdttqa/view";
	}

	@RequestMapping("save")
	public @ResponseBody
	AjaxResponse save(@RequestParam("question") String question,
			@RequestParam("brand") String brand,
			@RequestParam("proType") String proType,
			@RequestParam("ans") String ans,
			@RequestParam(value = "id", required = false) String id)
	{
		TechMdttQA qa = null;
		if (StringUtils.isNotBlank(id))
		{
			qa = (TechMdttQA) this.commonService.get(id, TechMdttQA.class);
		}

		if (qa == null)
		{
			qa = new TechMdttQA();
			qa.setLastUpdateTime(new Date());
			qa.setStatus(YORN.YES);
		}
		qa.setBrand(brand);
		qa.setProType(proType);
		qa.setQuestion(question);
		qa.setAns(ans);
		qa.setUserID(ExecuteContext.user().getId());
		qa.setVersionCode(this.newVersion());

		this.commonService.saveOrUpdateTX(qa);

		return new AjaxResponse();
	}

	private synchronized Integer newVersion()
	{
		TechMdttQA qa = (TechMdttQA) this.commonService
				.listOnlyObject("from TechMdttQA t order by t.versionCode desc");
		if (qa != null)
		{
			return qa.getVersionCode() + 1;
		} else
		{
			return 1;
		}
	}

	@RequestMapping("status")
	public @ResponseBody
	AjaxResponse status(@RequestParam("id") String id,
			@RequestParam("status") String status)
	{
		TechMdttQA qa = (TechMdttQA) this.commonService.get(id,
				TechMdttQA.class);
		if (qa != null)
		{
			qa.setStatus(status);
			qa.setVersionCode(this.newVersion());
			this.commonService.updateTX(qa);
		}

		return new AjaxResponse(true);
	}

	@RequestMapping("del")
	public @ResponseBody
	AjaxResponse del(Model model, @RequestParam("id") String id)
	{
		TechMdttQA qa = (TechMdttQA) this.commonService.get(id,
				TechMdttQA.class);
		if (qa != null)
		{
			qa.setStatus("D");
			qa.setVersionCode(this.newVersion());
			this.commonService.updateTX(qa);
		}
		return new AjaxResponse(true);
	}
}
