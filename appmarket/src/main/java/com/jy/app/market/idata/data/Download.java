package com.jy.app.market.idata.data;

import java.io.Serializable;

public class Download implements Serializable {
	private static final long serialVersionUID = 1641836448526226330L;

	private String name;
	private String dlUrl;
	private String iconUrl;
	private String pkgName;
	private int versionCode;
	private String versionName;
	private String md5;
	private long fileSize;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDlUrl() {
		return dlUrl;
	}

	public void setDlUrl(String dlUrl) {
		this.dlUrl = dlUrl;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
}
