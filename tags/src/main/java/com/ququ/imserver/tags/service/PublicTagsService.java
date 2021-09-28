package com.ququ.imserver.tags.service;

import com.alibaba.fastjson.JSONArray;
import com.ququ.imserver.constant.RedisKey;
import com.ququ.imserver.tags.redis.RedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

@Service
public class PublicTagsService {

    @Autowired
    private RedisDao redisDao;


    /**
     * 保存公共标签
     * @param userId
     * @param tag
     */
    public void savePublicTag(String userId,String tag){
        Jedis jedis = redisDao.getJedis();
        try {
            jedis.sadd(RedisKey.TAGS_PUBLIC+tag,userId);
            jedis.sadd(RedisKey.TAGS_PUBLIC_MY+userId,tag);
        }finally {
            redisDao.close(jedis);
        }
    }

    /**
     * 保存一个用户的多个标签
     * @param userId
     * @param tags
     */
    public void  savePublicTags(String userId,String tags){
        List<String> tagList = JSONArray.parseArray(tags,String.class);
        for (String tag: tagList)
            savePublicTag(userId,tag);
    }


    /**
     * 批量保存多个用户同一个标签
     * @param userIds
     * @param tag
     */
    public void saveUsersPublicTag(String userIds,String tag){
        List<String> userList = JSONArray.parseArray(userIds,String.class);
        for (String userId : userList)
            savePublicTag(userId,tag);
    }

    /**
     * 删除一个用户的一个公共标签
     * @param userId
     * @param tag
     */
    public void delUserPublicTag(String userId,String tag){
        Jedis jedis = redisDao.getJedis();
        try {
            jedis.srem(RedisKey.TAGS_PUBLIC+tag,userId);
            jedis.srem(RedisKey.TAGS_PUBLIC_MY+userId,tag);
        }finally {
            redisDao.close(jedis);
        }
    }

    /**
     * 从一个标签中删除多个用户
     * @param userIds
     * @param tag
     */
    public void delUsersPublicTag(String userIds,String tag){
        List<String> userList = JSONArray.parseArray(userIds,String.class);
        for (String userId: userList)
            delUserPublicTag(userId,tag);
    }

    /**
     * 删除一个用户的多个公共标签
     * @param userId
     * @param tags
     */
    public void delUserPublicTags(String userId,String tags){
        List<String> tagList = JSONArray.parseArray(tags,String.class);
        for (String tag: tagList)
            delUserPublicTag(userId,tag);
    }



    /**
     * 销毁一个公共标签
     * @param tag
     */
    public void removePublicTag(String tag){
        Jedis jedis = redisDao.getJedis();
        try {
            Set<String> userSet = jedis.smembers(RedisKey.TAGS_PUBLIC+tag);
            for (String userId:userSet)
                jedis.srem(RedisKey.TAGS_PUBLIC_MY+userId,tag);
            jedis.del(RedisKey.TAGS_PUBLIC+tag);
        }finally {
            redisDao.close(jedis);
        }
    }


    /**
     * 获取一个公共标签中所有的用户列表
     * @param tag
     * @return
     */
    public Set<String> listPublicTagUsers(String tag){
        Jedis jedis = redisDao.getJedis();
        try {
            return jedis.smembers(RedisKey.TAGS_PUBLIC+tag);
        }finally {
            redisDao.close(jedis);
        }
    }


    /**
     * 获取用户所有的公共标签
     * @param userId
     * @return
     */
    public Set<String> listMyPublicTags(String userId){
        Jedis jedis = redisDao.getJedis();
        try {
            return jedis.smembers(RedisKey.TAGS_PUBLIC_MY+userId);
        }finally {
            redisDao.close(jedis);
        }
    }

}
