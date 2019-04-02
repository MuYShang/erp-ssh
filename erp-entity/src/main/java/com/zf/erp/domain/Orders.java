package com.zf.erp.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 订单实体类
 * @author Administrator *
 */
@Getter
@Setter
public class Orders {

	/** 未审核 */
	public static final String STATE_CREATE = "0";
	/** 已审核 */
	public static final String STATE_CHECK = "1";
	/** 已确认 */
	public static final String STATE_START = "2";
	/** 已入库 */
	public static final String STATE_END = "3";

	/** 未出库 */
	public static final String STATE_NOT_OUT = "0";
	/** 已出库 */
	public static final String STATE_OUT = "1";

	/** 采购订单 */
	public static final String TYPE_IN = "1";
	/** 销售订单 */
	public static final String TYPE_OUT = "2";


	private Integer uuid;//编号
	private java.util.Date createtime;//生成日期
	private java.util.Date checktime;//审核日期
	private java.util.Date starttime;//确认日期
	private java.util.Date endtime;//入库或出库日期
	private String type;//1:采购 2:销售
	private Integer creater;//下单员
	private String createrName;//下单员名称
	private Integer checker;//审核员
	private String checkerName;//审核员名称
	private Integer starter;//采购员
	private String starterName;//采购员名称
	private Integer ender;//库管员
	private String enderName;//库管员名称
	private Integer supplieruuid;//供应商或客户
	private String supplierName;//供应商或客户名称
	private Double totalmoney;//合计金额
	private String state;//采购: 0:未审核 1:已审核, 2:已确认, 3:已入库；销售：0:未出库 1:已出库
	private Integer waybillsn;//运单号
	private Integer ordersuuid;

	private List<Orderdetail> orderdetails;

	@Override
	public String toString() {
		return "Orders{" +
				"uuid=" + uuid +
				", createtime=" + createtime +
				", checktime=" + checktime +
				", starttime=" + starttime +
				", endtime=" + endtime +
				", type='" + type + '\'' +
				", creater=" + creater +
				", createrName='" + createrName + '\'' +
				", checker=" + checker +
				", checkerName='" + checkerName + '\'' +
				", starter=" + starter +
				", starterName='" + starterName + '\'' +
				", ender=" + ender +
				", enderName='" + enderName + '\'' +
				", supplieruuid=" + supplieruuid +
				", supplierName='" + supplierName + '\'' +
				", totalmoney=" + totalmoney +
				", state='" + state + '\'' +
				", waybillsn=" + waybillsn +
				", ordersuuid=" + ordersuuid +
				", orderdetails=" + orderdetails +
				'}';
	}
}
