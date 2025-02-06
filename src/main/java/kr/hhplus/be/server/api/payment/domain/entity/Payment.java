package kr.hhplus.be.server.api.payment.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment")
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 결제 ID (기본 키)

    @Column(name = "user_id", nullable = false)
    private Long userId; // 사용자 ID

    @Column(name = "reservation_id", nullable = false)
    private Long reservationId; // 예약 ID

    @Column(name = "amount", nullable = false)
    private Long amount; // 결제 금액

    // 결제를 생성하는 정적 메서드
    public static Payment create(Long userId, Long reservationId, Long amount) {
        return Payment.builder()
                .userId(userId)
                .reservationId(reservationId)
                .amount(amount)
                .build();
    }
}
