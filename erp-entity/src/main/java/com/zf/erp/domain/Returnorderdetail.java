package com.zf.erp.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 退货订单明细实体类
 * @author Administrator *
 */
@Getter
@Setter
public class Returnorderdetail {



	/** 未出库 */
	public static final String STATE_NOT_OUT = "0";
	/** 已出库 */
	public static final String STATE_OUT = "1";

	private Integer uuid;//编号
	private Integer goodsuuid;//商品编号
	private String goodsname;//商品名称
	private Double price;//价格
	private Integer num;//数量
	private Double money;//金额
	private java.util.Date endtime;//结束日期
	private Integer ender;//库管员
	private Integer storeuuid;//仓库编号
	private String state;//状态
	private Integer ordersuuid;//退货订单编号

	private transient Returnorders returnorders;

	@Override
	public String toString() {
		return "Returnorderdetail{" +
				"uuid=" + uuid +
				", goodsuuid=" + goodsuuid +
				", goodsname='" + goodsname + '\'' +
				", price=" + price +
				", num=" + num +
				", money=" + money +
				", endtime=" + endtime +
				", ender=" + ender +
				", storeuuid=" + storeuuid +
				", state='" + state + '\'' +
				", ordersuuid=" + ordersuuid +
				'}';
	}
}
