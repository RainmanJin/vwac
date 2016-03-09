package com.ecannetwork.tech.controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ecannetwork.dto.core.EcanDomainvalueDTO;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.MwArticle;
import com.ecannetwork.dto.tech.TechResourseLog;
import com.ecannetwork.dto.tech.TechResourseManager;
import com.ecannetwork.dto.tech.TechTest;
import com.ecannetwork.dto.tech.TechTestDb;
import com.ecannetwork.tech.util.TechConsts;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.Configs;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.ExecuteContext;
import com.ecannetwork.core.util.FileUploadHelper;
import com.ecannetwork.core.util.UUID;

@Controller
@RequestMapping("/info")
public class InfoController {
	@Autowired
	private CommonService commonService;

	@SuppressWarnings("unchecked")
	@RequestMapping("index")
	public String GetNewsList(Model model) {
		List<MwArticle> list = commonService.list("from MwArticle t where typeId=?", "wxxw");
		if (list != null && list.size() > 0) {
			for (MwArticle artile : list) {
				List<EcanDomainvalueDTO> domainvalue = commonService.list(
						"from EcanDomainvalueDTO t where t.domainvalue = ?",
						artile.getTypeId());
				if (domainvalue != null && domainvalue.size() > 0) {
					artile.setTypeId(domainvalue.get(0).getDomainlabel());
				}
			}
		}
		model.addAttribute("articleList", list);
		return "tech/info/index";
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping("view")
	public String view(Model model,
			@RequestParam(value = "id", required = false) Integer id) {

		if (id != null) {
			MwArticle mwArticle = (MwArticle) commonService.get(id.toString(),
					MwArticle.class);
			model.addAttribute("mwarticle", mwArticle);
		}
		return "tech/info/infodetail";
	}

	@SuppressWarnings("unused")
	@RequestMapping("add")
	public String add(Model model) {
		return "tech/info/infodetail";
	}

	@RequestMapping("editres")
	public @ResponseBody AjaxResponse editResourse(
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "shareUsers", required = false) String shareUsers,
			@RequestParam(value = "sort", required = false) Integer sort,
			@RequestParam(value = "contents", required = false) String contents,
			@RequestParam("creatTime") String creatTime,
			MultipartHttpServletRequest request) {
		MwArticle manager = (MwArticle) commonService.get(id, MwArticle.class);

		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		//上传封面图
		MultipartFile multipartFile = multipartHttpServletRequest
				.getFile("filePath");
		String fileName = multipartFile.getOriginalFilename();
		if (fileName != null && !fileName.equals("")) {
			if (manager.getFilePath() == null || manager.getFilePath().equals("")) {
				AjaxResponse response = null;
				String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
						+ File.separator
						+ "uploads"
						+ File.separator
						+ "info"
						+ File.separator + fileName.split("\\.")[0];
				File file = new File(storeFileNameWithPath + "."
						+ fileName.split("\\.")[1]);
				if (!file.exists()) {
					response = FileUploadHelper.upload(request,
							storeFileNameWithPath, "filePath",
							Configs.getAsList("userHeadImgFileType"), true);
					manager.setFilePath(File.separator + "uploads"
							+ File.separator + "info" + File.separator
							+ fileName.split("\\.")[0] + "."
							+ fileName.split("\\.")[1].toLowerCase());
				}
			} else {
				fileName = fileName.split("\\.")[0]+"."
						+ fileName.split("\\.")[1].toLowerCase();
				String filepath = manager.getFilePath();
				String[] filepath1 = filepath.split("/");
				String[] filepath2 = filepath.split("\\\\");
				if (filepath1.length > 1) {
					if (!filepath1[filepath1.length - 1].equals(fileName)) {
						AjaxResponse response = null;
						String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
								+ File.separator
								+ "uploads"
								+ File.separator
								+ "info"
								+ File.separator
								+ fileName.split("\\.")[0];
						File file = new File(storeFileNameWithPath + "."
								+ fileName.split("\\.")[1]);
						if (!file.exists()) {
							response = FileUploadHelper.upload(request,
									storeFileNameWithPath, "filePath",
									Configs.getAsList("userHeadImgFileType"),
									true);
							manager.setFilePath(File.separator + "uploads"
									+ File.separator + "info" + File.separator
									+ fileName.split("\\.")[0] + "."
									+ fileName.split("\\.")[1].toLowerCase());
						}
					}
				} else {
					if (!filepath2[filepath2.length - 1].equals(fileName)) {
						AjaxResponse response = null;
						String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
								+ File.separator
								+ "uploads"
								+ File.separator
								+ "info"
								+ File.separator
								+ fileName.split("\\.")[0];
						File file = new File(storeFileNameWithPath + "."
								+ fileName.split("\\.")[1]);
						if (!file.exists()) {
							response = FileUploadHelper.upload(request,
									storeFileNameWithPath, "filePath",
									Configs.getAsList("userHeadImgFileType"),
									true);
							manager.setFilePath(File.separator + "uploads"
									+ File.separator + "info" + File.separator
									+ fileName.split("\\.")[0] + "."
									+ fileName.split("\\.")[1].toLowerCase());
						}
					}
				}
			}

		}
		
		//上传附件
		MultipartFile multipartFilefj = multipartHttpServletRequest
				.getFile("fj");
		String fileNamefj = multipartFilefj.getOriginalFilename();
		if (fileNamefj != null && !fileNamefj.equals("")) {
			if (manager.getFj() == null || manager.getFj().equals("")) {
				AjaxResponse response = null;
				String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
						+ File.separator
						+ "uploads"
						+ File.separator
						+ "fj"
						+ File.separator + fileNamefj.split("\\.")[0];
				File filefj = new File(storeFileNameWithPath + "."
						+ fileNamefj.split("\\.")[1]);
				if (!filefj.exists()) {
					response = FileUploadHelper.upload(request,
							storeFileNameWithPath, "fj",
							Configs.getAsList("courseAttachementFileType"), true);
					manager.setFj(File.separator + "uploads"
							+ File.separator + "fj" + File.separator
							+ fileNamefj.split("\\.")[0] + "."
							+ fileNamefj.split("\\.")[1].toLowerCase());
				}
			} else {
				fileNamefj = fileNamefj.split("\\.")[0]+"."
						+ fileNamefj.split("\\.")[1].toLowerCase();
				String filepath = manager.getFj();
				String[] filepath1 = filepath.split("/");
				String[] filepath2 = filepath.split("\\\\");
				if (filepath1.length > 1) {
					if (!filepath1[filepath1.length - 1].equals(fileNamefj)) {
						AjaxResponse response = null;
						String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
								+ File.separator
								+ "uploads"
								+ File.separator
								+ "fj"
								+ File.separator
								+ fileNamefj.split("\\.")[0];
						File file = new File(storeFileNameWithPath + "."
								+ fileNamefj.split("\\.")[1]);
						if (!file.exists()) {
							response = FileUploadHelper.upload(request,
									storeFileNameWithPath, "fj",
									Configs.getAsList("courseAttachementFileType"),
									true);
							manager.setFj(File.separator + "uploads"
									+ File.separator + "fj" + File.separator
									+ fileNamefj.split("\\.")[0] + "."
									+ fileNamefj.split("\\.")[1].toLowerCase());
						}
					}
				} else {
					if (!filepath2[filepath2.length - 1].equals(fileNamefj)) {
						AjaxResponse response = null;
						String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
								+ File.separator
								+ "uploads"
								+ File.separator
								+ "fj"
								+ File.separator
								+ fileNamefj.split("\\.")[0];
						File file = new File(storeFileNameWithPath + "."
								+ fileNamefj.split("\\.")[1]);
						if (!file.exists()) {
							response = FileUploadHelper.upload(request,
									storeFileNameWithPath, "fj",
									Configs.getAsList("courseAttachementFileType"),
									true);
							manager.setFj(File.separator + "uploads"
									+ File.separator + "fj" + File.separator
									+ fileNamefj.split("\\.")[0] + "."
									+ fileNamefj.split("\\.")[1].toLowerCase());
						}
					}
				}
			}

		}

		manager.setId(id);
		manager.setTitle(title);
		manager.setShareUsers(shareUsers);
		if (sort != null) {
			manager.setSort(sort);
		} else {
			manager.setSort(0);
		}

		manager.setContents(contents);
		manager.setUpdateTime(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(creatTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		manager.setCreateTime(date);
		commonService.updateTX(manager);

		return new AjaxResponse();
	}

	@RequestMapping("save")
	public @ResponseBody AjaxResponse addResourse(
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "shareUsers", required = false) String shareUsers,
			@RequestParam(value = "sort", required = false) Integer sort,
			@RequestParam("contents") String contents,
			@RequestParam("creatTime") String creatTime,
			MultipartHttpServletRequest request) {
		MwArticle manager = new MwArticle();

		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		
		//上传封面图
		MultipartFile multipartFile = multipartHttpServletRequest
				.getFile("filePath");
		String fileName = multipartFile.getOriginalFilename();
		if (fileName != null && !fileName.equals("")) {
			AjaxResponse response = null;
			String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
					+ File.separator + "uploads" + File.separator + "info"
					+ File.separator + fileName.split("\\.")[0];
			File file = new File(storeFileNameWithPath + "."
					+ fileName.split("\\.")[1]);
			if (!file.exists()) {
				response = FileUploadHelper.upload(request,
						storeFileNameWithPath, "filePath",
						Configs.getAsList("userHeadImgFileType"), true);
				manager.setFilePath(File.separator + "uploads" + File.separator
						+ "info" + File.separator + fileName.split("\\.")[0]
						+ "." + fileName.split("\\.")[1].toLowerCase());
			}
		}
		
		//上传附件
		MultipartFile multipartFilefj = multipartHttpServletRequest
				.getFile("fj");
		String fileNamefj = multipartFilefj.getOriginalFilename();
		if (fileNamefj!=null&&!fileNamefj.equals("")) {
			AjaxResponse response = null;
			String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
					+ File.separator + "uploads" + File.separator + "fj"
					+ File.separator + fileNamefj.split("\\.")[0];
			File file = new File(storeFileNameWithPath + "."
					+ fileNamefj.split("\\.")[1]);
			if (!file.exists()) {
				response = FileUploadHelper.upload(request,
						storeFileNameWithPath, "fj",
						Configs.getAsList("courseAttachementFileType"), true);
				manager.setFj(File.separator + "uploads" + File.separator
						+ "fj" + File.separator + fileNamefj.split("\\.")[0]
						+ "." + fileNamefj.split("\\.")[1].toLowerCase());
			}
		}

		manager.setTitle(title);
		manager.setTypeId("wxxw");
		manager.setShareUsers(shareUsers);
		if (sort != null) {
			manager.setSort(sort);
		} else {
			manager.setSort(0);
		}

		manager.setContents(contents);
		manager.setAudit(1);
		manager.setRecoveryState(0);
		manager.setUpdateTime(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(creatTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		manager.setCreateTime(date);
		commonService.saveOrUpdateTX(manager);

		return new AjaxResponse();
	}

	@RequestMapping("beforedelete")
	public String beforeDelete(@RequestParam(value = "id") String id,
			Model model) {
		MwArticle manager = (MwArticle) commonService.get(id, MwArticle.class);
		model.addAttribute("manager", manager);
		return "tech/info/delete";
	}

	@RequestMapping("delete")
	public @ResponseBody AjaxResponse delete(@RequestParam("id") String id,
			@RequestParam("title") String title) {
		MwArticle manager = (MwArticle) commonService.get(id, MwArticle.class);
		File file = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
				+ manager.getFilePath());
		if (file.exists()) {
			file.delete();
		}
		File filefj=new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH+manager.getFj());
		if (filefj.exists()) {
			filefj.delete();
		}
		commonService.deleteTX(MwArticle.class, id);
		return new AjaxResponse();
	}
}
