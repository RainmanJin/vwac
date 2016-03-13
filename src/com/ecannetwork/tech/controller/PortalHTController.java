package com.ecannetwork.tech.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ecannetwork.core.app.auth.AuthFacade;
import com.ecannetwork.core.app.user.service.UserService;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.db.dao.CommonDAO;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.AESEXT;
import com.ecannetwork.core.util.Configs;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.FileUploadHelper;
import com.ecannetwork.core.util.MD5;
import com.ecannetwork.dto.core.EcanDomainvalueDTO;
import com.ecannetwork.dto.core.EcanI18NPropertiesDTO;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.EcanDomainvalue;
import com.ecannetwork.dto.tech.Lang;
import com.ecannetwork.dto.tech.MwAppPlatForm;
import com.ecannetwork.dto.tech.MwArticle;
import com.ecannetwork.dto.tech.MwCoursecomment;
import com.ecannetwork.dto.tech.TechMdttNotes;
import com.ecannetwork.tech.controller.bean.RestResponse;
import com.ecannetwork.tech.controller.bean.RestResponseList;

@RequestMapping("portalht")
@Controller
public class PortalHTController {
	@Autowired
	private CommonService commonService;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthFacade authFacade;
	private CommonDAO commonDAO;
	
	private static final DateFormat FORMATTER = new SimpleDateFormat(
			"yyyy-MM-dd");

	@RequestMapping("login")
	public @ResponseBody RestResponse GetUserLogin(HttpServletRequest request,
			Model model, @RequestParam(value = "version") String version,
			@RequestParam(value = "apptype") String apptype,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {
		try {
			RestResponse resp = this.validateUser(username, password);
			if (resp.success()) {
				String req = request.getParameter("version");
				Integer toVersion = 0;
				if (version != null && !version.equals("")) {
					toVersion = Integer.valueOf(version);
				}
				String appurl = "";
				if (apptype != null && !apptype.equals("")) {
					if (apptype.toLowerCase().indexOf("android") != -1) {
						appurl = Configs.get("Android.app");
					} else if (apptype.toLowerCase().indexOf("iphone") != -1) {
						appurl = Configs.get("iPhone.app");
					} else {
						appurl = Configs.get("iPad.app");
					}
				}
				if (appurl != null && !appurl.equals("")
						& appurl.indexOf("|") > -1) {
					String[] arr = appurl.split("|");
					if (Integer.valueOf(version) == 0
							|| Integer.valueOf(version) < Integer
									.valueOf(arr[0])) {
						resp.setData("version.mustupdate");

					}
				}
				return resp;
			} else {
				resp.setData(null);
				return resp;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping("newnote")
	public @ResponseBody RestResponse SavenewNote(Model model,
			@RequestParam(value = "pkgid") String pkgid,
			@RequestParam(value = "menuid") String menuid,
			@RequestParam(value = "contenttype") String contenttype,
			@RequestParam(value = "content") String content,
			@RequestParam(value = "ownerid") String ownerid,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password,
			MultipartHttpServletRequest request) {

		try {
			RestResponse resp = this.validateUser(username, password);
			if (resp.success()) {
				String pkgID = "", menuID = "0", contentType = "", conTent = "", ownerID = "";
				String uid = "";
				if (pkgid != null && !pkgid.equals("")) {
					pkgID = pkgid;
				}
				if (menuid != null && !menuid.equals("")) {
					menuID = menuid;
				}
				if (contenttype != null && !contenttype.equals("")) {
					contentType = contenttype;
				}
				if (content != null && !content.equals("")) {
					conTent = content;
				}
				if (ownerid != null && !ownerid.equals("")) {
					ownerID = ownerid;
				}

				TechMdttNotes notesModel = new TechMdttNotes();
				notesModel.setPkgID(pkgID);
				notesModel.setMenuID(menuID);
				notesModel.setContentType(contentType);
				notesModel.setContent(conTent);
				notesModel.setOwnerID(ownerID);
				notesModel.setCreateTime(new Date());
				
				MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
				MultipartFile multipartFile = multipartHttpServletRequest
						.getFile("pkgimg");
				String fileName = multipartFile.getOriginalFilename();
				if (fileName!=null&&!fileName.equals("")) {
					String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH+File.separator+"uploads" + File.separator + "ipadnotes"
							+ File.separator + fileName.split("\\.")[0];
					//判断路径
					File dirFile = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH+File.separator+"uploads");
					if (!dirFile.exists()) {
						dirFile.mkdir();
						dirFile=new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
							+ File.separator +"uploads"+File.separator+"ipadnotes");
						dirFile.mkdir();
					}else {
						dirFile=new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
								+ File.separator +"uploads"+File.separator+"ipadnotes");
						if (!dirFile.exists()) {
							dirFile.mkdir();
						}				
					}
					//上传文件
					File file = new File(storeFileNameWithPath + "."
							+ fileName.split("\\.")[1]);
					if (!file.exists()) {
						AjaxResponse response = null;
						response = FileUploadHelper.upload(request, storeFileNameWithPath,
								"pkgimg", Configs.getAsList("courseAttachementFileType"), true);
						String filePathString=File.separator+"uploads" + File.separator
								+ "ipadnotes" + File.separator + fileName.split("\\.")[0] + "."
								+ fileName.split("\\.")[1].toLowerCase();
						String apath = filePathString.replace('\\', '/');
						notesModel.setPkgimg(apath);
					}
				}
				
				commonService.saveOrUpdateTX(notesModel);
				return resp;

			} else {
				resp.setData(null);
				return resp;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping("notes")
	public @ResponseBody RestResponseList GetNotes(Model model,
			@RequestParam(value = "pkgid") String pkgid,
			@RequestParam(value = "menuid") String menuid,
			@RequestParam(value = "ownerid") String ownerid,
			@RequestParam(value = "uid") String uid,
			@RequestParam(value = "pagesize") String pagesize,
			@RequestParam(value = "currentpage") String currentpage,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {
		try {
			RestResponse resp = this.validateUser(username, password);
			RestResponseList resplist = new RestResponseList();
			if (resp.success()) {
				String pkgID = "", menuID = "", ownerID = "";
				int pageSize = 0, currentPage = 1;
				String uId = "";
				if (pkgid != null && !pkgid.equals("")) {
					pkgID = pkgid;
				}
				if (menuid != null && !menuid.equals("")) {
					menuID = menuid;
				}
				if (ownerid != null && !ownerid.equals("")) {
					ownerID = ownerid;
				}
				if (uid != null && !uid.equals("")) {
					uId = uid;
				}
				if (pagesize != null && !pagesize.equals("")) {
					pageSize = Integer.valueOf(pagesize);
				}
				if (currentpage != null && !currentpage.equals("")) {
					currentPage = Integer.valueOf(currentpage);
				}

				StringBuilder hql = new StringBuilder(
						"from TechMdttNotes t where 1=1");
				if (StringUtils.isNotBlank(pkgID)) {

					hql.append(" and pkgID = '" + pkgID + "'");
				}
				if (StringUtils.isNotBlank(menuID)) {

					hql.append(" and menuID = '" + menuID + "'");
				}
				if (StringUtils.isNotBlank(ownerID)) {

					hql.append(" and ownerID = '" + ownerID + "'");
				}
				if (StringUtils.isNotBlank(uId)) {

					hql.append(" and uId = '" + uId + "'");
				}
				List<TechMdttNotes> list = (List<TechMdttNotes>) commonService
						.pageListQuery(hql.toString(), pageSize, (currentPage - 1)
								* pageSize);
				if (list != null && list.size() > 0) {
					for (TechMdttNotes techMdttNotes : list) {
						EcanUser user=(EcanUser)commonService.get(techMdttNotes.getOwnerID(), EcanUser.class);
						if (user!=null) {
							techMdttNotes.setName(user.getName());
						}
					}
					resp.setData(list);
					resp.setTotal(list.size() + "");
					resplist.setList(resp);
					resplist.setRespStatus(resp.getRespStatus());
					resp.setRespStatus(null);
				} else {
					resp.setData(null);
					resplist.setRespStatus(resp.getRespStatus());
				}
				return resplist;
			} else {
				resp.setData(null);
				resplist.setRespStatus(resp.getRespStatus());
				return resplist;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping("delnote")
	public @ResponseBody RestResponse DelNote(Model model,
			@RequestParam(value = "uid") String uid,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {
		try {
			RestResponse resp = this.validateUser(username, password);
			if (resp.success()) {
				if (uid == null || uid.equals("")) {
					resp.setData(null);
					return resp;
				} else {
					commonService.deleteAllTX(
							"from TechMdttNotes t where uid=?",
							Integer.valueOf(uid));
					return resp;
				}
			} else {
				resp.setData(null);
				return resp;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping("gethomenew")
	public @ResponseBody RestResponseList GetHomenew(
			Model model,
			@RequestParam(value = "id") String id,
			@RequestParam(value = "pageSize", required = false, defaultValue = "0") Integer pageSize,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		try {
			RestResponse resp =this.validateUserhomenew(username, password);
			RestResponseList resplist = new RestResponseList();
			
			if (resp.success()) {
				StringBuilder hql = new StringBuilder(
						//"from MwArticle t where typeId='homenew' and id=" + id);
						"from MwArticle t where typeId='homenew'");
				List<MwArticle> list = (List<MwArticle>) commonService
						.pageListQuery(hql.toString(), pageSize, (currentPage - 1)
								* pageSize);
				if (list != null && list.size() > 0) {
					resp.setData(list);
					resp.setTotal(list.size() + "");
					resplist.setList(resp);
					resplist.setRespStatus(resp.getRespStatus());
					resp.setRespStatus(null);
				}
			} else {
				resp.setData(null);
				resplist.setRespStatus(resp.getRespStatus());
			}
			return resplist;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping("getgg")
	public @ResponseBody RestResponseList GetGG(
			Model model,
			@RequestParam(value = "id") String id,
			@RequestParam(value = "pageSize",required=false,defaultValue="0") Integer pageSize,
			@RequestParam(value = "currentPage",required=false,defaultValue="1") Integer currentPage,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		try {
			RestResponse resp = this.validateUser(username, password);
			RestResponseList resplist = new RestResponseList();
			if (resp.success()) {
				StringBuilder hql = new StringBuilder(
						//"from MwArticle t where typeId='gg' and id=" + id);
						"from MwArticle t where typeId='gg'");
				List<MwArticle> list = (List<MwArticle>) commonService
						.pageListQuery(hql.toString(), pageSize, (currentPage - 1)
								* pageSize);
				if (list != null && list.size() > 0) {
					resp.setData(list);
					resp.setTotal(list.size() + "");
					resplist.setList(resp);
					resplist.setRespStatus(resp.getRespStatus());
					resp.setRespStatus(null);
				}
			} else {
				resp.setData(null);
				resplist.setRespStatus(resp.getRespStatus());
			}
			return resplist;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping("gettag")
	public @ResponseBody RestResponseList GetContentTag(
			Model model,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "pageSize", required = false, defaultValue = "0") Integer pageSize,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		try {
			RestResponse resp = this.validateUser(username, password);
			RestResponseList resplist = new RestResponseList();
			if (resp.success()) {
				//List<EcanDomainvalueDTO> dolist = GetListDTO(type);
				List<EcanDomainvalue> list = GetList(type);
				List<Lang> langs = new ArrayList<Lang>();
				if (list != null && list.size() > 0) {
					for (EcanDomainvalue ecanDomainvalue : list) {
						List<EcanI18NPropertiesDTO> liststr2 = new ArrayList<EcanI18NPropertiesDTO>();
						String label = ecanDomainvalue.getDomainlabel()
								.replace("_T_:", "");
						List<EcanI18NPropertiesDTO> ds2 = GetI18NList(label);
						if (ds2 != null && ds2.size() > 0) {
							for (EcanI18NPropertiesDTO ecanI18NPropertiesDTO : ds2) {
								liststr2.add(ecanI18NPropertiesDTO);
							}
						}
						Lang lang = new Lang();
						lang.setKey(ecanDomainvalue.getValue().toString());
						lang.setLangValueEs(liststr2);
						langs.add(lang);
					}
				}
				resp.setData(langs);
				resp.setTotal(langs.size() + "");

				resplist.setRespStatus(resp.getRespStatus());
				resp.setRespStatus(null);
				resplist.setList(resp);
			} else {
				resp.setData(null);
				resplist.setRespStatus(resp.getRespStatus());
			}
			return resplist;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	private List<EcanDomainvalueDTO> GetListDTO(String condition) {
		List<EcanDomainvalueDTO> list = null;
		list = (List<EcanDomainvalueDTO>) commonService.list(
				"from EcanDomainvalueDTO where domainId=? order by indexsequnce asc", condition);
		return list;
	}
	
	private List<EcanDomainvalue> GetList(String condition) {
		List<EcanDomainvalue> list = null;
		list = (List<EcanDomainvalue>) commonService.list(
				"from EcanDomainvalue where domainId=? order by indexsequnce asc", condition);
		return list;
	}

	private List<EcanI18NPropertiesDTO> GetI18NList(String condition) {
		List<EcanI18NPropertiesDTO> list = null;
		list = (List<EcanI18NPropertiesDTO>) commonService.list(
				"from EcanI18NPropertiesDTO where propertyId=?", condition);
		return list;
	}

	@RequestMapping("getcoursecomment")
	public @ResponseBody RestResponseList Getcoursecomment(
			Model model,
			@RequestParam(value = "pkgid") String pkgid,
			@RequestParam(value = "id") String id,
			@RequestParam(value = "pageSize", required = false, defaultValue = "0") Integer pageSize,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		try {
			RestResponse resp = this.validateUser(username, password);
			RestResponseList resplist = new RestResponseList();
			if (resp.success()) {
				if (pkgid==null||pkgid.equals("")||id==null) {
					resp.setData(null);
					resplist.setRespStatus(resp.getRespStatus());
				} else {
					StringBuilder hql = new StringBuilder(
							"from MwCoursecomment t where 1=1");
					if (StringUtils.isNotBlank(pkgid)) {

						hql.append(" and cid = '" + pkgid + "'");
					}
					if (StringUtils.isNotBlank(id)) {

						hql.append(" and id = '" + id + "'");
					}

					List<MwCoursecomment> list = (List<MwCoursecomment>) commonService
							.pageListQuery(hql.toString(), pageSize, (currentPage - 1)
									* pageSize);
					if (list!=null&&list.size()>0) {
						for (MwCoursecomment mwCoursecomment : list) {
							String userid=mwCoursecomment.getUid();
							EcanUser user=(EcanUser)commonService.get(userid, EcanUser.class);
							if (user!=null) {
								mwCoursecomment.setName(user.getName());
							}
							
						}
					}
					resp.setData(list);
					resp.setTotal(list.size() + "");
					resplist.setList(resp);
					resplist.setRespStatus(resp.getRespStatus());
					resp.setRespStatus(null);
				}
				
				return resplist;
			} else {
				resp.setData(null);
				resplist.setRespStatus(resp.getRespStatus());
				return resplist;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping("newcoursecomment")
	public @ResponseBody RestResponse Savecoursecomment(Model model,
			@RequestParam(value = "pkgid") String pkgid,
			@RequestParam(value = "menuid",required=false,defaultValue="0") String menuid,
			@RequestParam(value = "title") String title,
			@RequestParam(value = "content") String content,
			@RequestParam(value = "uid") String uid,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {
		try {
			RestResponse resp = this.validateUser(username, password);
			if (resp.success()) {
				RestResponseList resplist = new RestResponseList();
				if (pkgid == null || pkgid.equals("") || content == null
						|| content.equals("") || uid == null || uid.equals("")) {
					resp.setData(null);
				} else {
					MwCoursecomment coMwCoursecomment = new MwCoursecomment();
					coMwCoursecomment.setCid(pkgid);
					coMwCoursecomment.setUid(uid);
					coMwCoursecomment.setCcid(Integer.valueOf(menuid));
					coMwCoursecomment.setTitle(title);
					coMwCoursecomment.setRemark(content);
					coMwCoursecomment.setCreateTime(new Date());
					coMwCoursecomment.setReTime(new Date());
					commonService.saveOrUpdateTX(coMwCoursecomment);
				}
				return resp;
			} else {
				resp.setData(null);
				return resp;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping("appupdte")
	public @ResponseBody RestResponse GetAppUpdate(Model model,
			@RequestParam(value = "pkgid") String pkgid,
			@RequestParam(value = "vercode") String vercode,
			@RequestParam(value = "ostype") String ostype,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		try {
			RestResponse resp = this.validateUser(username, password);
			if (resp.success()) {
				List<MwAppPlatForm> appPlatForm = (List<MwAppPlatForm>) commonService
						.list("from MwAppPlatForm t where t.status='1' and t.pkgid=?",
								pkgid);
				if (appPlatForm != null
						&& appPlatForm.size() > 0
						&& appPlatForm.get(0).getVersionCode() > Integer
								.valueOf(vercode)) {
					if (ostype == "apk") {
						resp.setData("有更新版本" + appPlatForm.get(0).getApkurl()
								+ "," + appPlatForm.get(0).getVersionCode());
					} else {
						resp.setData("有更新版本" + appPlatForm.get(0).getIosurl()
								+ "," + appPlatForm.get(0).getVersionCode());
					}

				}
				return resp;
			} else {
				resp.setData(null);
				return resp;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	private boolean validateNameOrPassword(String text) {
		if (text != null) {
			if (text.length() >= 5 && text.length() <= 25) {
				String check = "[a-zA-Z0-9_]{5,25}";
				Pattern regex = Pattern.compile(check);
				Matcher matcher = regex.matcher(text);
				if (matcher.matches()) {
					return true;
				} else {
					check = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
					regex = Pattern.compile(check);
					matcher = regex.matcher(text);
					return matcher.matches();
				}
			}
		}
		return false;
	}

	private RestResponse validateUser(String username, String password) {
		EcanUser user = (EcanUser) this.commonService.listOnlyObject(
				"from EcanUser t where t.loginName = ?", username); // username

		if (user == null) {
			return RestResponse.authedFailedWithErrorUserIDOrPasswd();
		} else {
			String md5Passwd = MD5.encode(user.getLoginPasswd());
			if (!StringUtils.equals(password, md5Passwd)) {
				return RestResponse.authedFailedWithErrorUserIDOrPasswd();
			} else {
				if (!StringUtils.equals(user.getStatus(),
						EcanUser.STATUS.ACTIVE)) {
					return RestResponse.authedFailedWithUserStatus();
				} else {
					user.setLoginPasswd(null);

					return RestResponse.success(user);
				}
			}
		}

	}
	
	private RestResponse validateUserhomenew(String username, String password) {
		EcanUser user = (EcanUser) this.commonService.listOnlyObject(
				"from EcanUser t where t.loginName = ?", username); // username

		if (user!=null) {
			user.setLoginPasswd(null);

			return RestResponse.success(user);
		} else {
			return RestResponse.success(null);
		}
	}
}
