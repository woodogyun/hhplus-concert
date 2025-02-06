package kr.hhplus.be.server.concert.domain.entity;
import kr.hhplus.be.server.api.concert.domain.entity.Seat;
import kr.hhplus.be.server.api.concert.domain.entity.SeatState;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.common.exception.SeatReservationException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SeatTest {

    @Test
    public void testReserveSeatSuccessfully() {
        // 좌석 생성 (상태: AVAILABLE)
        Seat seat = new Seat();
        seat.setState(SeatState.AVAILABLE);

        // 좌석 예약
        seat.reserve();

        // 예약 후 상태가 UNAVAILABLE인지 확인
        assertThat(seat.getState()).isEqualTo(SeatState.UNAVAILABLE);
    }

    @Test
    public void testReserveSeatWhenNotAvailable() {
        // 좌석 생성 (상태: UNAVAILABLE)
        Seat seat = new Seat();
        seat.setState(SeatState.UNAVAILABLE);

        // 좌석 예약 시 예외 발생 확인
        assertThatThrownBy(seat::reserve)
                .isInstanceOf(SeatReservationException.class)
                .hasMessageContaining(ErrorCode.SEAT_NOT_AVAILABLE.getMessage());
    }

    @Test
    public void testMakeAvailableSuccessfully() {
        // 좌석 생성 (상태: UNAVAILABLE)
        Seat seat = new Seat();
        seat.setState(SeatState.UNAVAILABLE);

        // 좌석을 다시 사용 가능 상태로 변경
        seat.makeAvailable();

        // 상태가 AVAILABLE인지 확인
        assertThat(seat.getState()).isEqualTo(SeatState.AVAILABLE);
    }

    @Test
    public void testMakeAvailableWhenNotUnavailable() {
        // 좌석 생성 (상태: AVAILABLE)
        Seat seat = new Seat();
        seat.setState(SeatState.AVAILABLE);

        // 좌석을 사용 가능 상태로 변경 시 예외 발생 확인
        assertThatThrownBy(seat::makeAvailable)
                .isInstanceOf(SeatReservationException.class)
                .hasMessageContaining(ErrorCode.SEAT_NOT_UNAVAILABLE.getMessage());
    }
}
