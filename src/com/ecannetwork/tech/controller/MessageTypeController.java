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
import com.ecannetwork.core.util.UUID;
import com.ecannetwork.dto.tech.TechMessageType;

/**
 * @author 李伟
 * 2015-8-6下午4:26:58
 */
@Controller
@RequestMapping("messtype")
public class MessageTypeController {

	@Autowired
	private CommonService commonService;
	@RequestMapping("index")
	public String index(Model model){
		List<TechMessageType> list= this.commonService.list("from TechMessageType t order by t.lastUpdateTime desc");
		model.addAttribute("list", list);
		return "tech/messagetype/index";
	}
	@RequestMapping("view")
	public String view(Model model,
			@RequestParam(value = "id", required = false) String id
              ){
		TechMessageType tmt=null;
		if (StringUtils.isNotBlank(id))
		{
		tmt=(TechMessageType) this.commonService.get(id, TechMessageType.class);
		}
		if (tmt==null)
		{
			tmt = new TechMessageType();
		}
		model.addAttribute("dto", tmt);
		return "tech/messagetype/view";
	}
	@RequestMapping("save")
	public @ResponseBody
	AjaxResponse save(Model model,
			@RequestParam("typeName") String typeName,
			@RequestParam("typeCode") String typeCode,
			@RequestParam("status") String status,
			@RequestParam(value = "id", required = false) String id
			){
		TechMessageType tmt=null;
		if (StringUtils.isNotBlank(id))
		{
		tmt=(TechMessageType) this.commonService.get(id, TechMessageType.class);
		}
		if (tmt==null)
		{
			tmt = new TechMessageType();
		}
		tmt.setTypeName(typeName);
		tmt.setTypeCode(typeCode);
		tmt.setStatus(status);
		tmt.setLastUpdateTime(new Date());
		this.commonService.saveOrUpdateTX(tmt);
		return new AjaxResponse();
	}
	@RequestMapping("del")
	public @ResponseBody
	AjaxResponse del( @RequestParam("id") String id){
		commonService.deleteTX(TechMessageType.class, id);
		return new AjaxResponse();
	}
}
