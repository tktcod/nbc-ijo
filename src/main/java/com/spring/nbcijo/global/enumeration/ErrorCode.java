package com.spring.nbcijo.global.enumeration;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    NOT_UPDATE(FORBIDDEN, "수정 권한이 없습니다."),
    NOT_DELETE(FORBIDDEN, "삭제 권한이 없습니다."),
    NOT_CREATE(FORBIDDEN, "생성 권한이 없습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
