package com.itmk.web.home.entity;

import lombok.Data;

/**
 */
@Data
public class UpPasswordParm {
    private Long userId;
    private String password;
    private String oldPassword;
}
