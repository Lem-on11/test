package com.itmk.web.notice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.notice.entity.Notice;
import com.itmk.web.notice.entity.PageNotice;
import com.itmk.web.notice.service.NoticeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**

 */
@RestController
@RequestMapping("/api/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    //新增
    @PostMapping
    @PreAuthorize("hasAuthority('sys:notice:add')")
    public ResultVo add(@RequestBody Notice notice){
        notice.setCreateTime(new Date());
        if(noticeService.save(notice)){
            return ResultUtils.success("新增成功!");
        }
        return ResultUtils.error("新增失败!");
    }

    //编辑
    @PreAuthorize("hasAuthority('sys:notice:edit')")
    @PutMapping
    public ResultVo edit(@RequestBody Notice notice){
        if(noticeService.updateById(notice)){
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }

    //删除
    @PreAuthorize("hasAuthority('sys:notice:delete')")
    @DeleteMapping("/{noticeId}")
    public ResultVo delete(@PathVariable("noticeId") Long noticeId){
        if(noticeService.removeById(noticeId)){
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }

    //列表
    @GetMapping("/list")
    public ResultVo list(PageNotice parm){
        //构造分页对象
        IPage<Notice> page = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        //构造查询条件
        QueryWrapper<Notice> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getTitle())){
            query.lambda().like(Notice::getTitle,parm.getTitle());
        }
        query.lambda().orderByDesc(Notice::getCreateTime);
        IPage<Notice> list = noticeService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }
}
