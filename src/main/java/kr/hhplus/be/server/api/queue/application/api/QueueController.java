package kr.hhplus.be.server.api.queue.application.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.api.queue.application.dto.TokenRequest;
import kr.hhplus.be.server.api.queue.application.dto.TokenResponse;
import kr.hhplus.be.server.api.queue.domain.service.QueueService;
import kr.hhplus.be.server.common.annotation.ApiLog;
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

    @Operation(summary = "콘서트 대기열 순번 조회")
    @GetMapping("/status")
    public ResponseEntity<TokenResponse> getQueueStatus(@RequestHeader("x-token") String uuid) {
         return ResponseEntity.ok(queueService.getQueueStatus(uuid));
    }

    @PostMapping("/token")
    @Operation(summary = "토큰 생성")
    public ResponseEntity<TokenResponse> generateToken(@RequestBody TokenRequest tokenRequest) {
        TokenResponse tokenResponse = queueService.generateToken(tokenRequest.userId());

        // x-token 헤더에 UUID 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-token", tokenResponse.uuid());
        return new ResponseEntity<>(tokenResponse, headers, HttpStatus.OK);
    }

}
