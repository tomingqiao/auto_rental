package com.coder.rental.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.AutoInfo;
import com.coder.rental.entity.Dept;
import com.coder.rental.entity.Maintain;
import com.coder.rental.service.IAutoInfoService;
import com.coder.rental.service.IMaintainService;
import com.coder.rental.utils.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 叶发通
 * @since 2025-01-19
 */
@RestController
@RequestMapping("/rental/maintain")
public class MaintainController {

    @Resource
    private IMaintainService maintainService;

    @Resource
    private IAutoInfoService autoInfoService;

    @PostMapping("/{start}/{size}")
    @PreAuthorize("hasAuthority('busi:maintain:select')")
    public Result search(@PathVariable int start, @PathVariable int size, @RequestBody Maintain maintain){
        Page<Maintain> page = new Page<>(start, size);
        return Result.success(maintainService.search(page, maintain));
    }
    @PostMapping
    @PreAuthorize("hasAuthority('busi:maintain:add')")
    public Result save(@RequestBody Maintain maintain){
        AutoInfo auto = autoInfoService.getByAutoNum(maintain.getAutoNum());
        maintain.setAutoId(auto.getId());
        auto.setActualNum(auto.getActualNum()+1);
        autoInfoService.updateById(auto);
        return maintainService.save(maintain)?Result.success():Result.fail();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('busi:maintain:edit')")
    public Result update(@RequestBody Maintain maintain){
        AutoInfo auto = autoInfoService.getByAutoNum(maintain.getAutoNum());
        maintain.setAutoId(auto.getId());
        return maintainService.updateById(maintain)?Result.success():Result.fail();
    }

    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('busi:maintain:delete')")
    public Result delete(@PathVariable String ids){
        return maintainService.delete(ids)?Result.success():Result.fail();
    }

    @GetMapping("exportExcel")
    @PreAuthorize("hasAuthority('busi:maintain:export')")
    public Result exportExcel(HttpServletResponse response) throws IOException {
        List<Maintain> list = maintainService.list();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("deleted", "是否删除");
        writer.write(list, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("保养信息表", StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition","attachment;filename=test"+fileName+".xlsx");
        ServletOutputStream out=response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
        return Result.success();
    }

}
