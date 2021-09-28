package com.ququ.imserver.customerservice.controller;

import com.ququ.common.result.ResultJson;
import com.ququ.imserver.customerservice.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(tags = "客服群处理模块")
@RestController
@RequestMapping("/im/customerservice")
public class CustomerServiceController {

    private Logger logger = LoggerFactory.getLogger(CustomerServiceController.class);

    @Autowired
    private CustomerService customerService;

    @ApiOperation(value = "创建客服群接口", notes = "创建客服群接口",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopId", value = "客服商户的shopId", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "customerId", value = "咨询的客户的userId", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping("/create")
    public ResultJson createGroup(@RequestParam String shopId,
                                  @RequestParam String customerId){
        Map<String,Object> customerServiceGroup = customerService.createCustomerServiceGroup(shopId, customerId);
        logger.debug("建客服群的结果:{}",customerServiceGroup);
        return new ResultJson(customerServiceGroup);
    }


    @ApiOperation(value = "修改客服群资料接口", notes = "修改客服群资料接口",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "客服群的群号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "groupName", value = "客服群名称", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "announcement", value = "客服群公告", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "intro", value = "客服群介绍", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "custom", value = "自定义高级群扩展属性，JSON格式。SDK中查询群信息时可以取到", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "iconUrl", value = "客服群头像地址", required = false, paramType = "query", dataType = "String"),
    })
    @RequestMapping("/update")
    public ResultJson updateGroup(@RequestParam String groupId,
                                  @RequestParam(required = false) String groupName,
                                  @RequestParam(required = false) String announcement,
                                  @RequestParam(required = false) String intro,
                                  @RequestParam(required = false) String custom,
                                  @RequestParam(required = false) String iconUrl){
        Map<String,Object> result = customerService.updateGroup(groupId, groupName, announcement, intro, custom, iconUrl);
        return new ResultJson(Integer.parseInt(result.get("status").toString()),result.get("msg").toString());
    }

}
