package com.shimh.service.impl;

import com.macro.mall.common.exception.Asserts;
import com.macro.mall.mapper.UmsMemberMapper;
import com.macro.mall.model.UmsMember;
import com.macro.mall.model.UmsMemberExample;
import com.macro.mall.security.util.JwtTokenUtil;
import com.shimh.common.constant.ResultCode;
import com.shimh.common.util.PasswordHelper;
import com.shimh.entity.User;
import com.shimh.entity.UserStatus;
import com.shimh.service.RedisService;
import com.shimh.service.UmsMemberService;
import com.shimh.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 会员管理Service实现类
 * Created by macro on 2018/8/3.
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsMemberServiceImpl.class);
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UmsMemberMapper memberMapper;

    @Autowired
    private UserService userService;


    @Autowired
    private RedisService redisService;
    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    @Value("${redis.key.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;


    @Override
    public User loadUserByUsername(String username) {
        User user = getByUsername(username);
        if(user!=null){
            return user;
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }


    @Override
    public User getByUsername(String username) {
        return userService.getUserByAccount(username);
    }

    @Override
    public User getById(Long id) {
        return userService.getUserById(id);
    }

    @Override
    public String register(String username, String password, String telephone, String authCode) {
        //验证验证码
//        if(!verifyAuthCode(authCode,telephone)){
//            Asserts.fail("验证码错误");
//        }
        //查询是否已有该用户
//        UmsMemberExample example = new UmsMemberExample();
//        example.createCriteria().andUsernameEqualTo(username);
//        example.or(example.createCriteria().andPhoneEqualTo(telephone));
//        List<UmsMember> umsMembers = memberMapper.selectByExample(example);
        User user= userService.findByAcountAndMobilePhoneNumber(username,telephone);
        if (null!=user) {
            throw new BadCredentialsException(ResultCode.USER_HAS_EXISTED.message());
//            Asserts.fail("该用户已经存在");
        }
        //没有该用户进行添加操作
        User umsMember = new User();
        umsMember.setAccount(username);
        umsMember.setNickname(username);
        umsMember.setMobilePhoneNumber(telephone);
//        umsMember.setPassword(passwordEncoder.encode(password));
        umsMember.setPassword(password);
        umsMember.setCreateDate(new Date());
        umsMember.setStatus(UserStatus.normal);
        userService.saveUser(umsMember);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(umsMember, null, umsMember.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenUtil.generateToken(umsMember);
        return token;
    }

    @Override
    public String generateAuthCode(String telephone) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i=0;i<6;i++){
            sb.append(random.nextInt(10));
        }
        //验证码绑定手机号并存储到redis
        redisService.set(REDIS_KEY_PREFIX_AUTH_CODE+telephone,sb.toString());
        redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE+telephone,AUTH_CODE_EXPIRE_SECONDS);
        return sb.toString();
    }

    @Override
    public void updatePassword(String telephone, String password, String authCode) {
        UmsMemberExample example = new UmsMemberExample();
        example.createCriteria().andPhoneEqualTo(telephone);
        List<UmsMember> memberList = memberMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(memberList)){
            Asserts.fail("该账号不存在");
        }
        //验证验证码
        if(!verifyAuthCode(authCode,telephone)){
            Asserts.fail("验证码错误");
        }
        UmsMember umsMember = memberList.get(0);
        umsMember.setPassword(passwordEncoder.encode(password));
        memberMapper.updateByPrimaryKeySelective(umsMember);
    }

    @Override
    public User getCurrentMember() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        User user = (User) auth.getPrincipal();
        return user;
    }

//    @Override
//    public UmsMember getCurrentMember() {
//        SecurityContext ctx = SecurityContextHolder.getContext();
//        Authentication auth = ctx.getAuthentication();
//        MemberDetails memberDetails = (MemberDetails) auth.getPrincipal();
//        return memberDetails.getUmsMember();
//    }

    @Override
    public void updateIntegration(Long id, Integer integration) {
        UmsMember record=new UmsMember();
        record.setId(id);
        record.setIntegration(integration);
        memberMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            User userDetails = loadUserByUsername(username);
            password= PasswordHelper.encryptPassword(password,userDetails.getSalt());
//            if(!passwordEncoder.matches(password,userDetails.getPassword())){
//                throw new BadCredentialsException("密码不正确");
//            }
            if(!password.equals(userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            this.passwordEncoder.matches(userDetails.getPassword(), userDetails.getPassword(),null)
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public String refreshToken(String token) {
        return jwtTokenUtil.refreshHeadToken(token);
    }

    //对输入的验证码进行校验
    private boolean verifyAuthCode(String authCode, String telephone){
        if(StringUtils.isEmpty(authCode)){
            return false;
        }
        String realAuthCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        return authCode.equals(realAuthCode);
    }

}
