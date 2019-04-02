package com.zf.erp.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 商品分类实体类
 * @author Administrator *
 */
@Getter
@Setter
public class Goodstype {	
	private Integer uuid;//商品类型编号
	private String name;//商品类型名称

	@Override
	public String toString() {
		return "Goodstype{" +
				"uuid=" + uuid +
				", name='" + name + '\'' +
				'}';
	}
}
