package kr.hhplus.be.server.payment.domain.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.common.exception.PointException;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "point")
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
            throw new PointException(ErrorCode.INSUFFICIENT_POINTS);
        }
        this.value -= value;
    }

    public void increase(long value) {
        if (value <= 0) {
            throw new PointException(ErrorCode.INVALID_POINT_VALUE);
        }
        this.value += value;
    }

}
