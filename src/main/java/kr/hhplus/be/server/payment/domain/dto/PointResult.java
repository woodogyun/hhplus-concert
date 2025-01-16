package kr.hhplus.be.server.payment.domain.dto;

import kr.hhplus.be.server.payment.domain.entity.Point;
import lombok.Getter;

@Getter
public class PointResult {
    final private Long userId; // 사용자 ID
    final private Long point; // 포인트 수

    public PointResult(Long userId, Long point) {
        this.userId = userId;
        this.point = point;
    }

    // 정적 팩토리 메소드
    public static PointResult fromEntity(Point point) {
        return new PointResult(point.getUserId(), point.getValue());
    }
}
