package com.zf.erp.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zf.erp.Biz.IBaseBiz;
import com.zf.erp.domain.Dep;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.*;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

    private Class clazz;

    public BaseAction(){
        Type type = this.getClass().getGenericSuperclass();

        ParameterizedType Ptype = (ParameterizedType) type;

        Type[] types = Ptype.getActualTypeArguments();

        clazz = (Class) types[0];
    }

    private IBaseBiz<T> iBaseBiz;

    public void setiBaseBiz(IBaseBiz<T> iBaseBiz) {
        this.iBaseBiz = iBaseBiz;
    }


    public T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    @Setter
    @Getter
    public T t2;


    @Getter
    public Integer uuid;

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    @Getter
    public String Suuid;

    public void setUuid(String Suuid) {
        this.Suuid = Suuid;
    }


    @Override
    public T getModel() {
        return null;
    }

    @Setter
    private int page;

    @Setter
    private int rows;




    /**
     * 条件查询获取列表数据
     */
    public void getList(){


        Long count = iBaseBiz.getCount(t,t2,null);

        List<T> list = iBaseBiz.getList(t,t2,null,page,rows);

        Map<String,Object> map = new HashMap<>();

        map.put("total",count);
        map.put("rows",list);

        write(map);

    }


    /**
     * 条件查询
     */
    public void list(){
        List<T> list = iBaseBiz.getList(t,t2,null,page,rows);
        //把部门列表转JSON字符串
        write(list);
    }


    /**
     * 添加部门
     */
    public void add(){

        //创建回应数组
        Map<String,Object> rtn = new HashMap<>();
        //进行异常捕捉
        try {
            //存储成功
            iBaseBiz.add(t);
            //反馈信息
            rtn.put("success",true);
            rtn.put("message","添加成功！");
        }catch (Exception e){
            e.printStackTrace();
            //存储失败
            rtn.put("success",false);
            rtn.put("message","添加失败！");
        }

        write(rtn);

    }

    /**
     * 删除部门
     */
    public void del(){
        Map<String,Object> rtn = new HashMap<>();
        try {
            if(null != Suuid)
                iBaseBiz.del(Suuid);
            else iBaseBiz.del(uuid);
            rtn.put("success",true);
            rtn.put("message","删除成功！");
        }catch (Exception e){
            e.printStackTrace();
            rtn.put("success",false);
            rtn.put("message","删除失败！");
        }
        write(rtn);
    }



    public void update(){
        //创建回应数组
        Map<String,Object> rtn = new HashMap<>();
        //进行异常捕捉
        try {
            //修改成功
            iBaseBiz.update(t);
            //反馈信息
            rtn.put("success",true);
            rtn.put("message","修改成功！");
        }catch (Exception e){

            //修改失败
            rtn.put("success",false);
            rtn.put("message","修改失败！");
        }

        write(rtn);
    }

    /**
     * 获取某个部门信息
     */
    public void get() throws Exception {
        Map<String,Object> map = new HashMap<>();

        //获取所有字段
        Field[] fields = clazz.getDeclaredFields();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            //如果为get方法
            if(method.getName().contains("get")){
                //存入实际值
                Object invoke = method.invoke(t);
                for (Field field : fields) {
                    //如果当前字段与方法名相同
                    String substring = method.getName().toLowerCase().substring(3);
                    //将字段和对应值读入
                    if(substring.equals(field.getName())){
                        //对部门关联格式进行修正
                        if(null != invoke)
                        if(invoke.getClass().getName().contains("domain")){
                            //获取关联对象中的字段
                            Method getName = invoke.getClass().getMethod("getName");
                            Method getUuid = invoke.getClass().getMethod("getUuid");
                            String[] split = invoke.getClass().getSimpleName().toLowerCase().split("_");
                            map.put(split[0] + ".uuid",getUuid.invoke(invoke));
                            map.put(split[0] + ".name",getName.invoke(invoke));
                        }
                        map.put(field.getName(),invoke);
                    }
                }
            }
        }

        //清除密码
        map.remove("pwd");

        //对日期数据进行修正
        String date = JSON.toJSONStringWithDateFormat(map.get("birthday"),"yyyy-MM-dd");

        map.put("birthday",date.replaceAll("\"",""));


        write(map);
    }

    public void ajaxReturn(boolean success,String message){
        Map<String,Object> map = new HashMap<>();
        map.put("success",success);
        map.put("message",message);
        write(map);
    }

    /**
     * 将列表数据封装成JSON对象并写入响应
     * @param list
     */
    public void write(Object list){
        System.out.println("father write:");
        //将列表数据封装成JSON对象
        String jsonString = JSON.toJSONString(list,SerializerFeature.DisableCircularReferenceDetect);

        //设置编码格式
        System.out.println(jsonString);
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
