package com.hua.bean;

import java.io.Serializable;

/**
 * 
 * 城市Bean类
 *
 */
public class CityBean implements Serializable,Comparable{

	private String id;
	private String name;
	private String ele;//首字母
	
	public String getEle() {
		return ele;
	}
	public void setEle(String ele) {
		this.ele = ele;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return name ;
	}
	@Override
	public int compareTo(Object another) {
		char a = this.name.charAt(0);
		int b = (int)a;
		CityBean other = (CityBean) another;
		char c = other.getName().charAt(0);
		int d = (int)c;
		if(b>d){
			return 1;
		}else if(b<d){
			return -1;
		}else{
			return 0;
		}
		
	}

	
}
