package com.coder.rental.controller;

import com.coder.rental.entity.RentalType;
import com.coder.rental.service.IRentalTypeService;
import com.coder.rental.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
}
