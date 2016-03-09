package com.ecannetwork.tech.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.Configs;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.FileUploadHelper;
import com.ecannetwork.core.util.JsonFactory;
import com.ecannetwork.core.util.UUID;
import com.ecannetwork.dto.tech.TechCourse;
import com.ecannetwork.dto.tech.TechCourseAttchment;
import com.ecannetwork.dto.tech.TechCourseItem;
import com.ecannetwork.dto.tech.TechCourseTesting;
import com.ecannetwork.dto.tech.TechCourseTestingDb;
import com.ecannetwork.dto.tech.TechScormPkg;
import com.ecannetwork.dto.tech.TechTrainCourse;
import com.ecannetwork.tech.facade.ScormCourseFacade;
import com.ecannetwork.tech.service.ScormCorseService;

/**
 * 课件发布管理
 * 
 * @author leo
 * 
 */
@Controller
@RequestMapping("coursemanager")
public class CourseManagerController
{
	@Autowired
	private CommonService commonService;

	@Autowired
	private ScormCourseFacade scormFacade;

	@Autowired
	private ScormCorseService courseService;

	/**
	 * 列举所有已发布的课件， 并提供删除、编辑、停用功能。<br />
	 * 已分配课程的课件不能删除，只能停用
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public String index(Model model)
	{
		model.addAttribute("list", this.commonService.list(TechCourse.class));
		return "tech/coursemanager/index";
	}

	/**
	 * 列举所有的 scorm包信息， 并提供一个发布按钮
	 * 
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("listScorm")
	public String listScorm(Model model)
	{
		List<TechScormPkg> list = this.commonService.list(
				"from TechScormPkg t where t.status=?", CoreConsts.YORN.YES);

		model.addAttribute("list", list);

		return "tech/coursemanager/listScorm";
	}

	/**
	 * 发布::解压scrom包，生成课件信息，并跳转到编辑页面
	 * 
	 * @param model
	 * @param scromid
	 *            scorm包的编号
	 * @return
	 */
	@RequestMapping("pub")
	public @ResponseBody
	AjaxResponse pub(Model model, @RequestParam("scromid") String scromid)
	{
		return scormFacade.publishScorm(scromid);
	}

	/**
	 * 编辑一个课件，界面提供添加附件功能、跳转到添加习题功能
	 * 
	 * @param model
	 * @param courseId
	 * @return
	 */
	@RequestMapping("view")
	public String view(Model model, @RequestParam("id") String id)
	{
		// 课件信息
		model.addAttribute("dto", this.commonService.get(id, TechCourse.class));

		// 课件附件信息
		model.addAttribute("attachements", this.commonService.list(
				"from TechCourseAttchment t where t.courseId=?", id));

		// 课件测试题信息
		model.addAttribute("testtings", this.commonService.list(
				"from TechCourseTesting t where t.courseId=?", id));

		return "tech/coursemanager/view";
	}

	/**
	 * 进入上传附件页面
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("forUploadAttachement")
	public String forUploadAttachement(Model model,
			@RequestParam("id") String id)
	{

		return "tech/coursemanager/forUploadAttachement";
	}

	/**
	 * 上传附件
	 * 
	 * @param id
	 *            课件编号
	 * @param name
	 *            附件名称
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("uploadAttachement")
	public String uploadAttachement(@RequestParam("id") String id,
			@RequestParam("name") String name, HttpServletRequest request,
			HttpServletResponse resp)
	{
		String uuid = UUID.randomUUID();

		AjaxResponse response = null;
		// 预览图片
		String path = File.separator + "tech" + File.separator + "upload"
				+ File.separator + "course_attachement" + File.separator + uuid;

		response = FileUploadHelper.upload(request,
				CoreConsts.Runtime.APP_ABSOLUTE_PATH + path, "file", Configs
						.getAsList("courseAttachementFileType"), false, Configs
						.getLong("courseAttachementFileSize"));

		if (response.isSuccess())
		{
			// 成功
			TechCourseAttchment attach = new TechCourseAttchment();
			attach.setId(uuid);
			attach.setCourseId(id);
			attach.setName(name);
			attach.setUrl((path + "." + response.getData()).replace('\\', '/'));
			this.commonService.saveTX(attach);
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
	 * 删除附件
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("delAttachement")
	public @ResponseBody
	AjaxResponse delAttachement(@RequestParam("id") String id)
	{
		TechCourseAttchment attach = (TechCourseAttchment) this.commonService
				.get(id, TechCourseAttchment.class);

		File file = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
				+ attach.getUrl());
		file.delete();
		this.commonService.deleteTX(attach);

		return new AjaxResponse();
	}

	/**
	 * 保存
	 * 
	 * @param id
	 * @param name
	 * @param brand
	 * @param proType
	 * @param contentType
	 * @param remark
	 * @param preview
	 * @param url
	 * @param status
	 * @param request
	 * @param resp
	 * @return
	 */
	@RequestMapping("save")
	public String save(@RequestParam("id") String id,
			@RequestParam("name") String name,
			@RequestParam("brand") String brand,
			@RequestParam("proType") String proType,
			@RequestParam("contentType") String contentType,
			@RequestParam("remark") String remark,
			@RequestParam("preview") String preview,
			@RequestParam("scormId") String scormId,
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

			TechCourse pkg = (TechCourse) this.commonService.get(id,
					TechCourse.class);
			pkg.setName(name);
			pkg.setBrand(brand);
			pkg.setProType(proType);
			pkg.setContentType(contentType);
			pkg.setRemark(remark);
			pkg.setStatus(status);
			pkg.setPreview(preview);
			pkg.setScormId(scormId);

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
	 * 预览课件
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("preview")
	public String preview(Model model, @RequestParam("id") String id)
	{
		TechCourse course = (TechCourse) this.commonService.get(id,
				TechCourse.class);
		List<TechCourseItem> items = this.commonService.list(
				"from TechCourseItem t where t.courseId=? order by t.idx", id);

		// 构建成

		model.addAttribute("dto", course);
		model.addAttribute("items", items);

		return "tech/coursemanager/preview";
	}

	/**
	 * 添加课后测试试卷
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("addTesting")
	public @ResponseBody
	AjaxResponse addTestting(@RequestParam("id") String id,
			@RequestParam("courseid") String courseid)
	{

		List<TechCourseTesting> techCourseTesting = commonService.list(
				"from TechCourseTesting t where t.courseId=?", courseid);
		String[] ids = id.split(",");
		List<TechCourseTesting> testtingList = new ArrayList<TechCourseTesting>();
		int idx = 1;
		if (techCourseTesting != null && techCourseTesting.size() > 0)
		{
			idx = techCourseTesting.size() + 1;
		}
		for (int i = 0; i < ids.length; i++)
		{
			TechCourseTesting testing = new TechCourseTesting();
			testing.setCourseId(courseid);
			testing.setTitleId(ids[i]);
			testing.setIdx(idx);
			testing.setId(null);
			testtingList.add(testing);
			idx++;
		}
		commonService.saveOrUpdateTX(testtingList);
		return new AjaxResponse();
	}

	/**
	 * 列举当前课件的所有测试题
	 * 
	 * @param model
	 * @param courseId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("listTestting")
	public String listTestting(Model model,
			@RequestParam("courseId") String courseId)
	{
		List<TechCourseTesting> techCourseTesting = commonService.list(
				"from TechCourseTesting t where t.courseId=?", courseId);
		List<TechCourseTestingDb> list = commonService.list(
				"from TechCourseTestingDb t where t.status = ?",
				CoreConsts.dbstatus.PUBLISH);
		List<TechCourseTestingDb> comperList = this.compareList(
				techCourseTesting, list);
		model.addAttribute("techcoursetestingdb", comperList);
		
		
		List<TechTrainCourse> courses = this.commonService.list(TechTrainCourse.class);
		model.addAttribute("courseLists", courses);
		
		
		return "tech/coursemanager/listTestting";
	}

	/**
	 * 
	 * 课后测试试卷与课后测试题库比较剔重
	 * 
	 * @param list
	 *            课后测试试卷已有题目
	 * @param listdb
	 *            课后测试题库全部题目
	 * @return
	 */
	public List<TechCourseTestingDb> compareList(List<TechCourseTesting> list,
			List<TechCourseTestingDb> listdb)
	{

		Set<String> set = new HashSet<String>();

		for (TechCourseTesting testing : list)
		{
			set.add(testing.getTitleId());
		}

		for (Iterator<TechCourseTestingDb> it = listdb.iterator(); it.hasNext();)
		{
			TechCourseTestingDb db = it.next();
			if (set.contains(db.getId()))
			{
				it.remove();
			}
		}
		return listdb;
	}

	/**
	 * 删除:::删除附带的item信息等
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("del")
	public @ResponseBody
	AjaxResponse del(@RequestParam("id") String id)
	{
		return this.courseService.deleteCourseTX(id);
	}

	/**
	 * 
	 * 删除课后测试题
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("delTestting")
	public @ResponseBody
	AjaxResponse delTestting(@RequestParam("id") String id)
	{
		commonService.deleteTX(TechCourseTesting.class, id);
		return new AjaxResponse();

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
		TechCourse pkg = (TechCourse) this.commonService.get(id,
				TechCourse.class);
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
		TechCourse pkg = (TechCourse) this.commonService.get(id,
				TechCourse.class);
		pkg.setStatus(CoreConsts.YORN.YES);
		this.commonService.updateTX(pkg);

		return new AjaxResponse();
	}
}