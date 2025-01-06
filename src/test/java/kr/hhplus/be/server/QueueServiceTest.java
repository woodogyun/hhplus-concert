package kr.hhplus.be.server;

import kr.hhplus.be.server.module.queue.domain.Queue;
import kr.hhplus.be.server.module.queue.domain.QueueService;
import kr.hhplus.be.server.module.queue.dto.TokenResponse;
import kr.hhplus.be.server.module.queue.infra.QueueRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QueueServiceTest {

    @Mock
    private QueueRepositoryImpl queueRepository;

    @InjectMocks
    private QueueService queueService;

    @BeforeEach
    public void setUp() {
        // 테스트를 위한 Mock Queue 객체 리스트 생성
        List<Queue> mockQueues = new ArrayList<>();
        // scheduleId를 고정하여 100개의 Queue 객체 생성
        for (int i = 1; i <= 100; i++) {
            Queue queue = new Queue();
            queue.setUserId(Integer.toUnsignedLong(i));
            queue.setUuid("uuid-" + i); // UUID는 고유하게 설정
            queue.setScheduleId(1L); // 고정된 scheduleId
            queue.setStatus("INACTIVE");
            queue.setExpiresAt(null); // INACTIVE 상태이므로 만료 시간 없음
            mockQueues.add(queue);
        }
    }

    @Test
    public void 주어진_사용자와_큐를_생성할_때_생성된_큐가_올바른지() {
        // Given: 새로운 Queue를 생성할 사용자 ID와 스케줄 ID 설정
        Long scheduleId = 1L;
        Long userId = 1L;
        when(queueRepository.findByScheduleIdAndUserId(userId, scheduleId)).thenReturn(Optional.empty());

        // When: Queue 생성 메서드 호출
        Queue generatedQueue = queueService.generateQueue(scheduleId, userId);

        // Then: 생성된 Queue의 속성이 올바른지 검증
        assertNotNull(generatedQueue);
        assertEquals(userId, generatedQueue.getUserId());
        assertEquals(scheduleId, generatedQueue.getScheduleId());
        assertEquals("INACTIVE", generatedQueue.getStatus());
        verify(queueRepository, times(1)).save(generatedQueue);
    }

    @Test
    public void 주어진_UUID와_활성_큐의_상태를_조회할_때_진입이_가능한지() {
        // Given: 활성 상태의 Queue 설정
        String uuid = "uuid-1";
        long scheduleId = 1L;
        Queue activeQueue = new Queue();
        activeQueue.setUuid(uuid);
        activeQueue.setStatus("ACTIVE");
        when(queueRepository.findByUuidAndScheduleId(uuid, scheduleId)).thenReturn(Optional.of(activeQueue));

        // When: Queue 상태 조회 메서드 호출
        TokenResponse response = queueService.getQueueStatus(uuid, scheduleId);

        // Then: 활성 상태일 경우 진입 가능 여부 검증
        assertTrue(response.canEnter());
        verify(queueRepository, times(1)).findByUuidAndScheduleId(uuid, scheduleId);
    }

    @Test
    public void 주어진_UUID와_비활성_큐의_상태를_조회할_때_진입이_불가능한지_및_대기수는_몇인지() {
        // Given: 비활성 상태의 Queue 설정
        String uuid = "uuid-1";
        long scheduleId = 1L;
        Queue inactiveQueue = new Queue();
        inactiveQueue.setUuid(uuid);
        inactiveQueue.setStatus("INACTIVE");
        inactiveQueue.setId(1L);
        when(queueRepository.findByUuidAndScheduleId(uuid, scheduleId)).thenReturn(Optional.of(inactiveQueue));
        when(queueRepository.countByStatusAndIdLessThan("INACTIVE", inactiveQueue.getId())).thenReturn(5L);

        // When: Queue 상태 조회 메서드 호출
        TokenResponse response = queueService.getQueueStatus(uuid, scheduleId);

        // Then: 비활성 상태일 경우 진입 불가 및 대기 수 검증
        assertFalse(response.canEnter());
        assertEquals(5L, response.waitingCount());
        verify(queueRepository, times(1)).findByUuidAndScheduleId(uuid, scheduleId);
    }

    @Test
    public void 활성_큐의_수량을_조회할_때_정확한_수량인지() {
        // Given: 활성 상태의 Queue 수 설정
        when(queueRepository.countByStatus("ACTIVE")).thenReturn(10);

        // When: 활성 Queue 수 조회 메서드 호출
        int activeCount = queueService.countActiveQueues();

        // Then: 활성 Queue 수가 올바른지 검증
        assertEquals(10, activeCount);
        verify(queueRepository, times(1)).countByStatus("ACTIVE");
    }

    @Test
    public void 만료된_토큰을_삭제할_때_삭제된_갯수가_맞는지() {
        // Given: 만료된 토큰 리스트 생성
        List<Queue> expiredTokens = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Queue queue = new Queue();
            queue.setId((long) i);
            expiredTokens.add(queue);
        }
        when(queueRepository.findExpiredTokens()).thenReturn(expiredTokens);

        // When: 만료된 토큰 삭제 메서드 호출
        long deletedCount = queueService.deleteToken();

        // Then: 삭제된 토큰 수 검증
        assertEquals(5, deletedCount);
        verify(queueRepository, times(1)).deleteAll(expiredTokens);
    }
}
