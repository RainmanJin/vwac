package com.ecannetwork.tech.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.i18n.I18N;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.ExecuteContext;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.TechTest;
import com.ecannetwork.dto.tech.TechTestAnswer;
import com.ecannetwork.dto.tech.TechTestDb;
import com.ecannetwork.dto.tech.TechTrainCourse;
import com.ecannetwork.tech.service.TestDbService;

@Controller
@RequestMapping("/testdb")
public class TestDbController
{
	@Autowired
	private CommonService commonService;
	@Autowired
	private TestDbService dbService;

	/**
	 * 
	 * 查询试题库数据
	 * 
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("index")
	public String index(Model model)
	{
		// List<TechTestDb> list = commonService.list(
		// "from TechTestDb t where t.status = ?",
		// CoreConsts.dbstatus.PUBLISH);
		List<TechTestDb> list = commonService.list("from TechTestDb t order by t.createTime desc");
		List<TechTrainCourse> courseList = commonService.list("from TechTrainCourse tech where tech.status=?", CoreConsts.dbstatus.PUBLISH);
		model.addAttribute("courseList", courseList);// 用于绑定界面上查询条的下拉列表控件
		model.addAttribute("dblist", list);
		return "tech/testdb/index";
	}

	/**
	 * 删除试题
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("del")
	public @ResponseBody
	AjaxResponse del(Model model, @RequestParam(value = "id", required = true) String id)
	{
		TechTestDb techTestDb = (TechTestDb) commonService.get(id, TechTestDb.class);
		if (techTestDb != null)
		{
			List<TechTest> list = commonService.list("from TechTest t where t.testAcviceTitleId=?", techTestDb.getTrainCourseId());
			if (list != null && list.size() > 0)
			{
				return new AjaxResponse(false, "i18n.commit.del.desc");
			}
			else
			{
				commonService.deleteTX(TechTestDb.class, id);
				commonService.deleteAllTX(commonService.list("from TechTestAnswer t where t.testingId=?", id));
				return new AjaxResponse(true, "i18n.commit.del.success");
			}
		}
		return new AjaxResponse(true, "i18n.commit.del.success");
	}

	/**
	 * 
	 * 停用
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("disable")
	public @ResponseBody
	AjaxResponse disable(Model model, @RequestParam(value = "id", required = true) String id)
	{

		TechTestDb db = (TechTestDb) commonService.get(id, TechTestDb.class);
		db.setStatus(CoreConsts.YORN.NO);
		commonService.saveOrUpdateTX(db);
		return new AjaxResponse(true, "i18n.commit.success");
	}

	/**
	 * 激活 启用
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("active")
	public @ResponseBody
	AjaxResponse active(Model model, @RequestParam(value = "id", required = true) String id)
	{

		TechTestDb db = (TechTestDb) commonService.get(id, TechTestDb.class);
		db.setStatus(CoreConsts.YORN.YES);
		commonService.saveOrUpdateTX(db);
		return new AjaxResponse(true, "i18n.commit.success");
	}

	/**
	 * 
	 * 评测活动题目保存
	 * 
	 * @param model
	 * @param techTestDb
	 * @param result
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("save")
	public @ResponseBody
	AjaxResponse save(Model model, @Valid TechTestDb techTestDb, BindingResult result, HttpServletRequest request)
	{
		EcanUser user = ExecuteContext.user();
		Map<String, String> titleMap = new HashMap<String, String>();
		Map<String, String> rightMap = new HashMap<String, String>();

		if (!result.hasErrors())
		{
			if (StringUtils.isBlank(techTestDb.getId()))
			{
				techTestDb.setId(null);
			}
			techTestDb.setCreateTime(new Date());
			techTestDb.setCreateUserId(user.getId());
			Enumeration<String> en = request.getParameterNames();
			while (en.hasMoreElements())
			{
				String name = en.nextElement();
				if (name.startsWith("t_"))
				{
					String[] titleName = name.split("t_");
					String value = request.getParameter(name);
					System.out.println("name \r\t" + name + "\r\t" + "value \r\t" + value);
					if (titleName.length > 1 && StringUtils.isNotBlank(value.trim()))
					{
						titleMap.put(titleName[1], value);
					}
				}
				if (name.startsWith("c_"))
				{
					String[] names = name.split("c_");
					System.out.println("c_  \r\t" + names[1]);
					if (names.length > 1)
					{
						rightMap.put(names[1], "1");
					}

				}
			}

			List<TechTestAnswer> techTestAnswerList = judge(titleMap, rightMap, techTestDb.getId());

			// 检查是否有正确答案
			boolean hasRight = false;
			for (TechTestAnswer ans : techTestAnswerList)
			{
				if (CoreConsts.YORN.YES.equals(ans.getIsRight()))
				{
					hasRight = true;
					break;
				}
			}

			if (!hasRight)
			{
				return new AjaxResponse(false, I18N.parse("i18n.testdb.msg.leastOneRightAns"));
			}

			commonService.saveOrUpdateTX(techTestDb);
			for (TechTestAnswer ans : techTestAnswerList)
			{
				ans.setTestingId(techTestDb.getId());
			}
			commonService.deleteAllTX(commonService.list("from TechTestAnswer t where t.testingId=?", techTestDb.getId()));
			commonService.saveOrUpdateTX(techTestAnswerList);
			return new AjaxResponse(true, I18N.parse("i18n.commit.success"));
		}
		else
		{
			return new AjaxResponse(false, I18N.parse("i18n.commit.error"));
		}
	}

	/**
	 * 
	 * 保存答案表操作
	 * 
	 * @param titlemap
	 *            题干的map
	 * @param rightMap
	 *            是否为正确答案map
	 * @param testId
	 *            试题编号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TechTestAnswer> judge(Map<String, String> titlemap, Map<String, String> rightMap, String testId)
	{
		String checkString = "";
		List<TechTestAnswer> techTestAnswerList = new ArrayList<TechTestAnswer>();
		TechTestAnswer answer = null;
		Integer idx = new Integer(1);
		for (Map.Entry<String, String> entry : titlemap.entrySet())
		{
			checkString = rightMap.get(entry.getKey());
			answer = new TechTestAnswer();
			if (checkString != null)
			{
				answer.setIsRight(CoreConsts.dbAnswerstatus.YES);
			}
			else
			{
				answer.setIsRight(CoreConsts.dbAnswerstatus.NO);
			}
			answer.setId(null);
			answer.setIdx(idx);
			answer.setTitle(entry.getValue());
			techTestAnswerList.add(answer);
			idx++;
		}
		return techTestAnswerList;
	}

	/**
	 * 
	 * 编辑、查看方法
	 * 
	 * @param model
	 * @param id
	 * @return
	 */

	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping("view")
	public String view(Model model, @RequestParam(value = "id", required = true) String id)
	{
		List<TechTest> list = commonService.list("from TechTest t where t.testAcviceTitleId=?", id);
		if (list != null && list.size() > 0)
		{
			model.addAttribute("YON", CoreConsts.YORN.NO);
		}
		else
		{
			model.addAttribute("YON", CoreConsts.YORN.YES);
		}
		TechTestDb db = dbService.list(id);
		model.addAttribute("dto", db);
		return "tech/testdb/view";
	}

	/**
	 * 
	 * 编辑、查看方法
	 * 
	 * @param model
	 * @param id
	 * @return
	 */

	@SuppressWarnings("unused")
	@RequestMapping("add")
	public String add(Model model)
	{
		return "tech/testdb/save";
	}
}
