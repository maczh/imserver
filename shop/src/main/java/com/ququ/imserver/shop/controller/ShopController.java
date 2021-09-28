package com.ququ.imserver.shop.controller;

import com.alibaba.fastjson.JSONArray;
import com.ququ.common.result.ResultJson;
import com.ququ.imserver.constant.RedisKey;
import com.ququ.imserver.constant.StatusConstant;
import com.ququ.imserver.shop.redis.RedisDao;
import com.ququ.imserver.shop.service.ImApiGroupService;
import com.ququ.imserver.shop.service.ImUserService;
import com.ququ.imserver.shop.service.ShopNetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.*;

@Api(tags = "商户管理模块")
@RestController
@RequestMapping("/im/shop")
public class ShopController {

    private Logger logger = LoggerFactory.getLogger(ShopController.class);
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private ShopNetService shopNetService;
    @Autowired
    private ImUserService imUserService;
    @Autowired
    private ImApiGroupService imApiGroupService;


    /**
     * 添加客服成员
     * @param shopId
     * @param userIds
     * @return
     */
    @ApiOperation(value = "添加客服成员", notes = "添加客服成员，商户的所有客服群都会自动添加这些客服成员",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopId", value = "商户的shopId", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "userIds", value = "商户的多个客服userId，JSONArray格式", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping("/contactstaffs/add")
    public ResultJson addShopContactStaffs(@RequestParam String shopId,
                                           @RequestParam String userIds){
        Jedis jedis = redisDao.getJedis();
        try {
            List<String> userIdList = JSONArray.parseArray(userIds,String.class);
            //先检查userId是否本商户员工
            for (String userId: userIdList){
                if (!shopId.equals(jedis.get(RedisKey.USER_SHOPID+userId)))
                    return new ResultJson(StatusConstant.USER_IS_NOT_STAFF_OF_SHOP.getCode(),userId+StatusConstant.USER_IS_NOT_STAFF_OF_SHOP.getMsg());
            }
            //添加到客服列表中
            for (String userId: userIdList){
                if (StringUtils.isNotBlank(userId)){
                    jedis.sadd(RedisKey.SHOP_CONTACT_STAFFS+shopId,userId);
                }
            }
            List<String> imUidList = new ArrayList<>();
            ResultJson imUidListResult = imUserService.getImUids(userIds);
            List<Map> userList = (List<Map>) imUidListResult.getData();
            for (Map<String,String> map : userList){
                imUidList.add(map.get("imUid"));
            }
            String contactStaffs = JSONArray.toJSONString(imUidList);
            //找出所有的客服群
            Set<String> shopGroupIdKeys = jedis.keys(RedisKey.SHOP_CUSTOMER_GROUPID+shopId+":*");

            //按顺序给每个客服群拉人入群
            for (String groupIdKey : shopGroupIdKeys){
                String groupId = jedis.get(groupIdKey);
                Map<String,Object> groupInfo = imApiGroupService.queryGroupInfo(null,groupId,0);
                List<Map> infos = (List<Map>) groupInfo.get("groupInfos");
                Map<String,Object> info = infos.get(0);
                String owner = info.get("owner").toString();
                imApiGroupService.addToGroup(groupId,owner,contactStaffs," ",0,null);
            }
            return new ResultJson();
        }finally {
            redisDao.close(jedis);
        }
    }

    /**
     * 移除客服成员
     * @param shopId
     * @param userIds
     * @return
     */
    @ApiOperation(value = "移除客服成员", notes = "移除客服成员，商户的所有客服群都会自动移除这些客服成员",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopId", value = "商户的shopId", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "userIds", value = "商户的多个客服userId，JSONArray格式", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping("/contactstaffs/remove")
    public ResultJson removeShopContactStaffs(@RequestParam String shopId,
                                           @RequestParam String userIds){
        Jedis jedis = redisDao.getJedis();
        try {
            List<String> userIdList = JSONArray.parseArray(userIds,String.class);
            for (String userId: userIdList){
                if (StringUtils.isNotBlank(userId)){
                    jedis.srem(RedisKey.SHOP_CONTACT_STAFFS+shopId,userId);
                }
            }
            return new ResultJson();
        }finally {
            redisDao.close(jedis);
        }
    }

    /**
     * 列出所有客服成员
     * @param shopId
     * @return
     */
    @ApiOperation(value = "列出所有客服成员", notes = "列出所有客服成员",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopId", value = "商户的shopId", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping("/contactstaffs/list")
    public ResultJson listShopContactStaffs(@RequestParam String shopId){
        Jedis jedis = redisDao.getJedis();
        try {
            Set<String> members = jedis.smembers(RedisKey.SHOP_CONTACT_STAFFS+shopId);
            if (members != null && members.size()>0) {
                String userIds = JSONArray.toJSONString(members);
                ResultJson result = imUserService.getUsersInfoByUserId(userIds);
                return result;
            }else
                return new ResultJson(StatusConstant.SHOP_UNAVAILABLE.getCode(),StatusConstant.SHOP_UNAVAILABLE.getMsg());
        }finally {
            redisDao.close(jedis);
        }
    }

    /**
     * 获取商户信息
     * @param shopId
     * @return
     */
    @ApiOperation(value = "获取商户信息", notes = "获取商户信息",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopId", value = "商户的shopId", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping("/info")
    public ResultJson getShopInfo(@RequestParam String shopId){
        Map<String,String> shopInfo = null;
        Jedis jedis = redisDao.getJedis();
        try {
            shopInfo = jedis.hgetAll(RedisKey.SHOP_INFO+shopId);
            if (shopInfo == null || shopInfo.isEmpty()) {
                //从.net获取商户资料
                ResultJson resultJson = shopNetService.getShopInfo(shopId);
                if (resultJson.getStatus() == StatusConstant.SUCCESS.getCode()){
                    Map<String,Object> shopInfo1 = (Map<String, Object>) resultJson.getData();
                    if (shopInfo1 == null || shopInfo1.isEmpty())
                        return new ResultJson(StatusConstant.SHOP_UNAVAILABLE.getCode(),StatusConstant.SHOP_UNAVAILABLE.getMsg());
                    shopInfo=new HashMap<>();
                    for (String key: shopInfo1.keySet()){
                        shopInfo.put(key,shopInfo1.get(key).toString());
                    }
                    logger.debug("从.net取回的商户资料:{}",shopInfo);
                    //重新保存到缓存
                    jedis.hmset(RedisKey.SHOP_INFO+shopId,shopInfo);
                    //设置缓存过期时间为1天
                    jedis.expire(RedisKey.SHOP_INFO+shopId,3600*24);
                }else
                    return resultJson;
            }
        }finally {
            redisDao.close(jedis);
        }
        return new ResultJson(shopInfo);
    }

    /**
     * 获取商户管理员
     * @param shopId
     * @return
     */
    @ApiOperation(value = "获取商户管理员", notes = "获取商户管理员",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopId", value = "商户的shopId", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping("/admin")
    public ResultJson getShopAdmin(@RequestParam String shopId){
        Jedis jedis = redisDao.getJedis();
        try {
            Map<String,String> shopInfo = jedis.hgetAll(RedisKey.SHOP_INFO+shopId);
            Map<String,String> shopAdmin = new HashMap<>();
            shopAdmin.put("shopId",shopId);
            if (shopInfo != null && shopInfo.containsKey("admin"))
                shopAdmin.put("admin",shopInfo.get("admin"));
            else {
                //从.net获取商户管理员userId
                ResultJson resultJson = shopNetService.getShopInfo(shopId);
                if (resultJson.getStatus() == StatusConstant.SUCCESS.getCode()){
                    Map<String,Object> shopInfo1 = (Map<String, Object>) resultJson.getData();
                    shopInfo=new HashMap<>();
                    for (String key: shopInfo1.keySet()){
                        shopInfo.put(key,shopInfo1.get(key).toString());
                    }
                    logger.debug("从.net取回的商户资料:{}",shopInfo);
                    //重新保存到缓存
                    jedis.hmset(RedisKey.SHOP_INFO+shopId,shopInfo);
                    //设置缓存过期时间为1天
                    jedis.expire(RedisKey.SHOP_INFO+shopId,3600*24);
                    shopAdmin.put("admin",shopInfo.get("admin"));
                }else
                    return resultJson;
            }
            return new ResultJson(shopAdmin);

        }finally {
            redisDao.close(jedis);
        }
    }

    /**
     * 获取商户员工清单
     * @param shopId
     * @return
     */
    @ApiOperation(value = "获取商户员工清单", notes = "获取商户员工清单",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopId", value = "商户的shopId", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping("/staffs")
    public ResultJson getShopStaffs(@RequestParam String shopId){
        //从.net获取商户所有员工userId
        ResultJson resultJson = shopNetService.getShopStaffs(shopId);
        return resultJson;
    }

    /**
     * 设置员工所属商户ID
     * @param shopId
     * @param userId
     * @return
     */
    @ApiOperation(value = "设置员工所属商户ID", notes = "设置员工所属商户ID",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopId", value = "商户的shopId", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "员工userId", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping("/staff/set")
    public ResultJson setStaffShop(@RequestParam String shopId,
                                   @RequestParam String userId){
        Jedis jedis = redisDao.getJedis();
        try {
            jedis.set(RedisKey.USER_SHOPID+userId,shopId);
            return new ResultJson();
        }finally {
            redisDao.close(jedis);
        }
    }

    /**
     * 获取员工所属商户ID
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取员工所属商户ID", notes = "获取员工所属商户ID",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "员工userId", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping("/staff/get")
    public ResultJson getStaffShop(@RequestParam String userId){
        Jedis jedis = redisDao.getJedis();
        try {
            String shopId = jedis.get(RedisKey.USER_SHOPID+userId);
            Map<String,String> map = new HashMap<>();
            map.put("userId",userId);
            map.put("shopId",shopId);
            return new ResultJson(map);
        }finally {
            redisDao.close(jedis);
        }
    }

}
