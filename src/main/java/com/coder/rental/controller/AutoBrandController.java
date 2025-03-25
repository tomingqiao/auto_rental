package com.coder.rental.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.AutoBrand;
import com.coder.rental.entity.AutoMaker;
import com.coder.rental.service.IAutoBrandService;
import com.coder.rental.utils.PinYinUtils;
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
@RequestMapping("/rental/autoBrand")
public class AutoBrandController {

    @Resource
    private IAutoBrandService autoBrandService;

    @PostMapping("/{start}/{size}")
    @PreAuthorize("hasAuthority('auto:brand:select')")
    public Result search(@PathVariable int start, @PathVariable int size,
                         @RequestBody AutoBrand autoBrand) {
        Page<AutoBrand> page = new Page<>(start, size);
        return Result.success(autoBrandService.searchByPage(page, autoBrand));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('auto:brand:add')")
    public Result save(@RequestBody AutoBrand autoBrand) {
        return autoBrandService.save(autoBrand)? Result.success() : Result.fail();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('auto:brand:edit')")
    public Result update(@RequestBody AutoBrand autoBrand) {
        return autoBrandService.updateById(autoBrand)? Result.success() : Result.fail();
    }

    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('auto:brand:delete')")
    public Result delete(@PathVariable String ids) {
        List<Integer> list = Arrays.stream(ids.split(",")).map(Integer::parseInt).toList();
        return autoBrandService.removeByIds(list)? Result.success() : Result.fail();
    }

    @GetMapping("/{id}")
    public Result selectByMakerId(@PathVariable Integer id) {
        return Result.success(autoBrandService.selectByMakerId(id));
    }

    @GetMapping("exportExcel")
    @PreAuthorize("hasAuthority('auto:brand:export')")
    public Result exportExcel(HttpServletResponse response) throws IOException {
        List<AutoBrand> list = autoBrandService.list();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("brandName", "品牌名称");
        writer.addHeaderAlias("deleted", "是否删除");
        writer.write(list, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("汽车品牌信息表", StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition","attachment;filename=test"+fileName+".xlsx");
        ServletOutputStream out=response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
        return Result.success();
    }
}
