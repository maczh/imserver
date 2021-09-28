package com.ququ.imserver.customerservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "imserver-api")
public interface ImApiService {

    @RequestMapping(value = "/group/create",method = RequestMethod.POST)
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


    @RequestMapping(value = "/group/detail",method = RequestMethod.POST)
    Map<String,Object> queryGroupDetail(@RequestParam(value = "groupId") String groupId);


    @RequestMapping(value = "/group/query",method = RequestMethod.POST)
    Map<String,Object> queryGroupInfo(@RequestParam(value = "groupIds") String groupIds,
                       @RequestParam(value = "groupId") String groupId,
                       @RequestParam(value = "listMembers") int listMembers);
}
