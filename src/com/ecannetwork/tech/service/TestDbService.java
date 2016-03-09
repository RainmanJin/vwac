package com.ecannetwork.tech.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecannetwork.core.module.db.dao.CommonDAO;
import com.ecannetwork.core.module.service.ServiceSupport;
import com.ecannetwork.dto.tech.TechTestAnswer;
import com.ecannetwork.dto.tech.TechTestDb;

@Service
public class TestDbService extends ServiceSupport {

	@Autowired
	private CommonDAO commonDAO;

	/**
	 * 
	 * 添加试题及答案
	 * 
	 * @param list
	 *            试题对应的答案集
	 * @param db
	 *            试题
	 */
	public void saveTestDbTX(List<TechTestAnswer> list) {
		commonDAO.saveOrUpdate(list);
	}

	@SuppressWarnings("unchecked")
	public TechTestDb list(String techTestDbId) {
		TechTestDb db = new TechTestDb();
		db = (TechTestDb) commonDAO.get(techTestDbId, TechTestDb.class);
		List<TechTestAnswer> list = new ArrayList<TechTestAnswer>();
		list = commonDAO.list("from TechTestAnswer t where t.testingId = ? order by t.idx",
				techTestDbId);
		db.setList(list);
		return db;
	}

}
