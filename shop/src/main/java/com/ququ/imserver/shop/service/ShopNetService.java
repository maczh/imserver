package com.ququ.imserver.shop.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ququ.common.result.ResultJson;
import com.ququ.common.utils.OkHttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShopNetService {

    private Logger logger = LoggerFactory.getLogger(ShopNetService.class);

    private String SHOP_API_URL_BASE = "http://116.62.37.221:8080/User/UserInfo/";

    private String SHOP_INFO_URL = "GetShop4IM";

    private String SHOP_STAFFS_URL = "GetEmployeeIds4IM";

    @Autowired
    private ImUserService imUserService;

    public ResultJson getShopInfo(String shopId){
        Map<String,Object> params = new HashMap<>();
        params.put("shopId",shopId);
        String result = OkHttpClientUtil.post(SHOP_API_URL_BASE+SHOP_INFO_URL,params,null);
        logger.debug("商户号查询结果:{}",result);
        ResultJson resultJson = JSON.parseObject(result,ResultJson.class);
        return resultJson;
    }


    public ResultJson getShopStaffs(String shopId){
        Map<String,Object> params = new HashMap<>();
        params.put("shopId",shopId);
        String result = OkHttpClientUtil.post(SHOP_API_URL_BASE+SHOP_STAFFS_URL,params,null);
        logger.debug("商户员工号查询结果:{}",result);
        ResultJson resultJson = JSON.parseObject(result,ResultJson.class);
        List<Integer> userIds = (List<Integer>) resultJson.getData();
        List<String> userIdList = new ArrayList<>();
        for (Integer uid : userIds)
            userIdList.add(uid.toString());
        ResultJson userListResult = imUserService.getUsersInfoByUserId(JSONArray.toJSONString(userIdList));
        return userListResult;
    }

}
