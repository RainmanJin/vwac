package com.ecannetwork.tech.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.ecannetwork.dto.tech.MwServicecost;
import com.ecannetwork.dto.tech.MwSktcourse;
import com.ecannetwork.tech.controller.bean.RestResponse;
import com.ecannetwork.tech.controller.bean.RestResponseList;

@RequestMapping("retailknowhowht")
@Controller
public class ServiceCostHTController {

	@Autowired
	private CommonService commonService;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthFacade authFacade;
	private CommonDAO commonDAO;

	@RequestMapping("oneitem")
	public @ResponseBody RestResponseList GetOneItem(Model model,
			@RequestParam(value = "id") String id,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {
		try {
			RestResponse resp = this.validateUser(username, password);
			RestResponseList resplist = new RestResponseList();
			if (resp.success()) {
				List<MwServicecost> list = (List<MwServicecost>) commonService.list(
						"from MwServicecost t where t.id=?", id);
				if (list != null && list.size() > 0) {
					resp.setData(list);
					resp.setTotal(list.size() + "");
					resplist.setList(resp);
					resplist.setRespStatus(resp.getRespStatus());
					resp.setRespStatus(null);
				}
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
	
	@RequestMapping("list")
	public @ResponseBody RestResponseList GetList(Model model,
			@RequestParam(value = "id") String id,
			@RequestParam(value = "projectid") String projectid,
			@RequestParam(value = "tableid") String tableid,
			@RequestParam(value = "pageSize", required = false,defaultValue="0") Integer pageSize,
			@RequestParam(value = "currentPage", required = false,defaultValue="1") Integer currentPage,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {
        try {
        	RestResponse resp = this.validateUser(username, password);
    		RestResponseList resplist = new RestResponseList();
    		if (resp.success()) {
    			StringBuilder hql = new StringBuilder("from MwServicecost t where 1=1");
    			if (StringUtils.isNotBlank(projectid)&&Integer.valueOf(projectid)>0) {

    				hql.append(" and projectid = '" + projectid + "'");
    			}
    			if (StringUtils.isNotBlank(tableid)&&Integer.valueOf(tableid)!=-1) {

    				hql.append(" and tableid = '" + tableid + "'");
    			}
    			List<MwServicecost> list = (List<MwServicecost>) commonService.pageListQuery(hql.toString(),
						pageSize, (currentPage - 1) * pageSize);
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
	
	@RequestMapping("saveitem")
	public @ResponseBody RestResponse SaveItem(Model model,
			@RequestParam(value = "projectid") String projectid,
			@RequestParam(value = "content") String content,
			@RequestParam(value = "tableid") String tableid,
			@RequestParam(value = "a3") String a3,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {
		try {
			RestResponse resp = this.validateUser(username, password);
			if (resp.success()) {
				MwServicecost servicecost=new MwServicecost();
				servicecost.setProjectid(Integer.valueOf(projectid));
				servicecost.setContent(content);
	            servicecost.setTableid(tableid);
	            servicecost.setA3(a3);
	            commonService.saveOrUpdateTX(servicecost);
	            return resp;
			}else {
				resp.setData(null);	
				return resp;
			}
			
		} catch (Exception e) {
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
