package com.zf.erp.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 表Dep实体类
 */
@Getter@Setter
public class Dep {

    //部门UUID
    private Integer uuid;

    //部门名称
    private String name;

    //部门电话
    private String tele;


    @Override
    public String toString() {
        return "Dep{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", tele='" + tele + '\'' +
                '}';
    }
}
