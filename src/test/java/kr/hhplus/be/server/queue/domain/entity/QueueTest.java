package kr.hhplus.be.server.queue.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QueueTest {

    private Queue queue;

    @BeforeEach
    public void setUp() {
        queue = Queue.create(1L, 100L);
    }

    @Test
    public void testCreateQueue() {
        assertThat(queue).isNotNull();
        assertThat(queue.getUserId()).isEqualTo(1L);
        assertThat(queue.getConcertId()).isEqualTo(100L);
        assertThat(queue.getState()).isEqualTo(QueueState.INACTIVE);
        assertThat(queue.getUuid()).isNotNull();
    }

    @Test
    public void testActivateQueue() {
        queue.activate();
        assertThat(queue.getState()).isEqualTo(QueueState.ACTIVE);
    }

    @Test
    public void testDeactivateQueue() {
        queue.activate(); // 먼저 활성화
        queue.deactivate();
        assertThat(queue.getState()).isEqualTo(QueueState.INACTIVE);
    }

    @Test
    public void testSetExpiration() {
        LocalDateTime expirationTime = LocalDateTime.now().plusHours(1);
        queue.setExpiration(expirationTime);
        assertThat(queue.getExpiresAt()).isEqualTo(expirationTime);
    }

    @Test
    public void testCountWaiting() {
        List<Queue> queues = new ArrayList<>();

        Queue queue1 = Queue.create(1L, 100L);
        queue1.setId(1L); // ID 1
        queues.add(queue1);

        Queue queue2 = Queue.create(2L, 100L);
        queue2.setId(2L); // ID 2
        queues.add(queue2);

        Queue queue3 = Queue.create(3L, 100L);
        queue3.setId(3L); // ID 3
        queues.add(queue3);

        // 대기 상태로 설정
        queues.get(0).deactivate(); // ID 1
        queues.get(1).activate();    // ID 2
        queues.get(2).deactivate();   // ID 3

        Long waitingCount = Queue.countWaiting(queues, 3L);
        assertThat(waitingCount).isEqualTo(2L); // ID 1과 ID 3이 대기 중
    }
}

