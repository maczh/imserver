package com.ququ.imserver.user.redis;

import com.ququ.imserver.constant.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

@Repository
public class ImToken {

    @Autowired
    private RedisDao redisDao;

    /**
     * 保存IM的Token，自动切换IM通道商
     * @param imUid
     * @param token
     */
    public void setImToken(String imUid,String token){
        Jedis jedis = redisDao.getJedis();
        try {
            String imApi = jedis.get(RedisKey.IM_API);
            jedis.set(RedisKey.TOKEN+imApi+":"+imUid,token);
        }finally {
            redisDao.close(jedis);
        }
    }

    /**
     * 获取IM的Token
     * @param imUid
     * @return
     */
    public String getImToken(String imUid){
        Jedis jedis = redisDao.getJedis();
        try {
            String imApi = jedis.get(RedisKey.IM_API);
            return jedis.get(RedisKey.TOKEN+imApi+":"+imUid);
        }finally {
            redisDao.close(jedis);
        }
    }

}
