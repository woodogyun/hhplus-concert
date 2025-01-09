package kr.hhplus.be.server.user.infra.orm;

import kr.hhplus.be.server.user.domain.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointJPARepository extends JpaRepository<Point, Long> {
    Point findByUserId(Long userId);
}
