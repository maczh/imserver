package com.ququ.imserver.api.neteaseim.controller;

import com.alibaba.fastjson.JSON;
import com.ququ.common.result.ResultJson;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Macro on 2017/1/11.
 */
@Scope("prototype")
@RestController
public class ErrorPageHandler implements ErrorController {

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
