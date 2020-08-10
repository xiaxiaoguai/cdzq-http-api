package com.cdzq.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    NOT_LOGIN(1000,"未登录"),
    NOT_FORBIDDEN(1001,"未授权"),
    NOT_ENOUGH_FORBIDDEN(1002,"无权限"),
    NOT_USER_PASSWORD(1008,"用户名或密码错误"),
    NOT_ALLOW_ACCOUNT(1009,"帐户已禁用"),
    NOT_ALLOW_REQUEST(1010,"非法请求");


    private Integer code;
    private String value;

    @Override
    public String toString() {
        return "[" + this.code + "]" + this.value;
    }
}
