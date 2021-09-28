package com.ququ.imserver.imapi.controller;

import com.alibaba.fastjson.JSON;
import com.ququ.common.exception.QuQuException;
import com.ququ.common.result.ResultJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Macro on 2017/1/11.
 */
@Scope("prototype")
@RestController
@ControllerAdvice
public class GlobalExceptHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String defaultErrorHandler(HttpServletRequest request, HttpServletResponse response,Exception e){
        e.printStackTrace();
        String errmsg=e.getMessage();
        int errcode=-1;
        if (errmsg != null && errmsg.indexOf("xception:") > 0){
                errmsg = e.getMessage().substring(e.getMessage().indexOf("ion:")+4,e.getMessage().indexOf('\r'));
        }
        ResultJson resultJson = new ResultJson(errcode,errmsg);
        response.addHeader("Content-Type","application/json;charset=UTF-8");
        return JSON.toJSONString(resultJson);
    }

    @ExceptionHandler(value = QuQuException.class)
    @ResponseBody
    public String ququExceptHandler(HttpServletRequest request, HttpServletResponse response,QuQuException e){
        e.printStackTrace();
        ResultJson resultJson = new ResultJson(e.getCode(),e.getMessage());
        response.addHeader("Content-Type","application/json;charset=UTF-8");
        return JSON.toJSONString(resultJson);
    }

    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public String nullPointerExceptHandler(HttpServletRequest request, HttpServletResponse response,NullPointerException e){
        e.printStackTrace();
        ResultJson resultJson = new ResultJson(-2,"空指针系统异常:"+e.getMessage());
        response.addHeader("Content-Type","application/json;charset=UTF-8");
        return JSON.toJSONString(resultJson);
    }
}
