package com.zf.erp.dao;

import com.zf.erp.domain.Emp;

/**
 * 员工数据持久层
 */
public interface IEmpDao extends IBaseDao<Emp> {

    //验证用户名和密码
    Emp logincheckUser(String username,String password);

    void updatePassword(Integer uuid,String password);

}
