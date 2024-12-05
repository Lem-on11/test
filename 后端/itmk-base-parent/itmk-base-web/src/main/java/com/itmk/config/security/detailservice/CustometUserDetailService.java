package com.itmk.config.security.detailservice;

import com.itmk.web.sys_menu.entity.SysMenu;
import com.itmk.web.sys_menu.service.SysMenuService;
import com.itmk.web.sys_user.entity.SysUser;
import com.itmk.web.sys_user.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component("custometUserDetailService")
public class CustometUserDetailService implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //认证
        SysUser user = sysUserService.loadUser(s);
        if(user == null){
            throw  new UsernameNotFoundException("用户名或密码错误!");
        }
        //授权
        List<SysMenu> menuList = null;
        //超级管理员
        if(StringUtils.isNotEmpty(user.getIsAdmin()) && user.getIsAdmin().equals("1")){
            menuList = sysMenuService.list();
        }else{
            menuList = sysMenuService.getMenuByUserId(user.getUserId());
        }
        List<String> collect = menuList.stream().map(item -> item.getCode()).filter(item -> item != null && StringUtils.isNotEmpty(item)).collect(Collectors.toList());
        String[] strings = collect.toArray(new String[collect.size()]);
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(strings);
        user.setAuthorities(authorityList);
        return user;
    }
}
