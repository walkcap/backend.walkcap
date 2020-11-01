package com.walkcap.zuul.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.zuul.exception.ZuulException;
import com.walkcap.common.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * 异常统一反馈前端
 * 
 * @author shoen
 *
 */
@Slf4j
@RestController
public class AccessErrorController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    /**
     * zuul的异常处理
     *
     * @param request HTTP请求
     * @return API统一响应
     */
    @RequestMapping
    public Result<String> error(HttpServletRequest request, HttpServletResponse response) {
        Integer code = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        String message="";
        if (exception instanceof ZuulException) {
            message = exception.getMessage();
        }
        //response.setStatus(HttpStatus.OK.value());
        
        ///return Result.error(code,message);
        return Result.error(message);
    }
}

