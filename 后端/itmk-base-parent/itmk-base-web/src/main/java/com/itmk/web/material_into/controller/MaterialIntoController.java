package com.itmk.web.material_into.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.material_into.entity.ApplyParm;
import com.itmk.web.material_into.entity.IntoPageParm;
import com.itmk.web.material_into.entity.MaterialInto;
import com.itmk.web.material_into.entity.MaterialIntoParm;
import com.itmk.web.material_into.service.MaterialIntoService;
import com.itmk.web.material_into_detail.entity.MaterialIntoDetail;
import com.itmk.web.material_into_detail.service.MaterialIntoDetailService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 */
@RestController
@RequestMapping("/api/into")
public class MaterialIntoController {
    @Autowired
    private MaterialIntoService materialIntoService;
    @Autowired
    private MaterialIntoDetailService materialIntoDetailService;

    //入库
    @PostMapping("/intoSave")
    public ResultVo intoSave(@RequestBody MaterialIntoParm parm){
        materialIntoService.intoSave(parm);
        return ResultUtils.success("提交成功!");
    }

    //记录列表
    @GetMapping("/getIntoList")
    public ResultVo getIntoList(IntoPageParm parm){
        //构造分页对象
        IPage<MaterialInto> page = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        //构造查询条件
        QueryWrapper<MaterialInto> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getName())){
            query.lambda().like(MaterialInto::getName,parm.getName());
        }
        if(StringUtils.isNotEmpty(parm.getType())){
            query.lambda().eq(MaterialInto::getType,parm.getType());
        }
        if(StringUtils.isNotEmpty(parm.getStatus())){
            query.lambda().eq(MaterialInto::getStatus,parm.getStatus());
        }
        if(StringUtils.isNotEmpty(parm.getProvince())){
            query.lambda().like(MaterialInto::getProvince,parm.getProvince());
        }
        query.lambda().orderByAsc(MaterialInto::getStatus);
        IPage<MaterialInto> list = materialIntoService.page(page, query);
        //查询详细信息
        if(list.getRecords().size() >0){
            List<MaterialInto> records = list.getRecords();
            for(int i=0;i<records.size();i++){
                //根据记录的id查询详细信息
                QueryWrapper<MaterialIntoDetail> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(MaterialIntoDetail::getIntoId,records.get(i).getIntoId());
                List<MaterialIntoDetail> details = materialIntoDetailService.list(queryWrapper);
                records.get(i).setChildren(details);
            }
        }
        return ResultUtils.success("查询成功",list);
    }

    @PostMapping("/apply")
    public ResultVo apply(@RequestBody ApplyParm parm){
        materialIntoService.apply(parm);
        return ResultUtils.success("操作成功!");
    }
}
