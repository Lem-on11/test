package com.itmk.web.material_info.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.home.entity.EchartsItem;
import com.itmk.web.material_info.entity.InfoParm;
import com.itmk.web.material_info.entity.MaterialInfo;
import com.itmk.web.material_info.mapper.MaterialInfoMapper;
import com.itmk.web.material_info.service.MaterialInfoService;
import com.itmk.web.material_into.entity.InfoUpdateParm;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 */
@Service
public class MaterialInfoServiceImpl extends ServiceImpl<MaterialInfoMapper, MaterialInfo> implements MaterialInfoService {
    @Override
    public IPage<MaterialInfo> getList(InfoParm parm) {
        //构造分页对象
        IPage<MaterialInfo> pages = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        return this.baseMapper.getList(pages,parm);
    }

    @Override
    public void updateStore(List<InfoUpdateParm> parm) {
        this.baseMapper.updateStore(parm);
    }

    @Override
    public void sendUpdateStore(List<InfoUpdateParm> parm) {
        this.baseMapper.sendUpdateStore(parm);
    }

    @Override
    public List<EchartsItem> moreInfor() {
        return this.baseMapper.moreInfor();
    }

    @Override
    public List<EchartsItem> lessInfor() {
        return this.baseMapper.lessInfor();
    }
}
