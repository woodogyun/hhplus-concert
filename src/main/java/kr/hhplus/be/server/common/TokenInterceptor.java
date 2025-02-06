package kr.hhplus.be.server.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.common.exception.TokenException;
import kr.hhplus.be.server.api.queue.infra.QueueRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    final private QueueRepositoryImpl queueRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String uuid = request.getHeader("x-token");

        if (!queueRepository.existsByUuid(uuid)) {
            throw new TokenException(ErrorCode.EXPIRED_TOKEN);
        }
        return true; // 컨트롤러로의 접근을 허용합니다.
    }
}