package com.jy.app.market.idata.data;

import java.io.Serializable;
import java.util.List;

public class LockscreenCfg implements Serializable {
	private static final long serialVersionUID = -1515411134918808240L;
	/**
	 * 本地记录保存时间的字段，和服务器没有关系
	 */
	private long time;
	private List<LockscreenCfgItem> config;

	public List<LockscreenCfgItem> getConfig() {
		return config;
	}

	public void setConfig(List<LockscreenCfgItem> config) {
		this.config = config;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}