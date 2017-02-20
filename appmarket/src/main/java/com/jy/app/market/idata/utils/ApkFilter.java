package com.jy.app.market.idata.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.jy.app.market.idata.data.Apk;

/**
 * 过滤榜单或卡片下Apk多余的字段数据
 *   
 * @author zzg.zhou(11039850@qq.com)
 */
public class ApkFilter {
 
	/**
	 * 过滤掉一般榜单Apk下的数据
	 * 
	 * @param apk
	 */
	public static void filterApk(Apk apk){
		apk.setCategoryLv1(null);
		apk.setCategoryLv2(null);
		apk.setChangelog(null);
		apk.setDeveloper(null);
		apk.setPermissions(null);
		apk.setScreenshots(null);
		apk.setTags(null);
		apk.setAdsType(null);
		apk.setCreation(null);
		apk.setSignature(null);
		
		fixedIcons(apk);
	}
	
	/**
	 * 过滤掉排行榜单Apk下的数据(和一般榜单相同)
	 * 
	 * @param apk
	 */
	public static  void filterApkTop(Apk apk){
		filterApk(apk);		 
	}
	
	/**
	 * 过滤带有屏幕截图的Apk数据, 如搜索结果的第一条
	 * 
	 * @param apk
	 */
	public static void filterApkImages(Apk apk){
		apk.setCategoryLv1(null);
		apk.setCategoryLv2(null);
		apk.setChangelog(null);
		apk.setDeveloper(null);
		apk.setPermissions(null);
		
		fixedIcons(apk);
	}

	/**
	 * 修复Icon路径,防止客户端异常:
	 * icons对象必须要有: px256,px100,px78, px68,px48,px78
	 * 
	 */
	public static void fixedIcons(Apk apk){
		JsonObject icons=apk.getIcons();
		if(icons!=null){
			String[] px=new String[]{"px256","px100","px78", "px68","px48"};
			for(String x:px){
				JsonElement js=icons.get(x);
				if(js==null || js.isJsonNull()){
					icons.add(x, new JsonPrimitive(""));
				}
			}
		}
	}
}
