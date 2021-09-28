package com.ququ.imserver.user.dao;

import com.ququ.imserver.user.pojo.ImUser;

public interface ImUserDao extends BaseDao<ImUser> {

    ImUser getImUserByUserId(String userId);

    ImUser getImUserByImUid(String imUid);

}
