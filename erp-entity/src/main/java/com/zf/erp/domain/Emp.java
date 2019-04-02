package com.zf.erp.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * 员工实体类
 * @author Administrator *
 */
@Getter
@Setter
public class Emp {	
	private Integer uuid;//编号
	private String username;//登陆名
	//不转换json字符串
	@JSONField(serialize=false)
	private String pwd;//登陆密码
	private String name;//真实姓名
	private Integer gender;//性别
	private String email;//邮件地址
	private String tele;//联系电话
	private String address;//联系地址
	private java.util.Date birthday;//出生年月日
	private Dep dep;//部门


	@Override
	public String toString() {
		return "Emp{" +
				"uuid=" + uuid +
				", username='" + username + '\'' +
				", pwd='" + pwd + '\'' +
				", name='" + name + '\'' +
				", gender=" + gender +
				", email='" + email + '\'' +
				", tele='" + tele + '\'' +
				", address='" + address + '\'' +
				", birthday=" + birthday +
				", dep=" + dep +
				'}';
	}
}
