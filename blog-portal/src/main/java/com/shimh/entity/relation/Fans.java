package com.shimh.entity.relation;

import java.io.Serializable;
import java.util.Date;

public class Fans implements Serializable {

    private static final long serialVersionUID = 2314669034682793526L;

    public Fans() {

    }

    public Fans(long uid, long fansUid, int relationType) {
        Date now = new Date();
        this.uid = uid;
        this.fansUid = fansUid;
        this.relationType = relationType;
        this.setCreatedTime(now);
        this.setUpdatedTime(now);
    }

    public Fans(int id, long uid, long fansUid, int relationType) {
        Date now = new Date();
        this.id = id;
        this.uid = uid;
        this.fansUid = fansUid;
        this.relationType = relationType;
        this.setUpdatedTime(now);
    }

    private int id;

    /**
     * 主播id
     */
    private long uid=-1L;

    /**
     * 粉丝id
     */
    private long fansUid=-1L;

    /**
     * 关系类型 1 粉丝  2 好友 粉丝表示被关注 好友表示双向关注
     */
    private int relationType=0;

    private Date createdTime;

    private Date updatedTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getFansUid() {
        return fansUid;
    }

    public void setFansUid(long fansUid) {
        this.fansUid = fansUid;
    }

    public int getRelationType() {
        return relationType;
    }

    public void setRelationType(int relationType) {
        this.relationType = relationType;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        return "Fans{" +
                "id=" + id +
                ", uid=" + uid +
                ", fansUid=" + fansUid +
                ", relationType=" + relationType +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
