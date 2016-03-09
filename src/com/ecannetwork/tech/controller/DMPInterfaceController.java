package com.ecannetwork.tech.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.AES;
import com.ecannetwork.core.util.Configs;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.CoreConsts.YORN;
import com.ecannetwork.core.util.FileSizeUtil;
import com.ecannetwork.core.util.JsonFactory;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.EcanUserToken;
import com.ecannetwork.dto.tech.TechMdttPkg;

@RequestMapping("/rest")
@Controller
public class DMPInterfaceController
{
	private static Log log = LogFactory.getLog(DMPInterfaceController.class);

	@Autowired
	private CommonService commonService;

	/**
	 * 根据token获取用户信息
	 * 
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "user", method = RequestMethod.GET)
	public @ResponseBody
	String user(@RequestParam("token") String token)
	{
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("token", token);

		EcanUserToken ut = (EcanUserToken) this.commonService.listOnlyObject(
				"from EcanUserToken t where t.token=?", token);
		if (ut != null)
		{
			if (ut.isValid())
			{
				EcanUser u = (EcanUser) this.commonService.get(ut.getId(),
						EcanUser.class);
				if (u != null)
				{
					ret.put("respCode", "0");
					ret.put("respDesc", "Success");
					ret.put("Userid", ut.getId());
					ret.put("Username", u.getLoginName());
				} else
				{
					ret.put("respCode", "401");
					ret.put("respDesc", "Request Token not valid");
				}
			} else
			{
				ret.put("respCode", "405");
				ret.put("respDesc", "Request Token has expired");
			}
		} else
		{
			ret.put("respCode", "404");
			ret.put("respDesc", "Request Token not found");
		}

		String ARG = JsonFactory.toJson(ret);
		String aesArgs = "";
		try
		{
			aesArgs = AES.Encrypt(ARG, Configs.get("dmp.aesKey"));
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		if (log.isInfoEnabled())
		{
			log.info("DMP token:" + token + "\tResponse:" + ARG);
		}

		return aesArgs;
	}

	@RequestMapping(value = "ipadPkgMaintain", method = RequestMethod.POST)
	public @ResponseBody
	String ipadPkgMaintain(HttpServletRequest request,
			@RequestParam("id") String id, @RequestParam("name") String name,
			@RequestParam("version") String version,
			@RequestParam("type") String type,
			@RequestParam(value = "file", required = false) String file,
			@RequestParam(value = "fileExt") String fileExt,
			@RequestParam("oper") String oper)
	{
		if (log.isInfoEnabled())
		{
			log.info("DMP pkg request:\tid" + id + "\tname:" + name
					+ "\tversion:" + version + "\ttype:" + type + "\tfile:"
					+ file + "\tfileExt:" + fileExt + "\toper:" + oper);
		}

		String filePath = null;

		if (oper.equalsIgnoreCase("A") && StringUtils.isBlank(file))
		{
			return response("404", "empty pkg file filed");
		}

		TechMdttPkg pkg = (TechMdttPkg) this.commonService.get(id,
				TechMdttPkg.class);

		if (pkg == null)
		{// 更新
			pkg = new TechMdttPkg();
			pkg.setStatus(YORN.NO);// 新的状态肯定是NO
			pkg.setId(id);
			pkg.setFixedName(name);
			//新文件
			pkg.setVersionCode(new Double(
					Double.valueOf(version).doubleValue() * 1000).intValue());
		} else
		{
			if (!pkg.getConentType().equals(type))
			{// 类型变更了，状态需要修改为NO
				pkg.setStatus(YORN.NO);
			}
		}

		pkg.setName(name);
		pkg.setVersion(version);
		
		
		if ("CL".equals(type))
		{
			pkg.setConentType(TechMdttPkg.CONTENT_TYPE.CLASS_ROOM);
		} else if ("SS".equals(type))
		{// SS
			pkg.setConentType(TechMdttPkg.CONTENT_TYPE.SSP);
		} else
		{
			return response("400", "unknown package content tag type");
		}

		if ("pdf".equalsIgnoreCase(fileExt))
		{
			pkg.setPkgType("PDF");
			pkg.setValid("1");
		} else if ("zip".equalsIgnoreCase(fileExt))
		{
			pkg.setPkgType("SCO");
			pkg.setValid("1");
		} 
		else if ("mp4".equalsIgnoreCase(fileExt))
		{
			pkg.setPkgType("MP4");
			pkg.setValid("1");
		}else
		{
			pkg.setValid("0");
			pkg.setStatus(YORN.NO);// 格式错误状态肯定是NO
		}

		if (pkg.getConentType().equals(TechMdttPkg.CONTENT_TYPE.CLASS_ROOM)
				&& pkg.getPkgType().equals("SCO"))
		{// 在线教学必须是SCO的ZIP格式
			pkg.setValid("0");
			pkg.setStatus("NO");
		}

		if (StringUtils.isNotBlank(file))
		{// 新增或修改文件， 文件需要重新下载
			String storePath = File.separator + "tech" + File.separator
					+ "upload" + File.separator + "dmppkg" + File.separator
					+ id + "." + fileExt;
			filePath = CoreConsts.Runtime.APP_ABSOLUTE_PATH + storePath;

			// 下载文件
			String ret = downloadFile(file, filePath);

			if (ret == null)
			{// 下载成功
				pkg.setFilePath(storePath + "?_t=" + System.currentTimeMillis());
				pkg.setFileSize(FileSizeUtil.getFormatFileSize(new File(
						filePath)));
			} else
			{// 下载文件失败
				return ret;
			}
		}

		pkg.setLastUpdateTime(new Date());
		this.commonService.saveOrUpdateTX(pkg);

		return response("0", "Succ");
	}

	private String response(String respCode, String respDesc)
	{

		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("respCode", respCode);
		ret.put("respDesc", respDesc);
		String ARG = JsonFactory.toJson(ret);

		// String aesArgs = "";
		// try
		// {
		// aesArgs = AES.Encrypt(ARG, Configs.get("dmp.aesKey"));
		// } catch (Exception e)
		// {
		// e.printStackTrace();
		// }
		if (log.isInfoEnabled())
		{
			log.info("DMP pkg response:" + ARG);
		}
		return ARG;
	}

	/**
	 * 下载文件
	 * 
	 * @param id
	 * @param file
	 * @param fileExt
	 * @param ret
	 * @param filePath
	 * @return
	 */
	private String downloadFile(String file, String filePath)
	{
		String fileURL = null;
		try
		{
			URL url = new URL(file);
			URI uri = new URI("http", null, url.getHost(), url.getPort(),
					url.getPath(), url.getQuery(), null);
			fileURL = uri.toASCIIString();

		} catch (Exception e)
		{
			log.error(e);
			e.printStackTrace();

			return response("400", "Download pkg file faild with error URL");
		}

		if (fileURL != null)
		{
			HttpClient client = new HttpClient();
			GetMethod get = new GetMethod(fileURL);
			try
			{
				client.executeMethod(get);
				if (get.getStatusCode() == 200)
				{
					File localFile = new File(filePath + ".tmp");
					BufferedOutputStream out = new BufferedOutputStream(
							new FileOutputStream(localFile));
					InputStream in = get.getResponseBodyAsStream();

					int bt = -1;
					while ((bt = in.read()) != -1)
					{
						out.write(bt);
					}
					out.flush();
					out.close();

					// 下载完成后重命名
					localFile.renameTo(new File(filePath));
					return null;// 成功
				} else
				{
					log.error("Download DMP pkg error status:"
							+ get.getStatusCode());

					return response("404", "Download pkg file faild");
				}

			} catch (Exception e)
			{
				e.printStackTrace();
				log.error(e);

				return response("404", "Download pkg file faild");
			} finally
			{
				get.releaseConnection();
			}
		} else
		{
			return response("400", "Unkown pkg download url");
		}
	}

	@RequestMapping(value = "ipadPkgDel", method = RequestMethod.POST)
	public @ResponseBody
	String ipadPkgMaintain(HttpServletRequest request,
			@RequestParam("id") String id, @RequestParam("oper") String oper)
	{
		if (log.isInfoEnabled())
		{
			log.info("DMP pkg delete:\tid" + id + "\toper:" + oper);
		}

		TechMdttPkg pkg = (TechMdttPkg) this.commonService.get(id,
				TechMdttPkg.class);

		Map<String, Object> ret = new HashMap<String, Object>();

		// 删除课件包
		if (pkg != null)
		{
			pkg.setStatus("D");// 删除
			this.commonService.updateTX(pkg);

			// 设置课件为删除状态
			String storePath = pkg.getFilePath();
			File file = new File(CoreConsts.Runtime.APP_ABSOLUTE_PATH
					+ storePath);

			if (file.exists())
			{
				file.delete();
			}
			ret.put("respCode", "0");
			ret.put("respDesc", "delete succ");
		} else
		{
			ret.put("respCode", "404");
			ret.put("respDesc", "package not found");
		}

		String ARG = JsonFactory.toJson(ret);

		// String aesArgs = "";
		// try
		// {
		// aesArgs = AES.Encrypt(ARG, Configs.get("dmp.aesKey"));
		// } catch (Exception e)
		// {
		// e.printStackTrace();
		// }

		if (log.isInfoEnabled())
		{
			log.info("DMP pkg:" + id + "\tResponse:" + ARG);
		}

		return ARG;
	}

	public static void main(String[] args) throws MalformedURLException,
			URISyntaxException
	{
		String file = "http://academy.vgc.com.cn/alfresco/d/d/workspace/SpacesStore/6df60176-c697-4b33-98f7-6a4236d600f8/GTO Content List_20121204.xls?ticket=TICKET_1b5ea171e012a365499b4d53dbaeeedf020986e1";

		String local = "/tmp/a.xls";
		new DMPInterfaceController().downloadFile(file, local);

	}
}
