package com.shimh.entity.relation;

import java.io.Serializable;
import java.util.Date;

public class FacadeRelation implements Serializable {

    private static final long serialVersionUID = 5618993736123208868L;

    public FacadeRelation() {
    }

    public FacadeRelation(String nickname, String avatar, long uid, Integer relationType, Date createdTime, Date updatedTime) {
        this.nickname = nickname;
        this.avatar = avatar;
        this.uid = uid;
        this.relationType = relationType;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    /**
     * 昵称
     */
    private String nickname = "";

    /**
     * 用户头像,为完整地址,返回尺寸为120x120, 原尺寸头像地址请把"_120"替换成"_original"
     */
    private String avatar = "";

    /**
     * 关系人uid
     */
    private long uid=-1L;

    /**
     * 关系类型 1 无  2 以关注 3 互相关注
     */
    private Integer relationType;

    private Date createdTime;

    private Date updatedTime;

    public long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getRelationType() {
        return relationType;
    }

    public void setRelationType(Integer relationType) {
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


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "FacadeFollow{" +
                "nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", uid=" + uid +
                ", relationType=" + relationType +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
