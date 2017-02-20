package com.jy.app.market.idata.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

 
/**
 * @author zzg {MSN:pingzzg@hotmail.com, QQ:11039850}
 *  
 */
public class HttpURL{
	 
	private String host;
	private String path;
	private String contextpath;
	
	private Map<String,String[]> hParameters=new LinkedHashMap<String,String[]>();
 
	public HttpURL(String url){
		setURL(url,"utf-8");
	}
	public HttpURL(String url,String encoding){
		setURL(url,encoding);
	}
	 
	public String getPath(){
		return path;
	}
	
	public String getHost() {
		return host;
	}
	
	public String getContextPath() {
		return contextpath;
	}
	
	public String getQuery(){
		StringBuffer sb=new StringBuffer();
		for(String key:hParameters.keySet()){
			String[] vs=hParameters.get(key);
			if(vs!=null && vs.length>0){
				for(String v:vs){
					if(v!=null && v.length()>0){
						if(sb.length()>0){
							sb.append("&");
						}
						try{
							sb.append(key);
							sb.append("=");
							sb.append(URLEncoder.encode(v, "utf-8"));
						}catch(UnsupportedEncodingException e){
							throw new RuntimeException("Error encode: "+v,e);
						}
					}
				}
			}
		}
		return sb.toString();
	}
	
	public String getUrl(){
		String q=getQuery();
		if(q!=null && q.length()>0){
			if(path==null){
				return q;
			}else{
				return path+"?"+q;
			}
		}else{
			return path;
		}
	}
	
	public Map<String,String[]> getParameters(){
		return hParameters;
	}
	
	public void setParameter(String name,int value){	
		setParameter(name,""+value);
	}
	public void setNotNullParameter(String name,String value){
		if(value!=null){
			setParameter(name,value);
		}
	}
	public void setParameter(String name,String value){				
		hParameters.put(name, new String[]{value});
	}
 
	public void cleanParameter(String name){
		if(hParameters.containsKey(name)){
			hParameters.remove(name);
		}
	}
	
	public void addParameter(String name,String value){
		String[] vs=hParameters.get(name);
		if(vs==null || vs.length==0){
			vs=new String[]{value};
		}else{
			String[] new_vs=new String[vs.length+1];
			for(int k=0;k<vs.length;k++){
				new_vs[k]=vs[k];
			}
			new_vs[vs.length]=value;
			vs=new_vs;
			new_vs=null;
		}
		hParameters.put(name,vs);
		 
	} 
	
	public void setURL(String url){
		setURL(url,"utf-8");
	}
	public void setURL(String url,String encoding){		 
		hParameters.clear();
		path=url;
		
		if(url!=null){
			String tmpUrl= "";
			if(url.startsWith("http://")){
				tmpUrl = url.substring(7);
			}else if(url.startsWith("https://")){
				tmpUrl = url.substring(8);
			}else{
				tmpUrl = url;
			}
			
			if(tmpUrl.indexOf("/")<0){
				
			}else{
				host = tmpUrl.substring(0,tmpUrl.indexOf("/"));
				if(tmpUrl.indexOf("?") >0){
					contextpath = tmpUrl.substring(tmpUrl.indexOf("/"),tmpUrl.indexOf("?"));
				}else{
					contextpath = tmpUrl.substring(tmpUrl.indexOf("/"));
				}
			}
			
			int p=url.indexOf("?");	
			String q=url;
			if(p>=0){
				path=url.substring(0,p);
				q=url.substring(p+1);
			}else{				 
				q="";
			}
			
			String[] ps=q.split("\\&");			 
			for(int i=0;i<ps.length;i++){
				String[] nv=ps[i].split("=");
				if(nv.length==2){
					String name=nv[0];
					String value=nv[1];
					try{
						if(value!=null && value.length()>0){
							value=URLDecoder.decode(value,encoding);
						}
					}catch(Exception e){}
					
					addParameter(name,value);
					
				}
			}				
		}
		
	}
	
	public int getIntParameter(String name,int defaultValue){
		Integer v=getIntParameter(name);
		if(v==null){
			return defaultValue;
		}else{
			return v.intValue();
		}
	}
	public Integer getIntParameter(String name){
		String v=getParameter(name);
		if(v!=null){
			return Integer.parseInt(v.trim());
		}else{
			return null;
		}
	}
	public Date getDateParameter(String name){
		String v=getParameter(name);
		if(v!=null){
			try{
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return sdf.parse(v);
			}catch(ParseException pe){
				throw new RuntimeException("Invalid date(yyyy-MM-dd HH:mm:ss): "+v);			 
			}
		}else{
			return null;
		}
	}
	
	public boolean getBoolParameter(String name,boolean defaultValue){
		Boolean v=getBoolParameter(name);
		if(v==null){
			return defaultValue;
		}else{
			return v.booleanValue();
		}
	}
	public Boolean getBoolParameter(String name){
		String v=getParameter(name);
		if(v!=null){
			v=v.trim();
			if(v.equals("1") || v.equals("true") ){
				return true;
			}else{
				return false;
			}
		}else{
			return null;
		}
	}
	public String getParameter(String name,String defaultValue){
		String v=getParameter(name);
		if(v==null){
			return defaultValue;
		}else{
			return v;
		}
	}
	
	public String getParameter(String name){
		String[] r=hParameters.get(name);
		if(r==null || r.length==0){
			return null;
		}else{
			return r[0];
		}
	}
	
	public String[] getParameterValues(String name){
		return hParameters.get(name);
	}
	 
}
