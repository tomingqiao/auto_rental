package com.coder.rental.controller;

import com.coder.rental.entity.Customer;
import com.coder.rental.service.ICustomerService;
import com.coder.rental.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
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
}
