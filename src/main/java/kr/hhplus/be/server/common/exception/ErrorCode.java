package kr.hhplus.be.server.common.exception;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;

@Getter
public enum ErrorCode  {

    //Seat
    NOT_FOUNT_SEAT("좌석을 찾을 수 없습니다."),
    SEAT_NOT_AVAILABLE("좌석이 예약 가능 상태가 아닙니다."),
    SEAT_NOT_UNAVAILABLE("좌석이 예약 불가능 상태가 아닙니다."),

    //point
    INSUFFICIENT_POINTS("포인트가 부족합니다."),
    INVALID_POINT_VALUE( "잘못된 포인트 값입니다."),

    //user

    //token
    NOT_FOUNT_TOKEN("토큰을 찾을 수 없습니다."),
    EXPIRED_TOKEN("토큰이 만료되었습니다.");


    // 유의미한 상태 코드를 못찾아서 bad request 통일
    private final int statusCode = HttpServletResponse.SC_BAD_REQUEST;
    private final String message;
    ErrorCode(String message) {
        this.message = message;
    }

//    ErrorCode(int statusCode, String message) {
//        this.statusCode = statusCode;
//        this.message = message;
//    }
}

