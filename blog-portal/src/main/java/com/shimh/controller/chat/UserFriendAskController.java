package com.shimh.controller.chat;

import ai.yunxi.im.common.constant.MessageConstant;
import ai.yunxi.im.common.pojo.ImRouterRequestMessage;
import ai.yunxi.im.common.pojo.UserInfo;
import com.macro.mall.common.api.ResultCode;
import com.shimh.common.result.Result;
import com.shimh.common.util.UserUtils;
import com.shimh.config.kafka.KafkaUtil;
import com.shimh.entity.User;
import com.shimh.entity.user.UserFriend;
import com.shimh.entity.user.UserFriendAsk;
import com.shimh.entity.user.UserFriendMsg;
import com.shimh.entity.vo.res.UserFriendAskAckReqVO;
import com.shimh.entity.vo.res.UserFriendAskListResVO;
import com.shimh.service.UserService;
import com.shimh.service.chat.UserFriendAskService;
import com.shimh.service.chat.UserFriendMsgService;
import com.shimh.service.chat.UserFriendService;
import com.shimh.util.ResultUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 好友请求
 */
@RequestMapping("/user/friendAsk")
@RestController
public class UserFriendAskController {

    @Resource
    private UserFriendAskService userFriendAskService;
    @Resource
    private UserFriendService userFriendService;
    @Resource
    private UserService userService;
    @Resource
    private KafkaUtil kafkaUtil;


    @Resource
    private UserFriendMsgService userFriendMsgService;

    @GetMapping("/lists")
    public Result lists(HttpServletRequest request) {

        // 验证登录
        User user = UserUtils.getCurrentUser();
        Long uid = user.getId();
        List<UserFriendAskListResVO> userFriendAskListResVOS = new ArrayList<>();
        List<UserFriendAsk> userFriendAsks = userFriendAskService.listByUid(uid);

        if (userFriendAsks.size() == 0) {
            return Result.success(userFriendAskListResVOS);
        }

        List<Long> uids = userFriendAsks.stream().map(UserFriendAsk::getFriendUid).collect(Collectors.toList());
        List<User> userList = userService.findAllBYId(uids);
        Map<Long, User> userInfoListResVOMap = userList.stream().collect(Collectors.toMap(User::getId, a -> a));

        userFriendAsks.forEach(v -> {
            UserFriendAskListResVO userFriendAskListResVO = new UserFriendAskListResVO();
            BeanUtils.copyProperties(v, userFriendAskListResVO);
            userFriendAskListResVO.setUser(userInfoListResVOMap.get(v.getFriendUid()));
            userFriendAskListResVOS.add(userFriendAskListResVO);
        });
        return Result.success(userFriendAskListResVOS);
    }

    // 发起好友请求
    @PostMapping("/create")
    public Result create(@RequestParam(value = "checkCode", required = false, defaultValue = "") String checkCode,
                         @RequestParam(value = "friendUid", required = false, defaultValue = "0L") Long friendUid,
                         @RequestParam(value = "remark", required = false, defaultValue = "") String remark,
                         HttpServletRequest request) {
        // 验证登录

        Long uid = UserUtils.getCurrentUser().getId();

        UserFriend userFriend = userFriendService.findByUidAndFriendUid(uid, friendUid);
        if (userFriend != null) {
            return Result.error(ResultCode.DATA_REPEAT, "已经是好友了~");
        }

        User friendUser = userService.getUserById(friendUid);
        if (friendUser == null) {
            return Result.error(ResultCode.DATA_NOT);
        }

        UserFriendAsk userFriendAsk = new UserFriendAsk();
        userFriendAsk.setUid(friendUid);
        userFriendAsk.setFriendUid(uid);
        userFriendAsk.setRemark(remark);
        userFriendAsk.setStatus(0);

        boolean b = userFriendAskService.insertUserFriendAsk(userFriendAsk);
        if (!b) {
            return Result.error(ResultCode.ERROR);
        }

        // 发送在线消息
        // 查询用户信息
        User toUser = userService.getUserById(uid);
        String msgContent = "请求加为好友";
        Long toUid = toUser.getId();
//        String name = toUser.getNickname();
//        String avatar = toUser.getAvatar();
        UserDetails userDetail= UserUtils.getCurrentUser();
        UserInfo userInfo=new UserInfo();
        BeanUtils.copyProperties(userDetail,userInfo);
        ImRouterRequestMessage imRouterRequestMessage
                =new ImRouterRequestMessage(toUid, MessageConstant.FRIEND_ASK,userInfo);
        kafkaUtil.sendChatKafka(imRouterRequestMessage);
        return Result.success();

    }

    /**
     * 确认好友请求
     *
     * @param userFriendAskAckReqVO
     * @param bindingResult
     * @param request
     * @return
     */
    @PostMapping("/ack")
    public Result ack(@Valid @RequestBody UserFriendAskAckReqVO userFriendAskAckReqVO,
                         BindingResult bindingResult,
                         HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return ResultUtils.error(ResultCode.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }
        User userDetail= UserUtils.getCurrentUser();
        Long uid = userDetail.getId();
        Long id = userFriendAskAckReqVO.getId();

        UserFriendAsk userFriendAsk = userFriendAskService.findById(id);
        // 已经添加过了
        if (userFriendAsk == null || !uid.equals(userFriendAsk.getUid()) || userFriendAsk.getStatus() != 0) {
            return ResultUtils.error(ResultCode.PARAM_VERIFY_FALL, "请勿重复添加~");
        }

        UserFriendAsk upUserFriendAsk = new UserFriendAsk();
        upUserFriendAsk.setId(id);
        upUserFriendAsk.setStatus(userFriendAskAckReqVO.getStatus());
        boolean b = userFriendAskService.updateUserFriendAsk(upUserFriendAsk);

        // 拒绝添加
        if (!b) {
            return ResultUtils.error();
        }

        // 如果是拒绝
        if (userFriendAskAckReqVO.getStatus() == 2) {
            return ResultUtils.success();
        }

        Long friendUid = userFriendAsk.getFriendUid();
        // 判断是不是好友
        UserFriend userFriend = userFriendService.findByUidAndFriendUid(uid, friendUid);
        if (userFriend != null && userFriend.getId() != null) {
            return ResultUtils.success();
        }

        // 判断好友数量
        List<User> userProfiles = userService.findAllBYId(Arrays.asList(uid, friendUid));
        for (User userProfile : userProfiles) {
            if (userProfile != null
                    && userProfile.getFriendCount() != null
                    && userProfile.getFriendCount() >= 2000) {
                return ResultUtils.error(ResultCode.PARAM_VERIFY_FALL, "好友数量已到上限~");
            }
        }
        String msgContent = userFriendAsk.getRemark();

        msgContent = msgContent != null && !"".equals(msgContent) ? msgContent : "成为好友，现在开始聊吧~";

        // 增加朋友
        List<UserFriend> userFriends = new ArrayList<>();

        // 增加当前用户的好友列表
        UserFriend userFriend1 = new UserFriend();
        userFriend1.setUid(uid);
        userFriend1.setFriendUid(friendUid);
        userFriend1.setRemark("");
        userFriend1.setLastMsgContent(msgContent);
        userFriend1.setCreateTime(new Date());
        userFriend1.setModifiedTime(new Date());
        userFriends.add(userFriend1);

        // 增加申请人的好友列表
        UserFriend userFriend2 = new UserFriend();
        userFriend2.setUid(friendUid);
        userFriend2.setFriendUid(uid);
        userFriend2.setRemark("");
        userFriend2.setLastMsgContent(msgContent);
        userFriend2.setCreateTime(new Date());
        userFriend2.setModifiedTime(new Date());
        userFriends.add(userFriend2);

        boolean b1 = userFriendService.insertUserFriendAll(userFriends);

        // 更新好友数量
        List<User> userList = new ArrayList<>();
        User userProfile1 = new User();
        userProfile1.setId(uid);
        userProfile1.setFriendCount(1);
        userList.add(userProfile1);
        User userProfile2 = new User();
        userProfile2.setId(friendUid);
        userProfile2.setFriendCount(1);
        userList.add(userProfile2);
        userService.updateUserCountAll(userList);

        Long senderUid = uid;

        // 追加消息
        UserFriendMsg userFriendMsg = new UserFriendMsg();
        // 把最小的那个 用户ID作为 之后的查询uid
        Long toUid = friendUid;
        if (uid > friendUid) {
            toUid = uid;
            uid = friendUid;
        }
        userFriendMsg.setUid(uid);
        userFriendMsg.setToUid(toUid);
        userFriendMsg.setSenderUid(senderUid);
        userFriendMsg.setMsgContent(msgContent);
        userFriendMsg.setMsgType(1);
        userFriendMsgService.insertUserFriendMsg(userFriendMsg);

        // 发送在线消息
        // 查询用户信息
        User user = userService.getUserById(senderUid);
        UserInfo userInfo=new UserInfo();
        BeanUtils.copyProperties(userDetail,userInfo);
        ImRouterRequestMessage imRouterRequestMessage
                =new ImRouterRequestMessage(toUid, MessageConstant.FRIEND_ASK,userInfo);
        kafkaUtil.sendChatKafka(imRouterRequestMessage);

        return ResultUtils.success();

    }

    /**
     * 清空请求数量
     *
     * @return
     */
    @PostMapping("/clearFriendAskCount")
    public Result clearFriendAskCount(HttpServletRequest request) {
        User user = UserUtils.getCurrentUser();
        boolean b = userService.updateUserAckCount(user);
        if (!b) {
            return ResultUtils.error();
        }
        return ResultUtils.success();

    }

}
