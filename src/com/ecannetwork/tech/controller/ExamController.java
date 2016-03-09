package com.ecannetwork.tech.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ecannetwork.core.i18n.I18N;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.Configs;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.FileUploadHelper;
import com.ecannetwork.core.util.JsonFactory;
import com.ecannetwork.core.util.UUID;
import com.ecannetwork.dto.tech.TechExamAnswer;
import com.ecannetwork.dto.tech.TechExamMain;
import com.ecannetwork.dto.tech.TechExamQuestion;
import com.ecannetwork.dto.tech.TechMdttPkg;
import com.ecannetwork.tech.controller.bean.RestResponse;
import com.ecannetwork.tech.util.ImportExcel;
import com.ecannetwork.tech.util.TechConsts;

/**
 * @author 李伟
 * 2015-8-12下午4:31:38
 */
@Controller
@RequestMapping("exam")
public class ExamController {
	@Autowired
	private CommonService commonService;
	/**
	 *查询题库列表
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public String index(Model model){
		List<TechExamMain> tem=null;
		                 tem = this.commonService.list("from TechExamMain t");
		model.addAttribute("list",tem);
		return "tech/exam/index";
	}
	@RequestMapping("addmain")
	public String addmain(Model model){
		
		return "tech/exam/viewmain";
	}
/**
 * 保存题库信息
 * @param model
 * @param title
 * @param type
 * @param timeFlag
 * @param singleScort
 * @param multiScort
 * @param showAnswer
 * @param flag
 * @param randomCount
 * @param passLevel
 * @param leftCount
 * @param id
 * @return
 */
	@RequestMapping("savemain")
	public @ResponseBody
	AjaxResponse savemain(Model model,
			@RequestParam("title") String  title,
			@RequestParam("type") Integer  type,
			@RequestParam("timeFlag") Integer  timeFlag,
			@RequestParam("singleScort") Integer  singleScort,
			@RequestParam("multiScort") Integer  multiScort,
			@RequestParam("showAnswer") Integer  showAnswer,
			@RequestParam("flag") Integer  flag,
			@RequestParam("randomCount") Integer  randomCount,
			@RequestParam("passLevel") Integer  passLevel,
			@RequestParam("leftCount") Integer  leftCount,
			@RequestParam(value = "id", required = false) String id
			){
		TechExamMain tem = null;
		if (StringUtils.isNotBlank(id))
		{
			tem=(TechExamMain) this.commonService.get(id, TechExamMain.class);
		}
		if (tem == null)
		{
			tem = new TechExamMain();
		}
		tem.setTitle(title);
		tem.setType(type);
		tem.setTimeFlag(timeFlag);
		tem.setSingleScort(singleScort);
		tem.setMultiScort(multiScort);
		tem.setShowAnswer(showAnswer);
		tem.setFlag(flag);
		tem.setRandomCount(randomCount);
		tem.setPassLevel(passLevel);
		tem.setLeftCount(leftCount);
		this.commonService.saveOrUpdateTX(tem);
		return new AjaxResponse();
	}
	/**
	 * 新增试题
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("addques")
	public String addques(Model model,
			@RequestParam(value = "id", required = false) String id
			){
		TechExamMain tem=(TechExamMain) this.commonService.get(id, TechExamMain.class);
		model.addAttribute("dto",tem);
		return "tech/exam/addques";
	}
	/**
	 * 添加试题，及试题答案
	 * @param model
	 * @param techExamQuestion
	 * @param result
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("saveques")
	public @ResponseBody
	AjaxResponse saveques(Model model, @Valid
			TechExamQuestion techExamQuestion, BindingResult result, HttpServletRequest request)
	{
		Map<String, String> titleMap = new HashMap<String, String>();
		Map<String, String> rightMap = new HashMap<String, String>();
		Enumeration<String> en = request.getParameterNames();
		if (!result.hasErrors())
		{
		while (en.hasMoreElements())
		{
			String name = en.nextElement();
			if (name.startsWith("t_"))
			{
				String[] titleName = name.split("t_");
				String value = request.getParameter(name);
				System.out.println("+++++++++++++++++++++++++name \r\t" + name + "\r\t"
				        + "value \r\t" + value);
				if (titleName.length > 1
				        && StringUtils.isNotBlank(value.trim()))
				{
					titleMap.put(titleName[1], value);
				}
			}
			if (name.startsWith("c_"))
			{
				String[] names = name.split("c_");
				System.out.println("++++++++++++++++++++++++++c_  \r\t" + names[1]);
				if (names.length > 1)
				{
					rightMap.put(names[1], "1");
				}

			}
		}
		
		List<TechExamAnswer> techExamAnswerList = judge(titleMap, rightMap,
				techExamQuestion.getId());
		// 检查是否有正确答案
					boolean hasRight = false;
					for (TechExamAnswer ans : techExamAnswerList)
					{
						if ("Y".equals(ans.getIsRight()))
						{
							hasRight = true;
							
							break;
						}
					}

					if (!hasRight)
					{
						return new AjaxResponse(false, I18N
						        .parse("i18n.testdb.msg.leastOneRightAns"));
					}
					if(techExamQuestion.getId()==""){
					     techExamQuestion.setId(null);
					}
					commonService.saveOrUpdateTX(techExamQuestion);
					for (TechExamAnswer ans : techExamAnswerList)
					{
						ans.setQuesId(techExamQuestion.getId());
					}
					commonService.deleteAllTX(commonService.list(
					        "from TechExamAnswer t where t.quesId=?", techExamQuestion.getId()));
					commonService.saveOrUpdateTX(techExamAnswerList);
		return new AjaxResponse(true, I18N.parse("i18n.commit.success"));
		} else
		{
			return new AjaxResponse(false, I18N.parse("i18n.commit.error"));
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TechExamAnswer> judge(Map<String, String> titlemap,
	        Map<String, String> rightMap, String testId)
	{
		String checkString = "";
		List<TechExamAnswer> techExamAnswerList = new ArrayList<TechExamAnswer>();
		TechExamAnswer answer = null;
		Integer idx = new Integer(0);
		char[] array= new char[]{'A','B','C','D','E','F','G','H','I','J','K','L','M','N'}; 
		for (Map.Entry<String, String> entry : titlemap.entrySet())
		{
			checkString = rightMap.get(entry.getKey());
			answer = new TechExamAnswer();
			if (checkString != null)
			{
				answer.setIsRight("Y");
			} else
			{
				answer.setIsRight("N");
			}
			answer.setId(null);
			answer.setIdx(String.valueOf(array[idx]));
			answer.setTitle(entry.getValue());
			techExamAnswerList.add(answer);
			idx++;
		}
		return techExamAnswerList;
	}
	/**
	 *编辑题库
	 *进入题库修改页面
	 *可选择进入题库试题编辑
	 */
	@RequestMapping("viewmain")
	public String viewmain(Model model,
			@RequestParam(value = "id", required = false) String id){
		TechExamMain tem=(TechExamMain) this.commonService.get(id, TechExamMain.class);
		model.addAttribute("dto",tem);
		return "tech/exam/viewmain";
	}
	/**
	 * 进入试题列表 可重新编辑试题及答案
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("editques")
	public String editques(Model model,
			@RequestParam(value = "id", required = false) String id){
		List<TechExamQuestion> teqList = null;
		teqList=this.commonService.list("from TechExamQuestion t where t.mainId=?",id);
		model.addAttribute("list", teqList);
		model.addAttribute("mainId",id);
		return "tech/exam/queslist";
	}
	/**
	 * 进入编辑试题
	 */
	@RequestMapping("viewques")
	public String viewques(Model model,
			@RequestParam(value = "id", required = false) String id
			){
		TechExamQuestion teq = (TechExamQuestion) this.commonService.get(id, TechExamQuestion.class);
		List<Object[]> temliST = (List<Object[]>)commonService.list("from TechExamAnswer t join t.examQuestion e where e.id=?",id);
			for(Object[] ob:temliST){
				TechExamQuestion cmain = (TechExamQuestion)ob[1];
				TechExamAnswer ans = (TechExamAnswer)ob[0];
				if(teq.getId().equals(cmain.getId())){
					teq.getTea().add(ans);
				}
			}
			model.addAttribute("dto",teq);
			return "tech/exam/viewques";
	}
	/**
	 * 删除试题库
	 */
	@RequestMapping("delmain")
	public @ResponseBody
	AjaxResponse delmain(Model model,
			@RequestParam("id") String  id
			){
		//commonService.deleteTX(TechExamMain.class, id);
		List<TechExamQuestion> techExamQuestionList = this.commonService
				.list("from TechExamQuestion t where t.mainId=?",
						id);
		for(TechExamQuestion teqs:techExamQuestionList){
			List<TechExamAnswer> techExamAnswerList=this.commonService
					.list("from TechExamAnswer t where t.quesId=?",
							teqs.getId());
			 if(techExamAnswerList !=null){
		        	this.commonService.deleteAllTX(techExamAnswerList);
		        }
		}
			if(techExamQuestionList !=null){
				this.commonService.deleteAllTX(techExamQuestionList);
			}
	   commonService.deleteTX(TechExamMain.class, id);
		return new AjaxResponse();
	}
	/**
	 * 删除试题
	 */
	@RequestMapping("delques")
	public @ResponseBody
	AjaxResponse delques(Model model,
			@RequestParam("id") String  id
			){
		List<TechExamAnswer> techExamAnswerList=this.commonService
				.list("from TechExamAnswer t where t.quesId=?",
						id);
		this.commonService.deleteAllTX(techExamAnswerList);
		this.commonService.deleteTX(TechExamQuestion.class, id);
		return new AjaxResponse();
	}
	/**
	 * 发布到资料库，进入发布页面
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("pushpkg")
	public String pushpkg(Model model,
			@RequestParam(value = "id", required = false) String id
			){
		TechExamMain tem = (TechExamMain) this.commonService.get(id, TechExamMain.class);
		model.addAttribute("dto",tem);
		return "tech/exam/pushpkg";
	}
	/**
	 * 保存资料库
	 * @param model
	 * @param id 为题库Id
	 * @param title 为题库标题
	 * @return
	 */
	@RequestMapping("push")
	public @ResponseBody
	AjaxResponse push(Model model,
			@RequestParam(value = "id", required = false) String id
			/*@RequestParam("title") String title,
			@RequestParam("openflag") String openflag,
			@RequestParam(value="proType",defaultValue="0") String proType*/
			){
		TechExamMain tem = (TechExamMain) this.commonService.get(id, TechExamMain.class);
		TechMdttPkg tmpkg = null;
		if (StringUtils.isNotBlank(id))
		{
			tmpkg=(TechMdttPkg) this.commonService.get(id, TechMdttPkg.class);
		}
		if (tmpkg == null)
		{
			tmpkg = new TechMdttPkg();
			tmpkg.setVersionCode(1);
		}
		String pkgId = UUID.randomUUID();
		String filePath = "/tech/upload/dmppkg/"+pkgId+".json";
		
		File file = beJson(id,pkgId+".json");
		
		long fileSize = file.length();
		String pkgSize = "";
		if(fileSize<1024){
			pkgSize = fileSize+"B";
		}else if(fileSize<1024*1024){
			pkgSize = (float)(Math.round(fileSize/1024.0*10))/10+"K";
		}else{
			pkgSize =  (float)(Math.round(fileSize/(1024.0*10*1024)))/10+"M";
		}
		
		tmpkg.setId(pkgId);//id
		//tmpkg.setName(title);
		tmpkg.setName(tem.getTitle());
		tmpkg.setVersion("1");
		tmpkg.setFilePath(filePath);
		tmpkg.setBrand("VW");
		//tmpkg.setProType(proType);
		tmpkg.setProType("");
		tmpkg.setLastUpdateTime(new Date());
		tmpkg.setStatus("1");
		tmpkg.setConentType("SSP");
		tmpkg.setValid("1");
		tmpkg.setPkgType("JSON");
		tmpkg.setVersionCode(tmpkg.getVersionCode()+1);
		tmpkg.setFileSize(pkgSize);
		/*tmpkg.setFixedName(title);*/
		tmpkg.setFixedName(tem.getTitle());
		tmpkg.setMotorcycle("");
		tmpkg.setosType("0");
		//tmpkg.setStatus(openflag);
		tmpkg.setStatus("0");
		tmpkg.setTrianPlanID(id);
				List<TechMdttPkg> pkgs = this.commonService
				.list("from TechMdttPkg t where t.trianPlanID=?",
						id);
        if(pkgs !=null){
        	this.commonService.deleteAllTX(pkgs);
        }
		this.commonService.saveTX(tmpkg);
		
		TechExamMain temp = null;
		if (StringUtils.isNotBlank(id))
		{
			temp=(TechExamMain) this.commonService.get(id, TechExamMain.class);
		}
		if (temp == null)
		{
			temp = new TechExamMain();
		}
		temp.setFlag(Integer.parseInt("0"));
		this.commonService.saveOrUpdateTX(temp);
		
		return new AjaxResponse();
	}
	
	@RequestMapping(value = "importExamPage", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse importExamPage(Model mode,
			HttpServletRequest request, HttpServletResponse resp)
	{
		
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartHttpServletRequest
				.getFile("excelExam");
		
		String filePathString = File.separator + TechConsts.SCORM_FILE_PATH
		+ File.separator ;
		String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
		+ filePathString;
		
		try {
			File fTemp = SaveFileFromInputStream(multipartFile.getInputStream(),storeFileNameWithPath,multipartFile.getOriginalFilename());
			importExam(storeFileNameWithPath,multipartFile.getOriginalFilename());
			fTemp.delete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new AjaxResponse();
	}
	
	/**
	 * 试题导入
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("importPage")
	public String importPage(Model model){
		return "tech/exam/exportExam";
	}
	
	/**
	 * 导入excel试题
	 */
	@RequestMapping("importExam")
	public @ResponseBody
	AjaxResponse importExam(String filePath,String fileName){
		/*List<TechExamAnswer> techExamAnswerList=this.commonService
				.list("from TechExamAnswer t where t.quesId=?");*/
		ImportExcel importExcel = new ImportExcel();
		TechExamMain exam = importExcel.impExam(filePath+"/"+fileName);
		commonService.saveTX(exam);
		
		
		List<TechExamQuestion> questionList = exam.getQuestionList();
		for(TechExamQuestion quest:questionList){
			quest.setMainId(exam.getId());
			commonService.saveTX(quest);
			List<TechExamAnswer> techExamAnswerList = quest.getItems();
			
			for(TechExamAnswer answer:techExamAnswerList){
				answer.setQuesId(quest.getId());
				commonService.saveTX(answer);
			}
			
		}
		
		return new AjaxResponse();
	}
	
	/**
	 * 生成考试文件
	 * @param examId
	 * @return
	 */
	@RequestMapping("beJson")
	public File beJson(String examId,String fileName){
		
		TechExamMain exam = (TechExamMain) this.commonService.get(examId, TechExamMain.class);
		
		RestResponse resp =  RestResponse.success(null);
		
		TechExamMain main = new TechExamMain();
		
		List<TechExamQuestion> techExamQuestionList = this.commonService
				.list("from TechExamQuestion t where t.mainId=?",
						examId);
		for(TechExamQuestion teqs:techExamQuestionList){
			List<TechExamAnswer> techExamAnswerList=this.commonService
					.list("from TechExamAnswer t where t.quesId=?",
							teqs.getId());
			for(TechExamAnswer answer:techExamAnswerList){
				answer.setExamQuestion(null);
				answer.setQuesId(null);
				answer.setExts(null);
			}
			teqs.setExts(null);
			teqs.setTea(null);
			teqs.setItems(techExamAnswerList);
			main.getQuestionList().add(teqs);
		
		}
		
		resp.setProperty(exam);
		resp.setData(main.getQuestionList());
		String toJson = JsonFactory.toJson(resp);
		
		String filePathString = File.separator + TechConsts.CREATE_EXAM_JSON
		+ File.separator ;
		
		String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
		+ filePathString;
		
		return PrintStreamFile(toJson, storeFileNameWithPath+"/"+fileName);
		
	}
	
	public File SaveFileFromInputStream(InputStream stream,String path,String filename) throws IOException
    {      
		File f = new File(path + "/"+ filename);
		FileOutputStream fs = new FileOutputStream(f);
        byte[] buffer =new byte[1024*1024];
        int bytesum = 0;
        int byteread = 0; 
        while ((byteread=stream.read(buffer))!=-1)
        {
           bytesum+=byteread;
           fs.write(buffer,0,byteread);
           fs.flush();
        } 
        fs.close();
        stream.close();    
        return f;
    }   
	
	 public File PrintStreamFile(String content,String filePath){
		 File f = new File(filePath);
         try{
             FileOutputStream out=new FileOutputStream(f);
             PrintStream p = new PrintStream(out);
             p.println(content);
         } catch (FileNotFoundException e){
             e.printStackTrace();
         }
         return f;
     }
	
}
