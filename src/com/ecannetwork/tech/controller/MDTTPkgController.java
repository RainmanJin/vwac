package com.ecannetwork.tech.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.Configs;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.CoreConsts.YORN;
import com.ecannetwork.core.util.FileUploadHelper;
import com.ecannetwork.core.util.JoinHelper;
import com.ecannetwork.core.util.JsonFactory;
import com.ecannetwork.core.util.UUID;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.EcanUserMessage;
import com.ecannetwork.dto.tech.MwArticle;
import com.ecannetwork.dto.tech.TechChildrenTag;
import com.ecannetwork.dto.tech.TechContentTag;
import com.ecannetwork.dto.tech.TechExamAnswer;
import com.ecannetwork.dto.tech.TechExamQuestion;
import com.ecannetwork.dto.tech.TechMdttLN;
import com.ecannetwork.dto.tech.TechMdttPkg;
import com.ecannetwork.dto.tech.TechMdttPkgUsers;
import com.ecannetwork.dto.tech.TechTrainCourse;
import com.ecannetwork.dto.tech.TechTrainPlan;
import com.ecannetwork.dto.tech.TechUserMessage;
import com.ecannetwork.tech.util.TechConsts;

/**
 * 课件包管理
 * 
 * @author leo
 * 
 */
@Controller
@RequestMapping("mdttpkg")
public class MDTTPkgController {
	@Autowired
	private CommonService commonService;

	@RequestMapping("index")
	public String index(Model model) {
		model.addAttribute(
				"list",
				this.commonService
						.list("from TechMdttPkg t where t.status !=? order by t.lastUpdateTime desc",
								"D"));

		return "tech/mdttpkg/index";
	}

	@RequestMapping(value = "name", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse updateName(@RequestParam("id") String id,
			@RequestParam("name") String name) {
		TechMdttPkg pkg = (TechMdttPkg) this.commonService.get(id,
				TechMdttPkg.class);
		if (pkg != null) {
			pkg.setFixedName(name);
			pkg.setVersionCode(this.newVersion());
			this.commonService.updateTX(pkg);
		}

		return new AjaxResponse();
	}

	@RequestMapping(value = "namecx", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse updateNameCx(
			@RequestParam("id") String id, @RequestParam("name") String name,
			@RequestParam("cx") String cx) {
		TechMdttPkg pkg = (TechMdttPkg) this.commonService.get(id,
				TechMdttPkg.class);
		if (pkg != null) {
			pkg.setFixedName(name);
			pkg.setMotorcycle(cx);
			pkg.setVersionCode(this.newVersion());
			this.commonService.updateTX(pkg);
		}

		return new AjaxResponse();
	}

	@RequestMapping(value = "namecxtype", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse updateNameCxType(
			@RequestParam("id") String id, @RequestParam("name") String name,
			@RequestParam("cx") String cx, @RequestParam("os") String os) {
		TechMdttPkg pkg = (TechMdttPkg) this.commonService.get(id,
				TechMdttPkg.class);
		if (pkg != null) {
			pkg.setFixedName(name);
			pkg.setMotorcycle(cx);
			pkg.setosType(os);
			pkg.setVersionCode(this.newVersion());
			this.commonService.updateTX(pkg);
		}

		return new AjaxResponse();
	}

	@RequestMapping("beforeupdate")
	public String beforeDelete(@RequestParam(value = "id") String id,
			Model model) {
		TechMdttPkg pkg = (TechMdttPkg) this.commonService.get(id,
				TechMdttPkg.class);
		model.addAttribute("pkg", pkg);
		return "tech/mdttpkg/updatepkg";
	}

	@RequestMapping("version")
	public @ResponseBody AjaxResponse version(@RequestParam("id") String id,
			MultipartHttpServletRequest request) {
		TechMdttPkg pkg = (TechMdttPkg) this.commonService.get(id,
				TechMdttPkg.class);

		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartHttpServletRequest
				.getFile("filePath");
		String fileName = multipartFile.getOriginalFilename();
		String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH+File.separator+"tech"
				+ File.separator + "upload" + File.separator + "dmppkg"
				+ File.separator + fileName.split("\\.")[0];
		//判断路径
		File dirFile = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH+File.separator+"tech"
				+ File.separator + "upload");
		if (!dirFile.exists()) {
			dirFile.mkdir();
			dirFile=new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
				+ File.separator + "tech"+File.separator+"upload"+File.separator+"dmppkg");
			dirFile.mkdir();
		}else {
			dirFile=new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
					+ File.separator + "tech"+File.separator+"upload"+File.separator+"dmppkg");
			if (!dirFile.exists()) {
				dirFile.mkdir();
			}				
		}
		//上传文件
		File file = new File(storeFileNameWithPath + "."
				+ fileName.split("\\.")[1]);
		if (!file.exists()) {
			AjaxResponse response = null;
			response = FileUploadHelper.upload(request, storeFileNameWithPath,
					"filePath", Configs.getAsList("courseAttachementFileType"), true);
			String filePathString=File.separator+"tech"+File.separator + "upload" + File.separator
					+ "dmppkg" + File.separator + fileName.split("\\.")[0] + "."
					+ fileName.split("\\.")[1].toLowerCase();
			String apath = filePathString.replace('\\', '/');
			pkg.setFilePath(apath);
		}

		String version = pkg.getVersion();
		int versionInt = 0;
		versionInt = Integer.parseInt(version) + 1;
		
		pkg.setVersion("" + versionInt);
		commonService.updateTX(pkg);

		return new AjaxResponse(true);
	}

	/**
	 * 查看、编辑课件
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("view")
	public String view(
			Model model,
			@RequestParam(value = "id", required = false, defaultValue = "-1") String id,
			@RequestParam(value = "conentType", required = false, defaultValue = "SSP") String conentType) {
		TechMdttPkg pkg = (TechMdttPkg) this.commonService.get(id,
				TechMdttPkg.class);

		if (pkg == null) {
			pkg = new TechMdttPkg();
			pkg.setVersion("1");
			if (TechMdttPkg.CONTENT_TYPE.SSP.equals(conentType)) {
				pkg.setConentType(TechMdttPkg.CONTENT_TYPE.SSP);
			} else {
				pkg.setConentType(TechMdttPkg.CONTENT_TYPE.CLASS_ROOM);
			}
		}
		/**
		 * 李伟：改造资料库标签展示 20150820
		 */

		if (StringUtils.equals(pkg.getConentType(),
				TechMdttPkg.CONTENT_TYPE.SSP)) {
			/* liwei改造资料库，修改标签时改动 */
			// lc 修改标签，存储标签内容
			/*
			 * String[] moto=pkg.getmotorcycle().split(",");
			 * if(!moto.equals("")){ String ids[] = moto; for(String tid:ids){
			 * TechChildrenTag tct = (TechChildrenTag)
			 * this.commonService.get(tid,TechChildrenTag.class); if(tct==null){
			 * continue; } pkg.setMotorcycles(tct.getCtagName()); }
			 * pkg.setMotorcycle(JoinHelper.join(pkg.getMotorcycles(), ",")); }
			 */
			model.addAttribute("dto", pkg);
			/*---------------------------20150820*/

			return "tech/mdttpkg/view_ssp";
		} else {
			if (StringUtils.isNotBlank(pkg.getTrianPlanID())) {
				TechTrainPlan plan = (TechTrainPlan) this.commonService.get(
						pkg.getTrianPlanID(), TechTrainPlan.class);

				if (plan != null) {
					TechTrainCourse course = (TechTrainCourse) this.commonService
							.get(plan.getTrainId(), TechTrainCourse.class);
					model.addAttribute("course", course);
					model.addAttribute("plan", plan);
				}
			}
			/*---------------------------20150820*/
			model.addAttribute("dto", pkg);
			/*---------------------------20150820*/
			return "tech/mdttpkg/view_cp";
		}
	}

	@RequestMapping(value = "img", method = RequestMethod.POST)
	public String img(Model mode, @RequestParam("pkgID") String pkgID,
			MultipartHttpServletRequest request, HttpServletResponse resp) {
		AjaxResponse response = null;
		String filePathString = File.separator + TechConsts.DMP_PKG_IMG
				+ File.separator + UUID.randomUUID();

		String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
				+ filePathString;

		response = FileUploadHelper.upload(request, storeFileNameWithPath,
				"img", Configs.getAsList("userHeadImgFileType"), false);

		if (response.isSuccess()) {
			if (!FileUploadHelper.EMPTY_FILE.equals(response.getData())) {
				TechMdttPkg pkg = (TechMdttPkg) this.commonService.get(pkgID,
						TechMdttPkg.class);
				pkg.setIconURL(filePathString + "." + response.getData());
				this.commonService.updateTX(pkg);
			}
		}
		resp.setContentType("text/html; charset=UTF-8");
		try {
			resp.getWriter().write(JsonFactory.toJson(response));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "oimg", method = RequestMethod.POST)
	public String oimg(Model mode, @RequestParam("pkgID") String pkgID,
			MultipartHttpServletRequest request, HttpServletResponse resp) {
		AjaxResponse response = null;
		String filePathString = File.separator + TechConsts.DMP_PKG_IMG
				+ File.separator + UUID.randomUUID();

		String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
				+ filePathString;

		response = FileUploadHelper.upload(request, storeFileNameWithPath,
				"oimg", Configs.getAsList("userHeadImgFileType"), false);

		if (response.isSuccess()) {
			if (!FileUploadHelper.EMPTY_FILE.equals(response.getData())) {
				TechMdttPkg pkg = (TechMdttPkg) this.commonService.get(pkgID,
						TechMdttPkg.class);
				pkg.setIcon1URL(filePathString + "." + response.getData());
				this.commonService.updateTX(pkg);
			}
		}
		resp.setContentType("text/html; charset=UTF-8");
		try {
			resp.getWriter().write(JsonFactory.toJson(response));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "timg", method = RequestMethod.POST)
	public String timg(Model mode, @RequestParam("pkgID") String pkgID,
			MultipartHttpServletRequest request, HttpServletResponse resp) {
		AjaxResponse response = null;
		String filePathString = File.separator + TechConsts.DMP_PKG_IMG
				+ File.separator + UUID.randomUUID();

		String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
				+ filePathString;

		response = FileUploadHelper.upload(request, storeFileNameWithPath,
				"timg", Configs.getAsList("userHeadImgFileType"), false);

		if (response.isSuccess()) {
			if (!FileUploadHelper.EMPTY_FILE.equals(response.getData())) {
				TechMdttPkg pkg = (TechMdttPkg) this.commonService.get(pkgID,
						TechMdttPkg.class);
				pkg.setIcon2URL(filePathString + "." + response.getData());
				this.commonService.updateTX(pkg);
			}
		}
		resp.setContentType("text/html; charset=UTF-8");
		try {
			resp.getWriter().write(JsonFactory.toJson(response));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 保存CP课件
	 * 
	 * @param model
	 * @param pkg
	 * @return
	 */
	@RequestMapping(value = "saveCP", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse saveCP(Model model,
			@RequestParam(value = "id") String id,
			@RequestParam("name") String name,
			@RequestParam("brand") String brand,
			@RequestParam("proType") String proType,
			@RequestParam("status") String status,
			@RequestParam("planID") String planID,
			@RequestParam("motorcycle") String motorcycle,
			@RequestParam("osType") String osType,
			@RequestParam(value = "remark", required = false) String remark,
			MultipartHttpServletRequest request) {
		System.out.println("-----------------------------------" + status);
		TechMdttPkg pkg = null;
		if ("".equals(id)) {
			pkg = new TechMdttPkg();
			pkg.setId(UUID.randomUUID());
			pkg.setConentType(TechMdttPkg.CONTENT_TYPE.CLASS_ROOM);
			pkg.setLastUpdateTime(new Date());
		} else {
			pkg = (TechMdttPkg) this.commonService.get(id, TechMdttPkg.class);
		}

		if (StringUtils.equals(pkg.getConentType(),
				TechMdttPkg.CONTENT_TYPE.CLASS_ROOM)) {
			//上传
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartHttpServletRequest
					.getFile("filePath");
			if(multipartFile != null)
			{
				String fileName = multipartFile.getOriginalFilename();
				String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH+File.separator+"tech"
						+ File.separator + "upload" + File.separator + "dmppkg"
						+ File.separator + fileName.split("\\.")[0];
				//判断路径
				File dirFile = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH+File.separator+"tech"
						+ File.separator + "upload");
				if (!dirFile.exists()) {
					dirFile.mkdir();
					dirFile=new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
						+ File.separator + "tech"+File.separator+"upload"+File.separator+"dmppkg");
					dirFile.mkdir();
				}else {
					dirFile=new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
							+ File.separator + "tech"+File.separator+"upload"+File.separator+"dmppkg");
					if (!dirFile.exists()) {
						dirFile.mkdir();
					}				
				}
				//上传文件
				String[] tmp = fileName.split("\\.");
				File file = new File(storeFileNameWithPath + "."
						+ fileName.split("\\.")[1]);
				if (!file.exists()) {
					AjaxResponse response = null;
					response = FileUploadHelper.upload(request, storeFileNameWithPath,
							"filePath", Configs.getAsList("courseAttachementFileType"), true);
					String filePathString=File.separator+"tech"+File.separator + "upload" + File.separator
							+ "dmppkg" + File.separator + fileName.split("\\.")[0] + "."
							+ fileName.split("\\.")[1].toLowerCase();
					String apath = filePathString.replace('\\', '/');
					pkg.setFilePath(apath);
				}
			}
			
			pkg.setName(name);
			pkg.setBrand(brand);
			pkg.setProType(proType);
			// pkg.setStatus(status);//all the satuts yes
			pkg.setStatus(status);
			pkg.setRemark(remark);
			pkg.setTrianPlanID(planID);
			pkg.setVersionCode(this.newVersion());
			pkg.setMotorcycle(motorcycle);
			pkg.setosType(osType);
			this.commonService.saveOrUpdateTX(pkg);
			return new AjaxResponse(true);
		} else {
			return new AjaxResponse(false, "保存失败");
		}

	}

	/**
	 * 保存SSP课件
	 * 
	 * @param model
	 * @param pkg
	 * @return
	 */
	@RequestMapping(value = "saveSSP")
	public @ResponseBody AjaxResponse saveSSP(Model model,
			@RequestParam(value = "id") String id,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "fixedName", required = false) String fixedName,
			@RequestParam(value = "brand", required = false) String brand,
			@RequestParam(value = "proType", required = false) String proType,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "osType", required = false) String osType,
			@RequestParam(value = "motorcycle", required = false) String motorcycle,
			@RequestParam(value = "remark", required = false) String remark,
			MultipartHttpServletRequest request) {
		TechMdttPkg pkg = null;
		if ("".equals(id)) {
			pkg = new TechMdttPkg();
			pkg.setId(UUID.randomUUID());
			pkg.setConentType(TechMdttPkg.CONTENT_TYPE.SSP);
			pkg.setLastUpdateTime(new Date());
			
			pkg.setMotorcycle(motorcycle);//标签
		} else {
			pkg = (TechMdttPkg) this.commonService.get(id, TechMdttPkg.class);
		}

		if (StringUtils.equals(pkg.getConentType(),
				TechMdttPkg.CONTENT_TYPE.SSP)) {
			
			//上传
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartHttpServletRequest
					.getFile("filePath");
			if(multipartFile != null)
			{
				String fileName = multipartFile.getOriginalFilename();
				String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH+File.separator+"tech"
						+ File.separator + "upload" + File.separator + "dmppkg"
						+ File.separator + fileName.split("\\.")[0];
				//判断路径
				File dirFile = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH+File.separator+"tech"
						+ File.separator + "upload");
				if (!dirFile.exists()) {
					dirFile.mkdir();
					dirFile=new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
						+ File.separator + "tech"+File.separator+"upload"+File.separator+"dmppkg");
					dirFile.mkdir();
				}else {
					dirFile=new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
							+ File.separator + "tech"+File.separator+"upload"+File.separator+"dmppkg");
					if (!dirFile.exists()) {
						dirFile.mkdir();
					}				
				}
				//上传文件
				String[] tmp = fileName.split("\\.");
				File file = new File(storeFileNameWithPath + "."
						+ fileName.split("\\.")[1]);
				if (!file.exists()) {
					AjaxResponse response = null;
					response = FileUploadHelper.upload(request, storeFileNameWithPath,
							"filePath", Configs.getAsList("courseAttachementFileType"), true);
					String filePathString=File.separator+"tech"+File.separator + "upload" + File.separator
							+ "dmppkg" + File.separator + fileName.split("\\.")[0] + "."
							+ fileName.split("\\.")[1].toLowerCase();
					String apath = filePathString.replace('\\', '/');
					pkg.setFilePath(apath);
				}
			}
			
						
			pkg.setFixedName(fixedName);
			pkg.setBrand(brand);
			pkg.setProType(proType);
			// pkg.setStatus(status);//all the status yes
			System.out.println("-----------------------------------" + status);
			pkg.setStatus(status);
			System.out.println(remark);
			pkg.setRemark(remark);
			int version = this.newVersion();
			pkg.setVersionCode(this.newVersion());
			pkg.setVersion(String.valueOf(version));
			/* 李伟改造资料库，修改标签改动 */
			// pkg.setMotorcycle(motorcycle);
			/*---------------------------20150820*/
			
			pkg.setMotorcycle(motorcycle);//标签
			pkg.setosType(osType);
			this.commonService.saveOrUpdateTX(pkg);
			return new AjaxResponse(true);
		} else {
			return new AjaxResponse(false, "保存失败");
		}
	}
	
	/**
	 * 保存SSP课件
	 * 
	 * @param model
	 * @param pkg
	 * @return
	 */
	@RequestMapping(value = "addSSP")
	public @ResponseBody AjaxResponse addSSP(Model model,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "fixedName", required = false) String fixedName,
			@RequestParam(value = "brand", required = false) String brand,
			@RequestParam(value = "proType", required = false) String proType,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "osType", required = false) String osType,
			@RequestParam(value = "remark", required = false) String remark,
			MultipartHttpServletRequest request) {
		TechMdttPkg pkg = null;
		
		pkg = new TechMdttPkg();
		pkg.setId(UUID.randomUUID());
		pkg.setConentType(TechMdttPkg.CONTENT_TYPE.SSP);
		pkg.setStatus("1");
		pkg.setPkgType("SCO");
		pkg.setFileSize("0M");
		pkg.setLastUpdateTime(new Date());

		if (StringUtils.equals(pkg.getConentType(),
				TechMdttPkg.CONTENT_TYPE.SSP)) {
			
			//上传
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartHttpServletRequest
					.getFile("filePath");
			if(multipartFile != null)
			{
				String fileName = multipartFile.getOriginalFilename();
				String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH+File.separator+"tech"
						+ File.separator + "upload" + File.separator + "dmppkg"
						+ File.separator + fileName.split("\\.")[0];
				//判断路径
				File dirFile = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH+File.separator+"tech"
						+ File.separator + "upload");
				if (!dirFile.exists()) {
					dirFile.mkdir();
					dirFile=new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
						+ File.separator + "tech"+File.separator+"upload"+File.separator+"dmppkg");
					dirFile.mkdir();
				}else {
					dirFile=new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
							+ File.separator + "tech"+File.separator+"upload"+File.separator+"dmppkg");
					if (!dirFile.exists()) {
						dirFile.mkdir();
					}				
				}
				//上传文件
				String[] tmp = fileName.split("\\.");
				File file = new File(storeFileNameWithPath + "."
						+ fileName.split("\\.")[1]);
				if (!file.exists()) {
					AjaxResponse response = null;
					response = FileUploadHelper.upload(request, storeFileNameWithPath,
							"filePath", Configs.getAsList("courseAttachementFileType"), true);
					String filePathString=File.separator+"tech"+File.separator + "upload" + File.separator
							+ "dmppkg" + File.separator + fileName.split("\\.")[0] + "."
							+ fileName.split("\\.")[1].toLowerCase();
					String apath = filePathString.replace('\\', '/');
					pkg.setFilePath(apath);
				}
			}
			
			
			pkg.setName(name);			
			pkg.setFixedName(fixedName);
			pkg.setBrand(brand);
			pkg.setProType(proType);
			pkg.setStatus("0");
			pkg.setRemark(remark);
			pkg.setVersionCode(1);
			pkg.setVersion("1");
			pkg.setosType(osType);
			pkg.setValid("1");
			
			this.commonService.saveOrUpdateTX(pkg);
			return new AjaxResponse(true);
		} else {
			return new AjaxResponse(false, "保存失败");
		}
	}

	@RequestMapping("status")
	public @ResponseBody AjaxResponse status(@RequestParam("id") String id,
			@RequestParam("status") String status) {
		TechMdttPkg pkg = (TechMdttPkg) this.commonService.get(id,
				TechMdttPkg.class);
		if (pkg != null) {
			if (YORN.NO.equals(pkg.getValid())) {
				return new AjaxResponse(false, "课件包格式错误，不能启用");
			} else {
				if (TechMdttPkg.CONTENT_TYPE.SSP.equals(pkg.getConentType())) {
					if (StringUtils.isBlank(pkg.getBrand())
							|| StringUtils.isBlank(pkg.getProType())) {
						return new AjaxResponse(false, "课件包尚未设置品牌和领域信息");
					}
				} else {
					if (StringUtils.isBlank(pkg.getTrianPlanID())) {
						return new AjaxResponse(false, "课件包尚未设置排期信息");
					}
				}

				pkg.setStatus(status);
				pkg.setVersionCode(this.newVersion());
				this.commonService.updateTX(pkg);
			}
		}

		return new AjaxResponse(true);
	}

	/**
	 * 列举排期计划列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("cfgPlan")
	public String cfgPlan(Model model) {
		List<Object[]> tcList = this.commonService
				.list("select t, c from TechTrainPlan t, TechTrainCourse c where c.id = t.trainId");

		List<TechTrainPlan> list = new ArrayList<TechTrainPlan>(tcList.size());

		for (Object[] os : tcList) {
			TechTrainPlan plan = (TechTrainPlan) os[0];
			TechTrainCourse c = (TechTrainCourse) os[1];
			plan.setTrainCourseName(c.getName());
			list.add(plan);
		}

		// 去掉已经设置课件包的
		List<TechMdttPkg> pkgs = this.commonService
				.list("from TechMdttPkg t where t.conentType = ? and t.trianPlanID is not null",
						TechMdttPkg.CONTENT_TYPE.CLASS_ROOM);
		Set<String> planIDs = new HashSet<String>();
		for (TechMdttPkg pkg : pkgs) {
			planIDs.add(pkg.getTrianPlanID());
		}

		Iterator<TechTrainPlan> it = list.iterator();
		while (it.hasNext()) {
			TechTrainPlan plan = it.next();
			if (planIDs.contains(plan.getId())) {
				it.remove();
			}
		}

		model.addAttribute("list", list);

		return "tech/mdttpkg/cfgPlan";
	}

	@RequestMapping("saveCfgPlan")
	public @ResponseBody AjaxResponse saveCfgPlan(Model model,
			@RequestParam("planID") String planID, @RequestParam("id") String id) {
		TechMdttPkg pkg = (TechMdttPkg) this.commonService.get(id,
				TechMdttPkg.class);
		if (pkg != null) {
			pkg.setTrianPlanID(planID);

			TechTrainPlan plan = (TechTrainPlan) this.commonService.get(planID,
					TechTrainPlan.class);
			TechTrainCourse course = (TechTrainCourse) this.commonService.get(
					plan.getTrainId(), TechTrainCourse.class);
			if (course != null) {
				pkg.setBrand(course.getBrand());
				pkg.setProType(course.getProType());
				pkg.setVersionCode(this.newVersion());
				// pkg.setMotorcycle(course.getmotorcycle());
				this.commonService.updateTX(pkg);
			}
		}
		return new AjaxResponse(true);
	}

	/**
	 * 预览课件
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	public String preview(Model model, @RequestParam("id") String id) {
		TechMdttPkg pkg = (TechMdttPkg) this.commonService.get(id,
				TechMdttPkg.class);
		model.addAttribute("dto", pkg);
		Map menu = JsonFactory.parseJsonString(pkg.getJsonMenu());
		model.addAttribute("menu", menu);

		return "tech/mdttpkg/preview";
	}

	private synchronized Integer newVersion() {
		TechMdttPkg pkg = (TechMdttPkg) this.commonService
				.listOnlyObject("from TechMdttPkg t order by t.versionCode desc");
		if (pkg != null) {
			return pkg.getVersionCode() + 1;
		} else {
			return 1;
		}
	}

	/**
	 * 改造资料库 添加权限功能 20150820
	 */
	@RequestMapping("pkgpower")
	public String power(Model model, @RequestParam("id") String id) {
		TechMdttPkg tmp = null;
		tmp = (TechMdttPkg) this.commonService.get(id, TechMdttPkg.class);
		List<Object[]> temliST = (List<Object[]>) commonService.list(
				"from TechMdttPkgUsers t join t.mdttPkg m where m.id=?", id);
		if (temliST.size() != 0) {
			for (Object[] ob : temliST) {
				TechMdttPkg ctmp = (TechMdttPkg) ob[1];
				TechMdttPkgUsers tmpus = (TechMdttPkgUsers) ob[0];
				if (tmp.getId().equals(ctmp.getId())) {
					tmp.getTmus().add(tmpus);
				}
			}
		}
		model.addAttribute("dto", tmp);
		return "tech/mdttpkg/pkgpower";
	}

	/**
	 * 改造资料库 添加权限功能 跳转选择人员 20150820
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("listusers")
	public String listUsers(Model model,
			@RequestParam(value = "id", required = false) String id) {
		TechMdttPkg tmp = null;
		tmp = (TechMdttPkg) this.commonService.get(id, TechMdttPkg.class);
		List<EcanUser> users = this.commonService
				.list("from EcanUser e where e.status = '1'");
		List<String> userids = this.commonService
				.list("select distinct t.userId from TechMdttPkgUsers t where t.pkgId=?",
						id);
		Set<String> useridsSet = new HashSet<String>();
		useridsSet.addAll(userids);
		for (Iterator<EcanUser> it = users.iterator(); it.hasNext();) { // 过滤掉已经选择的人员,
			EcanUser u = it.next();
			if (tmp.getPusers().contains(u.getId())
					|| useridsSet.contains(u.getId())) {
				it.remove();
			}
		}
		model.addAttribute("list", users);
		return "tech/mdttpkg/selectuser";
	}

	/**
	 * 改造资料库 添加权限功能 保存选择人员 20150820
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("chgusers")
	public @ResponseBody AjaxResponse chgusers(@RequestParam("id") String id,
			@RequestParam("uids") String uids) {
		String ids[] = uids.split(",");
		for (String uid : ids) {
			TechMdttPkgUsers tuser = new TechMdttPkgUsers();
			tuser.setUserId(uid);
			tuser.setPkgId(id);
			this.commonService.saveOrUpdateTX(tuser);
		}
		return new AjaxResponse(true);

	}

	/**
	 * 改造资料库 添加权限功能 删除被添加权限的人员 20150820
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("deluser")
	public @ResponseBody AjaxResponse delcour(@RequestParam("id") String id) {
		List<TechMdttPkgUsers> tmpus = this.commonService.list(
				"from TechMdttPkgUsers t where t.id=?", id);
		if (tmpus != null) {
			this.commonService.deleteAllTX(tmpus);
		}
		return new AjaxResponse();
	}

	@RequestMapping("powerOk")
	public @ResponseBody AjaxResponse powerOk() {
		return new AjaxResponse();
	}

	/**
	 * 改造资料库选择多级标签 20150820
	 */
	@RequestMapping("listtag")
	public String listtag(Model model) {
		List<TechContentTag> mainlist = (List<TechContentTag>) commonService
				.list("from TechContentTag ");
		List<Object[]> temliST = (List<Object[]>) commonService
				.list("from TechChildrenTag t join t.mainTag m ");
		List<TechContentTag> resultlist = new ArrayList<TechContentTag>();
		for (TechContentTag main : mainlist) {
			for (Object[] ob : temliST) {
				TechContentTag cmain = (TechContentTag) ob[1];
				TechChildrenTag child = (TechChildrenTag) ob[0];
				if (main.getId().equals(cmain.getId())) {
					main.getTct().add(child);
				}
			}
			resultlist.add(main);
		}
		model.addAttribute("list", resultlist);

		return "tech/mdttpkg/selecttag";
	}

	/**
	 * 资料库改造选择标签保存数据库 20150820
	 */
	@RequestMapping(value = "chgtags")
	public @ResponseBody AjaxResponse chgtags(@RequestParam("id") String id,
			@RequestParam("tagid") String tagid) {
		TechMdttPkg tmp = (TechMdttPkg) this.commonService.get(id,
				TechMdttPkg.class);
		try {
			String userids = new String(tagid.getBytes("ISO-8859-1"), "UTF-8");
			tmp.setMotorcycle(tagid);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.commonService.updateTX(tmp);
		return new AjaxResponse();

	}
}
