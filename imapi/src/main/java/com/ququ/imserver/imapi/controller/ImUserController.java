package com.ququ.imserver.imapi.controller;

import com.ququ.imserver.imapi.service.ImApiBusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(tags = "用户注册与管理接口，不对外开放")
@RestController
@RequestMapping("/user")
public class ImUserController {

    @Autowired
    private ImApiBusinessService imApiBusinessService;

    @ApiOperation(value = "IM用户注册接口", hidden = true)
    @RequestMapping(value = "/register")
    public Map<String,Object> register(@RequestParam String imUid,
                                       @RequestParam String name,
                                       @RequestParam(required = false) String iconUrl,
                                       @RequestParam(defaultValue = "0") Integer sex,
                                       @RequestParam(required = false) String mobile,
                                       @RequestParam(required = false) String extend){
        return imApiBusinessService.register(imUid, name, iconUrl, sex, mobile, extend);
    }


    @ApiOperation(value = "IM用户修改基本信息", hidden = true)
    @RequestMapping(value = "/update")
    public Map<String,Object> updateUser(@RequestParam String imUid,
                                         @RequestParam(required = false) String name,
                                         @RequestParam(required = false) String iconUrl,
                                         @RequestParam(required = false) Integer sex,
                                         @RequestParam(required = false) String mobile,
                                         @RequestParam(required = false) String email,
                                         @RequestParam(required = false) String birth,
                                         @RequestParam(required = false) String personSign,
                                         @RequestParam(required = false) String extend){
        return imApiBusinessService.updateUser(imUid, name, iconUrl, sex, mobile, email, birth, personSign, extend);
    }

}
