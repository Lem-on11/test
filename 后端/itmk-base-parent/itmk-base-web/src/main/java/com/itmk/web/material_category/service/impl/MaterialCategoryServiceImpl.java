package com.itmk.web.material_category.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.home.entity.EchartsItem;
import com.itmk.web.material_category.entity.MaterialCategory;
import com.itmk.web.material_category.mapper.MaterialCategoryMapper;
import com.itmk.web.material_category.service.MaterialCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 */
@Service
public class MaterialCategoryServiceImpl extends ServiceImpl<MaterialCategoryMapper, MaterialCategory> implements MaterialCategoryService {
    @Override
    public List<EchartsItem> getTotalCategory() {
        return this.baseMapper.getTotalCategory();
    }
}
