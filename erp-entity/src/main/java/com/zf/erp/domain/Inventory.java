package com.zf.erp.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 盘盈盘亏实体类
 * @author Administrator *
 */
@Setter
@Getter
public class Inventory {

	/** 未审核 */
	public static final String STATE_CREATE = "0";
	/** 已审核 */
	public static final String STATE_END = "1";

	private Integer uuid;//编号
	private Integer goodsuuid;//商品
	private String goodsName;
	private Integer storeuuid;//仓库
	private String storeName;
	private Integer num;//数量
	private String type;//类型
	private java.util.Date createtime;//登记日期
	private java.util.Date checktime;//审核日期
	private Integer creater;//登记人
	private String createrName;
	private Integer checker;//审核人
	private String checkerName;
	private String state;//状态
	private String remark;//备注


}
