package com.jy.app.market.idata.card;

import java.util.List;

import com.jy.app.market.idata.data.Top;

/**
 * <b>榜单组合</b><br>
 * 榜单集合卡片：用于组合多个榜单
 *  
 *
 * @author zzg.zhou(11039850@qq.com)
 */
public class CardGroupTop extends Card{ 
	private static final long serialVersionUID = 548806411310064004L;
	
	/**
	 * 榜单列表
	 */
	private List<Top> tops;

	public List<Top> getTops() {
		return tops;
	}

	public void setTops(List<Top> tops) {
		this.tops = tops;
	}
}
