package kr.hhplus.be.server.api.concert.domain.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.common.exception.SeatReservationException;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seat") // 테이블 이름 지정
@Builder
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 좌석 ID (기본 키)

    @Column(name = "schedule_id")
    private Long scheduleId; // 일정 ID

    @Column(name = "seat_no", nullable = false)
    private String seatNo; // 좌석 번호

    @Column(nullable = false)
    private Long price; // 좌석 가격

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatState state; // 좌석 상태

    public void reserve() {
        if (this.state != SeatState.AVAILABLE) {
            throw new SeatReservationException(ErrorCode.SEAT_NOT_AVAILABLE);
        }
        this.state = SeatState.UNAVAILABLE;
    }

    public void makeAvailable() {
        if (this.state != SeatState.UNAVAILABLE) {
            throw new SeatReservationException(ErrorCode.SEAT_NOT_UNAVAILABLE);
        }
        this.state = SeatState.AVAILABLE;
    }

}