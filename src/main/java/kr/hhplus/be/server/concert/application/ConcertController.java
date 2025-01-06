package kr.hhplus.be.server.concert.application;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.concert.dto.AvailableSeatResponse;
import kr.hhplus.be.server.concert.dto.SeatResponse;

@RestController
@RequestMapping("/api/concert")
@Tag(name = "콘서트 API", description = "콘서트 API")
public class ConcertController {

    @Operation(summary = "콘서트 예약 가능 날짜")
    @GetMapping("/{concert-id}/seat")
    public List<AvailableSeatResponse> getAvailableDates(@PathVariable(value = "concert-id") String concertId) {
        return List.of(new AvailableSeatResponse(1L, LocalDateTime.now()), new AvailableSeatResponse(2L, LocalDateTime.now()));
    }
    
    @Operation(summary = "좌석 조회")
    @GetMapping("/{shedule-id}/seat")
    public List<SeatResponse> getSeats(@PathVariable(value = "shedule-id") String sheduleId) {
        return List.of(new SeatResponse(1,"ACTIVE"), 
                        new SeatResponse(2,"ACTIVE"));
    }

}
