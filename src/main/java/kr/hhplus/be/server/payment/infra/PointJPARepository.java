package kr.hhplus.be.server.payment.infra;

import kr.hhplus.be.server.payment.domain.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointJPARepository extends JpaRepository<Point, Long> {
    Point findByUserId(Long userId);
}
