package com.ecannetwork.core.tld;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * 做授权的按钮
 * 
 * @author leo
 * 
 */
public class DateFormartTLD extends IBaseTag {
	private static final long serialVersionUID = 1L;

	public int doEndTag() throws JspException {
		String result = formartResult();
		if (result != null) {
			JspWriter out = this.pageContext.getOut();
			try {
				out.print(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * 生成按钮
	 * 
	 * @return
	 */
	private String formartResult() {
		if (this.value == null || String.valueOf(this.value).isEmpty()) {
			return "";
		} else {
			return new SimpleDateFormat(this.formart == null ? "yyyy-MM-dd"
					: this.formart).format(this.value);
		}
	}

	private Object value;
	private String formart;

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getFormart() {
		return formart;
	}

	public void setFormart(String formart) {
		this.formart = formart;
	}
}
