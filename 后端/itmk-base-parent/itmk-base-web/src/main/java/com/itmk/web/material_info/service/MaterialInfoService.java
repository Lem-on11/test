package com.itmk.web.material_info.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.home.entity.EchartsItem;
import com.itmk.web.material_info.entity.InfoParm;
import com.itmk.web.material_info.entity.MaterialInfo;
import com.itmk.web.material_into.entity.InfoUpdateParm;

import java.util.List;

/**
 */
public interface MaterialInfoService extends IService<MaterialInfo> {
    IPage<MaterialInfo> getList(InfoParm parm);
    void updateStore(List<InfoUpdateParm> parm);
    void sendUpdateStore(List<InfoUpdateParm> parm);
    List<EchartsItem> moreInfor();
    List<EchartsItem> lessInfor();
}
