package com.itmk.web.material_into.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.material_info.service.MaterialInfoService;
import com.itmk.web.material_into.entity.ApplyParm;
import com.itmk.web.material_into.entity.InfoUpdateParm;
import com.itmk.web.material_into.entity.MaterialInto;
import com.itmk.web.material_into.entity.MaterialIntoParm;
import com.itmk.web.material_into.mapper.MaterialIntoMapper;
import com.itmk.web.material_into.service.MaterialIntoService;
import com.itmk.web.material_into_detail.entity.MaterialIntoDetail;
import com.itmk.web.material_into_detail.service.MaterialIntoDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**

 */
@Service
public class MaterialIntoServiceImpl extends ServiceImpl<MaterialIntoMapper, MaterialInto> implements MaterialIntoService {
    @Autowired
    private MaterialIntoDetailService materialIntoDetailService;
    @Autowired
    private MaterialInfoService materialInfoService;

    @Override
    @Transactional
    public void intoSave(MaterialIntoParm parm) {
        //主表入库
        MaterialInto into = new MaterialInto();
        BeanUtils.copyProperties(parm,into);
        into.setCreateTime(new Date());
        int insert = this.baseMapper.insert(into);
        if(insert > 0){
            //设置主表的id
            parm.getInfos().forEach(item ->{
                item.setIntoId(into.getIntoId());
            });
            //批量保存明细
            materialIntoDetailService.saveBatch(parm.getInfos());
        }
    }

    @Override
    @Transactional
    public void apply(ApplyParm parm) {
        //审核通过
        if(parm.getStatus().equals("1")){
            //更新记录的审核状态
            //更新记录的状态
            MaterialInto into = new MaterialInto();
            into.setIntoId(parm.getIntoId());
            into.setStatus(parm.getStatus());
            int i = this.baseMapper.updateById(into);
            //更新物资的库存
            if(i>0){
                //根据记录的id查询物资库存
                QueryWrapper<MaterialIntoDetail> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(MaterialIntoDetail::getIntoId,parm.getIntoId());
                List<MaterialIntoDetail> list = materialIntoDetailService.list(queryWrapper);
                List<InfoUpdateParm> upList = new ArrayList<>();
                for(int j =0;j<list.size();j++){
                    InfoUpdateParm updateParm = new InfoUpdateParm();
                    updateParm.setStore(list.get(j).getNum());
                    updateParm.setInfoId(list.get(j).getInfoId());
                    upList.add(updateParm);
                }
                //入库处理：批量更新
                materialInfoService.updateStore(upList);
            }
        }else if(parm.getStatus().equals("2")){ //拒绝
            //更新记录的状态
            MaterialInto into = new MaterialInto();
            into.setIntoId(parm.getIntoId());
            into.setStatus(parm.getStatus());
            this.baseMapper.updateById(into);
        }
    }
}
