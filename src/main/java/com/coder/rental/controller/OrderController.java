package com.coder.rental.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.Dept;
import com.coder.rental.entity.Order;
import com.coder.rental.service.IOrderService;
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
@RequestMapping("/rental/order")
public class OrderController {

    @Resource
    private IOrderService orderService;

    @PostMapping
    @PreAuthorize("hasAuthority('busi:rental:rental')")
    public Result save(@RequestBody Order order) {
        return orderService.add(order)?Result.success() : Result.fail();
    }

    @PostMapping("/unfinished/{start}/{size}")
    @PreAuthorize("hasAuthority('busi:rentalReturn:select')")
    public Result selectUnfinished(@PathVariable int start,@PathVariable int size,@RequestBody Order order) {
        return Result.success(orderService.selectUnfinished(start,size,order));
    }

    @PostMapping("/{start}/{size}")
    @PreAuthorize("hasAuthority('busi:order:select')")
    public Result search(@PathVariable int start, @PathVariable int size, @RequestBody Order order){
        Page<Order> page = new Page<>(start, size);
        return Result.success(orderService.search(page, order));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('busi:rentalReturn:return')")
    public Result update(@RequestBody Order order) {
        return orderService.updateOrder(order)?Result.success() : Result.fail();
    }

    @GetMapping("/returnDeposit/{autoId}")
    public Result returnDeposit(@PathVariable Integer autoId){
        long count = orderService.returnDeposit(autoId);
        return Result.success(count);
    }

    @PutMapping("/updateStatus/{orderId}")
    @PreAuthorize("hasAuthority('busi:order:returnDeposit')")
    public Result updateStatus(@PathVariable Integer orderId){
        Order order = orderService.getById(orderId);
        if(order.getDepositReturn()==0)
            order.setDepositReturn(1);
        return orderService.updateById(order)?Result.success() : Result.fail();
    }

    @GetMapping("exportExcel")
    @PreAuthorize("hasAuthority('busi:order:export')")
    public Result exportExcel(HttpServletResponse response) throws IOException {
        List<Order> list = orderService.list();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("deleted", "是否删除");
        writer.write(list, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("订单信息表", StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition","attachment;filename=test"+fileName+".xlsx");
        ServletOutputStream out=response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
        return Result.success();
    }
}
