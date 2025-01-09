package kr.hhplus.be.server.queue.application.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.queue.domain.entity.Queue;
import kr.hhplus.be.server.queue.domain.service.QueueService;
import kr.hhplus.be.server.queue.application.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/queue")
@Tag(name = "대기열 API", description = "대기열 API")
@RequiredArgsConstructor
public class QueueController {

    private final QueueService queueService;

    /**
     * 대기열 진입 체크를 위한 API
     * @param uuid
     * @return
     */
    // TODO: scheduleId로 조회하도록 수정되어야함
    @Operation(summary = "대기열 순번 조회")
    @GetMapping("/{schedule-id}/status")
    public TokenResponse getQueueStatus(@RequestHeader("x-token") String uuid, @PathVariable("sche0dule-id")Long scheduleId) {
        //scheduleId
         return queueService.getQueueStatus(uuid, scheduleId);
    }

    @PostMapping("/token")
    @Operation(summary = "토큰 생성")
    public ResponseEntity<Queue> generateToken() {

        Long scheduleId = 1L; // 하드코딩된 scheduleId 생성
        Long userId = 1L;     // 하드코딩된 userId 생성

        // Queue 객체 생성
        Queue queue = queueService.generateQueue(scheduleId, userId);

        // x-token 헤더에 UUID 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-token", queue.getUuid());

        return new ResponseEntity<>(queue, headers, HttpStatus.OK);
    }

}
