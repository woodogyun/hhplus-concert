package kr.hhplus.be.server.concert.domain.service;

import kr.hhplus.be.server.concert.domain.entity.ConcertSchedule;
import kr.hhplus.be.server.concert.infra.ConcertScheduleRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConcertScheduleServiceTest {

    @InjectMocks
    private ConcertScheduleService concertScheduleService;

    @Mock
    private ConcertScheduleRepositoryImpl concertScheduleRepository;

    @Test
    public void testAvailableSeats() {
        // Given
        long concertId = 1L;
        LocalDateTime currentDate = LocalDateTime.now();
        ConcertSchedule schedule1 = new ConcertSchedule();
        ConcertSchedule schedule2 = new ConcertSchedule();
        List<ConcertSchedule> expectedSchedules = List.of(schedule1, schedule2);

        when(concertScheduleRepository.findAvailableConcertSchedules(concertId))
                .thenReturn(expectedSchedules);

        // When
        List<ConcertSchedule> actualSchedules = concertScheduleService.availableSeats(concertId);

        // Then
        assertEquals(expectedSchedules, actualSchedules);
        verify(concertScheduleRepository, times(1)).findAvailableConcertSchedules(concertId); // 호출 검증
    }
}
