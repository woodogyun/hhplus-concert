package kr.hhplus.be.server.user.application.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.payment.application.dto.PointResponse;
import kr.hhplus.be.server.payment.domain.entity.Point;
import kr.hhplus.be.server.payment.domain.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "유저 API", description = "유저 API")
public class UserController {

    private final PointService pointService;

    @Operation(summary = "포인트 조회")
    @GetMapping("/{user-id}/point")
    public PointResponse getPoint(@PathVariable(value = "user-id") Long userId) {
        Point point = pointService.getPoint(userId);
        return new PointResponse(point.getValue());
    }

    @Operation(summary = "포인트 충전")
    @PostMapping("/{user-id}/point/charge")
    public PointResponse pointCharge(@PathVariable(value = "user-id") Long userId, @RequestBody int value) {
        Long amount = pointService.chargePoint(userId, value);
        return new PointResponse(amount);
    }

}
