package kr.hhplus.be.server.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.hhplus.be.server.common.exception.InvalidTokenException;
import kr.hhplus.be.server.queue.infra.QueueRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private QueueRepositoryImpl queueRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String uuid = request.getHeader("x-token");

        if (!queueRepository.existsByUuid(uuid)) {
            throw new InvalidTokenException("Unauthorized: Invalid or missing token");
        }

        return true; // 컨트롤러로의 접근을 허용합니다.
    }
}