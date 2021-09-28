package com.ququ.imserver.imapi.service;

import com.ququ.imserver.constant.NeaseImErrorCode;
import com.ququ.imserver.constant.StatusConstant;
import com.ququ.imserver.imapi.redis.ImApiSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImApiBusinessService {
    
    private Logger logger = LoggerFactory.getLogger(ImApiBusinessService.class);
    
    
    
    @Autowired
    private NeteaseImApiService neteaseImApiService;
    

    public Map<String,Object> register(String imUid,String name,String iconUrl,Integer sex,String mobile,String extend){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            switch (ImApiSetting.getImApiUsing()) {
                case "neteaseim":
                    Map<String, Object> map = neteaseImApiService.registerUser(imUid, name, iconUrl, sex, mobile, extend);
                    if (map != null && map.containsKey("code")){
                        if (Integer.parseInt(map.get("code").toString()) == NeaseImErrorCode.SUCCESS.getCode()){
                            //云信注册成功
                            Map<String,String> infoMap = (Map<String, String>) map.get("info");
                            resultMap.put("status",StatusConstant.SUCCESS.getCode());
                            resultMap.put("msg",StatusConstant.SUCCESS.getMsg());
                            resultMap.put("imUid",imUid);
                            resultMap.put("imToken",infoMap.get("token"));
                        }else {
                            resultMap.put("status",map.get("code"));
                            resultMap.put("msg",StatusConstant.getErrorMsg(Integer.parseInt(map.get("code").toString())));
                        }
                    }else {
                        resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
                        resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+"网络故障");
                    }
                    break;
                default:
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
            resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+e.getMessage());
        }
        return resultMap;
    }


    public Map<String,Object> updateUser(String imUid,String name,String iconUrl,Integer sex,String mobile,String email,String birth,String personSign,String extend){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            switch (ImApiSetting.getImApiUsing()) {
                case "neteaseim":
                    Map<String, Object> map = neteaseImApiService.updateUser(imUid, name, iconUrl, sex, mobile,email,birth,personSign,extend);
                    if (map != null && map.containsKey("code")){
                        if (Integer.parseInt(map.get("code").toString()) == NeaseImErrorCode.SUCCESS.getCode()){
                            //云信更新用户信息成功
                            resultMap.put("status",StatusConstant.SUCCESS.getCode());
                            resultMap.put("msg",StatusConstant.SUCCESS.getMsg());
                        }else {
                            resultMap.put("status",map.get("code"));
                            resultMap.put("msg",StatusConstant.getErrorMsg(Integer.parseInt(map.get("code").toString())));
                        }
                    }else {
                        resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
                        resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+"网络故障");
                    }
                    break;
                default:
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
            resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+e.getMessage());
        }
        return resultMap;
    }


    public Map<String,Object> createGroup(String groupName, String owner, String members, String msg, String announcement,
                                                 String intro, Integer magree, Integer joinmode, String custom, String iconUrl,
                                                 Integer beinvitemode, Integer invitemode, Integer uptinfomode, Integer upcustommode){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            switch (ImApiSetting.getImApiUsing()) {
                case "neteaseim":
                    Map<String, Object> map = neteaseImApiService.createGroup(groupName, owner, members, msg, announcement, intro, magree, joinmode, custom, iconUrl, beinvitemode, invitemode, uptinfomode, upcustommode);
                    if (map != null && map.containsKey("code")){
                        if (Integer.parseInt(map.get("code").toString()) == NeaseImErrorCode.SUCCESS.getCode()){
                            //云信建群成功
                            resultMap.put("status",StatusConstant.SUCCESS.getCode());
                            resultMap.put("msg",StatusConstant.SUCCESS.getMsg());
                            resultMap.put("groupId",map.get("tid"));
                        }else {
                            resultMap.put("status",map.get("code"));
                            resultMap.put("msg",StatusConstant.getErrorMsg(Integer.parseInt(map.get("code").toString())));
                        }
                    }else {
                        resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
                        resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+"网络故障");
                    }
                    break;
                default:
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
            resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+e.getMessage());
        }
        return resultMap;
    }


    public Map<String,Object> addToGroup(String groupId,String owner,String members,String msg,Integer magree,String attach){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            switch (ImApiSetting.getImApiUsing()) {
                case "neteaseim":
                    Map<String, Object> map = neteaseImApiService.addToGroup(groupId, owner, members, msg, magree, attach);
                    if (map != null && map.containsKey("code")){
                        if (Integer.parseInt(map.get("code").toString()) == NeaseImErrorCode.SUCCESS.getCode()){
                            //云信拉人入群成功
                            resultMap.put("status",StatusConstant.SUCCESS.getCode());
                            resultMap.put("msg",StatusConstant.SUCCESS.getMsg());
                        }else {
                            resultMap.put("status",map.get("code"));
                            resultMap.put("msg",StatusConstant.getErrorMsg(Integer.parseInt(map.get("code").toString())));
                        }
                    }else {
                        resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
                        resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+"网络故障");
                    }
                    break;
                default:
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
            resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+e.getMessage());
        }
        return resultMap;
    }



    public Map<String,Object> kickFromGroup(String groupId,String owner,String members,String member,String attach){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            switch (ImApiSetting.getImApiUsing()) {
                case "neteaseim":
                    Map<String, Object> map = neteaseImApiService.kickFromGroup(groupId, owner, members, member, attach);
                    if (map != null && map.containsKey("code")){
                        if (Integer.parseInt(map.get("code").toString()) == NeaseImErrorCode.SUCCESS.getCode()){
                            //云信踢人成功
                            resultMap.put("status",StatusConstant.SUCCESS.getCode());
                            resultMap.put("msg",StatusConstant.SUCCESS.getMsg());
                        }else {
                            resultMap.put("status",map.get("code"));
                            resultMap.put("msg",StatusConstant.getErrorMsg(Integer.parseInt(map.get("code").toString())));
                        }
                    }else {
                        resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
                        resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+"网络故障");
                    }
                    break;
                default:
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
            resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+e.getMessage());
        }
        return resultMap;
    }

    public Map<String,Object> removeGroup(String groupId,String owner) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            switch (ImApiSetting.getImApiUsing()) {
                case "neteaseim":
                    Map<String, Object> map = neteaseImApiService.removeGroup(groupId, owner);
                    if (map != null && map.containsKey("code")){
                        if (Integer.parseInt(map.get("code").toString()) == NeaseImErrorCode.SUCCESS.getCode()){
                            resultMap.put("status",StatusConstant.SUCCESS.getCode());
                            resultMap.put("msg",StatusConstant.SUCCESS.getMsg());
                        }else {
                            resultMap.put("status",map.get("code"));
                            resultMap.put("msg",StatusConstant.getErrorMsg(Integer.parseInt(map.get("code").toString())));
                        }
                    }else {
                        resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
                        resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+"网络故障");
                    }
                    break;
                default:
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
            resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+e.getMessage());
        }
        return resultMap;
    }



    public Map<String,Object> updateGroup(String groupId,String groupName,String owner,String announcement,
                                          String intro,Integer joinmode,String custom,String iconUrl,
                                          Integer beinvitemode,Integer invitemode,Integer uptinfomode,Integer upcustommode) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            switch (ImApiSetting.getImApiUsing()) {
                case "neteaseim":
                    Map<String, Object> map = neteaseImApiService.updateGroup(groupId, groupName, owner, announcement, intro, joinmode, custom, iconUrl, beinvitemode, invitemode, uptinfomode, upcustommode);
                    if (map != null && map.containsKey("code")){
                        if (Integer.parseInt(map.get("code").toString()) == NeaseImErrorCode.SUCCESS.getCode()){
                            resultMap.put("status",StatusConstant.SUCCESS.getCode());
                            resultMap.put("msg",StatusConstant.SUCCESS.getMsg());
                        }else {
                            resultMap.put("status",map.get("code"));
                            resultMap.put("msg",StatusConstant.getErrorMsg(Integer.parseInt(map.get("code").toString())));
                        }
                    }else {
                        resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
                        resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+"网络故障");
                    }
                    break;
                default:
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
            resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+e.getMessage());
        }
        return resultMap;
    }



    public Map<String,Object> queryGroupInfo(String groupIds,String groupId,int listMembers) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            switch (ImApiSetting.getImApiUsing()) {
                case "neteaseim":
                    Map<String, Object> map = neteaseImApiService.queryGroupInfo(groupIds, groupId, listMembers);
                    if (map != null && map.containsKey("code")){
                        if (Integer.parseInt(map.get("code").toString()) == NeaseImErrorCode.SUCCESS.getCode()){
                            resultMap.put("status",StatusConstant.SUCCESS.getCode());
                            resultMap.put("msg",StatusConstant.SUCCESS.getMsg());
                            List<Map> groupList = (List<Map>) map.get("tinfos");
                            for (Map<String,Object> groupInfo : groupList){
                                groupInfo.put("groupId",groupInfo.get("tid"));
                                groupInfo.put("groupName",groupInfo.get("tname"));
                                groupInfo.remove("tid");
                                groupInfo.remove("tname");
                            }
                            resultMap.put("groupInfos",groupList);
                        }else {
                            resultMap.put("status",map.get("code"));
                            resultMap.put("msg",StatusConstant.getErrorMsg(Integer.parseInt(map.get("code").toString())));
                        }
                    }else {
                        resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
                        resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+"网络故障");
                    }
                    break;
                default:
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
            resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+e.getMessage());
        }
        return resultMap;
    }


    public Map<String,Object> queryGroupDetail(String groupId) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            switch (ImApiSetting.getImApiUsing()) {
                case "neteaseim":
                    Map<String, Object> map = neteaseImApiService.queryGroupDetail(groupId);
                    if (map != null && map.containsKey("code")){
                        if (Integer.parseInt(map.get("code").toString()) == NeaseImErrorCode.SUCCESS.getCode()){
                            resultMap.put("status",StatusConstant.SUCCESS.getCode());
                            resultMap.put("msg",StatusConstant.SUCCESS.getMsg());
                            Map<String,Object> info = (Map<String, Object>) map.get("tinfo");
                            info.put("groupId",info.get("tid"));
                            info.remove("tid");
                            info.put("groupName",info.get("tname"));
                            info.remove("tname");
                            Map<String,Object> owner = (Map<String, Object>) info.get("owner");
                            owner.put("imUid",owner.get("accid"));
                            owner.remove("accid");
                            info.put("owner",owner);
                            List<Map> adminList = (List<Map>) info.get("admins");
                            if (adminList.size()>0){
                                for (Map<String,Object> user : adminList){
                                    user.put("imUid",user.get("accid"));
                                    user.remove("accid");
                                }
                            }
                            info.put("admins",adminList);
                            List<Map> memberList = (List<Map>) info.get("members");
                            for (Map<String,Object> user : memberList){
                                user.put("imUid",user.get("accid"));
                                user.remove("accid");
                            }
                            info.put("members",memberList);
                            resultMap.put("groupDetail",info);
                        }else {
                            resultMap.put("status",map.get("code"));
                            resultMap.put("msg",StatusConstant.getErrorMsg(Integer.parseInt(map.get("code").toString())));
                        }
                    }else {
                        resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
                        resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+"网络故障");
                    }
                    break;
                default:
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
            resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+e.getMessage());
        }
        return resultMap;
    }


    public Map<String,Object> changeGroupOwner(String groupId,String owner,String newOwner,int leave) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            switch (ImApiSetting.getImApiUsing()) {
                case "neteaseim":
                    Map<String, Object> map = neteaseImApiService.changeGroupOwner(groupId, owner, newOwner, leave);
                    if (map != null && map.containsKey("code")){
                        if (Integer.parseInt(map.get("code").toString()) == NeaseImErrorCode.SUCCESS.getCode()){
                            resultMap.put("status",StatusConstant.SUCCESS.getCode());
                            resultMap.put("msg",StatusConstant.SUCCESS.getMsg());
                        }else {
                            resultMap.put("status",map.get("code"));
                            resultMap.put("msg",StatusConstant.getErrorMsg(Integer.parseInt(map.get("code").toString())));
                        }
                    }else {
                        resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
                        resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+"网络故障");
                    }
                    break;
                default:
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
            resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+e.getMessage());
        }
        return resultMap;
    }


    public Map<String,Object> addGroupAdmin(String groupId, String owner,String members) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            switch (ImApiSetting.getImApiUsing()) {
                case "neteaseim":
                    Map<String, Object> map = neteaseImApiService.addGroupAdmin(groupId, owner, members);
                    if (map != null && map.containsKey("code")){
                        if (Integer.parseInt(map.get("code").toString()) == NeaseImErrorCode.SUCCESS.getCode()){
                            resultMap.put("status",StatusConstant.SUCCESS.getCode());
                            resultMap.put("msg",StatusConstant.SUCCESS.getMsg());
                        }else {
                            resultMap.put("status",map.get("code"));
                            resultMap.put("msg",StatusConstant.getErrorMsg(Integer.parseInt(map.get("code").toString())));
                        }
                    }else {
                        resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
                        resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+"网络故障");
                    }
                    break;
                default:
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
            resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+e.getMessage());
        }
        return resultMap;
    }



    public Map<String,Object> delGroupAdmin(String groupId, String owner,String members) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            switch (ImApiSetting.getImApiUsing()) {
                case "neteaseim":
                    Map<String, Object> map = neteaseImApiService.delGroupAdmin(groupId, owner, members);
                    if (map != null && map.containsKey("code")){
                        if (Integer.parseInt(map.get("code").toString()) == NeaseImErrorCode.SUCCESS.getCode()){
                            resultMap.put("status",StatusConstant.SUCCESS.getCode());
                            resultMap.put("msg",StatusConstant.SUCCESS.getMsg());
                        }else {
                            resultMap.put("status",map.get("code"));
                            resultMap.put("msg",StatusConstant.getErrorMsg(Integer.parseInt(map.get("code").toString())));
                        }
                    }else {
                        resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
                        resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+"网络故障");
                    }
                    break;
                default:
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
            resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+e.getMessage());
        }
        return resultMap;
    }



    public Map<String,Object> listMyGroups(String imUid) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            switch (ImApiSetting.getImApiUsing()) {
                case "neteaseim":
                    Map<String, Object> map = neteaseImApiService.listMyGroups(imUid);
                    if (map != null && map.containsKey("code")){
                        if (Integer.parseInt(map.get("code").toString()) == NeaseImErrorCode.SUCCESS.getCode()){
                            resultMap.put("status",StatusConstant.SUCCESS.getCode());
                            resultMap.put("msg",StatusConstant.SUCCESS.getMsg());
                            List<Map> groupList = (List<Map>) map.get("infos");
                            for (Map<String,Object> group : groupList){
                                group.put("groupId",group.get("tid"));
                                group.put("groupName",group.get("tname"));
                                group.remove("tid");
                                group.remove("tname");
                            }
                            resultMap.put("groups",groupList);
                        }else {
                            resultMap.put("status",map.get("code"));
                            resultMap.put("msg",StatusConstant.getErrorMsg(Integer.parseInt(map.get("code").toString())));
                        }
                    }else {
                        resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
                        resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+"网络故障");
                    }
                    break;
                default:
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
            resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+e.getMessage());
        }
        return resultMap;
    }


    public Map<String,Object> sendMessage(String fromImUid,String toImUid,int isGroupMessage,int messageType,String body,String option,
                                          String iosPushContent,String iosPushPayload,String extend) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            switch (ImApiSetting.getImApiUsing()) {
                case "neteaseim":
                    Map<String, Object> map = neteaseImApiService.sendMessage(fromImUid, toImUid, isGroupMessage, messageType, body, option, iosPushContent, iosPushPayload, extend);
                    if (map != null && map.containsKey("code")){
                        if (Integer.parseInt(map.get("code").toString()) == NeaseImErrorCode.SUCCESS.getCode()){
                            resultMap.put("status",StatusConstant.SUCCESS.getCode());
                            resultMap.put("msg",StatusConstant.SUCCESS.getMsg());
                            Map<String,Object> data = (Map<String, Object>) map.get("data");
                            resultMap.put("messageId",data.get("msgid"));
                        }else {
                            resultMap.put("status",map.get("code"));
                            resultMap.put("msg",StatusConstant.getErrorMsg(Integer.parseInt(map.get("code").toString())));
                        }
                    }else {
                        resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
                        resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+"网络故障");
                    }
                    break;
                default:
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("status",StatusConstant.SYSTEM_ERROR.getCode());
            resultMap.put("msg",StatusConstant.SYSTEM_ERROR.getMsg()+e.getMessage());
        }
        return resultMap;
    }



}
