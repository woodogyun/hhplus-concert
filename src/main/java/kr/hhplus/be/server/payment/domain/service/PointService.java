package kr.hhplus.be.server.payment.domain.service;

import kr.hhplus.be.server.payment.domain.entity.Point;
import kr.hhplus.be.server.payment.infra.PointRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepositoryImpl pointRepository;

    public Point getPoint(Long userId) {
        return pointRepository.findByUserId(userId);
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
