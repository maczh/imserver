package com.ququ.imserver.tags.controller;

import com.ququ.common.result.ResultJson;
import com.ququ.imserver.constant.StatusConstant;
import com.ququ.imserver.tags.service.PublicTagsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/im/tags/public")
public class PublicTagsController {

    @Autowired
    private PublicTagsService publicTagsService;

    /**
     * 保存用户公共标签
     *
     * 参数 userIds+tag: 保存多个用户的同一个标签
     * 参数 userId+tags: 保存一个用户的多个标签
     * 参数 userId+tag:  保存一个用户单个标签
     * @param userId
     * @param tag
     * @param userIds
     * @param tags
     * @return
     */
    @RequestMapping("/add")
    public ResultJson savePublicTag(@RequestParam(required = false) String userId,
                                    @RequestParam(required = false) String tag,
                                    @RequestParam(required = false) String userIds,
                                    @RequestParam(required = false) String tags){
        if (StringUtils.isNotBlank(userIds)){
            if (StringUtils.isBlank(tag))
                return new ResultJson(StatusConstant.TAGS_PARAMS_ERROR.getCode(),StatusConstant.TAGS_PARAMS_ERROR.getMsg());
            else
                publicTagsService.saveUsersPublicTag(userIds, tag);
        }else if (StringUtils.isNotBlank(tags)){
            if (StringUtils.isBlank(userId))
                return new ResultJson(StatusConstant.TAGS_PARAMS_ERROR.getCode(),StatusConstant.TAGS_PARAMS_ERROR.getMsg());
            else
                publicTagsService.savePublicTags(userId, tags);
        }else if (StringUtils.isBlank(userId) || StringUtils.isBlank(tag))
            return new ResultJson(StatusConstant.TAGS_PARAMS_ERROR.getCode(),StatusConstant.TAGS_PARAMS_ERROR.getMsg());
        else
            publicTagsService.savePublicTag(userId, tag);
        return new ResultJson();
    }


    /**
     * 删除一个或多个公共标签、销毁公共标签
     * 参数 仅tag： 销毁标签
     * 参数 userIds+tag: 删除多个用户的同一个标签
     * 参数 userId+tags: 删除一个用户的多个标签
     * 参数 userId+tag:  删除一个用户单个标签
     * @param userId
     * @param tag
     * @param userIds
     * @param tags
     * @return
     */
    @RequestMapping("/del")
    public ResultJson delPublicTag(@RequestParam(required = false) String userId,
                                    @RequestParam(required = false) String tag,
                                    @RequestParam(required = false) String userIds,
                                    @RequestParam(required = false) String tags){
        if (StringUtils.isNotBlank(userIds)){
            if (StringUtils.isBlank(tag))
                return new ResultJson(StatusConstant.TAGS_PARAMS_ERROR.getCode(),StatusConstant.TAGS_PARAMS_ERROR.getMsg());
            else
                publicTagsService.delUsersPublicTag(userIds, tag);
        }else if (StringUtils.isNotBlank(tags)){
            if (StringUtils.isBlank(userId))
                return new ResultJson(StatusConstant.TAGS_PARAMS_ERROR.getCode(),StatusConstant.TAGS_PARAMS_ERROR.getMsg());
            else
                publicTagsService.delUserPublicTags(userId, tags);
        }else if (StringUtils.isNotBlank(tag) && StringUtils.isBlank(userId))
            publicTagsService.removePublicTag(tag);
        else if (StringUtils.isBlank(userId) || StringUtils.isBlank(tag))
            return new ResultJson(StatusConstant.TAGS_PARAMS_ERROR.getCode(),StatusConstant.TAGS_PARAMS_ERROR.getMsg());
        else
            publicTagsService.delUserPublicTag(userId, tag);
        return new ResultJson();
    }

    /**
     * 列出某个标签的所有用户
     * @param tag
     * @return
     */
    @RequestMapping("/list/user")
    public ResultJson listPublicTagUsers(@RequestParam String tag){
        Set<String> userIdSet = publicTagsService.listPublicTagUsers(tag);
        return new ResultJson(userIdSet);
    }


    /**
     * 列出某个用户所属的所有标签
     * @param userId
     * @return
     */
    @RequestMapping("/list/tag")
    public ResultJson listMyPublicTags(@RequestParam String userId){
        Set<String> tagSet = publicTagsService.listMyPublicTags(userId);
        return new ResultJson(tagSet);
    }


}
