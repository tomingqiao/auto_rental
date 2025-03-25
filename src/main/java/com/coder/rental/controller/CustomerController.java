package com.coder.rental.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.coder.rental.entity.AutoBrand;
import com.coder.rental.entity.Customer;
import com.coder.rental.service.ICustomerService;
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
@RequestMapping("/rental/customer")
public class CustomerController {

    @Resource
    private ICustomerService customerService;

    @PostMapping("/{start}/{size}")
    @PreAuthorize("hasAuthority('busi:customer:select')")
    public Result search(@PathVariable int start, @PathVariable int size, @RequestBody Customer customer) {
        return Result.success(customerService.searchByPage(start, size, customer));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('busi:customer:add')")
    public Result save(@RequestBody Customer customer) {
        return customerService.save(customer)?Result.success():Result.fail();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('busi:customer:edit')")
    public Result update(@RequestBody Customer customer) {
        return customerService.updateById(customer)?Result.success():Result.fail();
    }

    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('busi:customer:delete')")
    public Result delete(@PathVariable String ids) {
        return customerService.delete(ids)?Result.success():Result.fail();
    }

    @GetMapping("/{tel}")
    @PreAuthorize("hasAuthority('busi:rental:rental')")
    public Result selectByTel(@PathVariable String tel) {
        return Result.success(customerService.selectByTel(tel));
    }

    @GetMapping("exportExcel")
    @PreAuthorize("hasAuthority('busi:customer:export')")
    public Result exportExcel(HttpServletResponse response) throws IOException {
        List<Customer> list = customerService.list();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("deleted", "是否删除");
        writer.write(list, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("客户信息表", StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition","attachment;filename=test"+fileName+".xlsx");
        ServletOutputStream out=response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
        return Result.success();
    }
}
