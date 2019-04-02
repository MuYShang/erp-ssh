package com.zf.erp.realm;

import com.zf.erp.Biz.IEmpBiz;
import com.zf.erp.domain.Emp;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

public class erpRealm extends AuthorizingRealm {

    private IEmpBiz iEmpBiz;

    public void setiEmpBiz(IEmpBiz iEmpBiz) {
        this.iEmpBiz = iEmpBiz;
    }

    //授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection collection) {
        return null;
    }

    //认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("认证");
        //强转
        UsernamePasswordToken utp = (UsernamePasswordToken) token;
        String password = new String(utp.getPassword());

        Emp emp = iEmpBiz.logincheckUser(utp.getUsername(), password);
        if (null != emp){
            /**
             * 构造器参数1：主角 = 登录用户
             * 参数2：授权码
             * 参数3：realm名称
             */
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(emp,password,getName());
            System.out.println(info);
            return info;
        }
        return null;
    }
}
