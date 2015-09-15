package com.hua.bean;

import java.io.Serializable;
import java.util.List;


/**
 * @author tzc
 *         <p/>
 *         帖子列表实例
 */
public class PostsListBean extends GDTnTanxADBean implements Serializable {
//		tid : "470052"
//		fid : "67"
//		subject : "提醒各位准备怀孕的妈妈们毋必做优生四项检查！"
//		typeid : "0"
//		author : "乾妈妈"
//		authorid : "13246"
//		dateline : "1204867229"
//		lastpost : "1204868121"
//		views : "811"
//		replies : "5"
//		attachment : "0"
//		closed : "0"   
//		status : "64"
//		digest : "0" 1 精
//		heats : "75" >100 hot


    private String tid;

    public PostsListBean() {
        super();
    }

    public PostsListBean(String tid, String fid, String subject, String typeid,
                         String author, String authorid, String dateline, String lastpost,
                         String views, String replies, String attachment, String closed,
                         String status, String pic, String digest, String heats) {
        super();
        this.tid = tid;
        this.fid = fid;
        this.subject = subject;
        this.typeid = typeid;
        this.author = author;
        this.authorid = authorid;
        this.dateline = dateline;
        this.lastpost = lastpost;
        this.views = views;
        this.replies = replies;
        this.attachment = attachment;
        this.closed = closed;
        this.status = status;
        this.pic = pic;
        this.digest = digest;
        this.heats = heats;
    }

    private String fid;
    private String subject;
    private String typeid;
    private String author;
    private String authorid;
    private String dateline;
    private String lastpost;
    private String views;
    private String replies;
    private String attachment;
    private String closed;
    private String status;
    private String pic;
    private String digest;
    private String heats;
    private String bb_birthday;
    private String newer;
    private String displayorder;
    private String group_pic;

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getLastpost() {
        return lastpost;
    }

    public void setLastpost(String lastpost) {
        this.lastpost = lastpost;
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

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getClosed() {
        return closed;
    }

    public void setClosed(String closed) {
        this.closed = closed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getHeats() {
        return heats;
    }

    public void setHeats(String heats) {
        this.heats = heats;
    }

    public String getBb_birthday() {
        return bb_birthday;
    }

    public void setBb_birthday(String bb_birthday) {
        this.bb_birthday = bb_birthday;
    }

    public String getNewer() {
        return newer;
    }

    public void setNewer(String newer) {
        this.newer = newer;
    }

    public String getDisplayorder() {
        return displayorder;
    }

    public void setDisplayorder(String displayorder) {
        this.displayorder = displayorder;
    }

    public String getGroup_pic() {
        return group_pic;
    }

    public void setGroup_pic(String group_pic) {
        this.group_pic = group_pic;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getAttachedimages() {
        return attachedimages;
    }

    public void setAttachedimages(List<String> attachedimages) {
        this.attachedimages = attachedimages;
    }

    private List<String> attachedimages;

}
