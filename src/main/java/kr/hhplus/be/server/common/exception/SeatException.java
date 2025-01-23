package kr.hhplus.be.server.common.exception;

import lombok.Getter;

@Getter
public class SeatException extends RuntimeException {
    private final ErrorCode errorCode;

    public SeatException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}