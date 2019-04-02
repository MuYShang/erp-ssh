package com.zf.erp.Biz.Impl;

import com.zf.erp.Biz.IDepBiz;
import com.zf.erp.dao.IDepDao;
import com.zf.erp.domain.Dep;
import lombok.Setter;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 部门Biz接口实现类
 */
public class DepBiz extends BaseBiz<Dep> implements IDepBiz {

    private IDepDao depDao;

    public void setDepDao(IDepDao depDao) {
        this.depDao = depDao;
        super.setiBaseDao(depDao);
    }

}
