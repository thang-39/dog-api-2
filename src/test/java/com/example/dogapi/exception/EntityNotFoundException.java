package com.example.dogapi.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class EntityNotFoundException extends RuntimeException {
    private String errorCode;
    private int status;

    public EntityNotFoundException(String message, String errorCode, int status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
}
