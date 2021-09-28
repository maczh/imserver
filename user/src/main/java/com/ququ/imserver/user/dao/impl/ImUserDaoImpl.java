package com.ququ.imserver.user.dao.impl;

import com.ququ.imserver.user.dao.ImUserDao;
import com.ququ.imserver.user.pojo.ImUser;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class ImUserDaoImpl extends BaseDaoImpl<ImUser> implements ImUserDao {
    @Override
    @Cacheable(value = "im:cache",key = "'imuser:userid:'+#userId")
    public ImUser getImUserByUserId(String userId) {
        ImUser imUser = new ImUser();
        imUser.setUserid(userId);
        return getEntity(imUser);
    }

    @Override
    @Cacheable(value = "im:cache",key = "'imuser:uid:'+#imUid")
    public ImUser getImUserByImUid(String imUid) {
        ImUser imUser = new ImUser();
        imUser.setImuid(imUid);
        return getEntity(imUser);
    }

}
