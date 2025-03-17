package com.coder.rental.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coder.rental.vo.FinanceCostVo;
import com.coder.rental.vo.FinanceNumDayVo;
import com.coder.rental.vo.FinanceNumMonthVo;

import java.time.LocalDate;
import java.util.List;

public interface IFinanceService extends IService<FinanceNumDayVo> {

    public List<FinanceNumDayVo> countDayRental();

    public List<FinanceNumDayVo> countDayReturn();

    public List<FinanceNumDayVo> countDay(LocalDate date);

    public FinanceCostVo countDayCost(LocalDate date);

    List<FinanceNumMonthVo> countMonth(LocalDate date);

    FinanceCostVo countMonthCost(LocalDate date);
}
