package com.ecannetwork.tech.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.i18n.I18N;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.Configs;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.FileUploadHelper;
import com.ecannetwork.core.util.JsonFactory;
import com.ecannetwork.core.util.UUID;
import com.ecannetwork.dto.tech.TechCourse;
import com.ecannetwork.dto.tech.TechScormPkg;
import com.ecannetwork.tech.facade.ScormCourseFacade;
import com.ecannetwork.tech.scorm.Organizations;

/**
 * scorm包管理，上传、下载、删除
 * 
 * @author leo
 * 
 */
@Controller
@RequestMapping("scormmanager")
public class ScormManagerController
{
	private static final String SCORM_PREVIEW_DEFAULT_IMG = "/images/default_course_preview.jpg";

	@Autowired
	private CommonService commonService;

	@Autowired
	public ScormCourseFacade scormFacade;

	/**
	 * 列举所有的scorm包， 提供删除、下载功能
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public String index(Model model)
	{
		model.addAttribute("list", this.commonService.list(TechScormPkg.class));
		return "tech/scormmanager/index";
	}

	/**
	 * 查看或编辑
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("view")
	public String view(Model model, @RequestParam(value = "id") String id)
	{
		model.addAttribute("dto", this.commonService
				.get(id, TechScormPkg.class));
		return "tech/scormmanager/view";
	}

	@RequestMapping("save")
	public String save(@RequestParam("id") String id,
			@RequestParam("name") String name,
			@RequestParam("brand") String brand,
			@RequestParam("proType") String proType,
			@RequestParam("contentType") String contentType,
			@RequestParam("remark") String remark,
			@RequestParam("preview") String preview,
			@RequestParam("url") String url,
			@RequestParam(value = "status", required = false) String status,
			HttpServletRequest request, HttpServletResponse resp)
	{
		AjaxResponse response = null;
		// 预览图片
		String path = File.separator + "tech" + File.separator + "upload"
				+ File.separator + "scorm_preview" + File.separator + id;

		response = FileUploadHelper.upload(request,
				CoreConsts.Runtime.APP_ABSOLUTE_PATH + path, "file", Configs
						.getAsList("userHeadImgFileType"), true);

		if (response.isSuccess())
		{
			if (response.getData() != null
					&& !FileUploadHelper.EMPTY_FILE.equals(response.getData()))
			{// 上传了预览图
				preview = path + "." + response.getData();
			} else
			{// 未上传文件,DO NTH

			}

			TechScormPkg pkg = (TechScormPkg) this.commonService.get(id,
					TechScormPkg.class);
			pkg.setName(name);
			pkg.setBrand(brand);
			pkg.setProType(proType);
			pkg.setContentType(contentType);
			pkg.setRemark(remark);
			pkg.setStatus(status);
			pkg.setPreview(preview);
			pkg.setUrl(url);

			this.commonService.updateTX(pkg);
		} else
		{
			// upload error
		}

		// 不推荐的写法
		String re = JsonFactory.toJson(response);
		resp.setContentType("text/html; charset=utf-8");
		try
		{
			resp.getWriter().write(re);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 上传课件包
	 * 
	 * @return
	 */
	@RequestMapping("forUpload")
	public String forUpload()
	{
		return "tech/scormmanager/upload";
	}

	/**
	 * 上传课件包<br />
	 * 上传课件包完成将课件包解压，并读取里面的标题
	 * 
	 * @return
	 */
	@RequestMapping("upload")
	public String upload(HttpServletRequest request, HttpServletResponse resp)
	{
		String uuid = UUID.randomUUID();
		String path = File.separator + "tech" + File.separator + "upload"
				+ File.separator + "scorm" + File.separator + uuid;

		String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
				+ path;

		AjaxResponse response = FileUploadHelper.upload(request,
				storeFileNameWithPath, "file", Configs
						.getAsList("scormFileType"));

		if (response.isSuccess())
		{
			String ext = response.getData().toString();
			// 解压课件包，并读取课件包名称
			response = scormFacade.validateScorm(storeFileNameWithPath + "."
					+ ext, uuid);

			if (response.isSuccess())
			{// 创建一个scorm包记录
				TechScormPkg pkg = new TechScormPkg();
				pkg.setId(uuid);
				Organizations orgs = (Organizations) response.getData();
				pkg.setName(orgs.getTitle());
				pkg.setUrl((path + "." + ext).replace('\\', '/'));
				pkg.setStatus(CoreConsts.YORN.NO);
				pkg.setPreview(SCORM_PREVIEW_DEFAULT_IMG);
				this.commonService.saveOrUpdateTX(pkg);
				response.setData(uuid);
			} else
			{
				// 删除上传文件
				File file = new File(storeFileNameWithPath + "." + ext);
				file.delete();
			}
		} else
		{// 上传失败

		}

		// 不推荐的写法
		String re = JsonFactory.toJson(response);
		resp.setContentType("text/html; charset=utf-8");
		try
		{
			resp.getWriter().write(re);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("del")
	public @ResponseBody
	AjaxResponse del(@RequestParam("id") String id)
	{
		List<TechCourse> course = this.commonService.list(
				"from TechCourse t where t.scormId=?", id);
		if (course.size() > 0)
		{
			return new AjaxResponse(false, I18N
					.parse("i18n.scormpkg.msg.delPublishedScorm"));
		} else
		{
			TechScormPkg pkg = (TechScormPkg) this.commonService.get(id,
					TechScormPkg.class);
			File file = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
					+ pkg.getUrl());
			file.delete();
			this.commonService.deleteTX(pkg);

			return new AjaxResponse();
		}
	}

	/**
	 * 禁用
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("disable")
	public @ResponseBody
	AjaxResponse disable(Model model, @RequestParam("id") String id)
	{
		TechScormPkg pkg = (TechScormPkg) this.commonService.get(id,
				TechScormPkg.class);
		pkg.setStatus(CoreConsts.YORN.NO);
		this.commonService.updateTX(pkg);

		return new AjaxResponse();
	}

	/**
	 * 启用
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("active")
	public @ResponseBody
	AjaxResponse active(Model model, @RequestParam("id") String id)
	{
		TechScormPkg pkg = (TechScormPkg) this.commonService.get(id,
				TechScormPkg.class);
		pkg.setStatus(CoreConsts.YORN.YES);
		this.commonService.updateTX(pkg);

		return new AjaxResponse();
	}

	public static void main(String[] args)
	{
		System.out.println("\\abc\\ddd.gif".replace('\\','/'));
	}
}
