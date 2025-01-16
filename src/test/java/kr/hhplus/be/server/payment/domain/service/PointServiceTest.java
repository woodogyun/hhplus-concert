package kr.hhplus.be.server.payment.domain.service;

import kr.hhplus.be.server.payment.domain.entity.Point;
import kr.hhplus.be.server.payment.domain.repository.PointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PointServiceTest {

    @InjectMocks
    private PointService pointService;

    @Mock
    private PointRepository pointRepository;

    private Point point;

    @BeforeEach
    public void setUp() {
        point = new Point();
        point.setValue(100L); // 초기 포인트 값 설정
    }

    @Test
    public void testGetPoint() {
        Long userId = 1L;
        when(pointRepository.findByUserId(userId)).thenReturn(point);

        Point result = pointService.getPoint(userId);

        assertEquals(point, result);
        verify(pointRepository, times(1)).findByUserId(userId);
    }

    @Test
    public void testChargePoint() {
        Long userId = 1L;
        int chargeValue = 50;
        when(pointRepository.findByUserId(userId)).thenReturn(point);

        Long newValue = pointService.chargePoint(userId, chargeValue);

        assertEquals(150, newValue); // 100 + 50
        verify(pointRepository, times(1)).findByUserId(userId);
        verify(pointRepository, times(1)).save(point);
    }

    @Test
    public void testPay() {
        Long userId = 1L;
        int payAmount = 30;
        when(pointRepository.findByUserId(userId)).thenReturn(point);

        pointService.pay(userId, payAmount);

        assertEquals(70, point.getValue()); // 100 - 30
        verify(pointRepository, times(1)).findByUserId(userId);
        verify(pointRepository, times(1)).save(point);
    }
}
