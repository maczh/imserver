package com.ququ.imserver.user.service;

import com.ququ.imserver.user.dao.ImUserDao;
import com.ququ.imserver.user.pojo.ImUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Service
@EnableTransactionManagement
public class ImUserService {
    @Autowired
    private ImUserDao imUserDao;

    public ImUser getImUserByUserId(String userId){
        return imUserDao.getImUserByUserId(userId);
    }

    public ImUser getImUserByImUid(String imUid){
        return imUserDao.getImUserByImUid(imUid);
    }

    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = "im:cache", key = "'imuser:userid:'+#imUser.userid"),
                    @CacheEvict(value = "im:cache", key = "'imuser:uid:'+#imUser.imuid")
            }
    )
    public Integer update(ImUser imUser){
        return imUserDao.update(imUser);
    }

    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = "im:cache", key = "'imuser:userid:'+#imUser.userid"),
                    @CacheEvict(value = "im:cache", key = "'imuser:uid:'+#imUser.imuid")
            }
    )
    public Integer save(ImUser imUser){
        return imUserDao.save(imUser);
    }
}
