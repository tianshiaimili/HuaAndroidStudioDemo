package com.hua.bean;

import java.io.Serializable;

public class RecommendMessageBean implements Serializable{
	
	
	String main_title;
	String desc_title;
	
	
	
	public RecommendMessageBean(String main_title, String desc_title) {
		super();
		this.main_title = main_title;
		this.desc_title = desc_title;
	}
	public String getMain_title() {
		return main_title;
	}
	public void setMain_title(String main_title) {
		this.main_title = main_title;
	}
	public String getDesc_title() {
		return desc_title;
	}
	public void setDesc_title(String desc_title) {
		this.desc_title = desc_title;
	}

}
