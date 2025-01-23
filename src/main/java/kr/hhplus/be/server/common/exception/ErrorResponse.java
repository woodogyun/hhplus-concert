package kr.hhplus.be.server.common.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final Integer code;
    private final String message;
    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.getStatusCode(); // Enum 이름을 코드로 사용
        this.message = errorCode.getMessage();
    }
}
