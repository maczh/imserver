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
public class FriendTagsService {

    @Autowired
    private RedisDao redisDao;

    /**
     * 保存一个好友标签
     * @param userId
     * @param friendUserId
     * @param tag
     */
    public void saveFriendTag(String userId,String friendUserId,String tag){
        Jedis jedis = redisDao.getJedis();
        try {
            jedis.sadd(RedisKey.TAGS_FRIEND+userId+":"+friendUserId,tag);
            jedis.sadd(RedisKey.TAGS_MY_FRIENDS+userId+":"+tag,friendUserId);
            jedis.sadd(RedisKey.TAGS_BY_FRIEND+userId,tag);
        }finally {
            redisDao.close(jedis);
        }
    }


    /**
     * 批量保存多个好友同一个标签
     * @param userId
     * @param friends
     * @param tag
     */
    public void saveFriendsTag(String userId,String friends,String tag){
        List<String> friendList = JSONArray.parseArray(friends,String.class);
        for (String friendUserId : friendList)
            saveFriendTag(userId, friends, tag);
    }

    /**
     * 批量保存一个好友的多个标签
     * @param userId
     * @param friendUserId
     * @param tags
     */
    public void saveFriendTags(String userId,String friendUserId,String tags){
        List<String> tagList = JSONArray.parseArray(tags,String.class);
        for (String tag : tagList)
            saveFriendTag(userId, friendUserId, tag);
    }


    /**
     * 删除一个好友标签
     * @param userId
     * @param friendUserId
     * @param tag
     */
    public void delFriendTag(String userId,String friendUserId,String tag){
        Jedis jedis = redisDao.getJedis();
        try{
            jedis.srem(RedisKey.TAGS_FRIEND+userId+":"+friendUserId,tag);
            jedis.srem(RedisKey.TAGS_MY_FRIENDS+userId+":"+tag,friendUserId);
            jedis.srem(RedisKey.TAGS_BY_FRIEND+userId,tag);
        }finally {
            redisDao.close(jedis);
        }

    }


    /**
     * 批量删除多个好友的同一个标签
     * @param userId
     * @param friends
     * @param tag
     */
    public void delFriendsTag(String userId,String friends,String tag){
        List<String> friendList = JSONArray.parseArray(friends,String.class);
        for (String friendUserId: friendList)
            delFriendTag(userId, friendUserId, tag);
    }


    /**
     * 批量删除一个好友的多个标签
     * @param userId
     * @param friendUserId
     * @param tags
     */
    public void delFriendTags(String userId,String friendUserId,String tags){
        List<String> tagList = JSONArray.parseArray(tags,String.class);
        for (String tag:tagList)
            delFriendTag(userId, friendUserId, tag);
    }

    /**
     * 删除一个好友的所有标签
     * @param userId
     * @param friendUserId
     */
    public void delFriendAllTags(String userId,String friendUserId){
        Set<String> tags = listFriendTags(userId, friendUserId);
        delFriendTags(userId,friendUserId,JSONArray.toJSONString(tags));
    }

    /**
     * 列出好友的所有标签
     * @param userId
     * @param friendUserId
     * @return
     */
    public Set<String> listFriendTags(String userId,String friendUserId){
        Jedis jedis = redisDao.getJedis();
        try{
            return jedis.smembers(RedisKey.TAGS_FRIEND+userId+":"+friendUserId);
        }finally {
            redisDao.close(jedis);
        }
    }

    /**
     * 列出某个标签的所有好友
     * @param userId
     * @param tag
     * @return
     */
    public Set<String> listTagFriends(String userId,String tag){
        Jedis jedis = redisDao.getJedis();
        try {
            return jedis.smembers(RedisKey.TAGS_MY_FRIENDS+userId+":"+tag);
        }finally {
            redisDao.close(jedis);
        }
    }

    /**
     * 列出我被好友打上的所有标签
     * @param userId
     * @return
     */
    public Set<String> listMyTagsByFriend(String userId){
        Jedis jedis = redisDao.getJedis();
        try {
            return jedis.smembers(RedisKey.TAGS_BY_FRIEND+userId);
        }finally {
            redisDao.close(jedis);
        }
    }

}
