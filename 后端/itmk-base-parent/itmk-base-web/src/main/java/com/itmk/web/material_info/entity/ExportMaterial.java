package com.itmk.web.material_info.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 */
@Data
public class ExportMaterial {
    @Excel(name = "图片", type = 2, orderNum = "1", width = 15,height = 30,imageType = 2)
    private byte[] imgFile;
    @Excel(name = "物资名称",orderNum = "2", width=30)
    private String infoName;
    @Excel(name = "规格", orderNum = "3", width=50)
    private String specs;
    @Excel(name = "单位", orderNum = "4", width=30)
    private String unit;
    @Excel(name = "库存", orderNum = "4", width=30)
    private Integer store;
}
