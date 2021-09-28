package com.ququ.imserver.user.service;

import com.ququ.common.result.ResultJson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "imserver-shop")
public interface ImShopService {

    @RequestMapping(value = "/im/shop/info",method = RequestMethod.POST)
    ResultJson getShopInfo(@RequestParam(value = "shopId") String shopId);


    @RequestMapping(value = "/im/shop/staff/set",method = RequestMethod.POST)
    ResultJson setStaffShop(@RequestParam(value = "shopId") String shopId,
                            @RequestParam(value = "userId") String userId);

}
