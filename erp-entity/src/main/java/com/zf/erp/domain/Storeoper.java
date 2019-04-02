package com.zf.erp.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 仓库操作记录实体类
 * @author Administrator *
 */
@Getter
@Setter
public class Storeoper {

	/**入库 */
	public static final String TYPE_IN = "1";
	/**出库*/
	public static final String TYPE_OUT = "2";

	private Integer uuid;//编号
	private Integer empuuid;//操作员工编号
	private String empName;
	private java.util.Date opertime;//操作日期
	private Integer storeuuid;//仓库编号
	private String storeName;
	private Integer goodsuuid;//商品编号
	private String goodsName;
	private Integer num;//数量
	private String type;//1：入库 2：出库




}
