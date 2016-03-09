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

import com.ecannetwork.core.i18n.I18N;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.controller.DateBindController;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.ExecuteContext;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.TechConsumablesBatch;
import com.ecannetwork.dto.tech.TechConsumablesManager;
import com.ecannetwork.dto.tech.TechResourseLog;
import com.ecannetwork.dto.tech.TechTrainPlan;
import com.ecannetwork.dto.tech.TechTrainPlanResourse;

/**
 * 资源管理：
 * 
 * @author think
 * 
 */
@Controller
@RequestMapping("/consumablesmanager")
public class ConsumablesManagerController extends DateBindController
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

		List<TechConsumablesManager> resourseList = new ArrayList<TechConsumablesManager>();
		resourseList = commonService.list(
		        "from TechConsumablesManager t where t.conStatus=?",
		        CoreConsts.YORN.YES);
		for (TechConsumablesManager techConsumablesManager : resourseList)
		{
			List<TechTrainPlanResourse> list = commonService.list(
			        "from TechTrainPlanResourse t where t.resId=?",
			        techConsumablesManager.getId());
			if (list != null && list.size() > 0)
			{
				techConsumablesManager.setHasUsed(true);
			} else
			{
				techConsumablesManager.setHasUsed(false);
			}
		}
		model.addAttribute("resourseList", resourseList);
		return "tech/consumablesmanager/index";
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
	String name, @RequestParam(value = "type", required = false)
	String type, @RequestParam(value = "num", required = false)
	String num, @RequestParam(value = "remark", required = false)
	String remark, @RequestParam(value = "idlenum", required = false)
	String idlenum, @RequestParam(value = "id", required = false)
	String id, @RequestParam(value = "conPirce", required = false)
	String conPrice)

	{
		// EcanUser user = ExecuteContext.user();
		TechConsumablesManager manager = new TechConsumablesManager();
		if (id != null && !"".equals(id))
		{
			manager = (TechConsumablesManager) commonService.get(id,
			        TechConsumablesManager.class);

		} else
		{
			manager.setId(null);
			manager.setConSum(Float.parseFloat(num == null ? "0" : num));
		}

		manager.setConName(name);
		manager.setConRemark(remark);
		manager.setConType(type);
		manager.setConStatus(CoreConsts.YORN.YES);
		commonService.saveOrUpdateTX(manager);
		// //批次表插入
		// TechConsumablesBatch batchdto = new TechConsumablesBatch();
		// batchdto.setId(null);
		// batchdto.setConDate(new Date());
		// batchdto.setConPrice(Float.valueOf(conPrice));
		// batchdto.setConRemark(remark);
		// batchdto.setConSum(Float.parseFloat(num == null ? "0" : num));
		// batchdto.setUserName(user.getName());
		// batchdto.setConsumablesId(manager.getId());
		// commonService.saveOrUpdateTX(batchdto);
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
			TechConsumablesManager resourseManager = (TechConsumablesManager) commonService
			        .get(id, TechConsumablesManager.class);
			mode.addAttribute("resourseManager", resourseManager);
		}
		return "tech/consumablesmanager/view";
	}

	/**
	 * 
	 * 查看资源信息
	 * 
	 * @param mode
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("edit")
	public String edit(Model mode,
	        @RequestParam(value = "id", required = false)
	        String id, @RequestParam(value = "type", required = false)
	        String type)
	{
		if (StringUtils.isNotBlank(id))
		{
			TechConsumablesManager resourseManager = (TechConsumablesManager) commonService
			        .get(id, TechConsumablesManager.class);
			List<TechConsumablesBatch> batchList = commonService.list(
			        "from TechConsumablesBatch t where t.consumablesId=?", id);
			Integer batch = Integer.valueOf(1);
			if (batchList != null && batchList.size() > 0)
			{
				batch = batchList.size() + 1;
			}
			mode.addAttribute("batch", batch);
			mode.addAttribute("operType", type);
			mode.addAttribute("resourseManager", resourseManager);
		}
		return "tech/consumablesmanager/addbatch";
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
		TechConsumablesManager manager = (TechConsumablesManager) commonService
		        .get(id, TechConsumablesManager.class);
		model.addAttribute("resourseManager", manager);
		return "tech/consumablesmanager/delete";
	}

	@RequestMapping("delete")
	public @ResponseBody
	AjaxResponse delete(@RequestParam("id")
	String id, @RequestParam("remark")
	String remark)
	{
		TechConsumablesManager manager = (TechConsumablesManager) commonService
		        .get(id, TechConsumablesManager.class);
		EcanUser user = ExecuteContext.user();
		if (manager != null)
		{
			TechResourseLog log = new TechResourseLog();
			log.setId(null);
			log.setLogInfo(remark);
			log.setResId(id);
			log.setResType(CoreConsts.RESOURSETYPE.consumable);
			log.setResSum(manager.getConSum());
			log.setInsertDate(new Date());
			log.setOperName(user.getName());
			log.setOperType(CoreConsts.RESOURSE_OPER_TYPE.DELETE);
			commonService.saveTX(log);
			manager.setConStatus(CoreConsts.YORN.NO);
			commonService.updateTX(manager);
		}
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
	String remark, @RequestParam("modifytype")
	String modifytype)
	{
		EcanUser user = ExecuteContext.user();
		TechConsumablesManager resourseManager = (TechConsumablesManager) commonService
		        .get(id, TechConsumablesManager.class);
		float _num = 0;
		float sum = 0;
		if (resourseManager != null)
		{
			_num = Float.parseFloat((num == null ? "0" : num));
			sum = resourseManager.getConSum();
			TechResourseLog techResourseLog = new TechResourseLog();
			if ("1".equals(modifytype))
			{
				if ((sum - _num) < 0)
				{
					return new AjaxResponse(false, I18N
					        .parse("i18n.consumablesmanager.reduce.error"));
				}
				resourseManager.setConSum(sum - _num);
				techResourseLog.setResSum(Float.parseFloat(num == null ? "0"
				        : "-" + num));
			}
			if ("0".equals(modifytype))
			{
				System.out.println(sum + _num);
				resourseManager.setConSum(sum + _num);
				techResourseLog.setResSum(Float.parseFloat(num == null ? "0"
				        : num));
			}
			// _num = Float.parseFloat((num == null ? "0" : num));
			techResourseLog.setId(null);
			techResourseLog.setResId(id);
			techResourseLog.setResType(CoreConsts.RESOURSETYPE.consumable);
			techResourseLog.setOperType(CoreConsts.RESOURSE_OPER_TYPE.ADDBATCH);
			// techResourseLog
			// .setResSum(Float.parseFloat(num == null ? "0" : num));
			techResourseLog.setLogInfo(remark);
			techResourseLog.setInsertDate(new Date());
			techResourseLog.setOperName(user.getName());
			commonService.saveTX(techResourseLog);
			// resourseManager.setConSum(_num);
			commonService.updateTX(resourseManager);
		}

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
	@SuppressWarnings("unchecked")
	@RequestMapping("addBatch")
	public @ResponseBody
	AjaxResponse addBatch(@RequestParam(value = "id")
	String id, @RequestParam(value = "num")
	String num, @RequestParam(value = "conremark")
	String remark, @RequestParam(value = "price")
	String price)
	{
		EcanUser user = ExecuteContext.user();
		TechConsumablesManager resourseManager = (TechConsumablesManager) commonService
		        .get(id, TechConsumablesManager.class);
		float _num = 0;
		float sum = 0;
		if (resourseManager != null)
		{
			_num = Float.parseFloat((num == null ? "0" : num));
			sum = resourseManager.getConSum();
			TechResourseLog techResourseLog = new TechResourseLog();
			if (_num < 0)
			{
				return new AjaxResponse(false, I18N
				        .parse("i18n.consumablesmanager.reduce.error"));
			}
			// resourseManager.setConSum(sum - _num);
			// techResourseLog.setResSum(Float.parseFloat(num == null ? "0"
			// : "-" + num));
			resourseManager.setConSum(sum + _num);
			techResourseLog
			        .setResSum(Float.parseFloat(num == null ? "0" : num));
			// _num = Float.parseFloat((num == null ? "0" : num));
			techResourseLog.setId(null);
			techResourseLog.setResId(id);
			techResourseLog.setResType(CoreConsts.RESOURSETYPE.consumable);
			techResourseLog.setOperType(CoreConsts.RESOURSE_OPER_TYPE.ADDBATCH);
			// techResourseLog
			// .setResSum(Float.parseFloat(num == null ? "0" : num));
			techResourseLog.setLogInfo(remark);
			techResourseLog.setInsertDate(new Date());
			techResourseLog.setOperName(user.getName());

			List<TechConsumablesBatch> batchList = commonService.list(
			        "from TechConsumablesBatch t where t.consumablesId=?", id);
			Integer batch = Integer.valueOf(1);
			if (batchList != null && batchList.size() > 0)
			{
				batch = batchList.size() + 1;
			}
			TechConsumablesBatch batchdto = new TechConsumablesBatch();
			batchdto.setConsumablesId(id);
			// batchdto.setBatch(batch)
			batchdto.setConDate(new Date());
			batchdto.setConPrice(Float.valueOf(price));
			batchdto.setConRemark(remark);
			batchdto.setConSum(_num);
			batchdto.setId(null);
			batchdto.setUserName(user.getName());
			batchdto.setBatch(batch + "");
			commonService.saveOrUpdateTX(batchdto);
			commonService.saveTX(techResourseLog);
			// resourseManager.setConSum(_num);
			commonService.updateTX(resourseManager);
		}

		return new AjaxResponse();
	}

	/**
	 * 
	 * 查看全部批次
	 * 
	 * @param args
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping("viewbatch")
	public String viewBatch(@RequestParam(value = "id", required = false)
	String id, Model model)
	{
		// TechConsumablesManager manager = (TechConsumablesManager)
		// commonService
		// .list(TechConsumablesManager.class);
		List<TechConsumablesBatch> batchList = commonService.list(
		        "from TechConsumablesBatch t where t.consumablesId=?", id);
		model.addAttribute("batchList", batchList);
		model.addAttribute("consumablesId", id);
		// model.addAttribute("manager", manager);
		return "tech/consumablesmanager/viewbatch";
	}

	/**
	 * 根据ID查看一条批次
	 * 
	 * @param args
	 */
	@RequestMapping("viewBatchbyId")
	public String viewBatchbyId(@RequestParam(value = "id")
	String id, Model model)
	{
		TechConsumablesBatch batch = (TechConsumablesBatch) commonService.get(
		        id, TechConsumablesBatch.class);
		model.addAttribute("batch", batch);
		return "tech/consumablesmanager/viewbatchbyid";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("userrecord")
	public String userRecord(@RequestParam("id")
	String id, Model model)
	{
		List<TechTrainPlanResourse> resourseList = commonService.list(
		        "from TechTrainPlanResourse t where t.resId=?", id);

		TechConsumablesManager manager = (TechConsumablesManager) commonService
		        .get(id, TechConsumablesManager.class);

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
				techTrainPlan.setResName(manager.getConName());
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
		// float n = 100;
		System.out.println(100 + (Float.parseFloat(i)));

	}
}
