package kr.hhplus.be.server.common.exception;

public class SeatUnavailableException extends RuntimeException {

    public SeatUnavailableException(String message) {
        super(message);
    }

    public SeatUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}