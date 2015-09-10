package com.hua.bean;

import java.io.Serializable;

/**
 * 测试emojiico
 *
 */
public class DraftBean implements Serializable {
	private boolean update;//是否要更新
	private String content;
	private String title;
	private long time;//时间
 
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isUpdate() {
		return update;
	}
	public void setUpdate(boolean update) {
		this.update = update;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	 
}
