package com.ququ.imserver.api.rongcloud.apiclient;

import io.rong.RongCloud;
import io.rong.methods.group.Group;
import io.rong.methods.user.User;
import io.rong.models.Result;
import io.rong.models.group.GroupMember;
import io.rong.models.group.GroupModel;
import io.rong.models.response.TokenResult;
import io.rong.models.user.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RongCloudApi {

    private static Logger logger = LoggerFactory.getLogger(RongCloudApi.class);


    private static RongCloud getRongCloud(){
        return RongCloud.getInstance(RongCloudConfig.APPKEY,RongCloudConfig.APPSECRET);
    }

    /**
     * 注册融云用户
     * @param imUid
     * @param name
     * @param picUrl
     * @return
     */
    public static TokenResult registerUser(String imUid,String name,String picUrl){
        RongCloud rongCloud = getRongCloud();
        User userMethod = rongCloud.user;
        UserModel userModel = new UserModel();
        userModel.setId(imUid);
        userModel.setName(name);
        userModel.setPortrait(picUrl);
        try {
            logger.debug("融云用户注册参数:{}",userModel);
            TokenResult result =userMethod.register(userModel);
            logger.debug("融云用户注册结果:{}",result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 融云修改用户昵称头像
     * @param imUid
     * @param name
     * @param picUrl
     * @return
     */
    public static Result updateUser(String imUid,String name,String picUrl){
        RongCloud rongCloud = getRongCloud();
        User userMethod = rongCloud.user;
        UserModel userModel = new UserModel();
        userModel.setId(imUid);
        userModel.setName(name);
        userModel.setPortrait(picUrl);
        try {
            logger.debug("融云用户更新参数:{}",userModel);
            Result result =userMethod.update(userModel);
            logger.debug("融云用户更新结果:{}",result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 建群
     * @param groupId
     * @param groupName
     * @param imUidList 群成员清单，第0个成员默认为群管理
     * @return
     */
    public static Result createGroup(String groupId, String groupName, List<String> imUidList){
        RongCloud rongCloud = getRongCloud();
        Group group = rongCloud.group;

        GroupMember[] groupMembers = new GroupMember[imUidList.size()];
        int i = 0;
        for (String imUid : imUidList){
            groupMembers[i++] = new GroupMember().setId(imUid);
        }

        GroupModel groupModel = new GroupModel();
        groupModel.setId(groupId);
        groupModel.setName(groupName);
        groupModel.setMembers(groupMembers);

        try {
            logger.debug("融云创建群参数:{}",groupModel);
            Result result = group.create(groupModel);
            logger.debug("融云创建群结果:{}",result);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 群改名
     * @param groupId
     * @param groupName
     * @return
     */
    public static Result renameGroup(String groupId, String groupName){
        RongCloud rongCloud = getRongCloud();
        Group group = rongCloud.group;


        GroupModel groupModel = new GroupModel();
        groupModel.setId(groupId);
        groupModel.setName(groupName);

        try {
            logger.debug("融云群改名参数:{}",groupModel);
            Result result = group.update(groupModel);
            logger.debug("融云群改名结果:{}",result);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 将用户加入到群
     * @param groupId
     * @param imUid
     * @return
     */
    public static Result joinGroup(String groupId, String imUid){
        RongCloud rongCloud = getRongCloud();
        Group group = rongCloud.group;

        GroupMember[] groupMembers = {new GroupMember().setId(imUid)};

        GroupModel groupModel = new GroupModel();
        groupModel.setId(groupId);
        groupModel.setMembers(groupMembers);

        try {
            logger.debug("融云群加人参数:{}",groupModel);
            Result result = group.join(groupModel);
            logger.debug("融云群加人结果:{}",result);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 用户退群
     * @param groupId
     * @param imUid
     * @return
     */
    public static Result leaveGroup(String groupId, String imUid){
        RongCloud rongCloud = getRongCloud();
        Group group = rongCloud.group;

        GroupMember[] groupMembers = {new GroupMember().setId(imUid)};

        GroupModel groupModel = new GroupModel();
        groupModel.setId(groupId);
        groupModel.setMembers(groupMembers);

        try {
            logger.debug("融云退群参数:{}",groupModel);
            Result result = group.quit(groupModel);
            logger.debug("融云退群结果:{}",result);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}
