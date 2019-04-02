package com.zf.erp.dao.Impl;

import com.zf.erp.dao.IBaseDao;
import com.zf.erp.domain.Menu;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 通用DAO实现类
 * @param <T>
 */
public class BaseDao<T> extends HibernateDaoSupport implements IBaseDao<T> {

    private Class clazz;

    public BaseDao(){
        Type type = this.getClass().getGenericSuperclass();

        ParameterizedType Ptype = (ParameterizedType) type;

        Type[] types = Ptype.getActualTypeArguments();

        clazz = (Class) types[0];
    }

    public List<T> getAll() {
        return (List<T>) this.getHibernateTemplate().find("from T");
    }


    @Override
    public List<T> getList(T t1,T t2,Object param,int firstResult, int maxResults) {
        DetachedCriteria dc = deta(t1,t2,param);
        return (List<T>) this.getHibernateTemplate().findByCriteria(dc,(firstResult-1)*maxResults, maxResults);
    }

    @Override
    public Long getCount(T t1,T t2,Object param) {
        DetachedCriteria dc = deta(t1,t2,param);
        System.out.println("dc" + dc);
        dc.setProjection(Projections.rowCount());
        List<Long> list = (List<Long>)getHibernateTemplate().findByCriteria(dc);
        System.out.println(list);
        return list.get(0);
    }

    /**
     * 子类具体实现该方法
     * @return
     */
    public DetachedCriteria deta(T t1, T t2, Object param){
        return null;
    }

    public void add(T t){
        this.getHibernateTemplate().save(t);
    }

    @Override
    public void del(Integer uuid) {
        //获取持久化对象
        T t = (T) this.getHibernateTemplate().get(clazz, uuid);
        //删除
        this.getHibernateTemplate().delete(t);
    }

    @Override
    public void del(String uuid) {
        //获取持久化对象
        T t = (T) this.getHibernateTemplate().get(clazz, uuid);
        //删除
        this.getHibernateTemplate().delete(t);
    }

    @Override
    public T get(Integer uuid) {
        return (T) this.getHibernateTemplate().get(clazz,uuid);
    }

    //多态，覆写父类的get方法
    public T get(String uuid) {
        return (T) this.getHibernateTemplate().get(Menu.class,uuid);
    }

    @Override
    public void update(T t) {
        this.getHibernateTemplate().update(t);
    }
    
}
