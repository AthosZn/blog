package com.shimh.controller;

import com.shimh.service.UmsMemberService;
import com.shimh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户api
 *
 * @author shimh
 * <p>
 * 2018年1月23日
 */
@RestController
@RequestMapping(value = "/oauth")
public class OauthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UmsMemberService umsMemberService;

    @PostMapping("/check_token")
    @ResponseBody
    public Map<String, Object> checkToken(@RequestParam("token") String value) {
        Map<String, Object> map=new HashMap<>();
        map.put("active",true);
        return map;
    }

}
