package com.shimh.controller.chat;

import ai.yunxi.im.common.pojo.UserInfo;
import com.macro.mall.common.api.ResultCode;
import com.shimh.common.result.Result;
import com.shimh.common.util.UserUtils;
import com.shimh.entity.User;
import com.shimh.entity.user.UserFriend;
import com.shimh.service.UserService;
import com.shimh.service.chat.UserFriendService;
import com.shimh.util.ResultUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 朋友相关
 */
@RequestMapping("/user/friend")
@RestController
public class UserFriendController {

    @Resource
    private UserFriendService userFriendService;

    @Resource
    private UserService userService;

    /**
     * 获取朋友列表
     *
     * @return
     */
    @GetMapping("/lists")
    public Result lists(HttpServletRequest request) {
        List<UserInfo> result=new ArrayList<>();
        User user = UserUtils.getCurrentUser();
        Long uid = user.getId();
        List<UserFriend> userFriends = userFriendService.listByUid(uid);
        List<Long> uids = userFriends.stream().map(UserFriend::getFriendUid).collect(Collectors.toList());
        List<User> friendList = userService.findAllBYId(uids);
        for(User friend:friendList){
            UserInfo userInfo=new UserInfo(); BeanUtils.copyProperties(friend,userInfo);
            result.add(userInfo);
        }
        return ResultUtils.success(result);
    }


    /**
     * 删除好友
     *
     * @return
     */
    @PostMapping("/delete")
    public Result delete(@Valid @RequestBody UserInfo userInfo,
                            BindingResult bindingResult,
                            HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return ResultUtils.error(ResultCode.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }
        User currentUser=UserUtils.getCurrentUser();
        Long uid = currentUser.getId();
        Long friendUid = userInfo.getId();
        boolean b = userFriendService.deleteByUidAndFriendUid(uid, friendUid);
        boolean b1 = userFriendService.deleteByUidAndFriendUid(friendUid, uid);
        if (!b) {
            return ResultUtils.error(ResultCode.NOT_NETWORK);
        }

        return ResultUtils.success();
    }
}
