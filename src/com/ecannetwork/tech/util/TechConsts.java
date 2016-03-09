package com.ecannetwork.tech.util;

import java.io.File;

/**
 * 常量表
 * 
 * @author leo
 * 
 */
public class TechConsts
{
	/**
	 * 课件的播放界面
	 * 
	 * @author leo
	 * 
	 */
	public static class scormPlayControlType
	{
		/**
		 * 一步一步的看，之支持上一页，下一页
		 */
		public static final String step = "step";
		/**
		 * 栏目导航形式
		 */
		public static final String nav = "nav";
	}

	/**
	 * scorm课件存储路径
	 */
	public static String SCORM_FILE_PATH = "tech" + File.separator
			+ "scorm_course";

	public static String UPLOAD_HEAD_IMG = "tech" + File.separator + "upload"
			+ File.separator + "headimg";
	public static String DMP_PKG_IMG = "tech" + File.separator + "upload"
			+ File.separator + "dmppkgimg";
	

	public static String INFORMATION = "tech" + File.separator + "upload"
			+ File.separator + "information";

	public static String USER_TEMPLATE_PATH = "tech" + File.separator
			+ "upload" + File.separator + "usertemplate";
	public static String UPLOAD_IPAD_NOTES_IMG = "tech" + File.separator
			+ "upload" + File.separator + "ipadNotes";
	
	public static String CREATE_EXAM_JSON = "tech" + File.separator
	+ "upload" + File.separator + "dmppkg";
	
	public static String SELLING_POINT_ZIP = "tech" + File.separator
	+ "upload" + File.separator + "sellingPoint";

	/**
	 * 角色
	 * 
	 * @author liulibin
	 * 
	 */
	public static class Role
	{
		public static final String STUDENT = "student";
		public static final String TEACHER = "teacher";
		public static final String OPERMAN = "operman";
		public static final String ADMIN = "admin";
		public static final String PLAN_ADM = "planadm";
		public static final String RES_ADM = "resadm";
	}

	// ptp 类别测评的维度编号
	public static final String PTP = "1002";
}
