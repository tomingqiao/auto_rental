package com.coder.rental.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.coder.rental.entity.AutoBrand;
import com.coder.rental.entity.Dept;
import com.coder.rental.service.IDeptService;
import com.coder.rental.utils.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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
@RequestMapping("/rental/dept")
public class DeptController {

    @Resource
    private IDeptService deptService;

    @PostMapping
    @PreAuthorize("hasAuthority('sys:dept:select')")
    public Result search(@RequestBody Dept dept) {
        return Result.success(deptService.searchList(dept));
    }

    @GetMapping
    public Result tree(){
        return Result.success(deptService.selectTree());
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:dept:add')")
    public Result save(@RequestBody Dept dept) {
        return deptService.save(dept) ? Result.success() : Result.fail();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('sys:dept:edit')")
    public Result update(@RequestBody Dept dept) {
        return deptService.updateById(dept) ? Result.success() : Result.fail();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:dept:delete')")
    public Result delete(@PathVariable Integer id) {
        return deptService.removeById(id) ? Result.success() : Result.fail();
    }

    @GetMapping("/{id}")
    public Result hasChildren(@PathVariable Integer id) {
        return deptService.hasChildren(id)?Result.success().setMessage("有子部门"):Result.success().setMessage("无子部门");
    }

    @GetMapping("exportExcel")
    @PreAuthorize("hasAuthority('sys:dept:export')")
    public Result exportExcel(HttpServletResponse response) throws IOException {
        List<Dept> list = deptService.list();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("deleted", "是否删除");
        writer.write(list, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("部门信息表", StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition","attachment;filename=test"+fileName+".xlsx");
        ServletOutputStream out=response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
        return Result.success();
    }
}
