package kr.hhplus.be.server.queue.domain.service;

import kr.hhplus.be.server.api.queue.application.dto.TokenResponse;
import kr.hhplus.be.server.api.queue.domain.entity.Queue;
import kr.hhplus.be.server.api.queue.domain.service.QueueService;
import kr.hhplus.be.server.api.queue.infra.QueueRepositoryImpl;
import kr.hhplus.be.server.provider.TimeProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QueueServiceTest {

    @InjectMocks
    private QueueService queueService;

    @Mock
    private QueueRepositoryImpl queueRepositoryImpl;

    @Mock
    private TimeProvider timeProvider;

    private Queue queue;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        long userId = 123L;
        queue = Queue.create(userId);
        queue.passQueue(LocalDateTime.now().plusMinutes(10)); // 상태를 '통과'로 설정
    }

    @Test
    void 토큰_생성_테스트() {
        // Given
        when(queueRepositoryImpl.save(any(Queue.class))).thenReturn(queue);

        // When
        TokenResponse response = queueService.generateToken(123L);

        // Then
        assertNotNull(response);
        assertEquals(queue.getUuid(), response.uuid());
    }

    @Test
    void 큐_상태_조회_테스트_통과() {
        // Given
        when(queueRepositoryImpl.findByUuid(queue.getUuid())).thenReturn(Optional.of(queue));

        // When
        TokenResponse response = queueService.getQueueStatus(queue.getUuid());

        // Then
        assertTrue(response.canEnter());
        assertNull(response.waitingCount());
    }

    @Test
    void 큐_상태_조회_테스트_대기중() {
        // Given
        Queue waitingQueue = Queue.create(456L);
        when(queueRepositoryImpl.findByUuid(waitingQueue.getUuid())).thenReturn(Optional.of(waitingQueue));
        when(queueRepositoryImpl.countByIsQueuePassedAndIdLessThan(false, waitingQueue.getId())).thenReturn(5L);

        // When
        TokenResponse response = queueService.getQueueStatus(waitingQueue.getUuid());

        // Then
        assertFalse(response.canEnter());
        assertEquals(5L, response.waitingCount());
    }

    @Test
    void 토큰_삭제_테스트() {
        // Given
        when(queueRepositoryImpl.findExpiredTokens()).thenReturn(Collections.singletonList(queue));

        // When
        long deletedCount = queueService.deleteToken();

        // Then
        assertEquals(1, deletedCount);
        verify(queueRepositoryImpl).deleteAll(Collections.singletonList(queue));
    }

    @Test
    void 토큰_조회_테스트() {
        // Given
        when(queueRepositoryImpl.findByUuid(queue.getUuid())).thenReturn(Optional.of(queue));

        // When
        Queue foundQueue = queueService.getToken(queue.getUuid());

        // Then
        assertNotNull(foundQueue);
        assertEquals(queue.getUuid(), foundQueue.getUuid());
    }

    @Test
    void 토큰_삭제_유저_테스트() {
        // Given
        long userId = 123L;

        // When
        queueService.delete(userId);

        // Then
        verify(queueRepositoryImpl).deleteByUserId(userId);
    }
}
