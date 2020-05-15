package com.shimh.common.enums;

import java.util.Objects;

/**
 * 粉丝关系类型
 * Created by zn on 2020/3/6.
 */
public enum FansEnum {

    NON_FANS(0, ""),
    /**
     * 粉丝
     */
    FANS(1, "粉丝"),

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

    FansEnum(int key, String desc) {
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
    public static FansEnum getEnum(int key) {
        FansEnum[] values = FansEnum.values();
        for (FansEnum value : values) {
            if (Objects.equals(value.getKey(), key)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "FansEnum{" +
                "key=" + key +
                ", desc='" + desc + '\'' +
                '}';
    }
}
