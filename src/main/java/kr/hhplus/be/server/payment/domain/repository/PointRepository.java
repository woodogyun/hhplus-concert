package kr.hhplus.be.server.payment.domain.repository;

import kr.hhplus.be.server.payment.domain.entity.Point;

public interface PointRepository {
    Point findByUserId(Long userId);
    Point save(Point point);
}
