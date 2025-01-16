package kr.hhplus.be.server.common.exception;

import lombok.Getter;

@Getter
public class SeatReservationException extends RuntimeException {
    private final ErrorCode errorCode;

    public SeatReservationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
