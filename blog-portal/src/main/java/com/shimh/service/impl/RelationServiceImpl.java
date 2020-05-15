package com.shimh.service.impl;


import com.macro.mall.common.exception.ApiException;
import com.shimh.common.enums.FansEnum;
import com.shimh.common.enums.FollowEnum;
import com.shimh.dao.FansDao;
import com.shimh.dao.FollowDao;
import com.shimh.entity.User;
import com.shimh.entity.relation.FacadeRelation;
import com.shimh.entity.relation.Fans;
import com.shimh.entity.relation.Follow;
import com.shimh.repository.UserRepository;
import com.shimh.service.RelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by zn on 2020/3/5.
 * 关系接口
 */
@Service
public class RelationServiceImpl implements RelationService {

    @Resource
    private FansDao fansDao;
    @Resource
    private FollowDao followDao;
    @Resource
    private UserRepository userRepository;

    @Override
    public List<FacadeRelation> queryFansList(long selfUid, long uid, int breakPoint, int count) {
        List<Fans> fansList = fansDao.getFansByPages(uid, breakPoint, count);
        List<Long> uidList = fansList.stream().map(Fans::getFansUid).collect(Collectors.toList());
        List<Follow> followList = followDao.selectByFollowUids(selfUid, uidList);
        Map<Long, Follow> followMap = followList.stream().collect(Collectors.toMap(Follow::getFollowUid, a -> a));
        List<FacadeRelation> facadeRelations = new ArrayList<>();
        for (Fans fans : fansList) {
            User user = userRepository.getOne(fans.getFansUid());
            int relation = followMap.get(fans.getFansUid()) == null ? 0 : followMap.get(fans.getFansUid()).getRelationType();
            FacadeRelation facadeRelation = new FacadeRelation(user.getNickname(), user.getAvatar(),
                    fans.getFansUid(), relation, fans.getCreatedTime(),
                    fans.getUpdatedTime());
            facadeRelations.add(facadeRelation);
        }
        return facadeRelations;
    }

    @Override
    public List<FacadeRelation> queryFollowList(long selfUid, long uid, int breakPoint, int count) {
        List<Follow> followList = followDao.selectFollowByPages(uid, breakPoint, count);
        Map<Long, Follow> followMap = new HashMap<>();
        if (uid != selfUid) {
            List<Long> uidList = followList.stream().map(Follow::getFollowUid).collect(Collectors.toList());
            List<Follow> selfFollowList = followDao.selectByFollowUids(selfUid, uidList);
            followMap = selfFollowList.stream().collect(Collectors.toMap(Follow::getFollowUid, a -> a));
        } else {
            followMap = followList.stream().collect(Collectors.toMap(Follow::getFollowUid, a -> a));
        }
        List<FacadeRelation> facadeRelations = new ArrayList<>();
        for (Follow follow : followList) {
            User user = userRepository.getOne(follow.getFollowUid());
            int relation = followMap.get(follow.getFollowUid()) == null ? 0 : followMap.get(follow.getFollowUid()).getRelationType();
            FacadeRelation facadeRelation = new FacadeRelation(user.getNickname(), user.getAvatar(),
                    follow.getFollowUid(), relation, follow.getCreatedTime(),
                    follow.getUpdatedTime());
            facadeRelations.add(facadeRelation);
        }
        return facadeRelations;
    }

    @Override
    public Follow subscribe(long uidA, long uidB) {
        if (uidA == uidB) {
            throw new ApiException("不能关注自己");
        }
        if (followDao.getFollowCount(uidA) > 999) {
            throw new ApiException("你关注的人太多，不能再关注TA了");
        }
        Follow followA2B = followDao.selectByUidAndFollowUid(uidA, uidB);
        Follow followB2A = followDao.selectByUidAndFollowUid(uidB, uidA);
        Fans fansB2A = fansDao.selectByUidAndFansUid(uidB, uidA);


        FansEnum fansEnumB2A = FansEnum.FANS;
        FollowEnum followEnumA2b = FollowEnum.FOLLOW;

        Follow result = new Follow(uidA, uidB, followEnumA2b.getKey());
        if (null != followB2A) {
            if (followB2A.getRelationType() != FollowEnum.NON_FOLLOW.getKey()) {
                followEnumA2b = FollowEnum.FRIEND;
                fansEnumB2A = FansEnum.FRIEND;
                followDao.update(new Follow(followB2A.getId(), uidB, uidA, followEnumA2b.getKey()));
                Fans fansA2B = fansDao.selectByUidAndFansUid(uidA, uidB);
                if (fansA2B == null) {
                    fansDao.insert(new Fans(uidA, uidB, fansEnumB2A.getKey()));
                } else {
                    fansDao.update(new Fans(fansA2B.getId(), uidA, uidB, fansEnumB2A.getKey()));
                }
            }
        }

        if (followA2B == null) {
            result.setRelationType(followEnumA2b.getKey());
            followDao.insert(result);
        } else {
            result.setCreatedTime(followA2B.getCreatedTime());
            result.setId(followA2B.getId());
            result.setRelationType(followEnumA2b.getKey());
            followDao.update(result);
        }

        if (fansB2A == null) {
            fansDao.insert(new Fans(uidB, uidA, fansEnumB2A.getKey()));
        } else {
            fansDao.update(new Fans(fansB2A.getId(), uidB, uidA, fansEnumB2A.getKey()));
        }
        return result;
    }


    @Override
    public Follow unSubscribe(long uidA, long uidB) {
        Follow followA2B = followDao.selectByUidAndFollowUid(uidA, uidB);
        //查询是否有toUid的fans
        Fans fansA2B = fansDao.selectByUidAndFansUid(uidA, uidB);
        Follow followB2A = followDao.selectByUidAndFollowUid(uidB, uidA);
        Fans fansB2A = fansDao.selectByUidAndFansUid(uidB, uidA);

        FollowEnum followRelation = FollowEnum.NON_FOLLOW;
        //如果touid的粉丝是uid
        if (null != fansB2A) {
            fansDao.update(new Fans(fansB2A.getId(), uidB, uidA, FansEnum.NON_FANS.getKey()));
        }
        Follow result = new Follow(uidA, uidB, followRelation.getKey());
        if (null != followA2B) {
            result.setId(followA2B.getId());
            result.setCreatedTime(followA2B.getCreatedTime());
            result.setRelationType(followRelation.getKey());
            followDao.update(result);
        }

        if (followB2A != null && followB2A.getRelationType() == FollowEnum.FRIEND.getKey()) {
            followDao.update(new Follow(followB2A.getId(), uidB, uidA, FollowEnum.FOLLOW.getKey()));
        }
        if (fansA2B != null && fansA2B.getRelationType() == FansEnum.FRIEND.getKey()) {
            fansDao.update(new Fans(fansA2B.getId(), uidA, uidB, FollowEnum.FOLLOW.getKey()));
        }

        return result;
    }

    @Override
    public List<Follow> selectByFansUids(long selfUid, List<Long> uids) {
        List<Follow> followList=  followDao.selectByFollowUids(selfUid,uids);
        return followList;
    }

}
