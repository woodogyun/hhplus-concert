package kr.hhplus.be.server.api.payment.domain.service;

import kr.hhplus.be.server.api.payment.domain.dto.PointResult;
import kr.hhplus.be.server.api.payment.domain.entity.Point;
import kr.hhplus.be.server.api.payment.domain.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;

    public PointResult getPoint(Long userId) {
        Point point = pointRepository.findByUserId(userId);
        return PointResult.fromEntity(point);
    }

    @Transactional
    public Long chargePoint(Long userId, int value) {
        Point point = pointRepository.findByUserId(userId);
        point.increase(value);
        pointRepository.save(point);
        return point.getValue();
    }

    @Transactional
    public void pay(long userId, long amount) {
        Point point = pointRepository.findByUserId(userId);
        point.decrease(amount);
        pointRepository.save(point);
    }
}
