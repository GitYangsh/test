package com.jy.app.market.idata.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jy.app.market.idata.card.CardApk;

/**
 * 新机必备
 *  
 *
 * @author zzg.zhou(11039850@qq.com)
 */
public class MapApk implements Serializable {	
	private static final long serialVersionUID = 1L;

	private long dataVersion;
	
	private Map<String,List<CardApk>> apks;

	public Map<String, List<CardApk>> getApks() {
		if(apks==null){
			apks=new HashMap<String, List<CardApk>>();
		}
		return apks;
	}

	public void setApks(Map<String, List<CardApk>> apks) {
		this.apks = apks;
	}

	public long getDataVersion() {
		return dataVersion;
	}

	public void setDataVersion(long dataVersion) {
		this.dataVersion = dataVersion;
	}
	 
}
