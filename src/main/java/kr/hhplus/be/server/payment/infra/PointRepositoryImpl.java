package kr.hhplus.be.server.payment.infra;

import kr.hhplus.be.server.payment.domain.entity.Point;
import kr.hhplus.be.server.payment.domain.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepository {

    private final PointJPARepository pointJPARepository;

    @Override
    public Point findByUserId(Long userId) {
        return pointJPARepository.findByUserId(userId);
    }

    @Override
    public Point save(Point point) {
        return pointJPARepository.save(point);
    }

}
