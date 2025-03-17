package com.coder.rental.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coder.rental.vo.FinanceCostVo;
import com.coder.rental.vo.FinanceNumDayVo;
import com.coder.rental.vo.FinanceNumMonthVo;
import org.apache.poi.ss.formula.functions.Finance;


import java.time.LocalDate;
import java.util.List;

public interface FinanceMapper extends BaseMapper<FinanceNumDayVo> {
    public List<FinanceNumDayVo> countDayRental(LocalDate date);

    public List<FinanceNumDayVo> countDayReturn(LocalDate date);

    public List<FinanceNumDayVo> countDay(LocalDate date);

    FinanceCostVo countDayCost(LocalDate date);

    public List<FinanceNumMonthVo> countMonth(LocalDate date);

    FinanceCostVo countMonthCost(LocalDate date);
}
