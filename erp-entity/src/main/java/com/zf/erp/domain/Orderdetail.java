package com.zf.erp.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 订单明细实体类
 * @author Administrator *
 */
@Getter
@Setter
public class Orderdetail {

	/**未入库 */
	public static final String STATE_NOT_IN = "0";
	/**已入库*/
	public static final String STATE_IN = "1";

	/**未出库 */
	public static final String STATE_NOT_OUT = "0";
	/**已出库*/
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
	private String state;//采购：0=未入库，1=已入库  销售：0=未出库，1=已出库
	private Integer ordersuuid;//订单编号

	private transient Orders orders;

}
