package com.zero.config;

import lombok.*;

@Data
@Builder
public class Response {

    @Builder.Default
    private Integer code = 200;
    @Builder.Default
    private String message = "SUCCESS";

    private Object data;

    public Response(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
