package com.zf.erp.domain;
/**
 * 角色实体类
 * @author Administrator *
 */
public class Role {	
	private Integer uuid;//编号
	private String name;//名称

	public Integer getUuid() {		
		return uuid;
	}
	public void setUuid(Integer uuid) {
		this.uuid = uuid;
	}
	public String getName() {		
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
