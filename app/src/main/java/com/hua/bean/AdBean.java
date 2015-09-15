package com.hua.bean;

import java.io.Serializable;

public class AdBean implements Serializable{
	//		id : "14868"
//			type	 广告跳转类型，1=跳转到圈子列表页面，2=跳转到帖子详情页面，
//			3=跳转到我的圈子页面，4=跳转到我的页面，5=跳转到热点页面，6=调整到同城页面,7=外部链接
//			siteflag	 和type配合使用，调整到那个城市下面的帖子详情获或列表页面
//			siteflag=mmq , gz, sy 等
//			starttime	 广告投放开始时间
//			siteflag	 和type配合使用，调整到那个城市下面的帖子详情获或列表页面
//			endtime	 广告投放结束时间
//			fid	 圈子id，和type，siteflag配合使用，跳转到那个具体的圈子
//			tid	 帖子id，和type配合使用，跳转到那个帖子
//			pv_code	 广告pv记录地址，每次浏览调用一次
//			click_code	 记录广告点击数的url，每次点击客户端调用一次
//			adlink	 广告外链地址，只有type=7时，此参数才有用
//			attrcontent	 广告素材，是图片地址，还是文字说明
//			attrtype	 广告类型，img=图片，txt=文本
//			status	 广告状态 0=正常，1=暂停
//			order	 广告排序，由1开始，越大越后面
	private String id;
	private String adid;
	private String adfid;
	private String fname;
	private String type;
	private String siteflag;
	private String starttime;
	private String endtime;
	private String fid;
	private String tid;
	private String pv_code;
	private String click_code;
	private String adlink;
	private String attrcontent;
	private String attrtype;
	private String status;
	private String order;
	private String pv_code_extra;
	private String ad_url;
	private String click_code_extra;

	public String getAd_url() {
		return ad_url;
	}

	public void setAd_url(String ad_url) {
		this.ad_url = ad_url;
	}

	public String getPv_code_extra() {
		return pv_code_extra;
	}
	public void setPv_code_extra(String pv_code_extra) {
		this.pv_code_extra = pv_code_extra;
	}
	public String getClick_code_extra() {
		return click_code_extra;
	}
	public void setClick_code_extra(String click_code_extra) {
		this.click_code_extra = click_code_extra;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSiteflag() {
		return siteflag;
	}
	public void setSiteflag(String siteflag) {
		this.siteflag = siteflag;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getPv_code() {
		return pv_code;
	}
	public void setPv_code(String pv_code) {
		this.pv_code = pv_code;
	}
	public String getClick_code() {
		return click_code;
	}
	public void setClick_code(String click_code) {
		this.click_code = click_code;
	}
	public String getAdlink() {
		return adlink;
	}
	public void setAdlink(String adlink) {
		this.adlink = adlink;
	}
	public String getAttrcontent() {
		return attrcontent;
	}
	public void setAttrcontent(String attrcontent) {
		this.attrcontent = attrcontent;
	}
	public String getAttrtype() {
		return attrtype;
	}
	public void setAttrtype(String attrtype) {
		this.attrtype = attrtype;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAdid() {
		return adid;
	}
	public void setAdid(String adid) {
		this.adid = adid;
	}
	public String getAdfid() {
		return adfid;
	}
	public void setAdfid(String adfid) {
		this.adfid = adfid;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public AdBean(String id, String adid, String adfid, String fname,
				  String type, String siteflag, String starttime, String endtime,
				  String fid, String tid, String pv_code, String click_code,
				  String adlink, String attrcontent, String attrtype, String status,
				  String order) {
		super();
		this.id = id;
		this.adid = adid;
		this.adfid = adfid;
		this.fname = fname;
		this.type = type;
		this.siteflag = siteflag;
		this.starttime = starttime;
		this.endtime = endtime;
		this.fid = fid;
		this.tid = tid;
		this.pv_code = pv_code;
		this.click_code = click_code;
		this.adlink = adlink;
		this.attrcontent = attrcontent;
		this.attrtype = attrtype;
		this.status = status;
		this.order = order;
	
	}
	public AdBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
