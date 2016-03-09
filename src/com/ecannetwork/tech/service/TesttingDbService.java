package com.ecannetwork.tech.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecannetwork.core.module.db.dao.CommonDAO;
import com.ecannetwork.core.module.service.ServiceSupport;
import com.ecannetwork.dto.tech.TechCourseTestingDb;

@Service
public class TesttingDbService extends ServiceSupport {

	@Autowired
	private CommonDAO commonDAO;

	@SuppressWarnings("unchecked")
	public TechCourseTestingDb list(String techTestingDbId) {
		TechCourseTestingDb db = new TechCourseTestingDb();
		db = (TechCourseTestingDb) commonDAO.get(techTestingDbId,
				TechCourseTestingDb.class);
		List<TechCourseTestingDb> list = new ArrayList<TechCourseTestingDb>();
		list = commonDAO
				.list(
						"from TechCourseTestingAnswer t where t.testingId = ? order by t.idx",
						techTestingDbId);
		db.setList(list);
		return db;
	}

}
