package com.shimh.entity.relation;

import java.io.Serializable;

public class FacadeFriend implements Serializable {

    public FacadeFriend(){

    }

    public FacadeFriend(String nickname, String avatar, long uid) {
        this.nickname = nickname;
        this.avatar = avatar;
        this.uid = uid;
    }

    private static final long serialVersionUID = -7118171575095696474L;
    /**
     * 昵称
     */
    private String nickname = "";

    /**
     * 用户头像,为完整地址,返回尺寸为120x120, 原尺寸头像地址请把"_120"替换成"_original"
     */
    private String avatar = "";

    /**
     * friend uid
     */
    private long uid=-1;

    /**
     * 1好友 2 最近匹配
     */
    private int type=1;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
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
        return "FacadeFriend{" +
                "nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", uid=" + uid +
                ", type=" + type +
                '}';
    }
}
