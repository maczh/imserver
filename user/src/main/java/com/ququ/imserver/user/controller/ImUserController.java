package com.ququ.imserver.user.controller;

import com.ququ.common.result.ResultJson;
import com.ququ.imserver.user.service.ImUserBusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "IM服务用户管理模块")
@RestController
@RequestMapping("/im/user")
public class ImUserController {

    @Autowired
    private ImUserBusinessService imUserBusinessService;


    @ApiOperation(value = "注册IM用户", notes = "注册IM用户",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopId", value = "商户的shopId", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "用户昵称", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "iconUrl", value = "头像照片地址", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "mobile", value = "手机号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "sex", value = "性别：0-女 1-男", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "extend", value = "备注信息，JSON格式", required = false, paramType = "query", dataType = "String")
    })
    @RequestMapping("/register")
    public ResultJson registerIm(@RequestParam String shopId,
                                 @RequestParam String userId,
                                 @RequestParam String name,
                                 @RequestParam(required = false) String iconUrl,
                                 @RequestParam(required = false) String mobile,
                                 @RequestParam(defaultValue = "0") Integer sex,
                                 @RequestParam(required = false) String extend){
        return imUserBusinessService.registerImUser(shopId, userId, name, iconUrl, mobile, sex, extend);
    }


    @ApiOperation(value = "修改IM用户信息", notes = "修改IM用户基本信息",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "用户昵称", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "iconUrl", value = "头像照片地址", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "mobile", value = "手机号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "sex", value = "性别：0-女 1-男", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "email", value = "邮箱地址", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "birth", value = "生日", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "personSign", value = "个性签名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "extend", value = "备注信息，JSON格式", required = false, paramType = "query", dataType = "String")
    })
    @RequestMapping("/update")
    public ResultJson updateImUserInfo(@RequestParam String userId,
                                 @RequestParam(required = false) String name,
                                 @RequestParam(required = false) String iconUrl,
                                 @RequestParam(required = false) String mobile,
                                 @RequestParam(required = false) Integer sex,
                                 @RequestParam(required = false) String email,
                                 @RequestParam(required = false) String birth,
                                 @RequestParam(required = false) String personSign,
                                 @RequestParam(required = false) String extend){
        return imUserBusinessService.updateImUser(userId, name, iconUrl, mobile, sex, email,birth,personSign,extend);
    }


    @ApiOperation(value = "获取用户的IMUID", notes = "获取用户的IMUID",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping("/uid")
    public ResultJson getImUid(@RequestParam String userId){
        String imUid = imUserBusinessService.getImUid(userId);
        if (imUid != null){
            Map<String,String> map = new HashMap<>();
            map.put("userId",userId);
            map.put("imUid",imUid);
            return new ResultJson(map);
        }else
            return new ResultJson(2002,"未注册用户");
    }


    @ApiOperation(value = "批量获取用户的IMUID", notes = "批量获取用户的IMUID",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userIds", value = "用户id列表，JSONArray格式", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping("/uids")
    public ResultJson getImUids(@RequestParam String userIds){
        List<Map> list = imUserBusinessService.getImUids(userIds);
        if (list.size()>0){
            return new ResultJson(list);
        }else
            return new ResultJson(2002,"未注册用户");
    }


    @ApiOperation(value = "登录IM，获取token", notes = "登录IM，获取token",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "nonce", value = "nonce参数", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "登录密码", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping("/logon")
    public ResultJson logon(@RequestParam String userId,
                            @RequestParam String nonce,
                            @RequestParam String password){
        return imUserBusinessService.getToken(userId,nonce,password);
    }


    @ApiOperation(value = "从用户的IMUID反查userId", notes = "从用户的IMUID反查userId",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "imUid", value = "用户imUid", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping("/userid")
    public ResultJson getUserInfo(@RequestParam String imUid){
        return imUserBusinessService.getUserInfo(imUid);
    }


    @ApiOperation(value = "从批量用户的IMUID反查userId", notes = "从批量用户的IMUID反查userId",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "imUids", value = "用户imUid列表，JSONArray格式", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping("/userids")
    public ResultJson getUserInfos(@RequestParam String imUids){
        return imUserBusinessService.getUserInfos(imUids);
    }

    @ApiOperation(value = "获取用户的详情", notes = "获取用户详情",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping("/userinfo")
    public ResultJson getUserInfoByUserId(@RequestParam String userId){
        return imUserBusinessService.getUserInfoByUserId(userId);
    }


    @ApiOperation(value = "批量获取用户的详情", notes = "批量获取用户详情",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userIds", value = "用户id列表，JSONArray格式", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping("/usersinfo")
    public ResultJson getUsersInfoByUserId(@RequestParam String userIds){
        return imUserBusinessService.getUsersInfoByUserId(userIds);
    }

}
