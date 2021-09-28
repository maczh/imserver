package com.ququ.imserver.user.redis;

import com.ququ.imserver.constant.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

@Repository
public class ImUid {

    private static RedisDao redisDao;

    @Autowired
    public void setRedisDao(RedisDao redisDao) {
        this.redisDao = redisDao;
    }

    private static final String IMUID_PREFIX = "ququ";
    private static final String IMUID_FIRST = "1000000001";

    /**
     * 获取最新的IMUID编号
     * @return
     */
    public static String getNewImuid(){
        Jedis jedis = redisDao.getJedis();
        try {
            if (jedis.exists(RedisKey.IM_UID_LAST)){
                return IMUID_PREFIX+jedis.incr(RedisKey.IM_UID_LAST);
            }else {
                jedis.set(RedisKey.IM_UID_LAST,IMUID_FIRST);
                return IMUID_PREFIX+IMUID_FIRST;
            }
        }finally {
            redisDao.close(jedis);
        }
    }
}
