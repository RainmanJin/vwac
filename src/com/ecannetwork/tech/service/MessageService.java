package com.ecannetwork.tech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecannetwork.core.module.db.dao.CommonDAO;
import com.ecannetwork.dto.tech.EcanUserMessage;
import com.ecannetwork.dto.tech.TechUserMessage;

/**
 * @author 李伟
 * 2015-8-1下午11:59:00
 */
@Service
public class MessageService {
	@Autowired
	private CommonDAO commonDAO;
	@SuppressWarnings("unchecked")
	public TechUserMessage get(String id)
	{
		TechUserMessage tum = (TechUserMessage) this.commonDAO.get(id,
				TechUserMessage.class);
		List<EcanUserMessage> euser = this.commonDAO.list(
				"from EcanUserMessage t where t.mesid = ?", id);
		//active.setUserids(users);
         tum.setEusers(euser);
		return tum;
	}
}
