package com.ecannetwork.core.module.controller;

import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class ControllerParamBinding implements WebBindingInitializer
{

	public void initBinder(WebDataBinder binder, WebRequest webRequest)
	{
        binder.registerCustomEditor(Date.class, new DateConvertEditor());  
	}

}
