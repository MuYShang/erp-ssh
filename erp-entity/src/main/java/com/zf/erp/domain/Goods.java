package com.zf.erp.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 商品实体类
 * @author Administrator *
 */
@Getter
@Setter
public class Goods {
	private Integer uuid;//编号
	private String name;//名称
	private String origin;//产地
	private String producer;//厂家
	private String unit;//计量单位
	private Double inprice;//进货价格
	private Double outprice;//销售价格
	private Goodstype goodstype;//商品类型

	@Override
	public String toString() {
		return "Goods{" +
				"uuid=" + uuid +
				", name='" + name + '\'' +
				", origin='" + origin + '\'' +
				", producer='" + producer + '\'' +
				", unit='" + unit + '\'' +
				", inprice=" + inprice +
				", outprice=" + outprice +
				", goodstype=" + goodstype +
				'}';
	}
}
