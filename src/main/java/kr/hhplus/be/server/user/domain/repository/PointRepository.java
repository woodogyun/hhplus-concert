package kr.hhplus.be.server.user.domain.repository;

import kr.hhplus.be.server.user.domain.entity.Point;

public interface PointRepository {
    Point findByUserId(Long userId);
    Point save(Point point);
}
