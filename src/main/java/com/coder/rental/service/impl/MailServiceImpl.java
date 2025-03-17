package com.coder.rental.service.impl;

import com.coder.rental.service.IMailService;
import com.coder.rental.vo.MailVo;
import jakarta.annotation.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements IMailService {

    @Resource
    private JavaMailSender javaMailSender;

    @Override
    public void sendMail(MailVo mailVo) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(mailVo.getFrom());
        simpleMailMessage.setTo(mailVo.getTo());
        simpleMailMessage.setSubject(mailVo.getSubject());
        simpleMailMessage.setText(mailVo.getContent());
        simpleMailMessage.setCc(mailVo.getFrom());
        javaMailSender.send(simpleMailMessage);
    }
}
