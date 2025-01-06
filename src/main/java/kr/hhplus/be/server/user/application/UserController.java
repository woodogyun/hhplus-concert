package kr.hhplus.be.server.user.application;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.user.dto.PointResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/user")
@Tag(name = "유저 API", description = "유저 API")
public class UserController {
    
    @Operation(summary = "포인트 조회")
    @GetMapping("/{user-id}/point")
    public PointResponse getBalance(@PathVariable(value = "user-id") String concertId) {
        return new PointResponse(1);
    }
    
    @Operation(summary = "잔액 조회")
    @PostMapping("/{user-id}/point/charge")
    public PointResponse pointCharge(@PathVariable(value = "user-id") String concertId) {
        return new PointResponse(1);
    }

}
