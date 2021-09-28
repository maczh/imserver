package com.ququ.imserver.customerservice.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ququ.common.result.ResultJson;
import com.ququ.imserver.constant.NeaseImErrorCode;
import com.ququ.imserver.constant.RedisKey;
import com.ququ.imserver.constant.StatusConstant;
import com.ququ.imserver.customerservice.redis.RedisDao;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    private Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private ImApiService imApiService;

    @Autowired
    private ImShopService imShopService;

    @Autowired
    private ImUserService imUserService;

    @Autowired
    private RedisDao redisDao;



    private Map<String,Object> errorMap(int status,String msg){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("status",status);
        resultMap.put("msg",msg);
        return resultMap;
    }


    /**
     * 创建客服群
     * @param shopId
     * @param customerId
     * @return
     */
    public Map<String,Object> createCustomerServiceGroup(String shopId,String customerId){
        Jedis jedis = redisDao.getJedis();
        try {
            Map<String,Object> groupInfoMap = null;
            String groupId = jedis.get(RedisKey.SHOP_CUSTOMER_GROUPID+shopId+":"+customerId);
            if (StringUtils.isNotBlank(groupId)){
                groupInfoMap = imApiService.queryGroupDetail(groupId);
                logger.debug("客服群已存在，返回结果:{}",groupInfoMap);
                return groupInfoMap;
            }else {
                //新建客服群,获取商户信息
                ResultJson shopInfoResult = imShopService.getShopInfo(shopId);
                if (shopInfoResult == null || shopInfoResult.getStatus() != StatusConstant.SUCCESS.getCode())
                    errorMap(StatusConstant.SHOP_UNAVAILABLE.getCode(),StatusConstant.SHOP_UNAVAILABLE.getMsg());
                Map<String,Object> shopInfo = (Map<String, Object>) shopInfoResult.getData();
                String shopName = shopInfo.get("shopname").toString();
                Map<String,Object> customMap = new HashMap<>();
                customMap.put("ServiceGroup",1);
                customMap.put("ShopId",shopId);
                customMap.put("ShopName",shopName);
                customMap.put("ServiceMemberNick",shopInfo.get("customer:service:nick"));
                customMap.put("ServiceMemberIconUrl",shopInfo.get("customer:service:icon"));
                String adminUserId = shopInfo.get("admin").toString();
                //获取管理员imUid
                ResultJson adminInfoResult = imUserService.getImUid(adminUserId);
                if (adminInfoResult == null || adminInfoResult.getStatus() != StatusConstant.SUCCESS.getCode())
                    errorMap(StatusConstant.USER_UNAVAILABLE.getCode(),StatusConstant.USER_UNAVAILABLE.getMsg());
                Map<String,Object> adminInfo = (Map<String, Object>) adminInfoResult.getData();
                String owner = adminInfo.get("imUid").toString();
                //获取商户客服成员userId
                ResultJson contactStaffsResult = imShopService.listShopContactStaffs(shopId);
                if (contactStaffsResult == null || contactStaffsResult.getStatus() != StatusConstant.SUCCESS.getCode())
                    errorMap(StatusConstant.SHOP_UNAVAILABLE.getCode(),StatusConstant.SHOP_UNAVAILABLE.getMsg());
                List<Map> contactStaffDetailList = (List<Map>) contactStaffsResult.getData();
                List<String> contactStaffList = new ArrayList<>();
                if (contactStaffDetailList==null || contactStaffDetailList.size()==0) {
                    //没有客服成员时管理员默认自动成为客服
                    contactStaffList.add(adminUserId);
                }else {
                    for (Map<String,String> contactStaff : contactStaffDetailList)
                        contactStaffList.add(contactStaff.get("userId"));
                }
                //获取所有客服成员imUid
                String contactStaffUserIds = JSONArray.toJSONString(contactStaffList);
                ResultJson contactStaffImuidResult = imUserService.getImUids(contactStaffUserIds);
                if (contactStaffImuidResult == null || contactStaffImuidResult.getStatus() != StatusConstant.SUCCESS.getCode())
                    errorMap(StatusConstant.USER_UNAVAILABLE.getCode(),StatusConstant.USER_UNAVAILABLE.getMsg());
                List<Map> contactStaffImuidMapList = (List<Map>) contactStaffImuidResult.getData();
                List<String> contactStaffImuidList = new ArrayList<>();
                for (Map<String,String> contactStaff : contactStaffImuidMapList){
                    contactStaffImuidList.add(contactStaff.get("imUid"));
                }
                customMap.put("ServiceMembers",contactStaffImuidList);
                List<String> memberList = new ArrayList<>(contactStaffImuidList);
                //获取客户个人信息
                ResultJson customerUserInfoResult = imUserService.getUserInfoByUserId(customerId);
                if (customerUserInfoResult == null || customerUserInfoResult.getStatus() != StatusConstant.SUCCESS.getCode())
                    errorMap(StatusConstant.USER_UNAVAILABLE.getCode(),StatusConstant.USER_UNAVAILABLE.getMsg());
                Map<String,Object> customerUserInfo = (Map<String, Object>) customerUserInfoResult.getData();
                String customerName = customerUserInfo.get("name").toString();
                //获取客户imUid
                String customerImuid = customerUserInfo.get("imUid").toString();
                memberList.add(customerImuid);
                customMap.put("Customer",customerImuid);
                customMap.put("CustomerName",customerName);
                customMap.put("CustomerUserId",customerId);
                //获取客户所属商户shopId
                ResultJson customerShopResult = imShopService.getStaffShop(customerId);
                if (customerShopResult == null || customerShopResult.getStatus() != StatusConstant.SUCCESS.getCode())
                    errorMap(StatusConstant.USER_IS_NOT_STAFF_OF_SHOP.getCode(),StatusConstant.USER_IS_NOT_STAFF_OF_SHOP.getMsg());
                Map<String,String> customerShopIdMap = (Map<String, String>) customerShopResult.getData();
                String customerShopId = customerShopIdMap.get("shopId");
                //获取客户商户信息
                ResultJson customerShopInfoResult = imShopService.getShopInfo(customerShopId);
                if (customerShopInfoResult == null || customerShopInfoResult.getStatus() != StatusConstant.SUCCESS.getCode())
                    errorMap(StatusConstant.SHOP_UNAVAILABLE.getCode(),StatusConstant.SHOP_UNAVAILABLE.getMsg());
                Map<String,String> customerShopInfo = (Map<String, String>) customerShopInfoResult.getData();
                String customerShopName = customerShopInfo.get("shopname");
                customMap.put("CustomerShopId",customerShopId);
                customMap.put("CustomerShopName",customerShopName);
                //建群，获取群号
                Map<String,Object> groupInfo = imApiService.createGroup(shopName+"-"+customerName,
                        owner,
                        JSONArray.toJSONString(memberList),
                        shopName+"欢迎您!",
                        null,
                        null,
                        0,
                        0,
                        JSON.toJSONString(customMap),
                        shopInfo.get("logo").toString(),
                        1,
                        1,
                        1,
                        1);
                if (groupInfo != null && Integer.parseInt(groupInfo.get("status").toString()) == StatusConstant.SUCCESS.getCode())
                    groupId = groupInfo.get("groupId").toString();
                //保存群号
                jedis.set(RedisKey.SHOP_CUSTOMER_GROUPID+shopId+":"+customerId,groupId);
                jedis.hset(RedisKey.GROUP_CUSTOMERSERVICE+groupId,"shopId",shopId);
                jedis.hset(RedisKey.GROUP_CUSTOMERSERVICE+groupId,"customerId",customerId);
                groupInfoMap = imApiService.queryGroupDetail(groupId);
                if (groupInfoMap == null || groupInfoMap.isEmpty() || Integer.parseInt(groupInfo.get("status").toString()) != StatusConstant.SUCCESS.getCode())
                    return errorMap(StatusConstant.GROUP_UNAVAILABLE.getCode(),StatusConstant.GROUP_UNAVAILABLE.getMsg());
                logger.debug("客服群已创建完成，返回结果:{}",groupInfoMap);
                Map<String,Object> groupDetail = (Map<String, Object>) groupInfoMap.get("groupDetail");
                return groupDetail;
            }
        }finally {
            redisDao.close(jedis);
        }
    }


    /**
     * 更新客服群资料
     * @param groupId
     * @param groupName
     * @param announcement
     * @param intro
     * @param custom
     * @param iconUrl
     * @return
     */
    public Map<String,Object> updateGroup(String groupId,String groupName, String announcement,String intro,String custom,String iconUrl){
        Map<String,Object> resultMap = new HashMap<>();
        Map<String,Object> groupInfoQuery = imApiService.queryGroupInfo(null,groupId,0);
        if (groupInfoQuery == null || groupInfoQuery.isEmpty() || Integer.parseInt(groupInfoQuery.get("status").toString()) != StatusConstant.SUCCESS.getCode())
            return errorMap(StatusConstant.GROUP_UNAVAILABLE.getCode(),StatusConstant.GROUP_UNAVAILABLE.getMsg());
        List<Map> groupList = (List<Map>) groupInfoQuery.get("groupInfos");
        Map<String,Object> groupInfo = groupList.get(0);
        String owner = groupInfo.get("owner").toString();
        Map<String,Object> result = imApiService.updateGroup(groupId,groupName,owner,announcement,intro,null,custom,iconUrl,null,null,null,null);
        if (Integer.parseInt(result.get("code").toString()) == NeaseImErrorCode.SUCCESS.getCode()){
            resultMap.put("status",StatusConstant.SUCCESS.getCode());
            resultMap.put("msg",StatusConstant.SUCCESS.getMsg());
        }else {
            resultMap.put("status",result.get("code"));
            resultMap.put("msg",StatusConstant.getErrorMsg(Integer.parseInt(result.get("code").toString())));
        }
        return resultMap;
    }
}
