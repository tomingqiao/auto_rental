package com.coder.rental.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coder.rental.mapper.FinanceMapper;
import com.coder.rental.service.IFinanceService;
import com.coder.rental.vo.FinanceCostVo;
import com.coder.rental.vo.FinanceNumDayVo;
import com.coder.rental.vo.FinanceNumMonthVo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FinanceServiceImpl extends ServiceImpl<FinanceMapper, FinanceNumDayVo> implements IFinanceService {

    @Override
    public List<FinanceNumDayVo> countDayRental() {
        return baseMapper.countDayRental(LocalDate.now());
    }

    @Override
    public List<FinanceNumDayVo> countDayReturn() {
        return baseMapper.countDayReturn(LocalDate.now());
    }

    @Override
    public List<FinanceNumDayVo> countDay(LocalDate date) {
        return baseMapper.countDay(date);
    }

    @Override
    public FinanceCostVo countDayCost(LocalDate date) {
        return baseMapper.countDayCost(date);
    }

    @Override
    public List<FinanceNumMonthVo> countMonth(LocalDate date) {
        return baseMapper.countMonth(date);
    }

    @Override
    public FinanceCostVo countMonthCost(LocalDate date) {
        return baseMapper.countMonthCost(date);
    }
}
