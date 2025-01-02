package kr.hhplus.be.server.module.queue.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "토큰 값 반환합니다.")
public record TokenResponse(
    @Schema(description = "uuid")
    String uuid,

    @Schema(description = "상태 값")
    String status
) {}
