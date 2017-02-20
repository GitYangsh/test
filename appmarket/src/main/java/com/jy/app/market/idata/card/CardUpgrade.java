package com.jy.app.market.idata.card;

import com.jy.app.market.idata.data.Upgrade;

/**
 * <b>升级卡片</b> <br>
 * 
 * 用于更新应用市场本身
 * 
 *
 * @author zzg.zhou(11039850@qq.com)
 */
public class CardUpgrade extends Card{ 
	private static final long serialVersionUID = 548806411310064004L;
	
	 private Upgrade upgrade;

	public Upgrade getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(Upgrade upgrade) {
		this.upgrade = upgrade;
	}
}
