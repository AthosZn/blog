package com.shimh.service.impl.chat;

import com.shimh.dao.chat.UserFriendMsgDao;
import com.shimh.entity.user.UserFriendMsg;
import com.shimh.service.chat.UserFriendMsgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserFriendMsgServiceImpl implements UserFriendMsgService {

    @Resource
    private UserFriendMsgDao userFriendMsgDao;

    @Override
    public List<UserFriendMsg> listByUidAndToUid(Long uid, Long toUid, Integer offset, Integer limit) {
        return userFriendMsgDao.listByUidAndToUid(uid, toUid, offset, limit);
    }

    @Override
    public boolean insertUserFriendMsg(UserFriendMsg userFriendMsg) {
        userFriendMsg.setCreateTime(new Date());
        return userFriendMsgDao.insertUserFriendMsg(userFriendMsg);
    }
}
