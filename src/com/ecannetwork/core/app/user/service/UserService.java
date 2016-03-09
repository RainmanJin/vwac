package com.ecannetwork.core.app.user.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecannetwork.core.app.user.dao.UserDAO;
import com.ecannetwork.core.i18n.I18N;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.db.dao.CommonDAO;
import com.ecannetwork.core.module.service.ServiceSupport;
import com.ecannetwork.core.util.Configs;
import com.ecannetwork.dto.core.EcanRole;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.tech.service.DmpInterfaceFacade;

@Service
public class UserService extends ServiceSupport
{
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private CommonDAO commonDAO;

	@Autowired
	private DmpInterfaceFacade dmpInterfaceFacade;

	/**
	 * 列举某个状态的所有用户
	 * 
	 * @param status
	 * @return
	 */
	public List<EcanUser> queryByStatUsers(String status)
	{
		String hql = "from EcanUser t where t.status = ?";
		return userDAO.list(hql, status);
	}

	/**
	 * 根据用户名查询用户信息:::用户对象中已经包含了角色信息
	 * 
	 * @param username
	 * @return
	 */
	public EcanUser getByUserName(String username)
	{
		String hql = "from com.ecannetwork.dto.core.EcanUser t where t.loginName = ?";
		List<EcanUser> users = this.userDAO.list(hql, username);
		if (users.size() > 0)
		{
			EcanUser user = users.get(0);

			EcanRole role = (EcanRole) commonDAO.get(user.getRoleId(),
					EcanRole.class);

			user.setRole(role);

			return user;
		}
		return null;
	}

	public EcanUser getByLoginName(String username)
	{
		String hql = "from com.ecannetwork.dto.core.EcanUser t where t.loginName = ?";
		List<EcanUser> users = this.userDAO.list(hql, username);
		if (users.size() > 0)
		{
			return users.get(0);
		}
		return null;
	}

	/**
	 * 按公司所属和角色列举用户
	 * 
	 * @param company
	 * @param role
	 * @return
	 */
	public List<EcanUser> listByCompanyAndRole(String company, String role)
	{
		String hql = "from com.ecannetwork.dto.core.EcanUser t where t.company = ? and t.roleId=? and t.status='1'";

		return this.userDAO.list(hql, company, role);
	}

	/**
	 * 按专业类别和角色列举用户
	 * 
	 * @param proType
	 *            专业类别
	 * @param role
	 *            角色
	 * @return
	 */
	public List<EcanUser> listByProType(String proType, String role)
	{
		// DELETE PROTYPE
		proType = null;

		String hql = null;
		if (StringUtils.isBlank(proType))
		{
			hql = "from com.ecannetwork.dto.core.EcanUser t where t.roleId=? and t.status='1'";
			return this.userDAO.list(hql, role);
		} else
		{
			hql = "from com.ecannetwork.dto.core.EcanUser t where t.proType = ? and t.roleId=? and t.status='1'";
			return this.userDAO.list(hql, proType, role);
		}
	}

	/**
	 * 删除用户：：：已激活的用户均不能删除
	 * 
	 * @param id
	 * @return
	 */
	public void deleteUserTX(EcanUser user)
	{
		this.userDAO.delete(user);
	}

	/**
	 * 停用用户
	 * 
	 * @param id
	 * @return
	 */
	public AjaxResponse disableUserTX(String id)
	{
		EcanUser user = this.userDAO.get(id);
		user.setStatus(EcanUser.STATUS.SUSPENDED);
		this.userDAO.update(user);
		return new AjaxResponse(true);
	}

	/**
	 * 启用用户
	 * 
	 * @param id
	 * @return
	 */
	public AjaxResponse activeUserTX(String id)
	{
		EcanUser user = this.userDAO.get(id);
		user.setStatus(EcanUser.STATUS.ACTIVE);
		this.userDAO.update(user);
		return new AjaxResponse(true);
	}

	/**
	 * 批量添加用户
	 * 
	 * @param prefix
	 * @param company
	 * @param proType
	 * @param role
	 * @param status
	 * @param password
	 * @param count
	 */
	public Integer batchUserTX(final String prefix, String company,
			String proType, String role, String status, String password,
			Integer count)
	{
		List<EcanUser> list = this.userDAO.list(
				"from EcanUser t where t.loginName like ?", prefix + "%");
		int first = 1000;

		if (list.size() > 0)
		{// 已经有该前缀的用户了，查找出最大的后缀
			Collections.sort(list, new Comparator<EcanUser>()
			{
				public int compare(EcanUser paramT1, EcanUser paramT2)
				{
					String t1 = paramT1.getLoginName().substring(
							prefix.length());
					String t2 = paramT2.getLoginName().substring(
							prefix.length());
					Integer i1 = NumberUtils.toInt(t1, 0);
					Integer i2 = NumberUtils.toInt(t2, 0);
					return i1.compareTo(i2);
				}
			});
			first = NumberUtils.toInt(list.get(list.size() - 1).getLoginName()
					.substring(prefix.length()), first);
		}
		int re = first + 1;
		for (int i = 0; i < count; i++)
		{
			EcanUser user = new EcanUser();
			user.setLoginName(prefix + ++first);
			user.setCompany(company);
			// DELETE PROTYPE
			// user.setProType(proType);
			user.setRoleId(role);
			user.setStatus(status);
			if (StringUtils.isBlank(password))
			{
				user.setLoginPasswd(randomPassword());
			} else
			{
				user.setLoginPasswd(password);
			}
			this.userDAO.save(user);

			if (Configs.getInt("dmp.enable") == 1)
			{
				user.setEmail("");
				user.setName("");
				user.setNickName("");
				dmpInterfaceFacade.userAdded(user);
			}
		}
		return re;
	}

	/**
	 * 随机密码
	 * 
	 * @return
	 */
	private String randomPassword()
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 6; i++)
		{
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}

	private Random random = new Random();

	/**
	 * 列举前缀用户
	 * 
	 * @param prefix
	 * @return
	 */
	public List<EcanUser> listByLoginNamePrefix(String prefix)
	{
		return this.userDAO.list("from EcanUser t where t.loginName like ?",
				prefix + "%");
	}

	/**
	 * 按照角色获取激活的用户
	 * 
	 * @param role
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List listByRole(String role)
	{
		return this.userDAO.list(
				"from EcanUser t where t.roleId=? and t.status=?", role,
				EcanUser.STATUS.ACTIVE);
	}

	public EcanUser getByEmail(String email)
	{
		List<EcanUser> list = this.commonDAO.list(
				"from EcanUser t where t.email = ?", email);
		if (list.size() > 0)
		{
			return list.get(0);
		}
		return null;
	}
}
