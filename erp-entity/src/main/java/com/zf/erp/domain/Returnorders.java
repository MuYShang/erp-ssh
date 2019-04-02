package com.zf.erp.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 退货订单实体类
 * @author Administrator *
 */
@Getter
@Setter
public class Returnorders {

	/** 未审核 */
	public static final String STATE_CREATE = "0";
	/** 已审核 */
	public static final String STATE_CHECK = "1";
	/** 已入库 */
	public static final String STATE_END = "2";

	/** 商家退货订单 */
	public static final String TYPE_SUPPLIER = "1";
	/** 客户退货订单 */
	public static final String TYPE_CUSTOMER = "2";

	private Integer uuid;//编号
	private java.util.Date createtime;//生成日期
	private java.util.Date checktime;//检查日期
	private java.util.Date endtime;//结束日期
	private String type;//订单类型
	private Integer creater;//下单员
	private String createrName;//下单员姓名
	private Integer checker;//审核员工编号
	private String checkerName;//审核员姓名
	private Integer ender;//库管员
	private String enderName;//库管员姓名
	private Integer supplieruuid;//供应商及客户编号
	private String supplierName;
	private Double totalmoney;//合计金额
	private String state;//订单状态
	private Integer waybillsn;//运单号
	private Integer ordersuuid;//原订单编号

	private List<Returnorderdetail> returnorderdetails;

	@Override
	public String toString() {
		return "Returnorders{" +
				"uuid=" + uuid +
				", createtime=" + createtime +
				", checktime=" + checktime +
				", endtime=" + endtime +
				", type='" + type + '\'' +
				", creater=" + creater +
				", createrName='" + createrName + '\'' +
				", checker=" + checker +
				", checkerName='" + checkerName + '\'' +
				", ender=" + ender +
				", enderName='" + enderName + '\'' +
				", supplieruuid=" + supplieruuid +
				", supplierName='" + supplierName + '\'' +
				", totalmoney=" + totalmoney +
				", state='" + state + '\'' +
				", waybillsn=" + waybillsn +
				", ordersuuid=" + ordersuuid +
				", returnorderdetails=" + returnorderdetails +
				'}';
	}
}
