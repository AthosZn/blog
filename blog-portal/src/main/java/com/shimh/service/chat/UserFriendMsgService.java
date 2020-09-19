package com.shimh.service.chat;

import com.shimh.entity.user.UserFriendMsg;

import java.util.List;

public interface UserFriendMsgService {

    List<UserFriendMsg> listByUidAndToUid(Long uid, Long toUid, Integer offset, Integer limit);

    boolean insertUserFriendMsg(UserFriendMsg userFriendMsg);

}
