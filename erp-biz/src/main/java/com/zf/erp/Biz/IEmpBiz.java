package com.zf.erp.Biz;

import com.zf.erp.domain.Emp;

/**
 * 员工业务层接口
 */
public interface IEmpBiz extends IBaseBiz<Emp> {

    //验证用户名和密码
    Emp logincheckUser(String username,String password);

    //修改密码
    boolean updatePassword(Integer uuid,String oldPassword,String newPassword);

    //重置密码
    void updatePwd_reset(Integer uuid);
}
