package com.ecannetwork.tech.controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.Configs;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.FileUploadHelper;
import com.ecannetwork.core.util.UUID;
import com.ecannetwork.dto.core.EcanDomainvalueDTO;
import com.ecannetwork.dto.tech.MwAppPlatForm;
import com.ecannetwork.dto.tech.MwArticle;

@Controller
@RequestMapping("/app")
public class AppController {
	@Autowired
	private CommonService commonService;

	@SuppressWarnings("unchecked")
	@RequestMapping("index")
	public String GetNewsList(Model model) {
		List<MwAppPlatForm> list = commonService.list("from MwAppPlatForm t",
				null);
		model.addAttribute("appplat", list);
		return "tech/app/index";
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping("view")
	public String view(Model model,
			@RequestParam(value = "id", required = false) Integer id) {

		if (id != null) {
			MwAppPlatForm appPlatForm = (MwAppPlatForm) commonService.get(
					id.toString(), MwAppPlatForm.class);
			model.addAttribute("appPlatForm", appPlatForm);
		}
		return "tech/app/view";
	}

	@RequestMapping("editres")
	public @ResponseBody AjaxResponse editResourse(
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "pkgid", required = false) String pkgid,
			@RequestParam(value = "version", required = false) String version,
			@RequestParam(value = "remark", required = false) String remark,
			@RequestParam(value = "brand", required = false) String brand,
			@RequestParam(value = "proType", required = false) String proType,
			@RequestParam(value = "status", required = false) String status,
			MultipartHttpServletRequest request) {
		MwAppPlatForm manager = (MwAppPlatForm) commonService.get(id,
				MwAppPlatForm.class);

		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;

		// 上传路径不存在则创建
		File dirFile = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
				+ File.separator + "uploads");
		if (!dirFile.exists()) {
			dirFile.mkdir();
			dirFile = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
					+ File.separator + "uploads" + File.separator + "apkimg");
			dirFile.mkdir();

			dirFile = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
					+ File.separator + "uploads" + File.separator + "WBT");
			dirFile.mkdir();
		} else {
			dirFile = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
					+ File.separator + "uploads" + File.separator + "apkimg");
			if (!dirFile.exists()) {
				dirFile.mkdir();
			}

			dirFile = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
					+ File.separator + "uploads" + File.separator + "WBT");
			if (!dirFile.exists()) {
				dirFile.mkdir();
			}
		}

		// 上传封面图
		MultipartFile multipartFileIcon = multipartHttpServletRequest
				.getFile("iconUrl");
		String fileNameIcon = multipartFileIcon.getOriginalFilename();
		if (fileNameIcon != null && !fileNameIcon.equals("")) {
			if (manager.getIconUrl() == null || manager.getIconUrl().equals("")) {
				AjaxResponse response = null;
				String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
						+ File.separator
						+ "uploads"
						+ File.separator
						+ "apkimg"
						+ File.separator
						+ fileNameIcon.split("\\.")[0];
				File file = new File(storeFileNameWithPath + "."
						+ fileNameIcon.split("\\.")[1]);
				if (!file.exists()) {
					response = FileUploadHelper.upload(request,
							storeFileNameWithPath, "iconUrl",
							Configs.getAsList("userHeadImgFileType"), true);
					manager.setIconUrl(File.separator + "uploads"
							+ File.separator + "apkimg" + File.separator
							+ fileNameIcon.split("\\.")[0] + "."
							+ fileNameIcon.split("\\.")[1].toLowerCase());
				}
			} else {
				fileNameIcon = fileNameIcon.split("\\.")[0] + "."
						+ fileNameIcon.split("\\.")[1].toLowerCase();
				String filepath = manager.getIconUrl();
				String[] filepath1 = filepath.split("/");
				String[] filepath2 = filepath.split("\\\\");
				if (filepath1.length > 1) {
					if (!filepath1[filepath1.length - 1].equals(fileNameIcon)) {
						AjaxResponse response = null;
						String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
								+ File.separator
								+ "uploads"
								+ File.separator
								+ "apkimg"
								+ File.separator
								+ fileNameIcon.split("\\.")[0];
						File file = new File(storeFileNameWithPath + "."
								+ fileNameIcon.split("\\.")[1]);
						if (!file.exists()) {
							response = FileUploadHelper.upload(request,
									storeFileNameWithPath, "iconUrl",
									Configs.getAsList("userHeadImgFileType"),
									true);
							manager.setIconUrl(File.separator
									+ "uploads"
									+ File.separator
									+ "apkimg"
									+ File.separator
									+ fileNameIcon.split("\\.")[0]
									+ "."
									+ fileNameIcon.split("\\.")[1]
											.toLowerCase());
						}
					}
				} else {
					if (!filepath2[filepath2.length - 1].equals(fileNameIcon)) {
						AjaxResponse response = null;
						String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
								+ File.separator
								+ "uploads"
								+ File.separator
								+ "apkimg"
								+ File.separator
								+ fileNameIcon.split("\\.")[0];
						File file = new File(storeFileNameWithPath + "."
								+ fileNameIcon.split("\\.")[1]);
						if (!file.exists()) {
							response = FileUploadHelper.upload(request,
									storeFileNameWithPath, "iconUrl",
									Configs.getAsList("userHeadImgFileType"),
									true);
							manager.setIconUrl(File.separator
									+ "uploads"
									+ File.separator
									+ "apkimg"
									+ File.separator
									+ fileNameIcon.split("\\.")[0]
									+ "."
									+ fileNameIcon.split("\\.")[1]
											.toLowerCase());
						}
					}
				}
			}

		}

		// 上传安卓课件包
		MultipartFile multipartFileApk = multipartHttpServletRequest
				.getFile("apkurl");
		String fileNameApk = multipartFileApk.getOriginalFilename();
		if (fileNameApk != null && !fileNameApk.equals("")) {
			if (manager.getApkurl() == null || manager.getApkurl().equals("")) {
				AjaxResponse response = null;
				String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
						+ File.separator
						+ "uploads"
						+ File.separator
						+ "WBT"
						+ File.separator + fileNameApk.split("\\.")[0];
				File file = new File(storeFileNameWithPath + "."
						+ fileNameApk.split("\\.")[1]);
				if (!file.exists()) {
					response = FileUploadHelper.upload(request,
							storeFileNameWithPath, "apkurl",
							Configs.getAsList("courseAttachementFileType"),
							true);
					manager.setApkurl(File.separator + "uploads"
							+ File.separator + "WBT" + File.separator
							+ fileNameApk.split("\\.")[0] + "."
							+ fileNameApk.split("\\.")[1].toLowerCase());
				}
			} else {
				fileNameApk = fileNameApk.split("\\.")[0] + "."
						+ fileNameApk.split("\\.")[1].toLowerCase();
				String filepath = manager.getApkurl();
				String[] filepath1 = filepath.split("/");
				String[] filepath2 = filepath.split("\\\\");
				if (filepath1.length > 1) {
					if (!filepath1[filepath1.length - 1].equals(fileNameApk)) {
						AjaxResponse response = null;
						String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
								+ File.separator
								+ "uploads"
								+ File.separator
								+ "WBT"
								+ File.separator
								+ fileNameApk.split("\\.")[0];
						File file = new File(storeFileNameWithPath + "."
								+ fileNameApk.split("\\.")[1]);
						if (!file.exists()) {
							response = FileUploadHelper
									.upload(request,
											storeFileNameWithPath,
											"apkurl",
											Configs.getAsList("courseAttachementFileType"),
											true);
							manager.setApkurl(File.separator + "uploads"
									+ File.separator + "WBT" + File.separator
									+ fileNameApk.split("\\.")[0] + "."
									+ fileNameApk.split("\\.")[1].toLowerCase());
						}
					}
				} else {
					if (!filepath2[filepath2.length - 1].equals(fileNameIcon)) {
						AjaxResponse response = null;
						String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
								+ File.separator
								+ "uploads"
								+ File.separator
								+ "WBT"
								+ File.separator
								+ fileNameApk.split("\\.")[0];
						File file = new File(storeFileNameWithPath + "."
								+ fileNameApk.split("\\.")[1]);
						if (!file.exists()) {
							response = FileUploadHelper
									.upload(request,
											storeFileNameWithPath,
											"apkurl",
											Configs.getAsList("courseAttachementFileType"),
											true);
							manager.setApkurl(File.separator + "uploads"
									+ File.separator + "WBT" + File.separator
									+ fileNameApk.split("\\.")[0] + "."
									+ fileNameApk.split("\\.")[1].toLowerCase());
						}
					}
				}
			}

		}

		// 上传IOS课件包
		MultipartFile multipartFileIOS = multipartHttpServletRequest
				.getFile("iosurl");
		String fileNameIOS = multipartFileIOS.getOriginalFilename();
		if (fileNameIOS != null && !fileNameIOS.equals("")) {
			if (manager.getIosurl() == null || manager.getIosurl().equals("")) {
				AjaxResponse response = null;
				String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
						+ File.separator
						+ "uploads"
						+ File.separator
						+ "WBT"
						+ File.separator + fileNameIOS.split("\\.")[0];
				File file = new File(storeFileNameWithPath + "."
						+ fileNameIOS.split("\\.")[1]);
				if (!file.exists()) {
					response = FileUploadHelper.upload(request,
							storeFileNameWithPath, "iosurl",
							Configs.getAsList("courseAttachementFileType"),
							true);
					manager.setIosurl(File.separator + "uploads"
							+ File.separator + "iosurl" + File.separator
							+ fileNameIOS.split("\\.")[0] + "."
							+ fileNameIOS.split("\\.")[1].toLowerCase());
				}
			} else {
				fileNameIOS = fileNameIOS.split("\\.")[0] + "."
						+ fileNameIOS.split("\\.")[1].toLowerCase();
				String filepath = manager.getIosurl();
				String[] filepath1 = filepath.split("/");
				String[] filepath2 = filepath.split("\\\\");
				if (filepath1.length > 1) {
					if (!filepath1[filepath1.length - 1].equals(fileNameIOS)) {
						AjaxResponse response = null;
						String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
								+ File.separator
								+ "uploads"
								+ File.separator
								+ "WBT"
								+ File.separator
								+ fileNameIOS.split("\\.")[0];
						File file = new File(storeFileNameWithPath + "."
								+ fileNameIOS.split("\\.")[1]);
						if (!file.exists()) {
							response = FileUploadHelper
									.upload(request,
											storeFileNameWithPath,
											"iosurl",
											Configs.getAsList("courseAttachementFileType"),
											true);
							manager.setIosurl(File.separator + "uploads"
									+ File.separator + "WBT" + File.separator
									+ fileNameIOS.split("\\.")[0] + "."
									+ fileNameIOS.split("\\.")[1].toLowerCase());
						}
					}
				} else {
					if (!filepath2[filepath2.length - 1].equals(fileNameIcon)) {
						AjaxResponse response = null;
						String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
								+ File.separator
								+ "uploads"
								+ File.separator
								+ "WBT"
								+ File.separator
								+ fileNameIOS.split("\\.")[0];
						File file = new File(storeFileNameWithPath + "."
								+ fileNameIOS.split("\\.")[1]);
						if (!file.exists()) {
							response = FileUploadHelper
									.upload(request,
											storeFileNameWithPath,
											"iosurl",
											Configs.getAsList("courseAttachementFileType"),
											true);
							manager.setIosurl(File.separator + "uploads"
									+ File.separator + "WBT" + File.separator
									+ fileNameIOS.split("\\.")[0] + "."
									+ fileNameIOS.split("\\.")[1].toLowerCase());
						}
					}
				}
			}

		}

		manager.setId(id);
		manager.setName(name);
		manager.setPkgid(pkgid);
		manager.setVersion(version);
		manager.setRemark(remark);
		manager.setBrand(brand);
		manager.setProType(proType);
		manager.setStatus(status);
		manager.setLastUpdateTime(new Date());
		commonService.updateTX(manager);

		return new AjaxResponse();
	}

	@RequestMapping("save")
	public @ResponseBody AjaxResponse addResourse(
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "pkgid", required = false) String pkgid,
			@RequestParam(value = "version", required = false) String version,
			@RequestParam(value = "remark", required = false) String remark,
			@RequestParam(value = "brand", required = false) String brand,
			@RequestParam(value = "proType", required = false) String proType,
			@RequestParam(value = "status", required = false) String status,
			MultipartHttpServletRequest request) {
		MwAppPlatForm manager = new MwAppPlatForm();

		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;

		// 上传路径不存在则创建
		File dirFile = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
				+ File.separator + "uploads");
		if (!dirFile.exists()) {
			dirFile.mkdir();
			dirFile = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
					+ File.separator + "uploads" + File.separator + "apkimg");
			dirFile.mkdir();

			dirFile = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
					+ File.separator + "uploads" + File.separator + "WBT");
			dirFile.mkdir();
		} else {
			dirFile = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
					+ File.separator + "uploads" + File.separator + "apkimg");
			if (!dirFile.exists()) {
				dirFile.mkdir();
			}

			dirFile = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
					+ File.separator + "uploads" + File.separator + "WBT");
			if (!dirFile.exists()) {
				dirFile.mkdir();
			}
		}

		// 上传封面图
		MultipartFile multipartFileIcon = multipartHttpServletRequest
				.getFile("iconUrl");
		String fileNameIcon = multipartFileIcon.getOriginalFilename();
		if (fileNameIcon != null & !fileNameIcon.equals("")) {
			AjaxResponse response = null;
			String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
					+ File.separator + "uploads" + File.separator + "apkimg"
					+ File.separator + fileNameIcon.split("\\.")[0];
			File file = new File(storeFileNameWithPath + "."
					+ fileNameIcon.split("\\.")[1]);
			if (!file.exists()) {
				response = FileUploadHelper.upload(request,
						storeFileNameWithPath, "iconUrl",
						Configs.getAsList("userHeadImgFileType"), true);
				manager.setIconUrl(File.separator + "uploads" + File.separator
						+ "apkimg" + File.separator
						+ fileNameIcon.split("\\.")[0] + "."
						+ fileNameIcon.split("\\.")[1].toLowerCase());
			}
		}

		// 上传安卓课件包
		MultipartFile multipartFileApk = multipartHttpServletRequest
				.getFile("apkurl");
		String fileNameApk = multipartFileApk.getOriginalFilename();
		if (fileNameApk != null && !fileNameApk.equals("")) {
			AjaxResponse response = null;
			String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
					+ File.separator + "uploads" + File.separator + "WBT"
					+ File.separator + fileNameApk.split("\\.")[0];
			File file = new File(storeFileNameWithPath + "."
					+ fileNameApk.split("\\.")[1]);
			if (!file.exists()) {
				response = FileUploadHelper.upload(request,
						storeFileNameWithPath, "apkurl",
						Configs.getAsList("courseAttachementFileType"), true);
				manager.setApkurl(File.separator + "uploads" + File.separator
						+ "WBT" + File.separator + fileNameApk.split("\\.")[0]
						+ "." + fileNameApk.split("\\.")[1].toLowerCase());
			}
		}
		// 上传IOS课件包
		MultipartFile multipartFileIOS = multipartHttpServletRequest
				.getFile("iosurl");
		String fileNameIOS = multipartFileIOS.getOriginalFilename();
		if (fileNameIOS != null && !fileNameIOS.equals("")) {
			AjaxResponse response = null;
			String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
					+ File.separator + "uploads" + File.separator + "WBT"
					+ File.separator + fileNameIOS.split("\\.")[0];
			File file = new File(storeFileNameWithPath + "."
					+ fileNameIOS.split("\\.")[1]);
			if (!file.exists()) {
				response = FileUploadHelper.upload(request,
						storeFileNameWithPath, "iosurl",
						Configs.getAsList("courseAttachementFileType"), true);
				manager.setIosurl(File.separator + "uploads" + File.separator
						+ "WBT" + File.separator + fileNameIOS.split("\\.")[0]
						+ "." + fileNameIOS.split("\\.")[1].toLowerCase());
			}
		}

		manager.setName(name);
		manager.setPkgid(pkgid);
		manager.setVersion(version);
		manager.setRemark(remark);
		manager.setBrand(brand);
		manager.setProType(proType);
		manager.setStatus(status);
		manager.setVersionCode(0);
		manager.setLastUpdateTime(new Date());
		commonService.saveOrUpdateTX(manager);

		return new AjaxResponse();
	}

	@RequestMapping("del")
	public @ResponseBody AjaxResponse delete(@RequestParam("id") String id) {
		MwAppPlatForm manager = (MwAppPlatForm) commonService.get(id,
				MwAppPlatForm.class);
		File file = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
				+ manager.getIconUrl());
		if (file.exists()) {
			file.delete();
		}
		File fileapk = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
				+ manager.getApkurl());
		if (fileapk.exists()) {
			fileapk.delete();
		}
		File fileIos = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
				+ manager.getIosurl());
		if (fileIos.exists()) {
			fileIos.delete();
		}
		commonService.deleteTX(MwAppPlatForm.class, id);
		return new AjaxResponse();
	}
}
