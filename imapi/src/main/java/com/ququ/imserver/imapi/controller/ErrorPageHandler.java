package com.ququ.imserver.imapi.controller;

import com.alibaba.fastjson.JSON;
import com.ququ.common.result.ResultJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Macro on 2017/1/11.
 */
@Api(tags = "错误页面")
@Scope("prototype")
@RestController
public class ErrorPageHandler implements ErrorController {

    @ApiOperation(value = "错误处理", hidden = true)
    @RequestMapping(value = "error")
    @ResponseBody
    public String errorPage(HttpServletResponse response){
        ResultJson resultJson = new ResultJson(-1,"找不到这个url");
        response.addHeader("Content-Type","application/json;charset=UTF-8");
        return JSON.toJSONString(resultJson);
    }

    @Override
    public String getErrorPath() {
        return "";
    }
}
