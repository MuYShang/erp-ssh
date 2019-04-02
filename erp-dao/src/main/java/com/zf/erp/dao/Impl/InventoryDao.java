package com.zf.erp.dao.Impl;
import com.zf.erp.dao.IInventoryDao;
import com.zf.erp.domain.Inventory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import java.util.Calendar;

/**
 * 盘盈盘亏数据访问类
 * @author Administrator
 *
 */
public class InventoryDao extends BaseDao<Inventory> implements IInventoryDao {

	/**
	 * 构建查询条件
	 * @param param
	 * @return
	 */
	public DetachedCriteria deta(Inventory inventory1,Inventory inventory2,Object param){
		DetachedCriteria dc=DetachedCriteria.forClass(Inventory.class);
		if(inventory1!=null){
			if(null != inventory1.getType() && inventory1.getType().trim().length()>0){
				dc.add(Restrictions.like("type", inventory1.getType(), MatchMode.ANYWHERE));
			}
			if(null != inventory1.getState() && inventory1.getState().trim().length()>0){
				dc.add(Restrictions.like("state", inventory1.getState(), MatchMode.ANYWHERE));
			}
			if(null != inventory1.getRemark() && inventory1.getRemark().trim().length()>0){
				dc.add(Restrictions.like("remark", inventory1.getRemark(), MatchMode.ANYWHERE));
			}
			//登记时间 开始
			if(null != inventory1.getCreatetime()){
				Calendar car = Calendar.getInstance();
				car.setTime(inventory1.getCreatetime());
				car.set(Calendar.HOUR, 0);
				car.set(Calendar.MINUTE, 0);
				car.set(Calendar.SECOND, 0);
				car.set(Calendar.MILLISECOND,0);
				//2017-02-01 15:30:05 => 2017-02-01 00:00:00
				dc.add(Restrictions.ge("createtime", car.getTime()));
			}
			//审核时间 开始
			if(null != inventory1.getChecktime()){
				Calendar car = Calendar.getInstance();
				car.setTime(inventory1.getChecktime());
				car.set(Calendar.HOUR, 0);
				car.set(Calendar.MINUTE, 0);
				car.set(Calendar.SECOND, 0);
				car.set(Calendar.MILLISECOND,0);
				//2017-02-01 15:30:05 => 2017-02-01 00:00:00
				dc.add(Restrictions.ge("checktime", car.getTime()));
			}
		}
		if(inventory2!=null){
			//登记时间 结束
			if(null != inventory2.getCreatetime()){
				Calendar car = Calendar.getInstance();
				car.setTime(inventory2.getCreatetime());
				car.set(Calendar.HOUR, 23);
				car.set(Calendar.MINUTE, 59);
				car.set(Calendar.SECOND, 59);
				car.set(Calendar.MILLISECOND,999);
				//2017-02-01 15:30:05 => 2017-02-01 23:59:59.999
				dc.add(Restrictions.le("createtime", car.getTime()));
			}
			//审核时间 结束
			if(null != inventory2.getChecktime()){
				Calendar car = Calendar.getInstance();
				car.setTime(inventory2.getChecktime());
				car.set(Calendar.HOUR, 0);
				car.set(Calendar.MINUTE, 0);
				car.set(Calendar.SECOND, 0);
				car.set(Calendar.MILLISECOND,0);
				//2017-02-01 15:30:05 => 2017-02-01 00:00:00
				dc.add(Restrictions.le("checktime", car.getTime()));
			}
		}
		return dc;
	}

}
