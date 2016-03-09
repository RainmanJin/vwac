package com.ecannetwork.dto.core;

/**
 * EcanApp entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class EcanApp extends AbstractEcanApp implements java.io.Serializable
{

	// Constructors

	private static final long serialVersionUID = -1815513417371878575L;

	/**
	 * 级别编码长度
	 */
	public static final int LEVEL_CODE_LENGTH = 4;

	public String getParentLevelCode()
	{
		if (this.getLevelCode().length() > 4)
		{
			return this.getLevelCode().substring(0,
					this.getLevelCode().length() - 4);
		}
		return null;
	}

	/** default constructor */
	public EcanApp()
	{
	}

	/** full constructor */
	public EcanApp(String appName, String appCode, String levelCode,
			String funName, String funCode, String isOutside, String outsideUrl)
	{
		super(appName, appCode, levelCode, funName, funCode, isOutside,
				outsideUrl);
	}

}
