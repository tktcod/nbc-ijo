package com.spring.nbcijo.global.enumeration;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    NOT_FOUND_POST(BAD_REQUEST, "없는 게시글입니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
