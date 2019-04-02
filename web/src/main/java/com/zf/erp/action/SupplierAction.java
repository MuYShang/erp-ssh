package com.zf.erp.action;

import com.zf.erp.Biz.ISupplierBiz;
import com.zf.erp.domain.Supplier;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 供应商web层
 */
public class SupplierAction extends BaseAction<Supplier>{

    private ISupplierBiz iSupplierBiz;

    @Resource(name = "SupplierBiz")
    public void setSupplierBiz(ISupplierBiz iSupplierBiz) {
        this.iSupplierBiz = iSupplierBiz;
        super.setiBaseBiz(iSupplierBiz);
    }

    private Supplier supplier = new Supplier();


    @Setter
    @Getter
    private String q;//easyUI加载数据默认参数

    public void export(){
        String filename = null;
        if("1".equals(supplier.getType())) {
            filename = "供应商.xls";
        }else if("2".equals(supplier.getType())){
            filename = "客户.xls";
        }else{
            filename = "全部.xls";
        }

        //响应对象
        HttpServletResponse response = ServletActionContext.getResponse();
        try {
            //设置输出流,实现下载文件
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    new String(filename.getBytes(),"ISO-8859-1"));
            iSupplierBiz.export(response.getOutputStream(), supplier);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Getter
    @Setter
    private File file;//上传文件
    @Getter
    @Setter
    private String fileFileName;//文件名称
    @Getter
    @Setter
    private String fileContentType;//文件类型


    //Excel导入
    public void doImport(){

        if(!"application/vnd.ms-excel".equals(fileContentType)){
            ajaxReturn(false,"请上传正确的文件类型");
        }

        try {
            iSupplierBiz.doImport(new FileInputStream(file));
            ajaxReturn(true,"导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            ajaxReturn(false,"导入失败");
        }
    }

    @Override
    public void list() {
        supplier.setName(q);
        super.setT(supplier);
        super.list();
    }

    /**
     * 获取某个部门信息
     */
    public void get() throws Exception {

        supplier = iSupplierBiz.get(supplier.getUuid());

        super.setT(supplier);

        super.get();

    }



    /**
     * 删除部门
     */
    public void del(){
        super.setUuid(supplier.getUuid());
        super.del();
    }



    @Override
    public Supplier getModel() {
        super.setT(supplier);
        return supplier;
    }
}
