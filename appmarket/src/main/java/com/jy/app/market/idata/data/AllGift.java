package com.jy.app.market.idata.data;

import java.io.Serializable;
import java.util.List;

public class AllGift implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5917284273207859354L;
	private List<Apk> apks;
	private List<Gift> gifts;

	public List<Apk> getApks() {
		return apks;
	}

	public void setApks(List<Apk> apks) {
		this.apks = apks;
	}

	public List<Gift> getGifts() {
		return gifts;
	}

	public void setGifts(List<Gift> gifts) {
		this.gifts = gifts;
	}
}
