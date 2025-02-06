package kr.hhplus.be.server.payment.domain.entity;

import kr.hhplus.be.server.api.payment.domain.entity.Point;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.common.exception.PointException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PointTest {

    @Test
    public void testDecreasePointsSuccessfully() {
        // 포인트 생성 (초기값: 100)
        Point point = new Point();
        point.setValue(100L);

        // 포인트 감소
        point.decrease(50L);

        // 감소 후 포인트 값이 50인지 확인
        assertThat(point.getValue()).isEqualTo(50L);
    }

    @Test
    public void testDecreasePointsWhenInsufficient() {
        // 포인트 생성 (초기값: 30)
        Point point = new Point();
        point.setValue(30L);

        // 포인트 감소 시 예외 발생 확인
        assertThatThrownBy(() -> point.decrease(50L))
                .isInstanceOf(PointException.class)
                .hasMessageContaining(ErrorCode.INSUFFICIENT_POINTS.getMessage());
    }

    @Test
    public void testIncreasePointsSuccessfully() {
        // 포인트 생성 (초기값: 100)
        Point point = new Point();
        point.setValue(100L);

        // 포인트 증가
        point.increase(50L);

        // 증가 후 포인트 값이 150인지 확인
        assertThat(point.getValue()).isEqualTo(150L);
    }

    @Test
    public void testIncreasePointsWithInvalidValue() {
        // 포인트 생성 (초기값: 100)
        Point point = new Point();
        point.setValue(100L);

        // 포인트 증가 시 예외 발생 확인 (0 이하의 값)
        assertThatThrownBy(() -> point.increase(0L))
                .isInstanceOf(PointException.class)
                .hasMessageContaining(ErrorCode.INVALID_POINT_VALUE.getMessage());

        assertThatThrownBy(() -> point.increase(-10L))
                .isInstanceOf(PointException.class)
                .hasMessageContaining(ErrorCode.INVALID_POINT_VALUE.getMessage());
    }
}
