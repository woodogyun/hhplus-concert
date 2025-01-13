package kr.hhplus.be.server.payment.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "point를 반환합니다.")
public record PointResponse(
    @Schema(description = "포인트 값")
    long point
) {}
