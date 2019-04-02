package com.zf.erp.action;

import com.zf.erp.Biz.IEmpBiz;
import com.zf.erp.domain.Emp;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

public class EmpAction extends BaseAction<Emp> {

    private IEmpBiz empBiz;

    @Resource(name = "EmpBiz")
    public void setEmpBiz(IEmpBiz empBiz) {
        this.empBiz = empBiz;
        super.setiBaseBiz(empBiz);
    }

    private Emp emp = new Emp();


    @Getter
    @Setter
    private String newPassword;

    /**
     * 获取某个部门信息
     */
    public void get() throws Exception {

        emp = empBiz.get(emp.getUuid());

        super.setT(emp);
        super.get();
    }

    public void updatePwd_reset(){
        try {
            empBiz.updatePwd_reset(emp.getUuid());
            ajaxReturn(true,"当前用户密码已重置为用户名");
        }catch (Exception e){
            e.printStackTrace();
            ajaxReturn(false,"重置失败");
        }
    }

    /**
     * 修正员工的新增方法，让新增员工的默认密码为员工的用户名
     */
    @Override
    public void add() {
        emp.setPwd(emp.getUsername());
        super.setT(emp);
        super.add();
    }

    /**
     * 删除部门
     */
    public void del(){
        super.setUuid(emp.getUuid());
        super.del();
    }

    public void updatePassword(){

        //获取旧密码
        String password = emp.getPwd();
        HttpSession session = ServletActionContext.getRequest().getSession();
        Emp loginemp = (Emp) session.getAttribute("isLogin");
        if(null == loginemp){
            ajaxReturn(false,"未登录");
        }
        Integer uuid = loginemp.getUuid();
        if(empBiz.updatePassword(uuid,password,newPassword)){
            ajaxReturn(true,"修改成功");
            loginemp.setPwd(emp.getPwd());
            session.setAttribute("isLogin",loginemp);
        }else{
            ajaxReturn(false,"修改失败");
        }

    }


    @Override
    public Emp getModel() {
        super.setT(emp);
        return emp;
    }

}
