package com.ecannetwork.tech.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.JoinHelper;
import com.ecannetwork.dto.tech.TechMdttAns;
import com.ecannetwork.dto.tech.TechMdttOption;
import com.ecannetwork.dto.tech.TechMdttPkg;
import com.ecannetwork.dto.tech.TechMdttQustion;
import com.ecannetwork.dto.tech.TechTrainPlan;
import com.ecannetwork.tech.controller.bean.MdttStatViewBean;

/**
 * 课件包管理
 * 
 * @author leo
 * 
 */
@Controller
@RequestMapping("mdttstat")
public class MDTTStatController
{
	@Autowired
	private CommonService commonService;

	@RequestMapping("index")
	public String index(Model model)
	{
		List<TechMdttPkg> pkgs = this.commonService
				.list("from TechMdttPkg t where t.conentType = ? and t.trianPlanID is not null",
						TechMdttPkg.CONTENT_TYPE.CLASS_ROOM);

		Map<String, MdttStatViewBean> beans = new HashMap<String, MdttStatViewBean>();

		for (TechMdttPkg pkg : pkgs)
		{
			MdttStatViewBean bean = new MdttStatViewBean();
			bean.setPkgID(pkg.getId());
			bean.setPkgName(pkg.getName());
			beans.put(pkg.getTrianPlanID(), bean);
		}

		String ids = JoinHelper.joinToSql(beans.keySet());

		List<TechTrainPlan> plans = this.commonService
				.list("from TechTrainPlan t where t.id in (" + ids + ")");

		for (TechTrainPlan plan : plans)
		{
			MdttStatViewBean bean = beans.get(plan.getId());
			if (bean != null)
			{
				bean.setPlanID(plan.getId());
				bean.setPlanWeek(plan.getYearValue() + " / "
						+ plan.getPlanWeek());
				bean.setCourseName(plan.getTrainId());
			}
		}

		List<MdttStatViewBean> list = new ArrayList<MdttStatViewBean>();
		list.addAll(beans.values());

		for (Iterator<MdttStatViewBean> it = list.iterator(); it.hasNext();)
		{// 删除没有排期的
			MdttStatViewBean bean = it.next();
			if (StringUtils.isBlank(bean.getPlanWeek()))
			{
				it.remove();
			}
		}
		Collections.sort(list, new Comparator<MdttStatViewBean>()
		{
			public int compare(MdttStatViewBean arg0, MdttStatViewBean arg1)
			{
				return arg0.getPlanWeek().compareTo(arg1.getPlanWeek());
			}
		});

		model.addAttribute("list", list);
		return "tech/mdttstat/index";
	}

	@RequestMapping("view")
	public String view(Model model, @RequestParam("id") String id)
	{
		List<TechMdttQustion> list = this.commonService.list(
				"from TechMdttQustion t where t.pkgID = ?", id);

		List<TechMdttOption> opts = this.commonService.list(
				"from TechMdttOption t where t.pkgID = ?", id);

		List<TechMdttAns> anss = this.commonService.list(
				"from TechMdttAns t where t.pkgID = ?", id);

		Map<String, TechMdttQustion> qusMap = new HashMap<String, TechMdttQustion>();
		for (TechMdttQustion qus : list)
		{
			qusMap.put(qus.getId(), qus);
		}

		for (TechMdttOption opt : opts)
		{
			TechMdttQustion qus = qusMap.get(opt.getQustionID());
			if (qus != null
					&& !qus.getQusType().equals(
							TechMdttQustion.QustionType.INPUT))
			{
				qus.getOptions().put(opt.getOptID(), opt);
			}
		}

		for (TechMdttAns ans : anss)
		{
			TechMdttQustion qus = qusMap.get(ans.getQuestionID());
			if (qus != null)
			{
				qus.addAns(ans);
			}
		}

		model.addAttribute("list", list);

		return "tech/mdttstat/view";
	}
}
