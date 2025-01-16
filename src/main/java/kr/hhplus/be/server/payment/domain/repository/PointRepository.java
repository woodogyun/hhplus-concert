package kr.hhplus.be.server.payment.domain.repository;

import kr.hhplus.be.server.payment.domain.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {
    Point findByUserId(Long userId);
}
