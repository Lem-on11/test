package com.itmk.web.material_into.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.material_into.entity.ApplyParm;
import com.itmk.web.material_into.entity.MaterialInto;
import com.itmk.web.material_into.entity.MaterialIntoParm;

/**

 */
public interface MaterialIntoService extends IService<MaterialInto> {
    void intoSave(MaterialIntoParm parm);
    //审核
    void apply(ApplyParm parm);
}
