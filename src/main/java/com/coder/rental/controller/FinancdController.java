package com.coder.rental.controller;

import com.coder.rental.service.IFinanceService;
import com.coder.rental.utils.IntegerUtil;
import com.coder.rental.utils.Result;
import com.coder.rental.vo.FinanceNumDayVo;
import com.coder.rental.vo.FinanceNumMonthVo;
import io.swagger.models.auth.In;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rental/finance")
public class FinancdController {

    @Resource
    private IFinanceService financeService;

    @GetMapping("/day/{date}")
    public Result countDay(@PathVariable LocalDate date) {
        List<FinanceNumDayVo> financeNumDayVos = financeService.countDay(date);
        List<List<Object>> list= new ArrayList<>();
        List<Object> hoursList= new ArrayList<>();
        List<Object> rentalNumList= new ArrayList<>();
        List<Object> returnNumList= new ArrayList<>();
        financeNumDayVos.forEach(item -> {
            hoursList.add(item.getHours());
            rentalNumList.add(item.getRentalNum());
            returnNumList.add(item.getReturnNum());
        });
        list.add(hoursList);
        list.add(rentalNumList);
        list.add(returnNumList);
        return Result.success(list);
    }

    @GetMapping("/day/cost/{date}")
    public Result countDayCost(@PathVariable LocalDate date) {
        return Result.success(financeService.countDayCost(date));
    }

    @GetMapping("/month/{date}")
    public Result countMonth(@PathVariable LocalDate date) {
        List<FinanceNumMonthVo> financeNumMonthVos = financeService.countMonth(date);
        List<List<Object>> list= new ArrayList<>();
        List<Object> daysList= new ArrayList<>();
        List<Object> rentalNumList= new ArrayList<>();
        List<Object> returnNumList= new ArrayList<>();
        financeNumMonthVos.forEach(item -> {
            daysList.add(item.getDays());
            rentalNumList.add(item.getRentalNum());
            returnNumList.add(item.getReturnNum());
        });
        list.add(daysList);
        list.add(rentalNumList);
        list.add(returnNumList);
        return Result.success(list);
    }

    @GetMapping("/month/cost/{date}")
    public Result countMonthCost(@PathVariable LocalDate date) {
        return Result.success(financeService.countMonthCost(date));
    }
}
