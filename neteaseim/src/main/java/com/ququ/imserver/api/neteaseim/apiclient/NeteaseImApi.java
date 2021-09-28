package com.ququ.imserver.api.neteaseim.apiclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ququ.common.utils.OkHttpClientUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 网易云信对接接口
 *
 * Write by Macro
 * at 2018-07-13
 */
@Component
public class NeteaseImApi {

    private Logger logger = LoggerFactory.getLogger(NeteaseImApi.class);


    /**
     * 生成网易云信API接口Header
     * @return
     */
    private Map<String,String> getHeader(){
        Map<String,String> header = new HashMap<>();
        header.put("AppKey",NeteaseConfig.APPKEY);
        header.put("Nonce",RandomStringUtils.randomNumeric(128));
        header.put("CurTime",""+(new Date().getTime())/1000l);
        header.put("CheckSum",CheckSumBuilder.getCheckSum(NeteaseConfig.APPSECRET,header.get("Nonce"),header.get("CurTime")));
//        logger.debug("header={}",header);
        return header;
    }


    /**************************************************************************
     * 用户基本信息部分
     *************************************************************************/



    /**
     * 注册IM号
     * @param imUid
     * @param name
     * @param iconUrl
     * @param gender
     * @param mobile
     * @param email
     * @param birth
     * @param memo
     * @param props
     * @param ex
     * @return
     */
    public Map<String,Object> register(String imUid,String name,String iconUrl,Integer gender,String mobile,String email,String birth,String memo,String props,String ex,String token){
        Map<String,Object> params = new HashMap<>();
        params.put("accid",imUid);
        if (StringUtils.isNotBlank(name)) params.put("name",name);
        if (StringUtils.isNotBlank(props)) params.put("props",props);
        if (StringUtils.isNotBlank(iconUrl)) params.put("icon",iconUrl);
        if (StringUtils.isNotBlank(memo)) params.put("sign",memo);
        if (StringUtils.isNotBlank(email)) params.put("email",email);
        if (StringUtils.isNotBlank(birth)) params.put("birth",birth);
        if (StringUtils.isNotBlank(mobile)) params.put("mobile",mobile);
        if (StringUtils.isNotBlank(token)) params.put("token",token);
        if (gender == null) gender=0;
        params.put("gender",gender.intValue());
        if (StringUtils.isNotBlank(ex)) params.put("ex",ex);
        try {
            String result = OkHttpClientUtil.post(NeteaseConfig.API_URL_BASE+NeteaseConfig.API_URL_REGISTER,params,getHeader());
            logger.debug("用户注册结果:{}",result);
            Map<String,Object> resultMap = JSON.parseObject(result,Map.class);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 更新属性
     * @param imUid
     * @param props
     * @return
     */
    public  Map<String,Object> updateUserProps(String imUid,String props){
        Map<String,Object> params = new HashMap<>();
        params.put("accid",imUid);
        if (StringUtils.isNotBlank(props)) params.put("props",props);
        try {
            String result = OkHttpClientUtil.post(NeteaseConfig.API_URL_BASE+NeteaseConfig.API_URL_UPDATE_USERID,params,getHeader());
            logger.debug("用户更新属性结果:{}",result);
            Map<String,Object> resultMap = JSON.parseObject(result,Map.class);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 修改用户名片
     * @param imUid
     * @param name
     * @param iconUrl
     * @param gender
     * @param mobile
     * @param email
     * @param birth
     * @param memo
     * @param ex
     * @return
     */
    public  Map<String,Object> updateUserInfo(String imUid,String name,String iconUrl,Integer gender,String mobile,String email,String birth,String memo,String ex){
        Map<String,Object> params = new HashMap<>();
        params.put("accid",imUid);
        if (StringUtils.isNotBlank(name)) params.put("name",name);
        if (StringUtils.isNotBlank(iconUrl)) params.put("icon",iconUrl);
        if (StringUtils.isNotBlank(memo)) params.put("sign",memo);
        if (StringUtils.isNotBlank(email)) params.put("email",email);
        if (StringUtils.isNotBlank(birth)) params.put("birth",birth);
        if (StringUtils.isNotBlank(mobile)) params.put("mobile",mobile);
        if (gender == null) gender=0;
        params.put("gender",gender.intValue());
        if (StringUtils.isNotBlank(ex)) params.put("ex",ex);
        try {
            String result = OkHttpClientUtil.post(NeteaseConfig.API_URL_BASE+NeteaseConfig.API_URL_UPDATE_USERINFO,params,getHeader());
            logger.debug("用户修改用户名片结果:{}",result);
            Map<String,Object> resultMap = JSON.parseObject(result,Map.class);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取用户名片(批量)
     * @param imUids
     * @return
     */
    public  Map<String,Object> getUserInfo(List<String> imUids){
        Map<String,Object> params = new HashMap<>();
        String uids = JSONArray.toJSONString(imUids);
        logger.debug("uids={}",uids);
        params.put("accids",uids);
        try {
            String result = OkHttpClientUtil.post(NeteaseConfig.API_URL_BASE+NeteaseConfig.API_URL_GET_USERINFO,params,getHeader());
            logger.debug("用户获取用户名片结果:{}",result);
            Map<String,Object> resultMap = JSON.parseObject(result,Map.class);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取单个用户名片
     * @param imUid
     * @return
     */
    public  Map<String,Object> getUserInfo(String imUid){
        List<String> imUids = new ArrayList<>();
        imUids.add(imUid);
        return getUserInfo(imUids);
    }


    /**********************************************************************************************
     * 群聊天功能
     **********************************************************************************************/

    /**
     * 创建群
     * @param groupName 群名称，最大长度64字符
     * @param owner     群主用户帐号，最大长度32字符
     * @param members   ["aaa","bbb"](JSONArray对应的accid，如果解析出错会报414)，一次最多拉200个成员
     * @param msg       邀请发送的文字，最大长度150字符
     * @param announcement  群公告，最大长度1024字符
     * @param intro     群描述，最大长度512字符
     * @param magree    管理后台建群时，0不需要被邀请人同意加入群，1需要被邀请人同意才可以加入群。其它会返回414
     * @param joinmode  群建好后，sdk操作时，0不用验证，1需要验证,2不允许任何人加入。其它返回414
     * @param custom    自定义高级群扩展属性，第三方可以跟据此属性自定义扩展自己的群属性。（建议为json）,最大长度1024字符
     * @param iconUrl   群头像，最大长度1024字符
     * @param beinvitemode  被邀请人同意方式，0-需要同意(默认),1-不需要同意。其它返回414
     * @param invitemode    谁可以邀请他人入群，0-管理员(默认),1-所有人。其它返回414
     * @param uptinfomode   谁可以修改群资料，0-管理员(默认),1-所有人。其它返回414
     * @param upcustommode  谁可以更新群自定义属性，0-管理员(默认),1-所有人。其它返回414
     * @return
     */
    public  Map<String,Object> createGroup(String groupName,String owner,List<String> members,String msg,String announcement,
                                                 String intro,Integer magree,Integer joinmode,String custom,String iconUrl,
                                                 Integer beinvitemode,Integer invitemode,Integer uptinfomode,Integer upcustommode){
        Map<String,Object> params = new HashMap<>();
        params.put("tname",groupName);
        params.put("owner",owner);
        params.put("members",JSONArray.toJSONString(members));
        params.put("msg",msg);
        if (StringUtils.isNotBlank(announcement)) params.put("announcement",announcement);
        if (StringUtils.isNotBlank(iconUrl)) params.put("icon",iconUrl);
        if (StringUtils.isNotBlank(intro)) params.put("intro",intro);
        if (StringUtils.isNotBlank(custom)) params.put("custom",custom);
        if (StringUtils.isNotBlank(iconUrl)) params.put("icon",iconUrl);
        if (magree == null) magree=0;
        if (joinmode == null) joinmode=0;
        if (beinvitemode == null) beinvitemode=1;
        if (invitemode == null) invitemode=1;
        if (uptinfomode == null) uptinfomode=0;
        if (upcustommode == null) upcustommode=1;
        params.put("magree",magree);
        params.put("joinmode",joinmode);
        params.put("beinvitemode",beinvitemode);
        params.put("invitemode",invitemode);
        params.put("uptinfomode",uptinfomode);
        params.put("upcustommode",upcustommode);
        try {
            String result = OkHttpClientUtil.post(NeteaseConfig.API_URL_BASE+NeteaseConfig.API_URL_GROUP_CREATE,params,getHeader());
            logger.debug("创建群结果:{}",result);
            Map<String,Object> resultMap = JSON.parseObject(result,Map.class);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 拉人入群
     * @param groupId
     * @param owner
     * @param members
     * @param msg
     * @param magree
     * @param attach
     * @return
     */
    public  Map<String,Object> addToGroup(String groupId,String owner,List<String> members,String msg,Integer magree,String attach){
        Map<String,Object> params = new HashMap<>();
        params.put("tid",groupId);
        params.put("owner",owner);
        params.put("members",JSONArray.toJSONString(members));
        params.put("msg",msg);
        if (StringUtils.isNotBlank(attach)) params.put("attach",attach);
        if (magree == null) magree=0;
        params.put("magree",magree);
        try {
            String result = OkHttpClientUtil.post(NeteaseConfig.API_URL_BASE+NeteaseConfig.API_URL_GROUP_ADD,params,getHeader());
            logger.debug("拉人入群结果:{}",result);
            Map<String,Object> resultMap = JSON.parseObject(result,Map.class);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 踢人出群
     * @param groupId
     * @param owner
     * @param members
     * @param member
     * @param attach
     * @return
     */
    public  Map<String,Object> kickFromGroup(String groupId,String owner,List<String> members,String member,String attach){
        Map<String,Object> params = new HashMap<>();
        params.put("tid",groupId);
        params.put("owner",owner);
        if (members != null && members.size()>0) params.put("members",JSONArray.toJSONString(members));
        if (StringUtils.isNotBlank(member)) params.put("member",member);
        if (StringUtils.isNotBlank(attach)) params.put("attach",attach);
        try {
            String result = OkHttpClientUtil.post(NeteaseConfig.API_URL_BASE+NeteaseConfig.API_URL_GROUP_KICK,params,getHeader());
            logger.debug("踢人出群结果:{}",result);
            Map<String,Object> resultMap = JSON.parseObject(result,Map.class);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 解散群
     * @param groupId
     * @param owner
     * @return
     */
    public  Map<String,Object> removeGroup(String groupId,String owner){
        Map<String,Object> params = new HashMap<>();
        params.put("tid",groupId);
        params.put("owner",owner);
        try {
            String result = OkHttpClientUtil.post(NeteaseConfig.API_URL_BASE+NeteaseConfig.API_URL_GROUP_REMOVE,params,getHeader());
            logger.debug("解散群结果:{}",result);
            Map<String,Object> resultMap = JSON.parseObject(result,Map.class);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 修改群资料
     * @param groupId
     * @param groupName
     * @param owner
     * @param announcement
     * @param intro
     * @param joinmode
     * @param custom
     * @param iconUrl
     * @param beinvitemode
     * @param invitemode
     * @param uptinfomode
     * @param upcustommode
     * @return
     */
    public  Map<String,Object> updateGroup(String groupId,String groupName,String owner,String announcement,
                                                 String intro,Integer joinmode,String custom,String iconUrl,
                                                 Integer beinvitemode,Integer invitemode,Integer uptinfomode,Integer upcustommode){
        Map<String,Object> params = new HashMap<>();
        params.put("tid",groupId);
        params.put("owner",owner);
        if (StringUtils.isNotBlank(groupName)) params.put("tname",groupName);
        if (StringUtils.isNotBlank(announcement)) params.put("announcement",announcement);
        if (StringUtils.isNotBlank(iconUrl)) params.put("icon",iconUrl);
        if (StringUtils.isNotBlank(intro)) params.put("intro",intro);
        if (StringUtils.isNotBlank(custom)) params.put("custom",custom);
        if (StringUtils.isNotBlank(iconUrl)) params.put("icon",iconUrl);
        if (joinmode != null) params.put("joinmode",joinmode);
        if (beinvitemode == null) params.put("beinvitemode",beinvitemode);
        if (invitemode == null) params.put("invitemode",invitemode);
        if (uptinfomode == null) params.put("uptinfomode",uptinfomode);
        if (upcustommode == null) params.put("upcustommode",upcustommode);
        try {
            String result = OkHttpClientUtil.post(NeteaseConfig.API_URL_BASE+NeteaseConfig.API_URL_GROUP_UPDATE,params,getHeader());
            logger.debug("修改群资料结果:{}",result);
            Map<String,Object> resultMap = JSON.parseObject(result,Map.class);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 查询群信息，多个
     * @param groupIds
     * @param listMembers
     * @return
     */
    public  Map<String,Object> queryGroupInfo(List<String> groupIds,int listMembers){
        Map<String,Object> params = new HashMap<>();
        params.put("tids",JSONArray.toJSONString(groupIds));
        params.put("ope",listMembers);
        try {
            String result = OkHttpClientUtil.post(NeteaseConfig.API_URL_BASE+NeteaseConfig.API_URL_GROUP_QUERY,params,getHeader());
            logger.debug("群信息与成员列表查询结果:{}",result);
            Map<String,Object> resultMap = JSON.parseObject(result,Map.class);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 查询单个群信息
     * @param groupId
     * @param listMembers
     * @return
     */
    public  Map<String,Object> queryGroupInfo(String groupId,int listMembers){
        List<String> groupIds = new ArrayList<>();
        groupIds.add(groupId);
        return queryGroupInfo(groupIds,listMembers);
    }


    /**
     * 查询单个群详细信息与成员详细信息
     * @param groupId
     * @return
     */
    public  Map<String,Object> queryGroupInfo(String groupId){
        Map<String,Object> params = new HashMap<>();
        params.put("tid",groupId);
        try {
            String result = OkHttpClientUtil.post(NeteaseConfig.API_URL_BASE+NeteaseConfig.API_URL_GROUP_QUERY_DETAIL,params,getHeader());
            logger.debug("群信息与成员详细列表查询结果:{}",result);
            Map<String,Object> resultMap = JSON.parseObject(result,Map.class);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 群消息已读结果查询
     * @param groupId
     * @param messageId
     * @param fromImUid
     * @return
     */
    public  Map<String,Object> queryGroupMessageReadedInfo(String groupId,String messageId,String fromImUid){
        Map<String,Object> params = new HashMap<>();
        params.put("tid",groupId);
        params.put("msgid",messageId);
        params.put("fromAccid",fromImUid);
        params.put("snapshot","true");
        try {
            String result = OkHttpClientUtil.post(NeteaseConfig.API_URL_BASE+NeteaseConfig.API_URL_GROUP_MSG_READED_INFO,params,getHeader());
            logger.debug("群消息已读查询结果:{}",result);
            Map<String,Object> resultMap = JSON.parseObject(result,Map.class);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }



    /**
     * 移交群主
     * @param groupId
     * @param owner
     * @param newOwner
     * @param leave
     * @return
     */
    public  Map<String,Object> changeGroupOwner(String groupId,String owner,String newOwner,int leave){
        Map<String,Object> params = new HashMap<>();
        params.put("tid",groupId);
        params.put("owner",owner);
        params.put("newowner",newOwner);
        params.put("leave",leave);
        try {
            String result = OkHttpClientUtil.post(NeteaseConfig.API_URL_BASE+NeteaseConfig.API_URL_GROUP_CHANGE_OWNER,params,getHeader());
            logger.debug("移交群主结果:{}",result);
            Map<String,Object> resultMap = JSON.parseObject(result,Map.class);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 添加群管理员
     * @param groupId
     * @param owner
     * @param members
     * @return
     */
    public  Map<String,Object> addGroupAdmin(String groupId,String owner,List<String> members){
        Map<String,Object> params = new HashMap<>();
        params.put("tid",groupId);
        params.put("owner",owner);
        params.put("members",JSONArray.toJSONString(members));
        try {
            String result = OkHttpClientUtil.post(NeteaseConfig.API_URL_BASE+NeteaseConfig.API_URL_GROUP_ADMIN_ADD,params,getHeader());
            logger.debug("添加群管理员结果:{}",result);
            Map<String,Object> resultMap = JSON.parseObject(result,Map.class);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 移除群管理员
     * @param groupId
     * @param owner
     * @param members
     * @return
     */
    public  Map<String,Object> delGroupAdmin(String groupId,String owner,List<String> members){
        Map<String,Object> params = new HashMap<>();
        params.put("tid",groupId);
        params.put("owner",owner);
        params.put("members",JSONArray.toJSONString(members));
        try {
            String result = OkHttpClientUtil.post(NeteaseConfig.API_URL_BASE+NeteaseConfig.API_URL_GROUP_ADMIN_REMOVE,params,getHeader());
            logger.debug("移除群管理员结果:{}",result);
            Map<String,Object> resultMap = JSON.parseObject(result,Map.class);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 列出用户所有群
     * @param imUid
     * @return
     */
    public  Map<String,Object> listMyGroups(String imUid){
        Map<String,Object> params = new HashMap<>();
        params.put("accid",imUid);
        try {
            String result = OkHttpClientUtil.post(NeteaseConfig.API_URL_BASE+NeteaseConfig.API_URL_GROUP_LIST,params,getHeader());
            logger.debug("列出用户所有群结果:{}",result);
            Map<String,Object> resultMap = JSON.parseObject(result,Map.class);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 修改群成员昵称与扩展字段
     * @param groupId
     * @param owner
     * @param member
     * @param nick
     * @param custom
     * @return
     */
    public  Map<String,Object> updateGroupMemberNick(String groupId,String owner,String member,String nick,String custom){
        Map<String,Object> params = new HashMap<>();
        params.put("tid",groupId);
        params.put("owner",owner);
        params.put("accid",member);
        if (StringUtils.isNotBlank(nick)) params.put("nick",nick);
        if (StringUtils.isNotBlank(custom)) params.put("custom",custom);
        try {
            String result = OkHttpClientUtil.post(NeteaseConfig.API_URL_BASE+NeteaseConfig.API_URL_GROUP_NICK,params,getHeader());
            logger.debug("修改群成员昵称与扩展字段结果:{}",result);
            Map<String,Object> resultMap = JSON.parseObject(result,Map.class);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 主动退群
     * @param groupId
     * @param member
     * @return
     */
    public  Map<String,Object> leaveGroup(String groupId,String member){
        Map<String,Object> params = new HashMap<>();
        params.put("tid",groupId);
        params.put("accid",member);
        try {
            String result = OkHttpClientUtil.post(NeteaseConfig.API_URL_BASE+NeteaseConfig.API_URL_GROUP_LEAVE,params,getHeader());
            logger.debug("主动退群结果:{}",result);
            Map<String,Object> resultMap = JSON.parseObject(result,Map.class);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /******************************************************************
     * 好友关系
     ******************************************************************/


    /**
     * 加为好友
     * @param imUid
     * @param friendImUid
     * @param type
     * @param msg
     * @return
     */
    public  Map<String,Object> addFriend(String imUid,String friendImUid,int type,String msg){
        Map<String,Object> params = new HashMap<>();
        params.put("accid",imUid);
        params.put("faccid",friendImUid);
        params.put("type",type);
        if (StringUtils.isNotBlank(msg))
            params.put("msg",msg);
        try {
            String result = OkHttpClientUtil.post(NeteaseConfig.API_URL_BASE+NeteaseConfig.API_URL_FRIEND_ADD,params,getHeader());
            logger.debug("加为好友结果:{}",result);
            Map<String,Object> resultMap = JSON.parseObject(result,Map.class);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 修改好友昵称备注
     * @param imUid
     * @param friendImUid
     * @param alias
     * @param ex
     * @param serverex
     * @return
     */
    public  Map<String,Object> updateFriend(String imUid,String friendImUid,String alias,String ex,String serverex){
        Map<String,Object> params = new HashMap<>();
        params.put("accid",imUid);
        params.put("faccid",friendImUid);
        if (StringUtils.isNotBlank(alias))
            params.put("alias",alias);
        if (StringUtils.isNotBlank(ex))
            params.put("ex",ex);
        if (StringUtils.isNotBlank(serverex))
            params.put("serverex",serverex);
        try {
            String result = OkHttpClientUtil.post(NeteaseConfig.API_URL_BASE+NeteaseConfig.API_URL_FRIEND_ALIAS,params,getHeader());
            logger.debug("修改好友昵称结果:{}",result);
            Map<String,Object> resultMap = JSON.parseObject(result,Map.class);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 删除好友
     * @param imUid
     * @param friendImUid
     * @return
     */
    public  Map<String,Object> delFriend(String imUid,String friendImUid){
        Map<String,Object> params = new HashMap<>();
        params.put("accid",imUid);
        params.put("faccid",friendImUid);
        try {
            String result = OkHttpClientUtil.post(NeteaseConfig.API_URL_BASE+NeteaseConfig.API_URL_FRIEND_DEL,params,getHeader());
            logger.debug("删除好友结果:{}",result);
            Map<String,Object> resultMap = JSON.parseObject(result,Map.class);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取近期更新的好友
     * @param imUid
     * @param updatetime   更新时间戳，接口返回该时间戳之后有更新的好友列表
     * @param createtime   创建时间戳，接口返回该时间戳之后新增加的好友列表
     * @return
     */
    public  Map<String,Object> getFriend(String imUid,Long updatetime,Long createtime){
        Map<String,Object> params = new HashMap<>();
        params.put("accid",imUid);
        params.put("updatetime",updatetime);
        if (createtime != null)
            params.put("createtime",createtime);
        try {
            String result = OkHttpClientUtil.post(NeteaseConfig.API_URL_BASE+NeteaseConfig.API_URL_FRIEND_GET,params,getHeader());
            logger.debug("获取近期更新的好友结果:{}",result);
            Map<String,Object> resultMap = JSON.parseObject(result,Map.class);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 设置/取消黑名单、静音
     * @param imUid
     * @param friendImUid
     * @param relationType  本次操作的关系类型,1:黑名单操作，2:静音列表操作
     * @param operate       操作类型，0:取消黑名单或静音，1:加入黑名单或静音
     * @return
     */
    public  Map<String,Object> blackFriend(String imUid,String friendImUid,int relationType,int operate){
        Map<String,Object> params = new HashMap<>();
        params.put("accid",imUid);
        params.put("targetAcc",friendImUid);
        params.put("relationType",relationType);
        params.put("value",operate);
        try {
            String result = OkHttpClientUtil.post(NeteaseConfig.API_URL_BASE+NeteaseConfig.API_URL_FRIEND_BLACK,params,getHeader());
            logger.debug("好友黑名单操作结果:{}",result);
            Map<String,Object> resultMap = JSON.parseObject(result,Map.class);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }




    /*************************************************************************
     * 聊天记录处理
     *************************************************************************/


    /**
     * 查询单人聊天记录
     * @param from
     * @param to
     * @param begintime
     * @param endtime
     * @param limit
     * @param reverse
     * @param type
     * @return
     */
    public  Map<String,Object> querySessionHistory(String from,String to,String begintime,String endtime,Integer limit,Integer reverse,String type){
        Map<String,Object> params = new HashMap<>();
        params.put("from",from);
        params.put("to",to);
        params.put("begintime",begintime);
        params.put("endtime",endtime);
        params.put("limit",limit==null?100:limit);
        if (reverse != null) params.put("reverse",reverse);
        if (StringUtils.isNotBlank(type)) params.put("type",type);
        try {
            String result = OkHttpClientUtil.post(NeteaseConfig.API_URL_BASE+NeteaseConfig.API_URL_HISTORY_PEER,params,getHeader());
            logger.debug("查询单人聊天记录结果:{}",result);
            Map<String,Object> resultMap = JSON.parseObject(result,Map.class);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 查询群聊记录
     * @param groupId
     * @param imUid
     * @param begintime
     * @param endtime
     * @param limit
     * @param reverse
     * @param type
     * @return
     */
    public  Map<String,Object> queryGroupHistory(String groupId,String imUid,String begintime,String endtime,Integer limit,Integer reverse,String type){
        Map<String,Object> params = new HashMap<>();
        params.put("tid",groupId);
        params.put("accid",imUid);
        params.put("begintime",begintime);
        params.put("endtime",endtime);
        params.put("limit",limit==null?100:limit);
        if (reverse != null) params.put("reverse",reverse);
        if (StringUtils.isNotBlank(type)) params.put("type",type);
        try {
            String result = OkHttpClientUtil.post(NeteaseConfig.API_URL_BASE+NeteaseConfig.API_URL_HISTORY_GROUP,params,getHeader());
            logger.debug("查询群聊天记录结果:{}",result);
            Map<String,Object> resultMap = JSON.parseObject(result,Map.class);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /*********************************************************************
     *  消息功能
     *********************************************************************/


    /**
     * 发送普通消息
     * @param fromImUid
     * @param toImUid   接收人imUid或群号
     * @param isGroupMessage  0-点对点消息  1-群消息
     * @param messageType
     * @param body
     * @param option
     * @param iosPushContent
     * @param iosPushPayload
     * @param extend    自定义扩展字段
     * @return
     */
    public  Map<String,Object> sendMessage(String fromImUid,String toImUid,int isGroupMessage,int messageType,String body,String option,
                                                 String iosPushContent,String iosPushPayload,String extend){
        Map<String,Object> params = new HashMap<>();
        params.put("from",fromImUid);
        params.put("ope",isGroupMessage);
        params.put("to",toImUid);
        params.put("type",messageType);
        params.put("body",body);
        if (StringUtils.isNotBlank(option)) params.put("option",option);
        if (StringUtils.isNotBlank(iosPushContent)) params.put("pushcontent",iosPushContent);
        if (StringUtils.isNotBlank(iosPushPayload)) params.put("payload",iosPushPayload);
        if (StringUtils.isNotBlank(extend)) params.put("ext",extend);
        try {
            String result = OkHttpClientUtil.post(NeteaseConfig.API_URL_BASE+NeteaseConfig.API_URL_MESSAGE_SEND,params,getHeader());
            logger.debug("发送普通消息结果:{}",result);
            Map<String,Object> resultMap = JSON.parseObject(result,Map.class);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }



//    public  void main(String[] args){
////        Map<String,Object> map = register("ququ1000000003","隔壁老王",null,1,"15294528171",null,null,"小卢的测试账号",
////                "{\"ShopId\":\"10036\",\"ShopName\":\"网梭网络\",\"Admin\":1}",null);
//        List<String> Uids = Arrays.asList("ququ1000000002");
////        Map<String,Object> customMap = new HashMap<>();
////        customMap.put("ServiceGroup",1);
////        customMap.put("ShopId","10036");
////        customMap.put("Customer","ququ1000000002");
////        customMap.put("ShopName","网梭网络");
////        customMap.put("CustomerShopId","61347");
////        customMap.put("CustomerShopName","小卢商铺");
////        List<String> serviceMembers = Arrays.asList("ququ1000000001","ququ1000000003");
////        customMap.put("ServiceMembers",serviceMembers);
////        customMap.put("ServiceMemberNick","蛐蛐客服");
////        customMap.put("ServiceMemberIconUrl","https://bbs.deepin.org/uc_server/avatar.php?uid=88517&size=small");
////        Map<String,Object> map = queryGroupInfo("593844528");
////        Map<String,Object> map = updateUserInfo("ququ1000000002",null,null,1,null,null,null,null,"{\"ShopId\":\"61347\",\"ShopName\":\"小卢商铺\",\"Admin\":1}");
////        Map<String,Object> map = createGroup("网梭网络客服-叛逆点起一根烟","ququ1000000003",Uids,"您好！请问您需要什么帮助？",null,"测试客服群",
////                null,null,JSON.toJSONString(customMap),null,null,null,null,null);
////        logger.debug("map={}",map);
//        getUserInfo(Uids);
//    }


}
