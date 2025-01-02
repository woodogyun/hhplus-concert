package kr.hhplus.be.server.module.queue.application;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.module.queue.dto.TokenResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/queue")
@Tag(name = "대기열 API", description = "대기열 API")
public class QueueController {
    
    @Operation(summary = "토큰 대기열")
    @GetMapping("/status")
    public TokenResponse getQueueStatus(@RequestParam("uuid") String uuid) {
        return new TokenResponse("123-123", "ACTIVE");
    }
    
}
