package com.ecannetwork.tech.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.UUID;
import com.ecannetwork.core.util.ZIP;
import com.ecannetwork.dto.tech.SellingPointConent;
import com.ecannetwork.dto.tech.SellingPointFile;
import com.ecannetwork.dto.tech.SellingPointMain;
import com.ecannetwork.dto.tech.TechChildrenTag;
import com.ecannetwork.dto.tech.TechContentTag;
import com.ecannetwork.dto.tech.TechMdttPkg;
import com.ecannetwork.tech.controller.bean.Result;

/**
 * 卡片管理
 * 
 * @author lc
 *
 */
@Controller
@RequestMapping("sellingPoint")
public class SellingPointController {
	
	private SimpleDateFormat datetimeFormat = new SimpleDateFormat(
	"yyyy/MM/dd HH:mm:ss");
	
	private final String SELLPOINTPATH = "/tech/upload/sellingPoint/";
	private final String SCORM = "/tech/upload/scorm/";
	private final String TEMPLATECSSPATH = "/js/kindeditor-4.1.10/plugins/template/html/css/style.css";
	private final String TEMPLATEMENUPATH = "/js/kindeditor-4.1.10/plugins/template/html/menu.js";
	
	
	String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
	+ SELLPOINTPATH;
	
	
	
	@Autowired
	private CommonService commonService;
	
	@RequestMapping("index")
	public String index(Model model){
		List<SellingPointMain> list= this.commonService.list("from SellingPointMain s order by s.createTime desc");
		model.addAttribute("list",list);
		return "tech/sellingPoint/index";
	}
	@RequestMapping("conentlist")
	public String conentlist(Model model){
		
		List<SellingPointConent> list= this.commonService.list("from SellingPointConent s order by s.createTime desc");
		model.addAttribute("list",list);
		return "tech/sellingPoint/conent_list";
	}
	@RequestMapping("delmain")
	public @ResponseBody
	AjaxResponse delmain(Model model,
			@RequestParam("id") String  id
			){
		SellingPointMain entity = (SellingPointMain) commonService.get(id, SellingPointMain.class);
		commonService.deleteTX(TechMdttPkg.class,entity.getPkgid());
		commonService.deleteTX(SellingPointMain.class, id);
		deleteDir(new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH+SCORM+entity.getPkgid()+".zip"));
		
		return new AjaxResponse();
	}
	@RequestMapping("add")
	public String add(Model model){
		return "tech/sellingPoint/add";
	}
	
	/**
	 * 创建卡片
	 * @param main
	 * @return
	 */
	@RequestMapping("createPoint")
	public String createPoint(SellingPointMain main){
		
		return "tech/sellingPoint/add";
	}
	/**
	 * 保存主卡片
	 */
	@RequestMapping("savemain")
	public @ResponseBody 
	AjaxResponse savemain(Model model,
			@RequestParam("title") String title,
			@RequestParam(value = "id", required = false) String id
			){
		SellingPointMain spm = null;
		if (StringUtils.isNotBlank(id))
		{
			spm = (SellingPointMain) this.commonService.get(id, SellingPointMain.class);
		}
		if (spm == null)
		{
			spm = new SellingPointMain();
		}
		spm.setTitle(title);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
		Date time = new Date();
		spm.setCreateTime(sdf.format(time));
		spm.setStatus("0");
		this.commonService.saveOrUpdateTX(spm);
		return new AjaxResponse();
	}
	/**
	 * 编辑卡片
	 */
	/*@RequestMapping("view")
	public String view(Model model,
			@RequestParam(value = "id", required = false) String id
			){
		SellingPointMain mainp = (SellingPointMain) this.commonService.get(id, SellingPointMain.class);
		List<Object[]> temliST = (List<Object[]>)commonService.list("from SellingPointConent s join s.mainPoint m where m.id=?",id);
			for(Object[] ob:temliST){
				SellingPointMain smain = (SellingPointMain)ob[1];
				SellingPointConent spc = (SellingPointConent)ob[0];
				if(mainp.getId().equals(smain.getId())){
					mainp.getSpc().add(spc);
				}
			}
			model.addAttribute("dto",mainp);
		return "tech/sellingPoint/view";
		
	}*/
	@RequestMapping("view")
	public String view(Model model,
			@RequestParam(value = "id", required = false) String id
			){
		SellingPointMain mainp = (SellingPointMain) this.commonService.get(id, SellingPointMain.class);
		String conentId = mainp.getContentId();
		if(conentId!=null){
		String[] cids = conentId.split(",");
		for(String cid:cids){
			SellingPointConent spc =  (SellingPointConent) this.commonService.get(cid, SellingPointConent.class);
			mainp.getSpc().add(spc);
		}
		}
			model.addAttribute("dto",mainp);
		return "tech/sellingPoint/view";
		
	}
	/**
	 * 编子卡片
	 */
	@RequestMapping("editconent")
	public String 
	editconent(Model model,
			@RequestParam(value = "id", required = false) String id
			){
		SellingPointConent spc =  (SellingPointConent) this.commonService.get(id, SellingPointConent.class);
		model.addAttribute("dto", spc);
		return "tech/sellingPoint/editconent";
		
	}
	@RequestMapping("addconent")
	public String 
	addconent(Model model,
			@RequestParam("mainId") String mainId
			){
	
		model.addAttribute("mainId", mainId);
		return "tech/sellingPoint/addconent";
		
	}
	@RequestMapping("listconents")
	public String listconents(Model model,
			@RequestParam(value = "id", required = false) String id
			){
		List<SellingPointConent> list= this.commonService.list("from SellingPointConent s order by s.createTime desc");
		SellingPointMain spm =(SellingPointMain) this.commonService.get(id, SellingPointMain.class);
		if(spm.getContentId()!=null){
		String[] cids = spm.getContentId().split(",");
		List cidslist = Arrays.asList(cids);
		Set<String> cidsSet = new HashSet<String>();
		cidsSet.addAll(cidslist);
		for (Iterator<SellingPointConent> it = list.iterator(); it.hasNext();)
		{ // 过滤掉已经选择的学员, // 过滤掉所有曾经参加过活动并已经打过分的候选人
			SellingPointConent s = it.next();
			if (cidsSet.contains(s.getId()))
			{
				it.remove();
			}
		}
		}
		model.addAttribute("list", list);
		return "tech/sellingPoint/selectconent";
	}
	
	@RequestMapping("chgconents")
	public @ResponseBody
	AjaxResponse chgusers(@RequestParam("id") String id,
			@RequestParam("uids") String uids)
	{
		
		SellingPointMain spm = null;
		if (StringUtils.isNotBlank(id))
		{
			spm = (SellingPointMain) this.commonService.get(id, SellingPointMain.class);
		}
		if (spm == null)
		{
			spm = new SellingPointMain();
		}
		String suids=spm.getContentId();
		String puids="";
		if(suids!=null){
		    puids=suids+","+uids;
		}else{
			puids=uids;
		}
		spm.setContentId(puids);
		this.commonService.saveOrUpdateTX(spm);
			return new AjaxResponse(true);
	
	}
	/**
	 * @param model
	 * @return
	 * 添加子标签，
	 * 改造子标签选择方式
	 * 20151018
	 * 同addconent方法共同使用
	 */
	@RequestMapping("add_conent")
	public String 
	add_conent(Model model
			){
		return "tech/sellingPoint/addconent";
		
	}
	@RequestMapping("listtag")
	public String listtag(Model model){
		List<TechContentTag> mainlist = (List<TechContentTag>)commonService.list("from TechContentTag ");
		List<Object[]> temliST = (List<Object[]>)commonService.list("from TechChildrenTag t join t.mainTag m ");
		List<TechContentTag> resultlist= new ArrayList<TechContentTag>();
		for(TechContentTag main: mainlist){
			for(Object[] ob:temliST){
				TechContentTag cmain = (TechContentTag)ob[1];
				TechChildrenTag child = (TechChildrenTag)ob[0];
				if(main.getId().equals(cmain.getId())){
					main.getTct().add(child);
				}
			}
			resultlist.add(main);
		}
		model.addAttribute("list",resultlist);
		
		return "tech/mdttpkg/selecttag";
	}
	/**
	 * 选择标签保存数据库
	 * 
	 * 	 */
	@RequestMapping("chgtags")
	public @ResponseBody
	AjaxResponse chgtags(
			@RequestParam(value = "id", required = false) String id,
			@RequestParam("mainId") String mainId,
			//@RequestParam("content") String content,
			@RequestParam("tagid") String tagid)
	{
		SellingPointConent spc = null;
		if (StringUtils.isNotBlank(id))
		{
			spc = (SellingPointConent) this.commonService.get(id, SellingPointConent.class);
		}
		if (spc == null)
		{
			spc = new SellingPointConent();
		}
			try {
				String userids =new String(tagid.getBytes("ISO-8859-1"), "UTF-8");
				spc.setContentTag(tagid);
				spc.setMainId(mainId);
				//spc.setContent(content);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.commonService.saveOrUpdateTX(spc);
			return new AjaxResponse();
	
	}
	
	/**
	 * 保存编辑子标签
	 */
	@RequestMapping("saveconent")
	public @ResponseBody 
	AjaxResponse saveconent(Model model,
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam("mainId") String mainId,
			@RequestParam(value = "id", required = false) String id
			){
		SellingPointConent spc = null;
		if (StringUtils.isNotBlank(id))
		{
			spc = (SellingPointConent) this.commonService.get(id, SellingPointConent.class);
		}
		if (spc == null)
		{
			spc = new SellingPointConent();
			spc.setId(UUID.randomUUID());
		}
		spc.setTitle(title);
		spc.setContent(content);
		spc.setMainId(mainId);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
		Date time = new Date();
		spc.setCreateTime(sdf.format(time));
		saveContent(spc);
		//this.commonService.saveOrUpdateTX(spc);
		return new AjaxResponse();
	}
	/**
	 * 保存编辑子标签,
	 * 改造子标签选择时使用
	 * 20151018添加
	 * 同saveconent共同使用
	 */
	@RequestMapping("save_conent")
	public @ResponseBody 
	AjaxResponse save_conent(Model model,
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam(value = "id", required = false) String id
			){
		SellingPointConent spc = null;
		if (StringUtils.isNotBlank(id))
		{
			spc = (SellingPointConent) this.commonService.get(id, SellingPointConent.class);
		}
		if (spc == null)
		{
			spc = new SellingPointConent();
			spc.setId(UUID.randomUUID());
		}
		spc.setTitle(title);
		spc.setContent(content);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
		Date time = new Date();
		spc.setCreateTime(sdf.format(time));
		this.commonService.saveOrUpdateTX(spc);
		return new AjaxResponse();
	}
	/**
	 *删除子标签
	 */
	@RequestMapping("delconent")
	public @ResponseBody
	AjaxResponse delconent(
			 @RequestParam("cid") String cid,
			 @RequestParam("id") String id
			){
		String tmp="";
		SellingPointMain spm = (SellingPointMain) this.commonService.get(id, SellingPointMain.class);
		String [] cids =spm.getContentId().split(",");
		String scid="";
		for (int i=0;i<cids.length;i++){
			//spm.getContentId().indexOf(cids[i])!=-1;
			if(cid.equals(cids[i])){
				continue;
			}
			tmp =tmp+cids[i]+",";
		}
		spm.setContentId(tmp);
		this.commonService.saveOrUpdateTX(spm);
			return new AjaxResponse();
		
	}
	/**
	 * 
	 * 发布到资料库，进入发布页面
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("pushpkg")
	public String pushpkg(Model model,
			@RequestParam(value = "id", required = false) String id
			){
		SellingPointMain main = (SellingPointMain) this.commonService.get(id, SellingPointMain.class);
		model.addAttribute("dto",main);
		return "tech/sellingPoint/pushpkg";
	}
	
	/**
	 * 发布
	 * @param model
	 * @param id
	 * @param title
	 * @param openflag
	 * @param proType
	 * @return
	 */
	@RequestMapping("push")
	public @ResponseBody AjaxResponse push(Model model,
			@RequestParam(value = "id", required = false) String id
		
			){
		SellingPointMain main = null;
		if (StringUtils.isNotBlank(id))
		{
			main=(SellingPointMain) this.commonService.get(id, SellingPointMain.class);
		}
		if (main == null)
		{
			main = new SellingPointMain();
			
		}
		this.commonService.saveOrUpdateTX(main);
		
		String html = createCatalogue(main.getTitle());
		
		
		Document index = Jsoup.parse(html);
		Element ulElement = index.getElementsByTag("ul").get(0);
		
		//File mainFileDir = new File();
		if(main.getContentId() == null || main.getContentId().length()==0){
			AjaxResponse ajax = new AjaxResponse();
			ajax.setData("未关联子卡片,发布失败");
			ajax.setSuccess(false);
			return ajax;
		}
		String pkgId = UUID.randomUUID();
		if (StringUtils.isBlank(main.getPkgid())){
			main.setPkgid(pkgId);
		}
		try {
			String[] contents = main.getContentId().split(",");
			String prefix = "";
			if(contents.length>1){
				for(int i=0;i<contents.length;i++){
					String content = contents[i];
					copyDirectiory(CoreConsts.Runtime.APP_ABSOLUTE_PATH+SELLPOINTPATH+"/"+content+"/",CoreConsts.Runtime.APP_ABSOLUTE_PATH+SCORM+main.getPkgid()+"/"+content+"/");
					
					//SellingPointConent content_copy = (SellingPointConent) this.commonService.get(content, SellingPointConent.class);
					
					SellingPointConent contentEntity =(SellingPointConent) this.commonService.get(content, SellingPointConent.class);
					Element a = ulElement.appendElement("a");
					a.attr("href",content+"/index.html");
					Element li = a.appendElement("li");
					if(i<9){
						prefix = "0"+(i+1);
					}else{
						prefix = i+1+"";
					}
					li.appendText(prefix+"、"+contentEntity.getTitle());
					//contentEntity.
					PrintStreamFileIndex(index.toString(), CoreConsts.Runtime.APP_ABSOLUTE_PATH+SCORM+main.getPkgid()+"/");
				}
			}else{
				copyDirectiory(CoreConsts.Runtime.APP_ABSOLUTE_PATH+SELLPOINTPATH+"/"+contents[0]+"/",CoreConsts.Runtime.APP_ABSOLUTE_PATH+SCORM+main.getPkgid()+"/");
			}
			
			
			copyImage(CoreConsts.Runtime.APP_ABSOLUTE_PATH+TEMPLATEMENUPATH, CoreConsts.Runtime.APP_ABSOLUTE_PATH+SCORM+main.getPkgid()+"/", "menu.js");
			//ZIP.zip(new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH+SELLPOINTPATH+main.getPkgid()), new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH+SELLPOINTPATH+main.getPkgid()+".zip"));
			
			ZIP.zip(new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH+SCORM+main.getPkgid()), new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH+SCORM+main.getPkgid()+".zip"));
			
			deleteDir(new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH+SCORM+main.getPkgid()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("压缩失败");
		}

		TechMdttPkg tmpkg = null;
		if (StringUtils.isNotBlank(main.getPkgid()))
		{
			tmpkg=(TechMdttPkg) this.commonService.get(main.getPkgid(), TechMdttPkg.class);
			if (tmpkg == null)
			{
				tmpkg = new TechMdttPkg();
				tmpkg.setVersion("1");
				tmpkg.setVersionCode(1);
				
			}else{
				tmpkg.setVersionCode(tmpkg.getVersionCode()+1);
				tmpkg.setVersion(tmpkg.getVersionCode()+"");
			}
			
		}else {
			main.setPkgid(pkgId);
		}
		if (tmpkg == null)
		{
			tmpkg = new TechMdttPkg();
			tmpkg.setVersion("1");
			tmpkg.setVersionCode(1);
			
		}
		String filePath = SCORM+main.getPkgid()+".zip";
		File file = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH+SCORM+main.getPkgid()+".zip");
		long fileSize = file.length();
		
		String pkgSize = "";
		if(fileSize<1024){
			pkgSize = fileSize+"B";
		}else if(fileSize<1024*1024){
			float a = (float) (fileSize*1.0/(1024));
			float b = (float) (Math.round(a * 10)) / 10;
			pkgSize = b+"K";
		}else{
			float a = (float) (fileSize*1.0/(1024*1024));
			float b = (float) (Math.round(a * 10)) / 10;
			pkgSize = b+"M";
			//pkgSize =  (float)(Math.round(fileSize/(1024.0*10*1024)))/10+"M";
		}
		tmpkg.setId(main.getPkgid());//id
		
		tmpkg.setName(main.getTitle());
		
		tmpkg.setFilePath(filePath);
		tmpkg.setBrand("VW");
		
		//tmpkg.setProType(proType);
		tmpkg.setProType("");
		
		tmpkg.setLastUpdateTime(new Date());
		tmpkg.setStatus("1");
		tmpkg.setConentType("SSP");
		tmpkg.setValid("1");
		tmpkg.setPkgType("SCO");
		tmpkg.setFileSize(pkgSize);
		//tmpkg.setFixedName(title);
		tmpkg.setFixedName(main.getTitle());
		
		tmpkg.setMotorcycle("");
		tmpkg.setosType("0");
		
		//tmpkg.setStatus(openflag);
		tmpkg.setStatus("0");
		
		main.setStatus("1");
		this.commonService.saveOrUpdateTX(tmpkg);
		this.commonService.saveOrUpdateTX(main);
		return new AjaxResponse();
	}
	
	/**
	 * 保存添加卡片内容
	 * @param request
	 * @param entity
	 * @return
	 */
	@RequestMapping("savePointContent")
	@ResponseBody
	public AjaxResponse saveContent(SellingPointConent entity){
		String relPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH+SELLPOINTPATH+"/"+entity.getId()+"/";
		deleteDir(new File(relPath));
		Document doc = Jsoup.parse(entity.getContent());
		Elements imgLists = doc.getElementsByTag("img");
		Long fileAllCount = 0l;
		for(Element element : imgLists){
			String src = element.attr("src");
			try {
				fileAllCount += copyImage(CoreConsts.Runtime.APP_ABSOLUTE_PATH+src, relPath+"images",src.substring(element.attr("src").lastIndexOf("/")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("创建图片失败");
				//e.printStackTrace();
			}
		}
		Elements videoLists = doc.getElementsByTag("video");
		for(Element element : videoLists){
			String src = element.attr("src");
			try {
				fileAllCount += copyImage(CoreConsts.Runtime.APP_ABSOLUTE_PATH+src, relPath+"media",src.substring(element.attr("src").lastIndexOf("/")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("创建视频失败");
				//e.printStackTrace();
			}
		}
		try {
			fileAllCount += copyImage(CoreConsts.Runtime.APP_ABSOLUTE_PATH+TEMPLATECSSPATH, relPath+"css", "style.css");
			fileAllCount += copyImage(CoreConsts.Runtime.APP_ABSOLUTE_PATH+TEMPLATEMENUPATH, relPath, "menu.js");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.err.println("生成css失败");
		}
		//生成html
		fileAllCount += PrintStreamFile(entity.getContent(), relPath).length();
		
		try {
			ZIP.zip(new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH+SELLPOINTPATH+"/"+entity.getId()), new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH+SELLPOINTPATH+"/"+entity.getId()+".zip"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("压缩失败");
		}
		entity.setContentSize(fileAllCount);

		//entity.setTitle("2015-10-20");
		//entity.setId(null);


		this.commonService.saveOrUpdateTX(entity);
		
		return new AjaxResponse();
	}
	
	
	/**
	 * 上传图片
	 */
	@RequestMapping(value="fileupload")
	@ResponseBody
	public Result fileupload(Model model,@RequestParam("dir") String dir,@RequestParam("imgFile") MultipartFile imgFile) throws IOException {
		String relPath = SELLPOINTPATH+dir+"/";
		String fileName = imgFile.getOriginalFilename();
		String fileType = imgFile.getContentType();
		String tail = fileName.substring(fileName.lastIndexOf("."));
        Result result = new Result();
        if("images".equals(dir)){
        	if ( "image/jpeg".equalsIgnoreCase(fileType) ||"image/jpg".equalsIgnoreCase(fileType) 
            		||"image/png".equalsIgnoreCase(fileType) ||"image/gif".equalsIgnoreCase(fileType))   {
    	        
    			try {
    				
    				String realPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH;
    				String url= realPath+relPath;
    				
    				SellingPointFile file = new SellingPointFile();
    				file.setId(UUID.randomUUID());
    				file.setCreateTime(datetimeFormat.format(new Date()));
    				file.setFileName(fileName);
    				file.setFileType(dir);
    				file.setFilePath(relPath+file.getId()+tail);
    				file.setFileSize(imgFile.getSize());
    				file.setTail(tail);
    				commonService.saveOrUpdateTX(file);
    				
    				result.setError(0);
    				result.setUrl(relPath+file.getId()+tail);
    				exportImg(imgFile.getInputStream(), url, file.getId()+tail);
    				
    				return result;
    			} catch (RuntimeException e) {
    				result.setError(1);
    				result.setMessage("网络连接失败");
    				return result;
    			}
            }else{
            	result.setMessage("仅支持jpeg、jpg、png、gif类型的图片");
            	result.setError(1);
            	return result;
            }
        }else if("media".equals(dir)){
        	if ("video/mp4".equalsIgnoreCase(fileType)||"video/avi".equalsIgnoreCase(fileType)
        			||"video/x-ms-wmv".equalsIgnoreCase(fileType)||"application/octet-stream".equalsIgnoreCase(fileType))   {
    	        
    			try {
    				
    				String realPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH;
    				String url= realPath+relPath;
    				
    				SellingPointFile file = new SellingPointFile();
    				file.setId(UUID.randomUUID());
    				file.setCreateTime(datetimeFormat.format(new Date()));
    				file.setFileName(fileName);
    				file.setFileType(dir);
    				file.setFilePath(relPath+file.getId()+tail);
    				file.setFileSize(imgFile.getSize());
    				file.setTail(tail);
    				commonService.saveOrUpdateTX(file);
    				
    				result.setError(0);
    				result.setUrl(relPath+file.getId()+tail);
    				exportImg(imgFile.getInputStream(), url, file.getId()+tail);
    				
    				return result;
    			} catch (RuntimeException e) {
    				result.setError(1);
    				result.setMessage("网络连接失败");
    				return result;
    			}
            }else{
            	result.setMessage("仅支持flv、mp4、avi、wmv类型的视频");
            	result.setError(1);
            	return result;
            }
        	//FIXME 待修改==》如打包时生成文件目录可能不正确或获取不到
        }else if("file".equals(dir)){
			try {
				
				String realPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH;
				String url= realPath+relPath;
				
				SellingPointFile file = new SellingPointFile();
				file.setId(UUID.randomUUID());
				file.setCreateTime(datetimeFormat.format(new Date()));
				file.setFileName(fileName);
				file.setFileType(dir);
				file.setFilePath(relPath+file.getId()+tail);
				file.setFileSize(imgFile.getSize());
				file.setTail(tail);
				commonService.saveOrUpdateTX(file);
				
				result.setError(0);
				result.setUrl(relPath+file.getId()+tail);
				exportImg(imgFile.getInputStream(), url, file.getId()+tail);
				
				return result;
			} catch (RuntimeException e) {
				result.setError(1);
				result.setMessage("网络连接失败");
				return result;
			}
        }else{
        	result.setMessage("文件格式不匹配");
        	result.setError(1);
        	return result;
        }
        
	}
	
	@RequestMapping(value="fileManager")
	@ResponseBody
	public Result fileManager(@RequestParam("dir") String dir,@RequestParam("order") String order,HttpServletRequest request){
		String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png"};
		
		List<SellingPointFile> list = this.commonService.list("from SellingPointFile where fileType=? order by "+order,dir);
		
		List<Hashtable<String,Object>> fileList = new ArrayList<Hashtable<String,Object>>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		if (list!=null){
			
			for (SellingPointFile file : list) {
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = file.getFileName();
				String fileExt = file.getTail().substring(1);
				hash.put("is_dir", false);
				hash.put("has_file", false);
				hash.put("filesize", file.getFileSize());
				hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
				hash.put("filetype", fileExt);
				hash.put("filepath", request.getContextPath()+file.getFilePath());
				hash.put("filename", fileName==null?"":fileName);
				hash.put("datetime", sdf.format(new Date()));
				fileList.add(hash);
			}
		}
		
		
		/*if ("size".equals(order)) {
			Collections.sort(fileList, new SizeComparator());
		} else if ("type".equals(order)) {
			Collections.sort(fileList, new TypeComparator());
		} else {
			Collections.sort(fileList, new NameComparator());
		}*/
		Result result = new Result();
		//result.setMoveup_dir_path(moveupDirPath);
		//result.setCurrent_dir_path(currentDirPath);
		result.setCurrent_url("");
		result.setTotal_count(fileList.size());
		result.setFile_list(fileList);
		return result;
	}
	
	public static void exportImg(InputStream is,String filedir, String fileName) {  
        File dir = new File(filedir);  
        if (!dir.exists()) {  
            dir.mkdirs(); 
           
        }  
        try {  
            File file = new File(filedir+fileName);  
            if(!file.exists()){  
                file.createNewFile();  
            }  
            FileOutputStream fos = new FileOutputStream(file);  
            int size = 0;  
            byte[] Buffer = new byte[1024]; 
            while ((size = is.read(Buffer)) != -1) {  
                fos.write(Buffer, 0, size);  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
	
	 public File PrintStreamFile(String content,String filePath){
		 StringBuilder contentHtml = new StringBuilder();
			contentHtml.append("<!DOCTYPE html>\r\n<html>\r\n<head>\r\n<meta charset=\"utf-8\">")
			.append("\r\n<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;\">")
			.append("\r\n<link rel=\"stylesheet\" href=\"css/style.css\">\r\n</head>\r\n<body>")
			.append(content.replaceAll("/js/kindeditor-4.1.10/plugins/template/html/", "").replaceAll(SELLPOINTPATH, ""))
			.append("</body>").append(addVideoPlayForAndriod()+"\r\n</html>");
		 File dir = new File(filePath);
		 if (!dir.exists()) {  
			 dir.mkdirs(); 
	           
	        }
		 File file = new File(filePath+"index.html");
		 
         try{
        	 if(!file.exists()){  
                 file.createNewFile();  
             }
             FileOutputStream out=new FileOutputStream(file);
             PrintStream p = new PrintStream(out,true,"utf-8");
             p.println(contentHtml);
         } catch (FileNotFoundException e){
             e.printStackTrace();
         } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         return file;
     }
	 
	 public File PrintStreamFileIndex(String content,String filePath){

		 File dir = new File(filePath);
		 if (!dir.exists()) {  
			 dir.mkdirs(); 
	           
	        }
		 File file = new File(filePath+"index.html");
		 
         try{
        	 if(!file.exists()){  
                 file.createNewFile();  
             }
             FileOutputStream out=new FileOutputStream(file);
             PrintStream p = new PrintStream(out,true,"utf-8");
             p.println(content);
             p.close();
             out.close();
         } catch (FileNotFoundException e){
             e.printStackTrace();
         } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         return file;
     }
	 
	 private Long copyImage(String importPath,String exportPath,String fileName) throws IOException{
		 File dir = new File(exportPath);
		 if(!dir.exists()){
			 dir.mkdirs();
		 }
		 File file = new File(exportPath+"/"+fileName);
		 if(!file.exists()){
			 file.createNewFile();
		 }
		 FileInputStream fi= new FileInputStream(importPath);
         BufferedInputStream in = new BufferedInputStream(fi);
         
         FileOutputStream fo = new FileOutputStream(exportPath+"/"+fileName);
         BufferedOutputStream out=new BufferedOutputStream(fo);
         
         byte[] buf=new byte[4096];
         int len=in.read(buf);
         while(len!=-1)
         {
          out.write(buf, 0, len);
          len=in.read(buf);
         }
         out.close();
         fo.close();
         in.close();
         fi.close();
         return file.length();
	 }
	 
	 private static boolean deleteDir(File dir) {
	        if (dir.isDirectory()) {
	            String[] children = dir.list();
	            //递归删除目录中的子目录下
	            for (int i=0; i<children.length; i++) {
	                boolean success = deleteDir(new File(dir, children[i]));
	                if (!success) {
	                    return false;
	                }
	            }
	        }
	        // 目录此时为空，可以删除
	        return dir.delete();
	    }
	 
/*	 private void moveContentTOMain(String mainDir,String contents){
		 File mainFileDir = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH+TechConsts.SCORM_FILE_PATH+mainDir);
		 if(!mainFileDir.exists()){
			 mainFileDir.mkdirs();
		 }
		 String[] arrayContents = contents.split(",");
		 for(String contentid:arrayContents){
			 File contentFile = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH+TechConsts.SCORM_FILE_PATH+contentid);
		 }
		 if (dir.isDirectory()) {
	            String[] children = dir.list();
	            //递归删除目录中的子目录下
	            for (int i=0; i<children.length; i++) {
	                boolean success = deleteDir(new File(dir, children[i]));
	                if (!success) {
	                    return false;
	                }
	            }
	        }
	        // 目录此时为空，可以删除
	        return dir.delete();
	 }*/
	 
	 public static void copyFile(File sourcefile,File targetFile) throws IOException{
	        
	        //新建文件输入流并对它进行缓冲
	        FileInputStream input=new FileInputStream(sourcefile);
	        BufferedInputStream inbuff=new BufferedInputStream(input);
	        
	        //新建文件输出流并对它进行缓冲
	        FileOutputStream out=new FileOutputStream(targetFile);
	        BufferedOutputStream outbuff=new BufferedOutputStream(out);
	        
	        //缓冲数组
	        byte[] b=new byte[1024*5];
	        int len=0;
	        while((len=inbuff.read(b))!=-1){
	            outbuff.write(b, 0, len);
	        }
	        
	        //刷新此缓冲的输出流
	        outbuff.flush();
	        
	        //关闭流
	        inbuff.close();
	        outbuff.close();
	        out.close();
	        input.close();
	        
	        
	    }
	 
	 public static void copyDirectiory(String sourceDir,String targetDir){
	        //新建目标目录
	        
	        (new File(targetDir)).mkdirs();
	        
	        //获取源文件夹当下的文件或目录
	        File sourceDirRoot = new File(sourceDir);
	        File[] file=sourceDirRoot.listFiles();
	        //System.out.println(file.length);
	        //System.out.println("获取源文件夹当下的文件或目录:"+file[0].getName());
	        if(file==null){
	        	return;
	        }
	        for (int i = 0; i < file.length; i++) {
	            if(file[i].isFile()){
	                //源文件
	                File sourceFile=file[i];
	                    //目标文件
	                File targetFile=new File(new File(targetDir).getAbsolutePath()+File.separator+file[i].getName());
	                try {
						copyFile(sourceFile, targetFile);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            
	            }
	            
	            if(file[i].isDirectory()){
	                //准备复制的源文件夹
	                String dir1=sourceDir+file[i].getName();
	                //准备复制的目标文件夹
	                String dir2=targetDir+"/"+file[i].getName();
	                copyDirectiory(dir1, dir2);
	            }
	        }
	        
	    }
	 
	 private static String addVideoPlayForAndriod(){
		 StringBuilder video = new StringBuilder();
		 video.append("<script>")
		 .append(" var u = navigator.userAgent, app = navigator.appVersion;")
		 .append("var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //android终端或者uc浏览器")
		 .append("if(isAndroid){")
		 .append("var v = document.getElementsByName(\"videoplay\");")
		 .append("for(var i=0;i<v.length;i++){")
		 .append("(function(){")
		 .append("var k =i;")
		 .append("v[k].onclick= function(){")
		 .append("window.playVideo.startVideo(v[k].src);")
		 .append(" }")
		 .append("})()")
		 .append("}")
		 .append(" }")
		 .append("</script>");
		 
		 return video.toString();
	 }
	 
	 private static String createCatalogue(String title){
		 StringBuilder catalogue = new StringBuilder();
		 catalogue.append("<!DOCTYPE html><html><head><meta charset=\"utf-8\" /><title></title><meta name=\"viewport\" content=\"width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no\" />");
		 catalogue.append("<style type=\"text/css\">")
		 .append("html,body,ul{margin: 0;padding: 0;}" +
		 		"body{font-family: helvetica sans-serif;}" +
		 		"ul{list-style: none;}" +
		 		"a{text-decoration: none;-webkit-tap-highlight-color: rgba(0,0,0,0);}" +
		 		".list{width:100%}" +
		 		".list h1{text-align: center;}" +
		 		".list ul{width: 100%;border-top: 1px solid #eaeaea;}" +
		 		".list ul li{width: 100%;border-bottom: 1px solid #eaeaea;line-height: 40px;font-weight: bold;text-indent: 2em;color: #363636;}")
		 		.append("</style></head><body><div class=\"list\">")
		 		.append("<h1>"+title+"</h1>").append("<ul></ul></div></body></html>");
		 
		 return catalogue.toString();
	 }
	
}
