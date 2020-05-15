package com.shimh.dao;

import com.shimh.entity.relation.Fans;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface FansDao {

    int insert(Fans record);

    int update(Fans record);

    List<Fans> selectByUid(Long uid);

    int getFansCount(Long uid);

    List<Fans> getFansByPages(@Param("uid") Long uid, @Param("breakPoint") int breakPoint, @Param("count") int count);

    List<Fans> selectFriend(Long uid);

    List<Fans> selectFriendByPages(Long uid);

    List<Fans> selectFriendByUids(@Param("uids") List<Long> uids);

    Fans selectByUidAndFansUid(@Param("uid") Long uid, @Param("fansUid") Long fansUid);

    int getNewFansCount(@Param("uid") long uid, @Param("startTime") Date startTime);

    /**
     * 获取最新粉丝历史列表
     *
     * @param params {uid,start,pageSize,startTime}
     * @return 列表
     */
    List<Fans> getNewFanList(Map<String, Object> params);

}
