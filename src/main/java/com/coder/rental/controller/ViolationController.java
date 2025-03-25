package com.coder.rental.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.coder.rental.entity.Dept;
import com.coder.rental.entity.Violation;
import com.coder.rental.service.IAutoInfoService;
import com.coder.rental.service.IViolationService;
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
@RequestMapping("/rental/violation")
public class ViolationController {

    @Resource
    private IViolationService violationService;

    @Resource
    private IAutoInfoService autoInfoService;

    @PostMapping("/{start}/{size}")
    @PreAuthorize("hasAuthority('busi:violation:select')")
    public Result search(@PathVariable int start, @PathVariable int size, @RequestBody Violation violation){
        return Result.success(violationService.searchByPage(start, size, violation));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('busi:violation:add')")
    public Result save(@RequestBody Violation violation){
        violation.setAutoId(autoInfoService.getIdByNum(violation.getAutoNum()));
        return violationService.save(violation)?Result.success():Result.fail();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('busi:violation:edit')")
    public Result update(@RequestBody Violation violation){
        violation.setAutoId(autoInfoService.getIdByNum(violation.getAutoNum()));
        return violationService.updateById(violation)?Result.success():Result.fail();
    }

    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('busi:violation:delete')")
    public Result delete(@PathVariable String ids){
        return violationService.delete(ids)?Result.success():Result.fail();
    }

    @GetMapping("exportExcel")
    @PreAuthorize("hasAuthority('busi:violation:export')")
    public Result exportExcel(HttpServletResponse response) throws IOException {
        List<Violation> list = violationService.list();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("deleted", "是否删除");
        writer.write(list, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("违章信息表", StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition","attachment;filename=test"+fileName+".xlsx");
        ServletOutputStream out=response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
        return Result.success();
    }
}
