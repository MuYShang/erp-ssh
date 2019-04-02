package com.zf.erp.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zf.erp.Biz.IEmpBiz;
import com.zf.erp.domain.Emp;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginAction {

    @Setter
    @Getter
    private String username;

    @Setter
    @Getter
    private String pwd;

//    因为shiro的存在，不需要在这里进行查询
//    @Resource(name = "EmpBiz")
//    private IEmpBiz iEmpBiz;

    /**
     * 验证用户名和密码，并将登录状态存入域中
     */
    public void checkUser(){
        try {
//            Emp emp = iEmpBiz.logincheckUser(username, pwd);
//            if(null != emp){
//                ajaxReturn(true,"");
//                HttpSession session = ServletActionContext.getRequest().getSession();
//                session.setAttribute("isLogin",emp);
//            }else{
//                ajaxReturn(false,"用户名或密码错误");
//            }
            //创建令牌，身份证明
            UsernamePasswordToken upt = new UsernamePasswordToken(username,pwd);
            //获取主题Subject
            Subject subject = SecurityUtils.getSubject();
            subject.login(upt);
            ajaxReturn(true,"");
        }catch (Exception e){
            e.printStackTrace();
            ajaxReturn(false,"用户名或密码错误");
        }


    }

    /**
     * 主页显示用户名
     */
    public void showName(){
        try {
//            HttpSession session = ServletActionContext.getRequest().getSession();
//            Emp login = (Emp) session.getAttribute("isLogin");
            Subject subject = SecurityUtils.getSubject();
            Emp login = (Emp) subject.getPrincipal();
            if(null != login){
                ajaxReturn(true,login.getName());
            }else{
                ajaxReturn(false,"登录超时");
            }
        }catch (Exception e){
            ajaxReturn(false,"请重新登录");
        }
    }

    /**
     * 安全退出
     */
    public void loginOut(){
//        HttpSession session = ServletActionContext.getRequest().getSession();
//        session.setAttribute("isLogin",null);
        SecurityUtils.getSubject().logout();
        ajaxReturn(false,"");
    }



    public void ajaxReturn(boolean success,String message){
        Map<String,Object> map = new HashMap<>();
        map.put("success",success);
        map.put("message",message);
        write(map);
    }

    public void write(Object list){
        //将列表数据封装成JSON对象
        String jsonString = JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect);

        //设置编码格式
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");

        //获取response并将数据写入
        try {
            response.getWriter().write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
