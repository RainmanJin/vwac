package com.ecannetwork.tech.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Component;

import com.ecannetwork.core.util.AES;
import com.ecannetwork.core.util.Configs;
import com.ecannetwork.core.util.JsonFactory;
import com.ecannetwork.dto.core.EcanUser;

@Component
public class DmpInterfaceFacade
{
	/**
	 * 增加用户
	 * 
	 * @param user
	 * @return
	 */
	public boolean userAdded(EcanUser user)
	{
		if (Configs.getInt("dmp.enable") == 1)
		{
			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod(Configs.get("dmp.userAddUrl"));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Userid", user.getId());
			map.put("Realname", user.getName());
			map.put("Nickname", user.getNickName());
			map.put("Username", user.getLoginName());
			map.put("Email", user.getEmail());
			map.put("Action", "Adduser");

			String ARG = JsonFactory.toJson(map);
			try
			{
				String aesArgs = AES.Encrypt(ARG, Configs.get("dmp.aesKey"));
				post.addParameter("ARG", aesArgs);
				client.executeMethod(post);
				if (post.getStatusCode() == 200)
				{
					return true;
				}
			} catch (Exception e1)
			{
				e1.printStackTrace();
			} finally
			{
				post.releaseConnection();
			}

			return false;
		} else
		{
			return true;
		}
	}

	/**
	 * 编辑用户
	 * 
	 * @param user
	 * @return
	 */
	public boolean userEditted(EcanUser user)
	{
		if (Configs.getInt("dmp.enable") == 1)
		{
			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod(Configs.get("dmp.userEditUrl"));

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Userid", user.getId());
			map.put("Realname", user.getName());
			map.put("Nickname", user.getNickName());
			map.put("Username", user.getLoginName());
			map.put("Email", user.getEmail());
			map.put("Action", "Updateuser");

			String ARG = JsonFactory.toJson(map);
			try
			{
				String aesArgs = AES.Encrypt(ARG, Configs.get("dmp.aesKey"));
				post.addParameter("ARG", aesArgs);
				client.executeMethod(post);
				if (post.getStatusCode() == 200)
				{
					return true;
				}
			} catch (Exception e1)
			{
				e1.printStackTrace();
			} finally
			{
				post.releaseConnection();
			}

			return false;
		} else
		{
			return true;
		}
	}

	/**
	 * 删除用户
	 * 
	 * @param user
	 * @return
	 */
	public boolean userDelete(EcanUser user)
	{
		if (Configs.getInt("dmp.enable") == 1)
		{
			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod(Configs.get("dmp.userDelUrl"));

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Userid", user.getId());
			map.put("Username", user.getLoginName());
			map.put("Action", "Deleteuser");

			String ARG = JsonFactory.toJson(map);
			try
			{
				String aesArgs = AES.Encrypt(ARG, Configs.get("dmp.aesKey"));
				post.addParameter("ARG", aesArgs);
				client.executeMethod(post);
				if (post.getStatusCode() == 200)
				{
					return true;
				}
			} catch (Exception e1)
			{
				e1.printStackTrace();
			} finally
			{
				post.releaseConnection();
			}

			return false;
		} else
		{
			return true;
		}
	}
}
