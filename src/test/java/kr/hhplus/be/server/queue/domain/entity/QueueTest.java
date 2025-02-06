package kr.hhplus.be.server.queue.domain.entity;

import kr.hhplus.be.server.api.queue.domain.entity.Queue;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.common.exception.TokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class QueueTest {

    private Queue queue;

    @BeforeEach
    void setUp() {
        long userId = 123L; // 테스트용 유저 ID
        queue = Queue.create(userId);
    }

    @Test
    void 토큰_생성_테스트() {
        // Queue 객체가 올바르게 생성되었는지 확인
        assertNotNull(queue);
        assertEquals(123L, queue.getUserId());
        assertNotNull(queue.getUuid());
        assertFalse(queue.isQueuePassed());
        assertNull(queue.getExpiredAt());
    }

    @Test
    void 토큰_통과_테스트() {
        // Queue 상태를 변경하는 메서드 테스트
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10);
        queue.passQueue(expirationTime);

        // 상태가 변경되었는지 확인
        assertTrue(queue.isQueuePassed());
        assertEquals(expirationTime, queue.getExpiredAt());
    }

    @Test
    void 유저_아이디_확인() {
        TokenException exception = assertThrows(TokenException.class, () -> {
            Queue.create(0L);
        });

        assertEquals(ErrorCode.INVALID_USER, exception.getErrorCode()); // 예외의 ErrorCode 확인
    }

}