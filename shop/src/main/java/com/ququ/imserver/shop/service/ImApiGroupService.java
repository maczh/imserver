package com.ququ.imserver.shop.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "imserver-api")
public interface ImApiGroupService {

    @RequestMapping(value = "/group/add",method = RequestMethod.POST)
    Map<String,Object> addToGroup(@RequestParam(value = "groupId") String groupId,
                                  @RequestParam(value = "owner") String owner,
                                  @RequestParam(value = "members") String members,
                                  @RequestParam(value = "msg") String msg,
                                  @RequestParam(value = "magree") Integer magree,
                                  @RequestParam(value = "attach") String attach);

    @RequestMapping(value = "/group/update",method = RequestMethod.POST)
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

    @RequestMapping(value = "/group/query",method = RequestMethod.POST)
    Map<String,Object> queryGroupInfo(@RequestParam(value = "groupIds") String groupIds,
                                      @RequestParam(value = "groupId") String groupId,
                                      @RequestParam(value = "listMembers") int listMembers);

    @RequestMapping(value = "/group/changeowner",method = RequestMethod.POST)
    Map<String,Object> changeGroupOwner(@RequestParam(value = "groupId") String groupId,
                                        @RequestParam(value = "owner") String owner,
                                        @RequestParam(value = "newOwner") String newOwner,
                                        @RequestParam(value = "leave") int leave);

    @RequestMapping(value = "/group/my",method = RequestMethod.POST)
    Map<String,Object> listMyGroups(@RequestParam(value = "imUid") String imUid);

}
