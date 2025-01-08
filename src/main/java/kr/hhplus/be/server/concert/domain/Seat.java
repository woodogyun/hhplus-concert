package kr.hhplus.be.server.concert.domain;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.SeatState;
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

    /*
    @ManyToOne
    @JoinColumn(name = "schedule_id", insertable = false, updatable = false)
    private ConcertSchedule concertSchedule;
    */
}