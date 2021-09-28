package com.ququ.imserver.user.service;

import com.alibaba.fastjson.JSONArray;
import com.ququ.common.result.ResultJson;
import com.ququ.common.utils.MD5Util;
import com.ququ.imserver.constant.StatusConstant;
import com.ququ.imserver.user.pojo.ImUser;
import com.ququ.imserver.user.redis.ImToken;
import com.ququ.imserver.user.redis.ImUid;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImUserBusinessService {

    private Logger logger = LoggerFactory.getLogger(ImUserBusinessService.class);

    @Autowired
    private ImApiServiceFeign imApiServiceFeign;

    @Autowired
    private ImUserService imUserService;

    @Autowired
    private ImToken imToken;

    @Autowired
    private ImShopService imShopService;


    /**
     * 注册IM用户
     * @param shopId
     * @param userId
     * @param name
     * @param iconUrl
     * @param mobile
     * @param sex
     * @param extend
     * @return
     */
    public ResultJson registerImUser(String shopId,String userId,String name,String iconUrl,String mobile,Integer sex, String extend){
        String imUid = null;
        String token = null;
        Map<String,String> resultMap = new HashMap<>();
        //判断是否已经注册
        ImUser imUser = imUserService.getImUserByUserId(userId);
        if (imUser != null){
            imUid = imUser.getImuid();
            token = imToken.getImToken(imUid);
            resultMap.put("userId",userId);
            resultMap.put("imUid",imUid);
            resultMap.put("imToken",token);
            return new ResultJson(resultMap);
        }
        //注册新号
        imUid = ImUid.getNewImuid();
        Map<String,Object> map = imApiServiceFeign.registerUser(imUid,name,iconUrl,sex,mobile,extend);
        logger.debug("IM用户注册结果:{}",map);
        if (Integer.parseInt(map.get("status").toString()) == StatusConstant.SUCCESS.getCode()){
            token = map.get("imToken").toString();
        }else
            return new ResultJson(Integer.parseInt(map.get("status").toString()),map.get("msg").toString());
        imUser = new ImUser();
        imUser.setUserid(userId);
        imUser.setImuid(imUid);
        imUser.setUsername(name);
        imUser.setIcon(iconUrl);
        imUser.setMobile(mobile);
        imUser.setSex(sex);
        imUserService.save(imUser);
        //保存token
        imToken.setImToken(imUid,token);

        //保存企业
        imShopService.setStaffShop(shopId,userId);
        //检查是否存在员群

        //创建员工群或加入员工群

        resultMap.put("userId",userId);
        resultMap.put("imUid",imUid);
        resultMap.put("imToken",token);
        return new ResultJson(resultMap);
    }


    /**
     * 更新用户数据
      * @param userId
     * @param name
     * @param iconUrl
     * @param mobile
     * @param sex
     * @param extend
     * @return
     */
    public ResultJson updateImUser(String userId,String name,String iconUrl,String mobile,Integer sex,String email,String birth,String personSign, String extend){
        String imUid = null;
        Map<String,String> resultMap = new HashMap<>();
        //判断是否已经注册
        ImUser imUser = imUserService.getImUserByUserId(userId);
        if (imUser == null){
            return new ResultJson(StatusConstant.USER_UNAVAILABLE.getCode(),StatusConstant.USER_UNAVAILABLE.getMsg());
        }else {
            if (sex != null) imUser.setSex(sex);
            if (StringUtils.isNotBlank(name)) imUser.setUsername(name);
            if (StringUtils.isNotBlank(iconUrl)) imUser.setIcon(iconUrl);
            if (StringUtils.isNotBlank(mobile)) imUser.setMobile(mobile);
            imUserService.update(imUser);
            imUid=imUser.getImuid();
        }
        //更新
        Map<String,Object> map = imApiServiceFeign.updateUser(imUid,name,iconUrl,sex,mobile,email,birth,personSign,extend);
        logger.debug("IM用户信息更新结果:{}",map);
        if (Integer.parseInt(map.get("status").toString()) == StatusConstant.SUCCESS.getCode()){
            return new ResultJson();
        }else
            return new ResultJson(Integer.parseInt(map.get("status").toString()),map.get("msg").toString());
    }

    /**
     * 获取单个用户的ImUid
     * @param userId
     * @return
     */
    public String getImUid(String userId){
        String imUid = null;
        ImUser imUser = imUserService.getImUserByUserId(userId);
        imUid = imUser==null?null:imUser.getImuid();
        return imUid;
    }

    /**
     * 批量获取用户ImUid
     * @param userIds
     * @return
     */
    public List<Map> getImUids(String userIds){
        List<Map> resultList = new ArrayList<>();
        List<String> userIdList = JSONArray.parseArray(userIds,String.class);
        for (String userId : userIdList){
            if (StringUtils.isNotBlank(userId)){
                ImUser imUser = imUserService.getImUserByUserId(userId);
                if (imUser != null){
                    Map<String,String> map = new HashMap<>();
                    map.put("userId",userId);
                    map.put("imUid",imUser.getImuid());
                    resultList.add(map);
                }
            }
        }
        return resultList;
    }


    /**
     * 登录获取token
     * @param userId
     * @param nonce
     * @param sign
     * @return
     */
    public ResultJson getToken(String userId,String nonce,String sign){
        ImUser imUser = imUserService.getImUserByUserId(userId);
        if (imUser == null)
            return new ResultJson(StatusConstant.USER_UNAVAILABLE.getCode(),StatusConstant.USER_UNAVAILABLE.getMsg());
        String sign1 = MD5Util.md5(nonce+imUser.getImuid()).toLowerCase();
        if (sign.equals(sign1)){
            //签名验证通过
            Map<String,String> resultMap = new HashMap<>();
            resultMap.put("userId",userId);
            resultMap.put("imUid",imUser.getImuid());
            resultMap.put("imToken",imToken.getImToken(imUser.getImuid()));
            return new ResultJson(resultMap);
        }else
            return new ResultJson(StatusConstant.USER_LOGIN_FAIL.getCode(),StatusConstant.USER_LOGIN_FAIL.getMsg());
    }


    /**
     * 从userId查询用户资料
     * @param userId
     * @return
     */
    public ResultJson getUserInfoByUserId(String userId){
        ImUser imUser = imUserService.getImUserByUserId(userId);
        if (imUser == null)
            return new ResultJson(StatusConstant.USER_UNAVAILABLE.getCode(),StatusConstant.USER_UNAVAILABLE.getMsg());
        Map<String,String> userMap = new HashMap<>();
        userMap.put("userId",userId);
        userMap.put("name",imUser.getUsername());
        userMap.put("imUid",imUser.getImuid());
        userMap.put("mobile",imUser.getMobile());
        userMap.put("sex",imUser.getSex().toString());
        userMap.put("iconUrl",imUser.getIcon());
        return new ResultJson(userMap);
    }


    /**
     * 批量从UserId查询用户资料
     * @param userIds
     * @return
     */
    public ResultJson getUsersInfoByUserId(String userIds){
        List<String> userIdList = JSONArray.parseArray(userIds,String.class);
        List<Map> userInfoList = new ArrayList<>();
        for (String userId: userIdList){
            ImUser imUser = imUserService.getImUserByUserId(userId);
            if (imUser != null) {
                Map<String,String> userMap = new HashMap<>();
                userMap.put("userId",userId);
                userMap.put("name",imUser.getUsername());
                userMap.put("imUid",imUser.getImuid());
                userMap.put("mobile",imUser.getMobile());
                userMap.put("sex",imUser.getSex().toString());
                userMap.put("iconUrl",imUser.getIcon());
                userInfoList.add(userMap);
            }
        }
        return new ResultJson(userInfoList);
    }


    /**
     * 从IM账号反向查询用户资料
     * @param imUid
     * @return
     */
    public ResultJson getUserInfo(String imUid){
        ImUser imUser = imUserService.getImUserByImUid(imUid);
        if (imUser == null)
            return new ResultJson(StatusConstant.USER_UNAVAILABLE.getCode(),StatusConstant.USER_UNAVAILABLE.getMsg());
        Map<String,String> userMap = new HashMap<>();
        userMap.put("userId",imUser.getUserid());
        userMap.put("name",imUser.getUsername());
        userMap.put("imUid",imUser.getImuid());
        userMap.put("mobile",imUser.getMobile());
        userMap.put("sex",imUser.getSex().toString());
        userMap.put("iconUrl",imUser.getIcon());
        return new ResultJson(userMap);
    }


    /**
     * 批量反向查询用户信息
     * @param imUids
     * @return
     */
    public ResultJson getUserInfos(String imUids){
        List<Map> list = new ArrayList<>();
        List<String> imUidList = JSONArray.parseArray(imUids,String.class);
        for (String imUid : imUidList) {
            if (StringUtils.isNotBlank(imUid)) {
                ImUser imUser = imUserService.getImUserByImUid(imUid);
                if (imUser != null) {
                    Map<String,String> userMap = new HashMap<>();
                    userMap.put("userId",imUser.getUserid());
                    userMap.put("name",imUser.getUsername());
                    userMap.put("imUid",imUser.getImuid());
                    userMap.put("mobile",imUser.getMobile());
                    userMap.put("sex",imUser.getSex().toString());
                    userMap.put("iconUrl",imUser.getIcon());
                    list.add(userMap);
                }
            }
        }
        if (list.size() == 0)
            return new ResultJson(StatusConstant.USER_UNAVAILABLE.getCode(),StatusConstant.USER_UNAVAILABLE.getMsg());
        return new ResultJson(list);
    }


}
