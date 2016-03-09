package com.ecannetwork.tech.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ecannetwork.core.i18n.I18N;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.Configs;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.ExecuteContext;
import com.ecannetwork.core.util.FileUploadHelper;
import com.ecannetwork.core.util.JsonFactory;
import com.ecannetwork.core.util.UUID;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.TechTrainPlan;
import com.ecannetwork.dto.tech.TechTrainPlanUser;
import com.ecannetwork.tech.service.DmpInterfaceFacade;
import com.ecannetwork.tech.util.TechConsts;

/**
 * 个人中心
 * 
 * @author leo
 * 
 */
@Controller
@RequestMapping("/usercenter")
public class UserCenterController
{
	@Autowired
	private CommonService commonService;
	@Autowired
	private DmpInterfaceFacade dmpInterfaceFacade;
	/**
	 * 进入用户中心
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public String index(Model model)
	{
		EcanUser user = ExecuteContext.user();
		model.addAttribute("curUser",
				this.commonService.get(user.getId(), EcanUser.class));
		model.addAttribute("head_img", user.getHeadImg());
		return "tech/usercenter/index";
	}

	/**
	 * 保存用户信息
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("modifyUserInfo")
	public String modifyUserInfo(
			Model model,
			HttpServletRequest request,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "sex", required = true) String sex,
			@RequestParam(value = "tel", required = false) String tel,
			@RequestParam(value = "mobile", required = false) String mobile,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "company", required = true) String company,
			@RequestParam(value = "caller", required = true) String caller,
			@RequestParam(value = "position", required = false) String position,
			@RequestParam("birthday") String birthday,
			@RequestParam("nickName") String nickName,
			@RequestParam("cardId") String cardId, HttpServletResponse response)
	{
		AjaxResponse resp = null;
		response.setContentType("text/html; charset=UTF-8");
		EcanUser sessionUser = ExecuteContext.user();
		if (sessionUser != null)
		{
			EcanUser user = (EcanUser) commonService.get(sessionUser.getId(),
					EcanUser.class);
			user.setCompany(company);
			user.setEmail(email);
			user.setMobile(mobile);
			user.setName(name);
			user.setSex(sex);
			user.setBirthday(birthday);
			user.setCaller(caller);
			user.setNickName(nickName);
			user.setCardId(cardId);
			user.setPosition(position);
			user.setTel(tel);

			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartHttpServletRequest
					.getFile("headImg");
			String fileName = multipartFile.getOriginalFilename();
			if (StringUtils.isNotBlank(fileName))
			{
				String filePathString = File.separator
						+ TechConsts.UPLOAD_HEAD_IMG + File.separator
						+ UUID.randomUUID();
				String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
						+ filePathString;

				resp = FileUploadHelper.upload(request, storeFileNameWithPath,
						"headImg", Configs.getAsList("userHeadImgFileType"));
				if (resp.isSuccess())
				{
					String filePath = filePathString.replace('\\', '/');
					user.setHeadImg(filePath + "." + resp.getData());
				} else
				{
					resp = new AjaxResponse(false,
							I18N.parse("i18n.fileupload.errorType"));
				}
			}

			MultipartFile multipartFile1 = multipartHttpServletRequest
					.getFile("information");
			fileName = multipartFile1.getOriginalFilename();
			if (StringUtils.isNotBlank(fileName))
			{
				String informationPath = File.separator
						+ TechConsts.INFORMATION + File.separator
						+ UUID.randomUUID();

				String informationNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
						+ informationPath;
				resp = FileUploadHelper.upload(request,
						informationNameWithPath, "information",
						Configs.getAsList("informationFileType"));
				if (resp.isSuccess() && !resp.getData().equals("empty"))
				{
					String filePath = informationPath.replace('\\', '/');
					user.setInformation(filePath + "." + resp.getData());

				} else
				{
					resp = new AjaxResponse(false,
							I18N.parse("i18n.fileupload.errorType"));
				}
			}
			// user.setProType(proType);
			// DELETE PROTYPE
			commonService.saveOrUpdateTX(user);
			sessionUser.setCompany(user.getCompany());
			sessionUser.setEmail(user.getEmail());
			sessionUser.setMobile(user.getMobile());
			sessionUser.setName(user.getName());
			sessionUser.setSex(user.getSex());
			sessionUser.setBirthday(user.getBirthday());
			sessionUser.setCaller(user.getCaller());
			sessionUser.setNickName(user.getNickName());
			sessionUser.setPosition(user.getPosition());
			sessionUser.setTel(user.getTel());
			sessionUser.setInformation(user.getInformation());
			sessionUser.setCardId(user.getCardId());
			sessionUser.setHeadImg(user.getHeadImg());
			ExecuteContext.session().setAttribute(
					CoreConsts.SessionScopeKeys.CURRENT_USER, sessionUser);
			if (resp == null || resp.isSuccess())
			{
				resp = new AjaxResponse(true, I18N.parse("i18n.commit.success"));
				dmpInterfaceFacade.userEditted(user);
			} else
			{
				resp = new AjaxResponse(false, resp.getData());
			}

		} else
		{
			resp = new AjaxResponse(false, I18N.parse("i18n.commit.error"));
		}

		try
		{
			response.getWriter().write(JsonFactory.toJson(resp));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 只能是email或者6-25为字符
	 * 
	 * @param text
	 * @return
	 */
	public static boolean validateNameOrPassword(String text)
	{
		if (text != null)
		{
			if (text.length() >= 5 && text.length() <= 25)
			{
				String check = "[a-zA-Z0-9_]{5,25}";
				Pattern regex = Pattern.compile(check);
				Matcher matcher = regex.matcher(text);
				if (matcher.matches())
				{
					return true;
				} else
				{
					check = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
					regex = Pattern.compile(check);
					matcher = regex.matcher(text);
					return matcher.matches();
				}
			}
		}
		return false;
	}

	/**
	 * 修改用户密码
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("modifyPwd")
	public @ResponseBody
	AjaxResponse modifyPwd(Model model,
			@RequestParam(value = "oldpassword", required = false) String old,
			@RequestParam("newpassword") String newpass,
			@RequestParam("conformpass") String conformpass)
	{
		EcanUser user = ExecuteContext.user();
		if (user != null && user.getLoginPasswd() != null)
		{
			if (old.equals(user.getLoginPasswd()))
			{
				if (!validateNameOrPassword(conformpass))
				{
					return new AjaxResponse(false,
							I18N.parse("i18n.validatePassword"));
				} else
				{
					if (conformpass.equals(newpass))
					{
						user.setLoginPasswd(newpass);
						EcanUser dto = (EcanUser) this.commonService.get(
								user.getId(), EcanUser.class);
						dto.setLoginPasswd(newpass);
						commonService.updateTX(dto);
						return new AjaxResponse(true,
								I18N.parse("i18n.commit.modify.success"));
					} else
					{
						return new AjaxResponse(false,
								I18N.parse("i18n.commit.password.error"));
					}
				}
			} else
			{
				return new AjaxResponse(false,
						I18N.parse("i18n.commit.password.olderror"));
			}
		}
		return new AjaxResponse(false);
	}

	/**
	 * 讲师排期报表
	 * 
	 * @param model
	 * @param year
	 * @return
	 */
	@RequestMapping("trainPlanReport")
	public String trainPlanReport(Model model,
			@RequestParam(value = "year", required = false) String year)
	{
		if (StringUtils.isBlank(year))
		{
			year = Calendar.getInstance().get(Calendar.YEAR) + "";
		}
		// 列举所有的讲师排期计划
		List<Object[]> list = this.commonService
				.list("select u,p from TechTrainPlanUser u, TechTrainPlan p where p.id = u.planId and u.userType = ? and p.yearValue = ?",
						TechConsts.Role.TEACHER, year);

		Map<String, Map<Integer, TechTrainPlan>> plansMap = new HashMap<String, Map<Integer, TechTrainPlan>>();

		for (Object[] o : list)
		{
			TechTrainPlanUser u = (TechTrainPlanUser) o[0];
			TechTrainPlan p = (TechTrainPlan) o[1];

			Map<Integer, TechTrainPlan> plans = plansMap.get(u.getUser());
			if (plans == null)
			{
				plans = new HashMap<Integer, TechTrainPlan>();
				plansMap.put(u.getUserId(), plans);
			}

			plans.put(p.getPlanWeek(), p);
		}

		List<String> userIDs = new ArrayList<String>();
		userIDs.addAll(plansMap.keySet());

		model.addAttribute("plansMap", plansMap);
		model.addAttribute("list", userIDs);
		model.addAttribute("year", year);
		return "tech/usercenter/trainPlanReport";
	}
}
