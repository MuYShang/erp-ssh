package com.zf.erp.dao.Impl;

import com.zf.erp.dao.IDepDao;
import com.zf.erp.domain.Dep;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

/**
 * 部门接口实现类
 */
public class DepDao extends BaseDao<Dep> implements IDepDao {

    @Override
    public DetachedCriteria deta(Dep dep,Dep dep2,Object param){
        DetachedCriteria dc = DetachedCriteria.forClass(Dep.class);

        if(null != dep){
            //部门名称的模糊查询
            if(dep.getName() != null && dep.getName().trim().length() >0){
                dc.add(Restrictions.like("name",dep.getName(), MatchMode.ANYWHERE));
            }

            //部门名称的模糊查询
            if(dep.getTele() != null && dep.getTele().trim().length() >0){
                dc.add(Restrictions.like("tele",dep.getTele(), MatchMode.ANYWHERE));
            }
        }
        return dc;
    }
}
