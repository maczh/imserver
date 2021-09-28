package com.ququ.imserver.api.neteaseim.controller;

import com.alibaba.fastjson.JSONArray;
import com.ququ.imserver.api.neteaseim.apiclient.NeaseImErrorCode;
import com.ququ.imserver.api.neteaseim.apiclient.NeteaseImApi;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/im/api")
public class NeteaseImController {

    private Logger logger = LoggerFactory.getLogger(NeteaseImController.class);

    @Autowired
    private NeteaseImApi neteaseImApi;
    
    @RequestMapping("/user/register")
    public Map registerIm(@RequestParam String imUid,
                          @RequestParam String name,
                          @RequestParam(required = false) String iconUrl,
                          @RequestParam(required = false) String token,
                          @RequestParam(defaultValue = "0") Integer sex,
                          @RequestParam(required = false) String mobile,
                          @RequestParam(required = false) String extend){
        return neteaseImApi.register(imUid,name,iconUrl,sex,mobile,null,null,null,null,extend,token);
    }


    @RequestMapping("/user/update")
    public Map updateImUserInfo(@RequestParam String imUid,
                          @RequestParam String name,
                          @RequestParam(required = false) String iconUrl,
                          @RequestParam(defaultValue = "0") Integer sex,
                          @RequestParam(required = false) String mobile,
                          @RequestParam(required = false) String email,
                          @RequestParam(required = false) String birth,
                          @RequestParam(required = false) String personSign,
                          @RequestParam(required = false) String extend){
        return neteaseImApi.updateUserInfo(imUid,name,iconUrl,sex,mobile,email,birth,personSign,extend);
    }

    @RequestMapping("/group/create")
    public Map createGroup(@RequestParam String groupName,
                           @RequestParam String owner,
                           @RequestParam String members,
                           @RequestParam(defaultValue = "欢迎") String msg,
                           @RequestParam(required = false) String announcement,
                           @RequestParam(required = false) String intro,
                           @RequestParam(required = false) Integer magree,
                           @RequestParam(required = false) Integer joinmode,
                           @RequestParam(required = false) String custom,
                           @RequestParam(required = false) String iconUrl,
                           @RequestParam(required = false) Integer beinvitemode,
                           @RequestParam(required = false) Integer invitemode,
                           @RequestParam(required = false) Integer uptinfomode,
                           @RequestParam(required = false) Integer upcustommode){
        List<String> memberList = JSONArray.parseArray(members,String.class);
        return neteaseImApi.createGroup(groupName,owner,memberList,msg,announcement,intro,magree,joinmode,custom,iconUrl,beinvitemode,invitemode,uptinfomode,upcustommode);
    }


    @RequestMapping("/group/add")
    public Map addToGroup(@RequestParam String groupId,
                          @RequestParam String owner,
                          @RequestParam String members,
                          @RequestParam String msg,
                          @RequestParam(required = false) Integer magree,
                          @RequestParam(required = false) String attach){
        List<String> memberList = JSONArray.parseArray(members,String.class);
        return neteaseImApi.addToGroup(groupId,owner,memberList,msg,magree,attach);
    }


    @RequestMapping("/group/kick")
    public Map kickFromGroup(@RequestParam String groupId,
                             @RequestParam String owner,
                             @RequestParam(required = false) String members,
                             @RequestParam(required = false) String member,
                             @RequestParam(required = false) String attach){
        List<String> memberList = JSONArray.parseArray(members,String.class);
        return neteaseImApi.kickFromGroup(groupId,owner,memberList,member,attach);
    }


    @RequestMapping("/group/remove")
    public Map removeGroup(@RequestParam String groupId,
                           @RequestParam String owner){
        return neteaseImApi.removeGroup(groupId, owner);
    }


    @RequestMapping("/group/update")
    public Map updateGroup(@RequestParam String groupId,
                           @RequestParam(required = false) String groupName,
                           @RequestParam String owner,
                           @RequestParam(required = false) String announcement,
                           @RequestParam(required = false) String intro,
                           @RequestParam(required = false) Integer joinmode,
                           @RequestParam(required = false) String custom,
                           @RequestParam(required = false) String iconUrl,
                           @RequestParam(required = false) Integer beinvitemode,
                           @RequestParam(required = false) Integer invitemode,
                           @RequestParam(required = false) Integer uptinfomode,
                           @RequestParam(required = false) Integer upcustommode){
        return neteaseImApi.updateGroup(groupId, groupName, owner, announcement, intro, joinmode, custom, iconUrl, beinvitemode, invitemode, uptinfomode, upcustommode);
    }


    @RequestMapping("/group/query")
    public Map queryGroupInfo(@RequestParam(required = false) String groupIds,
                              @RequestParam(required = false) String groupId,
                              @RequestParam(defaultValue = "0") int listMembers){
        if (StringUtils.isNotBlank(groupIds)) {
            List<String> groupList = JSONArray.parseArray(groupIds,String.class);
            return neteaseImApi.queryGroupInfo(groupList, listMembers);
        }
        if (StringUtils.isNotBlank(groupId))
            return neteaseImApi.queryGroupInfo(groupId,listMembers);
        else {
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("code",NeaseImErrorCode.GROUP_UNAVAILABLE.getCode());
            resultMap.put("msg",NeaseImErrorCode.GROUP_UNAVAILABLE.getMsg());
            return resultMap;
        }
    }


    @RequestMapping("/group/detail")
    public Map queryGroupDetail(@RequestParam String groupId){
        return neteaseImApi.queryGroupInfo(groupId);
    }


    @RequestMapping("/group/changeowner")
    public Map changeGroupOwner(@RequestParam String groupId,
                                @RequestParam String owner,
                                @RequestParam String newOwner,
                                @RequestParam(defaultValue = "2") int leave){
        return neteaseImApi.changeGroupOwner(groupId, owner, newOwner, leave);
    }


    @RequestMapping("/group/addadmin")
    public Map addGroupAdmin(@RequestParam String groupId,
                             @RequestParam String owner,
                             @RequestParam String members){
        List<String> memberList = JSONArray.parseArray(members,String.class);
        return neteaseImApi.addGroupAdmin(groupId,owner,memberList);
    }


    @RequestMapping("/group/deladmin")
    public Map delGroupAdmin(@RequestParam String groupId,
                             @RequestParam String owner,
                             @RequestParam String members){
        List<String> memberList = JSONArray.parseArray(members,String.class);
        return neteaseImApi.delGroupAdmin(groupId,owner,memberList);
    }


    @RequestMapping("/group/my")
    public Map listMyGroups(@RequestParam String imUid){
        return neteaseImApi.listMyGroups(imUid);
    }


    @RequestMapping("/message/send")
    public Map sendMessage(@RequestParam String fromImUid,
                           @RequestParam String toImUid,
                           @RequestParam int isGroupMessage,
                           @RequestParam int messageType,
                           @RequestParam String body,
                           @RequestParam(required = false) String option,
                           @RequestParam(required = false) String iosPushContent,
                           @RequestParam(required = false) String iosPushPayload,
                           @RequestParam(required = false) String extend){
        return neteaseImApi.sendMessage(fromImUid, toImUid, isGroupMessage, messageType, body, option, iosPushContent, iosPushPayload, extend);
    }

}
