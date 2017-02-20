package com.jy.app.market.idata.data;

import com.google.gson.JsonObject;
import com.jy.app.market.apk.type.AdsType;
import com.jy.app.market.apk.type.ApkType;
import com.jy.app.market.apk.type.PaidType;
import com.jy.app.market.apk.type.SecurityStatus;

import java.io.Serializable;
import java.util.List;

 
public class Apk implements Serializable{ 
	private static final long serialVersionUID = 548806411310064004L;


	public Apk(){		
		
	}
	
	public Apk(String packageName){
		this.packageName=packageName;
	}
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> package_name [<font color=red>KEY</font>|<font color=red>NOTNULL</font>] <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 128		 	&nbsp;<B>value:</B>  <br> 			
	* <li><B>remarks:</B> 应用的包名			 
	*/
	private String packageName;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> type <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 255					
	* <li><B>remarks:</B> APK类型(GAME, APP): #enum{com.ajy.app.market.apk.type.ApkType}			 
	*/
	private ApkType type;	
		 
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> creation <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 19					
	* <li><B>remarks:</B> 创建时间，即最新的 APK 的发布时间，一般可以用作这个应用的更新时间			 
	*/
	private java.util.Date creation;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> title <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 64					
	* <li><B>remarks:</B> 应用的名称			 
	*/
	private String title;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> tagline <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 64					
	* <li><B>remarks:</B> 应用的副标题			 
	*/
	private String tagline;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> version_name <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 128					
	* <li><B>remarks:</B> 版本名称			 
	*/
	private String versionName;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> version_code <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 10					
	* <li><B>remarks:</B> 版本号			 
	*/
	private Integer versionCode;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> bytes <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 10					
	* <li><B>remarks:</B> 文件大小			 
	*/
	private Integer bytes;	
	
	/**
	 * APK文件的MD5值
	 */
	private String md5;
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> signature <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 256					
	* <li><B>remarks:</B> RSA 签名中公钥的 md5 值			 
	*/
	private String signature;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> min_sdk_version <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 10					
	* <li><B>remarks:</B> 支持的最小版本的 Android API Level			 
	*/
	private Integer minSdkVersion;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> ads_type <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 16					
	* <li><B>remarks:</B> 广告类型:  	 
	*/
	private AdsType adsType;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> paid_type <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 16					
	* <li><B>remarks:</B> 付费类型:  		
	*/
	private PaidType paidType;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> security_status <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 16					
	* <li><B>remarks:</B> 安全状态:
	*/
	private SecurityStatus securityStatus;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> download_url <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 2,048					
	* <li><B>remarks:</B> 下载地址			 
	*/
	private Download downloadUrl;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> superior <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 10					
	* <li><B>remarks:</B> 是否是优质应用: #bool{}			 
	*/
	private Boolean superior;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> language <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 2,048					
	* <li><B>remarks:</B> 这个安装包支持的语言类型，是一个数组			 
	*/
	private String[] language;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> category_lv1 <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 256					
	* <li><B>remarks:</B> 一级分类，豌豆荚的应用最多会有两级分类 #array{}			 
	*/
	private String[] categoryLv1;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> category_lv2 <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 256					
	* <li><B>remarks:</B> 二级分类，豌豆荚的应用最多会有两级分类 #array{}			 
	*/
	private String[] categoryLv2;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> permissions <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 2,048					
	* <li><B>remarks:</B> 应用权限			 
	*/
	private String[] permissions;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> changelog <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 65,535					
	* <li><B>remarks:</B> 应用的更新日志			 
	*/
	private String changelog;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> description <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 65,535					
	* <li><B>remarks:</B> 应用的描述信息			 
	*/
	private String description;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> recommend <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 2,048					
	* <li><B>remarks:</B> 小编推荐			 
	*/
	private String recommend;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> developer <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 2,048					
	* <li><B>remarks:</B> 开发者信息(json格式)			 
	*/
	private JsonObject developer;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> likes_rate <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 12					
	* <li><B>remarks:</B> 好评率，75 表示 75% 的给了好评			 
	*/
	private Float likesRate;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> download_count <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 19					
	* <li><B>remarks:</B> 下载量			 
	*/
	private Long downloadCount;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> download_count_str <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 16					
	* <li><B>remarks:</B> 下载量的文字表示，比如 3.4 万			 
	*/
	private String downloadCountStr;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> installed_count <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 19					
	* <li><B>remarks:</B> 安装量			 
	*/
	private Long installedCount;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> installed_count_str <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 16					
	* <li><B>remarks:</B> 安装量的文字表示			 
	*/
	private String installedCountStr;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> tags <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 2,048					
	* <li><B>remarks:</B> 应用的标签，分类默认属于应用的标签			 
	*/
	private String[] tags;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> icons <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 2,048					
	* <li><B>remarks:</B> 应用的图标，目前提供了 78 px 和 48 px 两种尺寸的图标地址			 
	*/
	private JsonObject icons;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> publish_date <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 19					
	* <li><B>remarks:</B> 发布日期			 
	*/
	private java.util.Date publishDate;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> screenshots <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 65,535					
	* <li><B>remarks:</B> 应用截图，目前提供了定宽为 320 px 和 200 px 的截图			 
	*/
	private ScreenShots screenshots;	
	
	
	 
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> package_name [<font color=red>KEY</font>|<font color=red>NOTNULL</font>] <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 128		 	&nbsp;<B>value:</B>  <br> 			
	* <li><B>remarks:</B> 应用的包名			 
	*/
	public Apk setPackageName(String packageName){
		this.packageName = packageName;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> type <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 255					
	* <li><B>remarks:</B> APK类型(GAME, APP): #enum{com.ajy.app.market.apk.type.ApkType}			 
	*/
	public Apk setType(ApkType type){
		this.type = type;		
		return this;
	}
		 
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> creation <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 19					
	* <li><B>remarks:</B> 创建时间，即最新的 APK 的发布时间，一般可以用作这个应用的更新时间			 
	*/
	public Apk setCreation(java.util.Date creation){
		this.creation = creation;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> title <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 64					
	* <li><B>remarks:</B> 应用的名称			 
	*/
	public Apk setTitle(String title){
		this.title = title;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> tagline <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 64					
	* <li><B>remarks:</B> 应用的副标题			 
	*/
	public Apk setTagline(String tagline){
		this.tagline = tagline;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> version_name <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 128					
	* <li><B>remarks:</B> 版本名称			 
	*/
	public Apk setVersionName(String versionName){
		this.versionName = versionName;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> version_code <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 10					
	* <li><B>remarks:</B> 版本号			 
	*/
	public Apk setVersionCode(Integer versionCode){
		this.versionCode = versionCode;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> bytes <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 10					
	* <li><B>remarks:</B> 文件大小			 
	*/
	public Apk setBytes(Integer bytes){
		this.bytes = bytes;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> signature <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 256					
	* <li><B>remarks:</B> RSA 签名中公钥的 md5 值			 
	*/
	public Apk setSignature(String signature){
		this.signature = signature;		
		return this;
	}
	
	/**
	 * APK文件的MD5值
	 */
	public Apk setMd5(String md5){
		this.md5=md5;
		return this;
	}
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> min_sdk_version <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 10					
	* <li><B>remarks:</B> 支持的最小版本的 Android API Level			 
	*/
	public Apk setMinSdkVersion(Integer minSdkVersion){
		this.minSdkVersion = minSdkVersion;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> ads_type <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 16					
	* <li><B>remarks:</B> 广告类型:	 
	*/
	public Apk setAdsType(AdsType adsType){
		this.adsType = adsType;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> paid_type <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 16					
	* <li><B>remarks:</B> 付费类型:  			
	*/
	public Apk setPaidType(PaidType paidType){
		this.paidType = paidType;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> security_status <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 16					
	* <li><B>remarks:</B> 安全状态:
	*/
	public Apk setSecurityStatus(SecurityStatus securityStatus){
		this.securityStatus = securityStatus;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> download_url <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 2,048					
	* <li><B>remarks:</B> 下载地址			 
	*/
	public Apk setDownloadUrl(Download downloadUrl){
		this.downloadUrl = downloadUrl;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> superior <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 10					
	* <li><B>remarks:</B> 是否是优质应用: #bool{}			 
	*/
	public Apk setSuperior(Boolean superior){
		this.superior = superior;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> language <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 2,048					
	* <li><B>remarks:</B> 这个安装包支持的语言类型，是一个数组			 
	*/
	public Apk setLanguage(String[] language){
		this.language = language;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> category_lv1 <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 256					
	* <li><B>remarks:</B> 一级分类，豌豆荚的应用最多会有两级分类 #array{}			 
	*/
	public Apk setCategoryLv1(String[] categoryLv1){
		this.categoryLv1 = categoryLv1;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> category_lv2 <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 256					
	* <li><B>remarks:</B> 二级分类，豌豆荚的应用最多会有两级分类 #array{}			 
	*/
	public Apk setCategoryLv2(String[] categoryLv2){
		this.categoryLv2 = categoryLv2;		
		return this;
	}
	
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> permissions <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 2,048					
	* <li><B>remarks:</B> 应用权限			 
	*/
	public Apk setPermissions(String[] permissions){
		this.permissions = permissions;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> changelog <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 65,535					
	* <li><B>remarks:</B> 应用的更新日志			 
	*/
	public Apk setChangelog(String changelog){
		this.changelog = changelog;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> description <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 65,535					
	* <li><B>remarks:</B> 应用的描述信息			 
	*/
	public Apk setDescription(String description){
		this.description = description;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> recommend <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 2,048					
	* <li><B>remarks:</B> 小编推荐			 
	*/
	public Apk setRecommend(String recommend){
		this.recommend = recommend;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> developer <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 2,048					
	* <li><B>remarks:</B> 开发者信息(json格式)			 
	*/
	public Apk setDeveloper(JsonObject developer){
		this.developer = developer;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> likes_rate <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 12					
	* <li><B>remarks:</B> 好评率，75 表示 75% 的给了好评			 
	*/
	public Apk setLikesRate(Float likesRate){
		this.likesRate = likesRate;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> download_count <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 19					
	* <li><B>remarks:</B> 下载量			 
	*/
	public Apk setDownloadCount(Long downloadCount){
		this.downloadCount = downloadCount;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> download_count_str <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 16					
	* <li><B>remarks:</B> 下载量的文字表示，比如 3.4 万			 
	*/
	public Apk setDownloadCountStr(String downloadCountStr){
		this.downloadCountStr = downloadCountStr;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> installed_count <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 19					
	* <li><B>remarks:</B> 安装量			 
	*/
	public Apk setInstalledCount(Long installedCount){
		this.installedCount = installedCount;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> installed_count_str <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 16					
	* <li><B>remarks:</B> 安装量的文字表示			 
	*/
	public Apk setInstalledCountStr(String installedCountStr){
		this.installedCountStr = installedCountStr;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> tags <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 2,048					
	* <li><B>remarks:</B> 应用的标签，分类默认属于应用的标签			 
	*/
	public Apk setTags(String[] tags){
		this.tags = tags;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> icons <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 2,048					
	* <li><B>remarks:</B> 应用的图标，目前提供了 78 px 和 48 px 两种尺寸的图标地址			 
	*/
	public Apk setIcons(JsonObject icons){
		this.icons = icons;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> publish_date <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 19					
	* <li><B>remarks:</B> 发布日期			 
	*/
	public Apk setPublishDate(java.util.Date publishDate){
		this.publishDate = publishDate;		
		return this;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> screenshots <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 65,535					
	* <li><B>remarks:</B> 应用截图，目前提供了定宽为 320 px 和 200 px 的截图			 
	*/
	public Apk setScreenshots(ScreenShots screenshots){
		this.screenshots = screenshots;		
		return this;
	}
   
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> package_name [<font color=red>KEY</font>|<font color=red>NOTNULL</font>] <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 128		 	&nbsp;<B>value:</B>  <br> 			
	* <li><B>remarks:</B> 应用的包名			 
	*/
	public String getPackageName(){
		return this.packageName;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> type <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 255					
	* <li><B>remarks:</B> APK类型(GAME, APP): #enum{com.ajy.app.market.apk.type.ApkType}			 
	*/
	public ApkType getType(){
		return this.type;
	}
	 	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> creation <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 19					
	* <li><B>remarks:</B> 创建时间，即最新的 APK 的发布时间，一般可以用作这个应用的更新时间			 
	*/
	public java.util.Date getCreation(){
		return this.creation;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> title <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 64					
	* <li><B>remarks:</B> 应用的名称			 
	*/
	public String getTitle(){
		return this.title;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> tagline <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 64					
	* <li><B>remarks:</B> 应用的副标题			 
	*/
	public String getTagline(){
		return this.tagline;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> version_name <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 128					
	* <li><B>remarks:</B> 版本名称			 
	*/
	public String getVersionName(){
		return this.versionName;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> version_code <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 10					
	* <li><B>remarks:</B> 版本号			 
	*/
	public Integer getVersionCode(){
		return this.versionCode;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> bytes <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 10					
	* <li><B>remarks:</B> 文件大小			 
	*/
	public Integer getBytes(){
		return this.bytes;
	}
	
	/**
	 * APK文件的MD5值
	 */
	public String getMd5(){
		return this.md5;
	}
	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> signature <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 256					
	* <li><B>remarks:</B> RSA 签名中公钥的 md5 值			 
	*/
	public String getSignature(){
		return this.signature;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> min_sdk_version <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 10					
	* <li><B>remarks:</B> 支持的最小版本的 Android API Level			 
	*/
	public Integer getMinSdkVersion(){
		return this.minSdkVersion;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> ads_type <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 16					
	* <li><B>remarks:</B> 广告类型:  			
	*/
	public AdsType getAdsType(){
		return this.adsType;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> paid_type <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 16					
	* <li><B>remarks:</B> 付费类型: 	 
	*/
	public PaidType getPaidType(){
		return this.paidType;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> security_status <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 16					
	* <li><B>remarks:</B> 安全状态: 			 
	*/
	public SecurityStatus getSecurityStatus(){
		return this.securityStatus;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> download_url <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 2,048					
	* <li><B>remarks:</B> 下载地址			 
	*/
	public Download getDownloadUrl(){
		return this.downloadUrl;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> superior <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 10					
	* <li><B>remarks:</B> 是否是优质应用: #bool{}			 
	*/
	public Boolean isSuperior(){
		return this.superior;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> language <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 2,048					
	* <li><B>remarks:</B> 这个安装包支持的语言类型，是一个数组			 
	*/
	public String[] getLanguage(){
		return this.language;
	}
		
	 
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> category_lv1 <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 256					
	* <li><B>remarks:</B> 一级分类，豌豆荚的应用最多会有两级分类 #array{}			 
	*/
	public String[] getCategoryLv1(){
		return this.categoryLv1;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> category_lv2 <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 256					
	* <li><B>remarks:</B> 二级分类，豌豆荚的应用最多会有两级分类 #array{}			 
	*/
	public String[] getCategoryLv2(){
		return this.categoryLv2;
	}
	
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> permissions <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 2,048					
	* <li><B>remarks:</B> 应用权限			 
	*/
	public String[] getPermissions(){
		return this.permissions;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> changelog <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 65,535					
	* <li><B>remarks:</B> 应用的更新日志			 
	*/
	public String getChangelog(){
		return this.changelog;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> description <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 65,535					
	* <li><B>remarks:</B> 应用的描述信息			 
	*/
	public String getDescription(){
		return this.description;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> recommend <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 2,048					
	* <li><B>remarks:</B> 小编推荐			 
	*/
	public String getRecommend(){
		return this.recommend;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> developer <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 2,048					
	* <li><B>remarks:</B> 开发者信息(json格式)			 
	*/
	public JsonObject getDeveloper(){
		return this.developer;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> likes_rate <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 12					
	* <li><B>remarks:</B> 好评率，75 表示 75% 的给了好评			 
	*/
	public Float getLikesRate(){
		return this.likesRate;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> download_count <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 19					
	* <li><B>remarks:</B> 下载量			 
	*/
	public Long getDownloadCount(){
		return this.downloadCount;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> download_count_str <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 16					
	* <li><B>remarks:</B> 下载量的文字表示，比如 3.4 万			 
	*/
	public String getDownloadCountStr(){
		return this.downloadCountStr;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> installed_count <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 19					
	* <li><B>remarks:</B> 安装量			 
	*/
	public Long getInstalledCount(){
		return this.installedCount;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> installed_count_str <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 16					
	* <li><B>remarks:</B> 安装量的文字表示			 
	*/
	public String getInstalledCountStr(){
		return this.installedCountStr;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> tags <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 2,048					
	* <li><B>remarks:</B> 应用的标签，分类默认属于应用的标签			 
	*/
	public String[] getTags(){
		return this.tags;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> icons <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 2,048					
	* <li><B>remarks:</B> 应用的图标，目前提供了 78 px 和 48 px 两种尺寸的图标地址			 
	*/
	public JsonObject getIcons(){
		return this.icons;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> publish_date <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 19					
	* <li><B>remarks:</B> 发布日期			 
	*/
	public java.util.Date getPublishDate(){
		return this.publishDate;
	}
		
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> screenshots <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 65,535					
	* <li><B>remarks:</B> 应用截图，目前提供了定宽为 320 px 和 200 px 的截图			 
	*/
	public ScreenShots getScreenshots(){
		return this.screenshots;
	}	 	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> official [<font color=red>NOTNULL</font>] <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 10		 	&nbsp;<B>value:</B> 1 <br> 			
	* <li><B>remarks:</B> 是否官方包  #bool{}			 
	*/
	private Boolean official=true;	
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> official [<font color=red>NOTNULL</font>] <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 10		 	&nbsp;<B>value:</B> 1 <br> 			
	* <li><B>remarks:</B> 是否官方包  #bool{}			 
	*/
	public Apk setOfficial(Boolean official){
		this.official = official;		
		return this;
	}
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> official [<font color=red>NOTNULL</font>] <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 10		 	&nbsp;<B>value:</B> 1 <br> 			
	* <li><B>remarks:</B> 是否官方包  #bool{}			 
	*/
	public Boolean isOfficial(){
		return this.official;
	}
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> local [<font color=red>NOTNULL</font>] <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 10		 	&nbsp;<B>value:</B> 1 <br> 			
	* <li><B>remarks:</B> 应用来源：0.豌豆荚;1.应用市场;2.360;3.百度{}			 
	*/
	private Integer local=0;
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> local [<font color=red>NOTNULL</font>] <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 10		 	&nbsp;<B>value:</B> 1 <br> 			
	* <li><B>remarks:</B> 设置来源  #bool{}			 
	*/
	public Apk setLocal(Integer local){
		this.local= local;		
		return this;
	}
	
	/**
	* @Column 
	* <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>table:</B> market_apk&nbsp;<B>name:</B> local [<font color=red>NOTNULL</font>] <br>   
	* <li>&nbsp;&nbsp;&nbsp;<B>length:</B> 10		 	&nbsp;<B>value:</B> 1 <br> 			
	* <li><B>remarks:</B> 获取来源 #int{}			 
	*/
	public Integer getLocal(){
		return this.local;
	}
	
	public class ScreenShots{
		/**
		 * 小图的URL列表
		 */
		private List<String> small;
		
		/**
		 * 正常图片的URL列表
		 */
		private List<String> normal;

		public List<String> getSmall() {
			return small;
		}

		public void setSmall(List<String> small) {
			this.small = small;
		}

		public List<String> getNormal() {
			return normal;
		}

		public void setNormal(List<String> normal) {
			this.normal = normal;
		}
	}
	
	public class Download{
		/**
		 * 下载来源
		 */
		private String market;
		
		/**
		 * 下载URL 
		 */
		private String url;

		public Download(){
			
		}
		
		public Download(String market,String url){
			this.market=market;
			this.url=url;
		}
		
		public String getMarket() {
			return market;
		}

		public void setMarket(String market) {
			this.market = market;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}	 
}
