package com.shimh.common.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Session超时，通知前端删除token
 *
 * @author shimh
 * <p>
 * 2018年1月30日
 */
public class ClearTokenInteceptor extends HandlerInterceptorAdapter {

//    private static final String SESSION_TIME_OUT_K = "SESSION_TIME_OUT";
//    private static final String SESSION_TIME_OUT_V = "timeout";
//
//    @Autowired
//    private RedisManager redisManager;
//
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//            throws Exception {
//
//        String token = request.getHeader(OAuthSessionManager.OAUTH_TOKEN);
//
//        if (null != token) {
//            Session s = redisManager.get(token, Session.class);
//
//            if (null == s || null == s.getId()) {
//                response.setHeader(SESSION_TIME_OUT_K, SESSION_TIME_OUT_V);
//            }
//        }
//
//        return super.preHandle(request, response, handler);
//    }

}
