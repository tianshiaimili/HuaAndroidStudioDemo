package com.hua.bean;

import java.io.Serializable;


/**
 * 
 * 广告条
 *
 */

public class HeadLineImgBean implements Serializable {
	
//		id : "2"
//		title : "北京测试"
//		tid : "1369270"
//		to_url : "http://www.bjmama.com/thread-1369270-1-1.html"
//		is_jump : "0"
//		pic_url : "http://ios.gzmama.com/upload/2013/0122/Vb4UeffNCKDVseBpDTHo.jpg"
	private String id;
	private String title;
	private String tid;
	private String to_url;
	private String is_jump;
	private String pic_url;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getTo_url() {
		return to_url;
	}
	public void setTo_url(String to_url) {
		this.to_url = to_url;
	}
	public String getIs_jump() {
		return is_jump;
	}
	public void setIs_jump(String is_jump) {
		this.is_jump = is_jump;
	}
	public String getPic_url() {
		return pic_url;
	}
	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}
	
	
	

}
