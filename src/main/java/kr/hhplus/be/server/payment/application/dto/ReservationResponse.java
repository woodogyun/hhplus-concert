package kr.hhplus.be.server.payment.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "예약 가능 여부 반환합니다.")
public record ReservationResponse(
    @Schema(description = "예약 가능 여부")
    boolean isAvailable
) {}
