package com.ququ.imserver.user.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Macro on 2016/6/15.
 */
@Repository
public class MybatisDao {
    private final static Logger log = LoggerFactory.getLogger(MybatisDao.class);

    private SqlSession sqlSession;

    public MybatisDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public SqlSession getSqlSession() {
        return sqlSession;
    }

    /**
     * 增加
     *
     * @param key
     * @param obj
     * @return
     */
    public Integer save(String key, Object obj) {
        if (obj == null) {
            log.warn("save(String key,Object obj)　obj is null");
            return getSqlSession().update(key);
        }
        return getSqlSession().insert(key, obj);
    }

    /**
     * 删除
     *
     * @param key
     * @param obj
     * @return
     */
    public Integer delete(String key, Object obj) {
        if (obj == null) {
            log.warn("delete(String key,Object obj)　obj is null");
            return getSqlSession().update(key);
        }
        return getSqlSession().delete(key, obj);
    }

    /**
     * 修改
     *
     * @param key
     * @param obj
     * @return
     */
    public Integer update(String key, Object obj) {
        if (obj == null) {
            log.warn("update(String key,Object obj)　obj is null");
            return getSqlSession().update(key);
        }
        return getSqlSession().update(key, obj);
    }

    /**
     * 根据ｋｅｙ查询　一条记录
     *
     * @param <T>
     * @param key
     * @return
     */

    public <T> T getOne(String key) {
        return (T) getSqlSession().selectOne(key);
    }

    /**
     * 根据ｋｅｙ　和对象字段查询　一条记录
     *
     * @param <T>
     * @param key
     * @param t
     * @return
     */
    public <T> T getOne(String key, T t) {
        if (t == null) {
            log.warn("get(String key,T t)　t is null");
            return getOne(key);
        }
        log.debug("MyBatis selectOne传入参数:key={},对象={}",key,t.toString());
        return (T) getSqlSession().selectOne(key, t);
    }

    /**
     * 根据ｋｅｙ查询　一条记录
     *
     * @param <T>
     * @param key
     * @return
     */
    public <T> T get(String key) {
        return getOne(key);
    }

    /**
     * 根据ｋｅｙ　和对象字段查询　一条记录
     *
     * @param <T>
     * @param key
     * @param t
     * @return
     */
    public <T> T get(String key, T t) {
        return getOne(key, t);
    }

    /**
     * 根据ｋｅｙ查询列表
     *
     * @param <T>
     * @param key
     * @return
     */
    public <T> List<T> getList(String key) {
        return getSqlSession().selectList(key);
    }


    /**
     * 根据ｋｅｙ　和　ID列表　查询列表
     *
     * @param <T>
     * @param key
     * @param t
     * @return
     */
    public <T> List<T> getList(String key, T t) {
        if (t == null) {
            log.warn("getList(String key,T t)　t is null");
            return getList(key);
        }
        return getSqlSession().selectList(key, t);
    }

    /**
     * 根据ｋｅｙ　,ｍａｐ　　查询列表
     *
     * @param <T>
     * @param key
     * @param map
     * @return
     */
    public <T> List<T> getListByMap(String key, Map<?, ?> map) {
        if (map == null) {
            log.debug("getListByMap(String key,Map map)　map is null");
            return getList(key);
        }
        return getSqlSession().selectList(key, map);
    }


    /**
     * 根据ｋｅｙ　执行存储过程
     *
     * @param key
     * @return
     */
    public Object getProc(String key) {
        return getSqlSession().selectList(key);
    }

    /**
     * 根据ｋｅｙ　和ｍａｐ　执行存储过程
     *
     * @param key
     * @param map
     * @return
     */
    public Object getProc(String key, Map<?, ?> map) {
        if (map == null) {
            log.warn("getProc(String key,Map map)　map is null");
            return getProc(key);
        }
        return getSqlSession().selectList(key, map);
    }

    public List<Object> test(String... sqls) {
        try {
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < sqls.length; i++) {
                map.put("s" + (i + 1), sqls[i]);
            }
            return getSqlSession().selectList("Imall_userMapper.getsql", map);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 根据ｋｅｙ　和　对象属性　查询列表
     *
     * @param <T>
     * @param key
     * @param t
     * @return
     */
    public <T> List<T> getListByIds(String key, List<?> ids) {
        if (ids == null || ids.size() == 0) {
            return new LinkedList<T>();
        } else {
            return getSqlSession().selectList(key, ids);
        }
    }


    /**
     * 根据ｋｅｙ　和　对象属性　查询列表
     *
     * @param <T>
     * @param key
     * @param t
     * @return
     */
    public <T> List<T> getListByRelIds(String key, String idColumnName, List<?> idValues) {
        if (idColumnName == null || idColumnName.trim().equals("") || idValues == null || idValues.size() == 0) {
            return new LinkedList<T>();
        } else {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("idColumnName", idColumnName);
            params.put("idValues", idValues);
            return getSqlSession().selectList(key, params);
        }
    }

    public Long count(String key, Object params) {
        return getSqlSession().selectOne(key, params);
    }

//    public void insert(String key,Map<String,String > params){
//        getSqlSession().insert(key,params);
//    }
}
