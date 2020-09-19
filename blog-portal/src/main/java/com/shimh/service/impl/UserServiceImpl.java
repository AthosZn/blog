package com.shimh.service.impl;

import com.shimh.common.util.PasswordHelper;
import com.shimh.entity.User;
import com.shimh.mapper.user.SysUserMapper;
import com.shimh.repository.UserRepository;
import com.shimh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * @author shimh
 * <p>
 * 2018年1月23日
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Resource
    private SysUserMapper sysUserMapper;


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByAccount(String account) {
        return userRepository.findByAccount(account);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    @Transactional
    public Long saveUser(User user) {

        PasswordHelper.encryptPassword(user);
        int index = new Random().nextInt(6) + 1;
        String avatar = "/static/user/user_" + index + ".png";

        user.setAvatar(avatar);
        return userRepository.save(user).getId();
    }



    @Override
    @Transactional
    public Long updateUser(User user) {
        User oldUser = userRepository.getOne(user.getId());
        oldUser.setNickname(user.getNickname());

        return oldUser.getId();
    }

    /**
     * 清空好友请求数量
     * @param user
     * @return
     */
    @Override
    @Transactional
    public boolean updateUserAckCount(User user) {
        User oldUser = userRepository.getOne(user.getId());
        oldUser.setFriendAskCount(0);
        return true;
    }

    /**
     * 更新用户好友数量
     * @param userList
     * @return
     */
    @Override
    public void updateUserCountAll(List<User> userList) {
        sysUserMapper.updateUserCountAll(userList);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByAcountAndMobilePhoneNumber(String account,String mobilePhoneNumber){
        return userRepository.findByAccountAndMobilePhoneNumber(account,mobilePhoneNumber);
    }

    @Override
    public List<User> findAllBYId(List<Long> uids){
        return userRepository.findAllById(uids);
    }

}
