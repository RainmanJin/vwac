package com.ecannetwork.dto.tech;

import java.util.Date;

/**
 * TechConsumablesManager entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechConsumablesBatch extends AbstractTechConsumablesBatch
        implements java.io.Serializable
{

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public TechConsumablesBatch()
	{
	}

	/** full constructor */
	public TechConsumablesBatch(String batch, String userName, Float conPrice,
	        Float conSum, String conRemark, String consumablesId, Date conDate,
	        Float surplusSum)
	{
		super(batch, userName, conPrice, conSum, conRemark, consumablesId,
		        conDate, surplusSum);
	}

}
