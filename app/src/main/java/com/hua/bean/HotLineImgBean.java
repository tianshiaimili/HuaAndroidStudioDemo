package com.hua.bean;



/**
 * 
 * @author wj
 * 广告条
 *
 */

public class HotLineImgBean extends AdBean{
	
//	tid	 焦点图对应帖子id
//	subject	 标题
//	fid	 板块的fid
//	siteflag	 (bj=北京妈妈网, gz=广州妈妈网, tj=天津妈妈网, cs=长沙妈妈网, jn=济南妈妈网,
//					cq=重庆妈妈网, sy=沈阳妈妈网 ,qd=青岛妈妈网, sz=深圳妈妈网, xa=西安妈妈网 ， mmq=妈妈圈)
//	phonepic	 图片地址
	private String subject;
	private String phonepic;
	private String isad;//1：广告 0：焦点图
	

	 
	public String getIsad() {
		return isad;
	}
	public void setIsad(String isad) {
		this.isad = isad;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	 
	 
	public String getPhonepic() {
		return phonepic;
	}
	public void setPhonepic(String phonepic) {
		this.phonepic = phonepic;
	}
	
	
	

}
