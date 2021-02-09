package com.example.ums.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBean<T> extends Response {

    private T data;

    public ResponseBean(T data) {
        this.data = data;
    }

    public ResponseBean() {
        super();
    }

    public ResponseBean(boolean responseFlag, String code, String message, T data) {
        super(responseFlag, code, message);
        this.data = data;
    }

    public ResponseBean(boolean responseFlag, String code, String message) {
        super(responseFlag, code, message);
    }

    public ResponseBean(boolean success, String message) {
        super(success, message);
    }

    public ResponseBean(Boolean success, String message, T data) {
        super(success, message);
        this.data = data;
    }

    public ResponseBean(String errorCode, Boolean success, T data) {
        super(errorCode, success);
        this.data = data;
    }
}
