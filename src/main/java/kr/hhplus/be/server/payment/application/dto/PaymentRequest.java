package kr.hhplus.be.server.payment.application.dto;

public record PaymentRequest (
        Long reservationId,
        Long userId,
        Integer amount,
        String token
)
{}
