package com.leyou.common.advice;

import com.leyou.common.exception.LyException;
import com.leyou.common.vo.ExceptionResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(LyException.class)
    //拦截RuntimeException,这玩意就是出现LyException就会发生如下处理函数
    public ResponseEntity<ExceptionResult> handleException(LyException e)
    {

        return ResponseEntity.status(e.getExceptionEnum().getCode()).body(new ExceptionResult(e.getExceptionEnum()));
    }
}
