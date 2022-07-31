package com.project.common.service.exception;


public class GroupMemberNotFoundException extends RuntimeException {
    private static final String MESSAGE = "잘못된 요청입니다.";

    public GroupMemberNotFoundException() {
        super(MESSAGE);
    }

    public GroupMemberNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }
}