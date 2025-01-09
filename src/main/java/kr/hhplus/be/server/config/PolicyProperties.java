package kr.hhplus.be.server.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@ConfigurationProperties(prefix = "policy")
@Getter
@Setter
public class PolicyProperties {

    private int maxQueueCapacity; // 진입 인원 수
    private int expirationMinutes; // 토큰 만료 시간
    private int seatExpiredMinutes; // 좌석 만료 시간

    // 현재 시간에 expirationMinutes를 더한 만료 시간 계산
    public LocalDateTime calculateTokenExpirationTime() {
        return LocalDateTime.now().plusMinutes(expirationMinutes);
    }

    // 현재 시간에 seatExpiredMinutes를 더한 좌석 만료 시간 계산
    public LocalDateTime calculateSeatExpirationTime() {
        return LocalDateTime.now().plusMinutes(seatExpiredMinutes);
    }
}