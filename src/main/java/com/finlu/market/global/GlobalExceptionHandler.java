package com.finlu.market.global;

import com.finlu.market.domain.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultData> handleException(Exception e) {
        log.info("Exception: {}", e.getMessage());

        ResultData resultData = new ResultData();
        resultData.setCode(500);
        resultData.setData(null);
        resultData.setMessage(e.getMessage());

        return ResponseEntity.ok(resultData);
    }
}
