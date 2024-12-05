package com.itmk.web.material_send.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.material_into.entity.ApplyParm;
import com.itmk.web.material_send.entity.MaterialSend;
import com.itmk.web.material_send.entity.MaterialSendParm;

/**

 */
public interface MaterialSendService extends IService<MaterialSend> {
    void sendSave(MaterialSendParm parm);
     //审核
    void apply(ApplyParm parm);
}
