package com.coder.rental.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.AutoMaker;
import com.coder.rental.service.IAutoMakerService;
import com.coder.rental.utils.PinYinUtils;
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
@RequestMapping("/rental/autoMaker")
public class AutoMakerController {

    @Resource
    private IAutoMakerService autoMakerService;

    //分页查询
    @PostMapping("/{start}/{size}")
    public Result search(@PathVariable int start, @PathVariable int size,
                         @RequestBody AutoMaker autoMaker) {
        Page<AutoMaker> page = autoMakerService.search(start, size, autoMaker);
        return Result.success(page);
    }


    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids) {
        List<Integer> list = Arrays.stream(ids.split(",")).map(Integer::parseInt).toList();
        return autoMakerService.removeByIds(list)? Result.success() : Result.fail();
    }

    @PostMapping
    public Result save(@RequestBody AutoMaker autoMaker) {
        autoMaker.setOrderLetter(PinYinUtils.getPinYin(autoMaker.getName()));
        return autoMakerService.save(autoMaker)? Result.success() : Result.fail();
    }

    @PutMapping
    public Result update(@RequestBody AutoMaker autoMaker) {
        autoMaker.setOrderLetter(PinYinUtils.getPinYin(autoMaker.getName()));
        return autoMakerService.updateById(autoMaker)? Result.success() : Result.fail();
    }

    @GetMapping
    public Result selectAll() {
        return Result.success(autoMakerService.list());
    }

}
