package com.shimh.controller;


import com.shimh.common.result.Result;
import com.shimh.entity.User;
import com.shimh.entity.relation.FacadeRelation;
import com.shimh.entity.relation.Follow;
import com.shimh.service.RelationService;
import com.shimh.service.UmsMemberService;
import com.shimh.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = "text/plain;charset=UTF-8")
public class RelationApiController  {
    static final Logger LOGGER = LoggerFactory.getLogger(RelationApiController.class);

    @Resource
    private RelationService relationService;

    @Resource
    private UmsMemberService umsMemberService;

    @Resource
    private UserService userService;

    /***
     * 根据uid获取用户粉丝
     * @param request
     * @return
     */
    @RequestMapping(value = "/fans-list", method = RequestMethod.GET)
    public Result getFans(HttpServletRequest request,
                          @RequestParam(value = "uid", defaultValue = "-1") int uid,
                          @RequestParam(value = "breakpoint", defaultValue = "0") int breakPoint,
                          @RequestParam(value = "count", defaultValue = "20") int count) {
        count = count > 20 ? 20 : count;
        User user = umsMemberService.getCurrentMember();
        List<FacadeRelation> relationList = relationService.queryFansList(user.getId(), uid, breakPoint, count);
        return Result.success(relationList);
    }

    /***
     * 根据uid获取关注用户
     * @param request
     * @return
     */
    @RequestMapping(value = "/follow-list", method = RequestMethod.GET)
    public Result getFollow(HttpServletRequest request,
                            @RequestParam(value = "uid", defaultValue = "-1") int uid,
                            @RequestParam(value = "breakpoint", defaultValue = "0") int breakPoint,
                            @RequestParam(value = "count", defaultValue = "20") int count) {
        User user = umsMemberService.getCurrentMember();
        count = count > 20 ? 20 : count;
        List<FacadeRelation> relationList = relationService.queryFollowList(user.getId(), uid, breakPoint, count);
        return Result.success(relationList);
    }

    /***
     * 关注
     * @param request
     * @return
     */
    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    public Result subscribe(HttpServletRequest request,
                            @RequestParam(value = "uid") long uid) {
        User user = umsMemberService.getCurrentMember();
        Follow follow = relationService.subscribe(user.getId(), uid);
        return Result.success(follow);
    }

    /***
     * 取消关注
     * @param request
     * @return
     */
    @RequestMapping(value = "/unSubscribe", method = RequestMethod.POST)
    public Result unSubcribe(HttpServletRequest request,
                             @RequestParam(value = "uid") long uid) {
        User user = umsMemberService.getCurrentMember();
        Follow follow = relationService.unSubscribe(user.getId(), uid);
        return Result.success(follow);
    }

}
