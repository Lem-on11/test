package com.itmk.web.material_info.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itmk.web.home.entity.EchartsItem;
import com.itmk.web.material_info.entity.InfoParm;
import com.itmk.web.material_info.entity.MaterialInfo;
import com.itmk.web.material_into.entity.InfoUpdateParm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 */
public interface MaterialInfoMapper extends BaseMapper<MaterialInfo> {
    IPage<MaterialInfo> getList(IPage<MaterialInfo> page,@Param("parm") InfoParm parm);
    void updateStore(@Param("list") List<InfoUpdateParm> list);
    void sendUpdateStore(@Param("list") List<InfoUpdateParm> list);
    List<EchartsItem> moreInfor();
    List<EchartsItem> lessInfor();
}
