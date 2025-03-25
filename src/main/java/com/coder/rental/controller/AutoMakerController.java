package com.coder.rental.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.AutoBrand;
import com.coder.rental.entity.AutoMaker;
import com.coder.rental.service.IAutoMakerService;
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
@RequestMapping("/rental/autoMaker")
public class AutoMakerController {

    @Resource
    private IAutoMakerService autoMakerService;

    //分页查询
    @PostMapping("/{start}/{size}")
    @PreAuthorize("hasAuthority('auto:maker:select')")
    public Result search(@PathVariable int start, @PathVariable int size,
                         @RequestBody AutoMaker autoMaker) {
        Page<AutoMaker> page = autoMakerService.search(start, size, autoMaker);
        return Result.success(page);
    }


    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('auto:maker:delete')")
    public Result delete(@PathVariable String ids) {
        List<Integer> list = Arrays.stream(ids.split(",")).map(Integer::parseInt).toList();
        return autoMakerService.removeByIds(list)? Result.success() : Result.fail();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('auto:maker:add')")
    public Result save(@RequestBody AutoMaker autoMaker) {
        autoMaker.setOrderLetter(PinYinUtils.getPinYin(autoMaker.getName()));
        return autoMakerService.save(autoMaker)? Result.success() : Result.fail();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('auto:maker:edit')")
    public Result update(@RequestBody AutoMaker autoMaker) {
        autoMaker.setOrderLetter(PinYinUtils.getPinYin(autoMaker.getName()));
        return autoMakerService.updateById(autoMaker)? Result.success() : Result.fail();
    }

    @GetMapping
    public Result selectAll() {
        return Result.success(autoMakerService.list());
    }

    @GetMapping("exportExcel")
    @PreAuthorize("hasAuthority('auto:maker:export')")
    public Result exportExcel(HttpServletResponse response) throws IOException {
        List<AutoMaker> list = autoMakerService.list();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("deleted", "是否删除");
        writer.write(list, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("汽车厂商信息表", StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition","attachment;filename=test"+fileName+".xlsx");
        ServletOutputStream out=response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
        return Result.success();
    }

}
