package com.jy.app.market.idata.card;

import java.util.List;

/**
 * <b>搜索热词</b><br>
 * 搜索热词,若干个搜索词语
 *
 * @author zzg.zhou(11039850@qq.com)
 */
public class CardWords extends Card{ 
	private static final long serialVersionUID = 548806411310064004L;
	
	/**
	 * 热词列表
	 */
	private List<String> words;

	public List<String> getWords() {
		return words;
	}

	public void setWords(List<String> words) {
		this.words = words;
	}
}
