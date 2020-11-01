package com.walkcap.common;

import java.io.Serializable;

/**
 * 
 * @author shoen
 *
 */
public class StateCode implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer code;
    
    private String msg;
    
    public StateCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString(){
        return "StateCode{" + "code='" + code + '\'' +
                ", msg='" + msg + '\'' + '}';
    }
}

