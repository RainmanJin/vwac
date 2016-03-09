package com.ecannetwork.core.module.db.dao;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 重写spring dao方法， 保证sessionFactory可注入
 * 
 * @author leo
 * 
 */
public class SpringDAOSupport extends HibernateDaoSupport
{
	private SessionFactory mySessionFacotry;

	@Resource
	public void setMySessionFacotry(SessionFactory sessionFacotry)
	{
		this.mySessionFacotry = sessionFacotry;
	}

	@PostConstruct
	public void injectSessionFactory()
	{
		super.setSessionFactory(mySessionFacotry);
	}
}
