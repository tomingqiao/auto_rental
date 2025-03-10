package com.coder.rental.controller;

import com.coder.rental.entity.RentalType;
import com.coder.rental.service.IRentalTypeService;
import com.coder.rental.utils.Result;
import jakarta.annotation.Resource;
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
    public Result search(@PathVariable int start, @PathVariable int size, @RequestBody RentalType rentalType) {
        return Result.success(rentalTypeService.searchByPage(start, size, rentalType));
    }

    @PostMapping
    public Result save(@RequestBody RentalType rentalType){
        return Result.success(rentalTypeService.save(rentalType));
    }

    @PutMapping
    public Result update(@RequestBody RentalType rentalType){
        return rentalTypeService.updateById(rentalType)?Result.success():Result.fail();
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids){
        return rentalTypeService.delete(ids)? Result.success() : Result.fail();
    }
}
