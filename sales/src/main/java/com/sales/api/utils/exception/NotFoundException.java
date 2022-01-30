package com.sales.api.utils.exception;

public class NotFoundException extends AbstractException {

    public NotFoundException(String code, String message) {
        super(code, message);
    }
}
