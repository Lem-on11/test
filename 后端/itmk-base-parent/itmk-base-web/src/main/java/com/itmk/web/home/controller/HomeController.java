package com.itmk.web.home.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.home.entity.Echart;
import com.itmk.web.home.entity.EchartTotal;
import com.itmk.web.home.entity.EchartsItem;
import com.itmk.web.home.entity.UpPasswordParm;
import com.itmk.web.material_category.service.MaterialCategoryService;
import com.itmk.web.material_info.service.MaterialInfoService;
import com.itmk.web.material_into.entity.MaterialInto;
import com.itmk.web.material_into.service.MaterialIntoService;
import com.itmk.web.material_send.entity.MaterialSend;
import com.itmk.web.material_send.service.MaterialSendService;
import com.itmk.web.notice.entity.Notice;
import com.itmk.web.notice.service.NoticeService;
import com.itmk.web.sys_user.entity.SysUser;
import com.itmk.web.sys_user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/home")
public class HomeController {
    @Autowired
    private MaterialIntoService materialIntoService;
    @Autowired
    private MaterialSendService materialSendService;
    @Autowired
    private MaterialCategoryService materialCategoryService;
    @Autowired
    private MaterialInfoService materialInfoService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //统计
    @GetMapping("/getTotal")
    public ResultVo getTotal(){
        EchartTotal total = new EchartTotal();
        //入库待审核数量
        QueryWrapper<MaterialInto> query = new QueryWrapper<>();
        query.lambda().eq(MaterialInto::getStatus,"0");
        int count = materialIntoService.count(query);
        total.setIntoTotal(count);
        //发放待审核数量
        QueryWrapper<MaterialSend> sendQueryWrapper = new QueryWrapper<>();
        sendQueryWrapper.lambda().eq(MaterialSend::getStatus,"0");
        int count1 = materialSendService.count(sendQueryWrapper);
        total.setSendTotal(count1);
        //物资种类
        int count2 = materialCategoryService.count();
        total.setCategoryTotal(count2);
        //发放总数:统计审核成功的
        QueryWrapper<MaterialSend> sendQueryWrapperS = new QueryWrapper<>();
        sendQueryWrapperS.lambda().eq(MaterialSend::getStatus,"1");
        int count3 = materialSendService.count(sendQueryWrapperS);
        total.setTotalTotal(count3);
        return ResultUtils.success("查询成功",total);
    }

    //物资分类
    @GetMapping("/getTotalCategory")
    public ResultVo getTotalCategory(){
        List<EchartsItem> category = materialCategoryService.getTotalCategory();
        //组装数据
        Echart echart = new Echart();
        List<String> names = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        if(category.size()>0){
            for (int i=0;i<category.size();i++){
                names.add(category.get(i).getName());
                values.add(category.get(i).getValue());
            }
        }
        echart.setNames(names);
        echart.setValues(values);
        return ResultUtils.success("查询成功",echart);
    }

    //物资充足
    @GetMapping("/moreInfo")
    public ResultVo moreInfo(){
        List<EchartsItem> itemList = materialInfoService.moreInfor();
        return ResultUtils.success("查询成功",itemList);
    }


    //库存不足
    @GetMapping("/lessInfor")
    public ResultVo lessInfor(){
        List<EchartsItem> itemList = materialInfoService.lessInfor();
        return ResultUtils.success("查询成功",itemList);
    }

    //公告
    @GetMapping("/getNoticeList")
    public ResultVo getNoticeList(){
        QueryWrapper<Notice> query = new QueryWrapper<>();
        query.lambda().orderByDesc(Notice::getCreateTime).last(" limit 3");
        List<Notice> list = noticeService.list(query);
        return ResultUtils.success("查询成功",list);
    }

    //修改密码
    @PostMapping("/upPassword")
    public ResultVo upPassword(@RequestBody UpPasswordParm parm){
        //判断原密码是否正确
        SysUser user = sysUserService.getById(parm.getUserId());
        if(!passwordEncoder.matches(parm.getOldPassword(),user.getPassword())){
            return ResultUtils.error("原密码错误!");
        }
        SysUser sysUser = new SysUser();
        sysUser.setUserId(parm.getUserId());
        sysUser.setPassword(passwordEncoder.encode(parm.getPassword()));
        if(sysUserService.updateById(sysUser)){
            return ResultUtils.success("修改成功!");
        }
        return ResultUtils.error("修改失败!");
    }
}
