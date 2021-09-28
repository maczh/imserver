package com.ququ.imserver.imapi.redis;

import com.ququ.imserver.constant.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

@Repository
public class ImApiSetting {

    private static RedisDao redisDao;

    @Autowired
    public void setRedisDao(RedisDao redisDao) {
        this.redisDao = redisDao;
    }

    public static String getImApiUsing(){
        Jedis jedis = redisDao.getJedis();
        try {
            return jedis.get(RedisKey.IM_API);
        }finally {
            redisDao.close(jedis);
        }
    }
}
