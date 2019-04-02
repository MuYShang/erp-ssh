package com.zf.erp.Biz.Impl;

import com.zf.erp.Biz.IBaseBiz;
import com.zf.erp.dao.IBaseDao;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Transactional
public class BaseBiz<T> implements IBaseBiz<T> {

    private IBaseDao<T> iBaseDao;

    public void setiBaseDao(IBaseDao<T> iBaseDao) {
        this.iBaseDao = iBaseDao;
    }

    /**
     * 获取部门所有数据
     * @return
     */
    public List<T> getAll() {
        return (List<T>) iBaseDao.getAll();
    }


    @Override
    public Long getCount(T t1, T t2, Object param) {
        return iBaseDao.getCount(t1,t2,param);
    }

    /**
     * 模糊查询获取部门数据
     */
    @Override
    public List<T> getList(T t1, T t2, Object param, int firstResult, int maxResults) {
        return iBaseDao.getList(t1,t2,param,firstResult,maxResults);
    }

    /**
     * 获取姓名
     * @param uuid
     * @param map
     * @param iBaseDao
     * @return
     * @throws Exception
     */
    public String getName(Integer uuid, Map<Integer,String> map, IBaseDao iBaseDao) {

        if(null == uuid){
            return null;
        }
        String name = map.get(uuid);
        if(null == name){
            Object obj = iBaseDao.get(uuid);
            Class objClass = obj.getClass();
            Method getName = null;
            try {
                getName = objClass.getMethod("getName", null);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            try {
                name = (String) getName.invoke(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            map.put(uuid,name);
            return name;
        }

        return name;
    }



    @Override
    public void add(T t) {
        iBaseDao.add(t);
    }

    @Override
    public void del(Integer uuid) {
        iBaseDao.del(uuid);
    }

    @Override
    public void del(String uuid) {
        iBaseDao.del(uuid);
    }

    @Override
    public T get(Integer uuid) {
        return iBaseDao.get(uuid);
    }

    @Override
    public T get(String uuid) {
        return iBaseDao.get(uuid);
    }

    @Override
    public void update(T t) {
        iBaseDao.update(t);
    }
}
