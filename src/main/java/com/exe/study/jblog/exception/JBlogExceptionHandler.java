package com.exe.study.jblog.exception;


import com.exe.study.jblog.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

// 예외처리 핸들러 적용 : 모든 종류의 예외를 처리하기위해서 작성, 예외들의 일괄처리
@ControllerAdvice
@RestController
public class JBlogExceptionHandler {

    // 발생된 모든 예외를 받아서 HTML메세지로 반환
/*
    @ExceptionHandler(value = Exception.class)
    public String globalExceptionHandlerHtml(Exception e){
        return "<h1>" + e.getMessage() + "</h1>";
    }
*/

    //응답 dto로 시스템 예외처리하기
    @ExceptionHandler(value = Exception.class)
    public ResponseDTO<String> globalExceptionHandler(Exception e){
        return new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
