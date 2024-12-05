package com.itmk.web.sys_role.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.sys_role.entity.*;
import com.itmk.web.sys_role.service.SysRoleService;
import com.itmk.web.sys_role_menu.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**

 */
@RestController
@RequestMapping("/api/role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private RoleMenuService roleMenuService;

    //新增
    @PostMapping
    public ResultVo add(@RequestBody SysRole sysRole) {
        sysRole.setCreateTime(new Date());
        if (sysRoleService.save(sysRole)) {
            return ResultUtils.success("新增角色成功!");
        }
        return ResultUtils.error("新增角色失败!");
    }

    //编辑
    @PutMapping
    public ResultVo edit(@RequestBody SysRole sysRole) {
        sysRole.setUpdateTime(new Date());
        if (sysRoleService.updateById(sysRole)) {
            return ResultUtils.success("编辑角色成功!");
        }
        return ResultUtils.error("编辑角色失败!");
    }

    //删除
    @DeleteMapping("/{roleId}")
    public ResultVo delete(@PathVariable("roleId") Long roleId) {
        if (sysRoleService.removeById(roleId)) {
            return ResultUtils.success("删除角色成功!");
        }
        return ResultUtils.error("删除角色失败!");
    }

    //查询
    @GetMapping("/list")
    public ResultVo list(RoleParm parm) {
        IPage<SysRole> list = sysRoleService.getList(parm);
        return ResultUtils.success("查询成功", list);
    }

    //获取角色下拉数据
    @GetMapping("/getListSelect")
    public ResultVo getListSelect() {
        List<SysRole> list = sysRoleService.list();
        List<SelectType> roleList = new ArrayList<>();
        Optional.ofNullable(list).orElse(new ArrayList<>())
                .stream()
                .forEach(item -> {
                    SelectType type = new SelectType();
                    type.setValue(item.getRoleId());
                    type.setLabel(item.getRoleName());
                    roleList.add(type);
                });
        return ResultUtils.success("查询成功", roleList);
    }

    //查询权限树数据和回显
    @GetMapping("/getAssignTree")
    public ResultVo getAssignTree(RoleAssignParm parm){
        RolePermissionVo assignTree = sysRoleService.getAssignTree(parm);
        return ResultUtils.success("查询成功",assignTree);

    }

    //保存权限
    @PostMapping("/assignSave")
    public ResultVo assignSave(@RequestBody SaveMenuParm parm){
        roleMenuService.saveMenu(parm);
        return ResultUtils.success("保存成功!");
    }

}
