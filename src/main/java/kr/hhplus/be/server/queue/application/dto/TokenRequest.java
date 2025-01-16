package kr.hhplus.be.server.queue.application.dto;

import lombok.Getter;

@Getter
public class TokenRequest {
    private Long concertId;
    private Long userId;
}