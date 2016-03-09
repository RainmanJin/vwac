package com.ecannetwork.tech.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.JoinHelper;
import com.ecannetwork.core.util.UUID;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.BaiduYunPush;
import com.ecannetwork.dto.tech.EcanUserMessage;
import com.ecannetwork.dto.tech.TechMdttLN;
import com.ecannetwork.dto.tech.TechMdttLNPkg;
import com.ecannetwork.dto.tech.TechMdttPkg;
import com.ecannetwork.dto.tech.TechMessageType;
import com.ecannetwork.tech.service.MdttLnService;
import com.ecannetwork.tech.util.BaiduYunPushUtil;


/**
 * @author 李伟
 * 2015-7-16下午10:35:49
 * 学习任务
 */
@Controller
@RequestMapping("mdttln")
public class MDTTLNController {

	@Autowired
	private CommonService commonService;
	@Autowired
	private MdttLnService mdttLnService;

	@RequestMapping("index")
	public String index(Model model){
		List<TechMdttLN> list= this.commonService.list("from TechMdttLN t where type='S'  order by t.lastUpdateTime desc");
		List<TechMessageType> metypeList =this.commonService.list("from TechMessageType t order by t.lastUpdateTime asc");
		model.addAttribute("metypeList", metypeList);
		model.addAttribute("list", list);
		return "tech/mdttln/index";
	}
	@RequestMapping("add")
	public String add(Model model)
	{
		//List<TechMessageType> metypeList =this.commonService.list("from TechMessageType t order by t.lastUpdateTime asc");
		//model.addAttribute("metypeList", metypeList);
		return "tech/mdttln/add";
	}
	@RequestMapping("view")
	public String view(Model model,@RequestParam(value = "id", required = false) String id)
	{
		TechMdttLN ln = null;
		if (StringUtils.isNotBlank(id))
		{
			ln=(TechMdttLN) this.mdttLnService.get(id);
		}
		if (ln == null)
		{
			ln = new TechMdttLN();
			ln.setLastUpdateTime(new Date());
		}
		List<TechMessageType> metypeList =this.commonService.list("from TechMessageType t order by t.lastUpdateTime desc");
		model.addAttribute("metypeList", metypeList);
		model.addAttribute("dto", ln);
		return "tech/mdttln/viewfirst";
	}
	@RequestMapping("savefirst")
	public String savefirst(Model model,
			@RequestParam("title") String title,
			@RequestParam("style") String style,
			@RequestParam("starttime") String starttime,
			@RequestParam("endtime") String  endtime,
			//@RequestParam("type") String  type,
			@RequestParam(value = "id", required = false) String id)
	{
		TechMdttLN ln = null;
		if (StringUtils.isNotBlank(id))
		{
			ln=(TechMdttLN) this.mdttLnService.get(id);
			ln.setId(id);
		}
		if (ln == null)
		{
			ln = new TechMdttLN();
			ln.setId(UUID.randomUUID());
		}
	    ln.setTitle(title);
	    ln.setStyle(style);
        //ln.setType(type);
        ln.setType("S");
	    ln.setStarttime(starttime);
		ln.setEndtime(endtime);

	    ln.setLastUpdateTime(new Date());
		this.commonService.saveOrUpdateTX(ln);
		model.addAttribute("dto", ln);
		
		return "tech/mdttln/viewnext";
	}
	@RequestMapping("savenext")
	public String savenext(Model model,
			
			@RequestParam(value = "id", required = false) String id)
	{
		TechMdttLN ln = null;
		if (StringUtils.isNotBlank(id))
		{
			ln = (TechMdttLN) this.commonService.get(id, TechMdttLN.class);
		}
		if (ln == null)
		{
			ln = new TechMdttLN();
		}
		ln.setId(id);
	    ln.setStatus("2");
	    ln.setLastUpdateTime(new Date());
		this.commonService.updateTX(ln);
		model.addAttribute("dto", ln);
		
		Set<String> idSet = new HashSet<String>();
		if(StringUtils.isNotBlank(ln.getUserid())){
			String[] arrayId = ln.getUserid().split(",");
			for(String uid:arrayId){
				idSet.add(uid);
			}
			
			List<EcanUser> userList = this.commonService
			.list("from EcanUser e where e.status = '1' and e.id in (" + JoinHelper.joinToSql(idSet)
									+ ")");
			model.addAttribute("userList", userList);
		}else{
			model.addAttribute("userList", new ArrayList<EcanUser>());
		}
		
		return "tech/mdttln/viewthree";
	}
	@RequestMapping("savelast")
	public @ResponseBody
	AjaxResponse savelast(
			
			@RequestParam(value = "id", required = false) String id)
	{
		TechMdttLN ln = null;
		if (StringUtils.isNotBlank(id))
		{
			ln = (TechMdttLN) this.commonService.get(id, TechMdttLN.class);
		}
		if (ln == null)
		{
			ln = new TechMdttLN();
			ln.setId(UUID.randomUUID());
		}
	    ln.setStatus("3");
	    ln.setLastUpdateTime(new Date());
		this.commonService.updateTX(ln);
		return new AjaxResponse();
	}
	@RequestMapping("save")
	public @ResponseBody
	AjaxResponse save(
			@RequestParam("title") String title,
			@RequestParam("style") String style,
			@RequestParam("starttime") String starttime,
			@RequestParam("endtime") String  endtime,
			//@RequestParam("type") String  type,
			@RequestParam(value = "id", required = false) String id)
	{
		TechMdttLN ln =  new TechMdttLN();
		ln.setId(UUID.randomUUID());
		SimpleDateFormat FormatDate=new SimpleDateFormat("yyyy-MM-dd");
	    ln.setTitle(title);
	    ln.setStyle(style);
	    //ln.setType(type);
	    ln.setType("S");
	    ln.setStatus("2");

	    ln.setStarttime(starttime);
		ln.setEndtime(endtime);

	    ln.setLastUpdateTime(new Date());
		this.commonService.saveOrUpdateTX(ln);
		return new AjaxResponse();
	}
	@RequestMapping("listUsers")
	public String listUsers(Model model, @RequestParam("id") String id)
	{
		List<EcanUser> users = this.commonService
				.list("from EcanUser e where e.status = '1' and e.company='VWAC'" );
		model.addAttribute("list", users);
		return "tech/mdttln/selectuser";
	}
	@RequestMapping(value="chgusers")
	public @ResponseBody
	AjaxResponse chgusers(@RequestParam("id") String id,
			@RequestParam("uids") String uids)
	{
		String ids[] = uids.split(",");
		TechMdttLN ln = (TechMdttLN) this.commonService.get(id,
				TechMdttLN.class);
		StringBuilder s_user_name = new StringBuilder();
		
		for(String uid:ids){
			EcanUser user = (EcanUser) this.commonService.get(uid,EcanUser.class);
			s_user_name.append(user.getName());
			s_user_name.append(",");
			//ln.setUsernames(user.getName());
		}
		
		ln.setUsernames(s_user_name.substring(0, s_user_name.length()-1));
		ln.setUserid(uids);
		//ln.setUsername(JoinHelper.join(ln.getUsernames(), ","));
			/*try {
				String userids =new String(uids.getBytes("ISO-8859-1"), "UTF-8");
				ln.setUserid(userids);
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			this.commonService.updateTX(ln);
			return new AjaxResponse();
	
	}
	@RequestMapping("del")
	public @ResponseBody
	AjaxResponse del(Model model, @RequestParam("id") String id)
	{
		TechMdttLN ln = (TechMdttLN) this.commonService.get(id,
				TechMdttLN.class);
		if (ln != null)
		{
			ln.setStatus("0");
			this.commonService.updateTX(ln);
		}
		return new AjaxResponse(true);
	}
	@RequestMapping("listcourse")
    public String listkejans(Model model, @RequestParam("id") String id){
		TechMdttLN ln =null;
		ln = (TechMdttLN) this.commonService.get(id, TechMdttLN.class);
			List<TechMdttPkg> pkgs=	this.commonService
						.list("from TechMdttPkg t where t.status !=? order by t.lastUpdateTime desc",
								"D");
		List<String> pkgids = this.commonService
				.list("select distinct t.pkgid from TechMdttLNPkg t where t.mdttlnid=?",id);
		Set<String> pkgidsSet = new HashSet<String>();
		pkgidsSet.addAll(pkgids);
		for (Iterator<TechMdttPkg> it = pkgs.iterator(); it.hasNext();)
		{ // 过滤掉已经选择的学员, // 过滤掉所有曾经参加过活动并已经打过分的候选人
			TechMdttPkg t = it.next();
			if (ln.getCoursewareIds().contains(t.getId())||pkgidsSet.contains(t.getId()))
			{
				it.remove();
			}
		}
		model.addAttribute("list", pkgs);
		return "tech/mdttln/selectcourse";
	}
	@RequestMapping(value="chgcourses")
	public @ResponseBody
	AjaxResponse chgcourses(@RequestParam("id") String id,
			@RequestParam("cids") String cids)
	{
		TechMdttLN ln = null;
		if (StringUtils.isNotBlank(id))
		{
			ln = (TechMdttLN) this.commonService.get(id, TechMdttLN.class);
		}
		if (ln == null)
		{
			ln = new TechMdttLN();
		}
		String ids[] = cids.split(",");
		ln.setId(id);
		for (String pid : ids)
		{
			TechMdttPkg pkg=(TechMdttPkg) this.commonService.get(pid, TechMdttPkg.class);
			TechMdttLNPkg lpkg = new TechMdttLNPkg();
			lpkg.setPkgid(pid);
			lpkg.setPkgname(pkg.getFixedName());
			lpkg.setPkgfilepath(pkg.getFilePath());
			lpkg.setMdttlnid(id);
			this.commonService.saveOrUpdateTX(lpkg);
		}
			return new AjaxResponse(true);
	
	}
	@RequestMapping("delcour")
	public @ResponseBody
	AjaxResponse delcour(@RequestParam("cid") String cid,
			 @RequestParam("id") String id)
	{
		
        List<TechMdttLNPkg> pkgs = this.commonService
				.list("from TechMdttLNPkg t where t.mdttlnid=? and t.pkgid=?",
						id, cid);
        if(pkgs !=null){
        	this.commonService.deleteAllTX(pkgs);
        }
		return new AjaxResponse();
	}
	
	@RequestMapping("push")
	public @ResponseBody
	AjaxResponse push(Model model, @RequestParam("id") String id)
	{
		TechMdttLN ln = null;
		if (StringUtils.isNotBlank(id))
		{
			ln = (TechMdttLN) this.commonService.get(id, TechMdttLN.class);
		}
		if (ln == null)
		{
			ln = new TechMdttLN();
			ln.setId(UUID.randomUUID());
		}
	    ln.setStatus("4");
	    ln.setLastUpdateTime(new Date());
		this.commonService.updateTX(ln);
		
		
		List<BaiduYunPush> pushList = this.commonService.list("from BaiduYunPush" );
		
		Map<String, BaiduYunPush> channlidMap = new HashMap<String, BaiduYunPush>();  
		for(BaiduYunPush push:pushList){
			channlidMap.put(push.getId(), push);
		}
		
		String userids = ln.getUserid();
		String[] userid = userids.split(",");
		
		for(String uid:userid){
			if( channlidMap.get(uid)!=null){
				try {
					JSONObject notification = new JSONObject();
					notification.put("title", "系统任务通知");
					notification.put("description","您收到一个系统任务，请及时查看");
					JSONObject jsonCustormCont = new JSONObject();
					jsonCustormCont.put("push_id", id); 
					jsonCustormCont.put("open_type", 1);
					notification.put("custom_content", jsonCustormCont);
					JSONObject jsonAPS = new JSONObject();
					//jsonAPS.put("alert", "Hello Baidu Push");
					jsonAPS.put("sound", "default"); // 设置通知铃声样式
					jsonAPS.put("badge", 1); // 设置角标，提示消息个数
					notification.put("aps", jsonAPS);
					BaiduYunPush pushTemp = channlidMap.get(uid);
					BaiduYunPushUtil.pushMsgToSingleDevice(notification.toString(), pushTemp.getChannelid(), pushTemp.getDeviceType(),pushTemp.getUserid());
				} catch (PushClientException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (PushServerException e) {
					e.printStackTrace();
				}
			}
		}
		
		return new AjaxResponse();
	}
	
	@RequestMapping("deluser")
	public @ResponseBody
	AjaxResponse deluser(@RequestParam("uid") String uid,
			 @RequestParam("id") String id)
	{
		TechMdttLN ln = null;
		if (StringUtils.isNotBlank(id))
		{
			ln = (TechMdttLN) this.commonService.get(id, TechMdttLN.class);
		}
		if (ln == null)
		{
			ln = new TechMdttLN();
		}
		
		List<String> tempList = new ArrayList<String>();
		String[] uids = ln.getUserid().split(",");
		for(String userid:uids){
			tempList.add(userid);
		}
		tempList.remove(uid);
		
		ln.setUserid(tempList.toString().replaceAll("\\[","").replaceAll("\\]", "").replaceAll(" ", ""));
		
		this.commonService.saveOrUpdateTX(ln);
		
        /*List<EcanUserMessage> eums = this.commonService
				.list("from EcanUserMessage e where e.mesid=? and e.userid=?",
						id, uid);
        if(eums !=null){
        	this.commonService.deleteAllTX(eums);
        }*/
		return new AjaxResponse();
	}
}
