package kr.hhplus.be.server.api.queue.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.api.queue.domain.entity.Queue;
import lombok.Builder;

@Schema(description = "토큰 값과 관련된 정보를 반환합니다.")
@Builder
public record TokenResponse(
    @Schema(description = "uuid")
    String uuid,
    @Schema(description = "대기열에서 기다려야 하는 사람 수")
    Long waitingCount,
    @Schema(description = "진입 가능 여부")
    boolean canEnter
) {
    public static TokenResponse from(Queue queue) {
        return new TokenResponse(queue.getUuid(), null, false);
    }

}