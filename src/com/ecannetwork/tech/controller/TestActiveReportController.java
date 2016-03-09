package com.ecannetwork.tech.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecannetwork.core.app.domain.DomainFacade;
import com.ecannetwork.core.app.user.service.UserService;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.tld.facade.CachedDtoViewFacade;
import com.ecannetwork.core.util.ExecuteContext;
import com.ecannetwork.dto.tech.TechTestActive;
import com.ecannetwork.dto.tech.TechTestActiveFeedback;
import com.ecannetwork.dto.tech.TechTestRecommend;
import com.ecannetwork.tech.facade.TestActiveFacade;
import com.ecannetwork.tech.facade.TestActiveReportGenerator;
import com.ecannetwork.tech.service.TestActiveService;
import com.ecannetwork.tech.util.TechConsts;

/**
 * 测评活动报告
 * 
 * @author think
 * 
 */
@Controller
@RequestMapping("/testactivereport")
public class TestActiveReportController
{
	@Autowired
	private CommonService commonService;

	@Autowired
	private DomainFacade domainFacade;

	@Autowired
	private TestActiveService activeService;

	@Autowired
	private UserService userService;

	@Autowired
	private TestActiveFacade testActiveFacade;

	@Autowired
	private CachedDtoViewFacade cacheDtoViewFacade;

	@Autowired
	private TestActiveReportGenerator testActiveReportGenerator;

	@SuppressWarnings("unchecked")
	@RequestMapping("index")
	public String index(Model model)
	{

		// 我发起的活动：：：查询出所有活动，过滤
		List<TechTestActive> lists = null;

		String role = ExecuteContext.user().getRoleId();
		if (TechConsts.Role.ADMIN.equals(role))
		{
			lists = this.commonService
					.list(
							"from TechTestActive t where t.status != ? order by t.creatTime desc",
							TechTestActive.STATUS.EDIT);
		} else
		{
			String userid = ExecuteContext.user().getId();

			lists = this.commonService
					.list(
							"from TechTestActive t where t.status != ? and (t.hosterId=? or t.mainManId=? or t.watchMens like '%"
									+ userid + "%') order by t.creatTime desc",
							TechTestActive.STATUS.EDIT, userid, userid);
		}

		model.addAttribute("list", lists);

		return "tech/testactivereport/index";
	}

	/**
	 * 查看报告
	 * 
	 * @param model
	 * @param id
	 * @param userid
	 * @return
	 */
	@RequestMapping("view")
	public String view(Model model, @RequestParam("id") String id,
			@RequestParam(value = "userid", required = false) String userid)
	{
		TechTestActive active = this.activeService.get(id);
		model.addAttribute("dto", active);

		if (StringUtils.isBlank(userid))
		{
			userid = active.getUserids().iterator().next();
		}

		model.addAttribute("id", id);
		model.addAttribute("userid", userid);

		return "tech/testactivereport/view";
	}

	/**
	 * 查看报告
	 * 
	 * @param model
	 * @param id
	 * @param userid
	 * @return
	 */
	@RequestMapping("basic")
	public String basic(Model model, @RequestParam("id") String id,
			@RequestParam(value = "userid", required = false) String userid)
	{
		TechTestActive active = this.activeService.get(id);
		model.addAttribute("dto", active);

		return "tech/testactivereport/basic";
	}

	/**
	 * 查看报告
	 * 
	 * @param model
	 * @param id
	 * @param userid
	 * @return
	 */
	@RequestMapping("points")
	public String points(Model model, @RequestParam("id") String id,
			@RequestParam(value = "userid", required = false) String userid)
	{
		TechTestActive active = this.activeService.get(id);
		model.addAttribute("dto", active);

		return "tech/testactivereport/points";
	}

	/**
	 * 查看报告
	 * 
	 * @param model
	 * @param id
	 * @param userid
	 * @return
	 */
	@RequestMapping("test")
	public String test(Model model, @RequestParam("id") String id,
			@RequestParam(value = "userid", required = false) String userid)
	{
		TechTestActive active = this.activeService.get(id);
		model.addAttribute("dto", active);

		return "tech/testactivereport/test";
	}

	/**
	 * 查看报告
	 * 
	 * @param model
	 * @param id
	 * @param userid
	 * @return
	 */
	@RequestMapping("feedback")
	public String feedback(Model model, @RequestParam("id") String id,
			@RequestParam(value = "userid", required = false) String userid)
	{
		TechTestActive active = this.activeService.get(id);
		model.addAttribute("dto", active);

		if (StringUtils.isNotBlank(userid))
		{
			// 当前编辑的学员
			model.addAttribute("curUserid", userid);

			List<TechTestActiveFeedback> list = this.commonService
					.list(
							"from TechTestActiveFeedback t where t.testActiveId=? and t.userId=?",
							active.getId(), userid);

			for (TechTestActiveFeedback fb : list)
			{
				model.addAttribute("p" + fb.getDimension(), fb);
			}

			List<TechTestRecommend> recommends = this.commonService
					.list(
							"from TechTestRecommend t where t.testActiveId=? and userId=?",
							id, userid);

			model.addAttribute("list", recommends);

		}

		return "tech/testactivereport/feedback";
	}

}
