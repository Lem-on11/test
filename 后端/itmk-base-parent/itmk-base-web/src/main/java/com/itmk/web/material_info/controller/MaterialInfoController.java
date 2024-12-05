package com.itmk.web.material_info.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.HttpImgUtils;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.material_info.entity.ExportMaterial;
import com.itmk.web.material_info.entity.InfoParm;
import com.itmk.web.material_info.entity.MaterialInfo;
import com.itmk.web.material_info.service.MaterialInfoService;
import com.itmk.web.sys_user.entity.ExportUser;
import com.itmk.web.sys_user.entity.SysUser;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 */
@RestController
@RequestMapping("/api/materialInfo")
public class MaterialInfoController {
    @Autowired
    private MaterialInfoService materialInfoService;

    //新增
    @PostMapping
    public ResultVo add(@RequestBody MaterialInfo materialInfo) {
        if (materialInfoService.save(materialInfo)) {
            return ResultUtils.success("新增成功!");
        }
        return ResultUtils.error("新增失败!");
    }

    //编辑
    @PutMapping
    public ResultVo edit(@RequestBody MaterialInfo materialInfo) {
        if (materialInfoService.updateById(materialInfo)) {
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }

    //删除
    @DeleteMapping("/{infoId}")
    public ResultVo delete(@PathVariable("infoId") Long infoId) {
        if (materialInfoService.removeById(infoId)) {
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }

    //列表
    @GetMapping("/list")
    public ResultVo list(InfoParm parm) {
        IPage<MaterialInfo> list = materialInfoService.getList(parm);
        return ResultUtils.success("查询成功", list);
    }

    //列表
    @GetMapping("/selectList")
    public ResultVo selectList(InfoParm parm) {
        //构造分页对象
        IPage<MaterialInfo> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        //构造查询条件
        QueryWrapper<MaterialInfo> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(parm.getInfoName())) {
            query.lambda().like(MaterialInfo::getInfoName, parm.getInfoName());
        }
        if (parm.getCategoryId() != null) {
            query.lambda().eq(MaterialInfo::getCategoryId, parm.getCategoryId());
        }
        IPage<MaterialInfo> list = materialInfoService.page(page, query);

        return ResultUtils.success("查询成功", list);
    }

    //导出物资
    @RequestMapping("/exporMaterial")
    public void exporMaterial(HttpServletResponse response) throws Exception {
        //获取用户列表
        List<MaterialInfo> list = materialInfoService.list();
        //组装excel需要的数据格式
        List<ExportMaterial> exportList = new ArrayList<>();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                ExportMaterial vo = new ExportMaterial();
                BeanUtils.copyProperties(list.get(i), vo);
                //设置图片
                if (StringUtils.isNotEmpty(list.get(i).getImage())) {
                    vo.setImgFile(HttpImgUtils.getNetImgByUrl(list.get(i).getImage()));
                }
                exportList.add(vo);
            }
        }
        //导出
        String fileName = "物资信息.xlsx";
        ExportParams exportParams = new ExportParams();
        exportParams.setType(ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, ExportMaterial.class, exportList);
        downloadExcel(fileName, workbook, response);
    }

    public static void downloadExcel(String fileName, Workbook workbook, HttpServletResponse response) throws Exception {
        try {
            if (StringUtils.isEmpty(fileName)) {
                throw new RuntimeException("导出文件名不能为空");
            }
            String encodeFileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + encodeFileName);
            response.setHeader("FileName", encodeFileName);
            response.setHeader("Access-Control-Expose-Headers", "FileName");
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (Exception e) {
            workbook.close();
        }
    }
}
