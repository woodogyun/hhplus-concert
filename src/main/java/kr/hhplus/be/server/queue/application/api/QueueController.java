package kr.hhplus.be.server.queue.application.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.common.annotation.ApiLog;
import kr.hhplus.be.server.queue.application.dto.TokenRequest;
import kr.hhplus.be.server.queue.application.dto.TokenResponse;
import kr.hhplus.be.server.queue.domain.entity.Queue;
import kr.hhplus.be.server.queue.domain.service.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/queue")
@Tag(name = "대기열 API", description = "대기열 API")
@RequiredArgsConstructor
@ApiLog
public class QueueController {

    private final QueueService queueService;

    /**
     * 대기열 진입 체크를 위한 API
     * @param uuid
     * @return
     */
    @Operation(summary = "콘서트 대기열 순번 조회")
    @GetMapping("/{concert-id}/status")
    public ResponseEntity<TokenResponse> getQueueStatus(@RequestHeader("x-token") String uuid, @PathVariable("concert-id")Long concertId) {
         return ResponseEntity.ok(queueService.getQueueStatus(uuid, concertId));
    }

    @PostMapping("/token")
    @Operation(summary = "토큰 생성")
    public ResponseEntity<Queue> generateToken(@RequestBody TokenRequest tokenRequest) {
        Long concertId = tokenRequest.getConcertId();
        Long userId = tokenRequest.getUserId();

        // Queue 객체 생성
        Queue queue = queueService.generateQueue(concertId, userId);

        // x-token 헤더에 UUID 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-token", queue.getUuid());

        return new ResponseEntity<>(queue, headers, HttpStatus.OK);
    }

}
