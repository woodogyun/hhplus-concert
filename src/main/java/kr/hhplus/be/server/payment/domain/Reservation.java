package kr.hhplus.be.server.payment.domain;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.ReservationState;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation")
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 예약 ID (기본 키)

    @Column(name = "user_id", nullable = false)
    private Long userId; // 사용자 ID

    @Column(name = "seat_id", nullable = false)
    private Long seatId; // 좌석 ID

    @Column(name = "price", nullable = false)
    private Long price; // 가격

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private ReservationState state; // 상태

    @Column(name = "expire_at")
    private LocalDateTime expireAt; // 만료 날짜

}
