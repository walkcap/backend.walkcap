package com.walkcap.common;

import java.io.Serializable;
import lombok.Data;

@Data
public class Result<T> implements Serializable {
    private static final Integer SUCCESS = 0;

    private static final Integer ERROR = 1;

    private Integer code;

    private String msg;

    private T data;

    private Object dataExt;

    private Result(){}

    private Result(Integer code,T data,Object dataExt){
        this.code = code;
        this.data = data;
        this.dataExt = dataExt;
    }

    private Result(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public static Result ok(){

        return new Result(SUCCESS , "操作成功");
    }

    public static Result ok(Object data){

        return new Result(SUCCESS , data,null);
    }

    public static Result ok(Object data,Object dataExt){

        return new Result(SUCCESS , data, dataExt);
    }

    public static Result error(String msg){

        return new Result(ERROR , msg);
    }

    public static Result error(ErrorMessage message){

        return new Result(message.getCode() , message.message());
    }

    public static Result error(StateCode statecode) {
        return new Result(statecode.getCode(), statecode.getMsg());
    }
}

