package kr.hhplus.be.server.payment.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "결제 성공 여부를 반환합니다.")
public record PaymentResponse(
    @Schema(description = "성공 여부")
    boolean isSuccess
) {}
