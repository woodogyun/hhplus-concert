package kr.hhplus.be.server.concert.domain.service;

import kr.hhplus.be.server.api.concert.domain.dto.ConcertScheduleResult;
import kr.hhplus.be.server.api.concert.domain.entity.ConcertSchedule;
import kr.hhplus.be.server.api.concert.domain.repository.ConcertScheduleRepository;
import kr.hhplus.be.server.api.concert.domain.service.ConcertScheduleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConcertScheduleServiceTest {

    @InjectMocks
    private ConcertScheduleService concertScheduleService;

    @Mock
    private ConcertScheduleRepository concertScheduleRepository;

    @Test
    public void testAvailableSeats() {
        // Given
        long concertId = 1L;

        // Mocking the ConcertSchedule objects
        ConcertSchedule schedule1 = ConcertSchedule.builder()
                .id(1L)
                .concertId(concertId)
                .reservationStartAt(LocalDateTime.now().minusDays(1))
                .reservationEndAt(LocalDateTime.now().plusDays(1))
                .performanceDateAt(LocalDateTime.now().plusDays(1))
                .build();

        ConcertSchedule schedule2 = ConcertSchedule.builder()
                .id(2L)
                .concertId(concertId)
                .reservationStartAt(LocalDateTime.now().minusDays(1))
                .reservationEndAt(LocalDateTime.now().plusDays(1))
                .performanceDateAt(LocalDateTime.now().plusDays(2))
                .build();

        List<ConcertSchedule> mockSchedules = List.of(schedule1, schedule2);

        when(concertScheduleRepository.findAvailableConcertSchedules(anyLong(), any(LocalDateTime.class)))
                .thenReturn(mockSchedules);

        // When
        List<ConcertScheduleResult> availableSeats = concertScheduleService.availableSeats(concertId);

        // Then
        assertThat(availableSeats.get(0).getPerformanceDateAt()).isEqualTo(schedule1.getPerformanceDateAt());
        assertThat(availableSeats.get(1).getPerformanceDateAt()).isEqualTo(schedule2.getPerformanceDateAt());
    }
}
