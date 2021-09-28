package com.ququ.imserver.imapi.controller;

import com.ququ.imserver.imapi.service.ImApiBusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(tags = "消息发送功能")
@RestController
@RequestMapping("/im/message")
public class ImMessageController {

    @Autowired
    private ImApiBusinessService imApiBusinessService;

    @ApiOperation(value = "IM消息发送", notes = "IM消息发送",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fromImUid", value = "发送者imUid", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "toImUid", value = "接收者imUid或GroupId", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "isGroupMessage", value = "是否群消息：0：点对点个人消息，1：群消息（高级群）", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "messageType", value = "消息类型：0 表示文本消息,<br>\n" +
                    "1 表示图片，<br>\n" +
                    "2 表示语音，<br>\n" +
                    "3 表示视频，<br>\n" +
                    "4 表示地理位置信息，<br>\n" +
                    "6 表示文件，<br>\n" +
                    "100 自定义消息类型（特别注意，对于未对接易盾反垃圾功能的应用，该类型的消息不会提交反垃圾系统检测）", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "body", value = "消息内容，JSON格式，参见<a href=\"https://dev.yunxin.163.com/docs/product/IM%E5%8D%B3%E6%97%B6%E9%80%9A%E8%AE%AF/%E6%9C%8D%E5%8A%A1%E7%AB%AFAPI%E6%96%87%E6%A1%A3/%E6%B6%88%E6%81%AF%E5%8A%9F%E8%83%BD?#%E6%B6%88%E6%81%AF%E6%A0%BC%E5%BC%8F%E7%A4%BA%E4%BE%8B\">网易云信消息格式</a>", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "option", value = "发消息时特殊指定的行为选项,JSON格式，可用于指定消息的漫游，存云端历史，发送方多端同步，推送，消息抄送等特殊行为;option中字段不填时表示默认值", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "iosPushContent", value = "推送文案", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "iosPushPayload", value = "ios 推送对应的payload,必须是JSON,不能超过2k字符", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "extend", value = "开发者扩展字段，长度限制1024字符", required = false, paramType = "query", dataType = "String")
    })
    @RequestMapping("/send")
    public Map<String,Object> sendMessage(@RequestParam String fromImUid,
                                           @RequestParam String toImUid,
                                           @RequestParam(defaultValue = "0") Integer isGroupMessage,
                                           @RequestParam(defaultValue = "0") Integer messageType,
                                           @RequestParam String body,
                                           @RequestParam(required = false) String option,
                                           @RequestParam(required = false) String iosPushContent,
                                           @RequestParam(required = false) String iosPushPayload,
                                           @RequestParam(required = false) String extend){
        return imApiBusinessService.sendMessage(fromImUid, toImUid, isGroupMessage, messageType, body, option, iosPushContent, iosPushPayload, extend);
    }

}
