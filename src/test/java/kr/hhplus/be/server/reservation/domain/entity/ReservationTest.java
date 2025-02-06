package kr.hhplus.be.server.reservation.domain.entity;

import kr.hhplus.be.server.api.reservation.domain.entity.Reservation;
import kr.hhplus.be.server.api.reservation.domain.entity.ReservationState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationTest {

    private Reservation reservation;

    @BeforeEach
    public void setUp() {
        reservation = Reservation.create(1L, 10000L, 1L, LocalDateTime.now().plusHours(1));
    }

    @Test
    public void testCreateReservation() {
        assertThat(reservation).isNotNull();
        assertThat(reservation.getUserId()).isEqualTo(1L);
        assertThat(reservation.getSeatId()).isEqualTo(1L);
        assertThat(reservation.getPrice()).isEqualTo(10000L);
        assertThat(reservation.getState()).isEqualTo(ReservationState.IN_PROGRESS);
        assertThat(reservation.getExpireAt()).isAfter(LocalDateTime.now());
    }

    @Test
    public void testCompleteReservation() {
        reservation.complete();
        assertThat(reservation.getState()).isEqualTo(ReservationState.COMPLETED);
    }

    @Test
    public void testExpireReservation() {
        reservation.expire();
        assertThat(reservation.getState()).isEqualTo(ReservationState.EXPIRED);
    }

    @Test
    public void testInvalidStateChange() {
        reservation.complete();
        assertThat(reservation.getState()).isEqualTo(ReservationState.COMPLETED);

        // 이미 COMPLETED 상태에서 다시 complete()를 호출하는 경우
        reservation.complete(); // 이 호출은 상태를 변경하지 않음
        assertThat(reservation.getState()).isEqualTo(ReservationState.COMPLETED);
    }
}
