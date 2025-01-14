package kr.hhplus.be.server.payment.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation")
@Builder
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 예약 ID (기본 키)

    @Column(name = "user_id", nullable = false)
    private Long userId; // 사용자 ID

    @Column(name = "value", nullable = false)
    private Long value; // 포인트

    public void decrease(long value) {
        if (this.value < value) {
            throw new IllegalArgumentException("잘못된 포인트 값 입니다.");
        }
        this.value -= value;
    }

    public void increase(long value) {
        if (value <= 0) {
            throw new IllegalArgumentException("잘못된 포인트 값 입니다.");
        }
        this.value += value;
    }

}
