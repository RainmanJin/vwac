package com.ecannetwork.core.app.user.dao;

import org.springframework.stereotype.Repository;

import com.ecannetwork.core.module.db.dao.DaoSupport;
import com.ecannetwork.dto.core.EcanUser;

@Repository
public class UserDAO extends DaoSupport<EcanUser>
{

	@Override
	public Class<EcanUser> getDTOClass()
	{
		return EcanUser.class;
	}

}
