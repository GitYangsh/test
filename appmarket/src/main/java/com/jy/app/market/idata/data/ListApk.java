package com.jy.app.market.idata.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jy.app.market.idata.card.CardApk;

/**
 * APK列表
 *  
 *
 * @author zzg.zhou(11039850@qq.com)
 */
public class ListApk implements Serializable {	
	private static final long serialVersionUID = 1L;

	private List<CardApk> apks;

	public List<CardApk> getApks() {
		if(apks==null){
			apks=new ArrayList<CardApk>();
		}
		return apks;
	}

	public void setApks(List<CardApk> apks) {
		this.apks = apks;
	}
	 
}
