package com.ecannetwork.tech.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ecannetwork.core.app.user.service.UserService;
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
import com.ecannetwork.tech.notification.mail.MailSender;
import com.ecannetwork.tech.service.DmpInterfaceFacade;
import com.ecannetwork.tech.util.ExportExcel;
import com.ecannetwork.tech.util.ImportExcel;
import com.ecannetwork.tech.util.TechConsts;

/**
 * 用户管理,负责用户的增删改查工作
 * 
 * @author leo
 * 
 */
@Controller
@RequestMapping("/usermanager")
public class UserManagerController
{
	@Autowired
	private UserService userService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private ExportExcel exportExcel;
	@Autowired
	private ImportExcel importExcel;

	@Autowired
	private MailSender mailSender;

	@Autowired
	private DmpInterfaceFacade dmpInterfaceFacade;

	/**
	 * 列举所有用户:::显示所有用户，按创建时间倒序
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model)
	{
		model.addAttribute("list", commonService
				.list("from EcanUser t where t.status != ?",
						EcanUser.STATUS.SUPERMEN));

		return "tech/usermanager/index";
	}

	/**
	 * 删除用户：：：已有开始评测或关联测评信息的用户不能删除
	 * 
	 * @param model
	 * @param id
	 *            用户编号
	 * @return
	 */
	@RequestMapping("del")
	public @ResponseBody
	AjaxResponse delete(Model model, @RequestParam("id") String id)
	{
		EcanUser user = (EcanUser) this.commonService.get(id, EcanUser.class);

		if (EcanUser.STATUS.INACTIVE.equals(user.getStatus()))
		{// 未激活
			dmpInterfaceFacade.userDelete(user);
			this.userService.deleteUserTX(user);
			return new AjaxResponse(true);
		} else
		{
			return new AjaxResponse(false,
					I18N.parse("i18n.user.no_active_delete"));
		}
	}

	/**
	 * 禁用用户
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("disable")
	public @ResponseBody
	AjaxResponse disable(Model model, @RequestParam("id") String id)
	{
		return this.userService.disableUserTX(id);
	}

	/**
	 * 批量禁用用户
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("batchDisable")
	public @ResponseBody
	AjaxResponse batchDisable(Model model, @RequestParam("id") String id)
	{
		// EcanUser.STATUS.SUSPENDED
		String ids = id.substring(0, id.length() - 1);
		String[] _ids = ids.split(",");
		List<EcanUser> list = new ArrayList<EcanUser>();
		for (int i = 0; i < _ids.length; i++)
		{
			EcanUser user = (EcanUser) commonService.get(_ids[i],
					EcanUser.class);
			if (user != null)
			{
				user.setStatus(EcanUser.STATUS.SUSPENDED);
				list.add(user);
			}
		}
		this.commonService.saveOrUpdateTX(list);
		return new AjaxResponse(true);
	}

	/**
	 * 启用用户
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("active")
	public @ResponseBody
	AjaxResponse active(Model model, @RequestParam("id") String id)
	{
		return this.userService.activeUserTX(id);
	}

	/**
	 * 批量启用用户
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("batchActive")
	public @ResponseBody
	AjaxResponse batchActive(Model model, @RequestParam("id") String id)
	{
		// EcanUser.STATUS.ACTIVE
		String ids = id.substring(0, id.length() - 1);
		String[] _ids = ids.split(",");
		List<EcanUser> list = new ArrayList<EcanUser>();
		for (int i = 0; i < _ids.length; i++)
		{
			EcanUser user = (EcanUser) commonService.get(_ids[i],
					EcanUser.class);
			if (user != null)
			{
				user.setStatus(EcanUser.STATUS.ACTIVE);
				list.add(user);
			}
		}
		commonService.saveOrUpdateTX(list);
		return new AjaxResponse(true);
	}

	/**
	 * 编辑或新建用户
	 * 
	 * @param model
	 * @param id
	 *            id为空表示添加用户
	 * @return
	 */
	@RequestMapping("view")
	public String view(Model model,
			@RequestParam(value = "id", required = false) String id)
	{
		if (StringUtils.isNotBlank(id))
		{
			model.addAttribute("dto",
					this.commonService.get(id, EcanUser.class));
		}

		return "tech/usermanager/view";
	}

	@RequestMapping("profile")
	public String profile(Model model,
			@RequestParam(value = "id", required = false) String id)
	{
		if (StringUtils.isNotBlank(id))
		{
			model.addAttribute("dto",
					this.commonService.get(id, EcanUser.class));
		}

		return "tech/usermanager/profile";
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
				String check = "[a-zA-Z0-9_.]{5,25}";
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
	 * 保存或者更新用户
	 * 
	 * @param mode
	 * @param user
	 * @param result
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("save")
	public String save(Model mode, @Valid EcanUser user, BindingResult result,
			MultipartHttpServletRequest request, HttpServletResponse resp)
	{
		if (StringUtils.isBlank(user.getId()))
		{
			user.setId(null);
		}

		AjaxResponse response = null;

		if (!validateNameOrPassword(user.getLoginName()))
		{
			response = new AjaxResponse(false,
					I18N.parse("i18n.validateLoginname"));
		} else
		{

			if (!validateNameOrPassword(user.getLoginPasswd()))
			{
				response = new AjaxResponse(false,
						I18N.parse("i18n.validatePassword"));
			} else
			{
				String filePathString = File.separator
						+ TechConsts.UPLOAD_HEAD_IMG + File.separator
						+ UUID.randomUUID();

				String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
						+ filePathString;

				response = FileUploadHelper.upload(request,
						storeFileNameWithPath, "file",
						Configs.getAsList("userHeadImgFileType"), true);

				if (response.isSuccess())
				{
					if (!FileUploadHelper.EMPTY_FILE.equals(response.getData()))
					{
						user.setHeadImg(filePathString + "."
								+ response.getData());
					}
				}

				// informationFile
				filePathString = File.separator + TechConsts.INFORMATION
						+ File.separator + UUID.randomUUID();

				storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
						+ filePathString;

				response = FileUploadHelper.upload(request,
						storeFileNameWithPath, "informationFile",
						Configs.getAsList("informationFileType"), true);

				if (response.isSuccess())
				{
					if (!FileUploadHelper.EMPTY_FILE.equals(response.getData()))
					{
						user.setInformation(filePathString + "."
								+ response.getData());
					}
				}

				EcanUser dtoUser = this.userService.getByLoginName(user
						.getLoginName());

				if (dtoUser != null)
				{
					if (StringUtils.isBlank(user.getId()))
					{
						response = new AjaxResponse(
								false,
								I18N.parse("i18n.usermanager.msg.nameDumlicate"));
					} else
					{
						if (!dtoUser.getId().equals(user.getId()))
						{
							response = new AjaxResponse(
									false,
									I18N.parse("i18n.usermanager.msg.nameDumlicate"));
						}
					}
				}

				if (response == null)
				{
					EcanUser temp = (EcanUser) this.commonService.get(
							user.getId(), EcanUser.class);

					if (user.getStatus().equals(EcanUser.STATUS.INACTIVE)
							&& !user.getStatus().equals(temp.getStatus()))
					{// 用户状态不能变更为未激活
						response = new AjaxResponse(false,
								I18N.parse("i18n.user.notice.changeToNoActive"));
					}
				}

				if (response.isSuccess())
				{// 上面验证都成功了
					// email 验证
					dtoUser = this.userService.getByEmail(user.getEmail());
					if (dtoUser != null)
					{
						if (StringUtils.isBlank(user.getId()))
						{
							// 新增用户 email不能重复
							response = new AjaxResponse(
									false,
									I18N.parse("i18n.usermanager.msg.emailDumlicate"));
						} else
						{
							if (!user.getId().equals(dtoUser.getId()))
							{
								// 修改用户 email不能重复
								response = new AjaxResponse(
										false,
										I18N.parse("i18n.usermanager.msg.emailDumlicate"));
							}
						}
					}

				}
			}
		}

		if (response.isSuccess())
		{
			if (user.getId() == null
					&& Configs.getInt("notification.mail.send.userCreate") == 1)
			{// 创建用户
				mailSender.send(
						user,
						new HashMap<String, Object>(),
						"notification/mail/userCreateSubject_"
								+ ExecuteContext.getCurrentLang() + ".ftl",
						"notification/mail/userCreateContent_"
								+ ExecuteContext.getCurrentLang() + ".ftl");
				this.commonService.saveTX(user);

				// 通知DMP新增用户
				dmpInterfaceFacade.userAdded(user);
			} else
			{
				// 通知DMP更新用户
				dmpInterfaceFacade.userEditted(user);
				this.commonService.updateTX(user);
			}
			response = new AjaxResponse();
		}

		resp.setContentType("text/html; charset=UTF-8");
		try
		{
			resp.getWriter().write(JsonFactory.toJson(response));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 进入批量增加用户界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("batchAdd")
	public String batchAdd(Model model)
	{
		return "tech/usermanager/batch";
	}

	/**
	 * 上传文件跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("upload")
	public String upload(Model model)
	{

		return "tech/usermanager/upload";
	}

	/**
	 * 根据上传模板进行批量创建用户
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("batchTemplateSave")
	public String batchTemplateSave(Model model, HttpServletRequest request,
			HttpServletResponse response)
	{
		System.out.println("go batchTemplateSave");
		response.setContentType("text/html; charset=UTF-8");
		AjaxResponse res = null;
		String filePathString = File.separator + TechConsts.USER_TEMPLATE_PATH
				+ File.separator + UUID.randomUUID();

		String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
				+ filePathString;

		res = FileUploadHelper.upload(request, storeFileNameWithPath,
				"filename", Configs.getAsList("usertemplate"), true);

		if (res.isSuccess())
		{
			if (!FileUploadHelper.EMPTY_FILE.equals(res.getData()))
			{
				List<EcanUser> imported = importExcel
						.batchSave(CoreConsts.Runtime.APP_ABSOLUTE_PATH
								+ filePathString + "." + res.getData());

				// 给用户发送通知邮件
				mailSender.send(
						imported,
						new HashMap<String, Object>(),
						"notification/mail/userCreateSubject_"
								+ ExecuteContext.getCurrentLang() + ".ftl",
						"notification/mail/userCreateContent_"
								+ ExecuteContext.getCurrentLang() + ".ftl");

				res = new AjaxResponse(true, I18N.parse(
						"i18n.user.msg.importSucc", imported.size()));
			} else
			{
				res = new AjaxResponse(false,
						I18N.parse("i18n.user.msg.importError"));
			}

		} else
		{
			res = new AjaxResponse(false,
					I18N.parse("i18n.user.msg.importError"));
		}

		response.setContentType("text/html; charset=UTF-8");
		try
		{
			response.getWriter().write(JsonFactory.toJson(res));
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * 用户下载模板
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("download")
	public String download(HttpServletRequest request,
			HttpServletResponse response)
	{
		String fileName = exportExcel.ExExcel();
		File file = new File(fileName);
		String names = file.getName();
		System.out.println("file" + names);
		System.out.println("fileName" + fileName);
		response.setHeader("Content-disposition", "attachment;filename="
				+ names);
		response.setContentType("application/msexcel");
		// 定义下载文件的长度
		long fileLength = file.length();
		// 把长整形的文件长度转换为字符串
		String length = String.valueOf(fileLength);
		response.setHeader("content_Length", length);
		// 声明一个输入文件流clientFile,接收并读取己下载的文件
		FileInputStream clientFile;
		try
		{
			clientFile = new FileInputStream(file);
			// 声明一个输出流serverFile获取要下载的文件
			OutputStream serverFile = response.getOutputStream();

			// 把要下载的文件的内容读入输入流clientFile
			int n = 0;
			byte b[] = new byte[100];
			while ((n = clientFile.read(b)) != -1)
			{
				serverFile.write(b, 0, n);
			}
			serverFile.close();
			clientFile.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 批量增加用户。应检查该前缀是否已被使用， 如已被使用则不能继续使用
	 * 
	 * @param model
	 * @param prefix
	 *            批量增加用户的用户前缀
	 * @param company
	 *            公司所属
	 * @param role
	 *            角色
	 * @param password
	 *            初始密码:::密码为空表示随机密码
	 * @param count
	 *            数量
	 * @return
	 */
	@RequestMapping("batchSave")
	public @ResponseBody
	AjaxResponse batchSave(
			Model model,
			@RequestParam("prefix") String prefix,
			@RequestParam("company") String company,
			@RequestParam(value = "proType", required = false) String proType,
			@RequestParam("role") String role,
			@RequestParam("status") String status,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam("count") Integer count)
	{
		if (!prefix.matches("[A-Za-z0-9_]{1,6}"))
		{
			return new AjaxResponse(false,
					I18N.parse("i18n.user.batch.prefixRegex"));
		}

		Integer first = this.userService.batchUserTX(prefix, company, proType,
				role, status, password, count);
		return new AjaxResponse(true, first);
	}

	/**
	 * 列举某个前缀的用户
	 * 
	 * @param model
	 * @param prefix
	 * @return
	 */
	@RequestMapping("listByPrefix")
	public String listByPrefix(Model model,
			@RequestParam("prefix") String prefix,
			@RequestParam("first") Integer first)
	{
		if (prefix.matches("[A-Za-z0-9_]{1,6}"))
		{
			List<EcanUser> list = this.userService
					.listByLoginNamePrefix(prefix);

			for (Iterator<EcanUser> it = list.iterator(); it.hasNext();)
			{// 过滤掉非本次生成的
				Integer num = NumberUtils.toInt(it.next().getLoginName()
						.substring(prefix.length()), 0);
				if (num < first)
				{
					it.remove();
				}
			}

			model.addAttribute("list", list);
		}
		return "tech/usermanager/list";
	}

	public static void main(String[] args)
	{
		System.out.println("111111".matches("[A-Za-z0-9]{1,6}"));
		System.out.println("1111111".matches("[A-Za-z0-9]{1,6}"));
		System.out.println("111Aa".matches("[A-Za-z0-9]{1,6}"));
		System.out.println("你1".matches("[A-Za-z0-9]{1,6}"));
		System.out.println("11_1".matches("[A-Za-z0-9_]{1,6}"));

	}
}
