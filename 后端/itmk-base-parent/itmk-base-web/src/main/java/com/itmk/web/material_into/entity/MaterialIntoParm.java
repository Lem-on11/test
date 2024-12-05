package com.itmk.web.material_into.entity;

import com.itmk.web.material_into_detail.entity.MaterialIntoDetail;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**

 */
@Data
public class MaterialIntoParm {
    private String province;
    private String detailSource;
    private String name;
    private String phone;
    private String email;
    private String type;
    private List<MaterialIntoDetail> infos = new ArrayList<>();
}
