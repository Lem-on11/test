package com.itmk.web.sys_role.entity;

import lombok.Data;

/**

 */
@Data
public class RoleParm {
    //当前第几页
    private Long currentPage;
    //每页查询的条数
    private Long pageSize;
    //查询关键字
    private String roleName;
}