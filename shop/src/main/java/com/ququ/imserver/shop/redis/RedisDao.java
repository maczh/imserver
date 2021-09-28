package com.ququ.imserver.shop.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by Macro on 17/1/10.
 */
@Configuration
@EnableAutoConfiguration
@ConfigurationProperties(prefix = "spring.redis")
public class RedisDao {
    Logger logger = LoggerFactory.getLogger(RedisDao.class);
    private String host;
    private int port;
    private String password;
    private int timeout;
    private int database;

    private JedisPool jedisPool;
    private JedisPoolConfig config;

    @Bean
    public JedisPoolConfig getRedisConfig1(){
        config = new JedisPoolConfig();
        return config;
    }

    @Bean
    public JedisPool getRedisPool1(){
        jedisPool = new JedisPool(config,host,port,timeout,password);
        logger.info("init JedisPool ...");
        return jedisPool;
    }

    public Jedis getJedis(){
        Jedis jedis = jedisPool.getResource();
        jedis.select(database);
        return jedis;
    }

    public void close(Jedis jedis){
        jedisPool.returnResource(jedis);
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    @Override
    public String toString() {
        return "JedisDao{" +
                "hostName='" + host + '\'' +
                ", port='" + port + '\'' +
                ", password='" + password + '\'' +
                ", timeout='" + timeout + '\'' +
                '}';
    }
}
