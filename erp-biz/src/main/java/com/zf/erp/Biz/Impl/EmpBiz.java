package com.zf.erp.Biz.Impl;


import com.zf.erp.Biz.IEmpBiz;
import com.zf.erp.dao.IEmpDao;
import com.zf.erp.domain.Emp;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.transaction.annotation.Transactional;

/**
 * 员工业务层实现类
 */
public class EmpBiz extends BaseBiz<Emp> implements IEmpBiz {
    //加密次数
    private static int hash = 2;

    private IEmpDao iEmpDao;



    public void setiEmpDao(IEmpDao iEmpDao) {
        this.iEmpDao = iEmpDao;
        super.setiBaseDao(iEmpDao);
    }

    @Override
    public Emp logincheckUser(String username, String password) {

        password = MD5(password,username);
        return iEmpDao.logincheckUser(username,password);

    }

    @Override
    public boolean updatePassword(Integer uuid, String oldPassword, String newPassword) {
        //验证旧密码
        Emp emp = iEmpDao.get(uuid);
        oldPassword = MD5(oldPassword,emp.getUsername());
        if(emp.getPwd().equals(oldPassword)){
            //存入新密码
            newPassword = MD5(newPassword,emp.getUsername());
            iEmpDao.updatePassword(uuid,newPassword);
            return true;
        }
        return false;
    }

    @Override
    public void updatePwd_reset(Integer uuid) {
        Emp emp = iEmpDao.get(uuid);
        String newpassword = MD5(emp.getUsername(),emp.getUsername());
        iEmpDao.updatePassword(uuid,newpassword);
    }

    @Override
    public void add(Emp emp) {

        String pwd = MD5(emp.getPwd(), emp.getUsername());
        emp.setPwd(pwd);
        super.add(emp);
    }

    /**
     * 使用shiro 的MD5加密
     * @param source 源
     * @param salt 扰乱码
     * @return
     */
    public static String MD5(String source,String salt){
        Md5Hash md5 = new Md5Hash(source,salt,hash);
        return md5.toString();
    }

    public static void main(String[] args) {
        System.out.println(MD5("lisi","lisi"));
    }


}
