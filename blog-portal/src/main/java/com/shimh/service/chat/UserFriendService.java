package com.shimh.service.chat;


import com.shimh.entity.user.UserFriend;

import java.util.List;

public interface UserFriendService {

    List<UserFriend> listByUid(Long uid);

    UserFriend findByUidAndFriendUid(Long uid, Long friendUid);

    boolean insertUserFriendAll(List<UserFriend> userFriends);

    boolean updateUserFriend(UserFriend userFriend);

    boolean deleteByUidAndFriendUid(Long uid, Long friendUid);

}
