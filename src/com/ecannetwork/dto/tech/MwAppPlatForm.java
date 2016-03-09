package com.ecannetwork.dto.tech;

import java.util.Date;

public class MwAppPlatForm extends AbsctractAppPlatForm implements
		java.io.Serializable {
	public MwAppPlatForm() {

	}

	public MwAppPlatForm(String id, String version, String pkgid, String name,
			String apkurl, String iosurl, String brand, String proType,
			Date lastUpdateTime, String status, String trianPlanId,
			String remark, String iconUrl, String valid, String pkgType,
			Integer versionCode, String fileSize) {
		super(id, version, pkgid, name, apkurl, iosurl, brand, proType,
				lastUpdateTime, status, trianPlanId, remark, iconUrl, valid,
				pkgType, versionCode, fileSize);
	}
}
