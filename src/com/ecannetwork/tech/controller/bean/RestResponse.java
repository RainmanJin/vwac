package com.ecannetwork.tech.controller.bean;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.ecannetwork.core.i18n.I18N;

@JsonSerialize(include=Inclusion.NON_NULL)
public class RestResponse
{
	
	private Object data;
	private RestResponseStatus respStatus;
	private String version;
	
	private String total;
	
	private Object property;
	
	
	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public Object getProperty() {
		return property;
	}

	public void setProperty(Object property) {
		this.property = property;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public RestResponse(Integer respCode, String respDesc, Object data)
	{
		this.respStatus = new RestResponseStatus(respCode, respDesc);
		this.data = data;
	}

	public RestResponse(Integer respCode, String respDesc)
	{
		this.respStatus = new RestResponseStatus(respCode, respDesc);
	}

	public static RestResponse success(Object data)
	{
		return new RestResponse(RestResponseStatus.RestCode.SUCCESS, "success",
				data);
	}

	public static RestResponse authedFailedWithErrorUserIDOrPasswd()
	{
		return new RestResponse(RestResponseStatus.RestCode.AUTH_FAILED,
				"login.loginError");
	}
	
	public static RestResponse authedFailedWithWrongPassword()
	{
		return new RestResponse(RestResponseStatus.RestCode.AUTH_FAILED,
				"server.wrongPassword");
	}

	public static RestResponse authedFailedWithUserStatus()
	{
		return new RestResponse(RestResponseStatus.RestCode.USER_STATUS_ERROR,
				"login.loginError");
	}
	
	public static RestResponse validateFailure()
	{
		return new RestResponse(RestResponseStatus.RestCode.ERROR,
				"validatePassword");
	}

	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}

	public RestResponseStatus getRespStatus()
	{
		return respStatus;
	}

	public void setRespStatus(RestResponseStatus respStatus)
	{
		this.respStatus = respStatus;
	}

	public boolean success()
	{
		return this.respStatus.getRespCode() == RestResponseStatus.RestCode.SUCCESS;
	}
}
