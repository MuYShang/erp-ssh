package com.zf.erp.Biz;

import com.zf.erp.domain.Supplier;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 供应商/客户业务层接口
 */
public interface ISupplierBiz extends IBaseBiz<Supplier>{

    //导出Excel功能
    public void export(OutputStream os,Supplier supplier);

    //导入Excel功能
    public void doImport(InputStream is) throws Exception;
}
