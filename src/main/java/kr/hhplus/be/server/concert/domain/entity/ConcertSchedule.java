package kr.hhplus.be.server.concert.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "concert_schedule")
@Builder
public class ConcertSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키

    @Column(nullable = false, name = "concert_id")
    private Long concertId; // 콘서트 아이디

    @Column(name = "reservation_start_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime reservationStartAt; // 예약 시작 날짜

    @Column(name = "reservation_end_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime reservationEndAt; // 예약 종료 날짜

    @Column(name = "performance_date_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime performanceDateAt; // 공연 날짜

}