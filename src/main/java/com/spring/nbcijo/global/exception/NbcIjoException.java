package com.spring.nbcijo.global.exception;

import com.spring.nbcijo.global.enumeration.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class NbcIjoException extends RuntimeException {

    private final HttpStatus status;
    private final String message;

    public NbcIjoException(ErrorCode errorCode) {
        super(errorCode.getMessage());

        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }
}
