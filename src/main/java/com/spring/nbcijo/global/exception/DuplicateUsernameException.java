package com.spring.nbcijo.global.exception;

public class DuplicateUsernameException extends RuntimeException {

    public DuplicateUsernameException(String username) {
        super("이미 " + username + " 유저가 존재합니다.");
    }
}
