package com.itmk.web.material_category.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.home.entity.EchartsItem;
import com.itmk.web.material_category.entity.MaterialCategory;

import java.util.List;

/**
 */
public interface MaterialCategoryService extends IService<MaterialCategory> {
      List<EchartsItem> getTotalCategory();
}
