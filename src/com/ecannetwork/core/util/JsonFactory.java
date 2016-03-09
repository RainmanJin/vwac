package com.ecannetwork.core.util;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * json工厂
 * 
 * @author leo
 * 
 */
public class JsonFactory
{
	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * 转化对象为json格式， 异常时返回null
	 * 
	 * @param o
	 * @return
	 */
	public static String toJson(Object o)
	{
		try
		{
			return mapper.writeValueAsString(o);
		} catch (JsonGenerationException e)
		{
			e.printStackTrace();
		} catch (JsonMappingException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析json
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map parseJsonString(String jsonStr)
	{
		try
		{
			return mapper.readValue(jsonStr, Map.class);
		} catch (JsonParseException e)
		{
			e.printStackTrace();
		} catch (JsonMappingException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args)
	{
		String json = "{\"name\":\"leo\",\"age\":20}";
		System.out.println(parseJsonString(json));
	}
}
