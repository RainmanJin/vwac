package com.ecannetwork.tech.controller.bean;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include=Inclusion.NON_NULL)
public class RestResponseList {
	private Object list;

	private RestResponseStatus respStatus;
	private String version;
	

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}
	
	public RestResponseList(){
	}

	public RestResponseList(Integer respCode, String respDesc, Object list)
	{
		this.respStatus = new RestResponseStatus(respCode, respDesc);
		this.list = list;
	}

	public RestResponseList(Integer respCode, String respDesc)
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

	public Object getList() {
		return list;
	}

	public void setList(Object list) {
		this.list = list;
	}
	
	
}
