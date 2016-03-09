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
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.EcanUserMessage;
import com.ecannetwork.dto.tech.TechMessageType;
import com.ecannetwork.dto.tech.TechUserMessage;
import com.ecannetwork.tech.service.MessageService;
import com.ecannetwork.tech.util.BaiduYunPushUtil;
import com.ecannetwork.dto.tech.BaiduYunPush;
/**
 * @author 李伟
 * 2015-8-7上午9:18:00
 */
@RequestMapping("message")
@Controller
public class MessageController {
	@Autowired
	private CommonService commonService;
	@Autowired
	private MessageService messageService;
	
	private Map<String,String> userMap = new HashMap<String,String>();
	
	public Map<String, String> getUserMap() {
		return userMap;
	}

	public void setUserMap(Map<String, String> userMap) {
		this.userMap = userMap;
	}

	@RequestMapping("index")
	public String index(Model model){
		List<TechUserMessage> list= this.commonService.list("from TechUserMessage t order by t.createTime desc");
		List<TechMessageType> metypeList =this.commonService.list("from TechMessageType t order by t.lastUpdateTime asc");
		model.addAttribute("metypeList", metypeList);
		model.addAttribute("list", list);
		return "tech/message/index";
	}
	
	@RequestMapping("view")
	public String view(Model model,
			@RequestParam(value = "id", required = false) String id)
	{
		TechUserMessage tum = null;
		if (StringUtils.isNotBlank(id))
		{
			//tum=(TechUserMessage) this.commonService.get(id, TechUserMessage.class);
			tum=(TechUserMessage) this.messageService.get(id);
			
		}
		if (tum==null)
		{
			tum = new TechUserMessage();
		}
		List<TechMessageType> metypeList =this.commonService.list("from TechMessageType t order by t.lastUpdateTime asc");
		model.addAttribute("metypeList", metypeList);
		model.addAttribute("dto", tum);
		return "tech/message/view";
	}
	@RequestMapping("add")
	public String add(Model model,
			@RequestParam(value = "id", required = false) String id)
	{
		TechUserMessage tum = null;
		if (StringUtils.isNotBlank(id))
		{
			//tum=(TechUserMessage) this.commonService.get(id, TechUserMessage.class);
			tum=(TechUserMessage) this.messageService.get(id);
			
		}
		if (tum==null)
		{
			tum = new TechUserMessage();
		}
		List<TechMessageType> metypeList =this.commonService.list("from TechMessageType t order by t.lastUpdateTime asc");
		
		
	
		
		List<EcanUser> users = this.commonService
				.list("from EcanUser e where e.status = '1'" );
		
		List<String> userids = this.commonService
				.list("select distinct e.userid from EcanUserMessage e where e.mesid=?",id);
		Set<String> useridsSet = new HashSet<String>();
		useridsSet.addAll(userids);
			for (Iterator<EcanUser> it = users.iterator(); it.hasNext();)
			{ // 过滤掉已经选择的学员, // 过滤掉所有曾经参加过活动并已经打过分的候选人
				EcanUser u = it.next();
				if (tum!=null && tum.getEusers().contains(u.getId())||useridsSet.contains(u.getId()))
				{
					it.remove();
				}
			}
		
		tum.setEusers(useridsSet);
		
		
		
		model.addAttribute("metypeList", metypeList);
		model.addAttribute("dto", tum);
		
		
		
		return "tech/message/add";
	}
	@RequestMapping("listusers")
	public String listUsers(Model model, 
			@RequestParam(value = "id", required = false) String id){
		TechUserMessage tum = null;
		tum = (TechUserMessage) this.commonService.get(id, TechUserMessage.class);
		List<EcanUser> users = this.commonService
				.list("from EcanUser e where e.status = '1'" );
		
		List<String> userids = this.commonService
				.list("select distinct e.userid from EcanUserMessage e where e.mesid=?",id);
		Set<String> useridsSet = new HashSet<String>();
		useridsSet.addAll(userids);
			for (Iterator<EcanUser> it = users.iterator(); it.hasNext();)
			{ // 过滤掉已经选择的学员, // 过滤掉所有曾经参加过活动并已经打过分的候选人
				EcanUser u = it.next();
				if (tum!=null && tum.getEusers().contains(u.getId())||useridsSet.contains(u.getId()))
				{
					it.remove();
				}
			}
		model.addAttribute("list", users);
		return "tech/message/selectuser";
	}
	@RequestMapping("chgusers")
	public @ResponseBody
	AjaxResponse chgusers(@RequestParam("id") String id,
			@RequestParam("uids") String uids)
	{
		TechUserMessage tum = null;
		if (StringUtils.isNotBlank(id))
		{
			tum = (TechUserMessage) this.commonService.get(id, TechUserMessage.class);
		}
		if (tum == null)
		{
			tum = new TechUserMessage();
		}
		tum.setFlag("0");
		this.commonService.saveOrUpdateTX(tum);
		String ids[] = uids.split(",");
		//tum.setId(id);
		for (String uid : ids)
		{
			EcanUserMessage euser = new EcanUserMessage();
			euser.setUserid(uid);
			euser.setStatus("U");
			//euser.setMid(id);
			euser.setMesid(id);
			this.commonService.saveOrUpdateTX(euser);
		}
		//tum.getMessage().get(0).getId();
			return new AjaxResponse(true);
	
	}
	
	
	@RequestMapping("addsave")
	public @ResponseBody
	AjaxResponse addsave(Model model,
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam("type") String type,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "uids",required = false) String uids
			){
		
			
		TechUserMessage tum = null;
		if (StringUtils.isNotBlank(id))
		{
			tum = (TechUserMessage) this.commonService.get(id, TechUserMessage.class);
		}
		if (tum == null)
		{
			tum = new TechUserMessage();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		tum.setCreateTime(sdf.format(date));
		tum.setTitle(title);
		tum.setContent(content);
		tum.setType(type);
		tum.setFlag("0");
		this.commonService.saveOrUpdateTX(tum);
		String ids[] = uids.split(",");
		//tum.setId(id);
		id = tum.getId();
		for (String uid : ids)
		{
			EcanUserMessage euser = new EcanUserMessage();
			euser.setUserid(uid);
			euser.setStatus("U");
			//euser.setMid(id);
			euser.setMesid(id);
			this.commonService.saveOrUpdateTX(euser);
		}
		return new AjaxResponse();
	}
	@RequestMapping("save")
	public @ResponseBody
	AjaxResponse save(Model model,
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam("type") String type,
			@RequestParam(value = "id", required = false) String id
			){
		TechUserMessage tum = null;
		if (StringUtils.isNotBlank(id))
		{
			tum = (TechUserMessage) this.commonService.get(id, TechUserMessage.class);
		}
		if (tum == null)
		{
			tum = new TechUserMessage();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		tum.setCreateTime(sdf.format(date));
		tum.setTitle(title);
		tum.setContent(content);
		tum.setType(type);
		//tum.setStatus(status);
		tum.setFlag("0");
		this.commonService.saveOrUpdateTX(tum);
		return new AjaxResponse();
	}
	@RequestMapping("deluser")
	public @ResponseBody
	AjaxResponse delcour(@RequestParam("uid") String uid,
			 @RequestParam("id") String id)
	{
        List<EcanUserMessage> eums = this.commonService
				.list("from EcanUserMessage e where e.mesid=? and e.userid=?",
						id, uid);
        if(eums !=null){
        	this.commonService.deleteAllTX(eums);
        }
		return new AjaxResponse();
	}
	@RequestMapping("deleteMess")
	public @ResponseBody
	AjaxResponse deleteMess( @RequestParam("id") String id){
		commonService.deleteTX(TechUserMessage.class, id);
		List<EcanUserMessage> eums = this.commonService
				.list("from EcanUserMessage e where e.mesid=?",
						id);
        if(eums !=null){
        	this.commonService.deleteAllTX(eums);
        }
		return new AjaxResponse();
	}
	@RequestMapping("push")
	public @ResponseBody
	AjaxResponse push(Model model, @RequestParam("id") String id)
	{
		TechUserMessage tum = (TechUserMessage) this.commonService.get(id,
				TechUserMessage.class);
		if (tum != null)
		{
			tum.setFlag("1");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String mesTime = sdf.format(date);
			tum.setStatus("U");
			tum.setMesTime(mesTime);
			this.commonService.updateTX(tum);
			
			List<BaiduYunPush> pushList = this.commonService.list("from BaiduYunPush" );
			
			Map<String, BaiduYunPush> channlidMap = new HashMap<String, BaiduYunPush>();  
			for(BaiduYunPush push:pushList){
				channlidMap.put(push.getId(), push);
			}
			
			List<EcanUserMessage> userMesList = commonService
						.list("from EcanUserMessage m where m.mesid = ?",
								id);
			JSONObject notification = new JSONObject();
			notification.put("title", "系统消息通知");
			notification.put("description","您收到一个系统消息，请及时查看");
			JSONObject jsonCustormCont = new JSONObject();
			jsonCustormCont.put("push_id", id); 
			jsonCustormCont.put("open_type", 0);
			notification.put("custom_content", jsonCustormCont);
			JSONObject jsonAPS = new JSONObject();
			//jsonAPS.put("alert", "Hello Baidu Push");
			jsonAPS.put("sound", "default"); // 设置通知铃声样式
			jsonAPS.put("badge", 1); // 设置角标，提示消息个数
			notification.put("aps", jsonAPS);
			for(EcanUserMessage user:userMesList){
				if( channlidMap.get(user.getUserid())!=null){
					try {
						BaiduYunPush pushTemp = channlidMap.get(user.getUserid());
						BaiduYunPushUtil.pushMsgToSingleDevice(notification.toString(), pushTemp.getChannelid(), pushTemp.getDeviceType(),pushTemp.getUserid());
						//BaiduYunPushUtil.pushMsgToSingleDevice(notification.toString(), channlidMap.get(user.getUserid()).getChannelid(), channlidMap.get(user.getUserid()).getDeviceType());
					} catch (PushClientException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (PushServerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			
		}
		return new AjaxResponse(true);
	}
	@RequestMapping("batchpush")
	public @ResponseBody
	AjaxResponse batchActive(Model model, @RequestParam("id") String id)
	{
		String ids = id.substring(0, id.length() - 1);
		String[] _ids = ids.split(",");
		List<TechUserMessage> list = new ArrayList<TechUserMessage>();
		for (int i = 0; i < _ids.length; i++)
		{
			TechUserMessage tum = (TechUserMessage) commonService.get(_ids[i],
					TechUserMessage.class);
			if (tum != null)
			{
				tum.setFlag("1");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String mesTime = sdf.format(date);
				tum.setStatus("U");
				tum.setMesTime(mesTime);
				list.add(tum);
				
				List<BaiduYunPush> pushList = this.commonService.list("from BaiduYunPush" );
				
				Map<String, BaiduYunPush> channlidMap = new HashMap<String, BaiduYunPush>(); 
				
				for(BaiduYunPush push:pushList){
					channlidMap.put(push.getId(), push);
				}
				
				List<EcanUserMessage> userMesList = commonService
				.list("from EcanUserMessage m where m.mesid = ?",
						_ids[i]);
				JSONObject notification = new JSONObject();
				notification.put("title", "系统消息通知");
				notification.put("description","您收到一个系统消息，请及时查看");
				JSONObject jsonCustormCont = new JSONObject();
				jsonCustormCont.put("push_id", id); 
				jsonCustormCont.put("open_type", 0);
				notification.put("custom_content", jsonCustormCont);
				JSONObject jsonAPS = new JSONObject();
				//jsonAPS.put("alert", "Hello Baidu Push");
				jsonAPS.put("sound", "default"); // 设置通知铃声样式
				jsonAPS.put("badge", 1); // 设置角标，提示消息个数
				notification.put("aps", jsonAPS);
				for(EcanUserMessage user:userMesList){
					if( channlidMap.get(user.getUserid())!=null){
						try {
							BaiduYunPush pushTemp = channlidMap.get(user.getUserid());
							BaiduYunPushUtil.pushMsgToSingleDevice(notification.toString(), pushTemp.getChannelid(), pushTemp.getDeviceType(),pushTemp.getUserid());
						} catch (PushClientException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (PushServerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		commonService.saveOrUpdateTX(list);
		return new AjaxResponse();
	}
}
