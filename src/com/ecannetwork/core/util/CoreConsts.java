package com.ecannetwork.core.util;

import javax.servlet.ServletContext;

public class CoreConsts
{
	public static String SUPERMAN = "superman";
	public static String LANG = "_LANG";
	public static String THEME = "_THEME";

	/**
	 * 语言包：：：仅默认三种
	 * 
	 * @author leo
	 * 
	 */
	public static class LANGS
	{
		public static final String zh_CN = "zh_CN";
		public static final String zh_TW = "zh_TW";
		public static final String en = "en";

	}

	/**
	 * 是否、激活与否等
	 * 
	 * @author leo
	 * 
	 */
	public static class YORN
	{
		public static final String YES = "1";
		public static final String NO = "0";
	}

	/**
	 * 运行时的一些常量
	 * 
	 * @author leo
	 * 
	 */
	public static class Runtime
	{
		/**
		 * 应用程序的绝对路径
		 */
		public static String APP_ABSOLUTE_PATH;

		/**
		 * 执行环境
		 */
		public static ServletContext servletContext;
	}

	/**
	 * 执行环境的key
	 * 
	 * @author leo
	 * 
	 */
	public static class ExecuteContextKeys
	{
		/**
		 * 当前登录用户
		 */
		public static final String USER = "current_user";
		/**
		 * HttpSession
		 */
		public static final String SESSION = "_current_session";
		/**
		 * HttpServletRequest
		 */
		public static final String REQUEST = "_current_request";

		/**
		 * 当前用户使用的菜单
		 */
		public static final String CURRENT_MENU = "current_menu";

	}

	/**
	 * session环境变量
	 * 
	 * @author leo
	 * 
	 */
	public static class SessionScopeKeys
	{
		/**
		 * 当前用户
		 */
		public static final String CURRENT_USER = ExecuteContextKeys.USER;
		public static final String MENULIST = "MENU_LIST";
		public static final String AUTHED_MAP = "_AUTHED_SET";
		public static final String CURRENT_MENU = ExecuteContextKeys.CURRENT_MENU;

	}

	/**
	 * 题库状态
	 * 
	 * @author think
	 * 
	 */
	public static class dbstatus
	{

		public static final String PUBLISH = "1";
		public static final String NOVILID = "0";
	}

	public static class dbAnswerstatus
	{

		public static final String YES = "1";
		public static final String NO = "0";
	}

	public static class COURSESTATUS
	{
		// 发布中
		public static final String ING = "2";
		// 停用
		public static final String STOP = "0";
		// 过期
		public static final String EXPIRE = "3";
		// 未发布
		public static final String NOING = "1";
	}

	// 资源日志类别
	public static class RESOURSETYPE
	{
		// 常设品
		public static final String resourse = "0";

		// 消耗品
		public static final String consumable = "1";
	}

	public static class RESOURSE_OPER_TYPE
	{
		// 增加批次
		public static final String ADDBATCH = "0";
		// 分配出库
		public static final String ORDER = "1";

		public static final String DELETE = "2";
		
		public static final String INSERT="3";
		
		public static final String MODITY="4";
		
	}

	/**
	 * 问卷是否完成
	 * 
	 * @author think
	 * 
	 */
	public static class QUESTIONSTATUS
	{
		// 完成
		public static final String QUESTIONFINISH = "1";
		// 未完成
		public static final String QUESTIONUNFINISH = "0";
	}

	/**
	 * 问卷调查的单选表示
	 * 
	 * @author think
	 * 
	 */
	public static class QUESTIONASS
	{
		// 完全不同意
		public static final String StronglyDisagree = "1";
		// 不同意
		public static final String Disagree = "2";
		// 不表态
		public static final String NoResponse = "3";
		// 同意
		public static final String Agree = "4";
		// 完全同意
		public static final String StronglyAgree = "5";
	}

	public static class QUESTION
	{
		// 课程内容评估 Content Evaluation
		public static final String CONTENTEVALUATION = "1";
		// 培训讲师评估 Trainer Evaluation
		public static final String TRAINEREVALUATION = "2";
		// 培训组织评估Operation Evaluation
		public static final String OPERATIONEVALUATION = "3";
		// 简答comment
		public static final String COMMENT = "4";
		// 总体评价general comments
		public static final String GENERALCOMMENTS = "5";
	}

}
