package com.ecannetwork.core.tld.domain;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ecannetwork.dto.core.EcanDomainvalueDTO;

public class TreeDomainTag extends IDomainTag
{
	private String type;
	private String rootid;
	private int step;
	private int levelSplit;
	private String[] values = null;

	public String formartResult()
	{
		Map dm = (Map) this.pageContext.getServletContext().getAttribute(
				"BMSDOMAIN");

		if (!StringUtils.isBlank((String) getValue()))
		{
			this.values = ((String) getValue()).split("\\|");
		}

		List dovs = (List) dm.get(getDomain());

		if (dovs == null)
		{
			return "";
		}
		StringBuffer sb = new StringBuffer();

		sb.append("<style type=\"text/css\">");
		sb.append(".domainTree_c{");
		sb.append("border:0; padding:0; margin:0;");
		sb.append("}");
		sb.append(".domainTree_c *{");
		sb.append("border:0; padding:0; margin:0;");
		sb.append("}");
		sb.append(".domainTree_c b{");
		sb.append("font-size:14px; color:blue;");
		sb.append("}");
		sb.append("</style>");
		sb.append("<script type=\"text/javascript\">");
		sb.append("function domainTreeSH_(id)");
		sb.append("{");
		sb.append("var node = document.getElementById(id);");
		sb.append("node.style.display = node.style.display=='none'?'block':'none';");
		sb.append("}");
		sb.append("</script>");

		sb.append("<div class=\"domainTree_c\">");
		sb.append(rendarTree(this.rootid, dovs, 0));
		sb.append("</div>");

		return sb.toString();
	}

	private String rendarTree(String start, List source, int step)
	{
		if (step == this.step)
		{
			return null;
		}
		StringBuffer sb = new StringBuffer();
		// TODO tree tag
		List list = null;// filterList(start, source, this.levelSplit);

		if (list.size() == 0)
		{
			return null;
		}

		for (Iterator it = list.iterator(); it.hasNext();)
		{
			EcanDomainvalueDTO dv = (EcanDomainvalueDTO) it.next();
			String str = rendarTree(dv.getDomainLevel(), source, step + 1);
			sb.append("<div  style=\"padding-left:" + step * 20 + ";\">");
			if (str == null)
			{
				sb.append("<div onMouseOver=\"this.style.backgroundColor='#fdecae';\" onMouseOut=\"this.style.backgroundColor='#fff';\">");
				sb.append("<b>&nbsp;</b><input type=\"" + this.type
						+ "\" name=\"" + getName() + "\" value=\""
						+ dv.getDomainvalue() + "\" "
						+ (checked(dv.getDomainvalue()) ? "checked" : "")
						+ " />" + dv.getDomainlabel());
				sb.append("</div>");
			} else
			{
				sb.append("<div onClick=\"domainTreeSH_('d_"
						+ dv.getId()
						+ "')\" onMouseOver=\"this.style.backgroundColor='#fdecae';\" onMouseOut=\"this.style.backgroundColor='#fff';\">");
				sb.append("<b>+</b><input type=\"" + this.type + "\" name=\""
						+ getName() + "\"  value=\"" + dv.getDomainvalue()
						+ "\" "
						+ (checked(dv.getDomainvalue()) ? "checked" : "")
						+ " />" + dv.getDomainlabel());
				sb.append("</div>");

				sb.append("<div id = \"d_" + dv.getId()
						+ "\" style=\"display:none;\">");
				sb.append(str);
				sb.append("</div>");
			}
			sb.append("</div>");
		}

		return sb.toString();
	}

	private boolean checked(String v)
	{
		if (this.values == null)
			return false;
		for (int i = 0; i < this.values.length; i++)
		{
			if ((v != null) && (v.equals(this.values[i])))
			{
				return true;
			}
		}

		return false;
	}

	public String getType()
	{
		return this.type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getRootid()
	{
		return this.rootid;
	}

	public void setRootid(String rootid)
	{
		if (!StringUtils.isBlank(rootid))
		{
			this.rootid = rootid;
			this.levelSplit = rootid.length();
		}
	}

	public int getStep()
	{
		return this.step;
	}

	public void setStep(int step)
	{
		this.step = step;
	}

	public int getLevelSplit()
	{
		return this.levelSplit;
	}

	public void setLevelSplit(int levelSplit)
	{
		this.levelSplit = levelSplit;
	}
}