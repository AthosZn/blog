package com.shimh.entity.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Created by zn on 2020-04-09.
 */
@Data
public class RegisterParam implements Serializable {

    private static final long serialVersionUID = 8390466513572024229L;
    @NotEmpty
    private String account;

    /**
     * 使用md5(username + original password + salt)加密存储
     */
    @NotEmpty
    private String password;

    private String nickname;

    private String telephone;

    private String authCode;

}
