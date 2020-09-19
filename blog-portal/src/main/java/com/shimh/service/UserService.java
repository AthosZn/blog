package com.shimh.service;

import com.shimh.entity.User;

import java.util.List;

/**
 * @author shimh
 * <p>
 * 2018年1月23日
 */
public interface UserService {

    List<User> findAll();

    User getUserByAccount(String account);

    User getUserById(Long id);

    Long saveUser(User user);

    Long updateUser(User user);

    boolean updateUserAckCount(User user);

    void updateUserCountAll(List<User> user);

    void deleteUserById(Long id);

    User findByAcountAndMobilePhoneNumber(String account,String mobilePhoneNumber);

    List<User> findAllBYId(List<Long> uids);
}
