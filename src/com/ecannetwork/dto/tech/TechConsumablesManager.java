package com.ecannetwork.dto.tech;

/**
 * TechConsumablesManager entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TechConsumablesManager extends AbstractTechConsumablesManager
        implements java.io.Serializable
{

	// Constructors

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

	/** default constructor */
	public TechConsumablesManager()
	{
	}
	
	private boolean hasUsed;

	/** full constructor */
	public TechConsumablesManager(String conName, String conType, Float conSum,
	        String conRemark, String conStatus, Float conPrice)
	{
		super(conName, conType, conSum, conRemark, conStatus, conPrice);
	}

	public boolean isHasUsed()
    {
    	return hasUsed;
    }

	public void setHasUsed(boolean hasUsed)
    {
    	this.hasUsed = hasUsed;
    }

}
