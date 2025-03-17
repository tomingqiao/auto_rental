package com.coder.rental.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.Order;
import com.coder.rental.service.IOrderService;
import com.coder.rental.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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
    public Result save(@RequestBody Order order) {
        return orderService.add(order)?Result.success() : Result.fail();
    }

    @PostMapping("/unfinished/{start}/{size}")
    public Result selectUnfinished(@PathVariable int start,@PathVariable int size,@RequestBody Order order) {
        return Result.success(orderService.selectUnfinished(start,size,order));
    }

    @PostMapping("/{start}/{size}")
    public Result search(@PathVariable int start, @PathVariable int size, @RequestBody Order order){
        Page<Order> page = new Page<>(start, size);
        return Result.success(orderService.search(page, order));
    }

    @PutMapping
    public Result update(@RequestBody Order order) {
        return orderService.updateOrder(order)?Result.success() : Result.fail();
    }

    @GetMapping("/returnDeposit/{autoId}")
    public Result returnDeposit(@PathVariable Integer autoId){
        long count = orderService.returnDeposit(autoId);
        return Result.success(count);
    }

    @PutMapping("/updateStatus/{orderId}")
    public Result updateStatus(@PathVariable Integer orderId){
        Order order = orderService.getById(orderId);
        if(order.getDepositReturn()==0)
            order.setDepositReturn(1);
        return orderService.updateById(order)?Result.success() : Result.fail();
    }
}
