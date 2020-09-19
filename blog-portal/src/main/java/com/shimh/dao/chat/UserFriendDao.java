package com.shimh.dao.chat;

import com.shimh.entity.user.UserFriend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserFriendDao {

    /**
     * 查询朋友列表
     * @return
     */
    List<UserFriend> listByUid(@Param("uid") Long uid);

    /**
     * 查询两个用户是否是朋友关系
     * @return
     */
    UserFriend findByUidAndFriendUid(@Param("uid") Long uid, @Param("friendUid") Long friendUid);

    /**
     * 插入
     * @param userFriends
     * @return
     */
    boolean insertUserFriendAll(List<UserFriend> userFriends);

    /**
     * 更新
     * @param userFriend
     * @return
     */
    boolean updateUserFriend(UserFriend userFriend);

    /**
     * 删除
     * @return
     */
    boolean deleteByUidAndFriendUid(@Param("uid") Long uid, @Param("friendUid") Long friendUid);

}
