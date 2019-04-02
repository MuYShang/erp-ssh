package com.zf.erp.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 仓库库存实体类
 * @author Administrator *
 */
@Getter
@Setter
public class Storedetail {	
	private Integer uuid;//编号
	private Integer storeuuid;//仓库编号
	private Integer goodsuuid;//商品编号
	private String goodsName;
	private String storeName;
	private Integer num;//数量



}
