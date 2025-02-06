package kr.hhplus.be.server.concert.domain.service;

import kr.hhplus.be.server.api.concert.domain.service.SeatService;
import kr.hhplus.be.server.common.exception.SeatException;
import kr.hhplus.be.server.api.concert.domain.dto.SeatResult;
import kr.hhplus.be.server.api.concert.domain.entity.Seat;
import kr.hhplus.be.server.api.concert.domain.entity.SeatState;
import kr.hhplus.be.server.api.concert.domain.repository.SeatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertThrows;


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
                .price(10000L)
                .build();

        Seat seat2 = Seat.builder()
                .id(2L)
                .scheduleId(scheduleId)
                .state(seatStatus)
                .price(15000L)
                .build();

        List<Seat> mockSeats = Arrays.asList(seat1, seat2);

        when(seatRepository.findByScheduleIdAndState(scheduleId, seatStatus)).thenReturn(mockSeats);

        // When
        List<SeatResult> seatResults = seatService.getSeats(scheduleId, seatStatus);

        // Then
        assertThat(seatResults).hasSize(2);
        assertThat(seatResults.get(0).getId()).isEqualTo(seat1.getId());
        assertThat(seatResults.get(1).getId()).isEqualTo(seat2.getId());
    }

    @Test
    public void testReserveSeat() {
        // Given
        long seatId = 1L;
        long price = 10000L;
        Seat seat = Seat.builder()
                .id(seatId)
                .scheduleId(1L)
                .state(SeatState.AVAILABLE)
                .price(price)
                .build();

        when(seatRepository.findByIdAndState(seatId, SeatState.AVAILABLE)).thenReturn(Optional.of(seat));

        // When reserving the seat, change its state to RESERVED
        seat.setState(SeatState.AVAILABLE);
        when(seatRepository.save(any(Seat.class))).thenReturn(seat);

        // When
        SeatResult reservedSeat = seatService.reserveSeat(seatId);

        // Then
        assertThat(reservedSeat.getId()).isEqualTo(seatId);
        assertThat(reservedSeat.getState()).isEqualTo(SeatState.UNAVAILABLE);
        assertThat(reservedSeat.getPrice()).isEqualTo(price);
        verify(seatRepository, times(1)).save(seat);
    }

    @Test
    public void testReserveSeatNotFound() {
        // Given
        long seatId = 1L;

        when(seatRepository.findByIdAndState(seatId, SeatState.AVAILABLE)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(SeatException.class, () -> seatService.reserveSeat(seatId));
    }

    @Test
    public void testMakeSeatAvailableNotFound() {
        // Given
        long seatId = 1L;

        when(seatRepository.findById(seatId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(SeatException.class, () -> seatService.makeSeatAvailable(seatId));
    }
}

