package com.ecannetwork.tech.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.controller.DateBindController;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.ExecuteContext;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.TechResourseLog;
import com.ecannetwork.dto.tech.TechResourseManager;
import com.ecannetwork.dto.tech.TechTrainPlan;
import com.ecannetwork.dto.tech.TechTrainPlanResourse;

/**
 * 资源管理：
 * 
 * @author think
 * 
 */
@Controller
@RequestMapping("/resoursemanager")
public class ResourseManagerController extends DateBindController
{
	@Autowired
	private CommonService commonService;

	/**
	 * 查看资源列表
	 * 
	 * @param model
	 * @return
	 */
	@SuppressWarnings( { "unused", "unchecked" })
	@RequestMapping("index")
	public String listResourse(Model model)
	{

		List<TechResourseManager> resourseList = new ArrayList<TechResourseManager>();
		resourseList = commonService.list(
		        "from TechResourseManager t where t.resStatus=?",
		        CoreConsts.YORN.YES);
		for (TechResourseManager techResourseManager : resourseList)
		{
			List<TechTrainPlanResourse> list = commonService.list(
			        "from TechTrainPlanResourse t where t.resId=?",
			        techResourseManager.getId());
			if (list != null && list.size() > 0)
			{
				techResourseManager.setHasUser(true);
			} else
			{
				techResourseManager.setHasUser(false);
			}
		}
		model.addAttribute("resourseList", resourseList);
		return "tech/resoursemanager/index";
	}

	/**
	 * 增加资源管理
	 * 
	 * @param name
	 * @param type
	 * @param num
	 * @param remark
	 * @param idlenum
	 * @return
	 */
	@RequestMapping("add")
	public @ResponseBody
	AjaxResponse addResourse(@RequestParam(value = "name", required = true)
	String name, @RequestParam(value = "_type", required = false)
	String type, @RequestParam(value = "num", required = true)
	String num, @RequestParam("remark")
	String remark, @RequestParam(value = "idlenum", required = false)
	String idlenum, @RequestParam(value = "id", required = false)
	String id)
	{
		EcanUser user = ExecuteContext.user();
		TechResourseManager manager = new TechResourseManager();
		manager.setId(null);
		manager.setIdleSum(Float.parseFloat(idlenum == null ? "0" : idlenum));
		manager.setResName(name);
		manager.setResRemark(remark);
		manager.setResType(type);
		manager.setResSum(Float.parseFloat(num == null ? "0" : num));
		manager.setResStatus(CoreConsts.YORN.YES);
		// 设置状态 1为正常 0为停用
		manager.setStatus(CoreConsts.YORN.YES);
		commonService.saveOrUpdateTX(manager);
		TechResourseLog techResourseLog = new TechResourseLog();
		techResourseLog.setId(null);
		techResourseLog.setResId(manager.getId());
		techResourseLog.setResType(CoreConsts.RESOURSETYPE.resourse);
		techResourseLog.setLogInfo(remark);
		techResourseLog.setResSum(Float.parseFloat(num == null ? "0" : num));
		techResourseLog.setInsertDate(new Date());
		techResourseLog.setOperType(CoreConsts.RESOURSE_OPER_TYPE.INSERT);
		techResourseLog.setOperName(user.getName());
		commonService.saveOrUpdateTX(techResourseLog);
		return new AjaxResponse();
	}

	/**
	 * 
	 * 查看资源信息
	 * 
	 * @param mode
	 * @param id
	 * @return
	 */
	@RequestMapping("view")
	public String view(Model mode,
	        @RequestParam(value = "id", required = false)
	        String id)
	{
		if (StringUtils.isNotBlank(id))
		{
			TechResourseManager resourseManager = (TechResourseManager) commonService
			        .get(id, TechResourseManager.class);
			mode.addAttribute("resourseManager", resourseManager);
		}
		return "tech/resoursemanager/view";
	}

	/**
	 * 删除资源
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("beforedelete")
	public String beforeDelete(@RequestParam(value = "id")
	String id, Model model)
	{
		TechResourseManager manager = (TechResourseManager) commonService.get(
		        id, TechResourseManager.class);
		model.addAttribute("resourseManager", manager);
		return "tech/resoursemanager/delete";
	}

	@RequestMapping("delete")
	public @ResponseBody
	AjaxResponse delete(@RequestParam("id")
	String id, @RequestParam("remark")
	String remark)
	{
		EcanUser user = ExecuteContext.user();
		TechResourseManager manager = (TechResourseManager) commonService.get(
		        id, TechResourseManager.class);
		if (manager != null)
		{
			TechResourseLog log = new TechResourseLog();
			log.setId(null);
			log.setLogInfo(remark);
			log.setResId(id);
			log.setResType(CoreConsts.RESOURSETYPE.resourse);
			log.setResSum(manager.getResSum());
			log.setInsertDate(new Date());
			log.setOperName(user.getName());
			log.setOperType(CoreConsts.RESOURSE_OPER_TYPE.DELETE);
			commonService.saveTX(log);
			manager.setResStatus(CoreConsts.YORN.NO);
			commonService.updateTX(manager);
		}
		return new AjaxResponse();
	}

	/**
	 * 
	 * 查看资源信息
	 * 
	 * @param mode
	 * @param id
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(Model mode,
	        @RequestParam(value = "id", required = false)
	        String id, @RequestParam(value = "type", required = false)
	        String type)
	{
		if (StringUtils.isNotBlank(id))
		{
			TechResourseManager resourseManager = (TechResourseManager) commonService
			        .get(id, TechResourseManager.class);
			mode.addAttribute("operType", type);
			mode.addAttribute("resourseManager", resourseManager);
		}
		return "tech/resoursemanager/edit";
	}

	/**
	 * 
	 * 设置停用
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("disable")
	public @ResponseBody
	AjaxResponse disable(@RequestParam("id")
	String id)
	{
		TechResourseManager manager = (TechResourseManager) commonService.get(
		        id, TechResourseManager.class);
		if (manager != null)
		{
			manager.setStatus(CoreConsts.YORN.NO);
			commonService.saveOrUpdateTX(manager);
		}
		return new AjaxResponse();
	}

	/**
	 * 
	 * 设置正常
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("active")
	public @ResponseBody
	AjaxResponse active(@RequestParam("id")
	String id)
	{
		TechResourseManager manager = (TechResourseManager) commonService.get(
		        id, TechResourseManager.class);
		if (manager != null)
		{
			manager.setStatus(CoreConsts.YORN.YES);
			commonService.saveOrUpdateTX(manager);
		}
		return new AjaxResponse();
	}

	/**
	 * 增加资源管理
	 * 
	 * @param name
	 * @param type
	 * @param num
	 * @param remark
	 * @param idlenum
	 * @return
	 */
	@RequestMapping("editres")
	public @ResponseBody
	AjaxResponse editResourse(@RequestParam(value = "name", required = true)
	String name, @RequestParam(value = "num", required = true)
	String num, @RequestParam("remark")
	String remark, @RequestParam(value = "idlenum", required = false)
	String idlenum, @RequestParam(value = "id", required = true)
	String id)
	{
		TechResourseManager manager = (TechResourseManager) commonService.get(
		        id, TechResourseManager.class);
		manager.setId(id);

		manager.setIdleSum(Float.parseFloat(idlenum == null ? "0" : idlenum));
		manager.setResName(name);
		manager.setResRemark(remark);
		manager.setResSum(Float.parseFloat(num == null ? "0" : num));
		manager.setResStatus(CoreConsts.YORN.YES);
		// manager.setStatus(status);
		commonService.updateTX(manager);
		// TechResourseLog techResourseLog = new TechResourseLog();
		// techResourseLog.setId(null);
		// techResourseLog.setResId(id);
		// techResourseLog.setResType(CoreConsts.RESOURSETYPE.resourse);
		// techResourseLog.setLogInfo(remark);
		// techResourseLog.setInsertDate(new Date());
		// techResourseLog.setOperType(CoreConsts.RESOURSE_OPER_TYPE.MODITY);
		// techResourseLog.setOperName(user.getName());
		// commonService.saveOrUpdateTX(techResourseLog);
		return new AjaxResponse();
	}

	/**
	 * 
	 * 修改常设品数量
	 * 
	 * @param id
	 * @param num
	 * @param remark
	 * @return
	 */
	@RequestMapping("modifynum")
	public @ResponseBody
	AjaxResponse modifyNum(@RequestParam(value = "id")
	String id, @RequestParam(value = "num")
	String num, @RequestParam(value = "conremark")
	String remark, @RequestParam(value = "modifytype", required = false)
	String modifytype)
	{
		EcanUser user = ExecuteContext.user();
		TechResourseManager resourseManager = (TechResourseManager) commonService
		        .get(id, TechResourseManager.class);
		float _num = 0;
		// float sum = 0;
		if (resourseManager != null)
		{
			_num = Float.parseFloat((num == null ? "0" : num));
			// sum = resourseManager.getResSum();
			TechResourseLog techResourseLog = new TechResourseLog();
			// if ("1".equals(modifytype))
			// {
			resourseManager.setResSum(_num);
			techResourseLog
			        .setResSum(Float.parseFloat(num == null ? "0" : num));
			// }
			// if ("0".equals(modifytype))
			// {
			// System.out.println(sum + _num);
			// resourseManager.setResSum(sum + _num);
			// techResourseLog.setResSum(Float.parseFloat(num == null ? "0"
			// : num));
			// }
			// _num = Float.parseFloat((num == null ? "0" : num));
			techResourseLog.setId(null);
			techResourseLog.setResId(id);
			techResourseLog.setResType(CoreConsts.RESOURSETYPE.resourse);
			// techResourseLog
			// .setResSum(Float.parseFloat(num == null ? "0" : num));
			techResourseLog.setLogInfo(remark);
			techResourseLog.setInsertDate(new Date());
			techResourseLog.setOperName(user.getName());
			techResourseLog.setOperType(CoreConsts.RESOURSE_OPER_TYPE.MODITY);
			commonService.saveTX(techResourseLog);
			// resourseManager.setConSum(_num);
			commonService.updateTX(resourseManager);
		}

		return new AjaxResponse();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("userrecord")
	public String userRecord(@RequestParam("id")
	String id, Model model)
	{
		List<TechTrainPlanResourse> resourseList = commonService.list(
		        "from TechTrainPlanResourse t where t.resId=?", id);

		TechResourseManager manager = (TechResourseManager) commonService.get(
		        id, TechResourseManager.class);

		Map<String, Float> map = new HashMap<String, Float>();
		int i = 0;
		StringBuffer sb = new StringBuffer();
		for (TechTrainPlanResourse techTrainPlanResourse : resourseList)
		{
			map.put(techTrainPlanResourse.getPlanId(), techTrainPlanResourse
			        .getResSum());
			i++;
			sb.append("'").append(techTrainPlanResourse.getPlanId())
			        .append("'");
			if (i != resourseList.size())
			{
				sb.append(",");
			}
		}
		System.out.println(sb.toString() + "===========");
		List<TechTrainPlan> planList = commonService
		        .list("from TechTrainPlan t where t.id in(" + sb.toString()
		                + ")");

		for (TechTrainPlan techTrainPlan : planList)
		{
			if (map.containsKey(techTrainPlan.getId()))
			{
				techTrainPlan.setResName(manager.getResName());
				techTrainPlan.setResSum(map.get(techTrainPlan.getId()));
			}
		}

		model.addAttribute("manager", manager);
		model.addAttribute("resourseid", id);
		model.addAttribute("planList", planList);
		return "tech/resoursemanager/userrecord";
	}

	public static void main(String[] args)
	{

		String i = "-10";
		float n = 100;
		System.out.println(n + (Float.parseFloat(i)));

	}
}
