package kr.hhplus.be.server.reservation.application.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.common.annotation.ApiLog;
import kr.hhplus.be.server.reservation.application.dto.ReservationResponse;
import kr.hhplus.be.server.reservation.application.facade.ReservationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
@Tag(name = "예약 API", description = "예약 API")
@ApiLog
public class ReservationController {

    private final ReservationFacade reservationFacade;

    @Operation(summary = "좌석 클릭 시 좌석 상태 변경 및 예약 데이터 추가")
    @PostMapping("/{seat-id}/reserve")
    public ResponseEntity<ReservationResponse> reserveSeat(@PathVariable(value = "seat-id") long seatId,
                                                           @RequestHeader("x-token") String uuid,
                                                           @RequestParam long userId) {
        return ResponseEntity.ok(reservationFacade.reserveSeat(seatId, uuid, userId));
    }
}
