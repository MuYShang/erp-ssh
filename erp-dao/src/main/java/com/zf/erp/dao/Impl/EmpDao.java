package com.zf.erp.dao.Impl;

import com.zf.erp.dao.IEmpDao;
import com.zf.erp.domain.Emp;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class EmpDao extends BaseDao<Emp> implements IEmpDao {


    /**
     * 登录验证
     * @param username
     * @param password
     * @return
     */
    @Override
    public Emp logincheckUser(String username, String password) {
        String hql = "from Emp where username=? and pwd=?";
        List<Emp> list = (List<Emp>) this.getHibernateTemplate().find(hql, username, password);
        if(list.size() >0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 更新用户密码
     * @param uuid
     * @param password
     */
    @Override
    public void updatePassword(Integer uuid, String password) {
        String hql = "update Emp set pwd=? where uuid=?";
        this.getHibernateTemplate().bulkUpdate(hql,password,uuid);
    }


    /**
     * 构建查询条件
     * @param param
     * @return
     */
    public DetachedCriteria deta(Emp emp1,Emp emp2,Object param){
        DetachedCriteria dc=DetachedCriteria.forClass(Emp.class);
        if(emp1!=null){
            if(null != emp1.getUsername() && emp1.getUsername().trim().length()>0){
                dc.add(Restrictions.like("username", emp1.getUsername(), MatchMode.ANYWHERE));
            }
            if(null != emp1.getPwd() && emp1.getPwd().trim().length()>0){
                dc.add(Restrictions.like("pwd", emp1.getPwd(), MatchMode.ANYWHERE));
            }
            if(null != emp1.getName() && emp1.getName().trim().length()>0){
                dc.add(Restrictions.like("name", emp1.getName(), MatchMode.ANYWHERE));
            }
            if(null != emp1.getEmail() && emp1.getEmail().trim().length()>0){
                dc.add(Restrictions.like("email", emp1.getEmail(), MatchMode.ANYWHERE));
            }
            if(null != emp1.getTele() && emp1.getTele().trim().length()>0){
                dc.add(Restrictions.like("tele", emp1.getTele(), MatchMode.ANYWHERE));
            }
            //根据性别查询
            if(null != emp1.getGender()){
                dc.add(Restrictions.eq("gender", emp1.getGender()));
            }
            if(null != emp1.getAddress() && emp1.getAddress().trim().length()>0){
                dc.add(Restrictions.like("address", emp1.getAddress(), MatchMode.ANYWHERE));
            }
            //根据部门查询
            if(null != emp1.getDep() && null != emp1.getDep().getUuid()){
                dc.add(Restrictions.eq("dep", emp1.getDep()));
            }
            //出生年月日查询 起始日期
            if(null != emp1.getBirthday()){
                dc.add(Restrictions.ge("birthday", emp1.getBirthday()));
            }

        }
        if(null != emp2){
            //出生年月日查询 结束日期
            if(null != emp2.getBirthday()){
                dc.add(Restrictions.le("birthday", emp2.getBirthday()));
            }
        }

        return dc;
    }



}
