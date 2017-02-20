package com.jy.app.market.idata.card;

import java.util.List;

import com.jy.app.market.idata.data.Banner;

/**
 * <b>Banner组合</b><br>
 * 
 * Banner条组合,需显示多张Banner图
 * 
 *
 * @author zzg.zhou(11039850@qq.com)
 */
public class CardGroupBanner extends Card {
	private static final long serialVersionUID = 548806411310064004L;

	private List<Banner> banners;

	private int index;

	public List<Banner> getBanners() {
		return banners;
	}

	public void setBanners(List<Banner> banners) {
		this.banners = banners;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
