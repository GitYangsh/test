package com.jy.app.market.idata.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PushTag implements Serializable{ 
	private static final long serialVersionUID = 548806411310064004L;
	
	private List<String> tags=new ArrayList<String>();

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	public void addTag(String tag){
		String x=formatTag(tag);
		if(x!=null && x.length()>0){
			tags.add(x);
		}
	}

	///字母（区分大小写）、数字、下划线、汉字
	public static String formatTag(String tag){
		if(tag==null || tag.trim().length()==0){
			return "";
		}else{
			StringBuffer sb=new StringBuffer();
			tag=tag.trim();			
			for(int i=0;i<tag.length();i++){
				char c=tag.charAt(i);
				
				if ((c >= 0x4e00) && (c <= 0x9fbb)){
					sb.append(c);
				}else if(c>='a' && c<='z'){
					sb.append(c);
				}else if(c>='A' && c<='Z'){
					sb.append(c);
				}else if(c>='0' && c<='9'){
					sb.append(c);
				}else if(c=='-' || c==':' || c=='\r' || c=='\n' || c=='\t'){
					sb.append("_");
				}
			}
			return sb.toString().toUpperCase();
		}
	}
}
