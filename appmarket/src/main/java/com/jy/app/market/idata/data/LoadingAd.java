package com.jy.app.market.idata.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author zzg.zhou(11039850@qq.com)
 */
public class LoadingAd implements Serializable {	
	private static final long serialVersionUID = 1L;
	
	
	private List<Ad> ads=new ArrayList<Ad>();


	public List<Ad> getAds() {
		return ads;
	}


	public void setAds(List<Ad> ads) {
		this.ads = ads;
	}

}
