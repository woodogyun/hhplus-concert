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
@Tag(name = "결제 API", description = "결제 API")
@ApiLog
public class ReservationController {

    private final ReservationFacade reservationFacade;

    @Operation(summary = "좌석 클릭 시 좌석 상태 변경")
    @PostMapping("/{seat-id}/reserve")
    public ResponseEntity<ReservationResponse> reserveSeat(@PathVariable(value = "seat-id") long seatId,
                                                          @RequestHeader("x-token") String uuid) {
        // TODO : 하드코딩 된 유저 아이디 추후에 수정
        long userId = 1L;
        return ResponseEntity.ok(reservationFacade.reserveSeat(seatId, uuid, userId));
    }
}
