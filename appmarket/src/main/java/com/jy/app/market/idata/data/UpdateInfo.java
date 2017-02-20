package com.jy.app.market.idata.data;

import java.io.Serializable;

import com.jy.app.market.apk.type.NotRecommendReasonType;
import com.jy.app.market.apk.type.RecommendType;

public class UpdateInfo implements Serializable {
	private static final long serialVersionUID = -6193001963835576391L;

	public UpdateInfo() {

	}

	private String title;
	private String downloadUrl;
	private String iconPath;
	private String packageName;
	private int versionCode;
	private String versionName;
	private String fileMd5;
	private String changeLog;
	private long size;
	private long lastModified;
	private RecommendType isRecommended;
	private NotRecommendReasonType notRecommendedReason;
	private boolean superior;

	public String getTitle() {
		return title;
	}

	public void setTitle(String name) {
		this.title = name;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconUrl) {
		this.iconPath = iconUrl;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
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

	public String getFileMd5() {
		return fileMd5;
	}

	public void setFileMd5(String md5) {
		this.fileMd5 = md5;
	}

	public String getChangeLog() {
		return changeLog;
	}

	public void setChangeLog(String changeLog) {
		this.changeLog = changeLog;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long bytes) {
		this.size = bytes;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public RecommendType getIsRecommended() {
		return isRecommended;
	}

	public void setIsRecommended(RecommendType isRecommended) {
		this.isRecommended = isRecommended;
	}

	public NotRecommendReasonType getNotRecommendedReason() {
		return notRecommendedReason;
	}

	public void setNotRecommendedReason(NotRecommendReasonType notRecommendedReason) {
		this.notRecommendedReason = notRecommendedReason;
	}

	public boolean isSuperior() {
		return superior;
	}

	public void setSuperior(boolean superior) {
		this.superior = superior;
	}

}