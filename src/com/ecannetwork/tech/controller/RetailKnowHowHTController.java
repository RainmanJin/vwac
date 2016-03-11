package com.ecannetwork.tech.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.commons.lang.StringUtils;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.app.auth.AuthFacade;
import com.ecannetwork.core.app.user.service.UserService;
import com.ecannetwork.core.module.db.dao.CommonDAO;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.MD5;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.GetresultfieldHT;
import com.ecannetwork.dto.tech.MwSktChapter;
import com.ecannetwork.dto.tech.MwSktcourse;
import com.ecannetwork.dto.tech.MwSktItem;
import com.ecannetwork.dto.tech.MwSktresultitem;
import com.ecannetwork.tech.controller.bean.RestResponse;
import com.ecannetwork.tech.controller.bean.RestResponseList;

@RequestMapping("retailknowhowht")
@Controller
public class RetailKnowHowHTController {
	@Autowired
	private CommonService commonService;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthFacade authFacade;
	private CommonDAO commonDAO;

	@RequestMapping("getchapter")
	public @ResponseBody RestResponseList GetChapter(Model model,
			@RequestParam(value = "id") String id,
			@RequestParam(value = "chapterid") String chapterid,
			@RequestParam(value = "code") String code,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		try {
			RestResponse resp = this.validateUser(username, password);
			RestResponseList resplist = new RestResponseList();
			if (resp.success()) {
				List<MwSktcourse> listcourse = (List<MwSktcourse>) commonService.list(
						"from MwSktcourse t where t.id=? and t.CCode=?", id, code);
				if (listcourse != null && listcourse.size() > 0) {
					String ids = "";
					for (int i = 0; i < listcourse.size(); i++) {
						if (listcourse.size() == 1) {
							ids = "(" + listcourse.get(0).getId().toString() + ")";
						} else {
							if (i == 0) {
								ids = "(" + listcourse.get(i).getId();
							} else if (i == listcourse.size() - 1) {
								ids += "," + listcourse.get(i).getId() + ")";
							} else {
								ids += "," + listcourse.get(i).getId();
							}
						}
					}
					String hql = "from MwSktChapter t where t.cid in ? and t.islock=0 and t.id=?";
					List<MwSktChapter> listChapters = (List<MwSktChapter>) commonService
							.list(hql, ids, id);
					if (listChapters != null && listChapters.size() > 0) {
						resp.setData(listChapters);
						resp.setTotal(listChapters.size() + "");
						resplist.setList(resp);
						resplist.setRespStatus(resp.getRespStatus());
						resp.setRespStatus(null);
					}
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

	@RequestMapping("getchapterfield")
	public @ResponseBody RestResponseList Getchapterfield(Model model,
			@RequestParam(value = "cid") String cid,
			@RequestParam(value = "chapterid") String chapterid,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		try {
			RestResponse resp = this.validateUser(username, password);
			RestResponseList resplist = new RestResponseList();
			if (resp.success()) {
				List<MwSktItem> list = (List<MwSktItem>) commonService.list(
						"from MwSktitem t where t.cid=? and t.chapterid=?",
						Integer.valueOf(cid), Integer.valueOf(chapterid));
				if (list != null && list.size() > 0) {
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

	@RequestMapping("getresult")
	public @ResponseBody RestResponseList Getresult(Model model,
			@RequestParam(value = "cid") String cid,
			@RequestParam(value = "chapterid") String chapterid,
			@RequestParam(value = "uid") String uid,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		try {
			RestResponse resp = this.validateUser(username, password);
			RestResponseList resplist = new RestResponseList();
			if (resp.success()) {
				List<MwSktresultitem> list = (List<MwSktresultitem>) commonService
						.list("from MwSktresultitem t where t.cid=? and t.chapterid=? and t.uid=?",
								Integer.valueOf(cid), Integer.valueOf(chapterid), uid);
				if (list != null && list.size() > 0) {
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

	@RequestMapping("getresultfield")
	public @ResponseBody RestResponseList Getresultfield(Model model,
			@RequestParam(value = "id") String id,
			@RequestParam(value = "uid") String uid,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		try {
			RestResponse resp = this.validateUser(username, password);
			RestResponseList resplist = new RestResponseList();
			if (resp.success()) {
				List<GetresultfieldHT> list=new ArrayList<GetresultfieldHT>();		
				MwSktresultitem sktresultitem = (MwSktresultitem) commonService.get(id,
						MwSktresultitem.class);
				if (sktresultitem != null) {
					String content = sktresultitem.getCContent();
					int chapterid = sktresultitem.getChapterid();
					int cid = sktresultitem.getCid();
					int type = sktresultitem.getCType();// 类型（1 结构，2 排序图 3 树状图）
					String[] childs = content.split("|");
					
					switch (type) {
					case 1:
						GetresultfieldHT hxGetresultfieldHT=new GetresultfieldHT();
						hxGetresultfieldHT.setId("1");
						hxGetresultfieldHT.setChaptertype(type+"");
						hxGetresultfieldHT.setName("横向");
						hxGetresultfieldHT.setType("0");
						hxGetresultfieldHT.setColor("0");
						hxGetresultfieldHT.setParentid("0");
						hxGetresultfieldHT.setParentid2("0");
						hxGetresultfieldHT.setA1("0");
						hxGetresultfieldHT.setA2("0");
						hxGetresultfieldHT.setA3("");
						hxGetresultfieldHT.setA4("");
						list.add(hxGetresultfieldHT);
						
						GetresultfieldHT zxGetHt=new GetresultfieldHT();
						zxGetHt.setId("纵向");
						zxGetHt.setChaptertype(type+"");
						zxGetHt.setName("竖向");
						zxGetHt.setType("0");
						zxGetHt.setColor("0");
						zxGetHt.setParentid("0");
						zxGetHt.setParentid2("0");
						zxGetHt.setA1("0");
						zxGetHt.setA2("0");
						zxGetHt.setA3("");
						zxGetHt.setA4("");
						list.add(zxGetHt);
						
						for (int ii = 0; ii < childs.length; ii++) {
							String child=childs[ii];
							if(child=="") continue;
							String childt=child;
							String[] pars=childt.split(",");
							for (int i = 0; i < pars.length; i++) {
								String[] arrpars=pars[i].split("-");
								for (int j = 0; j < arrpars.length; j++) {
									if(arrpars[j]=="") continue;
									Integer ids=Integer.valueOf(arrpars[j]);
									if(ids==0) continue;
									MwSktItem mo=(MwSktItem)commonService.get(ids.toString(), MwSktItem.class);
									if (mo!=null) {
										GetresultfieldHT item=new GetresultfieldHT();
										item.setId(ids.toString());
										item.setChaptertype(type+"");
										item.setName(mo.getCname());
										item.setType(mo.getCtype().toString());
										item.setColor(mo.getCcolor().toString());
										if (ii==0) {
											item.setParentid("1");
										} else {
											item.setParentid(childs[ii].split("-")[i-1].trim());
										}
										if (ii>0&&i>0) {
											item.setParentid2(childs[ii].split("-")[0]);
										} else {
											item.setParentid2("0");
										}
										item.setA1("0");
										item.setA2(mo.getA2().toString());
										item.setA3(mo.getA3().toString());
										item.setA4(mo.getA4().toString());
										list.add(item);
									}
								}
							}
						}
						break;
					case 2:
					    for (String child : childs) {
							if(child=="")continue;
							String childt=child.trim();
							String[] pars=childt.split(",");
							for (int i = 0; i < pars.length; i++) {
								Integer ids=Integer.valueOf(pars[i],0);
								MwSktItem mo=(MwSktItem)commonService.get(ids.toString(), MwSktItem.class);
								if (mo!=null) {
									GetresultfieldHT item=new GetresultfieldHT();
									item.setId(ids.toString());
									item.setChaptertype(mo.getCtype().toString());
									item.setName(mo.getCname());
									item.setType(mo.getCtype().toString());
									item.setColor(mo.getCcolor().toString());
									if (i==0) {
										item.setParentid("0");
									} else {
										item.setParentid(pars[i-1]);
									}
									item.setParentid2("0");
									item.setA1(mo.getA1().toString());
									item.setA2(mo.getA2().toString());
									item.setA3(mo.getA3().toString());
									item.setA4(mo.getA4().toString());
									list.add(item);
								}
							}
						}
						break;
					case 3:
						for (int ii = 0; ii < childs.length; ii++) {
							String child=childs[ii];
							if(child=="") continue;
							String[] arrchild=child.split("-");
							if (ii==0) {
								Integer idp=Integer.valueOf(arrchild[0]);
								MwSktItem mo=(MwSktItem)commonService.get(idp.toString(), MwSktItem.class);
								if(mo==null) break;
								GetresultfieldHT item=new GetresultfieldHT();
								item.setId(idp.toString());
								item.setChaptertype(type+"");
								item.setName(mo.getCname());
								item.setType(mo.getCtype().toString());
								item.setColor(mo.getCcolor().toString());
								item.setParentid("0");
								item.setParentid2("0");
								item.setA1(arrchild[1]);
								item.setA2(mo.getA2().toString());
								item.setA3(mo.getA3());
								item.setA4(mo.getA4());
								list.add(item);
							}else {
								String childt=child.replace(",-undefined", "").replace(",-0", "");
								String[] pars=childt.split(",");
								for (int i = 0; i < pars.length; i++) {
									String[] arrpar=pars[i].split("-");
									Integer ids=Integer.valueOf(arrpar[0]);
									MwSktItem mo=(MwSktItem)commonService.get(ids.toString(), MwSktItem.class);
									if (mo!=null) {
										GetresultfieldHT item=new GetresultfieldHT();
										item.setId(ids.toString());
										item.setChaptertype(type+"");
										item.setName(mo.getCname());
										item.setType(mo.getCtype().toString());
										item.setColor(mo.getCcolor().toString());
										if (i==0) {
											item.setParentid(childs[0].split("-")[0]);
										}else {
											item.setParentid(pars[0].split("-")[0]);
										}
										item.setParentid2("0");
										item.setA1(arrpar[1]);
										item.setA2(mo.getA2().toString());
										item.setA3(mo.getA3());
										item.setA4(mo.getA4());
										list.add(item);
									}
									
								}
							}
						}
						break;
					}
				}
				resp.setData(list);
				resp.setTotal(list.size() + "");

				resplist.setRespStatus(resp.getRespStatus());
				resp.setRespStatus(null);
				resplist.setList(resp);

				return resplist;
			}else {
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

	@RequestMapping("saveresult")
	public @ResponseBody RestResponse Saveresult(Model model,
			@RequestParam(value = "cid") String cid,
			@RequestParam(value = "chapterid") String chapterid,
			@RequestParam(value = "uid") String uid,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "content") String content,
			@RequestParam(value = "type") Integer type,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		try {
			RestResponse resp = this.validateUser(username, password);
			if (resp.success()) {
				MwSktChapter sktChapter = (MwSktChapter) commonService.get(chapterid,
						MwSktChapter.class);
				MwSktresultitem skMwSktresultitem = new MwSktresultitem();
				if (chapterid != null && !chapterid.equals("")) {
					skMwSktresultitem.setChapterid(Integer.valueOf(chapterid));
				}
				if (cid != null && !cid.equals("")) {
					skMwSktresultitem.setCid(Integer.valueOf(cid));
				}
				if (sktChapter != null) {
					skMwSktresultitem.setCName(sktChapter.getCName());
					skMwSktresultitem.setCType(sktChapter.getType());
				} else {
					skMwSktresultitem.setCName(name);
					skMwSktresultitem.setCType(type);
				}
				skMwSktresultitem.setUid(uid);
				skMwSktresultitem.setUserName(username);
				skMwSktresultitem.setCreateTime(new Date());
				skMwSktresultitem.setCContent(content);
				commonService.saveOrUpdateTX(skMwSktresultitem);
				return resp;
			}else {
				resp.setData(null);
				return resp;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}		
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
}
