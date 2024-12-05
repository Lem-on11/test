package com.itmk.web.material_send.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.material_category.entity.PageParm;
import com.itmk.web.material_into.entity.ApplyParm;
import com.itmk.web.material_into.entity.IntoPageParm;
import com.itmk.web.material_into.entity.MaterialInto;
import com.itmk.web.material_into_detail.entity.MaterialIntoDetail;
import com.itmk.web.material_send.entity.MaterialSend;
import com.itmk.web.material_send.entity.MaterialSendParm;
import com.itmk.web.material_send.entity.SendPageParm;
import com.itmk.web.material_send.service.MaterialSendService;
import com.itmk.web.material_send_detail.entity.MaterialSendDetail;
import com.itmk.web.material_send_detail.service.MaterialSendDetailService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 */
@RestController
@RequestMapping("/api/send")
public class MaterialSendController {
    @Autowired
    private MaterialSendService materialSendService;
    @Autowired
    private MaterialSendDetailService materialSendDetailService;

    //发放物资保存
    @PostMapping("/sendSave")
    public ResultVo sendSave(@RequestBody MaterialSendParm parm){
        materialSendService.sendSave(parm);
        return ResultUtils.success("操作成功!");
    }

    //物资发放记录列表
    @GetMapping("/getSendList")
    public ResultVo getSendList(SendPageParm parm){
        //构造分页对象
        IPage<MaterialSend> page = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        //构造查询条件
        QueryWrapper<MaterialSend> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getName())){
            query.lambda().like(MaterialSend::getName,parm.getName());
        }
        if(StringUtils.isNotEmpty(parm.getType())){
            query.lambda().eq(MaterialSend::getType,parm.getType());
        }
        if(StringUtils.isNotEmpty(parm.getStatus())){
            query.lambda().eq(MaterialSend::getStatus,parm.getStatus());
        }
        if(StringUtils.isNotEmpty(parm.getProvince())){
            query.lambda().like(MaterialSend::getProvince,parm.getProvince());
        }
        query.lambda().orderByAsc(MaterialSend::getStatus);
        IPage<MaterialSend> list = materialSendService.page(page, query);
        //查询详细信息
        if(list.getRecords().size() >0){
            List<MaterialSend> records = list.getRecords();
            for(int i=0;i<records.size();i++){
                //根据记录的id查询详细信息
                QueryWrapper<MaterialSendDetail> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(MaterialSendDetail::getSendId,records.get(i).getSendId());
                List<MaterialSendDetail> details = materialSendDetailService.list(queryWrapper);
                records.get(i).setChildren(details);
            }
        }
        return ResultUtils.success("查询成功",list);
    }

    @PostMapping("/apply")
    public ResultVo apply(@RequestBody ApplyParm parm){
        materialSendService.apply(parm);
        return ResultUtils.success("操作成功!");
    }
}
