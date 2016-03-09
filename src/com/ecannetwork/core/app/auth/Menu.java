package com.ecannetwork.core.app.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ecannetwork.core.i18n.I18N;
import com.ecannetwork.dto.core.EcanApp;

/**
 * 用户菜单节点， 通过菜单节点构建成树形菜单
 * 
 * @author leo
 * 
 */
public class Menu implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String appCode;
	private String appName;
	private String funcCode;
	private String funcName;
	private String levelCode;
	private Menu parent;
	private Integer idx;
	private String outurl;

	/**
	 * 当前菜单的级别
	 * 
	 * @return
	 */
	public Integer getLevel()
	{
		return this.levelCode.length() / EcanApp.LEVEL_CODE_LENGTH;
	}

	public Menu(String appCode, String appName, String funcCode,
			String funcName, String levelCode, Integer idx)
	{
		super();
		this.appCode = appCode;
		this.appName = appName;
		this.funcCode = funcCode;
		this.funcName = funcName;
		this.levelCode = levelCode;
		this.idx = idx;
	}
	public Menu(String appCode, String appName, String funcCode,
			String funcName, String levelCode, Integer idx,String outurl)
	{
		super();
		this.appCode = appCode;
		this.appName = appName;
		this.funcCode = funcCode;
		this.funcName = funcName;
		this.levelCode = levelCode;
		this.idx = idx;
		this.outurl=outurl;
	}
	/**
	 * 根节点
	 * 
	 * @return
	 */
	public Menu getRoot()
	{
		if (this.parent == null)
			return this;

		Menu p = this.parent;

		while (p.getParent() != null)
		{
			p = p.getParent();
		}

		return p;
	}

	private List<Menu> submenu = new ArrayList<Menu>();

	public String getLevelCode()
	{
		return levelCode;
	}

	public void setLevelCode(String levelCode)
	{
		this.levelCode = levelCode;
	}

	public Menu getParent()
	{
		return parent;
	}

	public void setParent(Menu parent)
	{
		this.parent = parent;
	}

	public String getAppCode()
	{
		return appCode;
	}

	public void setAppCode(String appCode)
	{
		this.appCode = appCode;
	}

	public String getAppName()
	{
		return I18N.parse(this.appName);
	}

	public void setAppName(String appName)
	{
		this.appName = appName;
	}

	public String getFuncCode()
	{
		return funcCode;
	}

	public void setFuncCode(String funcCode)
	{
		this.funcCode = funcCode;
	}

	public String getFuncName()
	{
		return funcName;
	}

	public void setFuncName(String funcName)
	{
		this.funcName = funcName;
	}

	/**
	 * 子菜单数量
	 * 
	 * @return
	 */
	public Integer getSubCount()
	{
		return this.submenu.size();
	}

	public List<Menu> getSubmenu()
	{
		return submenu;
	}

	public void setSubmenu(List<Menu> submenu)
	{
		this.submenu = submenu;
	}

	/**
	 * 获取URI:::如果是菜单，则获取第一个子应用的访问连接当作访问连接
	 * 
	 * @return
	 */
	public String getUri()
	{
		if (StringUtils.isBlank(this.funcCode))
		{
			return this.submenu.get(0).getUri();
		} else
		{
			return this.appCode + "/" + this.funcCode;
		}
	}

	public Integer getIdx()
	{
		return idx;
	}

	public void setIdx(Integer idx)
	{
		this.idx = idx;
	}
	public String getOuturl()
	{
		return outurl;
	}

	public void setOuturl(String outurl)
	{
		this.outurl = outurl;
	}
}
