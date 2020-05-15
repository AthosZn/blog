package com.shimh.dao;

import com.shimh.entity.relation.Follow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FollowDao {

    int insert(Follow record);

    int update(Follow record);

    List<Follow> selectByUid(Long uid);

    int getFollowCount(Long uid);

    List<Follow> selectByFollow(Long followUid);

    List<Follow> selectFollowByPages(@Param("uid") Long uid, @Param("breakPoint") int breakPoint, @Param("count") int count);

    Follow selectByUidAndFollowUid(@Param("uid") Long uid, @Param("followUid") Long followUid);

    List<Follow>  selectFriendByUid(Long uid);

    List<Follow> selectByFollowUids(@Param("uid") Long uid, @Param("followUids") List<Long> followUids);

}
