package com.coder.rental.utils;

import com.coder.rental.service.IFinanceService;
import com.coder.rental.service.IMailService;
import com.coder.rental.vo.MailVo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class SendMailUtil {

    @Resource
    private IFinanceService financeService;

    @Resource
    private IMailService mailService;

    @Value("${spring.mail.username}")
    private String from;

    // @Scheduled(cron = "0 0 20 ? * *")
    public void sendMail() {
        StringBuffer content = new StringBuffer();
        content.append("今日收入：")
                .append(financeService.countDayCost(LocalDate.now()).getRentActual())
                .append("元\n")
                .append(",今日押金收到：")
                .append(financeService.countDayCost(LocalDate.now()).getDeposit())
                .append("元\n");
        MailVo mailVo = new MailVo();
        mailVo.setFrom(from);
        mailVo.setTo("3107099063@qq.com");
        String date=LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        mailVo.setSubject(date+"收入");
        mailVo.setContent(content.toString());
        mailService.sendMail(mailVo);
    }
}
