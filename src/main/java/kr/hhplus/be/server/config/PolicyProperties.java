package kr.hhplus.be.server.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "policy")
@Getter
@Setter
public class PolicyProperties {

    private int maxQueueCapacity; // 진입 인원 수
    private int expirationMinutes; // 토큰 만료 시간
    private int seatExpiredMinutes; // 좌석 만료 시간

}