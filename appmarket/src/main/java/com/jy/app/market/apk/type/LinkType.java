package com.jy.app.market.apk.type;

public enum LinkType {
	/**
	 * App详情页面,url值为包名: x.y.z
	 */
	App,
	
	/**
	 * Webview页面,url的值为: http://www.x/a?q=xx...
	 */
	WebView,
	
	/**
	 * 打开新卡片列表页面,打开新卡片列表页面, url值为不带参数的协议URI, 如: /discovery
	 */
	Cards;
}
