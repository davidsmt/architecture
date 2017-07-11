package com.davidsmt.architecture.domain.model;

/**
 * Created by d.san.martin.torres on 6/7/17.
 */

public class DomainError {

    public static final int REPOSITORY_ERROR = 0;

    private int code;
    private String message;

    public DomainError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
