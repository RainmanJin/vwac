package com.ecannetwork.tech.controller.bean;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class RestResponseStatus
{
	private Integer respCode;
	private String respDesc;

	public static final class RestCode
	{
		public static final Integer SUCCESS = 0;// 成功
		public static final Integer ERROR = 9;//失败
		public static final Integer NOT_FOUND = 404;
		public static final Integer AUTH_FAILED = 403;
		public static final Integer USER_STATUS_ERROR = 405;
	}

	public RestResponseStatus(Integer respCode2, String respDesc2)
	{
		this.respCode = respCode2;
		this.respDesc = respDesc2;
	}

	public Integer getRespCode()
	{
		return respCode;
	}

	public void setRespCode(Integer respCode)
	{
		this.respCode = respCode;
	}

	public String getRespDesc()
	{
		return respDesc;
	}

	public void setRespDesc(String respDesc)
	{
		this.respDesc = respDesc;
	}
}
