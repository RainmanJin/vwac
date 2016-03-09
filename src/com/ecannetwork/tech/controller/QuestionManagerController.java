package com.ecannetwork.tech.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.i18n.I18N;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.ExecuteContext;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.TechQuestionManager;
import com.ecannetwork.dto.tech.TechQuestionSubitems;
import com.ecannetwork.dto.tech.TechSubitmesDb;
import com.ecannetwork.dto.tech.TechTrainPlanUser;

@Controller
@RequestMapping("questionmanager")
public class QuestionManagerController
{
	@Autowired
	private CommonService commonService;
	private static DecimalFormat decimalFormat = new DecimalFormat("####.##");

	/**
	 * 查看问卷管理列表
	 * 
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("index")
	public String index(Model model)
	{
		List<TechQuestionManager> list = commonService
		        .list(TechQuestionManager.class);

		List<TechSubitmesDb> sublistdb = commonService
		        .list(TechSubitmesDb.class);
		Set<String> set = new HashSet<String>();
		for (TechSubitmesDb techSubitmesDb : sublistdb)
		{
			set.add(techSubitmesDb.getQuestionId());
		}

		// 具体是否有答题 如果有页面不显示删除和编辑

		for (TechQuestionManager techQuestionManager : list)
		{
			if (set.contains(techQuestionManager.getId()))
			{
				techQuestionManager.setIs_Answer(true);
			}
		}
		model.addAttribute("list", list);
		return "tech/questionmanager/index";
	}

	/**
	 * 查看问卷
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("view")
	public String view(@RequestParam(value = "id", required = false)
	String id, Model model)
	{
		if (id != null && !"".equals(id))
		{
			TechQuestionManager manager = (TechQuestionManager) commonService
			        .get(id, TechQuestionManager.class);
			model.addAttribute("manager", manager);
		}
		return "tech/questionmanager/view";
	}

	/**
	 * 
	 * 增加、修改问卷管理
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("add")
	public @ResponseBody
	AjaxResponse add(@RequestParam(value = "id", required = false)
	String id, @RequestParam("questionname")
	String questionName)
	{
		TechQuestionManager manager = null;
		EcanUser user = ExecuteContext.user();
		if (id != null && !"".equals(id))
		{
			manager = (TechQuestionManager) commonService.get(id,
			        TechQuestionManager.class);
			if (manager != null)
			{
				manager.setQuestionName(questionName);
				addsubitmes(manager.getId());
			}
		} else
		{
			manager = new TechQuestionManager();
			manager.setId(null);
			manager.setQuestionName(questionName);
			manager.setQuestionDate(new Date());
			manager.setQuestionStatus(CoreConsts.dbstatus.PUBLISH);
			manager.setQuestionUser(user.getId());
		}
		commonService.saveOrUpdateTX(manager);
		return new AjaxResponse();
	}

	/**
	 * 自动把最终评价题目添加
	 * 
	 * @param questionid
	 */
	public void addsubitmes(String questionid)
	{
		TechQuestionSubitems subitems = new TechQuestionSubitems();
		subitems.setId(null);
		subitems.setQuestionId(questionid);
		subitems.setSubitmesIdx(100);
		subitems.setSubitmesName(I18N.parse("i18n.question.topiccontent"));
		subitems.setSubitmeType("5");
		commonService.saveTX(subitems);

	}

	/**
	 * 禁用问卷
	 * 
	 * @param id
	 * @return
	 */

	@RequestMapping("disable")
	public @ResponseBody
	AjaxResponse disable(@RequestParam("id")
	String id)
	{
		TechQuestionManager manager = (TechQuestionManager) commonService.get(
		        id, TechQuestionManager.class);
		if (manager != null)
		{
			manager.setQuestionStatus(CoreConsts.dbstatus.NOVILID);
			commonService.saveOrUpdateTX(manager);
		}
		return new AjaxResponse();
	}

	/**
	 * 
	 * 启用问卷
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("canable")
	public @ResponseBody
	AjaxResponse canable(@RequestParam("id")
	String id)
	{
		TechQuestionManager manager = (TechQuestionManager) commonService.get(
		        id, TechQuestionManager.class);
		if (manager != null)
		{
			manager.setQuestionStatus(CoreConsts.dbstatus.PUBLISH);
			commonService.saveOrUpdateTX(manager);
		}
		return new AjaxResponse();
	}

	/**
	 * 删除问卷
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("del")
	public @ResponseBody
	AjaxResponse del(@RequestParam("id")
	String id)
	{
		TechQuestionManager manager = (TechQuestionManager) commonService.get(
		        id, TechQuestionManager.class);
		if (manager != null)
		{
			// if (!judeQuestion(manager.getId()))
			// {
			// return new AjaxResponse();
			// }
			commonService.deleteTX(manager);
		}
		return new AjaxResponse();
	}

	/**
	 * 
	 * 预览问答
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("preview")
	public String preview(@RequestParam("id")
	String id, Model model)
	{
		EcanUser user = ExecuteContext.user();
		List<TechQuestionSubitems> listSub = commonService
		        .list(
		                "from TechQuestionSubitems t where t.questionId=? order by subitmesIdx",
		                id);
		model.addAttribute("list", listSub);
		model.addAttribute("questionId", id);
		model.addAttribute("user", user);
		return "tech/questionmanager/preview";
	}

	/**
	 * 
	 * 列出用户并查处题目
	 * 
	 * @param id
	 *            问卷ID
	 * @param planid
	 *            培训ID
	 * 
	 * @param trainCourseId
	 *            培训课程ID
	 * @param model
	 * 
	 * ff80808135e90b410135eb1ca8d4000b
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("pretest")
	public String pretest(@RequestParam("id")
	String id, @RequestParam("planid")
	String planid, Model model)
	{

		List<TechTrainPlanUser> userList = commonService.list(
		        "from TechTrainPlanUser t where t.planId=?", planid);

		System.out.println("===========" + userList.size());
		@SuppressWarnings("unused")
		List<TechSubitmesDb> subitemsList = commonService.list(
		        "from TechSubitmesDb t where t.planId=? and t.questionId=?",
		        planid, id);
		Set<String> subSet = new HashSet<String>();
		for (TechSubitmesDb techSubitmesDb : subitemsList)
		{
			subSet.add(techSubitmesDb.getDbAnswerUser());
		}

		for (TechTrainPlanUser techTrainPlanUser : userList)
		{
			if (subSet.contains(techTrainPlanUser.getUserId()))
			{
				techTrainPlanUser
				        .setFinish(CoreConsts.QUESTIONSTATUS.QUESTIONFINISH);
			} else
			{
				techTrainPlanUser
				        .setFinish(CoreConsts.QUESTIONSTATUS.QUESTIONUNFINISH);
			}
		}

		model.addAttribute("questionId", id); // 40286581374646b201374647051f0001
		model.addAttribute("userList", userList);
		model.addAttribute("planid", planid);
		return "tech/questionmanager/pretest";
	}

	public static void main(String[] args)
	{
		// Set<String> hashSet = new HashSet<String>();
		// hashSet.add("1");
		// hashSet.add("2");
		// hashSet.add("1");
		//
		// System.out.println("===========" + hashSet.size());
		//
		// for (String string : hashSet)
		// {
		// System.out.println(string);
		// }

		float i = 1;
		float k = 3;
		float f = i / k;
		Float ff = new Float(1);
		Float fff = new Float(3);

		String s = decimalFormat.format((ff / fff * 100));
		System.out.println("===========" + s);
	}

	/**
	 * 
	 * 预览问答
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("beginTest")
	public String beginTest(@RequestParam("id")
	String id, @RequestParam("userid")
	String userid, @RequestParam("planid")
	String planid, Model model)
	{
		List<TechQuestionSubitems> listSub = commonService
		        .list(
		                "from TechQuestionSubitems t where t.questionId=? order by subitmesIdx",
		                id);
		model.addAttribute("list", listSub);
		model.addAttribute("questionId", id);
		model.addAttribute("userid", userid);
		model.addAttribute("planid", planid);
		return "tech/questionmanager/test";
	}

	/**
	 * 查询是否满足条件
	 * 
	 * @param id
	 *            问卷ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean judeQuestion(String id)
	{

		List<TechSubitmesDb> list = commonService.list(
		        "from TechSubitmesDb t where t.questionId = ?", id);
		if (list != null && list.size() > 0)
		{
			return false;
		}
		return true;
	}

	/**
	 * 
	 * 
	 * @param id
	 *            问卷ID
	 * @param model
	 * @return
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping("subindex")
	public String subindex(@RequestParam("id")
	String id, Model model)
	{

		List<TechQuestionSubitems> sublist = commonService.list(
		        "from TechQuestionSubitems t where t.questionId=?", id);
		model.addAttribute("sublist", sublist);
		return "tech/question/subindex";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("sublist")
	public String sublist(@RequestParam(value = "id", required = false)
	String id, Model model)
	{
		@SuppressWarnings("unused")
		List<TechQuestionSubitems> subList = commonService
		        .list(
		                "from TechQuestionSubitems t where questionId = ? order by subitmesIdx ",
		                id);
		model.addAttribute("subList", subList);
		model.addAttribute("questionid", id);
		return "tech/questionmanager/sublist";
	}

	@RequestMapping("viewsub")
	public String viewsub(@RequestParam(value = "id", required = false)
	String id, @RequestParam("questionid")
	String questionid, Model model)
	{
		TechQuestionSubitems subitems = (TechQuestionSubitems) commonService
		        .get(id, TechQuestionSubitems.class);

		if (subitems != null)
		{
			model.addAttribute("subitems", subitems);
		}
		model.addAttribute("questionid", questionid);
		return "tech/questionmanager/viewsub";
	}

	/**
	 * 
	 * 增加题目
	 * 
	 * @param id
	 * @param questionId
	 * @param subname
	 * @param subinx
	 * @param subtype
	 * @return
	 */
	@RequestMapping("addsub")
	public @ResponseBody
	AjaxResponse addsub(@RequestParam(value = "id", required = false)
	String id, @RequestParam("questionId")
	String questionId, @RequestParam("subitmesName")
	String subname, @RequestParam("subitmesIdx")
	String subinx, @RequestParam(value = "subitmeType", required = false)
	String subtype)
	{
		TechQuestionSubitems subitmes = null;
		if (id != null && !"".equals(id))
		{
			subitmes = (TechQuestionSubitems) commonService.get(id,
			        TechQuestionSubitems.class);
			if (subitmes != null)
			{
				subitmes.setQuestionId(questionId);
				subitmes.setSubitmesIdx(Integer.valueOf(subinx));
				subitmes.setSubitmesName(subname);
				subitmes.setSubitmeType(subtype);
			}
		} else
		{
			subitmes = new TechQuestionSubitems();
			subitmes.setId(null);
			subitmes.setQuestionId(questionId);
			subitmes.setSubitmesIdx(Integer.valueOf(subinx));
			subitmes.setSubitmesName(subname);
			subitmes.setSubitmeType(subtype);
		}
		commonService.saveOrUpdateTX(subitmes);
		return new AjaxResponse();
	}

	/**
	 * 删除问卷题目
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("delsub")
	public @ResponseBody
	AjaxResponse delsub(@RequestParam("id")
	String id)
	{
		TechQuestionSubitems subitems = (TechQuestionSubitems) commonService
		        .get(id, TechQuestionSubitems.class);
		if (subitems != null)
		{

			commonService.deleteTX(subitems);
		}
		return new AjaxResponse();
	}

	/**
	 * 
	 * @param questionId
	 * @param userid
	 * @param id
	 * @param request
	 *            t_5_id
	 * @return
	 */
	@RequestMapping("testing")
	@SuppressWarnings("unchecked")
	public @ResponseBody
	AjaxResponse testing(@RequestParam("questionId")
	String questionId, @RequestParam("userid")
	String userid, @RequestParam("planId")
	String planId, HttpServletRequest request, Model model)
	{
		Enumeration<String> em = request.getParameterNames();
		String planid = request.getParameter("planid");
		Map<String, String> titleMap = new HashMap<String, String>();
		Map<String, String> rightMap = new HashMap<String, String>();
		Map<String, String> evaluationMap = new HashMap<String, String>();
		List<TechQuestionSubitems> sbList = commonService
		        .list(
		                "from TechQuestionSubitems t where t.questionId=? and t.subitmeType <> '4'",
		                questionId);
		int ccount = 0;
		int rcount = 0;
		while (em.hasMoreElements())
		{
			// 取出简单题

			String name = em.nextElement();
			if (name.startsWith("tt_"))
			{

				String[] titleName = name.split("tt_");
				String value = request.getParameter(name);
				System.out.println("name \r\t" + name + "\r\t" + "value \r\t"
				        + value);
				if (titleName.length >= 1
				        && StringUtils.isNotBlank(value.trim()))
				{
					titleMap.put(titleName[1], value);
				}
			}

			// 取出单选
			if (name.startsWith("cc_"))
			{
				String[] names = name.split("cc_");
				System.out.println("c_  \r\t" + names[1]);
				if (names.length >= 1)
				{
					String value = request.getParameter(name);
					rightMap.put(names[1], value);
				}
				ccount++;
			}
			// 取出总体评价
			if (name.startsWith("rr_"))
			{
				String[] evaluations = name.split("rr_");
				System.out.println("r_  \r\t" + evaluations[1]);
				if (evaluations.length >= 1)
				{
					String values = request.getParameter(name);
					// Evaluation = new String[] { evaluations[1], values };
					evaluationMap.put(evaluations[1], values);
				}
				rcount++;
			}

		}

		// 题目数与提交数不符
		int count = rcount + ccount;
		if (sbList != null && sbList.size() > 0)
		{
			if (!(count == sbList.size()))
			{
				return new AjaxResponse(false, I18N
				        .parse("i18n.question.radio.fillin"));
			}
		}
		// 调用封装信息。

		List<TechSubitmesDb> judgeList = judge(titleMap, rightMap,
		        evaluationMap, questionId, userid, planId);
		if (judgeList != null && judgeList.size() > 0)
		{
			commonService.saveOrUpdateTX(judgeList);
		}

		model.addAttribute("planid", planid);
		model.addAttribute("questionId", "questionId");
		return new AjaxResponse();
	}

	/**
	 * 
	 * 保存提交问卷信息
	 * 
	 * @param titlemap
	 *            简单题内容
	 * @param rightMap
	 *            单选题内容
	 * @param str
	 *            最后综合评价内容
	 * @param questionId
	 *            问卷ID
	 * @param userid
	 *            用户ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TechSubitmesDb> judge(Map<String, String> titlemap,
	        Map<String, String> rightMap, Map<String, String> evaluationMap,
	        String questionId, String userid, String planId)
	{
		String[] type = {};
		List<TechSubitmesDb> techSubitmesDbList = new ArrayList<TechSubitmesDb>();
		TechSubitmesDb answer = null;

		// 循环简单变成list
		for (Map.Entry<String, String> entry : titlemap.entrySet())
		{
			type = entry.getKey().split("_");
			answer = new TechSubitmesDb();
			answer.setDbAnswerDate(new Date());
			answer.setDbAnswerUser(userid);
			answer.setId(null);
			answer.setQuestionId(questionId);
			answer.setDbAnswerContent((entry.getValue()));
			answer.setPlanId(planId);
			if (type != null && type.length >= 1)
			{
				answer.setDbAnswerType(type[0]);
				answer.setSubitmesId(type[2]);
				answer.setSubitmesIdx(Integer.valueOf(type[1]));
			}
			techSubitmesDbList.add(answer);
		}

		// 循环单选的 组装list
		for (Map.Entry<String, String> entry : rightMap.entrySet())
		{
			type = entry.getKey().split("_");
			answer = new TechSubitmesDb();
			answer.setDbAnswer(entry.getValue() == null ? "3" : entry
			        .getValue());
			answer.setDbAnswerDate(new Date());
			answer.setDbAnswerUser(userid);
			answer.setId(null);
			answer.setQuestionId(questionId);
			answer.setPlanId(planId);
			if (type != null && type.length >= 1)
			{
				System.out.println("============="+type.toString());
				answer.setDbAnswerType(type[0]);
				answer.setSubitmesId(type[2]);
				answer.setSubitmesIdx(Integer.valueOf(type[1]));
			}
			techSubitmesDbList.add(answer);
		}

		for (Map.Entry<String, String> entry : evaluationMap.entrySet())
		{
			type = entry.getKey().split("_");
			answer = new TechSubitmesDb();
			answer.setDbAnswer(entry.getValue() == null ? "2" : entry
			        .getValue());
			answer.setDbAnswerDate(new Date());
			answer.setDbAnswerUser(userid);
			answer.setId(null);
			answer.setQuestionId(questionId);
			answer.setPlanId(planId);
			if (type != null && type.length >= 1)
			{
				answer.setDbAnswerType(type[0]);
				answer.setSubitmesId(type[2]);
				answer.setSubitmesIdx(Integer.valueOf(type[1]));
			}
			techSubitmesDbList.add(answer);
		}
		return techSubitmesDbList;
	}

	/**
	 * 统计分析
	 * 
	 * @param questionid
	 *            问卷ID 1882231
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("sitestat")
	public String sitestat(@RequestParam("planid")
	String planid, @RequestParam("questionId")
	String questionId, Model model)
	{

		// 人
		// 题
		// 答案

		// 谁答了那道题 选择哪个

		// List<Tech>
		List<TechSubitmesDb> sublist = commonService
		        .list(
		                "from TechSubitmesDb t where t.planId=? and t.questionId=? and t.dbAnswerType <> '4' order by subitmesIdx",
		                planid,questionId);
		//
		// List<TechQuestionSubitems> questionList = commonService
		// .list(
		// "from TechQuestionSubitems t where t.questionId=? order by
		// t.subitmesIdx",
		// questionId);

		// Map<String, Integer> sortMap = new HashMap<String, Integer>();

		// for (TechQuestionSubitems techQuestionSubitems : questionList)
		// {
		// sortMap.put(techQuestionSubitems.getId(), techQuestionSubitems
		// .getSubitmesIdx());
		// }

		// for (TechSubitmesDb techSubitmesDb2 : sublist)
		// {
		// if (sortMap.containsKey(techSubitmesDb2.getSubitmesId()))
		// {
		// techSubitmesDb2.setIdx(sortMap.get(techSubitmesDb2
		// .getSubitmesId()));
		// }
		// }
		//
		// List<TechSubitmesDb> sortList = sublist;
		// Collections.sort(sortList, new Comparator<TechSubitmesDb>()
		// {
		//
		// @Override
		// public int compare(TechSubitmesDb o1, TechSubitmesDb o2)
		// {
		// return o1.getIdx().compareTo(o2.getIdx());
		// }
		//
		// });

		Set<String> userSet = new HashSet<String>();

		// 查出为1、2、3、5的

//		Map<String, Map<String, Float>> map = new HashMap<String, Map<String, Float>>();
//		Map<String, Map<String, Float>> _map = new HashMap<String, Map<String, Float>>();
		Map<String , Map<String, Float>> map = new TreeMap<String, Map<String,Float>>();
		Map<String , Map<String, Float>> _map = new TreeMap<String, Map<String,Float>>();
		for (TechSubitmesDb techSubitmesDb : sublist)
		{
			if (!userSet.contains(techSubitmesDb.getDbAnswerUser()))
			{
				userSet.add(techSubitmesDb.getDbAnswerUser());
			}

			if (!"5".equals(techSubitmesDb.getDbAnswerType()))
			{
				Map<String, Float> temp_map = map.get(techSubitmesDb
				        .getSubitmesId());

				if (temp_map == null)
				{
					temp_map = new TreeMap<String, Float>();

					temp_map.put("1", Float.valueOf(0));
					temp_map.put("2", Float.valueOf(0));
					temp_map.put("3", Float.valueOf(0));
					temp_map.put("4", Float.valueOf(0));
					temp_map.put("5", Float.valueOf(0));
					map.put(techSubitmesDb.getSubitmesId(), temp_map);

				}

				Float i = temp_map.get(techSubitmesDb.getDbAnswer());
				temp_map.put(techSubitmesDb.getDbAnswer(), i + 1);
			} else
			{
				Map<String, Float> temp_map = _map.get(techSubitmesDb
				        .getSubitmesId());
				if (temp_map == null)
				{
					temp_map = new TreeMap<String, Float>();

					temp_map.put("1", Float.valueOf(0));
					temp_map.put("2", Float.valueOf(0));
					temp_map.put("3", Float.valueOf(0));
					temp_map.put("4", Float.valueOf(0));
					_map.put(techSubitmesDb.getSubitmesId(), temp_map);

				}

				Float i = temp_map.get(techSubitmesDb.getDbAnswer());
				temp_map.put(techSubitmesDb.getDbAnswer(), i + 1);
			}

		}
		model.addAttribute("map", map);
		model.addAttribute("map1", _map);
		model.addAttribute("sublist",sublist);
		model.addAttribute("usercount", userSet.size());
		return "tech/questionmanager/sitestat";
	}

	// class StrComparator implements Comparator<TechSubitmesDb>
	// {
	//
	// public int compare(TechSubitmesDb o1, TechSubitmesDb o2)
	// {
	// return o1.getIdx().compareTo(o2.getIdx());
	// }
	// }

	@SuppressWarnings("unchecked")
	@RequestMapping("viewTest")
	public String viewTest(@RequestParam("questionid")
	String questionid, @RequestParam("userid")
	String userid, @RequestParam("planid")
	String planid, Model model)
	{
		// 取出
		List<TechSubitmesDb> subList = commonService
		        .list(
		                "from TechSubitmesDb t where t.questionId=? and t.dbAnswerUser=? and t.planId=?",
		                questionid, userid, planid);
		Map<String, String> set = new HashMap<String, String>();
		for (TechSubitmesDb techSubitmesDb : subList)
		{
			if (CoreConsts.QUESTION.COMMENT.equals(techSubitmesDb
			        .getDbAnswerType()))
			{
				set.put(techSubitmesDb.getSubitmesId(), techSubitmesDb
				        .getDbAnswerContent());
			} else
			{
				set.put(techSubitmesDb.getSubitmesId(), techSubitmesDb
				        .getDbAnswer());
			}

		}
		// 取出试卷的题
		List<TechQuestionSubitems> listSub = commonService
		        .list(
		                "from TechQuestionSubitems t where t.questionId=?  order by t.subitmesIdx",
		                questionid);
		for (TechQuestionSubitems techQuestionSubitems : listSub)
		{
			if (set.containsKey(techQuestionSubitems.getId()))
			{
				techQuestionSubitems.setContent(set.get(techQuestionSubitems
				        .getId()));
			}
		}
		model.addAttribute("list", listSub);
		System.out.println("list===============" + listSub.size());
		return "tech/questionmanager/viewanswer";
	}
}
