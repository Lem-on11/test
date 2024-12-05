package com.itmk.web.login.entity;

import lombok.Data;

/**
 */
@Data
public class UserInfo {
    private Long userId;
    private String name;
    //用户的权限字段
    private Object[] permissons;
}