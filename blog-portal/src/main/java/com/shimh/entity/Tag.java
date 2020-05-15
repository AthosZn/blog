package com.shimh.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shimh.common.entity.BaseEntity;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 文章标签
 *
 * @author shimh
 * <p>
 * 2018年1月23日
 */
@Entity
@Table(name = "me_tag")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class Tag extends BaseEntity<Integer> {

    /**
     *
     */
    private static final long serialVersionUID = 5025313969040054182L;

    @NotBlank
    private String tagname;

    @NotBlank
    private String avatar;


    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


}
