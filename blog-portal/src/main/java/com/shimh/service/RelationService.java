package com.shimh.service;

import com.shimh.entity.relation.FacadeRelation;
import com.shimh.entity.relation.Follow;

import java.util.List;

/**
 * Created by zn on 2020/3/5.
 */
public interface RelationService {


    /**
     * 获取粉丝分页列表
     *
     * @param uid
     * @return
     */
    List<FacadeRelation> queryFansList(long selfUid, long uid, int breakPoint, int count);

    /**
     * 获取关注分页列表
     *
     * @param uid
     * @return
     */
    List<FacadeRelation> queryFollowList(long selfUid, long uid, int breakPoint, int count);


    /**
     * 关注
     * @param uid
     * @param fansUid
     * @return
     */
    Follow subscribe(long uid, long fansUid);

    /**
     * 取关
     * @param uid
     * @param toUid
     * @return
     */
    Follow unSubscribe(long uid, long toUid);



    /**
     * 查询和其他人的关注关系
     * @param selfUid 用户uid
     * @param uids 他人uid
     * @return
     */
    List<Follow> selectByFansUids(long selfUid, List<Long> uids);

}
