package kr.hhplus.be.server.module.concert.dto;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "좌석 정보를 반환합니다.")
public record AvailableSeatResponse(
    @Schema(description = "콘서트 스케줄 아이디")
    long scheduleId,

    @Schema(description = "콘서트 날짜")
    LocalDateTime date
) {}
