package com.hua.bean;

import java.io.Serializable;

public class User implements Serializable{

	private String id;
	private String user_name;
	
	public User(String id, String user_name) {
		super();
		this.id = id;
		this.user_name = user_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	
}
