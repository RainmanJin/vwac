/**  
 * 文件名：ClientController.java  
 *   
 * 日期：2015年7月6日  
 *  
 */

package com.ecannetwork.tech.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.app.auth.AuthFacade;
import com.ecannetwork.core.app.auth.Menu;
import com.ecannetwork.core.app.user.service.UserService;
import com.ecannetwork.core.i18n.I18N;
import com.ecannetwork.core.module.db.dao.CommonDAO;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.AESEXT;
import com.ecannetwork.core.util.Configs;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.ExecuteContext;
import com.ecannetwork.core.util.JoinHelper;
import com.ecannetwork.core.util.MD5;
import com.ecannetwork.core.util.UUID;
import com.ecannetwork.core.util.CoreConsts.YORN;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.BaiduYunPush;
import com.ecannetwork.dto.tech.EcanUserMessage;
import com.ecannetwork.dto.tech.EcanUserToken;
import com.ecannetwork.dto.tech.TechBookCase;
import com.ecannetwork.dto.tech.TechChildrenTag;
import com.ecannetwork.dto.tech.TechContentTag;
import com.ecannetwork.dto.tech.TechExamMain;
import com.ecannetwork.dto.tech.TechExamProperty;
import com.ecannetwork.dto.tech.TechMdttLN;
import com.ecannetwork.dto.tech.TechMdttLNPkg;
import com.ecannetwork.dto.tech.TechMdttPkg;
import com.ecannetwork.dto.tech.TechMdttPkgTemp;
import com.ecannetwork.dto.tech.TechUserMessage;
import com.ecannetwork.dto.tech.TechmdttlnProgress;
import com.ecannetwork.tech.controller.bean.RestResponse;
import com.ecannetwork.tech.controller.bean.RestResponseList;

/**
 * 
 * @author: 李超
 *
 * @version: 2015年7月6日 下午5:23:26
 */
@RequestMapping("client")
@Controller
public class ClientController {

	@Autowired
	private CommonService commonService;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthFacade authFacade;
	private CommonDAO commonDAO;
	private static final DateFormat FORMATTER = new SimpleDateFormat(
			"yyyy-MM-dd");

	/**
	 * 修改密码
	 * 
	 * @param model
	 * @param username
	 * @param oldpassword
	 * @param newpassword
	 * @return
	 */

	@RequestMapping("modifyPwd")
	public @ResponseBody RestResponse modifyPwd(
			Model model,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "oldpassword", required = true) String oldpassword,
			@RequestParam(value = "password", required = true) String newpassword) {

		try {
			oldpassword = AESEXT.aesDecrypt(oldpassword, AESEXT.AES_KEY);
			newpassword = AESEXT.aesDecrypt(newpassword, AESEXT.AES_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.validateUserOnlyForModifyPwd(username, oldpassword,
				newpassword);
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

	private RestResponse validateUserOnlyForModifyPwd(String username,
			String password, String newpassword) {
		EcanUser user = (EcanUser) this.commonService.listOnlyObject(
				"from EcanUser t where t.loginName = ?", username); // username

		if (user == null) {
			return RestResponse.authedFailedWithErrorUserIDOrPasswd();
		} else {
			if (!user.getLoginPasswd().equals(password)) {
				return RestResponse.authedFailedWithWrongPassword();
			} else if (!validateNameOrPassword(newpassword)) {
				return RestResponse.validateFailure();
			} else {
				if (!StringUtils.equals(user.getStatus(),
						EcanUser.STATUS.ACTIVE)) {
					return RestResponse.authedFailedWithUserStatus();
				} else {
					user.setLoginPasswd(newpassword);
					EcanUser dto = (EcanUser) this.commonService.get(
							user.getId(), EcanUser.class);
					dto.setLoginPasswd(newpassword);
					commonService.updateTX(dto);
					return RestResponse.success(null);
				}
			}
		}

	}

	/**
	 * 消息列表
	 * 
	 * @param model
	 * @param status
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("messageList")
	public @ResponseBody RestResponseList messageList(Model model,
			@RequestParam(value = "status") String status,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password) {
		try {
			RestResponse resp = this.validateUser(username, password);
			RestResponseList resplist = new RestResponseList();
			if (resp.success()) {
				EcanUser user = (EcanUser) resp.getData();
				List<Object[]> list = null;
				if ("A".equals(status)) {
					list = commonService
							.list("from EcanUserMessage m join m.message u where m.userid = ?",
									user.getId());
				} else {
					list = commonService
							.list("from EcanUserMessage m join m.message u where m.userid = ? and m.status = ?",
									user.getId(), status);
				}
				if (list == null) {
					resp.setData(null);
					resplist.setRespStatus(resp.getRespStatus());
					resp.setRespStatus(null);
					resplist.setList(resp);
					return resplist;
				}
				List<TechUserMessage> messageList = new ArrayList<TechUserMessage>();
				// String
				// sql="select student.id, student.name, class.name from student join class on class.id = student.classid  ";
				// SQLQuery
				// query=HibernateSessionFactory.getSession().createSQLQuery(sql);
				// for(Object message : list){
				for (int i = 0; i < list.size(); i++) {
					Object[] obj = list.get(i);
					EcanUserMessage mes1 = (EcanUserMessage) obj[0];
					TechUserMessage mes0 = (TechUserMessage) obj[1];
					mes0.setExts(null);
					mes0.setStatus(mes1.getStatus());
					messageList.add(mes0);
				}
				resp.setData(messageList);
				resp.setTotal(list.size() + "");

				resplist.setRespStatus(resp.getRespStatus());
				resp.setRespStatus(null);
				resplist.setList(resp);

				return resplist;
			} else {
				resp.setData(null);
				resplist.setRespStatus(resp.getRespStatus());
				return resplist;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		// EcanUser user = ExecuteContext.user();
		// return this.validateUserOnlyForModifyPwd(username,
		// oldpassword,newpassword);

	}

	/**
	 * 消息详情
	 * 
	 * @param model
	 * @param id
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("messageDetail")
	public @ResponseBody RestResponse messageDetail(Model model,
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password) {
		try {
			RestResponse resp = this.validateUser(username, password);
			if (resp.success()) {
				EcanUser user = (EcanUser) resp.getData();
				Object[] obj = (Object[]) commonService
						.listOnlyObject(
								"from EcanUserMessage m join m.message u where m.userid = ? and m.mesid=?",
								user.getId(), id);
				if (obj != null) {
					EcanUserMessage message = (EcanUserMessage) obj[0];
					message.setStatus("R");
					message.setExts(null);
					resp.setData(message.getMessage());
					commonService.updateTX(message);
					return resp;
				} else {
					resp.setData(null);
					return resp;
				}
			} else {
				resp.setData(null);
			}

			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取最新收藏列表
	 * 
	 * @param updateTime
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("bookshelf")
	public @ResponseBody RestResponseList bookshelf(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password) {
		try {
			RestResponse resp = this.validateUser(username, password);
			RestResponseList resplist = new RestResponseList();
			if (resp.success()) {
				List<TechBookCase> list = commonService.list(
						"from TechBookCase where userid = ? and status = 1",
						username);
				if (list == null) {
					resp.setData(null);
					resplist.setRespStatus(resp.getRespStatus());
					resp.setRespStatus(null);
					resplist.setList(resp);
					return resplist;
				}

				List<TechBookCase> templist = new ArrayList<TechBookCase>();
				for (TechBookCase book : list) {
					TechBookCase temp = new TechBookCase();
					temp.setId(book.getPkgid());
					temp.setType(book.getType());
					templist.add(temp);
				}

				resp.setData(templist);
				resp.setTotal(templist.size() + "");

				resplist.setRespStatus(resp.getRespStatus());
				resp.setRespStatus(null);
				resplist.setList(resp);

				return resplist;
			} else {
				resp.setData(null);
				resplist.setRespStatus(resp.getRespStatus());
				return resplist;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 更新收藏记录
	 * 
	 * @param id
	 * @param type
	 * @param status
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping({ "uploadRecordInfo" })
	@ResponseBody
	public RestResponseList uploadRecordInfo(@RequestParam("id") String id,
			@RequestParam("type") String type,
			@RequestParam("status") String status,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password) {
		try {
			RestResponse resp = validateUser(username, password);
			RestResponseList resplist = new RestResponseList();
			if (resp.success()) {
				TechBookCase book = (TechBookCase) this.commonService
						.listOnlyObject(
								"from TechBookCase where userid = ? and pkgid=? and type=?",
								new Object[] { username, id, type });
				if (book != null) {
					book.setStatus(status);
					//this.commonService.updateTX(book);
					this.commonService.deleteTX(book);
				} else {
					book = new TechBookCase();
					book.setPkgid(id);
					book.setType(type);
					book.setStatus(status);
					book.setUserid(username);
					DateFormat format1 = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					book.setBookTime(format1.format(new Date()));
					this.commonService.saveTX(book);
				}
				List<TechBookCase> list = this.commonService.list(
						"from TechBookCase where userid = ? and status = 1",
						new Object[] { username });
				if (list == null) {
					resp.setData(null);
					resplist.setRespStatus(resp.getRespStatus());
					resp.setRespStatus(null);
					resplist.setList(resp);
					return resplist;
				}

				List<TechBookCase> templist = new ArrayList<TechBookCase>();
				for (TechBookCase booka : list) {
					TechBookCase temp = new TechBookCase();
					temp.setId(booka.getPkgid());
					temp.setType(booka.getType());
					templist.add(temp);
				}

				resp.setData(templist);
				resp.setTotal(templist.size() + "");

				resplist.setRespStatus(resp.getRespStatus());
				resp.setRespStatus(null);
				resplist.setList(resp);

				return resplist;
			}
			resp.setData(null);
			resplist.setRespStatus(resp.getRespStatus());
			return resplist;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取分类下资料
	 * 
	 * @param updateTime
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("showRecordPkgList")
	public @ResponseBody RestResponseList showRecordPkgList(
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password) {

		RestResponse resp = this.validateUser(username, password);
		RestResponseList resplist = new RestResponseList();
		if (resp.success()) {

			List<TechBookCase> bookList = commonService.list(
					"from TechBookCase where userid = ? and status = 1 and type=?" ,
					username,type);

			if (bookList.size() > 0) {
				// 查询资源
				Set<String> idSet = new HashSet<String>();
				for (TechBookCase book : bookList) {
					idSet.add(book.getPkgid());
				}
				String ids = JoinHelper.joinToSql(idSet);

				List<TechMdttPkg> pkgs = commonService.list(
						"from TechMdttPkg t where t.status=? and t.id in ("
								+ ids + ")", YORN.YES);

				List<TechMdttPkg> pkgList = new ArrayList<TechMdttPkg>();
				for (TechMdttPkg tct : pkgs) {
					TechMdttPkg temp = new TechMdttPkg();
					/*temp.setId(pkg.getId());
					temp.setName(pkg.getName());
					temp.setFilePath(pkg.getFilePath());
					temp.setFixedName(pkg.getFixedName());
					temp.setConentType(pkg.getConentType());
					temp.setStatus(pkg.getStatus());
					temp.setPkgType(pkg.getPkgType());
					temp.setVersionCode(pkg.getVersionCode());*/
					
					
					temp.setId(tct.getId());
					temp.setFixedName(tct.getFixedName());
					temp.setLastUpdateTime(tct.getLastUpdateTime());
					temp.setIconURL(tct.getIconURL());
					temp.setPkgType(tct.getPkgType());
					temp.setFileSize(tct.getFileSize());
					temp.setConentType(tct.getConentType());
					temp.setVersion(tct.getVersion());
					temp.setVersionCode(tct.getVersionCode());
					temp.setBrand(tct.getBrand());
					temp.setProType(tct.getProType());
					temp.setFilePath(tct.getFilePath());
					temp.setStatus(tct.getStatus());
					temp.setTrianPlanID(tct.getTrianPlanID());
					
					pkgList.add(temp);
				}
				resp.setData(pkgList);
				resp.setTotal(pkgList.size() + "");

				resplist.setRespStatus(resp.getRespStatus());
				resp.setRespStatus(null);
				resplist.setList(resp);
			}else{
				resp.setData(new ArrayList<TechMdttPkg>());
				resp.setTotal("0");

				resplist.setRespStatus(resp.getRespStatus());
				resp.setRespStatus(null);
				resplist.setList(resp);
			}

		} else {
			resp.setData(null);
			resplist.setRespStatus(resp.getRespStatus());
		}
		return resplist;

	}

	/**
	 * 上传考试信息
	 * 
	 * @param username
	 * @param password
	 * @param score
	 * @param endTime
	 * @param pkgid
	 * @return
	 */
	@RequestMapping("upLoadExamInfo")
	public @ResponseBody RestResponse upLloadExamInfo(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "score", required = true, defaultValue = "0") int score,
			@RequestParam(value = "id", required = true) String mainId) {
		try {
			RestResponse resp = this.validateUser(username, password);

			if (resp.success()) {
				TechExamProperty exam = (TechExamProperty) this.commonService
						.listOnlyObject(
								"from TechExamProperty where userid = ? and mainId=?",
								new Object[] { username, mainId });
				if (exam != null && exam.getScore() < score) {
					exam.setScore(score);
					commonService.saveOrUpdateTX(exam);
				}else if(exam == null){
					TechExamMain main = (TechExamMain) this.commonService.get(mainId, TechExamMain.class);
					exam = new TechExamProperty();
					exam.setLeftCount(main.getLeftCount()>0?main.getLeftCount()-1:0);
					exam.setMainId(mainId);
					exam.setScore(score);
					exam.setUserid(username);
					commonService.saveOrUpdateTX(exam);
				}
				
			}
			resp.setData(null);
			return resp;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 下载考试附属信息
	 * 
	 * @param username
	 * @param password
	 * @param startTime
	 * @param pkgid
	 * @return
	 */
	@RequestMapping("downLoadExamProperty")
	public @ResponseBody RestResponse downLoadExamProperty(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "id", required = true) String mainId) {
		try {
			RestResponse resp = this.validateUser(username, password);
			if (resp.success()) {
				TechExamProperty exam = (TechExamProperty) this.commonService
						.listOnlyObject(
								"from TechExamProperty where userid = ? and mainId=?",
								new Object[] { username, mainId });
				int leftCount = 0;
				if (exam == null) {
					TechExamMain main  = (TechExamMain) this.commonService.get(mainId, TechExamMain.class);
					leftCount = main.getLeftCount();
					exam = new TechExamProperty();
					exam.setLeftCount(--leftCount);
					exam.setMainId(main.getId());
					exam.setUserid(username);
					exam.setScore(0);
					commonService.saveOrUpdateTX(exam);
				} else {
					leftCount = exam.getLeftCount();
					if (leftCount > 0) {
						exam.setLeftCount(--leftCount);
						commonService.updateTX(exam);
					}
				}
				TechExamProperty result = new TechExamProperty();
				result.setLeftCount(leftCount);
				resp.setData(result);
			} else {
				resp.setData(null);
			}
			return resp;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 请求考试次数
	 * 
	 * @param username
	 * @param password
	 * @param startTime
	 * @param pkgid
	 * @return
	 */
	@RequestMapping("downLoadExamCount")
	public @ResponseBody RestResponse downLoadExamCount(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "id", required = true) String mainId) {
		try {
			RestResponse resp = this.validateUser(username, password);
			if (resp.success()) {
				TechExamProperty exam = (TechExamProperty) this.commonService
						.listOnlyObject(
								"from TechExamProperty where userid = ? and mainId=?",
								new Object[] { username, mainId });
				int leftCount = 0;
				if (exam == null) {
					TechExamMain main  = (TechExamMain) this.commonService.get(mainId, TechExamMain.class);
					leftCount = main.getLeftCount();
				} else {
					leftCount = exam.getLeftCount();
				}
				TechExamProperty result = new TechExamProperty();
				result.setLeftCount(leftCount);
				resp.setData(result);
			} else {
				resp.setData(null);
			}
			return resp;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 任务列表
	 * 
	 * @param model
	 * @param username
	 * @param password
	 * @param startTime
	 * @param endTime
	 * @param type
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	@RequestMapping("taskList")
	public @ResponseBody RestResponseList taskList(
			Model model,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "style", required = false) String style,
			@RequestParam(value = "pageSize", required = false, defaultValue = "0") Integer pageSize,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage) {
		try {
			RestResponse resp = this.validateUser(username, password);
			RestResponseList resplist = new RestResponseList();
			if (resp.success()) {
				EcanUser user = (EcanUser) resp.getData();
				StringBuilder hql = new StringBuilder(
						"from TechMdttLN t where status >=3 and userid like ('%"
								+ user.getId() + "%')");
				if (StringUtils.isNotBlank(startTime)) {

					hql.append(" and starttime >= '" + startTime + "'");
				}
				if (StringUtils.isNotBlank(endTime)) {
					hql.append(" and endtime < '" + endTime + "'");
				}
				if (StringUtils.isNotBlank(style)) {
					hql.append(" and style = '" + style + "'");
				}
				if (StringUtils.isNotBlank(type)) {
					hql.append(" and type = '" + type + "'");
				}
				List<TechMdttLN> list = null;
				if (pageSize > 0) {
					if (currentPage == 0) {
						currentPage = 1;
						list = (List<TechMdttLN>) commonService.pageListQuery(
								hql.toString(), (currentPage - 1) * pageSize,
								pageSize);
					}
				} else {
					list = (List<TechMdttLN>) commonService
							.list(hql.toString());
				}
				
				Set<String> mdttSet = new HashSet<String>();
				for (TechMdttLN mdtt : list)
				{
					
					List<TechMdttLNPkg> courselist= (List<TechMdttLNPkg>)commonService.list("from TechMdttLNPkg t where mdttlnid=?",mdtt.getId());
					List<TechmdttlnProgress> progressList = commonService.list("from TechmdttlnProgress t where mdttlnId=?", mdtt.getId());
					if(courselist.size()>progressList.size()){
						mdtt.getExts().put("isExist", "0");
					}else{
					
						mdttSet.add(mdtt.getId());
						Integer progress = (Integer)commonService.listOnlyObject("select ROUND(AVG(progress),0) as progress from TechmdttlnProgress where mdttlnId=?", mdtt.getId());
						if(progress == null){
							progress = 0;
						}
						mdtt.getExts().put("isExist", progress==100?"1":"0");
					}
				}
				
				List<TechMdttLN> resultList = new ArrayList<TechMdttLN>();
				for (TechMdttLN md : list) {
					TechMdttLN temp = new TechMdttLN();
					temp.setId(md.getId());
					temp.setTitle(md.getTitle());
					temp.setType(md.getType());
					temp.setStyle(md.getStyle());
					temp.setStarttime(md.getStarttime());
					temp.setEndtime(md.getEndtime());
					temp.setIsExist(md.getExts("isExist")+"");
					temp.setExts(null);
					resultList.add(temp);
				}
				resp.setData(resultList);
				resp.setTotal(list.size() + "");
				resplist.setList(resp);
				resplist.setRespStatus(resp.getRespStatus());
				resp.setRespStatus(null);
			} else {
				resp.setData(null);
				resplist.setRespStatus(resp.getRespStatus());
			}

			return resplist;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 任务明细下载
	 * 
	 * @param username
	 * @param password
	 * @param mdttlnid
	 * @return
	 */
	@RequestMapping("showTaskDetail")
	public @ResponseBody RestResponseList showTaskDetail(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "mdttlnid", required = true) String mdttlnid){
		try {
			RestResponse resp = this.validateUser(username, password);
			RestResponseList resplist = new RestResponseList();
			if (resp.success())
			{
				List<TechMdttLNPkg> list= (List<TechMdttLNPkg>)commonService.list("from TechMdttLNPkg t where mdttlnid=?",mdttlnid);
				Set<String> mdttSet = new HashSet<String>();

				for (TechMdttLNPkg mdtt : list)
				{
					mdttSet.add(mdtt.getPkgid());
				}
				String ids = JoinHelper.joinToSql(mdttSet);

				List<TechMdttPkg> pkgList = this.commonService
						.list("from TechMdttPkg t where t.id in (" + ids + ") order by t.lastUpdateTime desc");
				
				Map<String,String> progressMap = new HashMap<String, String>();
				List<TechmdttlnProgress> progressList = (List<TechmdttlnProgress>)commonService.list("from TechmdttlnProgress t where mdttlnId=? and pkgid in (" + ids + ")",mdttlnid);
				for(TechmdttlnProgress progress: progressList){
					progressMap.put(progress.getMdttlnId()+"-"+progress.getPkgid(), progress.getProgress());
				}
				
				List<TechMdttPkgTemp> resultList = new ArrayList<TechMdttPkgTemp>();
				for(TechMdttPkg pkg:pkgList){
					String progress = progressMap.get(mdttlnid+"-"+pkg.getId());
					TechMdttPkgTemp temp = new TechMdttPkgTemp();
					temp.setLastUpdateTime(pkg.getLastUpdateTime());
					temp.setIconURL(pkg.getIconURL());
					temp.setPkgType(pkg.getPkgType());
					temp.setId(pkg.getId());
					temp.setFileSize(pkg.getFileSize());
					temp.setVersion(pkg.getVersion());
					temp.setVersionCode(pkg.getVersionCode());
					temp.setBrand(pkg.getBrand());
					temp.setFixedName(pkg.getFixedName());
					temp.setConentType(pkg.getConentType());
					temp.setProType(pkg.getProType());
					temp.setFilePath(pkg.getFilePath());
					temp.setProgress(progress==null?"0":progress);
					temp.setTrianPlanID(pkg.getTrianPlanID());
					resultList.add(temp);
				}
				
				resp.setData(resultList);
				resp.setTotal(resultList.size()+"");
				resplist.setList(resp);
				resplist.setRespStatus(resp.getRespStatus());
				resp.setRespStatus(null);
			} else
			{
				resp.setData(null);
				resplist.setRespStatus(resp.getRespStatus());
			}
			return resplist;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 个人学习任务上传
	 * 
	 * @param username
	 * @param password
	 * @param mdttlnid
	 * @param tilte
	 * @param style
	 * @param starttime
	 * @param endtime
	 * @param status
	 * @param pkgids
	 * @return
	 */
	@RequestMapping("uploadPersonalTask")
	public @ResponseBody RestResponse uploadPersonalTask(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "title", required = false) String tilte,
			@RequestParam(value = "mdttid", required = true) String mdttid,
			@RequestParam(value = "style", required = false) String style,
			@RequestParam(value = "starttime", required = false) String starttime,
			@RequestParam(value = "endtime", required = false) String endtime,
			@RequestParam(value = "status", required = true) String status,
			@RequestParam(value = "pkgids", required = false) String pkgids) {
		try {
			RestResponse resp = this.validateUser(username, password);
			if (resp.success()) {
				
				EcanUser user = (EcanUser) resp.getData();

				TechMdttLN mdtt = new TechMdttLN();
				mdtt.setId(mdttid);
				commonService.deleteAllTX("from TechMdttLNPkg where mdttlnid = ?", mdttid);
				if("0".equals(status)){
					commonService.deleteTX(mdtt);
					List<TechMdttLNPkg> courselist = (List<TechMdttLNPkg>)commonService.list("from TechMdttLNPkg where mdttlnid=? ",mdttid);
					for(TechMdttLNPkg mpkg:courselist){
						commonService.deleteTX(mpkg);
					}
				}else{
					mdtt.setLastUpdateTime(new Date());
					mdtt.setTitle(tilte);
					mdtt.setStyle(style);
					mdtt.setType("P");
					mdtt.setStarttime(starttime);
					mdtt.setEndtime(endtime);
					mdtt.setStatus("3");
					mdtt.setUserid(user.getId());
					mdtt.setUsername(user.getName());
					mdtt.setLastUpdateTime(new Date());
					
					commonService.saveOrUpdateTX(mdtt);
					if(StringUtils.isNotBlank(pkgids)){
						String[] pkgArray = pkgids.split(",");
						for(String pkgid:pkgArray){
							TechMdttLNPkg mpkg = new TechMdttLNPkg();
							mpkg.setPkgid(pkgid);
							mpkg.setMdttlnid(mdtt.getId());
							commonService.saveOrUpdateTX(mpkg);
							//mpkg.set
						}
					}
					
				}
}
			resp.setData(null);
			return resp;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 */
	@RequestMapping("uploadTaskprogress")
	public @ResponseBody RestResponse uploadTaskprogress(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "mdttlnid", required = true) String mdttlnid,
			@RequestParam(value = "pkgid", required = true) String pkgid,
			@RequestParam(value = "progress", required = true) Integer progress) {
		try {
			RestResponse resp = this.validateUser(username, password);
			if (resp.success()) {
				TechmdttlnProgress mdtt = (TechmdttlnProgress) this.commonService.listOnlyObject("from TechmdttlnProgress where mdttlnId= ? and pkgid = ?", mdttlnid,pkgid);
				if(mdtt==null){
					mdtt = new TechmdttlnProgress();
					mdtt.setMdttlnId(mdttlnid);
					mdtt.setUserId(username);
					mdtt.setPkgid(pkgid);
					mdtt.setProgress(progress+"");
					
					commonService.saveOrUpdateTX(mdtt);
				}else{
					Integer temp = Integer.parseInt(mdtt.getProgress());
					if(temp<progress){
						mdtt.setMdttlnId(mdttlnid);
						mdtt.setUserId(username);
						mdtt.setPkgid(pkgid);
						mdtt.setProgress(progress+"");
						commonService.saveOrUpdateTX(mdtt);
					}
				}
			}
			resp.setData(null);
			return resp;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 资料库标签下载
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("resourceTags")
	public @ResponseBody RestResponseList resourceTags(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password){

		RestResponse resp = this.validateUser(username, password);
		RestResponseList resplist = new RestResponseList();
		if (resp.success())
		{
			
			List<TechContentTag> mainlist = (List<TechContentTag>)commonService.list("from TechContentTag "); 
			//List<Object[]> temliST = (List<Object[]>)commonService.list("from TechChildrenTag t join t.mainTag m ");
			
			List<TechContentTag> pkgList = new ArrayList<TechContentTag>();
			for(TechContentTag tct:mainlist){
				TechContentTag temp = new TechContentTag();
				temp.setId(tct.getId());
				temp.setTagName(tct.getTagName());
				List<TechChildrenTag> childlist = (List<TechChildrenTag>)commonService.list("from TechChildrenTag where mainId = ?",tct.getId());
				List<TechChildrenTag> cList = new ArrayList<TechChildrenTag>();
				for(TechChildrenTag child:childlist){
					TechChildrenTag c = new TechChildrenTag();
					c.setId(child.getId());
					c.setCtagName(child.getCtagName());
					cList.add(c);
				}
				temp.setItems(cList);
				pkgList.add(temp);
			}
			resp.setData(pkgList);
			resp.setTotal(pkgList.size()+"");
			
			resplist.setRespStatus(resp.getRespStatus());
			resp.setRespStatus(null);
			resplist.setList(resp);
			


		} else
		{
			resp.setData(null);
			resplist.setRespStatus(resp.getRespStatus());
		}
		return resplist;
		
	}
	
	
	/**
	 * 资料库标签查询
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("searchForTags")
	public @ResponseBody RestResponseList searchForTags(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "searchkey", required = false) String searchkey,
			@RequestParam(value = "tags", required = false) String tags,
			@RequestParam(value = "proType", required = false) String proType,
			@RequestParam(value = "pageSize", required = false,defaultValue="0") Integer pageSize,
			@RequestParam(value = "currentPage", required = false,defaultValue="1") Integer currentPage) {

		RestResponse resp = this.validateUser(username, password);
		RestResponseList resplist = new RestResponseList();
		int currentp = currentPage;
		int pages = pageSize;
		if (resp.success())
		{
			
			StringBuilder hql = new StringBuilder("from TechMdttPkg where status<>'0' ");
			List<TechMdttPkg> pkgs = null;
			if(searchkey !=null && searchkey.trim().length()>0){
				hql.append("and fixedName like '%"+searchkey+"%'");
			}
			if(proType !=null && proType.trim().length()>0 && !"all".equalsIgnoreCase(proType)){
				hql.append("and proType = '"+proType+"'");
			}
			if(tags==null){
				tags ="";
			}
			String condition[] = tags.split(",");
			if(condition.length>1){
				String [] one = tags.split(",");
				for(String two:one){
					String sear_tag = two.substring(tags.indexOf("@")+1);
					String[] sear_tags = sear_tag.split("\\|"); 
					for(int i=0;i<sear_tags.length;i++){
						if(i==0){
							hql.append("and (motorcycle like '%"+sear_tags[i]+"%'");
							
						}/*else if(i==sear_tags.length-1){
							hql.append("or motorcycle like '%"+sear_tags[i]+"%'");
						}*/else{
							hql.append("or motorcycle like '%"+sear_tags[i]+"%'");
						}
					}
					hql.append(")");
					
				}
				System.out.println("大于1============="+hql);
				//pkgs = commonService.list(hql.toString());
				if(pages > 0){
					pkgs= (List<TechMdttPkg>)commonService.pageListQuery(hql.toString(), currentp+pages,pages );
				}else{
					pkgs= (List<TechMdttPkg>)commonService.list(hql.toString());
				}
			}else if(null==tags||"".equals(tags.trim())){
				pkgs = commonService.list(hql.toString());
			}else if(condition.length==1){
				String sear_tag = tags.substring(tags.indexOf("@")+1);
				String[] sear_tags = sear_tag.split("\\|"); 
				for(int i=0;i< sear_tags.length;i++){
					if(i==0){
						hql.append("and motorcycle like '%"+sear_tags[i]+"%'");
					}else{
						hql.append("or motorcycle like '%"+sear_tags[i]+"%'");
					}
				}
				//pkgs = commonService.list(hql.toString());
				if(pages > 0){
					pkgs= (List<TechMdttPkg>)commonService.pageListQuery(hql.toString(), currentp+pages,pages );
				}else{
					pkgs= (List<TechMdttPkg>)commonService.list(hql.toString());
				}
			}
			
			
			List<TechMdttPkg> pkgList = new ArrayList<TechMdttPkg>();
			for(TechMdttPkg tct:pkgs){
				TechMdttPkg temp = new TechMdttPkg();
				temp.setId(tct.getId());
				temp.setFixedName(tct.getFixedName());
				temp.setLastUpdateTime(tct.getLastUpdateTime());
				temp.setIconURL(tct.getIconURL());
				temp.setPkgType(tct.getPkgType());
				temp.setFileSize(tct.getFileSize());
				temp.setConentType(tct.getConentType());
				temp.setVersion(tct.getVersion());
				temp.setVersionCode(tct.getVersionCode());
				temp.setBrand(tct.getBrand());
				temp.setProType(tct.getProType());
				temp.setFilePath(tct.getFilePath());
				temp.setStatus(tct.getStatus());
				temp.setTrianPlanID(tct.getTrianPlanID());
				pkgList.add(temp);
			}
			resp.setData(pkgList);
			resp.setTotal(pkgList.size()+"");
			
			resplist.setRespStatus(resp.getRespStatus());
			resp.setRespStatus(null);
			resplist.setList(resp);
			


		} else
		{
			resp.setData(null);
			resplist.setRespStatus(resp.getRespStatus());
		}
		return resplist;
		
	}


	/**
	 * 微信日历任务列表
	 * 
	 * @param model
	 * @param username
	 * @param password
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("wxtaskList")
	public @ResponseBody RestResponse wxtaskList() {
		try {
			RestResponse resp = new RestResponse(null, null);
			EcanUser user = ExecuteContext.user();
			if (user != null && user.getLoginPasswd() != null) {
				resp = this.validateUser(user.getLoginName(),
						user.getLoginPasswd());
				StringBuilder hql = new StringBuilder(
						"from TechMdttLN t where status != '0' and userid like ('%"
								+ user.getId() + "%')");
				List<TechMdttLN> list = (List<TechMdttLN>) commonService
						.list(hql.toString());
				List<TechMdttLN> resultList = new ArrayList<TechMdttLN>();

				Calendar startDay = Calendar.getInstance();
				Calendar endDay = Calendar.getInstance();
				List<String> datelist = null;
				List<String> datelist1 = null;
				List<String> datelistend = new ArrayList<String>();

				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getType().equals("P")) {
						startDay.setTime(FORMATTER.parse(list.get(i)
								.getStarttime()));
						endDay.setTime(FORMATTER
								.parse(list.get(i).getEndtime()));
						datelist = printDay(startDay, endDay);
						if (list.get(i).getStarttime()
								.equals(list.get(i).getEndtime())) {
							datelist.add(list.get(i).getStarttime());
						} else {
							datelist.add(list.get(i).getStarttime());
							datelist.add(list.get(i).getEndtime());
						}
						datelistend.addAll(datelist);
						for (int j = 0; j < list.size(); j++) {
							if (list.get(j).getType().equals("P")) {
								startDay.setTime(FORMATTER.parse(list.get(j)
										.getStarttime()));
								endDay.setTime(FORMATTER.parse(list.get(j)
										.getEndtime()));
								datelist1 = printDay(startDay, endDay);
								if (list.get(j).getStarttime()
										.equals(list.get(j).getEndtime())) {
									datelist1.add(list.get(j).getStarttime());
								} else {
									datelist1.add(list.get(j).getStarttime());
									datelist1.add(list.get(j).getEndtime());
								}
								for (int k = 0; k < datelistend.size(); k++) {
									for (int k2 = 0; k2 < datelist1.size(); k2++) {
										if (datelistend.get(k).equals(
												datelist1.get(k2))) {
											datelist1.remove(k2);
										}
									}
								}
								datelistend.addAll(datelist1);
							}
						}
						TechMdttLN temp = new TechMdttLN();
						temp.setType(list.get(i).getType());
						temp.setStarttime(datelistend.toString());
						resultList.add(temp);
						break;
					}
				}
				List<String> sdatelist = null;
				List<String> sdatelist1 = null;
				List<String> sdatelistend = new ArrayList<String>();
				for (int ii = 0; ii < list.size(); ii++) {
					if (list.get(ii).getType().equals("S")) {
						startDay.setTime(FORMATTER.parse(list.get(ii)
								.getStarttime()));
						endDay.setTime(FORMATTER.parse(list.get(ii)
								.getEndtime()));
						sdatelist = printDay(startDay, endDay);
						if (list.get(ii).getStarttime()
								.equals(list.get(ii).getEndtime())) {
							sdatelist.add(list.get(ii).getStarttime());
						} else {
							sdatelist.add(list.get(ii).getStarttime());
							sdatelist.add(list.get(ii).getEndtime());
						}
						sdatelistend.addAll(sdatelist);
						for (int j = 0; j < list.size(); j++) {
							if (list.get(j).getType().equals("S")) {
								startDay.setTime(FORMATTER.parse(list.get(j)
										.getStarttime()));
								endDay.setTime(FORMATTER.parse(list.get(j)
										.getEndtime()));
								sdatelist1 = printDay(startDay, endDay);
								if (list.get(j).getStarttime()
										.equals(list.get(j).getEndtime())) {
									sdatelist1.add(list.get(j).getStarttime());
								} else {
									sdatelist1.add(list.get(j).getStarttime());
									sdatelist1.add(list.get(j).getEndtime());
								}
								for (int k = 0; k < sdatelistend.size(); k++) {
									for (int k2 = 0; k2 < sdatelist1.size(); k2++) {
										if (sdatelistend.get(k).equals(
												sdatelist1.get(k2))) {
											sdatelist1.remove(k2);
										}
									}
								}
								sdatelistend.addAll(sdatelist1);
							}
						}
						TechMdttLN temp = new TechMdttLN();
						temp.setType(list.get(ii).getType());
						temp.setStarttime(sdatelistend.toString());
						resultList.add(temp);
						break;
					}
				}
				resp.setData(resultList);
			} else {
				resp.setData(null);
			}

			return resp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static List<String> printDay(Calendar startDay, Calendar endDay) {
		// 给出的日期开始日比终了日大则返回null
		List<String> dateList = new ArrayList<String>();
		if (startDay.compareTo(endDay) >= 0) {
			return dateList;
		}
		Calendar currentPrintDay = startDay;
		while (true) {
			// 日期加一
			currentPrintDay.add(Calendar.DATE, 1);
			// 日期加一后判断是否达到终了日，达到则终止打印
			if (currentPrintDay.compareTo(endDay) == 0) {
				break;
			}
			dateList.add(FORMATTER.format(currentPrintDay.getTime()));
		}
		return dateList;
	}

	@RequestMapping(value = "/wechatlogin", method = RequestMethod.GET)
	public String loginpage(Model model) {
		EcanUser user = ExecuteContext.user();
		if (user != null && user.getRole() != null) {// 已经登录的用户不能进入登录页面
			return "/tech/wechat/task/taskCalendar";
		}

		model.addAttribute("isLoginView", "1");

		return "/tech/wechat/wechatLogin";
	}

	/**
	 * 接收登录请求
	 * 
	 * @param model
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/wechatlogin", method = RequestMethod.POST)
	public String login(Model model,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {
		EcanUser user = ExecuteContext.user();
		if (user != null && user.getRole() != null) {// 已经登录的用户不能进入登录页面
			return "/tech/wechat/task/taskCalendar";
		}

		//
		user = userService.getByUserName(username);
		if (user == null || !user.getLoginPasswd().equals(password)) {
			model.addAttribute("msg",
					I18N.parse("i18n.login.errorNameOrPassword"));
		} else {// 登录成功
			if (EcanUser.STATUS.SUSPENDED.equals(user.getStatus())) {// 禁用的用户
				model.addAttribute("msg",
						I18N.parse("i18n.login.errorNameOrPassword"));
			} else {
				if (EcanUser.STATUS.INACTIVE.equals(user.getStatus())) {// 未激活
					ExecuteContext.session().setAttribute("inactiveUser", user);
					return "redirect:/p/active";
				} else {// 超级管理员或正常用户

					// 初始化session信息
					// 用户信息
					ExecuteContext.session().setAttribute(
							CoreConsts.SessionScopeKeys.CURRENT_USER, user);

					// 用户的权限菜单
					List<Menu> menus = authFacade.getMenus(user.getRoleId());

					// 为用户增加首页
					menus.add(0, new Menu("home", "i18n.appname.home", user
							.getRole().getHomeUrl(), "i18n.appname.home",
							"0000", 0));

					// 用户所有的已授权的可访问的功能菜单
					Map<String, Menu> authMap = authFacade.authMap(menus);

					// 菜单列表
					ExecuteContext.session().setAttribute(
							CoreConsts.SessionScopeKeys.MENULIST, menus);
					// 已授权的功能列表:::map结构
					ExecuteContext.session().setAttribute(
							CoreConsts.SessionScopeKeys.AUTHED_MAP, authMap);

					String token = UUID.randomUUID();
					ExecuteContext.session().setAttribute("token", token);
					// DMP跳转
					dmpLink(user, token);

					return "/tech/wechat/task/taskCalendar";
				}
			}
		}

		model.addAttribute("isLoginView", "1");
		return "/tech/wechat/wechatLogin";
	}

	private void dmpLink(EcanUser user, String token) {
		if (Configs.getInt("dmp.enable") == 1) {// 开启了dmp
												// String token =
												// UUID.randomUUID();

			ExecuteContext.session().setAttribute("dmpEnable", Boolean.TRUE);
			String url = Configs.get("dmp.loginUrl");
			if (url.indexOf('?') == -1) {
				url += "?token=" + token;
			} else {
				url += "&token=" + token;
			}
			ExecuteContext.session().setAttribute("dmpURL", url);

			EcanUserToken t = (EcanUserToken) this.commonService.get(
					user.getId(), EcanUserToken.class);
			if (t == null) {
				t = new EcanUserToken();
				t.setId(user.getId());
				t.setToken(token);
				t.setUpdateTime(new Date());
				this.commonService.saveTX(t);
			} else {
				t.setToken(token);
				t.setUpdateTime(new Date());
				this.commonService.updateTX(t);
			}
		}
	}

	@RequestMapping("/logout")
	public String logout(Model model, HttpServletRequest request) {
		request.getSession().invalidate();
		return "/tech/wechat/wechatLogin";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/wxdetail")
	public String wxshowTaskDetail(
			@RequestParam(value = "selectday", required = true) String selectday,
			@RequestParam(value = "type", required = true) String type,
			Model model) throws ParseException {
		EcanUser user = ExecuteContext.user();
		if (user != null && user.getLoginPasswd() != null) {
			String hql = "FROM TechMdttLN WHERE status<>'0' AND userid LIKE '%"
					+ user.getId() + "%' AND '" + selectday
					+ "' BETWEEN starttime AND endtime AND TYPE = '" + type
					+ "'";
			List<TechMdttLN> list = (List<TechMdttLN>) commonService.list(hql
					.toString());
			for(int i=0;i<list.size();i++){
				list.get(i).setStarttime(list.get(i).getStarttime().replace("-", "/"));
				list.get(i).setEndtime(list.get(i).getEndtime().replace("-", "/"));
			}
				List<TechMdttPkg> pkgs = (List<TechMdttPkg>) commonService
						.list("from TechMdttPkg");
				List<Object[]> temliST = (List<Object[]>)commonService.list("from TechMdttLNPkg t join t.tmln m ");
				
				List<TechMdttLN> resultlist1= new ArrayList<TechMdttLN>();
				for(TechMdttLN main: list){
					for(Object[] ob:temliST){
						TechMdttLN cmain = (TechMdttLN)ob[1];
						TechMdttLNPkg child = (TechMdttLNPkg)ob[0];
						if(main.getId().equals(cmain.getId())){
							main.getLnpkg().add(child);
						}
						
					}
					resultlist1.add(main);
					
				}
				model.addAttribute("list", resultlist1);
				model.addAttribute("pkglist", pkgs);
			}
		return "/tech/wechat/task/taskDetail";
	}
	
	/**
	 * 记录设备标识及类型，关联人与设备
	 * 
	 * @param model
	 * @param username
	 * @param password
	 * @param channelId
	 * @param deviceType
	 * @return
	 */
	@RequestMapping("baiduyunPush")
	public @ResponseBody RestResponse baiduyunPush(
			Model model,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "channelId", required = true) String channelId,
			@RequestParam(value = "keyType", required = true) String keyType,
			@RequestParam(value = "deviceType", required = true) Integer deviceType) {

		try {
			RestResponse resp = this.validateUser(username, password);
			if (resp.success()) {
				EcanUser user = (EcanUser) resp.getData();
				BaiduYunPush yun = new BaiduYunPush();
				yun.setId(user.getId());
				yun.setLoginName(username);
				yun.setUserid(keyType);
				yun.setChannelid(channelId);
				yun.setDeviceType(deviceType);
				commonService.saveOrUpdateTX(yun);
			}
			resp.setData(null);
			return resp;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}
