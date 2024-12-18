package com.itmk.web.material_category.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.material_category.entity.MaterialCategory;
import com.itmk.web.material_category.entity.PageParm;
import com.itmk.web.material_category.service.MaterialCategoryService;
import com.itmk.web.sys_role.entity.SelectType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 */
@RestController
@RequestMapping("/api/category")
public class MaterialCategoryController {
    @Autowired
    private MaterialCategoryService materialCategoryService;

    //新增
    @PostMapping
    public ResultVo add(@RequestBody MaterialCategory category){
        if(materialCategoryService.save(category)){
            return ResultUtils.success("新增成功!");
        }
        return ResultUtils.error("新增失败!");
    }

    //编辑
    @PutMapping
    public ResultVo edit(@RequestBody MaterialCategory category){
        if(materialCategoryService.updateById(category)){
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }

    //删除
    @DeleteMapping("/{categoryId}")
    public ResultVo delete(@PathVariable("categoryId") Long categoryId){
        if(materialCategoryService.removeById(categoryId)){
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error("删除失败!");
    }

    //列表
    @GetMapping("/list")
    public ResultVo list(PageParm parm){
        //构造分页对象
        IPage<MaterialCategory> page = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        //构造查询条件
        QueryWrapper<MaterialCategory> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getCategoryName())){
            query.lambda().like(MaterialCategory::getCategoryName,parm.getCategoryName());
        }
        query.lambda().orderByDesc(MaterialCategory::getOrderNum);
        IPage<MaterialCategory> list = materialCategoryService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }

    //物资分类
    @GetMapping("/getSelect")
    public ResultVo getSelect(){
        List<MaterialCategory> list = materialCategoryService.list();
        List<SelectType> roleList = new ArrayList<>();
        Optional.ofNullable(list).orElse(new ArrayList<>())
                .stream()
                .forEach(item -> {
                    SelectType type = new SelectType();
                    type.setValue(item.getCategoryId());
                    type.setLabel(item.getCategoryName());
                    roleList.add(type);
                });
        return ResultUtils.success("查询成功", roleList);
    }
}
