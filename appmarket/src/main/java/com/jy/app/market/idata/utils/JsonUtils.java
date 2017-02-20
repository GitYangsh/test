package com.jy.app.market.idata.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jy.app.market.idata.Doc;
import com.jy.app.market.idata.card.Card;
import com.jy.app.market.idata.data.PageCard;

public class JsonUtils {
	private static GsonBuilder gb=new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static Gson getGson(){			 
		return gb.create();   
	}

	
	public static String toJson(Object r){			
		return getGson().toJson(r);
	}
	 
	
	public static Doc jsonToDoc(String jsonString){
		Doc r=new Doc();
		try{
			JsonObject json=(JsonObject)new JsonParser().parse(jsonString);
			r.setStatus(getInt(json,"status",200));
			r.setMessage(getString(json,"message"));
			
			JsonElement dataType=json.get("dataType");
			if(dataType!=null){
				r.setDataType(dataType.getAsString());
				
				parseToDoc(r,json);				 				 									
			}												
		}catch(Exception e){
			r=new Doc(666,"JSON数据解析错误: "+e.getMessage());
		}
		return r;
	}
	
	private static void parseToDoc(Doc r,JsonObject json)throws Exception{
		Class<?> dataClass=null;
		
		String dataType=r.getDataType();
		if(dataType.indexOf(".")>0){
			dataClass=Class.forName(dataType);
		}else{
			dataClass=Class.forName("com.jy.app.market.idata.data."+dataType);
		}
		  
		if(dataClass==PageCard.class){
			PageCard data=new PageCard();
			
			JsonObject jdata=(JsonObject)json.get("data");
			
			data.setPageNo(getInt(jdata,"pageNo",1));
			data.setTotalPage(getInt(jdata,"totalPage",0));
			data.setDataVersion(getLong(jdata,"dataVersion",0L));
			data.setTitle(getString(jdata,"title"));
			 
			List<Card> cards=new ArrayList<Card>();
			JsonArray array=jdata.getAsJsonArray("cards");
			if(array!=null && array.size()>0){
				Gson gson=getGson();
				for(int i=0;i<array.size();i++){
					JsonObject c=(JsonObject)array.get(i);
					String type=c.get("type").getAsString();
					String cardClazz=Card.class.getPackage().getName()+"."+type;
					Card card=(Card)gson.fromJson(c,Class.forName(cardClazz));
					
					cards.add(card);
				}
			}
			data.setCards(cards);
			
			r.setData(data);
		}else{
			Gson gson=getGson();
			Object data=gson.fromJson(json.get("data"),dataClass);
			r.setData(data);
		}
	}
	
	private static String getString(JsonObject json,String name){
		JsonElement e=json.get(name);
		if(e!=null){
			return e.getAsString();
		}
		return null;
	}
	
	private static int getInt(JsonObject json,String name,int defaultValue){
		JsonElement e=json.get(name);
		if(e!=null){
			return e.getAsInt();
		}
		return defaultValue;
	}

	private static long getLong(JsonObject json,String name,long defaultValue){
		JsonElement e=json.get(name);
		if(e!=null){
			return e.getAsLong();
		}
		return defaultValue;
	}
}
