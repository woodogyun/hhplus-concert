package kr.hhplus.be.server.concert.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "좌석 정보를 반환합니다.")
public record SeatResponse(
    
    @Schema(description = "좌석 아이디")
    long seatId,

    @Schema(description = "좌석 상태")
    String state

) {}
