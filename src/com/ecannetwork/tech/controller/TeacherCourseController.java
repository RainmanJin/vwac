package com.ecannetwork.tech.controller;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.app.user.service.UserService;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.TechTeacherTrainCourse;
import com.ecannetwork.dto.tech.TechTrainCourse;
import com.ecannetwork.tech.service.TrainCourseService;
import com.ecannetwork.tech.util.TechConsts;

/**
 * 讲师可教的课程管理：：讲师、课程一对多关系
 * 
 * @author liulibin
 * 
 */
@Controller
@RequestMapping("teachercoursemanager")
public class TeacherCourseController
{
	@Autowired
	private CommonService commonService;

	@Autowired
	private UserService userService;

	@Autowired
	private TrainCourseService trainCourseService;

	/**
	 * 列举所有的讲师
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public String index(Model model)
	{
		List<EcanUser> users = userService.listByRole(TechConsts.Role.TEACHER);

		model.addAttribute("list", users);

		return "tech/teachercoursemanager/index";
	}

	/**
	 * 维护讲师可管理的课程
	 * 
	 * @param model
	 * @param id
	 *            讲师编号
	 * @return
	 */
	@RequestMapping("view")
	public String view(Model model, @RequestParam("id") String id)
	{
		List<TechTrainCourse> courses = this.trainCourseService
				.listByTeacher(id);
		model.addAttribute("list", courses);

		Set<String> hasCourses = new HashSet<String>();
		for (TechTrainCourse tc : courses)
		{
			hasCourses.add(tc.getId());
		}

		// 所有课程
		List<TechTrainCourse> all = this.commonService.list(
				"from TechTrainCourse t where t.status=?", CoreConsts.YORN.YES);
		
		for (Iterator<TechTrainCourse> it = all.iterator(); it.hasNext();)
		{// 过滤掉已经添加的课程
			TechTrainCourse tc = it.next();
			if (hasCourses.contains(tc.getId()))
			{
				it.remove();
			}
		}

		model.addAttribute("all", all);

		return "tech/teachercoursemanager/view";
	}

	/**
	 * 添加讲师可维护的课程
	 * 
	 * @param id
	 * @param trainCourseId
	 * @return
	 */
	@RequestMapping("add")
	public @ResponseBody
	AjaxResponse add(@RequestParam("id") String id,
			@RequestParam("trainCourseId") String trainCourseId)
	{
		TechTeacherTrainCourse tc = new TechTeacherTrainCourse();
		tc.setTeacherId(id);
		tc.setTrainCourseId(trainCourseId);
		this.commonService.saveTX(tc);

		return new AjaxResponse();
	}

	/**
	 * 删除关系
	 * 
	 * @param trainCourseId
	 *            课程编号
	 * @param id
	 *            讲师编号
	 * @return
	 */
	@RequestMapping("del")
	public @ResponseBody
	AjaxResponse del(@RequestParam("trainCourseId") String trainCourseId,
			@RequestParam("id") String id)
	{
		this.trainCourseService.deleteTeacherTrainCourseTX(id, trainCourseId);

		return new AjaxResponse();
	}

}
