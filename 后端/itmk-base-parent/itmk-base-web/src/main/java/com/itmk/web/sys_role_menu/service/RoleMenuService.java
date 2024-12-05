package com.itmk.web.sys_role_menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.sys_role.entity.SaveMenuParm;
import com.itmk.web.sys_role_menu.entity.RoleMenu;

/**

 */
public interface RoleMenuService extends IService<RoleMenu> {
    //保存角色权限
    void saveMenu(SaveMenuParm parm);
}
