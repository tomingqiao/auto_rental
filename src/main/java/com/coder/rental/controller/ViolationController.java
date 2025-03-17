package com.coder.rental.controller;

import com.coder.rental.entity.Violation;
import com.coder.rental.service.IAutoInfoService;
import com.coder.rental.service.IViolationService;
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
@RequestMapping("/rental/violation")
public class ViolationController {

    @Resource
    private IViolationService violationService;

    @Resource
    private IAutoInfoService autoInfoService;

    @PostMapping("/{start}/{size}")
    public Result search(@PathVariable int start, @PathVariable int size, @RequestBody Violation violation){
        return Result.success(violationService.searchByPage(start, size, violation));
    }

    @PostMapping
    public Result save(@RequestBody Violation violation){
        violation.setAutoId(autoInfoService.getIdByNum(violation.getAutoNum()));
        return violationService.save(violation)?Result.success():Result.fail();
    }

    @PutMapping
    public Result update(@RequestBody Violation violation){
        violation.setAutoId(autoInfoService.getIdByNum(violation.getAutoNum()));
        return violationService.updateById(violation)?Result.success():Result.fail();
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids){
        return violationService.delete(ids)?Result.success():Result.fail();
    }
}
