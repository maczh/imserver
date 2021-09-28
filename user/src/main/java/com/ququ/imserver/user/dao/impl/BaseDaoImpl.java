package com.ququ.imserver.user.dao.impl;

import com.ququ.imserver.user.dao.BaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by chenglinfu on 2016/6/15.
 */
@Repository
public class BaseDaoImpl<T> implements BaseDao<T> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MybatisDao dao;


    public Integer save(T t) {
        logger.debug("MySQL inserting data:{}", t.toString());
        return dao.save(t.getClass().getSimpleName().toUpperCase() + ".add" + t.getClass().getSimpleName(), t);
    }

    public Integer update(T t) {
        return dao.update(t.getClass().getSimpleName().toUpperCase() + ".update" + t.getClass().getSimpleName(), t);
    }

    public Integer delete(T t) {
        return dao.delete(t.getClass().getSimpleName().toUpperCase() + ".del" + t.getClass().getSimpleName(), t);
    }


    public T getEntity(T t) {
        logger.debug("MySQL Select查询，传入参数:{}",t.toString());
        return dao.getOne(t.getClass().getSimpleName().toUpperCase() + ".query" + t.getClass().getSimpleName(), t);
    }

    public List<T> getList(T t) {
        return dao.getList(t.getClass().getSimpleName().toUpperCase() + ".list" + t.getClass().getSimpleName(), t);
    }
}
