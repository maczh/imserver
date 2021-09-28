package com.ququ.imserver.user.dao;



import java.util.List;

public interface BaseDao<T> {

    public Integer save(T t);

    public Integer update(T t);

    public Integer delete(T t);

    public T getEntity(T t);

    public List<T> getList(T t);
}
