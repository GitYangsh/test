package com.jy.app.market.idata;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import com.jy.app.market.idata.utils.JsonUtils;

/**
 * 客户端和服务器的基本数据接口<br>
 * 
 * 调用示例: <code>
 * Doc doc=Doc.fromJson(String httpBody);
 * if(doc.isOk()){
 *   if(doc.isType(PageCard.class)){
 *   	PageCard data=doc.getData();
 *   }else{
 *   	showMessage("无效的数据类型: "+doc.getDataType()); 
 *   }
 *   ...
 * }else{
 *   showMessage(doc.message);
 * }
 * </code>
 *  
 *
 * @author zzg.zhou(11039850@qq.com)
 */
public class Doc implements Serializable{	 
	private static final long serialVersionUID = 5042617802808490420L;

	/**
	 * 状态代码参考HTTP协议的基本定义: <br>
	 * 200 - OK <br>
	 * 4xx - 请求错误 <br>
	 * 5xx - 服务器处理错误<br>
	 */
	private int status=200;
	
	/**
	 * 服务器响应的结果字符串描述
	 */
	private String message="OK";
	
	/**
	 * 服务器响应数据的类型
	 */
	private String dataType;
	
	/**
	 * 服务器响应的数据
	 */
	private Object data;
	 
	public Doc(){
		this(200,"OK");
	}
	
	public Doc(int status,String message){
		this.status=status;
		this.message=message;
	}
	
	
	public Doc(Object data){
		this(200,"OK");
		setData(data);
	}
	
	/**
	 * 响应是否正常
	 */
	public boolean isOk(){
		return status==200;
	}
	
	/**
	 * 状态代码参考HTTP协议的基本定义: <br>
	 * 200 - OK <br>
	 * 4xx - 请求错误 <br>
	 * 5xx - 服务器处理错误<br>
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param cod
	 * 状态代码参考HTTP协议的基本定义: <br>
	 * 200 - OK <br>
	 * 4xx - 请求错误 <br>
	 * 5xx - 服务器处理错误<br>
	 */
	public Doc setStatus(int status) {
		this.status = status;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public Doc setMessage(String message) {
		this.message = message;
		return this;
	}
  

	public String getDataType() {
		return dataType;
	}

	public Doc setDataType(String dataType) {
		this.dataType = dataType;
		
		return this;
	}

	/**
	 * 获取响应的数据对象, 调用前可使用 {@link #isType}, 以避免ClassCastException
	 * @see #isType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getData() {
		return (T)data;
	}

	/**
	 * 检测响应的数据是否为指定的Class
	 * 
	 * @see #getData
	 * @param clazz
	 * @return
	 */
	public boolean isType(Class<?> clazz) {		
		return data!=null && data.getClass()==clazz;
	}
	
	public Doc setData(Object data) {		
		if(data!=null && dataType==null){
			Class<?> clazz=data.getClass();
			if(clazz.getPackage().getName().equals("com.jy.app.market.idata.data")){
				this.dataType=data.getClass().getSimpleName();
			}else{			
				this.dataType=clazz.getName();
			}
		}
		 
		this.data = data;
		
		return this;
	}		 
	
	
	public String toJson(){
		return JsonUtils.toJson(this);
	}
	
	public byte[] getBytes(){
		try{
			return toJson().getBytes("utf-8");
		}catch(UnsupportedEncodingException e){
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 解析JSON字符串到Result对象
	 * 
	 * @param json  JSON字符串
	 * @return Result对象
	 */
	public static Doc fromJson(String jsonString){
		return JsonUtils.jsonToDoc(jsonString);		 
	}
		
}
