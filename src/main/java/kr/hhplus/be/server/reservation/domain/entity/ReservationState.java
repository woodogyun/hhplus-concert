package kr.hhplus.be.server.reservation.domain.entity;

public enum ReservationState {
    IN_PROGRESS, // 예약중 상태
    COMPLETED,   // 예약 완료 상태
    EXPIRED;     // 예약 만료 상태
}