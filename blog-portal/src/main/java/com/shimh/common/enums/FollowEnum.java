package com.shimh.common.enums;

import java.util.Objects;

/**
 * 粉丝关系类型
 * Created by zn on 2020/3/6.
 */
public enum FollowEnum {

    NON_FOLLOW(0, "未关注"),
    /**
     * 被关注
     */
    FOLLOW(1, "被关注"),

    /**
     * 好友
     */
    FRIEND(2, "好友");

    /**
     * id
     */
    private int key;

    /**
     * 描述
     */
    private String desc;

    FollowEnum(int key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public int getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 根据key找到对应的Enum值
     */
    public static FollowEnum getEnum(int key) {
        FollowEnum[] values = FollowEnum.values();
        for (FollowEnum value : values) {
            if (Objects.equals(value.getKey(), key)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "FollowEnum{" +
                "key=" + key +
                ", desc='" + desc + '\'' +
                '}';
    }
}
