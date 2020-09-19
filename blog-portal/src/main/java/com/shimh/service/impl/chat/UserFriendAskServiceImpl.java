package com.shimh.service.impl.chat;

import com.shimh.dao.chat.UserFriendAskDao;
import com.shimh.entity.user.UserFriendAsk;
import com.shimh.service.chat.UserFriendAskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserFriendAskServiceImpl implements UserFriendAskService {

    @Resource
    private UserFriendAskDao userFriendAskDao;

    @Override
    public List<UserFriendAsk> listByUid(Long uid) {
        return userFriendAskDao.listByUid(uid);
    }

    @Override
    public UserFriendAsk findById(Long id) {
        return userFriendAskDao.findById(id);
    }

    @Override
    public boolean insertUserFriendAsk(UserFriendAsk userFriendAsk) {
        userFriendAsk.setCreateTime(new Date());
        userFriendAsk.setModifiedTime(new Date());
        return userFriendAskDao.insertUserFriendAsk(userFriendAsk);
    }

    @Override
    public boolean updateUserFriendAsk(UserFriendAsk userFriendAsk) {
        userFriendAsk.setModifiedTime(new Date());
        return userFriendAskDao.updateUserFriendAsk(userFriendAsk);
    }

    @Override
    public boolean deleteById(Long id) {
        return userFriendAskDao.deleteById(id);
    }
}
