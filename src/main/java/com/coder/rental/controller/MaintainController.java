package com.coder.rental.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.AutoInfo;
import com.coder.rental.entity.Maintain;
import com.coder.rental.service.IAutoInfoService;
import com.coder.rental.service.IMaintainService;
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
@RequestMapping("/rental/maintain")
public class MaintainController {

    @Resource
    private IMaintainService maintainService;

    @Resource
    private IAutoInfoService autoInfoService;

    @PostMapping("/{start}/{size}")
    public Result search(@PathVariable int start, @PathVariable int size, @RequestBody Maintain maintain){
        Page<Maintain> page = new Page<>(start, size);
        return Result.success(maintainService.search(page, maintain));
    }
    @PostMapping
    public Result save(@RequestBody Maintain maintain){
        AutoInfo auto = autoInfoService.getByAutoNum(maintain.getAutoNum());
        maintain.setAutoId(auto.getId());
        auto.setActualNum(auto.getActualNum()+1);
        autoInfoService.updateById(auto);
        return maintainService.save(maintain)?Result.success():Result.fail();
    }

    @PutMapping
    public Result update(@RequestBody Maintain maintain){
        AutoInfo auto = autoInfoService.getByAutoNum(maintain.getAutoNum());
        maintain.setAutoId(auto.getId());
        return maintainService.updateById(maintain)?Result.success():Result.fail();
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids){
        return maintainService.delete(ids)?Result.success():Result.fail();
    }

}
