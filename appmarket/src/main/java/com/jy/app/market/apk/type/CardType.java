package com.jy.app.market.apk.type;


/**
 * 卡片类型
 * 
 * @author zzg.zhou(11039850@qq.com)
 */
public enum CardType { 
	/**
	 * APK的信息, 简要信息: 如搜索结果第2条以后的记录<br>	 
	 */
	Apk,
	
	/**
	 * APK的信息, 屏幕截图: 如搜索结果的第1条<br>
	 */
	ApkImages,
	
	/**
	 * 相关的APK推荐<br>
	 * 文字说明+若干个APK: 如APK详情的"相关推荐"; 下载管理里面的"大家还下载了"
	 */
	ApkRecommend,
	
	/**
	 * APK的信息, 带排序号: 如排行榜下的APK<br>
	 */
	ApkTop,
	
	/**
	 * 软件/游戏分类榜单：主要用于组合分类形式 <br>
	 * 一个主分类(带图标) 和 若干个 子分类 
	 */
	Category,
	
	/**
	 * 分组榜单：这种形式的榜单主要表现在一个榜单中的App可以分为不同的组别<br>
	 * 包含一个榜单名称, 以及若干个APK的信息.
	 */ 
	GroupApk,
	
	/**
	 * 排行卡片：用于展示某个排行榜<br>
	 * 排行榜名称以及若干的APK(带排序号)
	 */
	GroupApkTop,
	
	
	/**
	 * banner条组合<br>
	 * 需显示多张Banner图
	 */
	GroupBanner,
		
	/**
	 * 专题组合<br>
	 * 标题, 副标题, 多个APK
	 */
	GroupSubject,
	

	
	/**
	 * 榜单集合卡片：用于组合多个榜单
	 * 若干个榜单(名称,图标)
	 */
	GroupTop,
	
	
	/**
	 * 专题榜单形式：主要用于表现各种专题榜单<br>
	 * 包含一个Banner图, 文字说明信息
	 */
	SubjectHeader,
	
	/**
	 * 应用市场的升级信息
	 */
	Upgrade,
	
	/**
	 * 搜索热词<br>
	 * 若干个搜索词语
	 */
	Words
}
