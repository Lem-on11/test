package com.itmk.web.material_category.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmk.web.home.entity.EchartsItem;
import com.itmk.web.material_category.entity.MaterialCategory;

import java.util.List;

/**
 */
public interface MaterialCategoryMapper extends BaseMapper<MaterialCategory> {
    List<EchartsItem> getTotalCategory();
}
