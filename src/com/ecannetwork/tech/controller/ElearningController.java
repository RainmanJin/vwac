package com.ecannetwork.tech.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.ExecuteContext;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.TechCourse;
import com.ecannetwork.dto.tech.TechCourseAttchment;
import com.ecannetwork.dto.tech.TechCourseItem;
import com.ecannetwork.dto.tech.TechCourseTesting;
import com.ecannetwork.dto.tech.TechCourseTestingAnswer;
import com.ecannetwork.dto.tech.TechCourseTestingDb;
import com.ecannetwork.dto.tech.TechCouseInstance;
import com.ecannetwork.dto.tech.TechCouseInstanceStudentStauts;
import com.ecannetwork.dto.tech.TechStudentScoitem;
import com.ecannetwork.dto.tech.TechStudentStatus;
import com.ecannetwork.dto.tech.TechTestingInstance;
import com.ecannetwork.dto.tech.TechTestingStudentRecord;
import com.ecannetwork.tech.service.LmsService;

/**
 * 个人教室:::在线学习
 * 
 * @author leo
 * 
 */
@Controller
@RequestMapping("/elearning")
public class ElearningController
{

	@Autowired
	private CommonService commonService;
	@Autowired
	private LmsService lmsService;

	private static DecimalFormat decimalFormat = new DecimalFormat("####.##");
	private static final String TESTTING_SESSION_KEY = "testtingSession";

	/**
	 * 学习中心首页
	 * 
	 * @param model
	 * @param type
	 *            进行中, 已完成， 已过期
	 * @return
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping("index")
	public String index(Model model,
	        @RequestParam(value = "type", required = false)
	        String type)
	{

		EcanUser user = ExecuteContext.user();
		// 查询出来全部的课程

		List<TechCouseInstanceStudentStauts> expireITechCouseInstanceStudentStauts = commonService
		        .list(
		                "select new com.ecannetwork.dto.tech.TechCouseInstanceStudentStauts(a,b) from "
		                        + "TechCouseInstance a,TechStudentStatus b where a.id=b.courseInstanceId and a.status =? and a.expireTime <? and b.studentId=?",
		                CoreConsts.COURSESTATUS.ING, new Date(), user.getId());

		List<TechCouseInstanceStudentStauts> ingITechCouseInstanceStudentStauts = commonService
		        .list(
		                "select new com.ecannetwork.dto.tech.TechCouseInstanceStudentStauts(a,b) from "
		                        + "TechCouseInstance a,TechStudentStatus b where a.id=b.courseInstanceId and a.status=? and a.expireTime >? and b.studentId=?",
		                CoreConsts.COURSESTATUS.ING, new Date(), user.getId());

		// 查出进行中的课程是否有测试题
		for (TechCouseInstanceStudentStauts techCouseInstanceStudentStauts : ingITechCouseInstanceStudentStauts)
		{
			String courseId = techCouseInstanceStudentStauts
			        .getCourseInstance().getCourseId();
			if (courseId != null)
			{
				@SuppressWarnings("unused")
				List<TechCourseTesting> courseTesingList = commonService
				        .list("from TechCourseTesting t where t.courseId=?",
				                courseId);
				if (courseTesingList != null && courseTesingList.size() > 0)
				{
					techCouseInstanceStudentStauts.getCourseInstance()
					        .setHasDbs(true);
				}
			}
		}
		model.addAttribute("expireInstance",
		        expireITechCouseInstanceStudentStauts);
		model.addAttribute("ingInstance", ingITechCouseInstanceStudentStauts);
		model.addAttribute("srole",user.getRoleId());

		return "tech/elearning/index";
	}

	/**
	 * 开始学习
	 * 
	 * @param model
	 * @param id
	 *            课件编号
	 * @param courseInstanceId
	 *            课程编号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("study")
	public String study(Model model, @RequestParam("id")
	String id, @RequestParam("courseInstanceId")
	String courseInstanceId)
	{
		TechCourse course = (TechCourse) this.commonService.get(id,
		        TechCourse.class);
		List<TechCourseItem> items = this.commonService.list(
		        "from TechCourseItem t where t.courseId=? order by t.idx", id);

		// 查询出所有的学习记录
		List<TechStudentScoitem> studyRecords = this.commonService
		        .list(
		                "from TechStudentScoitem t where t.studentId = ? and t.courseInstanceId = ?",
		                ExecuteContext.user().getId(), courseInstanceId);

		Set<String> itemids = new HashSet<String>();
		for (TechStudentScoitem item : studyRecords)
		{
			itemids.add(item.getItemId());
		}

		for (TechCourseItem item : items)
		{
			if (itemids.contains(item.getId()))
			{// 标记该章节已经学习
				item.addExts("hasStudy", Boolean.TRUE);
			}
		}

		model.addAttribute("dto", course);
		model.addAttribute("items", items);

		// 查询测试题数量
		List<TechCourseTesting> testQues = this.commonService.list(
		        "from TechCourseTesting t where t.courseId = ?", id);
		model.addAttribute("questionsCount", testQues.size());

		return "tech/elearning/study";
	}

	/**
	 * 课程学习记录
	 * 
	 * @param courseInstanceId
	 * @param courseId
	 * @param channelId
	 * @param key
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("setStudyRecord")
	public @ResponseBody
	AjaxResponse setStudyRecord(@RequestParam("courseInstanceId")
	String courseInstanceId, @RequestParam("courseId")
	String courseId, @RequestParam("channelId")
	String channelId, @RequestParam("key")
	String key, @RequestParam(value = "value", required = false)
	String value)
	{
		// 存储lms值
		lmsService.setValueTX(courseInstanceId, channelId, key, ExecuteContext
		        .user().getId(), value);

		List<TechStudentScoitem> studyRecords = this.commonService
		        .list(
		                "from TechStudentScoitem t where t.studentId = ? and t.itemId = ? and t.courseInstanceId = ?",
		                ExecuteContext.user().getId(), channelId,
		                courseInstanceId);

		if (studyRecords.size() == 0)
		{// 没有学习过，插入一条学习记录
			TechStudentScoitem dto = new TechStudentScoitem();
			dto.setCourseId(courseId);
			dto.setCourseInstanceId(courseInstanceId);
			dto.setItemId(channelId);
			dto.setStudentId(ExecuteContext.user().getId());
			this.commonService.saveTX(dto);
		}

		List<TechStudentStatus> studentStatus = commonService
		        .list(
		                "from TechStudentStatus t where t.courseInstanceId = ? and t.studentId = ?",
		                courseInstanceId, ExecuteContext.user().getId());

		if (studentStatus != null && studentStatus.size() > 0)
		{
			TechStudentStatus student = studentStatus.get(0);
			if (student.getStudentCoursePro() == null
			        || !student.getStudentCoursePro().equals(new Double(100)))
			{
				List<TechStudentScoitem> studyRecordScoItemList = this.commonService
				        .list(
				                "from TechStudentScoitem t where t.studentId = ? and t.courseInstanceId = ?",
				                ExecuteContext.user().getId(), courseInstanceId);
				List<TechCourseItem> items = this.commonService
				        .list(
				                "from TechCourseItem t where t.courseId=? order by t.idx",
				                courseId);

				String pro = decimalFormat.format((studyRecordScoItemList
				        .size() * 100)
				        / items.size());

				student.setStudentCoursePro(Double.valueOf(pro));
				commonService.updateTX(student);

			}
		}

		return new AjaxResponse();
	}

	/**
	 * 获取lms学习记录的值（课件中定义的）
	 * 
	 * @param courseInstanceId
	 * @param channelId
	 * @param key
	 * @return
	 */
	@RequestMapping("getStudyRecord")
	public @ResponseBody
	AjaxResponse getStudyRecord(@RequestParam("courseInstanceId")
	String courseInstanceId, @RequestParam("channelId")
	String channelId, @RequestParam("key")
	String key)
	{
		String lmsRecord = this.lmsService.getValue(courseInstanceId,
		        channelId, key, ExecuteContext.user().getId());
		AjaxResponse response = new AjaxResponse();

		if (lmsRecord != null)
		{
			response.setData(lmsRecord);
		}

		return response;
	}

	/**
	 * 完成学习
	 * 
	 * @param courseInstanceId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("finishStudy")
	public @ResponseBody
	AjaxResponse finishStudy(@RequestParam("courseInstanceId")
	String courseInstanceId)
	{
		List<TechStudentStatus> studentList = commonService
		        .list(
		                "from TechStudentStatus t where t.courseInstanceId = ? and t.studentId = ?",
		                courseInstanceId, ExecuteContext.user().getId());
		if (studentList != null && studentList.size() > 0)
		{
			TechStudentStatus student = studentList.get(0);
			if (student != null)
			{
				if (student.getStudentCoursePro() == null
				        || !student.getStudentCoursePro().equals(
				                new Double(100.0)))
				{
					student.setStudentCoursePro(new Double(100));
				}
				if (student.getStartTime() == null)
				{
					student.setStartTime(new Date());
				}
			}

			commonService.updateTX(student);
		}

		return new AjaxResponse();
	}

	/**
	 * 开始测试
	 * 
	 * @param model
	 * @param id
	 *            课程编号
	 * @param testtingid
	 *            试卷表的题目编号,可以为空，为空时显示第一题
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("testting")
	public String testting(Model model, @RequestParam("id")
	String id, @RequestParam(value = "idx", required = false)
	String idx, @RequestParam("courseInstanceId")
	String courseInstanceId,
	        @RequestParam(value = "isFinish", required = false)
	        String isFinish, HttpServletRequest request)
	{
		EcanUser user = ExecuteContext.user();

		// 查询当前课程对应的试卷.
		Integer index = 0;

		TechTestingInstance testingInstance = null;
		TechCourseTestingDb db = null;
		TechCouseInstance courseInstance = (TechCouseInstance) commonService
		        .get(courseInstanceId, TechCouseInstance.class);
		if (StringUtils.isBlank(idx))
		{// 开启一个新测试，
			// 创建一个测试实例，并放置在session中,默认开始答第一题
			request.getSession().removeAttribute(TESTTING_SESSION_KEY);
			testingInstance = new TechTestingInstance();
			testingInstance.setCourseId(id);
			testingInstance.setCourseInstanceId(courseInstanceId);
			testingInstance.setIsFinish(CoreConsts.YORN.NO);
			testingInstance.setStudentId(user.getId());
			testingInstance.setTestingPoint(new Double(0));
			testingInstance.setTestingTime(new Date());
			// 存放全部的题
			testingInstance.setTestingDBS(this.listCourseTestDB(id));

			// 获取试卷编号
			if (testingInstance.getTestingDBS() != null
			        && testingInstance.getTestingDBS().size() > 0)
			{

				testingInstance.setCourseTesttingId(this
				        .getTechTesttingId((testingInstance.getTestingDBS()
				                .get(0)).getId()));
			} else
			{
				testingInstance.setCourseTesttingId("");
			}
			request.getSession().setAttribute(TESTTING_SESSION_KEY,
			        testingInstance);
			if (testingInstance.getTestingDBS() != null
			        && testingInstance.getTestingDBS().size() > 0)
				// 获取第一道题
				db = testingInstance.getTestingDBS().get(0);
		} else
		{
			testingInstance = (TechTestingInstance) request.getSession()
			        .getAttribute(TESTTING_SESSION_KEY);

			index = Integer.valueOf(idx);
			db = testingInstance.getTestingDBS().get(index);
		}

		// 已答题数量
		Integer docount = 0;
		for (TechCourseTestingDb dbs : testingInstance.getTestingDBS())
		{
			if (dbs != null && dbs.getHasAns() != null && dbs.getHasAns())
			{
				docount++;
			}
		}

		String currectPoint = decimalFormat
		        .format((docount.doubleValue() / testingInstance
		                .getTestingDBS().size()) * 100);
		List<TechStudentStatus> studentList = commonService
		        .list(
		                "from TechStudentStatus t where t.courseInstanceId=? and t.studentId=? and t.isFinish=?",
		                courseInstance.getId(), user.getId(),
		                CoreConsts.YORN.YES);
		// currect = Double.docount/testingInstance.getTestingDBS().size();
		// big = docount/testingInstance.getTestingDBS().size();
		model.addAttribute("idx", index);
		model.addAttribute("list", testingInstance.getTestingDBS());
		if (studentList != null && studentList.size() > 0)
		{
			model.addAttribute("doTesttingCount", studentList.get(0)
			        .getTestCount());
		}
		model.addAttribute("listsize", testingInstance.getTestingDBS().size());
		model.addAttribute("user", user);
		model.addAttribute("courseInstance", courseInstance);
		model.addAttribute("courseInstanceId", courseInstanceId);
		model.addAttribute("currectPoint", currectPoint);
		model.addAttribute("dto", db);

		return "tech/elearning/testting";
	}

	/**
	 * 
	 * 根据题目ID获取试卷编号
	 * 
	 * @param dbId
	 *            题目编号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getTechTesttingId(String dbId)
	{
		List<TechCourseTesting> techCourseTesting = commonService.list(
		        "from TechCourseTesting t where t.titleId = ?", dbId);
		if (techCourseTesting != null && techCourseTesting.size() > 0)
		{
			return (techCourseTesting.get(0)).getId();
		}
		return null;
	}

	/**
	 * 获取试卷的题干列表， 题干对象中包含所有答案
	 * 
	 * @param courceInstanceId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<TechCourseTestingDb> listCourseTestDB(String courceInstanceId)// 63d87cb156554194a1aa032baebf325b
	{
		List<TechCourseTestingDb> list = this.commonService
		        .list(
		                "select b from TechCourseTesting a, TechCourseTestingDb b where b.id = a.titleId and a.courseId = ?",
		                courceInstanceId);

		// 方便下面答案找题目时的索引
		Map<String, TechCourseTestingDb> temp = new HashMap<String, TechCourseTestingDb>(
		        list.size());

		if (list != null && list.size() > 0)
		{
			StringBuilder sb = new StringBuilder();
			int i = 0;
			for (TechCourseTestingDb db : list)
			{
				i++;
				sb.append("'").append(db.getId()).append("'");
				if (i != list.size())
				{
					sb.append(",");
				}
				temp.put(db.getId(), db);
			}

			List<TechCourseTestingAnswer> ans = this.commonService
			        .list("from TechCourseTestingAnswer t where t.testingId in ("
			                + sb.toString() + ")");

			// 存放题对应的全部答案
			for (TechCourseTestingAnswer an : ans)
			{
				TechCourseTestingDb db = temp.get(an.getTestingId());
				if (db != null)
					db.getAns().add(an);
			}
		}
		return list;
	}

	/**
	 * 保存编号为testtingid的题目答案
	 * 
	 * @param model
	 * @param idx
	 * @param checkAns
	 *            选中的答案编号，可以为空，逗号分隔
	 * @param uncheckAns
	 *            未选中的答案编号，可以为空，逗号分隔
	 * @param request
	 * @return
	 */
	@RequestMapping("saveTestting")
	public @ResponseBody
	AjaxResponse saveTestting(Model model, @RequestParam("idx")
	String idx, @RequestParam("checkAns")
	String checkAns, @RequestParam("uncheckAns")
	String uncheckAns, @RequestParam("courseInstanceId")
	String courseInstanceId, HttpServletRequest request)
	{
		TechTestingInstance testingInstance = (TechTestingInstance) request
		        .getSession().getAttribute(TESTTING_SESSION_KEY);

		TechCourseTestingDb testDb = testingInstance.getTestingDBS().get(
		        Integer.valueOf(idx));

		if (StringUtils.isNotBlank(checkAns))
		{// 保存已选择的答案编号
			// 记录该道题已答过
			testDb.setHasAns(Boolean.TRUE);

			String[] ckAns = checkAns.split(",");
			for (String s : ckAns)
			{
				testingInstance.getUserAns().add(s);
			}
		} else
		{
			testDb.setHasAns(Boolean.FALSE);
		}

		if (StringUtils.isNotBlank(uncheckAns))
		{// 删除未选中的答案编号
			String[] ckAns = uncheckAns.split(",");
			for (String s : ckAns)
			{
				testingInstance.getUserAns().remove(s);
			}
		}

		// 标记已选择的答案
		for (TechCourseTestingAnswer ans : testDb.getAns())
		{
			if (testingInstance.getUserAns().contains(ans.getId()))
			{
				ans.addExts("checked", "checked");
			} else
			{
				ans.removeExts("checked");
			}
		}

		// 已答题数量
		Integer docount = 0;
		for (TechCourseTestingDb dbs : testingInstance.getTestingDBS())
		{
			if (dbs != null && dbs.getHasAns() != null && dbs.getHasAns())
			{
				docount++;
			}
		}

		if (docount == testingInstance.getTestingDBS().size())
		{
			return new AjaxResponse(true, 1);
		} else
		{
			return new AjaxResponse(true, 0);
		}
	}

	/**
	 * 从session中取出答题实例，和所有答题记录,保存并计算得分等
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("finish")
	public @ResponseBody
	AjaxResponse finish(Model model, HttpServletRequest request)
	{
		EcanUser user = ExecuteContext.user();
		TechTestingInstance testingInstance = (TechTestingInstance) request
		        .getSession().getAttribute(TESTTING_SESSION_KEY);

		Double point = new Double(0);
		// 拿到全部的题
		for (TechCourseTestingDb db : testingInstance.getTestingDBS())
		{// 计算分数，每道题
			boolean error = false;
			// 题所有的答案
			for (TechCourseTestingAnswer ans : db.getAns())
			{// 循环每个答案
				if (ans.getIsRight().equals(CoreConsts.YORN.YES))
				{// 正确答案
					if (!testingInstance.getUserAns().contains(ans.getId()))
					{// 没有选择正确答案
						error = true;
						break;
					}
				} else
				{
					if (testingInstance.getUserAns().contains(ans.getId()))
					{// 选择了错误答案
						error = true;
						break;
					}
				}
			}

			if (!error)
			{
				point++;
			}
		}
		String getPoint = "0";
		if (point != 0)
		{
			getPoint = decimalFormat.format((point * 100 / testingInstance
			        .getTestingDBS().size()));
		}

		testingInstance.setTestingPoint(Double.valueOf(getPoint));
		testingInstance.setIsFinish(CoreConsts.YORN.YES);
		commonService.saveTX(testingInstance);

		List<TechTestingStudentRecord> studentRecordList = new ArrayList<TechTestingStudentRecord>();
		// 拿出全部的测试题及其对应的答题选项
		for (TechCourseTestingDb db : testingInstance.getTestingDBS())
		{
			// 取出全部的答案
			for (TechCourseTestingAnswer ans : db.getAns())
			{
				if (testingInstance.getUserAns().contains(ans.getId()))
				{
					TechTestingStudentRecord studentRecord = new TechTestingStudentRecord();
					studentRecord.setAnswerId(ans.getId());
					studentRecord.setCourseId(testingInstance.getCourseId());
					studentRecord.setCourseInstanceId(testingInstance
					        .getCourseInstanceId());
					// 试卷编号
					studentRecord.setCourseTestingId(testingInstance
					        .getCourseTesttingId());
					studentRecord.setStudentId(user.getId());
					studentRecord.setId(null);
					studentRecord.setTestingInstanceId(testingInstance.getId());
					studentRecord.setTitleId(ans.getTestingId());
					studentRecordList.add(studentRecord);
				}
			}
		}
		commonService.saveOrUpdateTX(studentRecordList);
		this.modifyTechStudentStatus(request);
		request.getSession().removeAttribute(TESTTING_SESSION_KEY);
		return new AjaxResponse(true);
	}

	public static void main(String[] args)
	{
		DecimalFormat dft = new DecimalFormat("####.##");
		Integer i = 1;
		Double dd = new Double(100);

		String r = dft.format((i * 100 / 1));
		System.out.println(Double.valueOf(r));
		System.out.println(dd.equals(Double.valueOf(r)));
	}

	/**
	 * 更新或者创建学员进度
	 * 
	 * @param request
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public void modifyTechStudentStatus(HttpServletRequest request)
	{
		EcanUser user = ExecuteContext.user();
		TechTestingInstance testingInstance = (TechTestingInstance) request
		        .getSession().getAttribute(TESTTING_SESSION_KEY);
		// 查询学员学习情况
		List<TechStudentStatus> list = commonService
		        .list(
		                "from TechStudentStatus t where t.courseInstanceId=? and studentId=?",
		                testingInstance.getCourseInstanceId(), user.getId());

		// 如果学员情况不为空那么执行更新操作
		if (list != null && list.size() > 0)
		{
			// 根据学员和课程编号查询全部测试实例
			List<TechTestingInstance> testtingList = commonService
			        .list(
			                "from TechTestingInstance t where t.courseInstanceId=? and t.studentId=? and t.isFinish=?",
			                testingInstance.getCourseInstanceId(),
			                user.getId(), CoreConsts.YORN.YES);
			int count = 0;
			Double avg = new Double(0.00);
			Double highPoint = new Double(0.00);
			Double totlePoint = new Double(0.00);
			if (testtingList != null && testtingList.size() > 0)
			{
				count = testtingList.size();
				for (TechTestingInstance techTestingInstance : testtingList)
				{
					totlePoint = totlePoint
					        + techTestingInstance.getTestingPoint();
					if (highPoint < techTestingInstance.getTestingPoint())
					{
						highPoint = techTestingInstance.getTestingPoint();
					}
				}
				avg = totlePoint / count;
			}

			TechStudentStatus status = list.get(0);
			if (status != null)
			{
				status.setAvgPoint(avg);
				status.setHighPoint(highPoint);
				status.setTestCount(count);
				if (status.getStartTime() == null)
				{
					status.setStartTime(new Date());
				}
				status.setIsFinish(CoreConsts.YORN.YES);
				commonService.updateTX(status);
			}
		} else
		{
			// 为空就创建一个新的。
			TechStudentStatus status = new TechStudentStatus();
			status.setCourseInstanceId(testingInstance.getCourseInstanceId());
			status.setStartTime(new Date());
			status.setTestCount(1);
			status.setStudentId(user.getId());
			status.setHighPoint(testingInstance.getTestingPoint());
			status.setAvgPoint(testingInstance.getTestingPoint());
			status.setIsFinish(CoreConsts.YORN.YES);
			commonService.saveTX(status);

		}
	}

	/**
	 * 
	 * 查看课程对应的附件列表
	 * 
	 * @param model
	 * @param courseId
	 *            课程编号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("listAttchment")
	public String listAttchment(Model model,
	        @RequestParam(value = "courseId", required = true)
	        String courseId)
	{
		List<TechCourseAttchment> attchmentList = commonService.list(
		        "from TechCourseAttchment t where t.courseId=?", courseId);
		model.addAttribute("attchmentList", attchmentList);
		return "tech/elearning/listattchment";
	}

	// @RequestMapping("downLoad")
	// public @ResponseBody
	// AjaxResponse downLoad(Model model,
	// @RequestParam(value = "attchmentid", required = true)
	// String attchmentid, HttpServletRequest request,
	// HttpServletResponse response)
	// {
	// TechCourseAttchment attchment = (TechCourseAttchment) commonService
	// .get(attchmentid, TechCourseAttchment.class);
	//
	// if (attchment != null)
	// {
	// try
	// {
	// String url = attchment.getUrl().replaceAll("\\", "/");
	// InputStream inStream = new FileInputStream(attchment.getUrl());
	// InputStream in = new BufferedInputStream(inStream);
	// File file = new File(attchment.getUrl());
	// String fileName = attchment.getName();
	// byte[] bs = new byte[in.available()];
	// in.read(bs);
	// in.close();
	// response.reset();
	// // 设置response的Header
	// // 使浏览器弹出下载对话框
	// response.addHeader("Content-Disposition",
	// "attachment;filename="
	// + new String(fileName.getBytes(), "UTF-8"));
	// response.addHeader("Content-Length", "" + file.length());
	// // 输出流
	// OutputStream toClient = new BufferedOutputStream(response
	// .getOutputStream());
	// response.setContentType("application/octet-stream");
	// toClient.write(bs);
	// toClient.flush();
	// toClient.close();
	// } catch (Exception e)
	// {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// return new AjaxResponse();
	// }

	/**
	 * 查看已做测试的列表
	 * 
	 * @param mode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("viewTesttingInstances")
	public String viewTesttingInstances(Model mode,
	        @RequestParam("courseInstanceId")
	        String courseInstanceId, @RequestParam("courseId")
	        String courseId)
	{
		EcanUser user = ExecuteContext.user();
		List<TechTestingInstance> testingInstanceList = commonService
		        .list(
		                "from TechTestingInstance t where t.studentId=? and t.courseInstanceId =? and t.courseId=? order by t.testingTime desc",
		                user.getId(), courseInstanceId, courseId);
		mode.addAttribute("list", testingInstanceList);
		return "tech/elearning/viewtesttinglist";
	}

	/**
	 * 
	 * 查看某一个测试实例的答案比对
	 * 
	 * @param model
	 * @param testtingInstanceId
	 *            测试实例编号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("viewTestting")
	public String viewTestting(Model model, @RequestParam("testtingInstanceId")
	String testtingInstanceId, @RequestParam(value = "idx", required = false)
	Integer idx, HttpServletRequest request)
	{
		EcanUser user = ExecuteContext.user();
		// 获取测试实例
		TechTestingInstance test = (TechTestingInstance) request.getSession()
		        .getAttribute("_testtingInstance");

		if (test == null || !test.getId().equals(testtingInstanceId))
		{// 第一次进入或进入其他的实例

			test = (TechTestingInstance) commonService.get(testtingInstanceId,
			        TechTestingInstance.class);

			// 获取到全部的题目 4028658134734bba01347378723c0006
			test.setTestingDBS(this.listCourseTestDB(test.getCourseId()));

			// 查出当前测试实例用户选择的全部题答案

			List<TechTestingStudentRecord> testingStudentRecord = commonService
			        .list(
			                "from TechTestingStudentRecord t where t.studentId=? and t.testingInstanceId=?",
			                user.getId(), test.getId());

			Set<String> set = new HashSet<String>();

			// 遍历出用户选择的题目对应的答案
			for (TechTestingStudentRecord techTestingStudentRecord : testingStudentRecord)
			{
				set.add(techTestingStudentRecord.getAnswerId());
			}

			// 标记题干中，答案是否选择了
			for (TechCourseTestingDb techCourseTestingDb : test.getTestingDBS())
			{// 循环所有题干
				for (TechCourseTestingAnswer ans : techCourseTestingDb.getAns())
				{// 循环所有答案

					if (set.contains(ans.getId()))
					{
						ans.setChecked(Boolean.TRUE);
					}
				}
			}

			request.getSession().setAttribute("_testtingInstance", test);
		}

		if (idx == null)
		{// 第一次进入默认选中第一道题目
			idx = 0;
		}
		TechCouseInstance courseInstance = (TechCouseInstance) commonService
		        .get(test.getCourseInstanceId(), TechCouseInstance.class);
		model.addAttribute("list", test.getTestingDBS());
		TechCourseTestingDb db = test.getTestingDBS().get(idx);
		model.addAttribute("idx", idx);
		model.addAttribute("dto", db);
		model.addAttribute("courseInstance", courseInstance);

		/**
		 * <pre>
		 * 	&lt;c:if test=&quot;${('1' eq ans.isRight &amp;&amp; ans.checked) ||}&quot;&gt;
		 * 	green
		 * &lt;/c:if&gt;
		 * 
		 * 	&lt;c:if test=&quot;${('0' eq ans.isRight &amp;&amp; ans.checked) || ('0' eq ans.isRight &amp;&amp; !ans.checked)}&quot;&gt;
		 * red
		 * &lt;/c:if&gt;
		 * 
		 * 
		 * </pre>
		 */

		return "tech/elearning/viewtesttinginstance";
	}

	/**
	 * 查看已做测试实例
	 * 
	 * @param mode
	 * @param id
	 *            测试实例编号
	 * @param testtingid
	 *            题目编号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("viewTesttingInstance")
	public String viewTesttingInstance(Model model,
	        @RequestParam("courseInstanceId")
	        String courseInstanceid)
	{
		EcanUser user = ExecuteContext.user();

		List<TechTestingInstance> testtingList = commonService
		        .list(
		                "from TechTestingInstance t where t.studentId=? and t.courseInstanceId=? and t.isFinish=?",
		                user.getId(), courseInstanceid, CoreConsts.YORN.YES);

		TechCouseInstance courseInstance = (TechCouseInstance) commonService
		        .get(courseInstanceid, TechCouseInstance.class);

		List<TechStudentStatus> statusList = commonService
		        .list(
		                "from TechStudentStatus t where t.courseInstanceId=? and t.studentId=?",
		                courseInstanceid, user.getId());
		if (statusList != null && statusList.size() > 0)
		{
			model.addAttribute("studentstatus", statusList.get(0));
		}
		Double correctRate = new Double(0.00);
		for (TechTestingInstance techTestingInstance : testtingList)
		{
			correctRate = correctRate + techTestingInstance.getTestingPoint();
		}
		if (!correctRate.equals(new Double(0)))
		{
			model.addAttribute("correctRate", decimalFormat.format(correctRate
			        / testtingList.size()));
		} else
		{
			model.addAttribute("correctRate", "0");
		}
		model.addAttribute("courseInstance", courseInstance);
		return "tech/elearning/viewtestting";
	}

}
