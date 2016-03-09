package com.ecannetwork.tech.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.ecannetwork.core.module.controller.DateBindController;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.DateUtils;
import com.ecannetwork.core.util.ExecuteContext;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.TechCourse;
import com.ecannetwork.dto.tech.TechCourseTesting;
import com.ecannetwork.dto.tech.TechCourseTestingDb;
import com.ecannetwork.dto.tech.TechCouseInstance;
import com.ecannetwork.dto.tech.TechStudentStatus;
import com.ecannetwork.tech.util.TechConsts;

/**
 * 课程管理：：：讲师在本应用中进行课程发布、候选人分配，
 * 
 * @author think
 * 
 */
@Controller
@RequestMapping("/courseinstancemanager")
public class CourseInstanceManagerController extends DateBindController
{
	@Autowired
	private CommonService commonService;
	@Autowired
	private UserService userService;

	/**
	 * 可分配课程列表:::所有操作员发布出来的课程都可分派，不做已分派校验. 只能查看专业方向相同的课件
	 * 
	 * @param model
	 * @return
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping("canDispach")
	public String canDispach(Model model)
	{
		EcanUser user = ExecuteContext.user();
		@SuppressWarnings("unused")
		List<TechCourse> techCourseList = commonService.list(
				"from TechCouseInstance  t where t.teacher=?", user.getId());
		model.addAttribute("list", techCourseList);
		return "tech/courseinstancemanager/canDispach";
	}

	/**
	 * 
	 * 预发布课程
	 * 
	 * @param model
	 * @return
	 */
	@SuppressWarnings( { "unchecked", "unchecked" })
	@RequestMapping("canPublishCourse")
	public String canPublicCourse(Model model)
	{
		// DELETE PROTYPE
		// EcanUser user = ExecuteContext.user();
		// List<TechCourse> techCourseList = commonService.list(
		// "from TechCourse t where t.status=? and t.proType=?",
		// CoreConsts.YORN.YES, user.getProType());
		List<TechCourse> techCourseList = commonService.list(
				"from TechCourse t where t.status=?", CoreConsts.YORN.YES);
		model.addAttribute("list", techCourseList);
		return "tech/courseinstancemanager/listcourse";
	}

	/**
	 * 
	 * 编辑或课件内容
	 * 
	 * @param model
	 * @param cousreid
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("view")
	public String view(Model model, @RequestParam("cousreid") String cousreid,
			@RequestParam(value = "id", required = false) String id)
	{
		EcanUser user = ExecuteContext.user();
		TechCourse techCourse = (TechCourse) commonService.get(cousreid,
				TechCourse.class);
		List<TechStudentStatus> statusList = commonService.list(
				"from TechStudentStatus t where t.courseInstanceId=?", id);
		if (id != null)
		{
			TechCouseInstance couseInstance = (TechCouseInstance) commonService
					.get(id, TechCouseInstance.class);

			List<TechCourseTesting> testingList = commonService.list(
					"from TechCourseTesting t where t.courseId=?",
					couseInstance.getCourseId());
			model.addAttribute("testingList", testingList);
			model.addAttribute("courseinstanceid", couseInstance.getId());
			model.addAttribute("dto", couseInstance);
		} else
		{
			TechCouseInstance couseInstance = new TechCouseInstance();
			couseInstance.setBrand(techCourse.getBrand());
			couseInstance.setCourseId(cousreid);
			couseInstance.setCreateTime(new Date());
			couseInstance.setExpireTime(DateUtils.getNextMonth());
			couseInstance.setId(null);
			couseInstance.setProType(techCourse.getProType());
			couseInstance.setName(techCourse.getName());
			couseInstance.setRemark(techCourse.getRemark());
			couseInstance.setStatus(CoreConsts.COURSESTATUS.NOING);
			couseInstance.setTeacher(user.getId());
			couseInstance.setPreview(techCourse.getPreview());
			commonService.saveOrUpdateTX(couseInstance);
			model.addAttribute("dto", couseInstance);
			model.addAttribute("courseinstanceid", couseInstance.getId());
			return "redirect:view?cousreid=" + cousreid + "&id="
					+ couseInstance.getId();
		}

		model.addAttribute("statusList", statusList);
		return "tech/courseinstancemanager/view";
	}

	/**
	 * 列举讲师
	 * 
	 * @param model
	 * @param id
	 *            活动编号
	 * @param type
	 *            视图名称：：候选人=user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("listUsers")
	public String listUsers(Model model, @RequestParam("id") String id,
			@RequestParam("courseinstanceid") String courseinstanceid)
	{
		// TechStudentStatus student = null;

		List<EcanUser> users = null;
		TechCouseInstance courseinstance = (TechCouseInstance) commonService
				.get(courseinstanceid, TechCouseInstance.class);

		List<TechStudentStatus> studentList = commonService.list(
				"from TechStudentStatus t where t.courseInstanceId=?", id);

		Set<String> studentSet = new HashSet<String>();

		for (TechStudentStatus student : studentList)
		{
			studentSet.add(student.getStudentId());
		}

		// 选择候选人
		users = this.userService.listByProType(courseinstance.getProType(),
				TechConsts.Role.STUDENT);
		for (Iterator<EcanUser> it = users.iterator(); it.hasNext();)
		{ // 过滤掉已经选择的学员
			EcanUser u = it.next();
			if (studentSet.contains(u.getId()))
			{
				it.remove();
			}
		}

		model.addAttribute("list", users);
		model.addAttribute("courseinstanceid", courseinstanceid);
		return "tech/testactive/selectuser";
	}

	/**
	 * 
	 * @param model
	 * @param courseid
	 *            课件编号
	 * @param protype
	 *            题目类别
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("listTitle")
	public String listTitle(Model model,
			@RequestParam("courseid") String courseid,
			@RequestParam("protype") String protype)
	{

		@SuppressWarnings("unused")
		List<TechCourseTesting> testingList = commonService.list(
				"from TechCourseTesting t where t.courseId=?", courseid);

		@SuppressWarnings("unused")
		List<TechCourseTestingDb> testingdbList = commonService.list(
				"from TechCourseTestingDb t where t.status=? and t.proType=?",
				CoreConsts.YORN.YES, protype);
		Set<String> testingSet = new HashSet<String>();
		for (TechCourseTesting techCourseTestingDb : testingList)
		{
			testingSet.add(techCourseTestingDb.getTitleId());
		}

		for (Iterator iterator = testingdbList.iterator(); iterator.hasNext();)
		{
			TechCourseTestingDb techCourseTestingDb = (TechCourseTestingDb) iterator
					.next();
			if (testingSet.contains(techCourseTestingDb.getId()))
			{
				iterator.remove();
			}

		}
		model.addAttribute("list", testingdbList);
		model.addAttribute("courseid", courseid);
		model.addAttribute("protype", protype);
		return "tech/courseinstancemanager/selecttitle";
	}

	/**
	 * 添加用户
	 * 
	 * @param userids
	 * @param type
	 *            候选人：user
	 * @param id
	 * @return
	 */
	@RequestMapping("addUsers")
	public @ResponseBody
	AjaxResponse addUsers(Model model, @RequestParam("userids") String userids,
			@RequestParam("id") String courseinstanceid)
	{
		List<TechStudentStatus> studentList = new ArrayList<TechStudentStatus>();
		String uids[] = userids.split(",");
		for (int i = 0; i < uids.length; i++)
		{
			TechStudentStatus student = new TechStudentStatus();
			student.setCourseInstanceId(courseinstanceid);
			student.setStudentId(uids[i]);
			student.setId(null);
			student.setIsFinish(CoreConsts.YORN.NO);
			studentList.add(student);
		}
		commonService.saveOrUpdateTX(studentList);
		model.addAttribute("courseinstanceid", courseinstanceid);
		return new AjaxResponse();
	}

	/**
	 * 添加课后测试题
	 * 
	 * @param userids
	 * @param type
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("addTitle")
	public @ResponseBody
	AjaxResponse addTitle(Model model,
			@RequestParam("courseid") String courseid,
			@RequestParam("titleid") String titleid)
	{
		@SuppressWarnings("unused")
		List<TechCourseTesting> courseList = commonService.list(
				"from TechCourseTesting t where t.courseId=?", courseid);
		int idx = 1;
		List<TechCourseTesting> addList = new ArrayList<TechCourseTesting>();
		if (courseList != null && courseList.size() > 0)
		{
			idx = courseList.size() + 1;
		}
		String[] ids = titleid.split(",");
		for (int i = 0; i < ids.length; i++)
		{
			TechCourseTesting testing = new TechCourseTesting();
			testing.setCourseId(courseid);
			testing.setIdx(idx);
			testing.setTitleId(ids[i]);
			testing.setId(null);
			addList.add(testing);
		}
		commonService.saveOrUpdateTX(addList);
		return new AjaxResponse();
	}

	/**
	 * 已分派课程列表,进入后仅可编辑，编辑时，应提示用户将清空学员学习记录
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("dispached")
	public @ResponseBody
	AjaxResponse dispached(Model model,
			@RequestParam(value = "id", required = true) String id)
	{
		TechCouseInstance courseInstance = (TechCouseInstance) commonService
				.get(id, TechCouseInstance.class);
		courseInstance.setStatus(CoreConsts.YORN.NO);
		commonService.updateTX(courseInstance);
		return new AjaxResponse();
	}

	/**
	 * 已分派课程列表,进入后仅可编辑，编辑时，应提示用户将清空学员学习记录
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("ing")
	public @ResponseBody
	AjaxResponse ing(Model model,
			@RequestParam(value = "id", required = true) String id)
	{
		TechCouseInstance courseInstance = (TechCouseInstance) commonService
				.get(id, TechCouseInstance.class);
		courseInstance.setStatus(CoreConsts.COURSESTATUS.ING);
		commonService.updateTX(courseInstance);
		return new AjaxResponse();
	}

	/**
	 * 
	 * 删除活动
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("del")
	public @ResponseBody
	AjaxResponse del(@RequestParam(value = "id", required = true) String id)
	{
		commonService.deleteTX(TechCouseInstance.class, id);
		@SuppressWarnings("unused")
		List<TechStudentStatus> studentlist = commonService.list(
				"from TechStudentStatus t where t.courseInstanceId=?", id);
		commonService.deleteAllTX(studentlist);
		return new AjaxResponse();
	}

	/**
	 * 查看分派信息或创建一个分派
	 * 
	 * @param model
	 * @param courseId
	 *            课件编号
	 * @param courseInstanceId
	 *            课程编号为空时，表示根据课件编号创建一个课程
	 * @return
	 */
	public String dispach(
			Model model,
			@RequestParam(value = "courseId", required = false) String courseId,
			@RequestParam(value = "courseInstanceId", required = false) String courseInstanceId)
	{
		return null;
	}

	/**
	 * 保存课程实例信息::::新的分派课程的ID为空， 非空时表示编辑，编辑时，应该在页面提示用户编辑已开始的课程将清空用户学习信息
	 * 
	 * @param model
	 * @param startDate
	 * @param endDate
	 * @param courseId
	 * @param name
	 * @param remark
	 * @return courseInstanceId 402865813451729f0134517372e00001 expireTime
	 *         2011-12-27 name Maritime Navigation Course remark
	 *         将为读者奉献三个显著特色一、强调和普遍性
	 *         ，在突出销售沟通，销售技巧的同时，以中等价位的车辆为基础，以提高更广大的销售顾问提高销售技能为目的。 startTime
	 *         2011-12-18
	 */
	@RequestMapping("save")
	public @ResponseBody
	AjaxResponse save(Model model,
			@RequestParam(value = "startTime", required = true) Date startDate,
			@RequestParam("expireTime") Date endDate,
			@RequestParam("courseInstanceId") String courseId,
			@RequestParam("name") String name,
			@RequestParam("remark") String remark)
	{

		if (endDate == null)
		{
			endDate = new Date();
		}
		TechCouseInstance course = (TechCouseInstance) commonService.get(
				courseId, TechCouseInstance.class);
		course.setCreateTime(startDate);
		course.setExpireTime(endDate);
		course.setName(name);
		course.setStatus(CoreConsts.COURSESTATUS.NOING);
		course.setRemark(remark);
		commonService.updateTX(course);
		return new AjaxResponse();
	}

	/**
	 * 保存课程实例信息::::新的分派课程的ID为空， 非空时表示编辑，编辑时，应该在页面提示用户编辑已开始的课程将清空用户学习信息
	 * 
	 * @param model
	 * @param startDate
	 * @param endDate
	 * @param courseId
	 * @param name
	 * @param remark
	 * @return courseInstanceId 402865813451729f0134517372e00001 expireTime
	 *         2011-12-27 name Maritime Navigation Course remark
	 *         将为读者奉献三个显著特色一、强调和普遍性
	 *         ，在突出销售沟通，销售技巧的同时，以中等价位的车辆为基础，以提高更广大的销售顾问提高销售技能为目的。 startTime
	 *         2011-12-18
	 */
	@RequestMapping("saveAll")
	public @ResponseBody
	AjaxResponse saveAll(Model model,
			@RequestParam(value = "startTime", required = true) Date startDate,
			@RequestParam("expireTime") Date endDate,
			@RequestParam("courseInstanceId") String courseId,
			@RequestParam("name") String name,
			@RequestParam("remark") String remark)
	{

		if (endDate == null)
		{
			endDate = new Date();
		}
		TechCouseInstance icourse = (TechCouseInstance) commonService.get(
				courseId, TechCouseInstance.class);
		icourse.setCreateTime(startDate);
		icourse.setExpireTime(endDate);
		icourse.setName(name);
		icourse.setStatus(CoreConsts.YORN.YES);
		icourse.setRemark(remark);
		TechCourse course = (TechCourse) commonService.get(icourse
				.getCourseId(), TechCourse.class);
		course.setStatus(CoreConsts.YORN.YES);
		commonService.updateTX(icourse);
		commonService.updateTX(course);
		return new AjaxResponse();
	}

	/**
	 * 删除候选人
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("delUsers")
	public @ResponseBody
	AjaxResponse delUsers(@RequestParam("id") String id)
	{
		List<TechStudentStatus> studentList = commonService.list(
				"from TechStudentStatus t where t.id=?", id);
		if (studentList != null)
		{
			this.commonService.deleteAllTX(studentList);
		}
		return new AjaxResponse();
	}

	/**
	 * 删除课后试题
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("delTitle")
	public @ResponseBody
	AjaxResponse delTitle(@RequestParam("id") String id,
			@RequestParam(value = "courseId", required = true) String courseId)
	{

		@SuppressWarnings("unused")
		List<TechCourseTesting> testtingList = commonService
				.list(
						"from TechCourseTesting t where t.courseId=? and t.titleId = ?",
						courseId, id);
		if (testtingList != null)
		{
			this.commonService.deleteAllTX(testtingList);
		}
		return new AjaxResponse();
	}

}
