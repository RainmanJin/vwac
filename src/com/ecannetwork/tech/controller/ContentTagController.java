package com.ecannetwork.tech.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import com.ecannetwork.dto.tech.TechChildrenTag;
import com.ecannetwork.dto.tech.TechContentTag;

/**
 * @author 李伟
 * 2015-8-10下午3:59:54
 */
@Controller
@RequestMapping("contenttag")
public class ContentTagController {

	@Autowired
	private CommonService commonService;
	@RequestMapping("index")
	public String indext(Model model){
		List<TechContentTag> mainlist = (List<TechContentTag>)commonService.list("from TechContentTag ");
		List<Object[]> temliST = (List<Object[]>)commonService.list("from TechChildrenTag t join t.mainTag m ");
		List<TechContentTag> resultlist= new ArrayList<TechContentTag>();
		for(TechContentTag main: mainlist){
			for(Object[] ob:temliST){
				TechContentTag cmain = (TechContentTag)ob[1];
				TechChildrenTag child = (TechChildrenTag)ob[0];
				if(main.getId().equals(cmain.getId())){
					main.getTct().add(child);
				}
			}
			resultlist.add(main);
		}
		model.addAttribute("list",resultlist);
		return "tech/contenttag/index";
	}
	@RequestMapping("view")
	public String view(Model model,
			@RequestParam(value = "id", required = false) String id
			){
		TechContentTag maint = (TechContentTag) this.commonService.get(id, TechContentTag.class);
		List<Object[]> temliST = (List<Object[]>)commonService.list("from TechChildrenTag t join t.mainTag m where m.id=?",id);
			for(Object[] ob:temliST){
				TechContentTag cmain = (TechContentTag)ob[1];
				TechChildrenTag child = (TechChildrenTag)ob[0];
				if(maint.getId().equals(cmain.getId())){
					maint.getTct().add(child);
				}
			}
			model.addAttribute("dto",maint);
		return "tech/contenttag/view";
		
	}
	@RequestMapping("selchildTag")
	public String 
	listchildTagds(Model model,
			@RequestParam(value = "id", required = false) String id
			){
		//List<TechChildrenTag> childs = this.commonService.list("from TechChildrenTag t where t.mainId=?",id);
		TechChildrenTag child =  (TechChildrenTag) this.commonService.get(id, TechChildrenTag.class);
		model.addAttribute("dto", child);
		return "tech/contenttag/selectchilds";
		
	}
	@RequestMapping("addmain")
	public String addmian(Model model){
		
		return "tech/contenttag/addmain";
	}
	@RequestMapping("savemain")
	public @ResponseBody 
	AjaxResponse savemain(Model model,
			@RequestParam("tagName") String tagName,
			@RequestParam(value = "id", required = false) String id
			){
		TechContentTag tmt = null;
		if (StringUtils.isNotBlank(id))
		{
			tmt = (TechContentTag) this.commonService.get(id, TechContentTag.class);
		}
		if (tmt == null)
		{
			tmt = new TechContentTag();
		}
	   
	    tmt.setLastUpdateTime(new Date());
		//
	   
	    AjaxResponse result = new AjaxResponse();
	    if(tagName.equals(tmt.getTagName())){
	    	
	    }else if(checkTagExist(tagName,0)){
	    	result.setData("标签重复");
		    result.setSuccess(false);
	    }else{
	    	 tmt.setTagName(tagName);
	    	this.commonService.saveOrUpdateTX(tmt);
	    }
		return result;
		
	}
	@RequestMapping("chgchild")
	public @ResponseBody 
	AjaxResponse chgchild(Model model,
			@RequestParam(value = "id", required = false) String id,
			 @RequestParam("mainId") String mainId,
			 @RequestParam("ctagName") String ctagName
			){
		try {
			ctagName=new String((ctagName.getBytes("ISO-8859-1")),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TechChildrenTag tctc = null;
		if (StringUtils.isNotBlank(id))
		{
			tctc = (TechChildrenTag) this.commonService.get(id, TechChildrenTag.class);
		}
		if (tctc == null)
		{
			tctc = new TechChildrenTag();
			tctc.setMainId(mainId);
		}
		//tctc.setMainId();
		
		AjaxResponse result = new AjaxResponse();
	    
		if(ctagName.equals(tctc.getCtagName())){
	    	
	    }else if(checkTagExist(ctagName,1)){
	    	result.setData("标签重复");
		    result.setSuccess(false);
	    }else{
	    	tctc.setCtagName(ctagName);
	    	this.commonService.saveOrUpdateTX(tctc);
	    }
		return result;
	}
	@RequestMapping("delchilds")
	public @ResponseBody
	AjaxResponse delchilds(
			 @RequestParam("id") String id,
			 @RequestParam("cid") String cid
			){
		System.out.println("==========================================");
		 List<TechChildrenTag> childtags = this.commonService
					.list("from TechChildrenTag t where t.id=? and t.mainId=?",
							cid, id);
	        if(childtags !=null){
	        	this.commonService.deleteAllTX(childtags);
	        }
			return new AjaxResponse();
		
	}
	@RequestMapping("delmainTag")
	public @ResponseBody 
	AjaxResponse delmainTag(@RequestParam("id") String id){
		commonService.deleteTX(TechContentTag.class, id);
		return new AjaxResponse();
	}
	
	private boolean checkTagExist(String tagName,int level){
		List<Object> tagList = null;
		if(level==0){
			tagList = commonService.list("from TechContentTag where tagName = ?", tagName);
		}else if(level==1){
			tagList = commonService.list("from TechChildrenTag where ctagName = ?", tagName);
		}
		return tagList.size()==0?false:true;
	}
}
