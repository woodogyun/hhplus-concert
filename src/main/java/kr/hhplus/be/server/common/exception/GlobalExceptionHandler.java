package kr.hhplus.be.server.common.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SeatReservationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleSeatReservationException(SeatReservationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode());
        return ResponseEntity.status(ex.getErrorCode().getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(SeatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleSeatException(SeatException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode());
        return ResponseEntity.status(ex.getErrorCode().getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(PointException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handlePointException(PointException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode());
        return ResponseEntity.status(ex.getErrorCode().getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(TokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleTokenException(TokenException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode());
        return ResponseEntity.status(ex.getErrorCode().getStatusCode()).body(errorResponse);
    }

}
