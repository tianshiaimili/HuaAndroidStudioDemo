package com.hua.bean;


import java.util.List;

public class CirclePostListBean extends GDTnTanxADBean {

    private String tid;//帖子id
    private String fid;//妈妈圈圈子id
    private String siteflag;//网站标识, mmq=妈妈圈,gz=广妈,cs=长沙
    private String sfid;//同城的板块id
    private String subject;//标题
    private String author;//发帖人姓名
    private String authorid;//发帖人uid
    private String dateline;//发帖时间
    private String lastpost;//回复时间 : "1363832686"
    private String views;//帖子浏览数
    private String replies;//帖子回复数
    private String attachment;//是否有图片，(attachment=2 时表示有图片)
    private String digest;//是否是精华帖子 1=加精 0=普通
    private String heats;//是否是热贴， heats>=100 为热贴
    private String ishelp;//是否求助帖
    private String authorbbinfo;
    private String pic;
    private String displayorder;
    private String newer;
    private String group_pic;//身份图片Url
    private String message;
    private String isNewDigest;
    private List<String> attachedimages;

    public String getIsNewDigest() {
        return isNewDigest;
    }

    public void setIsNewDigest(String isNewDigest) {
        this.isNewDigest = isNewDigest;
    }

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

    public String getSfid() {
        return sfid;
    }

    public void setSfid(String sfid) {
        this.sfid = sfid;
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

    public String getIshelp() {
        return ishelp;
    }

    public void setIshelp(String ishelp) {
        this.ishelp = ishelp;
    }

    public String getAuthorbbinfo() {
        return authorbbinfo;
    }

    public void setAuthorbbinfo(String authorbbinfo) {
        this.authorbbinfo = authorbbinfo;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getLastpost() {
        return lastpost;
    }

    public void setLastpost(String lastpost) {
        this.lastpost = lastpost;
    }

    public String getDisplayorder() {
        return displayorder;
    }

    public void setDisplayorder(String displayorder) {
        this.displayorder = displayorder;
    }

    public String getNewer() {
        return newer;
    }

    public void setNewer(String newer) {
        this.newer = newer;
    }

    public String getGroup_pic() {
        return group_pic;
    }

    public void setGroup_pic(String group_pic) {
        this.group_pic = group_pic;
    }




}
