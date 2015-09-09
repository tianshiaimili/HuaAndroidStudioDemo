package com.hua.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sundh on 2015/8/10.
 */
public class SquareBean2 implements Serializable {
	/**
	 * data : {"isFirst":1,"total":0,"stat":[],"ad":{"data":{"monitor_ios":[
	 * "http://as.bjmama.net/v.php?k=ca97c5742da75f2c&p=1036_4372_43_12_826_11764"
	 * ],"subject":"宝宝0-4个月","link":"http://www.xiaoshuxiong.com/mobile","icon":
	 * "http://image-qzone.mamaquan.mama.cn/upload/2015/04/16/thumb_w160_552f5694dad69.jpg"
	 * ,"click_monitor_ios":
	 * "http://as.bjmama.net/c.php?k=df0ff34d4594caa3&p=1036_4372_43_12_826_11764"
	 * ,"monitor":[
	 * "http://as.bjmama.net/v.php?k=4b13a03b6f3b3f67&p=1036_4372_43_12_826_11743"
	 * ],"pushtime":"1428051523","click_monitor":
	 * "http://as.bjmama.net/c.php?k=a7b0d933384d1956&p=1036_4372_43_12_826_11743"
	 * ,"hits":0,"pic1":
	 * "http://image-qzone.mamaquan.mama.cn/upload/2015/04/16/thumb_w200_552f5694a3afd.jpg"
	 * ,"siteflag":"ad","pic2":
	 * "http://image-qzone.mamaquan.mama.cn/upload/2015/04/16/thumb_w200_552f5694ad854.png"
	 * ,"brand":"宝宝0-4个月","pic3":
	 * "http://image-qzone.mamaquan.mama.cn/upload/2015/04/16/thumb_w200_552f5694c83fc.jpg"
	 * },"position1":0,"position2":0},"dateline":1439176401,"threads":[],"video"
	 * :{"data":{"dt":"1438582470","adType":2,"view":4,"exposureLink":"","thumb"
	 * :
	 * "http://mobile.ad.mama.cn/upload//video/2015/08/03/thumb_d179cf90745f7a28239e5b376134c28f_w1440X900.jpg"
	 * ,"columnId":"1","icon":
	 * "http://mobile.ad.mama.cn/upload//video/2015/08/04/5e32b0ea50ebf3240af8c710a81631a5_w50X50.gif"
	 * ,"id":"5","tag":"爆笑不NG","title":"宝宝0-1岁宝宝0-1岁宝宝0-1岁宝宝0-1岁宝宝0-1岁","url":
	 * "http://mobile.ad.mama.cn/wap/ad/detail"
	 * },"position1":0,"position2":0},"cb"
	 * :"http://bc.ad.bjmama.net/www/ad_scheduling/api/callback.php"} errmsg :
	 * {"msg":"加载成功","errno":"0"} status : 1
	 */
	/**
	 * isFirst : 1 total : 0 stat : [] ad : {"data":{"monitor_ios":[
	 * "http://as.bjmama.net/v.php?k=ca97c5742da75f2c&p=1036_4372_43_12_826_11764"
	 * ],"subject":"宝宝0-4个月","link":"http://www.xiaoshuxiong.com/mobile","icon":
	 * "http://image-qzone.mamaquan.mama.cn/upload/2015/04/16/thumb_w160_552f5694dad69.jpg"
	 * ,"click_monitor_ios":
	 * "http://as.bjmama.net/c.php?k=df0ff34d4594caa3&p=1036_4372_43_12_826_11764"
	 * ,"monitor":[
	 * "http://as.bjmama.net/v.php?k=4b13a03b6f3b3f67&p=1036_4372_43_12_826_11743"
	 * ],"pushtime":"1428051523","click_monitor":
	 * "http://as.bjmama.net/c.php?k=a7b0d933384d1956&p=1036_4372_43_12_826_11743"
	 * ,"hits":0,"pic1":
	 * "http://image-qzone.mamaquan.mama.cn/upload/2015/04/16/thumb_w200_552f5694a3afd.jpg"
	 * ,"siteflag":"ad","pic2":
	 * "http://image-qzone.mamaquan.mama.cn/upload/2015/04/16/thumb_w200_552f5694ad854.png"
	 * ,"brand":"宝宝0-4个月","pic3":
	 * "http://image-qzone.mamaquan.mama.cn/upload/2015/04/16/thumb_w200_552f5694c83fc.jpg"
	 * },"position1":0,"position2":0} dateline : 1439176401 threads : [] video :
	 * {"data":{"dt":"1438582470","adType":2,"view":4,"exposureLink":"","thumb":
	 * "http://mobile.ad.mama.cn/upload//video/2015/08/03/thumb_d179cf90745f7a28239e5b376134c28f_w1440X900.jpg"
	 * ,"columnId":"1","icon":
	 * "http://mobile.ad.mama.cn/upload//video/2015/08/04/5e32b0ea50ebf3240af8c710a81631a5_w50X50.gif"
	 * ,"id":"5","tag":"爆笑不NG","title":"宝宝0-1岁宝宝0-1岁宝宝0-1岁宝宝0-1岁宝宝0-1岁","url":
	 * "http://mobile.ad.mama.cn/wap/ad/detail"},"position1":0,"position2":0} cb
	 * : http://bc.ad.bjmama.net/www/ad_scheduling/api/callback.php
	 */
	private int isFirst;
	private int total;
	private List<String> stat;
	private AdEntity ad;
	private int dateline;
	private List<SquareListBean> threads;
	private VideoEntity video;
	private String cb;

	public void setIsFirst(int isFirst) {
		this.isFirst = isFirst;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setStat(List<String> stat) {
		this.stat = stat;
	}

	public void setAd(AdEntity ad) {
		this.ad = ad;
	}

	public void setDateline(int dateline) {
		this.dateline = dateline;
	}

	public void setThreads(List<SquareListBean> threads) {
		this.threads = threads;
	}

	public void setVideo(VideoEntity video) {
		this.video = video;
	}

	public void setCb(String cb) {
		this.cb = cb;
	}

	public int getIsFirst() {
		return isFirst;
	}

	public int getTotal() {
		return total;
	}

	public List<String> getStat() {
		return stat;
	}

	public AdEntity getAd() {
		return ad;
	}

	public int getDateline() {
		return dateline;
	}

	public List<SquareListBean> getThreads() {
		return threads;
	}

	public VideoEntity getVideo() {
		return video;
	}

	public String getCb() {
		return cb;
	}

	public static class SquareListBean extends GDTnTanxADBean {
		private String tid;// 主贴id
		private String fid;
		private String siteflag;// 城市标记类型 bh=j,gz,cs等等
		private String avatar;// 用户头像
		private String subject;// 标题
		private String author;// 用户名
		private String bbinfo;// 用户宝宝信息
		private String views;// 浏览数
		private String replies;// 回复数
		private String lastpost;// 最后回复时间
		private String forumname;// 板块名字
		private String digest;// 是否是精华，1为精华，0为非精华
		private String authorid;// 会员id
		private String message;// 内容
		private String hot;// 是否为火贴。1为火。0为非火
		private List<String> attachedimage;// 图片数组

		// 小树熊添加的字段
		private String link;
		private String price;
		private String discount;
		private String pic1;
		private String pic2;
		private String pic3;
		private String icon;
		private String name;

		private ArrayList<String> monitor;
		private String click_monitor;

		private String star_icon;// 达人标签

		public String getTid() {
			return tid;
		}

		public void setTid(String tid) {
			this.tid = tid;
		}

		public String getFid() {
			return fid;
		}

		public void setFid(String fid) {
			this.fid = fid;
		}

		public String getSiteflag() {
			return siteflag;
		}

		public void setSiteflag(String siteflag) {
			this.siteflag = siteflag;
		}

		public String getAvatar() {
			return avatar;
		}

		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public String getBbinfo() {
			return bbinfo;
		}

		public void setBbinfo(String bbinfo) {
			this.bbinfo = bbinfo;
		}

		public String getViews() {
			return views;
		}

		public void setViews(String views) {
			this.views = views;
		}

		public String getReplies() {
			return replies;
		}

		public void setReplies(String replies) {
			this.replies = replies;
		}

		public String getLastpost() {
			return lastpost;
		}

		public void setLastpost(String lastpost) {
			this.lastpost = lastpost;
		}

		public String getForumname() {
			return forumname;
		}

		public void setForumname(String forumname) {
			this.forumname = forumname;
		}

		public String getDigest() {
			return digest;
		}

		public void setDigest(String digest) {
			this.digest = digest;
		}

		public String getAuthorid() {
			return authorid;
		}

		public void setAuthorid(String authorid) {
			this.authorid = authorid;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getHot() {
			return hot;
		}

		public void setHot(String hot) {
			this.hot = hot;
		}

		public List<String> getAttachedimage() {
			return attachedimage;
		}

		public void setAttachedimage(List<String> attachedimage) {
			this.attachedimage = attachedimage;
		}

		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public String getDiscount() {
			return discount;
		}

		public void setDiscount(String discount) {
			this.discount = discount;
		}

		public String getPic1() {
			return pic1;
		}

		public void setPic1(String pic1) {
			this.pic1 = pic1;
		}

		public String getPic2() {
			return pic2;
		}

		public void setPic2(String pic2) {
			this.pic2 = pic2;
		}

		public String getPic3() {
			return pic3;
		}

		public void setPic3(String pic3) {
			this.pic3 = pic3;
		}

		public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public ArrayList<String> getMonitor() {
			return monitor;
		}

		public void setMonitor(ArrayList<String> monitor) {
			this.monitor = monitor;
		}

		public String getClick_monitor() {
			return click_monitor;
		}

		public void setClick_monitor(String click_monitor) {
			this.click_monitor = click_monitor;
		}

		public String getStar_icon() {
			return star_icon;
		}

		public void setStar_icon(String star_icon) {
			this.star_icon = star_icon;
		}
	}

	public static class AdEntity extends SquareListBean implements Serializable {
		/**
		 * data : {"monitor_ios":[
		 * "http://as.bjmama.net/v.php?k=ca97c5742da75f2c&p=1036_4372_43_12_826_11764"
		 * ],"subject":"宝宝0-4个月","link":"http://www.xiaoshuxiong.com/mobile",
		 * "icon":
		 * "http://image-qzone.mamaquan.mama.cn/upload/2015/04/16/thumb_w160_552f5694dad69.jpg"
		 * ,"click_monitor_ios":
		 * "http://as.bjmama.net/c.php?k=df0ff34d4594caa3&p=1036_4372_43_12_826_11764"
		 * ,"monitor":[
		 * "http://as.bjmama.net/v.php?k=4b13a03b6f3b3f67&p=1036_4372_43_12_826_11743"
		 * ],"pushtime":"1428051523","click_monitor":
		 * "http://as.bjmama.net/c.php?k=a7b0d933384d1956&p=1036_4372_43_12_826_11743"
		 * ,"hits":0,"pic1":
		 * "http://image-qzone.mamaquan.mama.cn/upload/2015/04/16/thumb_w200_552f5694a3afd.jpg"
		 * ,"siteflag":"ad","pic2":
		 * "http://image-qzone.mamaquan.mama.cn/upload/2015/04/16/thumb_w200_552f5694ad854.png"
		 * ,"brand":"宝宝0-4个月","pic3":
		 * "http://image-qzone.mamaquan.mama.cn/upload/2015/04/16/thumb_w200_552f5694c83fc.jpg"
		 * } position1 : 0 position2 : 0
		 */
		private AdData data;
		private int position1;
		private int position2;

		public void setData(AdData data) {
			this.data = data;
		}

		public void setPosition1(int position1) {
			this.position1 = position1;
		}

		public void setPosition2(int position2) {
			this.position2 = position2;
		}

		public AdData getData() {
			return data;
		}

		public int getPosition1() {
			return position1;
		}

		public int getPosition2() {
			return position2;
		}

		public class AdData extends SquareListBean implements Serializable {
			/**
			 * monitor_ios : [
			 * "http://as.bjmama.net/v.php?k=ca97c5742da75f2c&p=1036_4372_43_12_826_11764"
			 * ] subject : 宝宝0-4个月 link : http://www.xiaoshuxiong.com/mobile
			 * icon : http://image-qzone.mamaquan.mama.cn/upload/2015/04/16/
			 * thumb_w160_552f5694dad69.jpg click_monitor_ios :
			 * http://as.bjmama.
			 * net/c.php?k=df0ff34d4594caa3&p=1036_4372_43_12_826_11764 monitor
			 * : [
			 * "http://as.bjmama.net/v.php?k=4b13a03b6f3b3f67&p=1036_4372_43_12_826_11743"
			 * ] pushtime : 1428051523 click_monitor :
			 * http://as.bjmama.net/c.php
			 * ?k=a7b0d933384d1956&p=1036_4372_43_12_826_11743 hits : 0 pic1 :
			 * http://image-qzone.mamaquan.mama.cn/upload/2015/04/16/
			 * thumb_w200_552f5694a3afd.jpg siteflag : ad pic2 :
			 * http://image-qzone
			 * .mamaquan.mama.cn/upload/2015/04/16/thumb_w200_552f5694ad854.png
			 * brand : 宝宝0-4个月 pic3 :
			 * http://image-qzone.mamaquan.mama.cn/upload/
			 * 2015/04/16/thumb_w200_552f5694c83fc.jpg
			 */
			private List<String> monitor_ios;
			// private String subject;
			// private String link;
			// private String icon;
			private String click_monitor_ios;
			// private ArrayList<String> monitor;
			private String pushtime;
			private int hits;
			// private String pic1;
			// private String siteflag;
			// private String pic2;
			private String brand;

			// private String pic3;

			public void setMonitor_ios(List<String> monitor_ios) {
				this.monitor_ios = monitor_ios;
			}

			// public void setSubject(String subject) {
			// this.subject = subject;
			// }
			//
			// public void setLink(String link) {
			// this.link = link;
			// }
			//
			// public void setIcon(String icon) {
			// this.icon = icon;
			// }

			public void setClick_monitor_ios(String click_monitor_ios) {
				this.click_monitor_ios = click_monitor_ios;
			}

			// public void setMonitor(ArrayList<String> monitor) {
			// this.monitor = monitor;
			// }

			public void setPushtime(String pushtime) {
				this.pushtime = pushtime;
			}

			public void setHits(int hits) {
				this.hits = hits;
			}

			// public void setPic1(String pic1) {
			// this.pic1 = pic1;
			// // }

			// public void setSiteflag(String siteflag) {
			// this.siteflag = siteflag;
			// }

			// public void setPic2(String pic2) {
			// this.pic2 = pic2;
			// }

			public void setBrand(String brand) {
				this.brand = brand;
			}

			// public void setPic3(String pic3) {
			// this.pic3 = pic3;
			// }

			public List<String> getMonitor_ios() {
				return monitor_ios;
			}

			// public String getSubject() {
			// return subject;
			// }
			//
			// public String getLink() {
			// return link;
			// }
			//
			// public String getIcon() {
			// return icon;
			// }

			public String getClick_monitor_ios() {
				return click_monitor_ios;
			}

			// public ArrayList<String> getMonitor() {
			// return monitor;
			// }

			public String getPushtime() {
				return pushtime;
			}

			public int getHits() {
				return hits;
			}

			// public String getPic1() {
			// return pic1;
			// }

			// public String getSiteflag() {
			// return siteflag;
			// }

			// public String getPic2() {
			// return pic2;
			// }

			public String getBrand() {
				return brand;
			}

			// public String getPic3() {
			// return pic3;
			// }
		}
	}

	public static class VideoEntity extends SquareListBean implements
			Serializable {
		/**
		 * data :
		 * {"dt":"1438582470","adType":2,"view":4,"exposureLink":"","thumb":
		 * "http://mobile.ad.mama.cn/upload//video/2015/08/03/thumb_d179cf90745f7a28239e5b376134c28f_w1440X900.jpg"
		 * ,"columnId":"1","icon":
		 * "http://mobile.ad.mama.cn/upload//video/2015/08/04/5e32b0ea50ebf3240af8c710a81631a5_w50X50.gif"
		 * ,"id":"5","tag":"爆笑不NG","title":"宝宝0-1岁宝宝0-1岁宝宝0-1岁宝宝0-1岁宝宝0-1岁",
		 * "url":"http://mobile.ad.mama.cn/wap/ad/detail"} position1 : 0
		 * position2 : 0
		 */
		private VidioData data;
		private int position1;
		private int position2;

		public void setData(VidioData data) {
			this.data = data;
		}

		public void setPosition1(int position1) {
			this.position1 = position1;
		}

		public void setPosition2(int position2) {
			this.position2 = position2;
		}

		public VidioData getData() {
			return data;
		}

		public int getPosition1() {
			return position1;
		}

		public int getPosition2() {
			return position2;
		}

		public class VidioData extends SquareListBean implements Serializable {
			/**
			 * dt : 1438582470 adType : 2 view : 4 exposureLink : thumb :
			 * http://mobile.ad.mama.cn/upload//video/2015/08/03/
			 * thumb_d179cf90745f7a28239e5b376134c28f_w1440X900.jpg columnId : 1
			 * icon : http://mobile.ad.mama.cn/upload//video/2015/08/04/5e32
			 * b0ea50ebf3240af8c710a81631a5_w50X50.gif id : 5 tag : 爆笑不NG title
			 * : 宝宝0-1岁宝宝0-1岁宝宝0-1岁宝宝0-1岁宝宝0-1岁 url :
			 * http://mobile.ad.mama.cn/wap/ad/detail
			 */
			private String dt;
			private int adType;
			private int view;
			private String exposureLink;
			private String thumb;
			private String columnId;
			// private String icon;
			private String id;
			private String tag;
			private String title;
			private String url;
			private String click_count;

			private ArrayList<String> pv_code;
			private ArrayList<String> pv_code_extra;

			public String getClick_count() {
				return click_count;
			}

			public void setClick_count(String click_count) {
				this.click_count = click_count;
			}

			public ArrayList<String> getPv_code() {
				return pv_code;
			}

			public void setPv_code(ArrayList<String> pv_code) {
				this.pv_code = pv_code;
			}

			public ArrayList<String> getPv_code_extra() {
				return pv_code_extra;
			}

			public void setPv_code_extra(ArrayList<String> pv_code_extra) {
				this.pv_code_extra = pv_code_extra;
			}

			public void setDt(String dt) {
				this.dt = dt;
			}

			public void setAdType(int adType) {
				this.adType = adType;
			}

			public void setView(int view) {
				this.view = view;
			}

			public void setExposureLink(String exposureLink) {
				this.exposureLink = exposureLink;
			}

			public void setThumb(String thumb) {
				this.thumb = thumb;
			}

			public void setColumnId(String columnId) {
				this.columnId = columnId;
			}

			// public void setIcon(String icon) {
			// this.icon = icon;
			// }

			public void setId(String id) {
				this.id = id;
			}

			public void setTag(String tag) {
				this.tag = tag;
			}

			public void setTitle(String title) {
				this.title = title;
			}

			public void setUrl(String url) {
				this.url = url;
			}

			public String getDt() {
				return dt;
			}

			public int getAdType() {
				return adType;
			}

			public int getView() {
				return view;
			}

			public String getExposureLink() {
				return exposureLink;
			}

			public String getThumb() {
				return thumb;
			}

			public String getColumnId() {
				return columnId;
			}

			// public String getIcon() {
			// return icon;
			// }

			public String getId() {
				return id;
			}

			public String getTag() {
				return tag;
			}

			public String getTitle() {
				return title;
			}

			public String getUrl() {
				return url;
			}
		}
	}

}
