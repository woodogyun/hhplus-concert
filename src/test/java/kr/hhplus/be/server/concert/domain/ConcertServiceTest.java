package kr.hhplus.be.server.concert.domain;

import kr.hhplus.be.server.common.SeatState;
import kr.hhplus.be.server.concert.dto.ConcertDateResponse;
import kr.hhplus.be.server.concert.dto.SeatResponse;
import kr.hhplus.be.server.concert.infra.ConcertRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {

    @Mock
    private ConcertRepositoryImpl concertRepository;

    @InjectMocks
    private ConcertService concertService;

    @Test
    public void 예약가능좌석조회() {
        // given: 테스트에 필요한 데이터 준비
        long concertId = 1L;
        LocalDateTime currentDate = LocalDateTime.now();
        ConcertSchedule schedule = ConcertSchedule.builder()
                .id(1L)
                .performanceDateAt(currentDate.plusDays(1))
                .build(); // 예시 데이터

        // when: 특정 메서드 호출 시나리오 설정
        when(concertRepository.findAvailableConcertSchedules(concertId, currentDate))
                .thenReturn(Collections.singletonList(schedule));

        // then: 메서드 호출 및 결과 검증
        List<ConcertDateResponse> availableSeats = concertService.availableSeats(concertId, currentDate);

        assertEquals(1, availableSeats.size());
        assertEquals(1L, availableSeats.get(0).scheduleId());
        assertEquals(schedule.getPerformanceDateAt(), availableSeats.get(0).performanceDateAt());
    }

    @Test
    public void 좌석정보조회() {
        // given: 테스트에 필요한 데이터 준비
        long scheduleId = 1L;
        Seat seat = Seat.builder()
                .id(1L)
                .state(SeatState.AVAILABLE)
                .build(); // 예시 데이터

        // when: 특정 메서드 호출 시나리오 설정
        when(concertRepository.findByScheduleIdAndState(scheduleId, SeatState.AVAILABLE))
                .thenReturn(Collections.singletonList(seat));

        // then: 메서드 호출 및 결과 검증
        List<SeatResponse> seats = concertService.getSeats(scheduleId, SeatState.AVAILABLE);

        assertEquals(1, seats.size());
        assertEquals(1L, seats.get(0).seatId());
        assertEquals(SeatState.AVAILABLE.name(), seats.get(0).state());
    }

    @Test
    public void 좌석예약() {
        // given: 테스트에 필요한 데이터 준비
        long seatId = 1L;
        Seat seat = Seat.builder()
                .id(seatId)
                .state(SeatState.AVAILABLE)
                .build(); // 예시 데이터

        // when: 특정 메서드 호출 시나리오 설정
        when(concertRepository.findByIdAndState(seatId, SeatState.AVAILABLE))
                .thenReturn(Optional.of(seat));

        // then: 메서드 호출 및 결과 검증
        Seat reservedSeat = concertService.reserveSeat(seatId);

        assertEquals(SeatState.UNAVAILABLE, reservedSeat.getState());
        verify(concertRepository).save(seat);
    }

    @Test
    public void 좌석예약_좌석없음() {
        // given: 테스트에 필요한 데이터 준비
        long seatId = 1L;

        // when: 특정 메서드 호출 시나리오 설정
        when(concertRepository.findByIdAndState(seatId, SeatState.AVAILABLE))
                .thenReturn(Optional.empty());

        // then: 예외 발생 검증
        Exception exception = assertThrows(RuntimeException.class, () -> {
            concertService.reserveSeat(seatId);
        });

        assertEquals("Seat not found", exception.getMessage());
    }
}
