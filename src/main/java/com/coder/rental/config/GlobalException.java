package com.coder.rental.config;

import com.coder.rental.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalException {
    @ExceptionHandler(value = Exception.class)
    public Result handleException(Exception e){
        log.error("异常消息；{}",e.getMessage());
        e.printStackTrace();
        return Result.fail().setMessage(e.getMessage());
    }
}
