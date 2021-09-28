package com.ququ.imserver.tags.controller;

import com.ququ.common.result.ResultJson;
import com.ququ.imserver.constant.StatusConstant;
import com.ququ.imserver.tags.service.FriendTagsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/im/tags/friend")
public class FriendTagsController {

    @Autowired
    private FriendTagsService friendTagsService;

    /**
     * 保存好友的标签
     * 参数组合  userId+friendUserId+tag:   保存某个好友的一个标签
     * 参数组合  userId+friends+tag:        保存多个好友的同一个标签
     * 参数组合  userId+friendUserId+tags:  保存一个好友的多个标签
     * @param userId
     * @param friendUserId
     * @param tag
     * @param friends
     * @param tags
     * @return
     */
    @RequestMapping("/add")
    public ResultJson saveFriendTag(@RequestParam String userId,
                                    @RequestParam(required = false) String friendUserId,
                                    @RequestParam(required = false) String tag,
                                    @RequestParam(required = false) String friends,
                                    @RequestParam(required = false) String tags){
        if (StringUtils.isNotBlank(friends)){
            if (StringUtils.isBlank(tag))
                return new ResultJson(StatusConstant.TAGS_PARAMS_ERROR.getCode(),StatusConstant.TAGS_PARAMS_ERROR.getMsg());
            else
                friendTagsService.saveFriendsTag(userId, friends, tag);
        }else if (StringUtils.isNotBlank(tags)){
            if (StringUtils.isBlank(friendUserId))
                return new ResultJson(StatusConstant.TAGS_PARAMS_ERROR.getCode(),StatusConstant.TAGS_PARAMS_ERROR.getMsg());
            else
                friendTagsService.saveFriendTags(userId, friendUserId, tags);
        }else if (StringUtils.isBlank(friendUserId) || StringUtils.isBlank(tag))
            return new ResultJson(StatusConstant.TAGS_PARAMS_ERROR.getCode(),StatusConstant.TAGS_PARAMS_ERROR.getMsg());
        else
            friendTagsService.saveFriendTag(userId, friendUserId, tag);
        return new ResultJson();
    }


    /**
     * 删除好友的标签
     * 参数组合  userId+friendUserId:       删除指定好友的所有标签
     * 参数组合  userId+friendUserId+tag:   删除某个好友的一个标签
     * 参数组合  userId+friends+tag:        删除多个好友的同一个标签
     * 参数组合  userId+friendUserId+tags:  删除一个好友的多个标签
     *
     * @param userId
     * @param friendUserId
     * @param tag
     * @param friends
     * @param tags
     * @return
     */
    @RequestMapping("/del")
    public ResultJson delFriendTag(@RequestParam String userId,
                                    @RequestParam(required = false) String friendUserId,
                                    @RequestParam(required = false) String tag,
                                    @RequestParam(required = false) String friends,
                                    @RequestParam(required = false) String tags){
        if (StringUtils.isNotBlank(friends)){
            if (StringUtils.isBlank(tag))
                return new ResultJson(StatusConstant.TAGS_PARAMS_ERROR.getCode(),StatusConstant.TAGS_PARAMS_ERROR.getMsg());
            else
                friendTagsService.delFriendsTag(userId, friends, tag);
        }else if (StringUtils.isNotBlank(tags)){
            if (StringUtils.isBlank(friendUserId))
                return new ResultJson(StatusConstant.TAGS_PARAMS_ERROR.getCode(),StatusConstant.TAGS_PARAMS_ERROR.getMsg());
            else
                friendTagsService.delFriendTags(userId, friendUserId, tags);
        }else if (StringUtils.isNotBlank(friendUserId) && StringUtils.isBlank(tag))
            friendTagsService.delFriendAllTags(userId, friendUserId);
        else if (StringUtils.isBlank(friendUserId))
            return new ResultJson(StatusConstant.TAGS_PARAMS_ERROR.getCode(),StatusConstant.TAGS_PARAMS_ERROR.getMsg());
        else
            friendTagsService.saveFriendTag(userId, friendUserId, tag);
        return new ResultJson();
    }


    /**
     * 列出指定好友的所有标签
     * @param userId
     * @param friendUserId
     * @return
     */
    @RequestMapping("/listtags")
    public ResultJson listFriendTags(@RequestParam String userId,
                                     @RequestParam String friendUserId){
        return new ResultJson(friendTagsService.listFriendTags(userId, friendUserId));
    }

    /**
     * 列出指定好友标签的所有好友id列表
     * @param userId
     * @param tag
     * @return
     */
    @RequestMapping("/listfriends")
    public ResultJson listTagFriends(@RequestParam String userId,
                                     @RequestParam String tag){
        return new ResultJson(friendTagsService.listTagFriends(userId, tag));
    }

    /**
     * 列出所有好友给我打的标签
     * @param userId
     * @return
     */
    @RequestMapping("/listmytags")
    public ResultJson listMyTagsByFriend(@RequestParam String userId){
        return new ResultJson(friendTagsService.listMyTagsByFriend(userId));
    }

}
