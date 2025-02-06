package kr.hhplus.be.server.api.concert.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "좌석 정보를 반환합니다.")
public record ConcertDateResponse(
    @Schema(description = "콘서트 스케줄 아이디")
    long scheduleId,

    @Schema(description = "콘서트 공연 날짜")
    LocalDateTime performanceDateAt
) {}
