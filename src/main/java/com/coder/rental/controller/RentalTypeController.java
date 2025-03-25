package com.coder.rental.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.coder.rental.entity.Dept;
import com.coder.rental.entity.RentalType;
import com.coder.rental.service.IRentalTypeService;
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
@RequestMapping("/rental/rentalType")
public class RentalTypeController {

    @Resource
    private IRentalTypeService rentalTypeService;

    @PostMapping("/{start}/{size}")
    @PreAuthorize("hasAuthority('busi:rentalType:select')")
    public Result search(@PathVariable int start, @PathVariable int size, @RequestBody RentalType rentalType) {
        return Result.success(rentalTypeService.searchByPage(start, size, rentalType));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('busi:rentalType:add')")
    public Result save(@RequestBody RentalType rentalType){
        return Result.success(rentalTypeService.save(rentalType));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('busi:rentalType:edit')")
    public Result update(@RequestBody RentalType rentalType){
        return rentalTypeService.updateById(rentalType)?Result.success():Result.fail();
    }

    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('busi:rentalType:delete')")
    public Result delete(@PathVariable String ids){
        return rentalTypeService.delete(ids)? Result.success() : Result.fail();
    }

    @GetMapping
    public Result selectAll(){
        List<RentalType> list = rentalTypeService.list();
        return Result.success(list);
    }

    @GetMapping("exportExcel")
    @PreAuthorize("hasAuthority('busi:rentalType:export')")
    public Result exportExcel(HttpServletResponse response) throws IOException {
        List<RentalType> list = rentalTypeService.list();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("deleted", "是否删除");
        writer.write(list, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("出租类型信息表", StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition","attachment;filename=test"+fileName+".xlsx");
        ServletOutputStream out=response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
        return Result.success();
    }
}
