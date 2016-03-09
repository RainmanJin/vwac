package com.ecannetwork.tech.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecannetwork.core.app.user.service.UserService;
import com.ecannetwork.core.i18n.I18N;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.util.JsonFactory;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.TechTrainCourse;
import com.ecannetwork.tech.controller.bean.commonresouce.TrianCourseModule;
import com.ecannetwork.tech.facade.HolidayFacade;
import com.ecannetwork.tech.service.TrainPlanService;
import com.ecannetwork.tech.util.TechConsts;

/**
 * 公用信息调用接口,例如根据所属查询用户列表等
 * 
 * @author think
 * 
 */
@Controller
@RequestMapping("/commonresource")
public class CommonResourceController
{
	@Autowired
	private UserService userService;

	@Autowired
	private TrainPlanService trainPlanService;

	/**
	 * 列举某个公司所属下面的所有用户，以select的方式返回
	 * 
	 * @param model
	 * @param company
	 *            公司所属的域值
	 * @param selectid
	 *            html控件的编号
	 * @return
	 */
	@RequestMapping("listStudents")
	public String listStudents(Model model, @RequestParam("company") String company, @RequestParam("selectid") String selectid, @RequestParam("selectname") String selectname)
	{
		model.addAttribute("list", userService.listByCompanyAndRole(company, TechConsts.Role.STUDENT));
		model.addAttribute("selectid", selectid);
		model.addAttribute("selectname", selectname);

		return "tech/commonresource/userSelect";
	}

	/**
	 * 列举讲师，以select控件形式返回
	 * 
	 * @param model
	 * @param proType
	 *            专业方向：为空时，表示全部未禁用的讲师用户
	 * @param selectid
	 * @param selectname
	 * @param value
	 *            默认选择的值
	 * @return
	 */
	@RequestMapping("listTeacher")
	public String listTeacher(Model model, @RequestParam(value = "proType", required = false) String proType, @RequestParam("selectid") String selectid, @RequestParam("selectname") String selectname,
			@RequestParam(value = "value", required = false) String value)
	{
		model.addAttribute("list", userService.listByProType(proType, TechConsts.Role.TEACHER));
		model.addAttribute("selectid", selectid);
		model.addAttribute("selectname", selectname);

		return "tech/commonresource/userSelect";
	}

	/**
	 * 列举未休假的讲师，以select控件形式返回<br />
	 * 需要和培训排期、讲师假期日历做校验
	 * 
	 * @param model
	 * @param proType
	 *            专业方向：为空时，表示全部未禁用的讲师用户
	 * @param selectid
	 * @param selectname
	 * @param yearWeek
	 *            年周：：：例如201101表示2011年第一周，为空时不剔除修改的讲师
	 * @param value
	 *            默认选择的值
	 * @return
	 */
	@RequestMapping("listOnWorkTeacher")
	public String listOnWorkTeacher(Model model, @RequestParam(value = "proType", required = false) String proType, @RequestParam("selectname") String selectname,
			@RequestParam(value = "yearWeek", required = false) Integer yearWeek, @RequestParam(value = "value", required = false) String value)
	{
		List<EcanUser> list = userService.listByProType(proType, TechConsts.Role.TEACHER);
		if (yearWeek != null && yearWeek.intValue() != 0)
		{
			for (Iterator<EcanUser> it = list.iterator(); it.hasNext();)
			{
				EcanUser u = it.next();
				if (HolidayFacade.isHolidayWeek(yearWeek / 100, yearWeek % 100, u.getId()))
				{// 用户休假的踢出
					it.remove();
				}
			}
		}
		model.addAttribute("list", list);
		model.addAttribute("selectname", selectname);

		return "tech/commonresource/userSelect";
	}

	/**
	 * 列举某个品牌、某个领域下的课程
	 * 
	 * @param model
	 * @param brand
	 *            品牌
	 * @param proType
	 *            专业方向
	 * @param value
	 *            默认选中的值
	 * @return
	 */
	@RequestMapping("listTrainCourseByProTypeAndBrand")
	public String listTrainCourseByProTypeAndBrand(Model model, @RequestParam("brand") String brand, @RequestParam("proType") String proType, @RequestParam("name") String name,
			@RequestParam(value = "value", required = false) String value)
	{
		List<TechTrainCourse> list = trainPlanService.listTrainCourseByProTypeAndBrand(brand, proType);

		model.addAttribute("list", list);
		return "tech/commonresource/trainCourse";
	}

	/**
	 * 列举某个品牌、某个领域下的课程与模块，回调函数为selectCourseModule
	 * 
	 * @param model
	 * @param brand
	 *            品牌
	 * @param proType
	 *            专业方向
	 * @param value
	 *            默认选中的值
	 * @return
	 */
	@RequestMapping("listTrainCourseModelByProTypeAndBrand")
	public String listTrainCourseModelByProTypeAndBrand(Model model, @RequestParam("brand") String brand, @RequestParam("proType") String proType)
	{
		List<TrianCourseModule> list = trainPlanService.listTrainCourseAndItemsByProTypeAndBrand(brand, proType);

		model.addAttribute("list", list);
		return "tech/commonresource/trainCourseItems";
	}
	
}
