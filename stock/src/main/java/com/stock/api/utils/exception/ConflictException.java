package com.stock.api.utils.exception;

public class ConflictException extends AbstractException {

    public ConflictException(String code, String message) {
        super(code, message);

    }
}
