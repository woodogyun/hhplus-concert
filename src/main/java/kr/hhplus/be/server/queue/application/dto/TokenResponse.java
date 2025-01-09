package kr.hhplus.be.server.queue.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "토큰 값과 관련된 정보를 반환합니다.")
public record TokenResponse(
//    @Schema(description = "전체 인원 수")
//    Long totalCount, // 전체 수
    @Schema(description = "대기열에서 기다려야 하는 사람 수")
    Long waitingCount, // 대기열에서 기다려야 하는 사람 수
    @Schema(description = "진입 가능 여부")
    boolean canEnter // 진입 가능 여부
) {
    public TokenResponse(boolean canEnter) {
        this(null, canEnter);
    }
}