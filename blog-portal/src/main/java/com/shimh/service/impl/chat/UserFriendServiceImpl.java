package com.shimh.service.impl.chat;


import com.shimh.dao.chat.UserFriendDao;
import com.shimh.entity.user.UserFriend;
import com.shimh.service.chat.UserFriendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserFriendServiceImpl implements UserFriendService {

    @Resource
    private UserFriendDao userFriendDao;

    @Override
    public List<UserFriend> listByUid(Long uid) {
        return userFriendDao.listByUid(uid);
    }

    @Override
    public UserFriend findByUidAndFriendUid(Long uid, Long friendUid) {
        return userFriendDao.findByUidAndFriendUid(uid, friendUid);
    }

    @Override
    public boolean insertUserFriendAll(List<UserFriend> userFriends) {
        return userFriendDao.insertUserFriendAll(userFriends);
    }

    @Override
    public boolean updateUserFriend(UserFriend userFriend) {
        userFriend.setModifiedTime(new Date());
        return userFriendDao.updateUserFriend(userFriend);
    }

    @Override
    public boolean deleteByUidAndFriendUid(Long uid, Long friendUid) {
        return userFriendDao.deleteByUidAndFriendUid(uid, friendUid);
    }
}
