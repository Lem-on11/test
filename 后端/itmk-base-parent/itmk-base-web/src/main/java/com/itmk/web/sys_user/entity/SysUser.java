package com.itmk.web.sys_user.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/**

 */
@Data
@TableName("sys_user")
public class SysUser implements UserDetails {
    @TableId(type = IdType.AUTO)
    private Long userId;
    //排除roleId，该字段不属于sys_user表
    @TableField(exist = false)
    private Long roleId;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String sex;
    private String isAdmin;
     //帐户是否过期(1 未过期，0已过期)
    private boolean isAccountNonExpired = true;
    //帐户是否被锁定(1 未锁定，0已锁定)
    private boolean isAccountNonLocked = true;
    //密码是否过期(1 未过期，0已过期)
    private boolean isCredentialsNonExpired = true;
    //帐户是否可用(1 可用，0 删除用户)
    private boolean isEnabled = true;
    private String nickName;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

     //用户权限字段的集合
    @TableField(exist = false)
     Collection<? extends GrantedAuthority> authorities;
}