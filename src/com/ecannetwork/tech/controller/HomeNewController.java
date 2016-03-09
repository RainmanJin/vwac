package com.ecannetwork.tech.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.Configs;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.FileUploadHelper;
import com.ecannetwork.core.util.JsonFactory;
import com.ecannetwork.core.util.UUID;
import com.ecannetwork.dto.tech.MwArticle;

/**
 * @author 李伟 2015-8-10下午3:59:54
 */
@Controller
@RequestMapping("homenew")
public class HomeNewController {

	public static String HOME_NEW_UPLOAD = "tech/upload/homenew";

	@Autowired
	private CommonService commonService;

	@RequestMapping("index")
	public String indext(Model model) {
		List<MwArticle> mainlist = (List<MwArticle>) commonService
				.list("from MwArticle where typeId='homenew' order by id desc");
		for (MwArticle mwArticle : mainlist) {

			mwArticle.setCreateTimeDesc(new SimpleDateFormat("yyyy-MM-dd")
					.format(mwArticle.getCreateTime()));
		}

		model.addAttribute("list", mainlist);
		return "tech/homenew/index";
	}

	@RequestMapping("add")
	public String add(Model model) {
		MwArticle tum=new MwArticle();
		tum.setSort(1);
		tum.setCreateTimeDesc(new SimpleDateFormat("yyyy-MM-dd")
		.format(new Date()));
		model.addAttribute("dto", tum);
		return "tech/homenew/add";

	}

	@RequestMapping("save")
	public @ResponseBody
	AjaxResponse save(Model model, @RequestParam("title") String title,
			@RequestParam("contents") String contents,
			@RequestParam("shareUsers") String shareUsers,
			@RequestParam("sort") int sort,
			@RequestParam("filePath") String filePath,
			@RequestParam("fj") String fj,
			@RequestParam("createTimeDesc") String createTimeDesc,
			@RequestParam(value = "id", required = false) String id) {
		MwArticle tum = new MwArticle();
		if (StringUtils.isNotBlank(id)) {
			tum = (MwArticle) this.commonService.get(id, MwArticle.class);
		}
	

		tum.setCreateTime(new Date());
		if(null!=createTimeDesc && !createTimeDesc.trim().equals("")){
			try {
				tum.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createTimeDesc+" 00:00:00"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		tum.setUpdateTime(new Date());
		tum.setTitle(title);
		tum.setContents(contents);
		tum.setTypeId("homenew");
		tum.setFilePath(filePath);
		tum.setAudit(1);
		tum.setRecoveryState(0);
		tum.setSort(sort);
		tum.setAid("0");
		tum.setShareUsers(shareUsers);
		tum.setFj(fj);
		

		this.commonService.saveOrUpdateTX(tum);
		return new AjaxResponse();
	}

	@RequestMapping("view")
	public String view(Model model,
			@RequestParam(value = "id", required = false) String id) {
		MwArticle maint = (MwArticle) this.commonService.get(id,
				MwArticle.class);
		
		maint.setCreateTimeDesc(new SimpleDateFormat("yyyy-MM-dd")
		.format(maint.getCreateTime()));

		model.addAttribute("dto", maint);
		return "tech/homenew/add";

	}

	@RequestMapping("del")
	public @ResponseBody
	AjaxResponse del(Model model, @RequestParam("id") String id) {

		this.commonService.deleteTX(MwArticle.class, id);
		return new AjaxResponse();
	}

	@RequestMapping(value = "img", method = RequestMethod.POST)
	public String img(Model mode, @RequestParam("id") String id,
			MultipartHttpServletRequest request, HttpServletResponse resp) {
		AjaxResponse response = null;
		String filePathString ="/" + HOME_NEW_UPLOAD +"/"
				+ UUID.randomUUID();

		String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
				+ filePathString;

		//

		response = FileUploadHelper.upload(request, storeFileNameWithPath,
				"img", Configs.getAsList("userHeadImgFileType"), false);

		if (response.isSuccess()) {
			if (!FileUploadHelper.EMPTY_FILE.equals(response.getData())) {
				MwArticle pkg = (MwArticle) this.commonService.get(id,
						MwArticle.class);
				
				if (null != pkg) {
					pkg.setFilePath(filePathString + "." + response.getData());
					this.commonService.updateTX(pkg);
				}else{
					pkg=new MwArticle();
					pkg.setFilePath(filePathString + "." + response.getData());
					
					
				}
			
			}
		}
		resp.setContentType("text/html; charset=UTF-8");
		try {
			
			response.setData(filePathString + "." + response.getData());
			resp.getWriter().write(JsonFactory.toJson(response));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping(value = "attach", method = RequestMethod.POST)
	public String attach(Model mode, @RequestParam("id") String id,
			MultipartHttpServletRequest request, HttpServletResponse resp) {
		AjaxResponse response = null;
		String filePathString ="/" + HOME_NEW_UPLOAD +"/"
				+ UUID.randomUUID();

		String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
				+ filePathString;
		//storeFileNameWithPath=storeFileNameWithPath.replaceAll("\\\\", "/");

		response = FileUploadHelper.upload(request, storeFileNameWithPath,
				"attach", Configs.getAsList("courseAttachementFileType"), false);

		if (response.isSuccess()) {
			if (!FileUploadHelper.EMPTY_FILE.equals(response.getData())) {
				MwArticle pkg = (MwArticle) this.commonService.get(id,
						MwArticle.class);
				
				if (null != pkg) {
					pkg.setFj(filePathString + "." + response.getData());
					this.commonService.updateTX(pkg);
				}else{
					pkg=new MwArticle();
					pkg.setFj(filePathString + "." + response.getData());
					
					
				}
			
			}
		}
		resp.setContentType("text/html; charset=UTF-8");
		try {
			
			response.setData(filePathString + "." + response.getData());
			resp.getWriter().write(JsonFactory.toJson(response));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


}
