package com.coder.rental.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.AutoInfo;
import com.coder.rental.service.IAutoInfoService;
import com.coder.rental.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/rental/autoInfo")
public class AutoInfoController {

    @Value("${auto.info.maintain-mileage}")
    private Integer maintainMileage;

    @Resource
    private IAutoInfoService autoInfoService;

    @PostMapping("/{start}/{size}")
    @PreAuthorize("hasAuthority('auto:info:select')")
    public Result search(@PathVariable int start, @PathVariable int size, @RequestBody AutoInfo autoInfo){
        return Result.success(autoInfoService.search(start, size, autoInfo));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('auto:info:add')")
    public Result save(@RequestBody AutoInfo autoInfo){
        int i = autoInfo.getMileage() / maintainMileage;
        autoInfo.setExpectedNum(i);
        autoInfo.setActualNum(i);
        return autoInfoService.save(autoInfo)?Result.success():Result.fail();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('auto:info:edit')")
    public Result update(@RequestBody AutoInfo autoInfo){
        int i = autoInfo.getMileage() / maintainMileage;
        autoInfo.setExpectedNum(i);
        autoInfo.setActualNum(i);
        return autoInfoService.updateById(autoInfo)?Result.success():Result.fail();
    }

    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('auto:info:delete')")
    public Result delete(@PathVariable String ids){
        return autoInfoService.delete(ids)?Result.success():Result.fail();
    }

    @PostMapping("/exist")
    public Result existAutoNum(@RequestBody AutoInfo autoInfo){
        long autoNum1 = autoInfoService.count(new QueryWrapper<AutoInfo>().eq("auto_num", autoInfo.getAutoNum()));
        return autoNum1>0?Result.success().setMessage("存在该车牌号码"):Result.success().setMessage("不存在该车牌号码");
    }

    @GetMapping("/toBeMaintain")
    public Result toBeMaintain(){
        return Result.success(autoInfoService.toBeMaintain());
    }
}
