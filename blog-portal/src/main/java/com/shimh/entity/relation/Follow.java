package com.shimh.entity.relation;

import java.io.Serializable;
import java.util.Date;

public class Follow implements Serializable {

    private static final long serialVersionUID = -4561233450843428741L;

    public Follow() {

    }

    public Follow(long uid, long followUid, int relationType) {
        Date now = new Date();
        this.uid = uid;
        this.followUid = followUid;
        this.relationType = relationType;
        this.setCreatedTime(now);
        this.setUpdatedTime(now);
    }

    public Follow(int id, long uid, long followUid, int relationType) {
        Date now = new Date();
        this.id = id;
        this.uid = uid;
        this.followUid = followUid;
        this.relationType = relationType;
        this.setUpdatedTime(now);
    }

    private int id=-1;

    /**
     * uid
     */
    private long uid=-1L;

    /**
     * follow uid
     */
    private long followUid=-1L;

    /**
     * 关系类型 1 关注  2 好友 关注表示表示uid关注followuid 好友表示双向关注
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

    public long getFollowUid() {
        return followUid;
    }

    public void setFollowUid(long followUid) {
        this.followUid = followUid;
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
        return "Follow{" +
                "id=" + id +
                ", uid=" + uid +
                ", followUid=" + followUid +
                ", relationType=" + relationType +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
