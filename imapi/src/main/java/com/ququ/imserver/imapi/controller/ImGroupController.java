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

@Api(tags = "群组服务功能模块")
@RestController
@RequestMapping("/group")
public class ImGroupController {

    @Autowired
    private ImApiBusinessService imApiBusinessService;


    @ApiOperation(value = "建群功能接口，不对外开放", hidden = true)
    @RequestMapping("/create")
    public Map<String,Object> createGroup(@RequestParam String groupName,
                                          @RequestParam String owner,
                                          @RequestParam String members,
                                          @RequestParam String msg,
                                          @RequestParam(required = false) String announcement,
                                          @RequestParam(required = false) String intro,
                                          @RequestParam(required = false) Integer magree,
                                          @RequestParam(required = false) Integer joinmode,
                                          @RequestParam(required = false) String custom,
                                          @RequestParam(required = false) String iconUrl,
                                          @RequestParam(required = false) Integer beinvitemode,
                                          @RequestParam(required = false) Integer invitemode,
                                          @RequestParam(required = false) Integer uptinfomode,
                                          @RequestParam(required = false) Integer upcustommode) {
        return imApiBusinessService.createGroup(groupName, owner, members, msg, announcement, intro, magree, joinmode, custom, iconUrl, beinvitemode, invitemode, uptinfomode, upcustommode);
    }

    @ApiOperation(value = "拉人入群接口", notes = "1.可以批量邀请，邀请时需指定群主；<br>" +
            "2.当群成员达到上限时，再邀请某人入群返回失败；<br>" +
            "3.当群成员达到上限时，被邀请人『接受邀请』的操作也将返回失败。",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "群号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "owner", value = "群主imUid", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "members", value = "要拉入群的用户imUid列表,JSONArray格式", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "msg", value = "邀请发送的文字，最大长度150字符", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "magree", value = "管理后台建群时，0不需要被邀请人同意加入群，1需要被邀请人同意才可以加入群。", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "attach", value = "自定义扩展字段，最大长度512", required = false, paramType = "query", dataType = "String")
    })
    @RequestMapping("/add")
    public Map addToGroup(@RequestParam String groupId,
                          @RequestParam String owner,
                          @RequestParam String members,
                          @RequestParam String msg,
                          @RequestParam(required = false) Integer magree,
                          @RequestParam(required = false) String attach){
        return imApiBusinessService.addToGroup(groupId, owner, members, msg, magree, attach);
    }

    @ApiOperation(value = "踢人出群接口", notes = "踢人出群接口",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "群号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "owner", value = "群主imUid", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "members", value = "多人出群，要踢出群的用户imUid列表,JSONArray格式", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "member", value = "单独踢一人出群时的用户imUid", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "attach", value = "自定义扩展字段，最大长度512", required = false, paramType = "query", dataType = "String")
    })
    @RequestMapping("/kick")
    public Map kickFromGroup(@RequestParam String groupId,
                             @RequestParam String owner,
                             @RequestParam(required = false) String members,
                             @RequestParam(required = false) String member,
                             @RequestParam(required = false) String attach) {
        return imApiBusinessService.kickFromGroup(groupId, owner, members, member, attach);
    }


    @ApiOperation(value = "解散群接口", notes = "删除整个群，会解散该群，谨慎操作！",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "群号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "owner", value = "群主imUid", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping("/remove")
    public Map removeGroup(@RequestParam String groupId,
                           @RequestParam String owner) {

        return imApiBusinessService.removeGroup(groupId, owner);
    }

    @ApiOperation(value = "更新群基本信息接口，不对外", hidden = true)
    @RequestMapping("/update")
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
                           @RequestParam(required = false) Integer upcustommode) {
        return imApiBusinessService.updateGroup(groupId, groupName, owner, announcement, intro, joinmode, custom, iconUrl, beinvitemode, invitemode, uptinfomode, upcustommode);
    }


    @ApiOperation(value = "单个或多个群信息与成员列表查询接口", notes = "单个或多个群信息与成员列表查询接口",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupIds", value = "多个群号的列表，JSONArray格式", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "groupId", value = "单个群号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "listMembers", value = "1表示带上群成员列表，0表示不带群成员列表，只返回群信息", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping("/query")
    public Map queryGroupInfo(@RequestParam(required = false) String groupIds,
                              @RequestParam(required = false) String groupId,
                              @RequestParam(defaultValue = "0") int listMembers) {
        return imApiBusinessService.queryGroupInfo(groupIds, groupId, listMembers);
    }


    @ApiOperation(value = "群详情查询接口", notes = "查询指定群的详细信息（群信息+成员详细信息）",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "群号", required = false, paramType = "query", dataType = "String")
    })
    @RequestMapping("/detail")
    public Map queryGroupDetail(@RequestParam String groupId){
        return imApiBusinessService.queryGroupDetail(groupId);
    }


    @ApiOperation(value = "移交群主", notes = "转换群主身份；群主可以选择离开此群，还是留下来成为普通成员",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "群号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "owner", value = "老群主imUid", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "newOwner", value = "新群主imUid", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "leave", value = "是否离群。1:群主解除群主后离开群，2：群主解除群主后成为普通成员", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping("/changeowner")
    public Map changeGroupOwner(@RequestParam String groupId,
                                @RequestParam String owner,
                                @RequestParam String newOwner,
                                @RequestParam(defaultValue = "2") int leave) {
        return imApiBusinessService.changeGroupOwner(groupId, owner, newOwner, leave);
    }


    @ApiOperation(value = "任命管理员", notes = "提升普通成员为群管理员，可以批量，但是一次添加最多不超过10个人",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "群号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "owner", value = "群主imUid", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "members", value = "新增管理员imUid列表，JSONArray格式", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping("/addadmin")
    public Map addGroupAdmin(@RequestParam String groupId,
                             @RequestParam String owner,
                             @RequestParam String members) {
        return imApiBusinessService.addGroupAdmin(groupId, owner, members);
    }


    @ApiOperation(value = "移除管理员", notes = "解除管理员身份，可以批量，但是一次解除最多不超过10个人",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "群号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "owner", value = "群主imUid", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "members", value = "要解绑管理员imUid列表，JSONArray格式", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping("/deladmin")
    public Map delGroupAdmin(@RequestParam String groupId,
                             @RequestParam String owner,
                             @RequestParam String members) {
        return imApiBusinessService.delGroupAdmin(groupId, owner, members);
    }


    @ApiOperation(value = "获取某用户所加入的群信息", notes = "获取某用户所加入的群信息",httpMethod = "POST",response = com.ququ.common.result.ResultJson.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "imUid", value = "群主imUid", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping("/my")
    public Map listMyGroups(@RequestParam String imUid){
        return imApiBusinessService.listMyGroups(imUid);
    }

}
