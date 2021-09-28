package com.ququ.imserver.shop.service;

import com.ququ.common.result.ResultJson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "imserver-user")
public interface ImUserService {

    @RequestMapping(value = "/im/user/uid",method = RequestMethod.POST)
    ResultJson getImUid(@RequestParam(value = "userId") String userId);

    @RequestMapping("/im/user/uids")
    ResultJson getImUids(@RequestParam(value = "userIds") String userIds);

    @RequestMapping(value = "/im/user/userid",method = RequestMethod.POST)
    ResultJson getUserInfo(@RequestParam(value = "imUid") String imUid);

    @RequestMapping(value = "/im/user/userinfo",method = RequestMethod.POST)
    ResultJson getUserInfoByUserId(@RequestParam(value = "userId") String userId);

    @RequestMapping(value = "/im/user/usersinfo",method = RequestMethod.POST)
    ResultJson getUsersInfoByUserId(@RequestParam(value = "userIds") String userIds);

    }
