package com.zf.erp.dao;


import com.zf.erp.domain.Menu;

import java.util.List;

/**
 * 通用Dao接口
 */
public interface IBaseDao<T> {

    //获取部门数据
    public List<T> getAll();

    //离线模糊查询获取部门数据
    List<T> getList(T t1,T t2,Object param,int firstResult, int maxResults);

    //获取根据离线查询条件总数
    Long getCount(T t1,T t2,Object param);

    //新增部门
    void add(T t);

    //删除部门
    void del(Integer uuid);
    void del(String uuid);

    //单独获取某个部门
    T get(Integer uuid);
    T get(String uuid);

    //修改
    void update(T t);



}
