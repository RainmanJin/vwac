  
/**  
 * 文件名：AbstractTechUserMessage.java  
 *   
 * 日期：2015年7月3日  
 *  
*/  
    
package com.ecannetwork.dto.tech;


  
/**  
 * 
 * @author: 李超
 *
 * @version: 2015年7月3日 上午10:57:12   
 */

public abstract class AbstractTechUserMessage extends
com.ecannetwork.core.module.db.dto.DtoSupport implements java.io.Serializable {

	  
	private static final long serialVersionUID = 2644912975997544996L;
	
	private String id;
	private String title;
	private String content;
	private String status;
	private String type;
	private String mesTime;
	private String createTime;
	private String flag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMesTime() {
		return mesTime;
	}

	public void setMesTime(String mesTime) {
		//java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		//this.mesTime = format1.format(new Date());;
		this.mesTime = mesTime;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
