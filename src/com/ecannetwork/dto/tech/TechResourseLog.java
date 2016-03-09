package com.ecannetwork.dto.tech;

import java.util.Date;

/**
 * TechResourseLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechResourseLog extends AbstractTechResourseLog
{

	// Constructors

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

	/** default constructor */
	public TechResourseLog()
	{
	}

	/** full constructor */
	public TechResourseLog(String resType, String resId, String logInfo,
	        Float resSum, Date insertDate, String operName, String operType)
	{
		super(resType, resId, logInfo, resSum, insertDate, operName, operType);
	}

}
