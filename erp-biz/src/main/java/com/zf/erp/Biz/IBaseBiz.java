package com.zf.erp.Biz;


import java.util.List;

/**
 * 通用Biz接口
 */
public interface IBaseBiz<T> {

    //获取数据
    public List<T> getAll();

    //离线模糊查询获取部门数据
    List<T> getList(T t1,T t2,Object param,int firstResult, int maxResults);

    //获取根据离线查询条件总数
    Long getCount(T t1,T t2,Object param);

    //新增
    void add(T t);

    //删除
    void del(Integer uuid);
    void del(String uuid);

    //单独获取某个
    T get(Integer uuid);
    T get(String uuid);

    //修改
    void update(T t);
    
}
