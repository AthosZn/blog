package com.shimh.entity;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shimh.common.entity.BaseEntity;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Date;

/**
 * 用户
 *
 * @author shimh
 * <p>
 * 2018年1月23日
 */
@Entity
@Table(name = "sys_user")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class User  extends BaseEntity<Long> implements UserDetails {
    /**
     *
     */
    private static final long serialVersionUID = -4454737765850239378L;


    @NotBlank
    @Column(name = "account", unique = true, length = 10)
    private String account;

    /**
     * 使用md5(username + original password + salt)加密存储
     */
    @NotEmpty
    @Column(name = "password", length = 64)
    private String password;

    /**
     * 头像
     */
    private String avatar;

    @Column(name = "email", unique = true, length = 20)
    private String email;  // 邮箱

    @NotBlank
    @Column(name = "nickname", length = 10)
    private String nickname;

    @Column(name = "mobile_phone_number", length = 20)
    private String mobilePhoneNumber;


    /**
     * 加密密码时使用的种子
     */
    private String salt;


    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;


    /**
     * 最后一次登录时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;

    /**
     * 系统用户的状态
     */
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.normal;

    /**
     * 好友请求数量
     */
    @Column(name = "friend_ask_count")
    private Integer friendAskCount;

    /**
     * 好友数量
     */
    @Column(name = "friend_count")
    private Integer friendCount;

    /**
     * 是否是管理员
     */
    private Boolean admin = false;

    /**
     * 逻辑删除flag
     */
    private Boolean deleted = Boolean.FALSE;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return account;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Integer getFriendAskCount() {
        return friendAskCount;
    }

    public void setFriendAskCount(Integer friendAskCount) {
        this.friendAskCount = friendAskCount;
    }

    public Integer getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(Integer friendCount) {
        this.friendCount = friendCount;
    }

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", mobilePhoneNumber='" + mobilePhoneNumber + '\'' +
                ", salt='" + salt + '\'' +
                ", createDate=" + createDate +
                ", lastLogin=" + lastLogin +
                ", status=" + status +
                ", friendAskCount=" + friendAskCount +
                ", friendCount=" + friendCount +
                ", admin=" + admin +
                ", deleted=" + deleted +
                '}';
    }
}
