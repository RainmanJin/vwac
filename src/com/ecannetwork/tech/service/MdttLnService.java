package com.ecannetwork.tech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecannetwork.core.module.db.dao.CommonDAO;
import com.ecannetwork.dto.tech.TechMdttLN;
import com.ecannetwork.dto.tech.TechMdttLNPkg;
import com.ecannetwork.dto.tech.TechTestActive;
import com.ecannetwork.dto.tech.TechTestUser;

/**
 * @author 李伟
 * 2015-7-28下午2:18:11
 */
@Service
public class MdttLnService {
	@Autowired
	private CommonDAO commonDAO;
	@SuppressWarnings("unchecked")
	public TechMdttLN get(String id)
	{
		TechMdttLN ln = (TechMdttLN) this.commonDAO.get(id,
				TechMdttLN.class);
		List<TechMdttLNPkg> lnpkg = this.commonDAO.list(
				"from TechMdttLNPkg t where t.mdttlnid = ?", id);
		//active.setUserids(users);
         ln.setLnpkgs(lnpkg);
		return ln;
	}
}
