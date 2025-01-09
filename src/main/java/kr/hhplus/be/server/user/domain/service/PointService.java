package kr.hhplus.be.server.user.domain.service;

import kr.hhplus.be.server.user.domain.entity.Point;
import kr.hhplus.be.server.user.infra.PointRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepositoryImpl pointRepository;

    public Point getBalance(Long userId) {
        return pointRepository.findByUserId(userId);
    }

    @Transactional
    public Long increase(Long userId, int value) {
        Point point = pointRepository.findByUserId(userId);
        point.increase(value);
        pointRepository.save(point);
        return point.getValue();
    }

}
