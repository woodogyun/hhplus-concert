package kr.hhplus.be.server.concert.domain.service;

import kr.hhplus.be.server.common.SeatState;
import kr.hhplus.be.server.concert.domain.entity.Seat;
import kr.hhplus.be.server.concert.domain.repository.SeatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SeatServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private SeatService seatService;

    @Test
    public void testGetSeats() {
        // Given
        long scheduleId = 1L;
        SeatState seatStatus = SeatState.AVAILABLE;
        Seat seat1 = Seat.builder()
                .id(1L)
                .scheduleId(scheduleId)
                .state(seatStatus)
                .build();
        Seat seat2 = Seat.builder()
                .id(2L)
                .scheduleId(scheduleId)
                .state(seatStatus)
                .build();
        when(seatRepository.findByScheduleIdAndState(scheduleId, seatStatus))
                .thenReturn(Arrays.asList(seat1, seat2));

        // When
        List<Seat> seats = seatService.getSeats(scheduleId, seatStatus);

        // Then
        assertEquals(2, seats.size());
        verify(seatRepository, times(1)).findByScheduleIdAndState(scheduleId, seatStatus);
    }

    @Test
    public void testReserveSeat() {
        // Given
        long seatId = 1L;
        Seat seat = Seat.builder()
                .id(seatId)
                .scheduleId(1L)
                .state(SeatState.AVAILABLE)
                .build();
        when(seatRepository.findByIdAndState(seatId, SeatState.AVAILABLE))
                .thenReturn(Optional.of(seat));
        when(seatRepository.save(seat)).thenReturn(seat);

        // When
        Seat reservedSeat = seatService.reserveSeat(seatId);

        // Then
        assertEquals(SeatState.UNAVAILABLE, reservedSeat.getState());
        verify(seatRepository, times(1)).findByIdAndState(seatId, SeatState.AVAILABLE);
        verify(seatRepository, times(1)).save(seat);
    }

    @Test
    public void testReserveSeat_NotFound() {
        // Given
        long seatId = 1L;
        when(seatRepository.findByIdAndState(seatId, SeatState.AVAILABLE))
                .thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            seatService.reserveSeat(seatId);
        });

        assertEquals("Seat not found", exception.getMessage());
        verify(seatRepository, times(1)).findByIdAndState(seatId, SeatState.AVAILABLE);
        verify(seatRepository, never()).save(any());
    }
}
