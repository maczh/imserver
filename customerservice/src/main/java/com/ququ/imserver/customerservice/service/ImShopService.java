package com.ququ.imserver.customerservice.service;

import com.ququ.common.result.ResultJson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "imserver-shop")
public interface ImShopService {

    @RequestMapping(value = "/im/shop/contactstaffs/list",method = RequestMethod.POST)
    ResultJson listShopContactStaffs(@RequestParam(value = "shopId") String shopId);


    @RequestMapping(value = "/im/shop/info",method = RequestMethod.POST)
    ResultJson getShopInfo(@RequestParam(value = "shopId") String shopId);

    @RequestMapping(value = "/im/shop/admin",method = RequestMethod.POST)
    ResultJson getShopAdmin(@RequestParam(value = "shopId") String shopId);

    @RequestMapping(value = "/im/shop/staff/get",method = RequestMethod.POST)
    ResultJson getStaffShop(@RequestParam(value = "userId") String userId);
}
