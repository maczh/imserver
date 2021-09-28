package com.ququ.imserver.imapi.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "imserver-api-neteaseim")
public interface NeteaseImApiService {

    /**
     * IM用户注册
     * @param imUid
     * @param name
     * @param iconUrl
     * @param sex
     * @param mobile
     * @param extend
     * @return
     */
    @RequestMapping(value = "/im/api/user/register", method = RequestMethod.POST)
    Map<String, Object> registerUser(@RequestParam(value = "imUid") String imUid,
                                     @RequestParam(value = "name") String name,
                                     @RequestParam(value = "iconUrl") String iconUrl,
                                     @RequestParam(value = "sex") Integer sex,
                                     @RequestParam(value = "mobile") String mobile,
                                     @RequestParam(value = "extend") String extend);

    /**
     * IM用户资料更新
     * @param imUid
     * @param name
     * @param iconUrl
     * @param sex
     * @param mobile
     * @param email
     * @param birth
     * @param personSign
     * @param extend
     * @return
     */
    @RequestMapping(value = "/im/api/user/update",method = RequestMethod.POST)
    Map<String, Object> updateUser(@RequestParam(value = "imUid") String imUid,
                                   @RequestParam(value = "name") String name,
                                   @RequestParam(value = "iconUrl") String iconUrl,
                                   @RequestParam(value = "sex") Integer sex,
                                   @RequestParam(value = "mobile") String mobile,
                                   @RequestParam(value = "email") String email,
                                   @RequestParam(value = "birth") String birth,
                                   @RequestParam(value = "personSign") String personSign,
                                   @RequestParam(value = "extend") String extend);

    @RequestMapping(value = "/im/api/group/create",method = RequestMethod.POST)
    Map<String,Object> createGroup(@RequestParam(value = "groupName") String groupName,
                                   @RequestParam(value = "owner") String owner,
                                   @RequestParam(value = "members") String members,
                                   @RequestParam(value = "msg") String msg,
                                   @RequestParam(value = "announcement") String announcement,
                                   @RequestParam(value = "intro") String intro,
                                   @RequestParam(value = "magree") Integer magree,
                                   @RequestParam(value = "joinmode") Integer joinmode,
                                   @RequestParam(value = "custom") String custom,
                                   @RequestParam(value = "iconUrl") String iconUrl,
                                   @RequestParam(value = "beinvitemode") Integer beinvitemode,
                                   @RequestParam(value = "invitemode") Integer invitemode,
                                   @RequestParam(value = "uptinfomode") Integer uptinfomode,
                                   @RequestParam(value = "upcustommode") Integer upcustommode);


    @RequestMapping(value = "/im/api/group/add",method = RequestMethod.POST)
    Map<String,Object> addToGroup(@RequestParam(value = "groupId") String groupId,
                                  @RequestParam(value = "owner") String owner,
                                  @RequestParam(value = "members") String members,
                                  @RequestParam(value = "msg") String msg,
                                  @RequestParam(value = "magree") Integer magree,
                                  @RequestParam(value = "attach") String attach);

    @RequestMapping(value = "/im/api/group/kick",method = RequestMethod.POST)
    Map<String,Object> kickFromGroup(@RequestParam(value = "groupId") String groupId,
                                     @RequestParam(value = "owner") String owner,
                                     @RequestParam(value = "members") String members,
                                     @RequestParam(value = "member") String member,
                                     @RequestParam(value = "attach") String attach);


    @RequestMapping(value = "/im/api/group/remove",method = RequestMethod.POST)
    Map<String,Object> removeGroup(@RequestParam(value = "groupId") String groupId,
                                   @RequestParam(value = "owner") String owner);


    @RequestMapping(value = "/im/api/group/update",method = RequestMethod.POST)
    Map<String,Object> updateGroup(@RequestParam(value = "groupId") String groupId,
                                   @RequestParam(value = "groupName") String groupName,
                                   @RequestParam(value = "owner") String owner,
                                   @RequestParam(value = "announcement") String announcement,
                                   @RequestParam(value = "intro") String intro,
                                   @RequestParam(value = "joinmode") Integer joinmode,
                                   @RequestParam(value = "custom") String custom,
                                   @RequestParam(value = "iconUrl") String iconUrl,
                                   @RequestParam(value = "beinvitemode") Integer beinvitemode,
                                   @RequestParam(value = "invitemode") Integer invitemode,
                                   @RequestParam(value = "uptinfomode") Integer uptinfomode,
                                   @RequestParam(value = "upcustommode") Integer upcustommode);

    @RequestMapping(value = "/im/api/group/query",method = RequestMethod.POST)
    Map<String,Object> queryGroupInfo(@RequestParam(value = "groupIds") String groupIds,
                                      @RequestParam(value = "groupId") String groupId,
                                      @RequestParam(value = "listMembers") int listMembers);

    @RequestMapping(value = "/im/api/group/detail",method = RequestMethod.POST)
    Map<String,Object> queryGroupDetail(@RequestParam(value = "groupId") String groupId);


    @RequestMapping(value = "/im/api/group/changeowner",method = RequestMethod.POST)
    Map<String,Object> changeGroupOwner(@RequestParam(value = "groupId") String groupId,
                                        @RequestParam(value = "owner") String owner,
                                        @RequestParam(value = "newOwner") String newOwner,
                                        @RequestParam(value = "leave") int leave);

    @RequestMapping(value = "/im/api/group/addadmin",method = RequestMethod.POST)
    Map<String,Object> addGroupAdmin(@RequestParam(value = "groupId") String groupId,
                                     @RequestParam(value = "owner") String owner,
                                     @RequestParam(value = "members") String members);

    @RequestMapping(value = "/im/api/group/deladmin",method = RequestMethod.POST)
    Map<String,Object> delGroupAdmin(@RequestParam(value = "groupId") String groupId,
                                     @RequestParam(value = "owner") String owner,
                                     @RequestParam(value = "members") String members);

    @RequestMapping(value = "/im/api/group/my",method = RequestMethod.POST)
    Map<String,Object> listMyGroups(@RequestParam(value = "imUid") String imUid);


    @RequestMapping(value = "/im/api/message/send",method = RequestMethod.POST)
    Map<String,Object> sendMessage(@RequestParam(value = "fromImUid") String fromImUid,
                                   @RequestParam(value = "toImUid") String toImUid,
                                   @RequestParam(value = "isGroupMessage") int isGroupMessage,
                                   @RequestParam(value = "messageType") int messageType,
                                   @RequestParam(value = "body") String body,
                                   @RequestParam(value = "option") String option,
                                   @RequestParam(value = "iosPushContent") String iosPushContent,
                                   @RequestParam(value = "iosPushPayload") String iosPushPayload,
                                   @RequestParam(value = "extend") String extend);
}