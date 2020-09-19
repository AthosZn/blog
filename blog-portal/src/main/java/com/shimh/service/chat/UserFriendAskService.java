package com.shimh.service.chat;

import com.shimh.entity.user.UserFriendAsk;

import java.util.List;

public interface UserFriendAskService {

    List<UserFriendAsk> listByUid(Long uid);

    UserFriendAsk findById(Long id);

    boolean insertUserFriendAsk(UserFriendAsk userFriendAsk);

    boolean updateUserFriendAsk(UserFriendAsk userFriendAsk);

    boolean deleteById(Long id);

}
