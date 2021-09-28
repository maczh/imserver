package com.ququ.imserver.user.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "imserver-api")
public interface ImApiServiceFeign {

    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    Map<String,Object> registerUser(@RequestParam(value = "imUid") String imUid,
                                    @RequestParam(value = "name") String name,
                                    @RequestParam(value = "iconUrl") String iconUrl,
                                    @RequestParam(value = "sex") Integer sex,
                                    @RequestParam(value = "mobile") String mobile,
                                    @RequestParam(value = "extend") String extend);

    @RequestMapping(value = "/user/update",method = RequestMethod.POST)
    Map<String,Object> updateUser(@RequestParam(value = "imUid") String imUid,
                                    @RequestParam(value = "name") String name,
                                    @RequestParam(value = "iconUrl") String iconUrl,
                                    @RequestParam(value = "sex") Integer sex,
                                    @RequestParam(value = "mobile") String mobile,
                                    @RequestParam(value = "email") String email,
                                    @RequestParam(value = "birth") String birth,
                                    @RequestParam(value = "personSign") String personSign,
                                    @RequestParam(value = "extend") String extend);


}
