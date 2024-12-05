package com.itmk.web.material_send.entity;

import com.itmk.web.material_into_detail.entity.MaterialIntoDetail;
import com.itmk.web.material_send_detail.entity.MaterialSendDetail;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**

 */
@Data
public class MaterialSendParm {
    private String province;
    private String detailSource;
    private String name;
    private String phone;
    private String email;
    private String type;
    private String doType;
    private List<MaterialSendDetail> infos = new ArrayList<>();
}
