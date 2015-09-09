package com.hua.bean;

import java.io.Serializable;
import java.util.List;

public class PostsRecommendBean implements Serializable{

	private String id;
	private List<String> recommend;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getRecommend() {
		return recommend;
	}
	public void setRecommend(List<String> recommend) {
		this.recommend = recommend;
	}
	
}
